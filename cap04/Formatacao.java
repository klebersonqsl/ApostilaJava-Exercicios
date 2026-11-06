public class Formatacao {
    public static void main(String[] args) {
        String nome = "Mariana";
        int pedidos = 3;
        double total = 1234.5;

        // String.format devolve o texto formatado (não imprime)
        String linha = String.format("%s fez %d pedidos, total R$ %,.2f",
                nome, pedidos, total);
        System.out.println(linha);

        // formatted: o mesmo, chamado a partir do texto
        System.out.println("Código: %05d | Desconto: %.1f%%".formatted(42, 12.5));

        // colunas alinhadas
        System.out.println(String.format("|%-10s|%8s|", "Produto", "Preço"));
        System.out.println(String.format("|%-10s|%8.2f|", "Café", 18.9));
        System.out.println(String.format("|%-10s|%8.2f|", "Açúcar", 5.49));

        // text block: texto de várias linhas entre três aspas
        String recibo = """
                ===========================
                  RECIBO
                  Cliente: %s
                  Valor:   R$ %.2f
                ===========================
                """.formatted(nome, total);
        System.out.print(recibo);
    }
}
