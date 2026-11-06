public class Metodos {

    public static void main(String[] args) {
        exibirTitulo("Calculadora de exemplo");      // chamada sem retorno

        int total = somar(8, 5);                     // o retorno é guardado
        System.out.println("8 + 5 = " + total);
        System.out.println("10 + 32 = " + somar(10, 32));  // ou usado direto

        double m = media(7.0, 9.5);
        System.out.println("Média: " + m);

        for (int n = 1; n <= 4; n++) {
            System.out.println(n + (ehPar(n) ? " é par" : " é ímpar"));
        }
        exibirTitulo("Fim");
    }

    // void: não devolve nada
    static void exibirTitulo(String texto) {
        String linha = "-".repeat(texto.length() + 4);
        System.out.println(linha);
        System.out.println("| " + texto + " |");
        System.out.println(linha);
    }

    // recebe dois int e devolve um int
    static int somar(int a, int b) {
        return a + b;
    }

    static double media(double x, double y) {
        return (x + y) / 2;
    }

    static boolean ehPar(int numero) {
        return numero % 2 == 0;
    }
}
