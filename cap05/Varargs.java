public class Varargs {

    public static void main(String[] args) {
        System.out.println(somar());
        System.out.println(somar(5));
        System.out.println(somar(1, 2, 3, 4, 5));
        System.out.println(somar(new int[]{10, 20}));   // também aceita um array
        relatorio("Notas", 7.5, 8.0, 9.5);
    }

    // int... : quantidade variável de argumentos, recebidos como um array
    static int somar(int... numeros) {
        int total = 0;
        for (int n : numeros) {
            total += n;
        }
        return total;
    }

    // o parâmetro varargs precisa ser o último
    static void relatorio(String titulo, double... valores) {
        System.out.print(titulo + " (" + valores.length + "): ");
        for (double v : valores) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}
