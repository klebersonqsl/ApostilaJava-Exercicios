import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/** Cliente da API oficial do WhatsApp (WhatsApp Business Platform, Cloud API). */
public class WhatsAppCloud {

    public static final String VERSAO = "v26.0";        // versão da Graph API

    private final String base;                // https://graph.facebook.com
    private final String idTelefone;          // "Phone number ID" do painel da Meta
    private final String token;               // token de acesso (usuário do sistema)
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper json = new ObjectMapper();

    public WhatsAppCloud(String base, String idTelefone, String token) {
        this.base = base;
        this.idTelefone = idTelefone;
        this.token = token;
    }

    /** Texto livre: só é entregue se o cliente falou com você nas últimas 24 horas. */
    public String enviarTexto(String para, String texto)
            throws IOException, InterruptedException {
        ObjectNode corpo = json.createObjectNode()
                .put("messaging_product", "whatsapp")
                .put("to", para)
                .put("type", "text");
        corpo.putObject("text").put("body", texto);
        return enviar(corpo);
    }

    /** Modelo (template) aprovado pela Meta: pode iniciar a conversa a qualquer hora. */
    public String enviarModelo(String para, String modelo, String idioma,
                               String... valores)
            throws IOException, InterruptedException {
        ObjectNode corpo = json.createObjectNode()
                .put("messaging_product", "whatsapp")
                .put("to", para)
                .put("type", "template");
        ObjectNode tpl = corpo.putObject("template").put("name", modelo);
        tpl.putObject("language").put("code", idioma);
        if (valores.length > 0) {          // preenche {{1}}, {{2}}... do corpo do modelo
            var parametros = tpl.putArray("components").addObject()
                    .put("type", "body").putArray("parameters");
            for (String v : valores) {
                parametros.addObject().put("type", "text").put("text", v);
            }
        }
        return enviar(corpo);
    }

    /** Envia e devolve o id da mensagem (wamid...), usado depois nos status. */
    private String enviar(ObjectNode corpo) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(
                        URI.create(base + "/" + VERSAO + "/" + idTelefone + "/messages"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.writeValueAsString(corpo)))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        JsonNode r = json.readTree(resp.body());
        if (resp.statusCode() != 200) {
            JsonNode erro = r.path("error");
            throw new IOException("WhatsApp recusou (" + erro.path("code").asInt() + "): "
                    + erro.path("message").asText());
        }
        return r.path("messages").get(0).path("id").asText();
    }
}
