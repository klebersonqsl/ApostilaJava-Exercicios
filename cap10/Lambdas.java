import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Lambdas {
    public static void main(String[] args) {
        // Predicate<T>: recebe T e devolve boolean
        Predicate<String> vazio = s -> s.isBlank();
        Predicate<Integer> par = n -> n % 2 == 0;
        System.out.println("'  ' é vazio? " + vazio.test("  "));
        System.out.println("10 é par? " + par.test(10));

        // Function<T, R>: recebe T e devolve R
        Function<String, Integer> tamanho = s -> s.length();
        System.out.println("Tamanho de 'lambda': " + tamanho.apply("lambda"));

        // BiFunction<T, U, R>: dois parâmetros
        BiFunction<Double, Double, Double> potencia = (base, exp) -> Math.pow(base, exp);
        System.out.println("2^8 = " + potencia.apply(2.0, 8.0));

        // UnaryOperator<T>: recebe e devolve o mesmo tipo
        UnaryOperator<String> grito = s -> s.toUpperCase() + "!";
        System.out.println(grito.apply("atenção"));

        // Consumer<T>: recebe e não devolve nada
        Consumer<String> imprimir = s -> System.out.println("> " + s);
        imprimir.accept("mensagem consumida");

        // Supplier<T>: não recebe nada e devolve T
        Supplier<List<String>> novaLista = () -> new ArrayList<>();
        System.out.println("Lista criada: " + novaLista.get());

        // lambda com bloco: chaves e return
        Function<Double, String> conceito = nota -> {
            if (nota >= 9) return "A";
            if (nota >= 7) return "B";
            return "C";
        };
        System.out.println("Conceito de 7,5: " + conceito.apply(7.5));

        // combinando
        Predicate<Integer> positivo = n -> n > 0;
        Predicate<Integer> parPositivo = par.and(positivo);
        System.out.println("-4 é par e positivo? " + parPositivo.test(-4));
        Function<String, String> limpar = String::strip;
        Function<String, String> limparEGritar = limpar.andThen(grito);
        System.out.println(limparEGritar.apply("   olá   "));
    }
}
