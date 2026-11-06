import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LerCsv {

    record Produto(int codigo, String descricao, String categoria, BigDecimal preco,
                   int estoque) {

        static Produto deLinha(String linha) {
            String[] c = linha.split(";");
            return new Produto(Integer.parseInt(c[0]), c[1], c[2],
                    new BigDecimal(c[3].replace(",", ".")),     // "24,90" -> 24.90
                    Integer.parseInt(c[4]));
        }

        BigDecimal valorEmEstoque() {
            return preco.multiply(BigDecimal.valueOf(estoque));
        }
    }

    public static void main(String[] args) throws IOException {
        Path csv = Path.of("produtos.csv");
        List<String> linhas = Files.readAllLines(csv, StandardCharsets.UTF_8);
        List<Produto> produtos = new ArrayList<>();
        for (String l : linhas.subList(1, linhas.size())) {     // pula o cabeçalho
            produtos.add(Produto.deLinha(l));
        }
        System.out.println(produtos.size() + " produtos lidos.");

        Map<String, BigDecimal> porCategoria = new TreeMap<>();
        for (Produto p : produtos) {
            porCategoria.merge(p.categoria(), p.valorEmEstoque(), BigDecimal::add);
            if (p.estoque() == 0) {
                System.out.println("Sem estoque: " + p.descricao());
            }
        }

        // grava o relatório em outro CSV
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Path.of("estoque_por_categoria.csv")))) {
            out.println("categoria;valor_em_estoque");
            porCategoria.forEach((cat, valor) ->
                    out.printf("%s;%s%n", cat, valor.toPlainString().replace(".", ",")));
        }
        System.out.println("Relatório gravado em estoque_por_categoria.csv");
    }
}
