import java.util.Scanner;

public class Senha {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        final String SENHA_CORRETA = "java2026";
        int tentativas = 0;
        boolean acertou = false;

        // while: repete enquanto a condição for verdadeira
        while (!acertou && tentativas < 3) {
            System.out.print("Senha: ");
            String digitada = teclado.nextLine();
            tentativas++;
            if (digitada.equals(SENHA_CORRETA)) {
                acertou = true;
            } else {
                System.out.println("Senha incorreta (" + tentativas + " de 3).");
            }
        }
        System.out.println(acertou ? "Acesso liberado." : "Conta bloqueada.");
    }
}
