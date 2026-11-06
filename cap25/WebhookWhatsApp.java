import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Recebe os eventos do WhatsApp: mensagens dos clientes e status das enviadas.
 * Em produção, fica atrás de um endereço HTTPS público cadastrado no painel da Meta.
 */
public class WebhookWhatsApp {

    private final String tokenVerificacao;    // você inventa e cadastra no painel
    private final byte[] segredoApp;          // "App secret" do aplicativo na Meta
    private final ObjectMapper json = new ObjectMapper();

    public WebhookWhatsApp(String tokenVerificacao, String segredoApp) {
        this.tokenVerificacao = tokenVerificacao;
        this.segredoApp = segredoApp.getBytes(StandardCharsets.UTF_8);
    }

    public void iniciar(int porta) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(porta), 0);
        servidor.createContext("/webhook", t -> {
            try {
                if (t.getRequestMethod().equals("GET")) {
                    verificar(t);
                } else {
                    receber(t);
                }
            } catch (Exception e) {
                responder(t, 500, "erro");
            }
        });
        servidor.start();
        System.out.println("Webhook ouvindo em http://localhost:" + porta + "/webhook");
    }

    /** 1) Cadastro: a Meta confere se o endereço é seu, pelo token de verificação. */
    private void verificar(HttpExchange t) throws IOException {
        Map<String, String> q = parametros(t.getRequestURI().getRawQuery());
        if ("subscribe".equals(q.get("hub.mode"))
                && tokenVerificacao.equals(q.get("hub.verify_token"))) {
            System.out.println("[webhook] verificação aceita");
            responder(t, 200, q.get("hub.challenge"));   // o desafio, em texto puro
        } else {
            System.out.println("[webhook] verificação recusada");
            responder(t, 403, "token inválido");
        }
    }

    /** 2) Eventos: confere a assinatura e só então processa o JSON. */
    private void receber(HttpExchange t) throws Exception {
        byte[] corpo = t.getRequestBody().readAllBytes();
        String assinatura = t.getRequestHeaders().getFirst("X-Hub-Signature-256");
        if (!assinaturaValida(corpo, assinatura)) {
            System.out.println("[webhook] assinatura inválida: evento descartado");
            responder(t, 401, "assinatura inválida");
            return;
        }
        JsonNode raiz = json.readTree(corpo);
        for (JsonNode entrada : raiz.path("entry")) {
            for (JsonNode mudanca : entrada.path("changes")) {
                JsonNode valor = mudanca.path("value");
                for (JsonNode m : valor.path("messages")) {
                    String nome = valor.path("contacts").path(0)
                            .path("profile").path("name").asText();
                    System.out.println("[webhook] mensagem de " + nome + " ("
                            + m.path("from").asText() + "): "
                            + m.path("text").path("body").asText());
                }
                for (JsonNode s : valor.path("statuses")) {
                    System.out.println("[webhook] status da " + s.path("id").asText()
                            + ": " + s.path("status").asText());
                }
            }
        }
        responder(t, 200, "ok");                      // responda rápido: a Meta reenvia
    }

    /** X-Hub-Signature-256 = "sha256=" + HMAC-SHA256(corpo, segredo do app) em hexa. */
    boolean assinaturaValida(byte[] corpo, String cabecalho) throws Exception {
        if (cabecalho == null || !cabecalho.startsWith("sha256=")) {
            return false;
        }
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(segredoApp, "HmacSHA256"));
        byte[] esperado = mac.doFinal(corpo);
        byte[] recebido = HexFormat.of().parseHex(cabecalho.substring(7));
        return MessageDigest.isEqual(esperado, recebido);  // tempo constante
    }

    private static Map<String, String> parametros(String query) {
        Map<String, String> mapa = new HashMap<>();
        if (query != null) {
            for (String par : query.split("&")) {
                String[] kv = par.split("=", 2);
                mapa.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                        kv.length > 1
                                ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "");
            }
        }
        return mapa;
    }

    private static void responder(HttpExchange t, int status, String texto)
            throws IOException {
        byte[] b = texto.getBytes(StandardCharsets.UTF_8);
        t.sendResponseHeaders(status, b.length);
        t.getResponseBody().write(b);
        t.close();
    }
}
