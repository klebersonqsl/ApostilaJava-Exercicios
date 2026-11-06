import java.util.Arrays;
import java.util.Comparator;

public class Anonimas {

    interface Saudacao {
        String saudar(String nome);
    }

    public static void main(String[] args) {
        // classe anônima: implementa a interface ali mesmo, sem criar um arquivo
        Saudacao formal = new Saudacao() {
            @Override
            public String saudar(String nome) {
                return "Prezado(a) " + nome + ",";
            }
        };

        // lambda: forma curta quando a interface tem um único método (capítulo 10)
        Saudacao informal = nome -> "Oi, " + nome + "!";

        System.out.println(formal.saudar("Dra. Clara"));
        System.out.println(informal.saudar("Clara"));

        String[] cidades = {"Natal", "Belo Horizonte", "Rio", "Curitiba"};
        Arrays.sort(cidades, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.compare(a.length(), b.length());
            }
        });
        System.out.println(Arrays.toString(cidades));
    }
}
