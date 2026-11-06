import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/** O mesmo programa roda em qualquer um dos cinco bancos. Uso: url usuario senha */
public class TesteBancos {

    public static void main(String[] args) throws SQLException {
        String url = args[0];
        Banco banco = Banco.daUrl(url);

        try (Connection con = DriverManager.getConnection(url, args[1], args[2])) {
            DatabaseMetaData m = con.getMetaData();
            System.out.println("== " + m.getDatabaseProductName() + " "
                    + m.getDatabaseProductVersion().lines().findFirst().orElse(""));

            try (Statement st = con.createStatement()) {
                st.execute(banco.apagarTabela("produto"));
                st.execute(banco.criarTabela());
            }

            String[] nomes = {"Caneta", "Lápis", "Borracha", "Régua", "Estojo"};
            String[] precos = {"2.50", "1.20", "0.90", "3.75", "24.90"};
            for (int i = 0; i < nomes.length; i++) {
                long id = inserir(con, banco, nomes[i], new BigDecimal(precos[i]));
                System.out.print(nomes[i] + "=" + id + " ");
            }
            System.out.println();

            String select = "SELECT id, nome, preco, cadastro FROM produto ORDER BY nome";
            for (int pagina = 1; pagina <= 2; pagina++) {
                String sql = banco.paginar(select, 3, (pagina - 1) * 3);
                System.out.print("Página " + pagina + ":");
                try (Statement st = con.createStatement();
                     ResultSet rs = st.executeQuery(sql)) {
                    while (rs.next()) {
                        System.out.printf(" %s (%.2f)", rs.getString("nome"),
                                rs.getBigDecimal("preco"));
                    }
                }
                System.out.println();
            }

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                         "SELECT COUNT(*), SUM(preco) FROM produto")) {
                rs.next();
                System.out.println("Total: " + rs.getInt(1) + " produtos, soma "
                        + rs.getBigDecimal(2).setScale(2));
            }
        }
    }

    static long inserir(Connection con, Banco banco, String nome, BigDecimal preco)
            throws SQLException {
        String sql = "INSERT INTO produto (nome, preco, cadastro) VALUES (?, ?, ?)";
        if (banco.usaReturning()) {
            try (PreparedStatement ps = con.prepareStatement(sql + " RETURNING id")) {
                preencher(ps, nome, preco);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    return rs.getLong(1);
                }
            }
        }
        try (PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencher(ps, nome, preco);
            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                chaves.next();
                return chaves.getLong(1);
            }
        }
    }

    static void preencher(PreparedStatement ps, String nome, BigDecimal preco)
            throws SQLException {
        ps.setString(1, nome);
        ps.setBigDecimal(2, preco);
        ps.setObject(3, LocalDate.of(2026, 10, 26));
    }
}
