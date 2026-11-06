import java.util.Scanner;

public class Comparando {
    public static void main(String[] args) {
        String a = "Java";
        String b = "Java";
        String c = new String("Java");   // força a criação de outro objeto
        Scanner teclado = new Scanner(System.in);
        System.out.print("Digite Java: ");
        String d = teclado.nextLine();

        System.out.println("a == b ? " + (a == b));          // mesmo objeto (literal)
        System.out.println("a == c ? " + (a == c));          // objetos diferentes!
        System.out.println("a == d ? " + (a == d));          // objetos diferentes!
        System.out.println("a.equals(c) ? " + a.equals(c));  // compara o conteúdo
        System.out.println("a.equals(d) ? " + a.equals(d));
        System.out.println("equalsIgnoreCase ? " + "JAVA".equalsIgnoreCase(a));

        // compareTo: negativo, zero ou positivo (ordem alfabética)
        System.out.println("\"Ana\".compareTo(\"Bruno\") = " + "Ana".compareTo("Bruno"));
        System.out.println("\"Bruno\".compareTo(\"Ana\") = " + "Bruno".compareTo("Ana"));
        System.out.println("\"Ana\".compareTo(\"Ana\") = " + "Ana".compareTo("Ana"));
    }
}
