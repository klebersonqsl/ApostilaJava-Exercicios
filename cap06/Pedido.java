import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int proximoNumero = 1;

    private final int numero;
    private final String cliente;
    private final List<ItemPedido> itens = new ArrayList<>(); // lista que cresce (cap. 9)

    public Pedido(String cliente) {
        this.numero = proximoNumero++;
        this.cliente = cliente;
    }

    public void adicionar(Produto produto, int quantidade) {
        itens.add(new ItemPedido(produto, quantidade));
    }

    public double total() {
        double soma = 0;
        for (ItemPedido item : itens) {
            soma += item.subtotal();
        }
        return soma;
    }

    public void imprimir() {
        System.out.println("Pedido nº " + numero + " - Cliente: " + cliente);
        for (ItemPedido i : itens) {
            Produto p = i.getProduto();
            System.out.printf("  %-12s %3d x %8.2f = %9.2f%n",
                    p.getDescricao(), i.getQuantidade(), p.getPreco(), i.subtotal());
        }
        System.out.printf("  %-12s %28.2f%n", "TOTAL", total());
    }
}
