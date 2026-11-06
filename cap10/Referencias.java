import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Referencias {

    static boolean ehVogal(char c) {
        return "aeiou".indexOf(Character.toLowerCase(c)) >= 0;
    }

    public static void main(String[] args) {
        List<String> nomes = new ArrayList<>(List.of("Olga", "bruno", "Ivo", "carla"));

        nomes.forEach(n -> System.out.println(n));     // lambda
        nomes.forEach(System.out::println);        // referência de método equivalente

        // os quatro tipos de referência de método
        // 1) método estático
        Function<String, Integer> conversor = Integer::parseInt;
        // 2) método de instância de um objeto qualquer (o parâmetro)
        Function<String, String> maiusc = String::toUpperCase;
        // 3) método de instância de um objeto específico
        String prefixo = ">> ";
        Function<String, String> concatenar = prefixo::concat;
        // 4) construtor
        Supplier<ArrayList<String>> fabrica = ArrayList::new;

        System.out.println(conversor.apply("123") + 1);
        System.out.println(maiusc.apply("ref"));
        System.out.println(concatenar.apply("texto"));
        System.out.println(fabrica.get().isEmpty());

        nomes.removeIf(n -> ehVogal(n.charAt(0)));
        nomes.sort(String::compareToIgnoreCase);
        System.out.println("Sem os que começam com vogal: " + nomes);
    }
}
