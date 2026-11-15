package orquestracao;

import concorrencia.RegiaoCritica;
import dados.BaseDados;
import threads.ThreadEscritora;
import threads.ThreadLeitora;

import java.util.Random;

/**
 * T4.1: monta o arranjo de 100 threads de uma rodada.
 *
 * <p>Segue o passo 1 do enunciado ao pé da letra: cada objeto (leitor ou escritor) é criado
 * com {@code new} e guardado em uma posição <b>ainda vazia</b> do arranjo, sorteada
 * aleatoriamente. As threads saem daqui no estado {@code NEW}, prontas para o cronômetro.
 *
 * <p>Um arranjo novo deve ser montado a cada rodada; nada aqui é reaproveitado.
 */
public final class MontadorArranjo {

    public static final int TOTAL_THREADS = 100;

    private final Random aleatorio;

    public MontadorArranjo(Random aleatorio) {
        this.aleatorio = aleatorio;
    }

    /**
     * @param leitores quantidade de leitoras (0 a 100); o restante são escritoras
     * @param regiao   trava compartilhada por todas as threads da rodada
     * @param base     base de dados em memória
     */
    public Thread[] montar(int leitores, RegiaoCritica regiao, BaseDados base) {
        if (leitores < 0 || leitores > TOTAL_THREADS) {
            throw new IllegalArgumentException(
                    "leitores deve estar entre 0 e " + TOTAL_THREADS + ", recebido: " + leitores);
        }

        Thread[] arranjo = new Thread[TOTAL_THREADS];

        // Posições ainda vazias ficam em livres[0..qtdLivres-1]. Sortear um índice desse trecho
        // e trocá-lo com o último garante que cada posição seja escolhida uma única vez (O(1)).
        int[] livres = new int[TOTAL_THREADS];
        for (int i = 0; i < TOTAL_THREADS; i++) {
            livres[i] = i;
        }
        int qtdLivres = TOTAL_THREADS;

        for (int criadas = 0; criadas < TOTAL_THREADS; criadas++) {
            Thread nova = criadas < leitores
                    ? new ThreadLeitora(regiao, base)
                    : new ThreadEscritora(regiao, base);

            int sorteio = aleatorio.nextInt(qtdLivres);
            arranjo[livres[sorteio]] = nova;
            livres[sorteio] = livres[--qtdLivres];
        }
        return arranjo;
    }
}
