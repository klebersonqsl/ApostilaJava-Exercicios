import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PrimeiraConexao {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:loja.db";      // o arquivo é criado se não existir

        try (Connection con = DriverManager.getConnection(url)) {
            DatabaseMetaData meta = con.getMetaData();
            System.out.println("Conectado a " + meta.getDatabaseProductName()
                    + " " + meta.getDatabaseProductVersion());
            System.out.println("Driver: " + meta.getDriverName()
                    + " " + meta.getDriverVersion());

            String ddl = """
                    CREATE TABLE IF NOT EXISTS produto (
                        id        INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome      VARCHAR(80) NOT NULL,
                        categoria VARCHAR(30) NOT NULL,
                        preco     DECIMAL(10,2) NOT NULL,
                        estoque   INTEGER NOT NULL DEFAULT 0,
                        cadastro  DATE NOT NULL
                    )""";
            try (Statement st = con.createStatement()) {
                st.execute(ddl);
            }
            System.out.println("Tabela produto pronta.");
        } catch (SQLException e) {
            System.out.println("Erro de banco: " + e.getMessage());
        }
    }
}
