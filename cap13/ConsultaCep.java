import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ConsultaCep {

    // só os campos que interessam; os demais são ignorados
    @JsonIgnoreProperties(ignoreUnknown = true)
    record Endereco(String cep, String logradouro, String bairro, String localidade,
                    String uf, String erro) {}

    private final String baseUrl;
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    ConsultaCep(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    Optional<Endereco> buscar(String cep) throws Exception {
        String soDigitos = cep.replaceAll("\\D", "");
        URI uri = URI.create(baseUrl + soDigitos + "/json/");
        HttpRequest req = HttpRequest.newBuilder(uri).build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        Endereco e = mapper.readValue(resp.body(), Endereco.class);
        return e.erro() == null ? Optional.of(e) : Optional.empty();
    }

    public static void main(String[] args) throws Exception {
        // API real: https://viacep.com.br/ws/   (aqui, um servidor local que a imita)
        String base = args.length > 0 ? args[0] : "https://viacep.com.br/ws/";
        ConsultaCep api = new ConsultaCep(base);

        for (String cep : new String[]{"01001-000", "99999-999"}) {
            api.buscar(cep).ifPresentOrElse(
                    e -> System.out.println(cep + ": " + e.logradouro() + ", "
                            + e.bairro() + " - " + e.localidade() + "/" + e.uf()),
                    () -> System.out.println(cep + ": CEP não encontrado"));
        }
    }
}
