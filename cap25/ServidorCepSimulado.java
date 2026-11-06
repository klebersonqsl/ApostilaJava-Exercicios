import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Servidor local que imita as respostas da API dos Correios e do ViaCEP, para os
 * exemplos funcionarem sem contrato e sem internet. Porta 8095.
 * Com o argumento "correios-fora", a API dos Correios responde 503 (fora do ar).
 */
public class ServidorCepSimulado {

    static final Map<String, String[]> BASE = Map.of(
            "01001000", new String[]{"Praça da Sé", "Sé", "São Paulo", "SP"},
            "70040010", new String[]{"Esplanada dos Ministérios",
                    "Zona Cívico-Administrativa", "Brasília", "DF"});

    public static void main(String[] args) throws IOException {
        boolean correiosFora = args.length > 0 && args[0].equals("correios-fora");
        HttpServer s = HttpServer.create(new InetSocketAddress(8095), 0);

        s.createContext("/token/v1/autentica/cartaopostagem", t -> {
            if (correiosFora) { responder(t, 503, "{}"); return; }
            String exp = OffsetDateTime.now().plusHours(24).withNano(0).toString();
            responder(t, 201, "{\"ambiente\":\"PRODUCAO\",\"perfil\":\"PJ\","
                    + "\"emissao\":\""
                    + OffsetDateTime.now().withNano(0) + "\",\"expiraEm\":\"" + exp
                    + "\",\"token\":\"eyJhbGciOiJSUzI1NiJ9.exemplo.assinatura\"}");
        });
        s.createContext("/cep/v2/enderecos/", t -> {
            String cep = t.getRequestURI().getPath().replaceAll(".*/", "");
            String[] e = BASE.get(cep);
            if (e == null) {
                responder(t, 404, "{\"msgs\":[\"CEP não encontrado\"]}");
                return;
            }
            responder(t, 200, "{\"cep\":\"" + cep + "\",\"uf\":\"" + e[3]
                    + "\",\"localidade\":\""
                    + e[2] + "\",\"logradouro\":\"" + e[0] + "\",\"bairro\":\"" + e[1]
                    + "\",\"tipoCEP\":2}");
        });
        s.createContext("/ws/", t -> {
            String cep = t.getRequestURI().getPath().split("/")[2];
            String[] e = BASE.get(cep);
            responder(t, 200, e == null ? "{\"erro\": \"true\"}"
                    : "{\"cep\":\"" + cep.substring(0, 5) + "-" + cep.substring(5)
                    + "\",\"logradouro\":\"" + e[0] + "\",\"bairro\":\"" + e[1]
                    + "\",\"localidade\":\"" + e[2] + "\",\"uf\":\"" + e[3] + "\"}");
        });
        s.start();
        System.out.println("Simulador de CEP em http://localhost:8095"
                + (correiosFora ? " (Correios fora do ar)" : ""));
    }

    static void responder(HttpExchange t, int status, String json) throws IOException {
        byte[] b = json.getBytes(StandardCharsets.UTF_8);
        t.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        t.sendResponseHeaders(status, b.length);
        t.getResponseBody().write(b);
        t.close();
    }
}
