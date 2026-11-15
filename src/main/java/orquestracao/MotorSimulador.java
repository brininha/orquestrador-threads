package orquestracao;

import concorrencia.RegiaoCritica;
import concorrencia.TravaGlobal;
import concorrencia.TravaLeitoresEscritores;
import dados.BaseDados;
import dados.ExportadorMetricas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * T4.2: laço mestre do experimento.
 *
 * <p>Para cada proporção (0 leitoras/100 escritoras até 100/0) e cada uma das repetições,
 * roda uma rodada com a {@link TravaGlobal} e outra com a {@link TravaLeitoresEscritores}.
 * Cada rodada tem trava nova e arranjo de threads novo, montado e embaralhado do zero.
 *
 * <p>O cronômetro começa logo depois do arranjo pronto (threads em {@code NEW}) e para depois
 * do {@code join()} da última thread. Nenhuma escrita em disco nem {@code println} acontece
 * dentro da medição: os tempos ficam em memória e só vão para o CSV no final.
 *
 * <p>Uso (a partir da raiz do repositório):
 * <pre>
 * java -cp out orquestracao.MotorSimulador [repeticoes] [caminho_bd] [caminho_csv]
 * </pre>
 * Padrões: 50 repetições, {@code data/bd.txt} e {@code data/saida_tempos.csv}.
 * O CSV de saída é recriado a cada execução.
 */
public final class MotorSimulador {

    static final int REPETICOES_PADRAO = 50;
    static final int PROPORCOES = MontadorArranjo.TOTAL_THREADS + 1; // 0..100 leitoras
    static final int RODADAS_AQUECIMENTO = 20;

    private static final int GLOBAL = 0;
    private static final int LEITOR_ESCRITOR = 1;
    private static final String[] TIPOS = {TravaGlobal.TIPO, TravaLeitoresEscritores.TIPO};

    private final BaseDados base;
    private final MontadorArranjo montador;
    private final AtomicReference<Throwable> falhaEmThread = new AtomicReference<>();

    MotorSimulador(BaseDados base, Random aleatorio) {
        this.base = base;
        this.montador = new MontadorArranjo(aleatorio);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int repeticoes = args.length > 0 ? Integer.parseInt(args[0]) : REPETICOES_PADRAO;
        String caminhoBd = args.length > 1 ? args[1] : "data/bd.txt";
        String caminhoCsv = args.length > 2 ? args[2] : ExportadorMetricas.CAMINHO_PADRAO;
        if (repeticoes < 1) {
            throw new IllegalArgumentException("repeticoes deve ser >= 1, recebido: " + repeticoes);
        }

        MotorSimulador motor = new MotorSimulador(new BaseDados(caminhoBd), new Random());

        System.out.println("Aquecendo a JVM (" + RODADAS_AQUECIMENTO + " rodadas descartadas)...");
        motor.aquecer();

        System.out.println("Executando " + PROPORCOES + " proporções x " + repeticoes
                + " repetições x 2 travas...");
        long inicio = System.currentTimeMillis();
        long[][][] tempos = motor.executarExperimento(repeticoes);
        long duracaoSeg = (System.currentTimeMillis() - inicio) / 1000;

        exportar(tempos, caminhoCsv);
        System.out.println("Concluído em " + duracaoSeg + " s. Tempos salvos em " + caminhoCsv);
    }

    /**
     * Rodadas descartadas para o JIT compilar os caminhos quentes antes da medição;
     * sem isso, as primeiras proporções (0 leitoras) sairiam artificialmente lentas.
     */
    void aquecer() throws InterruptedException {
        for (int i = 0; i < RODADAS_AQUECIMENTO; i++) {
            int leitores = i * (MontadorArranjo.TOTAL_THREADS / RODADAS_AQUECIMENTO);
            executarRodada(leitores, new TravaGlobal());
            executarRodada(leitores, new TravaLeitoresEscritores());
        }
    }

    /** @return tempos[tipoTrava][leitores][repeticao], em ms */
    long[][][] executarExperimento(int repeticoes) throws InterruptedException {
        long[][][] tempos = new long[TIPOS.length][PROPORCOES][repeticoes];

        for (int leitores = 0; leitores < PROPORCOES; leitores++) {
            for (int rep = 0; rep < repeticoes; rep++) {
                // Alterna qual trava roda primeiro para nenhuma delas levar sempre
                // a vantagem/desvantagem de rodar logo após a outra.
                boolean globalPrimeiro = rep % 2 == 0;
                if (globalPrimeiro) {
                    tempos[GLOBAL][leitores][rep] = executarRodada(leitores, new TravaGlobal());
                }
                tempos[LEITOR_ESCRITOR][leitores][rep] =
                        executarRodada(leitores, new TravaLeitoresEscritores());
                if (!globalPrimeiro) {
                    tempos[GLOBAL][leitores][rep] = executarRodada(leitores, new TravaGlobal());
                }
            }
            // Fora da medição: só informa o progresso entre uma proporção e outra.
            System.out.println("  proporção " + leitores + "/" + (MontadorArranjo.TOTAL_THREADS - leitores)
                    + " concluída");
        }
        return tempos;
    }

    /** Executa uma rodada completa e devolve o tempo em ms (arredondado). */
    long executarRodada(int leitores, RegiaoCritica regiao) throws InterruptedException {
        Thread[] arranjo = montador.montar(leitores, regiao, base);
        for (Thread t : arranjo) {
            t.setUncaughtExceptionHandler((th, e) -> falhaEmThread.compareAndSet(null, e));
        }

        long inicio = System.nanoTime(); // arranjo pronto, nenhuma thread iniciada
        for (Thread t : arranjo) {
            t.start();
        }
        for (Thread t : arranjo) {
            t.join(); // a thread principal só segue depois que a última terminar
        }
        long fim = System.nanoTime();

        Throwable falha = falhaEmThread.get();
        if (falha != null) {
            throw new IllegalStateException("Thread falhou durante a rodada com " + leitores
                    + " leitoras (" + regiao.getClass().getSimpleName() + ")", falha);
        }
        return Math.round((fim - inicio) / 1_000_000.0);
    }

    private static void exportar(long[][][] tempos, String caminhoCsv) throws IOException {
        Files.deleteIfExists(Path.of(caminhoCsv)); // o exportador só acrescenta linhas
        ExportadorMetricas exportador = new ExportadorMetricas(caminhoCsv);
        for (int leitores = 0; leitores < PROPORCOES; leitores++) {
            int escritores = MontadorArranjo.TOTAL_THREADS - leitores;
            for (int rep = 0; rep < tempos[GLOBAL][leitores].length; rep++) {
                for (int tipo = 0; tipo < TIPOS.length; tipo++) {
                    exportador.salvarRodada(leitores, escritores, TIPOS[tipo], tempos[tipo][leitores][rep]);
                }
            }
        }
    }
}
