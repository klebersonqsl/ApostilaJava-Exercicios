public class Operadores {
    public static void main(String[] args) {
        int a = 17, b = 5;
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b) + "   (divisão inteira)");
        System.out.println("a % b = " + (a % b) + "   (resto da divisão)");
        System.out.println("17.0 / 5 = " + (17.0 / 5) + "   (divisão decimal)");

        // precedência: * / % antes de + -
        System.out.println("2 + 3 * 4 = " + (2 + 3 * 4));
        System.out.println("(2 + 3) * 4 = " + ((2 + 3) * 4));

        // atribuição composta
        int pontos = 10;
        pontos += 5;   // pontos = pontos + 5
        pontos *= 2;   // pontos = pontos * 2
        pontos -= 4;   // pontos = pontos - 4
        System.out.println("pontos = " + pontos);

        // incremento e decremento
        int x = 5;
        int y = x++;   // pós: usa o valor e depois incrementa
        System.out.println("x = " + x + ", y = " + y);
        int z = ++x;   // pré: incrementa e depois usa
        System.out.println("x = " + x + ", z = " + z);

        // cuidado: + com String concatena
        System.out.println("Total: " + 2 + 3);
        System.out.println("Total: " + (2 + 3));
    }
}
