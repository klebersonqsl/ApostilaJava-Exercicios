public class Tabuada {
    public static void main(String[] args) {
        int numero = 7;

        // for: inicialização; condição; atualização
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%d x %2d = %2d%n", numero, i, numero * i);
        }

        // contagem regressiva de 2 em 2
        for (int i = 10; i >= 0; i -= 2) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
