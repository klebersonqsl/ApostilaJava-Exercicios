public class Loja {
    public static void main(String[] args) {
        Produto caderno = new Produto("C10", "Caderno", 24.90);
        Produto caneta = new Produto("C20", "Caneta", 3.50);
        Produto mochila = new Produto("C30", "Mochila", 189.00);

        Pedido p1 = new Pedido("Joana");
        p1.adicionar(caderno, 3);
        p1.adicionar(caneta, 10);
        p1.imprimir();

        Pedido p2 = new Pedido("Ricardo");
        p2.adicionar(mochila, 1);
        p2.adicionar(caderno, 1);
        p2.imprimir();
    }
}
