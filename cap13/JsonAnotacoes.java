import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class JsonAnotacoes {

    @JsonIgnoreProperties(ignoreUnknown = true)          // ignora campos desconhecidos
    @JsonInclude(JsonInclude.Include.NON_NULL)          // não grava campos nulos
    record Produto(
            @JsonProperty("codigo_barras") String codigo,  // nome diferente no JSON
            String nome,
            BigDecimal preco,
            @JsonFormat(pattern = "dd/MM/yyyy") LocalDate validade,
            String observacao,
            @JsonIgnore String custoInterno) {             // nunca vai para o JSON
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())     // suporte a LocalDate etc.
                .enable(SerializationFeature.INDENT_OUTPUT);

        List<Produto> produtos = List.of(
                new Produto("7891000100103", "Leite integral 1L", new BigDecimal("5.49"),
                        LocalDate.of(2026, 12, 15), null, "3.10"),
                new Produto("7896004000015", "Café 500g", new BigDecimal("18.90"),
                        LocalDate.of(2027, 5, 1), "promoção", "11.20"));

        File arquivo = new File("produtos.json");
        mapper.writeValue(arquivo, produtos);                 // grava no arquivo
        System.out.println("Gravado produtos.json");

        Produto[] lidos = mapper.readValue(arquivo, Produto[].class);   // lê do arquivo
        for (Produto p : lidos) {
            System.out.println(p.nome() + " vence em " + p.validade() + " | custo: "
                    + p.custoInterno());
        }
    }
}
