import java.util.Scanner;

public class Calculadora {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Primeiro número: ");
        double a = Double.parseDouble(teclado.nextLine());
        System.out.print("Operação (+ - * /): ");
        String op = teclado.nextLine();
        System.out.print("Segundo número: ");
        double b = Double.parseDouble(teclado.nextLine());

        String resultado = switch (op) {
            case "+" -> String.valueOf(a + b);
            case "-" -> String.valueOf(a - b);
            case "*" -> String.valueOf(a * b);
            case "/" -> b == 0 ? "impossível dividir por zero" : String.valueOf(a / b);
            default -> "operação inválida";
        };
        System.out.println("Resultado: " + resultado);
    }
}
