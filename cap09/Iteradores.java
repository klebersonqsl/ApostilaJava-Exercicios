import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Iteradores {
    public static void main(String[] args) {
        List<String> frutas =
                new ArrayList<>(List.of("maçã", "uva", "kiwi", "manga", "pera"));

        // remover durante um for-each causa ConcurrentModificationException
        try {
            for (String f : frutas) {
                if (f.startsWith("m")) {
                    frutas.remove(f);
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Erro: " + e.getClass().getSimpleName());
        }

        // forma correta 1: Iterator.remove()
        Iterator<String> it = frutas.iterator();
        while (it.hasNext()) {
            if (it.next().length() == 4) {
                it.remove();
            }
        }
        System.out.println("Sem as de 4 letras: " + frutas);

        // forma correta 2: removeIf
        frutas.removeIf(f -> f.startsWith("m"));
        System.out.println("Sem as que começam com m: " + frutas);

        // utilitários da classe Collections
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        Collections.shuffle(nums, new Random(42));     // embaralha (semente fixa)
        System.out.println("Embaralhada: " + nums);
        Collections.reverse(nums);
        System.out.println("Invertida: " + nums);
        System.out.println("Frequência do 3: " + Collections.frequency(nums, 3));
        List<Integer> somenteLeitura = Collections.unmodifiableList(nums);
        System.out.println("Somente leitura: " + somenteLeitura.size() + " itens");
    }
}
