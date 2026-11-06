import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/** Imita a resposta da API pública ViaCEP para testes sem internet. */
public class ServidorCep {
    public static void main(String[] args) throws Exception {
        HttpServer s = HttpServer.create(new InetSocketAddress(8090), 0);
        s.createContext("/ws/", ex -> {
            String cep = ex.getRequestURI().getPath().split("/")[2];
            String json = cep.equals("01001000") ? """
                    {
                      "cep": "01001-000",
                      "logradouro": "Praça da Sé",
                      "complemento": "lado ímpar",
                      "bairro": "Sé",
                      "localidade": "São Paulo",
                      "uf": "SP",
                      "ibge": "3550308",
                      "ddd": "11"
                    }""" : "{\"erro\": \"true\"}";
            byte[] corpo = json.getBytes(StandardCharsets.UTF_8);
            ex.getResponseHeaders().set("Content-Type", "application/json;charset=utf-8");
            ex.sendResponseHeaders(200, corpo.length);
            try (OutputStream o = ex.getResponseBody()) {
                o.write(corpo);
            }
        });
        s.start();
    }
}
