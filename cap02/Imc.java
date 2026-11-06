import java.util.Scanner;

public class Imc {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("=== Calculadora de IMC ===");
        System.out.print("Peso (kg): ");
        double peso = teclado.nextDouble();
        System.out.print("Altura (m): ");
        double altura = teclado.nextDouble();

        double imc = peso / (altura * altura);
        boolean normal = imc >= 18.5 && imc < 25;

        System.out.printf("Seu IMC é %.2f%n", imc);
        String situacao = normal ? "dentro da faixa normal" : "fora da faixa normal";
        System.out.println("Peso " + situacao + ".");
    }
}
