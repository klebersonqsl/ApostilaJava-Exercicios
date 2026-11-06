import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

/** ViaCEP: gratuito e sem cadastro (https://viacep.com.br). */
public class CepViaCep implements BuscaCep {

    private final String base;              // https://viacep.com.br
    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5)).build();
    private final ObjectMapper json = new ObjectMapper();

    public CepViaCep(String base) {
        this.base = base;
    }

    @Override
    public Optional<Endereco> buscar(String cep) throws Exception {
        URI uri = URI.create(base + "/ws/" + BuscaCep.normalizar(cep) + "/json/");
        HttpRequest req = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(5)).build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        JsonNode e = json.readTree(resp.body());
        if (resp.statusCode() != 200 || e.has("erro")) {
            return Optional.empty();
        }
        return Optional.of(new Endereco(e.path("cep").asText().replace("-", ""),
                e.path("logradouro").asText(), e.path("bairro").asText(),
                e.path("localidade").asText(), e.path("uf").asText()));
    }
}
