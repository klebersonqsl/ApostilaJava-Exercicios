import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Imita o endpoint /{versão}/{id}/messages da Graph API, respondendo no mesmo formato
 * da Meta, para testar o cliente sem conta nem número de telefone. Porta 8096.
 */
public class SimuladorMeta {
    public static void main(String[] args) throws IOException {
        AtomicInteger contador = new AtomicInteger();
        HttpServer s = HttpServer.create(new InetSocketAddress(8096), 0);
        s.createContext("/", t -> {
            String corpo = new String(t.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8);
            String auth = t.getRequestHeaders().getFirst("Authorization");
            String resposta;
            int status;
            if (auth == null || !auth.equals("Bearer TOKEN-DE-TESTE")) {
                status = 401;
                resposta = "{\"error\":{\"message\":\"Invalid OAuth access token.\","
                        + "\"type\":\"OAuthException\",\"code\":190}}";
            } else {
                String para = corpo.replaceAll(".*\"to\":\"(\\d+)\".*", "$1");
                status = 200;
                resposta = "{\"messaging_product\":\"whatsapp\","
                        + "\"contacts\":[{\"input\":\""
                        + para + "\",\"wa_id\":\"" + para + "\"}],\"messages\":[{\"id\":"
                        + "\"wamid.HBgNNTU3MTk5OTk5MDAwMBUCABEYEjAw"
                        + contador.incrementAndGet()
                        + "\"}]}";
            }
            System.out.println("[meta] " + t.getRequestMethod() + " " + t.getRequestURI()
                    + " -> " + status);
            byte[] b = resposta.getBytes(StandardCharsets.UTF_8);
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(status, b.length);
            t.getResponseBody().write(b);
            t.close();
        });
        s.start();
    }
}
