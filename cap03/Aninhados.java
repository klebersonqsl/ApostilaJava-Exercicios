public class Aninhados {
    public static void main(String[] args) {
        // um for dentro de outro: linhas e colunas
        for (int linha = 1; linha <= 4; linha++) {
            for (int col = 1; col <= linha; col++) {
                System.out.print("* ");
            }
            System.out.println();
        }

        // tabela de multiplicação 5 x 5
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                System.out.printf("%4d", i * j);
            }
            System.out.println();
        }
    }
}
