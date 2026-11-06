import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AtualizarExcluir {
    public static void main(String[] args) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:loja.db")) {

            String reajuste =
                    "UPDATE produto SET preco = ROUND(preco * ?, 2) WHERE categoria = ?";
            try (PreparedStatement ps = con.prepareStatement(reajuste)) {
                ps.setDouble(1, 1.10);
                ps.setString(2, "Limpeza");
                int linhas = ps.executeUpdate();         // devolve quantas linhas mudaram
                System.out.println("Produtos reajustados: " + linhas);
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM produto WHERE estoque = 0")) {
                int excluidos = ps.executeUpdate();
                System.out.println("Produtos sem estoque excluídos: " + excluidos);
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE produto SET estoque = estoque - ? WHERE id = ?")) {
                ps.setInt(1, 5);
                ps.setLong(2, 999);
                int linhas = ps.executeUpdate();
                System.out.println(linhas == 0 ? "Nenhum produto com id 999." : "Ok.");
            }
        }
    }
}
