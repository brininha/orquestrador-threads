package threads;

import concorrencia.RegiaoCritica;
import dados.BaseDados;

public class ThreadEscritora extends ThreadTrabalhadora {

    public ThreadEscritora(RegiaoCritica regiao, BaseDados base) {
        super(regiao, base);
    }

    @Override protected void entrar() { regiao.entrarEscritor(); }
    @Override protected void sair()   { regiao.sairEscritor(); }
    @Override protected void acessar(int indice) { base.escrever(indice); }
}
