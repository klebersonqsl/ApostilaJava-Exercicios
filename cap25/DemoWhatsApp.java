import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/** Envia mensagens e testa o webhook. Uso: java DemoWhatsApp [urlGraph] */
public class DemoWhatsApp {
    public static void main(String[] args) throws Exception {
        String base = args.length > 0 ? args[0] : "https://graph.facebook.com";
        String id = System.getenv()
                .getOrDefault("WHATSAPP_ID_TELEFONE", "106540352242922");
        String token = System.getenv().getOrDefault("WHATSAPP_TOKEN", "TOKEN-DE-TESTE");

        // 1) envio
        WhatsAppCloud wa = new WhatsAppCloud(base, id, token);
        System.out.println("Modelo enviado: " + wa.enviarModelo("5571999990000",
                "confirmacao_pedido", "pt_BR", "Ana", "2026-00153", "R$ 149,90"));
        System.out.println("Texto enviado:  " + wa.enviarTexto("5571999990000",
                "Seu pedido saiu para entrega hoje!"));
        try {
            new WhatsAppCloud(base, id, "token-vencido")
                    .enviarTexto("5571999990000", "Oi");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 2) recebimento: o webhook sobe e simulamos as chamadas que a Meta faria
        WebhookWhatsApp webhook = new WebhookWhatsApp("minha-senha-de-verificacao",
                "segredo-do-app");
        webhook.iniciar(8097);
        HttpClient http = HttpClient.newHttpClient();
        String url = "http://localhost:8097/webhook";

        HttpRequest verificacao = HttpRequest.newBuilder(URI.create(url
                + "?hub.mode=subscribe&hub.verify_token=minha-senha-de-verificacao"
                + "&hub.challenge=1158201444")).build();
        HttpResponse<String> r =
                http.send(verificacao, HttpResponse.BodyHandlers.ofString());
        System.out.println("Resposta à verificação: " + r.statusCode() + " " + r.body());

        String evento = """
                {"object":"whatsapp_business_account","entry":[{"id":"102290129340398",
                "changes":[{"field":"messages","value":{"messaging_product":"whatsapp",
                "contacts":[{"profile":{"name":"Ana Souza"},"wa_id":"5571999990000"}],
                "messages":[{"from":"5571999990000","id":"wamid.ABGGFlA5Fpa",
                "timestamp":"1793635200","type":"text",
                "text":{"body":"Posso trocar o endereço de entrega?"}}]}}]}]}""";
        System.out.println("Evento assinado: " + postar(http, url, evento,
                assinar(evento, "segredo-do-app")));
        System.out.println("Evento forjado:  " + postar(http, url, evento,
                assinar(evento, "outro-segredo")));
        System.exit(0);
    }

    static int postar(HttpClient http, String url, String corpo, String assinatura)
            throws Exception {
        return http.send(HttpRequest.newBuilder(URI.create(url))
                        .header("X-Hub-Signature-256", assinatura)
                        .POST(HttpRequest.BodyPublishers.ofString(corpo)).build(),
                HttpResponse.BodyHandlers.ofString()).statusCode();
    }

    static String assinar(String corpo, String segredo) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(segredo.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"));
        return "sha256=" + HexFormat.of().formatHex(
                mac.doFinal(corpo.getBytes(StandardCharsets.UTF_8)));
    }
}
