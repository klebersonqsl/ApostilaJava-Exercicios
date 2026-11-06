public class TesteProduto {
    public static void main(String[] args) {
        System.out.println("Criados até agora: " + Produto.getTotalCriados());

        Produto a = new Produto("P01", "Caneca", 29.90);
        Produto b = new Produto("P02", "Camiseta", 59.90);
        Produto c = new Produto("P01", "Caneca azul", 31.50);

        System.out.println("Criados até agora: " + Produto.getTotalCriados());

        b.reajustar(10);
        System.out.println(a);                 // chama a.toString()
        System.out.println("Item: " + b);
        System.out.println("a == c ? " + (a == c));
        System.out.println("a.equals(c) ? " + a.equals(c));
        System.out.println("hash iguais? " + (a.hashCode() == c.hashCode()));
    }
}
