package br.com.loja.modelo;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDao {

    public void salvar(String nome, String tipo, long tamanho, InputStream conteudo)
            throws Exception {
        String sql = "INSERT INTO documento (nome, tipo, tamanho, enviado_em, conteudo) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Banco.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, tipo);
            ps.setLong(3, tamanho);
            ps.setObject(4, LocalDateTime.of(2026, 10, 26, 9, 15));  // data fixa
            ps.setBytes(5, conteudo.readAllBytes());
            ps.executeUpdate();
        }
    }

    public List<Documento> listar() throws SQLException {
        List<Documento> lista = new ArrayList<>();
        String sql = "SELECT id, nome, tipo, tamanho, enviado_em FROM documento "
                + "ORDER BY nome";
        try (Connection con = Banco.conectar(); Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Documento d = new Documento();
                d.setId(rs.getLong("id"));
                d.setNome(rs.getString("nome"));
                d.setTipo(rs.getString("tipo"));
                d.setTamanho(rs.getLong("tamanho"));
                d.setEnviadoEm(rs.getObject("enviado_em", LocalDateTime.class));
                lista.add(d);
            }
        }
        return lista;
    }

    /** Devolve {nome, tipo, bytes} do documento, ou null. */
    public Object[] conteudo(long id) throws SQLException {
        String sql = "SELECT nome, tipo, conteudo FROM documento WHERE id = ?";
        try (Connection con = Banco.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? new Object[]{rs.getString(1), rs.getString(2), rs.getBytes(3)}
                        : null;
            }
        }
    }
}
