import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Inserir {
    public static void main(String[] args) throws SQLException {
        // RETURNING id devolve a chave gerada pelo banco (SQLite, PostgreSQL, Firebird)
        String sql = """
                INSERT INTO produto (nome, categoria, preco, estoque, cadastro)
                VALUES (?, ?, ?, ?, ?) RETURNING id""";

        Object[][] dados = {
            {"Café torrado 500g", "Mercearia", "18.90", 40},
            {"Açúcar cristal 1kg", "Mercearia", "4.79", 0},
            {"Detergente 500ml", "Limpeza", "2.99", 120},
            {"Sabão em pó 1kg", "Limpeza", "14.50", 15},
        };

        try (Connection con = DriverManager.getConnection("jdbc:sqlite:loja.db");
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (Object[] d : dados) {
                ps.setString(1, (String) d[0]);          // cada ? recebe um valor
                ps.setString(2, (String) d[1]);
                ps.setBigDecimal(3, new BigDecimal((String) d[2]));
                ps.setInt(4, (Integer) d[3]);
                ps.setObject(5, LocalDate.of(2026, 10, 1));
                try (ResultSet rs = ps.executeQuery()) { // RETURNING devolve linhas
                    rs.next();
                    System.out.println("Inserido id " + rs.getLong("id") + ": " + d[0]);
                }
            }
        }
    }
}
