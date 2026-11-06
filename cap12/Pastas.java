import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

public class Pastas {
    public static void main(String[] args) throws IOException {
        Path raiz = Path.of("documentos");

        // conteúdo direto de uma pasta
        try (Stream<Path> itens = Files.list(raiz)) {
            List<Path> nomes = itens.map(Path::getFileName).sorted().toList();
            System.out.println("Em documentos: " + nomes);
        }

        // todos os .txt em todas as subpastas
        List<Path> textos;
        try (Stream<Path> todos = Files.walk(raiz)) {
            textos = todos.filter(p -> p.toString().endsWith(".txt")).sorted().toList();
        }
        System.out.println("Arquivos .txt:");
        for (Path p : textos) {
            System.out.printf("  %-34s %3d bytes%n", p, Files.size(p));
        }

        // procurar uma palavra dentro dos arquivos
        for (Path p : textos) {
            for (String linha : Files.readAllLines(p)) {
                if (linha.contains("contrato")) {
                    System.out.println("'contrato' em " + p.getFileName() + ": " + linha);
                }
            }
        }

        // copiar, mover, apagar
        Path backup = Path.of("backup");
        Files.createDirectories(backup);
        Path origem = Path.of("documentos/2026/notas.txt");
        Path copia = Files.copy(origem, backup.resolve("notas.txt"),
                StandardCopyOption.REPLACE_EXISTING);
        Path movido = Files.move(copia, backup.resolve("notas-2026.txt"),
                StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Copiado e renomeado para " + movido);
        Path foto = Path.of("documentos/2026/foto.jpg");
        Files.delete(foto);
        System.out.println("foto.jpg existe? " + Files.exists(foto));
        System.out.println("Apagar algo que não existe: "
                + Files.deleteIfExists(Path.of("nada.txt")));
    }
}
