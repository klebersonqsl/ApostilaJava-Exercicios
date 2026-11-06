import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteHttp {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest req = HttpRequest
                .newBuilder(URI.create("http://localhost:8080/lista"))
                .GET()
                .build();
        HttpResponse<String> resp =
                cliente.send(req, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + resp.statusCode());
        String tipo = resp.headers().firstValue("Content-Type").orElse("?");
        System.out.println("Content-Type: " + tipo);
        System.out.println("Arquivos no servidor:");
        resp.body().lines().forEach(l -> System.out.println("  - " + l));
    }
}
