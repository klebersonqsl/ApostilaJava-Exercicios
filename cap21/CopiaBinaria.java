import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class CopiaBinaria {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Path origem = Path.of("paisagem.jpg");
        Path destino = Path.of("copia.jpg");

        // copiando "na mão", em blocos de 8 KB
        long total = 0;
        try (InputStream in = new BufferedInputStream(Files.newInputStream(origem));
             OutputStream out =
                     new BufferedOutputStream(Files.newOutputStream(destino))) {
            byte[] buffer = new byte[8192];
            int lidos;
            while ((lidos = in.read(buffer)) != -1) {    // -1 indica o fim
                out.write(buffer, 0, lidos);
                total += lidos;
            }
        }
        System.out.println("Bytes copiados: " + total);

        // os primeiros bytes de um JPEG são sempre FF D8 FF
        byte[] todos = Files.readAllBytes(destino);
        System.out.printf("Assinatura: %02X %02X %02X%n", todos[0], todos[1], todos[2]);

        // conferindo se a cópia é idêntica com um hash SHA-256
        System.out.println("SHA-256 origem:  " + sha256(origem));
        System.out.println("SHA-256 cópia:   " + sha256(destino));
        System.out.println("Iguais? " + (Files.mismatch(origem, destino) == -1));
    }

    static String sha256(Path p) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(Files.readAllBytes(p));
        return HexFormat.of().formatHex(hash).substring(0, 32) + "...";
    }
}
