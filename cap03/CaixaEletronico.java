import java.util.Scanner;

public class CaixaEletronico {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        double saldo = 1000.0;
        int opcao;

        System.out.println("=== Banco Java ===");
        do {
            System.out.println();
            System.out.println("1) Saldo  2) Depósito  3) Saque  0) Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(teclado.nextLine());

            switch (opcao) {
                case 1 -> System.out.printf("Saldo atual: R$ %.2f%n", saldo);
                case 2 -> {
                    System.out.print("Valor do depósito: ");
                    double valor = Double.parseDouble(teclado.nextLine());
                    if (valor <= 0) {
                        System.out.println("Valor inválido.");
                    } else {
                        saldo += valor;
                        System.out.println("Depósito realizado.");
                    }
                }
                case 3 -> {
                    System.out.print("Valor do saque: ");
                    double valor = Double.parseDouble(teclado.nextLine());
                    if (valor > saldo) {
                        System.out.println("Saldo insuficiente.");
                    } else if (valor % 10 != 0) {
                        System.out.println("Só notas de 10, 20, 50 e 100.");
                    } else {
                        saldo -= valor;
                        System.out.println("Retire o dinheiro.");
                    }
                }
                case 0 -> System.out.println("Obrigado por usar o Banco Java.");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
