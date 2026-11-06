public class Argumentos {
    public static void main(String[] args) {
        System.out.println("Você passou " + args.length + " argumento(s).");
        if (args.length > 0) {
            System.out.println("O primeiro foi: " + args[0]);
        }
    }
}
