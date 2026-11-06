import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Upload {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path arquivo = Path.of("mini.png");
        // espaços e acentos precisam ser codificados na URL
        String nome = URLEncoder.encode("miniatura paisagem.png", StandardCharsets.UTF_8);

        HttpRequest req = HttpRequest.newBuilder(
                        URI.create("http://localhost:8080/upload?nome=" + nome))
                .header("Content-Type", Files.probeContentType(arquivo))
                .POST(HttpRequest.BodyPublishers.ofFile(arquivo))  // os bytes do arquivo
                .build();

        HttpResponse<String> resp = HttpClient.newHttpClient()
                .send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println("Enviados " + Files.size(arquivo) + " bytes");
        System.out.println("Resposta " + resp.statusCode() + ": " + resp.body());
    }
}
