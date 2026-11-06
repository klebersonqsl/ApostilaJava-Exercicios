import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Caminhos {
    public static void main(String[] args) throws IOException {
        Path arquivo = Path.of("documentos", "2026", "notas.txt");   // caminho relativo
        System.out.println("Caminho: " + arquivo);
        System.out.println("Nome do arquivo: " + arquivo.getFileName());
        System.out.println("Pasta pai: " + arquivo.getParent());
        System.out.println("Absoluto? " + arquivo.isAbsolute());

        Path pasta = Path.of("documentos");
        Path outro = pasta.resolve("2025").resolve("resumo.txt");   // junta caminhos
        System.out.println("resolve: " + outro);
        Path confuso = Path.of("documentos/2025/../2026/./a.txt");
        System.out.println("normalize: " + confuso.normalize());

        System.out.println("Existe 'documentos'? " + Files.exists(pasta));
        System.out.println("É pasta? " + Files.isDirectory(pasta));
        System.out.println("Existe notas.txt? " + Files.exists(arquivo));

        Path nova = Path.of("saida", "relatorios", "mensal");
        Files.createDirectories(nova);      // cria a pasta e as intermediárias
        System.out.println("Criada: " + nova + " -> " + Files.isDirectory(nova));
    }
}
