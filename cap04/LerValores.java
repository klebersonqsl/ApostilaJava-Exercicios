import java.util.Scanner;

public class LerValores {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Quantos produtos? ");
        int n = Integer.parseInt(teclado.nextLine());

        String[] nomes = new String[n];
        double[] precos = new double[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Produto " + (i + 1) + ": ");
            nomes[i] = teclado.nextLine();
            System.out.print("Preço: ");
            precos[i] = Double.parseDouble(teclado.nextLine().replace(',', '.'));
        }

        int maisCaro = 0;
        for (int i = 1; i < n; i++) {
            if (precos[i] > precos[maisCaro]) {
                maisCaro = i;
            }
        }
        System.out.printf("Mais caro: %s (R$ %.2f)%n", nomes[maisCaro], precos[maisCaro]);
    }
}
