import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lote {
    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:lote.db");
             Statement st = con.createStatement()) {
            st.execute("DROP TABLE IF EXISTS leitura");
            st.execute("CREATE TABLE leitura (sensor INTEGER, valor REAL)");

            long inicio = System.nanoTime();
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO leitura (sensor, valor) VALUES (?, ?)")) {
                for (int i = 0; i < 50_000; i++) {
                    ps.setInt(1, i % 10);
                    ps.setDouble(2, 20 + (i % 100) / 10.0);
                    ps.addBatch();                       // acumula
                    if (i % 1000 == 999) {
                        ps.executeBatch();               // envia 1000 de uma vez
                    }
                }
                ps.executeBatch();
            }
            con.commit();
            con.setAutoCommit(true);
            long ms = (System.nanoTime() - inicio) / 1_000_000;

            String sql = "SELECT COUNT(*), AVG(valor) FROM leitura";
            try (ResultSet rs = st.executeQuery(sql)) {
                rs.next();
                System.out.printf("%,d linhas inseridas, média %.2f%n", rs.getInt(1),
                        rs.getDouble(2));
            }
            System.out.println("Tempo abaixo de 5 segundos? " + (ms < 5000));
        }
    }
}
