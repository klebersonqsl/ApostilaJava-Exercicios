import java.io.FileReader;
import java.io.IOException;

public class Verificadas {
    public static void main(String[] args) {
        try {
            int c1 = primeiroCaractere("dados.txt");
            System.out.println("Primeiro caractere: " + (char) c1);
            int c2 = primeiroCaractere("naoexiste.txt");
            System.out.println("Primeiro caractere: " + (char) c2);
        } catch (IOException e) {
            System.out.println("Não foi possível ler: " + e.getMessage());
        }
    }

    // throws: avisa que este método pode lançar IOException (exceção verificada)
    static int primeiroCaractere(String arquivo) throws IOException {
        FileReader leitor = new FileReader(arquivo);
        int c = leitor.read();
        leitor.close();
        return c;
    }
}
