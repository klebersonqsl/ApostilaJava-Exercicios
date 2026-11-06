import java.util.Scanner;

public class Aprovacao {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Média final: ");
        double media = Double.parseDouble(teclado.nextLine().replace(',', '.'));

        if (media >= 7) {
            System.out.println("Aprovado!");
        } else if (media >= 5) {
            System.out.println("Em recuperação.");
        } else {
            System.out.println("Reprovado.");
        }
        System.out.println("Fim da análise.");
    }
}
