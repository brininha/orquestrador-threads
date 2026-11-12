package dados;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BaseDados {

    public static final int TOTAL_PALAVRAS = 36242;
    public static final String VALOR_ESCRITA = "MODIFICADO";

    private final String[] palavras;

    public BaseDados(String caminhoArquivo) throws IOException {
        List<String> linhas = Files.readAllLines(Path.of(caminhoArquivo));
        if (linhas.size() != TOTAL_PALAVRAS) {
            throw new IOException(
                    "Arquivo " + caminhoArquivo + " possui " + linhas.size()
                            + " linhas; esperado " + TOTAL_PALAVRAS + " palavras.");
        }
        palavras = linhas.toArray(new String[0]);
    }

    public String ler(int indice) {
        verificarIndice(indice);
        return palavras[indice];
    }

    public void escrever(int indice) {
        verificarIndice(indice);
        palavras[indice] = VALOR_ESCRITA;
    }

    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= palavras.length) {
            throw new IndexOutOfBoundsException(
                    "Indice " + indice + " fora do intervalo [0, " + (palavras.length - 1) + "]");
        }
    }
}
