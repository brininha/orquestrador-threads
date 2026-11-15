package threads;


import dados.BaseDados;
import java.util.concurrent.ThreadLocalRandom;
import concorrencia.RegiaoCritica;

abstract class ThreadTrabalhadora extends Thread {

    static final int TOTAL_ACESSOS = 100;
    static final long SONECA_MS = 1;

    protected final RegiaoCritica regiao;
    protected final BaseDados base;

    protected ThreadTrabalhadora(RegiaoCritica regiao, BaseDados base) {
        this.regiao = regiao;
        this.base = base;
    }

    protected abstract void entrar();
    protected abstract void sair();
    protected abstract void acessar(int indice);

    @Override
    public final void run() {
        entrar(); // fora do try: só libera a trava quem de fato a adquiriu
        try {
            ThreadLocalRandom rnd = ThreadLocalRandom.current();
            for (int i = 0; i < TOTAL_ACESSOS; i++) {
                acessar(rnd.nextInt(BaseDados.TOTAL_PALAVRAS));
            }
            Thread.sleep(SONECA_MS); // ainda dentro da região crítica
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            sair();
        }
    }
}