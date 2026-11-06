import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;

public class JsonBasico {

    record Endereco(String cidade, String uf) {}

    record Cliente(int id, String nome, String email, boolean ativo, Endereco endereco,
                   List<String> telefones) {}

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // objeto Java -> JSON (serialização)
        Cliente c = new Cliente(7, "Renata Alves", "renata@email.com", true,
                new Endereco("Fortaleza", "CE"),
                List.of("(85) 99999-1111", "(85) 3222-0000"));
        String json = mapper.writeValueAsString(c);
        System.out.println("Compacto:");
        System.out.println(json);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("Formatado:");
        System.out.println(mapper.writeValueAsString(c));

        // JSON -> objeto Java (desserialização)
        String recebido = """
                {"id": 8, "nome": "Tiago Lima", "email": "tiago@email.com",
                 "ativo": false, "endereco": {"cidade": "Goiânia", "uf": "GO"},
                 "telefones": []}
                """;
        Cliente lido = mapper.readValue(recebido, Cliente.class);
        System.out.println("Lido: " + lido.nome() + " de " + lido.endereco().cidade());

        // listas: TypeReference informa o tipo dos elementos
        String array = """
                [{"cidade": "Natal", "uf": "RN"}, {"cidade": "Belém", "uf": "PA"}]""";
        List<Endereco> enderecos = mapper.readValue(array, new TypeReference<>() {});
        System.out.println("Endereços: " + enderecos);
    }
}
