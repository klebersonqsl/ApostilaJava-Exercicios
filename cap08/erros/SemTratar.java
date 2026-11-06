import java.io.FileReader;

public class SemTratar {
    public static void main(String[] args) {
        FileReader leitor = new FileReader("dados.txt");
        System.out.println((char) leitor.read());
    }
}
