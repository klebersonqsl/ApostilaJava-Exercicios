import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Centraliza a criação de conexões a partir do arquivo db.properties. */
public final class FabricaConexao {

    private static final Properties CFG = new Properties();

    static {
        try (InputStream in = Files.newInputStream(Path.of("db.properties"))) {
            CFG.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível ler db.properties", e);
        }
    }

    private FabricaConexao() {
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(CFG.getProperty("db.url"),
                CFG.getProperty("db.usuario"), CFG.getProperty("db.senha"));
    }
}
