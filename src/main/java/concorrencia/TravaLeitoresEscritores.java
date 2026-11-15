package concorrencia;

/**
 * Leitores e escritores com prioridade para os leitores.
 *
 * <ul>
 *   <li>Vários leitores podem estar na região crítica ao mesmo tempo.</li>
 *   <li>Um escritor só entra com a base vazia (sem leitores e sem outro escritor).</li>
 *   <li>Leitores esperando têm preferência sobre escritores esperando: o escritor não entra
 *       enquanto houver leitor ativo <b>ou</b> aguardando.</li>
 * </ul>
 *
 * <p>Inanição de escritores é esperada quando há muitos leitores (ex.: 99x1), pois é
 * consequência da prioridade de leitura. Deadlock não ocorre: há um único monitor (sem
 * aquisição aninhada), toda espera é em laço sobre a condição, toda saída que pode liberar
 * alguém chama {@code notifyAll()} e leitores só esperam enquanto há escritor ativo, que
 * sempre sai após a soneca de 1ms.
 *
 * <p>O monitor foi preferido a semáforos/{@code ReentrantLock} porque na solução clássica o
 * primeiro leitor trava a base e o último a libera; com {@code ReentrantLock} isso lança
 * {@code IllegalMonitorStateException}, e com semáforos a preferência de leitores na saída
 * de um escritor depende do escalonador.
 */
public class TravaLeitoresEscritores implements RegiaoCritica {

    public static final String TIPO = "leitor_escritor";

    private int leitoresAtivos = 0;
    private int leitoresEsperando = 0;
    private boolean escritorAtivo = false;

    @Override
    public synchronized void entrarLeitor() {
        boolean interrompida = false;
        leitoresEsperando++;
        while (escritorAtivo) {
            try {
                wait();
            } catch (InterruptedException e) {
                interrompida = true;
            }
        }
        leitoresEsperando--;
        leitoresAtivos++;
        if (interrompida) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public synchronized void sairLeitor() {
        if (leitoresAtivos == 0) {
            throw new IllegalStateException("sairLeitor() chamado sem leitor na região crítica");
        }
        leitoresAtivos--;
        if (leitoresAtivos == 0) {
            notifyAll();
        }
    }

    @Override
    public synchronized void entrarEscritor() {
        boolean interrompida = false;
        while (escritorAtivo || leitoresAtivos > 0 || leitoresEsperando > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                interrompida = true;
            }
        }
        escritorAtivo = true;
        if (interrompida) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public synchronized void sairEscritor() {
        if (!escritorAtivo) {
            throw new IllegalStateException("sairEscritor() chamado sem escritor na região crítica");
        }
        escritorAtivo = false;
        notifyAll();
    }
}
