import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {
        List<Venda> vendas = Dados.vendas();

        // filter -> map -> collect
        List<String> grandes = vendas.stream()
                .filter(v -> v.total() > 3000)          // só vendas acima de 3 mil
                .map(v -> v.vendedor() + ": " + v.produto())
                .toList();
        System.out.println("Vendas acima de R$ 3.000: " + grandes);

        // operações numéricas
        double faturamento = vendas.stream().mapToDouble(Venda::total).sum();
        System.out.printf("Faturamento total: R$ %,.2f%n", faturamento);

        long notebooks = vendas.stream()
                .filter(v -> v.produto().equals("Notebook"))
                .count();
        System.out.println("Vendas de notebook: " + notebooks);

        // sorted + limit
        System.out.println("Top 3 por valor:");
        vendas.stream()
              .sorted((a, b) -> Double.compare(b.total(), a.total()))
              .limit(3)
              .forEach(v -> System.out.printf("  %-6s %-9s %10.2f%n",
                      v.vendedor(), v.produto(), v.total()));

        // distinct + joining
        String produtos = vendas.stream().map(Venda::produto).distinct().sorted()
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Produtos: " + produtos);

        // anyMatch, allMatch, noneMatch
        System.out.println("Alguma venda no Norte? "
                + vendas.stream().anyMatch(v -> v.regiao().equals("Norte")));
        System.out.println("Todas com quantidade > 0? "
                + vendas.stream().allMatch(v -> v.quantidade() > 0));

        // reduce: combina tudo em um valor
        int unidades = vendas.stream().map(Venda::quantidade).reduce(0, Integer::sum);
        System.out.println("Unidades vendidas: " + unidades);

        // streams a partir de outras fontes
        List<Integer> quadrados = IntStream.rangeClosed(1, 6)
                .map(n -> n * n).boxed().toList();
        System.out.println("Quadrados: " + quadrados);
        String letras = Stream.of("b", "a", "c").sorted()
                .collect(Collectors.joining("-"));
        System.out.println(letras);
    }
}
