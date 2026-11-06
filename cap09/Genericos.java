import java.util.List;

public class Genericos {

    record Par<A, B>(A primeiro, B segundo) {}

    // método genérico: <T> antes do tipo de retorno
    static <T> T ultimo(List<T> lista) {
        return lista.get(lista.size() - 1);
    }

    // T limitado: precisa ser comparável
    static <T extends Comparable<T>> T maior(List<T> lista) {
        T m = lista.get(0);
        for (T item : lista) {
            if (item.compareTo(m) > 0) {
                m = item;
            }
        }
        return m;
    }

    // curinga: aceita List<Integer>, List<Double>, List<Long>...
    static double somar(List<? extends Number> numeros) {
        double s = 0;
        for (Number n : numeros) {
            s += n.doubleValue();
        }
        return s;
    }

    public static void main(String[] args) {
        Caixa<String> c1 = new Caixa<>();
        c1.guardar("documentos");
        String texto = c1.abrir();        // sem cast: o compilador sabe que é String
        System.out.println("Caixa de String: " + texto);

        Caixa<Integer> c2 = new Caixa<>();
        System.out.println("Caixa de Integer vazia? " + c2.vazia());
        // c2.guardar("texto");                  // erro de compilação: tipo errado

        Par<String, Integer> p = new Par<>("idade", 30);
        System.out.println(p.primeiro() + " = " + p.segundo());

        System.out.println("Último: " + ultimo(List.of("a", "b", "c")));
        System.out.println("Maior: " + maior(List.of(12, 99, 37)));
        System.out.println("Maior: " + maior(List.of("pera", "abacaxi", "uva")));
        System.out.println("Soma de inteiros: " + somar(List.of(1, 2, 3)));
        System.out.println("Soma de decimais: " + somar(List.of(1.5, 2.5)));
    }
}
