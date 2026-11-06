import java.util.Scanner;

public class ProblemaNextLine {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Idade: ");
        int idade = teclado.nextInt();      // lê o número, mas deixa o Enter no buffer

        System.out.print("Nome: ");
        String nome = teclado.nextLine();   // lê o Enter que sobrou: vem vazio!

        System.out.println("Nome lido: [" + nome + "], idade: " + idade);
    }
}
