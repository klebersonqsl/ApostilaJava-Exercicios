import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Optional;

/**
 * API Busca CEP oficial dos Correios (exige contrato e credenciais do CWS).
 * 1) POST /token/v1/autentica/cartaopostagem  -> token (JWT) com validade
 * 2) GET  /cep/v2/enderecos/{cep}  com o cabeçalho Authorization: Bearer token
 */
public class CepCorreios implements BuscaCep {

    private final String base;              // https://api.correios.com.br
    private final String usuario;           // usuário do Meu Correios
    private final String codigoAcesso;      // código de acesso gerado no CWS
    private final String cartaoPostagem;    // número do cartão de postagem do contrato
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper json = new ObjectMapper();

    private String token;
    private Instant expiraEm = Instant.EPOCH;

    public CepCorreios(String base, String usuario, String codigoAcesso, String cartao) {
        this.base = base;
        this.usuario = usuario;
        this.codigoAcesso = codigoAcesso;
        this.cartaoPostagem = cartao;
    }

    /** Reaproveita o token até perto de expirar (um por consulta gera erro 429). */
    private synchronized String token() throws Exception {
        if (token != null && Instant.now().isBefore(expiraEm.minusSeconds(60))) {
            return token;
        }
        String basic = Base64.getEncoder().encodeToString(
                (usuario + ":" + codigoAcesso).getBytes(StandardCharsets.UTF_8));
        HttpRequest req = HttpRequest.newBuilder(
                        URI.create(base + "/token/v1/autentica/cartaopostagem"))
                .header("Authorization", "Basic " + basic)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"numero\": \"" + cartaoPostagem + "\"}"))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 201 && resp.statusCode() != 200) {
            throw new IllegalStateException("Falha ao obter o token: HTTP "
                    + resp.statusCode());
        }
        JsonNode corpo = json.readTree(resp.body());
        token = corpo.get("token").asText();
        expiraEm = OffsetDateTime.parse(corpo.get("expiraEm").asText()).toInstant();
        System.out.println("  (novo token, válido até "
                + corpo.get("expiraEm").asText() + ")");
        return token;
    }

    @Override
    public Optional<Endereco> buscar(String cep) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(
                        URI.create(base + "/cep/v2/enderecos/"
                                + BuscaCep.normalizar(cep)))
                .header("Authorization", "Bearer " + token())
                .header("Accept", "application/json")
                .GET().build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() == 404 || resp.statusCode() == 400) {
            return Optional.empty();                // CEP inexistente ou inválido
        }
        if (resp.statusCode() != 200) {
            throw new IllegalStateException("Correios: HTTP " + resp.statusCode());
        }
        JsonNode e = json.readTree(resp.body());
        return Optional.of(new Endereco(e.path("cep").asText(),
                e.path("logradouro").asText(), e.path("bairro").asText(),
                e.path("localidade").asText(), e.path("uf").asText()));
    }
}
