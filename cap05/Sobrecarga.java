public class Sobrecarga {

    public static void main(String[] args) {
        System.out.println("Quadrado de lado 4: " + area(4));
        System.out.println("Retângulo 3 x 5: " + area(3, 5));
        System.out.printf("Círculo de raio 2: %.2f%n", area(2.0));
        System.out.println("Formatado: " + formatar(1234.5));
        System.out.println("Formatado com moeda: " + formatar(1234.5, "US$"));
    }

    // mesmo nome, parâmetros diferentes: o compilador escolhe pela chamada
    static int area(int lado) {
        return lado * lado;
    }

    static int area(int base, int altura) {
        return base * altura;
    }

    static double area(double raio) {
        return Math.PI * raio * raio;
    }

    static String formatar(double valor) {
        return formatar(valor, "R$");   // reaproveita a outra versão
    }

    static String formatar(double valor, String moeda) {
        return String.format("%s %,.2f", moeda, valor);
    }
}
