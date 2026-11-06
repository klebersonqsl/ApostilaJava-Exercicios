public class Validacoes {
    public static void main(String[] args) {
        System.out.println(calcularDesconto(200, 10));
        try {
            System.out.println(calcularDesconto(200, 150));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println(calcularDesconto(-5, 10));   // não tratada: encerra
    }

    static double calcularDesconto(double preco, double percentual) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço negativo: " + preco);
        }
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("Percentual inválido: " + percentual);
        }
        return preco * percentual / 100;
    }
}
