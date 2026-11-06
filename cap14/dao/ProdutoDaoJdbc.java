import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDaoJdbc implements ProdutoDao {

    private static final String COLUNAS = "id, nome, categoria, preco, estoque, cadastro";

    @Override
    public Produto salvar(Produto p) {
        String sql = "INSERT INTO produto (nome, categoria, preco, estoque, cadastro) "
                + "VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection con = FabricaConexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            preencher(ps, p);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return p.comId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao salvar " + p.nome(), e);
        }
    }

    @Override
    public Optional<Produto> buscarPorId(long id) {
        String sql = "SELECT " + COLUNAS + " FROM produto WHERE id = ?";
        List<Produto> r = consultar(sql, id);
        return r.stream().findFirst();
    }

    @Override
    public List<Produto> listar() {
        return consultar("SELECT " + COLUNAS + " FROM produto ORDER BY nome");
    }

    @Override
    public List<Produto> buscarPorCategoria(String categoria) {
        String sql = "SELECT " + COLUNAS
                + " FROM produto WHERE categoria = ? ORDER BY nome";
        return consultar(sql, categoria);
    }

    @Override
    public boolean atualizar(Produto p) {
        String sql = "UPDATE produto SET nome = ?, categoria = ?, preco = ?, "
                + "estoque = ?, cadastro = ? WHERE id = ?";
        try (Connection con = FabricaConexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            preencher(ps, p);
            ps.setLong(6, p.id());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Erro ao atualizar produto " + p.id(), e);
        }
    }

    @Override
    public boolean excluir(long id) {
        try (Connection con = FabricaConexao.conectar();
             PreparedStatement ps =
                     con.prepareStatement("DELETE FROM produto WHERE id = ?")) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Erro ao excluir produto " + id, e);
        }
    }

    // ---------- auxiliares ----------

    private void preencher(PreparedStatement ps, Produto p) throws SQLException {
        ps.setString(1, p.nome());
        ps.setString(2, p.categoria());
        ps.setBigDecimal(3, p.preco());
        ps.setInt(4, p.estoque());
        ps.setObject(5, p.cadastro());
    }

    private Produto mapear(ResultSet rs) throws SQLException {
        return new Produto(rs.getLong("id"), rs.getString("nome"),
                rs.getString("categoria"), rs.getBigDecimal("preco"),
                rs.getInt("estoque"), rs.getObject("cadastro", LocalDate.class));
    }

    private List<Produto> consultar(String sql, Object... params) {
        try (Connection con = FabricaConexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            List<Produto> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new DaoException("Erro na consulta", e);
        }
    }
}
