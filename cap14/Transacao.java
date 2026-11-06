import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transacao {

    static void transferir(Connection con, int de, int para, BigDecimal valor)
            throws SQLException {
        con.setAutoCommit(false);                       // inicia uma transação
        try (PreparedStatement debito = con.prepareStatement(
                     "UPDATE conta SET saldo = saldo - ? WHERE numero = ?");
             PreparedStatement credito = con.prepareStatement(
                     "UPDATE conta SET saldo = saldo + ? WHERE numero = ?")) {

            debito.setBigDecimal(1, valor);
            debito.setInt(2, de);
            debito.executeUpdate();

            credito.setBigDecimal(1, valor);
            credito.setInt(2, para);
            if (credito.executeUpdate() == 0) {         // conta destino não existe
                throw new SQLException("Conta destino " + para + " inexistente");
            }
            con.commit();                               // confirma as duas operações
            System.out.println("Transferência de " + valor + " concluída.");
        } catch (SQLException e) {
            con.rollback();                             // desfaz TUDO
            System.out.println("Falhou (" + e.getMessage() + "): operação desfeita.");
        } finally {
            con.setAutoCommit(true);
        }
    }

    static void saldos(Connection con) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT numero, saldo FROM conta")) {
            while (rs.next()) {
                System.out.printf("  conta %d: R$ %.2f%n", rs.getInt(1), rs.getDouble(2));
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:banco.db");
             Statement st = con.createStatement()) {
            st.execute("DROP TABLE IF EXISTS conta");
            st.execute("CREATE TABLE conta (numero INTEGER PRIMARY KEY, "
                    + "saldo DECIMAL(12,2))");
            st.execute("INSERT INTO conta VALUES (1, 1000), (2, 500)");
            saldos(con);
            transferir(con, 1, 2, new BigDecimal("300.00"));
            saldos(con);
            transferir(con, 1, 99, new BigDecimal("200.00"));
            saldos(con);
        }
    }
}
