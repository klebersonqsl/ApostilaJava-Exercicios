import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcao;

        // do-while: executa pelo menos uma vez e testa no final
        do {
            System.out.println("\n1 - Saudação   2 - Hora do café   0 - Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(teclado.nextLine());

            switch (opcao) {
                case 1 -> System.out.println("Olá! Bons estudos.");
                case 2 -> System.out.println("Pausa de 15 minutos.");
                case 0 -> System.out.println("Até logo!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
