package concorrencia;

/**
 * Trava global: apenas uma thread (leitora ou escritora) fica na região crítica por vez;
 * as demais esperam.
 *
 * <p>Implementada com o mesmo monitor ({@code synchronized} + {@code wait/notifyAll}) da
 * {@link TravaLeitoresEscritores}, para que a comparação de desempenho meça a política de
 * acesso e não a primitiva de sincronização.
 *
 * <p>Há um único monitor (sem aquisição aninhada), a espera é feita em
 * laço sobre a condição e toda saída acorda as threads em espera.
 */
public class TravaGlobal implements RegiaoCritica {

    public static final String TIPO = "global";

    private boolean ocupada = false;

    @Override public void entrarLeitor()   { entrar(); }
    @Override public void sairLeitor()     { sair(); }
    @Override public void entrarEscritor() { entrar(); }
    @Override public void sairEscritor()   { sair(); }

    private synchronized void entrar() {
        boolean interrompida = false;
        while (ocupada) {
            try {
                wait();
            } catch (InterruptedException e) {
                interrompida = true;
            }
        }
        ocupada = true;
        if (interrompida) {
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void sair() {
        if (!ocupada) {
            throw new IllegalStateException("sair() chamado sem nenhuma thread na região crítica");
        }
        ocupada = false;
        notifyAll();
    }
}
