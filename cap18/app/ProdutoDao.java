import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Modelo: acesso ao banco SQLite. */
public class ProdutoDao {

    private final String url;

    public ProdutoDao(String arquivo) {
        this.url = "jdbc:sqlite:" + arquivo;
        try (Connection con = conectar(); Statement st = con.createStatement()) {
            st.execute("""
                    CREATE TABLE IF NOT EXISTS produto (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome VARCHAR(80) NOT NULL,
                        categoria VARCHAR(30) NOT NULL,
                        preco DECIMAL(10,2) NOT NULL,
                        estoque INTEGER NOT NULL,
                        foto BLOB)""");
        } catch (SQLException e) {
            throw new IllegalStateException("Não foi possível preparar o banco", e);
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public List<Produto> listar() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        try (Connection con = conectar(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM produto ORDER BY nome")) {
            while (rs.next()) {
                lista.add(new Produto(rs.getLong("id"), rs.getString("nome"),
                        rs.getString("categoria"), rs.getBigDecimal("preco"),
                        rs.getInt("estoque"), rs.getBytes("foto")));
            }
        }
        return lista;
    }

    public void salvar(Produto p) throws SQLException {
        String sql = p.id() == null
                ? "INSERT INTO produto (nome, categoria, preco, estoque, foto) "
                  + "VALUES (?, ?, ?, ?, ?)"
                : "UPDATE produto SET nome = ?, categoria = ?, preco = ?, estoque = ?, "
                  + "foto = ? WHERE id = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.nome());
            ps.setString(2, p.categoria());
            ps.setBigDecimal(3, p.preco());
            ps.setInt(4, p.estoque());
            ps.setBytes(5, p.foto());
            if (p.id() != null) {
                ps.setLong(6, p.id());
            }
            ps.executeUpdate();
        }
    }

    public void excluir(long id) throws SQLException {
        try (Connection con = conectar();
             PreparedStatement ps =
                     con.prepareStatement("DELETE FROM produto WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public boolean vazio() throws SQLException {
        try (Connection con = conectar(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM produto")) {
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    public static BigDecimal preco(String texto) {
        return new BigDecimal(texto.strip().replace(".", "").replace(',', '.'));
    }
}
