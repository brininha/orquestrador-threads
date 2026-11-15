package concorrencia;

/**
 * Contrato da região crítica (a base de dados inteira).
 *
 * <p>Uso esperado pelas threads:
 * <pre>
 * regiao.entrarLeitor();      // fora do try: só libera quem de fato adquiriu
 * try {
 *     // acessos + soneca
 * } finally {
 *     regiao.sairLeitor();
 * }
 * </pre>
 *
 * <p>Os métodos {@code entrar...()} sempre retornam com a trava adquirida (não lançam
 * exceção verificada; uma interrupção durante a espera é preservada no status da thread).
 * Chamar {@code sair...()} sem o {@code entrar...()} correspondente lança
 * {@link IllegalStateException}.
 */
public interface RegiaoCritica {
    void entrarLeitor();
    void sairLeitor();
    void entrarEscritor();
    void sairEscritor();
}
