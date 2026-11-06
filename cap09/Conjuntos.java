import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Conjuntos {
    public static void main(String[] args) {
        List<String> inscricoes =
                List.of("Rio", "Recife", "Belém", "Rio", "Natal", "Recife", "Belém");

        Set<String> hash = new HashSet<>(inscricoes);         // sem ordem garantida
        Set<String> linked = new LinkedHashSet<>(inscricoes); // ordem de inserção
        Set<String> tree = new TreeSet<>(inscricoes);         // ordem alfabética

        System.out.println("Lista original: " + inscricoes.size() + " itens");
        System.out.println("HashSet:       " + hash);
        System.out.println("LinkedHashSet: " + linked);
        System.out.println("TreeSet:       " + tree);

        boolean adicionou = linked.add("Rio");
        System.out.println("Adicionou 'Rio' de novo? " + adicionou);

        // operações de conjuntos
        Set<String> turmaA = new TreeSet<>(Set.of("Ana", "Bia", "Caio", "Davi"));
        Set<String> turmaB = new TreeSet<>(Set.of("Caio", "Davi", "Edu"));

        Set<String> uniao = new TreeSet<>(turmaA);
        uniao.addAll(turmaB);
        Set<String> intersecao = new TreeSet<>(turmaA);
        intersecao.retainAll(turmaB);
        Set<String> diferenca = new TreeSet<>(turmaA);
        diferenca.removeAll(turmaB);

        System.out.println("União: " + uniao);
        System.out.println("Nas duas turmas: " + intersecao);
        System.out.println("Só na turma A: " + diferenca);
    }
}
