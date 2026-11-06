public class TesteFormas {
    public static void main(String[] args) {
        Forma[] formas = {new Circulo(1.5), new Quadrado(3), new Circulo(0.5)};
        for (Forma f : formas) {
            System.out.println(f.descricao());
        }
        // Forma f = new Forma("x");  // erro: Forma é abstrata
    }
}
