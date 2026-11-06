import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonArvore {
    public static void main(String[] args) throws Exception {
        String json = """
                {
                  "loja": "Centro",
                  "vendas": [
                    {"produto": "Notebook", "valor": 3800.0, "tags": ["tech", "promo"]},
                    {"produto": "Cabo HDMI", "valor": 39.9, "tags": []}
                  ],
                  "gerente": {"nome": "Sônia", "ramal": 214}
                }
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode raiz = mapper.readTree(json);         // sem classe: navega pela árvore

        System.out.println("Loja: " + raiz.get("loja").asText());
        System.out.println("Gerente: " + raiz.path("gerente").path("nome").asText());
        System.out.println("Ramal: " + raiz.at("/gerente/ramal").asInt()); // JSON Pointer
        System.out.println("Campo inexistente: '" + raiz.path("cnpj").asText("?") + "'");

        double soma = 0;
        for (JsonNode venda : raiz.get("vendas")) {
            soma += venda.get("valor").asDouble();
            System.out.println("  " + venda.get("produto").asText()
                    + " (" + venda.get("tags").size() + " tags)");
        }
        System.out.println("Total: " + soma);

        // montando um JSON do zero
        ObjectNode resumo = mapper.createObjectNode();
        resumo.put("loja", raiz.get("loja").asText());
        resumo.put("total", soma);
        ArrayNode produtos = resumo.putArray("produtos");
        raiz.get("vendas").forEach(v -> produtos.add(v.get("produto").asText()));
        System.out.println(mapper.writeValueAsString(resumo));
    }
}
