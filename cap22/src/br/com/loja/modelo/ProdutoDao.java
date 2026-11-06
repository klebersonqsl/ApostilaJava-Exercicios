package br.com.loja.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDao {

    private static final String COLUNAS =
            "id, nome, categoria, preco, estoque, foto IS NOT NULL AS tem_foto";

    public List<Produto> buscar(String trecho) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM produto "
                + "WHERE nome LIKE ? OR categoria LIKE ? ORDER BY nome";
        try (Connection con = Banco.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String filtro = "%" + (trecho == null ? "" : trecho.strip()) + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            List<Produto> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        }
    }

    public Optional<Produto> porId(long id) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM produto WHERE id = ?";
        try (Connection con = Banco.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    /** Insere ou altera. A foto só é trocada quando uma nova foi enviada. */
    public void salvar(Produto p, byte[] foto, String tipoFoto) throws SQLException {
        try (Connection con = Banco.conectar()) {
            PreparedStatement ps;
            if (p.getId() == null) {
                ps = con.prepareStatement("INSERT INTO produto (nome, categoria, preco, "
                        + "estoque, foto, foto_tipo) VALUES (?, ?, ?, ?, ?, ?)");
                ps.setBytes(5, foto);
                ps.setString(6, tipoFoto);
            } else if (foto != null) {
                ps = con.prepareStatement("UPDATE produto SET nome = ?, categoria = ?, "
                        + "preco = ?, estoque = ?, foto = ?, foto_tipo = ? WHERE id = ?");
                ps.setBytes(5, foto);
                ps.setString(6, tipoFoto);
                ps.setLong(7, p.getId());
            } else {
                ps = con.prepareStatement("UPDATE produto SET nome = ?, categoria = ?, "
                        + "preco = ?, estoque = ? WHERE id = ?");
                ps.setLong(5, p.getId());
            }
            try (ps) {
                ps.setString(1, p.getNome());
                ps.setString(2, p.getCategoria());
                ps.setBigDecimal(3, p.getPreco());
                ps.setInt(4, p.getEstoque());
                ps.executeUpdate();
            }
        }
    }

    public void excluir(long id) throws SQLException {
        try (Connection con = Banco.conectar();
             PreparedStatement ps =
                     con.prepareStatement("DELETE FROM produto WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    /** Devolve {bytes, tipo} da foto, ou null. */
    public Object[] foto(long id) throws SQLException {
        String sql = "SELECT foto, foto_tipo FROM produto WHERE id = ?";
        try (Connection con = Banco.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getBytes(1) != null) {
                    return new Object[]{rs.getBytes(1), rs.getString(2)};
                }
                return null;
            }
        }
    }

    private Produto mapear(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setCategoria(rs.getString("categoria"));
        p.setPreco(rs.getBigDecimal("preco"));
        p.setEstoque(rs.getInt("estoque"));
        p.setTemFoto(rs.getBoolean("tem_foto"));
        return p;
    }
}
