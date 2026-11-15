package threads;

import concorrencia.RegiaoCritica;
import dados.BaseDados;

public class ThreadLeitora extends ThreadTrabalhadora {

    // Guarda o último valor lido para o JIT não descartar a leitura
    private String ultimaLeitura;

    public ThreadLeitora(RegiaoCritica regiao, BaseDados base) {
        super(regiao, base);
    }

    @Override protected void entrar() { regiao.entrarLeitor(); }
    @Override protected void sair()   { regiao.sairLeitor(); }
    @Override protected void acessar(int indice) { ultimaLeitura = base.ler(indice); }
}
