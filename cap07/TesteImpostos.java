import java.util.List;

public class TesteImpostos {
    public static void main(String[] args) {
        List<Tributavel> itens = List.of(
                new Mercadoria("Notebook", 4200),
                new Servico("Instalação", 3, 120),
                new Mercadoria("Mouse", 80));

        double totalImpostos = 0;
        for (Tributavel t : itens) {
            String nome = ((Descritivel) t).descricao();
            System.out.printf("%-24s base %8.2f  imposto %7.2f%n",
                    nome, t.valorBase(), t.imposto());
            totalImpostos += t.imposto();
        }
        System.out.printf("Total de impostos: %.2f%n", totalImpostos);
    }
}
