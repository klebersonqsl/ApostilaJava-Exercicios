import java.text.Normalizer;
import java.util.Scanner;

public class Palindromo {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Frase: ");
        String frase = teclado.nextLine();

        // remove acentos, espaços e pontuação e passa para minúsculas
        String limpa = Normalizer.normalize(frase, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^A-Za-z0-9]", "")
                .toLowerCase();
        String invertida = new StringBuilder(limpa).reverse().toString();

        System.out.println("Normalizada: " + limpa);
        if (limpa.equals(invertida)) {
            System.out.println("É um palíndromo!");
        } else {
            System.out.println("Não é um palíndromo.");
        }
    }
}
