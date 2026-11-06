import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Agrupamentos {
    public static void main(String[] args) {
        List<Venda> vendas = Dados.vendas();

        // groupingBy + summingDouble: total por região
        Map<String, Double> porRegiao = vendas.stream().collect(
                Collectors.groupingBy(Venda::regiao, TreeMap::new,
                        Collectors.summingDouble(Venda::total)));
        System.out.println("Total por região:");
        porRegiao.forEach((r, t) -> System.out.printf("  %-9s R$ %10.2f%n", r, t));

        // counting: quantas vendas por vendedor
        Map<String, Long> qtdPorVendedor = vendas.stream().collect(
                Collectors.groupingBy(Venda::vendedor, TreeMap::new,
                        Collectors.counting()));
        System.out.println("Vendas por vendedor: " + qtdPorVendedor);

        // agrupando por mês da data
        Map<Integer, List<String>> porMes = vendas.stream().collect(
                Collectors.groupingBy(v -> v.data().getMonthValue(), TreeMap::new,
                        Collectors.mapping(Venda::produto, Collectors.toList())));
        System.out.println("Produtos por mês: " + porMes);

        // partitioningBy: divide em dois grupos (true/false)
        Map<Boolean, Long> particao = vendas.stream().collect(
                Collectors.partitioningBy(v -> v.total() >= 1000, Collectors.counting()));
        System.out.println("Acima de mil: " + particao.get(true)
                + " | abaixo: " + particao.get(false));

        // estatísticas de uma vez
        var stats = vendas.stream().mapToDouble(Venda::total).summaryStatistics();
        System.out.printf("Mín %.2f | Máx %.2f | Média %.2f%n",
                stats.getMin(), stats.getMax(), stats.getAverage());

        // toMap: produto -> maior preço unitário
        Map<String, Double> maiorPreco = vendas.stream().collect(Collectors.toMap(
                Venda::produto, Venda::valorUnitario, Math::max, TreeMap::new));
        System.out.println("Maior preço por produto: " + maiorPreco);
    }
}
