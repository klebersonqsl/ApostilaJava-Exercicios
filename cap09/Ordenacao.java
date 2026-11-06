import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ordenacao {
    public static void main(String[] args) {
        List<Aluno> alunos = new ArrayList<>(List.of(
                new Aluno("Rafa", "Direito", 8.2),
                new Aluno("Bia", "Medicina", 9.1),
                new Aluno("Lucas", "Direito", 7.5),
                new Aluno("Ana", "Medicina", 8.2),
                new Aluno("Caio", "Física", 6.9)));

        Collections.sort(alunos);                          // usa compareTo (por nome)
        imprimir("Por nome (Comparable)", alunos);

        alunos.sort(Comparator.comparing(Aluno::getMedia).reversed());
        imprimir("Por média, decrescente", alunos);

        alunos.sort(Comparator.comparing(Aluno::getCurso)
                .thenComparing(Aluno::getMedia, Comparator.reverseOrder()));
        imprimir("Por curso e depois média", alunos);

        Aluno melhor = Collections.max(alunos, Comparator.comparing(Aluno::getMedia));
        System.out.println("Maior média: " + melhor.getNome());
    }

    static void imprimir(String titulo, List<Aluno> lista) {
        System.out.println("== " + titulo);
        for (Aluno a : lista) {
            System.out.println("   " + a);
        }
    }
}
