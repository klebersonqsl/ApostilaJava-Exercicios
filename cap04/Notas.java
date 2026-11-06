public class Notas {
    public static void main(String[] args) {
        // cria um array com espaço para 5 valores double (todos começam com 0.0)
        double[] notas = new double[5];
        notas[0] = 7.5;
        notas[1] = 9.0;
        notas[2] = 6.0;
        notas[3] = 8.5;
        notas[4] = 10.0;

        // ou: declarar e preencher de uma vez
        String[] alunos = {"Ana", "Bruno", "Carla", "Diego", "Elisa"};

        System.out.println("Quantidade de alunos: " + alunos.length);
        String ultimo = alunos[alunos.length - 1];
        System.out.println("Primeiro: " + alunos[0] + ", último: " + ultimo);

        // percorrendo com for e índice
        double soma = 0;
        double maior = notas[0];
        for (int i = 0; i < notas.length; i++) {
            System.out.printf("%-6s %5.1f%n", alunos[i], notas[i]);
            soma += notas[i];
            if (notas[i] > maior) {
                maior = notas[i];
            }
        }
        double media = soma / notas.length;
        System.out.printf("Média da turma: %.2f  |  Maior nota: %.1f%n", media, maior);

        // for-each: percorre todos os elementos, sem índice
        int acimaDe8 = 0;
        for (double n : notas) {
            if (n > 8) {
                acimaDe8++;
            }
        }
        System.out.println("Notas acima de 8: " + acimaDe8);
    }
}
