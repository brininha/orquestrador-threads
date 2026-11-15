package dados;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ExportadorMetricas {

    public static final String CAMINHO_PADRAO = "data/saida_tempos.csv";
    private static final String CABECALHO = "proporcao_leitores,proporcao_escritores,tipo_trava,tempo_medio_ms";

    private final Path arquivo;

    public ExportadorMetricas(String caminhoArquivo) {
        this.arquivo = Path.of(caminhoArquivo);
    }

    public ExportadorMetricas() {
        this(CAMINHO_PADRAO);
    }

    public void salvarRodada(int leitores, int escritores, String tipoTrava, long tempoMs) {
        if (!"global".equals(tipoTrava) && !"leitor_escritor".equals(tipoTrava)) {
            throw new IllegalArgumentException("tipoTrava deve ser \"global\" ou \"leitor_escritor\", recebido: " + tipoTrava);
        }
        String linha = leitores + "," + escritores + "," + tipoTrava + "," + tempoMs + System.lineSeparator();
        try {
            if (Files.notExists(arquivo)) {
                Files.createDirectories(arquivo.getParent() != null ? arquivo.getParent() : Path.of("."));
                Files.writeString(arquivo, CABECALHO + System.lineSeparator(), StandardCharsets.UTF_8);
            }
            Files.writeString(arquivo, linha, StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao gravar metricas em " + arquivo, e);
        }
    }
}
