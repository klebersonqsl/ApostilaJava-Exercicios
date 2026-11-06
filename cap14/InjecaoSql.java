import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InjecaoSql {

    // ERRADO: monta o SQL concatenando o que o usuário digitou
    static boolean loginInseguro(Connection con, String login, String senha)
            throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE login = '" + login
                + "' AND senha = '" + senha + "'";
        System.out.println("  SQL executado: " + sql);
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    // CERTO: parâmetros (?) são enviados separados do comando
    static boolean loginSeguro(Connection con, String login, String senha)
            throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE login = ? AND senha = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:seguranca.db");
             Statement st = con.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS usuario (login TEXT, senha TEXT)");
            st.execute("DELETE FROM usuario");
            st.execute("INSERT INTO usuario VALUES ('admin', 'S3nh@Forte')");

            String login = "admin";
            String senha = "qualquer' OR '1'='1";        // digitado por um atacante
            System.out.println("Tentativa com a senha: " + senha);
            System.out.println("Login inseguro: " + loginInseguro(con, login, senha));
            System.out.println("Login seguro:   " + loginSeguro(con, login, senha));
            System.out.println("Login seguro com a senha certa: "
                    + loginSeguro(con, "admin", "S3nh@Forte"));
        }
    }
}
