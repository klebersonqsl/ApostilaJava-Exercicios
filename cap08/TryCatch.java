import java.util.Scanner;

public class TryCatch {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int idade = -1;

        while (idade < 0) {
            System.out.print("Idade: ");
            String texto = teclado.nextLine();
            try {
                idade = Integer.parseInt(texto);      // pode lançar NumberFormatException
                if (idade < 0) {
                    System.out.println("A idade não pode ser negativa.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\"" + texto + "\" não é um número. Tente de novo.");
            }
        }
        System.out.println("Idade registrada: " + idade);
    }
}
