import java.util.Scanner;

public class Media {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Nome do aluno: ");
        String nome = teclado.nextLine();

        System.out.print("Primeira nota: ");
        double n1 = teclado.nextDouble();

        System.out.print("Segunda nota: ");
        double n2 = teclado.nextDouble();

        double media = (n1 + n2) / 2;
        System.out.printf("%s ficou com média %.1f%n", nome, media);
        System.out.println("Aprovado? " + (media >= 7));

        teclado.close();
    }
}
