import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Consultar {
    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:loja.db")) {

            // 1) todas as linhas
            System.out.println("ID  NOME                 CATEGORIA     PREÇO  ESTOQUE");
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM produto ORDER BY nome")) {
                while (rs.next()) {                       // avança para a próxima linha
                    long id = rs.getLong("id");
                    String nome = rs.getString("nome");
                    String categoria = rs.getString("categoria");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    int estoque = rs.getInt("estoque");
                    LocalDate cadastro = rs.getObject("cadastro", LocalDate.class);
                    System.out.printf("%-3d %-20s %-10s %8.2f %8d%n",
                            id, nome, categoria, preco, estoque);
                }
            }

            // 2) com parâmetro
            String sql = "SELECT nome, preco FROM produto WHERE preco <= ? ORDER BY 2";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setBigDecimal(1, new BigDecimal("5.00"));
                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("Até R$ 5,00:");
                    while (rs.next()) {
                        // colunas também podem ser lidas pela posição (1, 2...)
                        System.out.printf("  %s (R$ %.2f)%n",
                                rs.getString(1), rs.getDouble(2));
                    }
                }
            }

            // 3) agregação: uma linha de resultado
            String resumo = """
                    SELECT categoria, COUNT(*) AS qtd, SUM(preco * estoque) AS valor
                    FROM produto GROUP BY categoria ORDER BY categoria""";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(resumo)) {
                while (rs.next()) {
                    System.out.printf("%s: %d produto(s), R$ %.2f em estoque%n",
                            rs.getString("categoria"), rs.getInt("qtd"),
                            rs.getDouble("valor"));
                }
            }
        }
    }
}
