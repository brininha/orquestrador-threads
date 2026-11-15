package concorrencia;

public interface RegiaoCritica {
    void entrarLeitor();
    void sairLeitor();
    void entrarEscritor();
    void sairEscritor();
}