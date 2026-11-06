import java.util.Arrays;

public class Matriz {
    public static void main(String[] args) {
        String[] alunos = {"Ana", "Bruno", "Carla"};
        // 3 linhas (alunos) x 4 colunas (bimestres)
        double[][] notas = {
            {7.0, 8.5, 6.0, 9.0},
            {5.5, 6.0, 7.5, 6.5},
            {9.5, 10.0, 9.0, 8.5}
        };

        System.out.println("Aluno   B1    B2    B3    B4   Média");
        for (int i = 0; i < notas.length; i++) {           // linhas
            double soma = 0;
            System.out.printf("%-6s", alunos[i]);
            for (int j = 0; j < notas[i].length; j++) {    // colunas
                System.out.printf("%5.1f ", notas[i][j]);
                soma += notas[i][j];
            }
            System.out.printf("%6.2f%n", soma / notas[i].length);
        }

        System.out.println("Nota da Carla no 2º bimestre: " + notas[2][1]);
        System.out.println(Arrays.deepToString(notas));
    }
}
