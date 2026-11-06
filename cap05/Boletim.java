import java.util.Scanner;

public class Boletim {

    static final Scanner TECLADO = new Scanner(System.in);

    public static void main(String[] args) {
        String nome = lerTexto("Nome do aluno: ");
        double[] notas = lerNotas(3);
        double media = media(notas);
        System.out.println();
        imprimirBoletim(nome, notas, media);
    }

    static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return TECLADO.nextLine();
    }

    static double lerNota(String mensagem) {
        while (true) {
            double n = Double.parseDouble(lerTexto(mensagem).replace(',', '.'));
            if (n >= 0 && n <= 10) {
                return n;
            }
            System.out.println("  A nota deve estar entre 0 e 10.");
        }
    }

    static double[] lerNotas(int quantidade) {
        double[] notas = new double[quantidade];
        for (int i = 0; i < quantidade; i++) {
            notas[i] = lerNota("Nota " + (i + 1) + ": ");
        }
        return notas;
    }

    static double media(double[] notas) {
        double soma = 0;
        for (double n : notas) {
            soma += n;
        }
        return soma / notas.length;
    }

    static String situacao(double media) {
        if (media >= 7) {
            return "APROVADO";
        } else if (media >= 5) {
            return "RECUPERAÇÃO";
        }
        return "REPROVADO";
    }

    static void imprimirBoletim(String nome, double[] notas, double media) {
        System.out.println("===== BOLETIM =====");
        System.out.println("Aluno: " + nome);
        for (int i = 0; i < notas.length; i++) {
            System.out.printf("  Nota %d: %5.1f%n", i + 1, notas[i]);
        }
        System.out.printf("  Média:  %5.1f%n", media);
        System.out.println("Situação: " + situacao(media));
    }
}
