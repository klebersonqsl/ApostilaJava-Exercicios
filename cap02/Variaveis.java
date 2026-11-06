public class Variaveis {
    public static void main(String[] args) {
        // declaração e atribuição separadas
        int idade;
        idade = 19;

        // declaração com inicialização
        String nome = "Carlos";
        double altura = 1.78;
        boolean matriculado = true;

        System.out.println(nome + " tem " + idade + " anos e " + altura + " m.");
        System.out.println("Matriculado? " + matriculado);

        // o valor de uma variável pode mudar
        idade = idade + 1;
        System.out.println("No ano que vem terá " + idade + " anos.");

        // var: o compilador deduz o tipo pelo valor inicial
        var cidade = "Recife";      // String
        var nota = 9.5;             // double
        System.out.println(cidade + " - nota " + nota);

        // final: constante, não pode ser alterada depois
        final double PI = 3.14159;
        System.out.println("PI vale " + PI);
    }
}
