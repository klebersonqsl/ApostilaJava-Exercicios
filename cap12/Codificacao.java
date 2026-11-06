import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Codificacao {
    public static void main(String[] args) throws IOException {
        Path antigo = Path.of("clientes_antigo.txt");   // gravado por um sistema antigo

        try {
            Files.readAllLines(antigo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Lendo como UTF-8: " + e.getClass().getSimpleName());
        }

        // ISO-8859-1 (Latin-1) ou windows-1252: a codificação "ANSI" do Windows
        for (String linha : Files.readAllLines(antigo, Charset.forName("windows-1252"))) {
            System.out.println("Lido como windows-1252: " + linha);
        }

        // convertendo para UTF-8
        String texto = Files.readString(antigo, Charset.forName("windows-1252"));
        Files.writeString(Path.of("clientes_utf8.txt"), texto, StandardCharsets.UTF_8);
        System.out.println("Bytes: " + Files.size(antigo) + " (ANSI) -> "
                + Files.size(Path.of("clientes_utf8.txt")) + " (UTF-8)");
    }
}
