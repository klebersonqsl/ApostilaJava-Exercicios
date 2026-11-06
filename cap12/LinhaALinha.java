import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LinhaALinha {
    public static void main(String[] args) throws IOException {
        Path arq = Path.of("acessos.log");

        // gravando um arquivo grande, linha a linha, com buffer
        try (BufferedWriter out = Files.newBufferedWriter(arq, StandardCharsets.UTF_8)) {
            for (int i = 1; i <= 100_000; i++) {
                String nivel = i % 1000 == 0 ? "ERRO" : "INFO";
                out.write(nivel + " requisição " + i);
                out.newLine();
            }
        }   // o try-with-resources fecha (e descarrega o buffer) automaticamente

        // lendo linha a linha: só uma linha fica na memória por vez
        int total = 0;
        int erros = 0;
        try (BufferedReader in = Files.newBufferedReader(arq, StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = in.readLine()) != null) {   // null indica o fim do arquivo
                total++;
                if (linha.startsWith("ERRO")) {
                    erros++;
                }
            }
        }
        System.out.printf("Linhas: %,d | erros: %d | tamanho: %,d bytes%n",
                total, erros, Files.size(arq));

        // a mesma contagem com Files.lines (stream preguiçoso)
        try (var linhas = Files.lines(arq)) {
            String primeiro = linhas.filter(l -> l.startsWith("ERRO"))
                    .findFirst().orElse("nenhum");
            System.out.println("Primeiro erro: " + primeiro);
        }
    }
}
