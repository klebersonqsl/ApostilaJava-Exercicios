import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class EscreverLer {
    public static void main(String[] args) throws IOException {
        Path arq = Path.of("compras.txt");

        // escreve o texto inteiro (cria ou substitui o arquivo)
        Files.writeString(arq, "Lista de compras\n", StandardCharsets.UTF_8);

        // escreve várias linhas no fim do arquivo (APPEND)
        List<String> itens = List.of("arroz", "feijão", "café", "açúcar");
        Files.write(arq, itens, StandardCharsets.UTF_8, StandardOpenOption.APPEND);

        // lê o arquivo inteiro como um texto
        String conteudo = Files.readString(arq, StandardCharsets.UTF_8);
        System.out.println("--- readString ---");
        System.out.print(conteudo);

        // lê como lista de linhas
        List<String> linhas = Files.readAllLines(arq, StandardCharsets.UTF_8);
        System.out.println("--- readAllLines ---");
        System.out.println("O arquivo tem " + linhas.size() + " linhas");
        for (int i = 1; i < linhas.size(); i++) {
            System.out.println(i + ". " + linhas.get(i));
        }
        System.out.println("Tamanho em bytes: " + Files.size(arq));
    }
}
