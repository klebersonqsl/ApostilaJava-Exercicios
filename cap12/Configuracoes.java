import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Configuracoes {
    public static void main(String[] args) throws IOException {
        Properties cfg = new Properties();
        try (Reader r = Files.newBufferedReader(Path.of("config.properties"))) {
            cfg.load(r);
        }
        System.out.println("Aplicação: " + cfg.getProperty("app.nome")
                + " " + cfg.getProperty("app.versao"));
        System.out.println("Banco: " + cfg.getProperty("db.url"));
        // segundo argumento: valor padrão se a chave não existir
        System.out.println("Tema: " + cfg.getProperty("app.tema", "claro"));

        cfg.setProperty("app.tema", "escuro");
        cfg.setProperty("usuario.ultimo", "maria");
        try (Writer w = Files.newBufferedWriter(Path.of("config.properties"))) {
            cfg.store(w, "Atualizado pelo sistema");
        }
        System.out.println("Chaves salvas: " + cfg.stringPropertyNames().size());
    }
}
