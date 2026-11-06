import java.util.Scanner;

public class SolucaoNextLine {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Idade: ");
        // lê a linha inteira e converte para int
        int idade = Integer.parseInt(teclado.nextLine());

        System.out.print("Nome: ");
        String nome = teclado.nextLine();

        System.out.println("Nome lido: [" + nome + "], idade: " + idade);
    }
}
