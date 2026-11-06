import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PoolConexoes {
    public static void main(String[] args) throws SQLException {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:postgresql://localhost:5432/curso");
        cfg.setUsername("aluno");
        cfg.setPassword("senha123");
        cfg.setMaximumPoolSize(5);          // no máximo 5 conexões abertas
        cfg.setMinimumIdle(2);              // mantém 2 prontas mesmo sem uso
        cfg.setPoolName("PoolCurso");

        // o DataSource é criado uma vez e compartilhado pelo programa inteiro
        try (HikariDataSource ds = new HikariDataSource(cfg)) {
            List<Connection> emUso = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                emUso.add(ds.getConnection());          // pega uma conexão do pool
            }
            var pool = ds.getHikariPoolMXBean();
            System.out.println("Em uso: " + pool.getActiveConnections()
                    + " | total aberto: " + pool.getTotalConnections());

            for (Connection c : emUso) {
                c.close();               // devolve ao pool (não fecha de verdade)
            }
            int ativas = pool.getActiveConnections();
            int ociosas = pool.getIdleConnections();
            System.out.println("Agora em uso: " + ativas + " | ociosas: " + ociosas);

            try (Connection con = ds.getConnection();
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM produto")) {
                rs.next();
                System.out.println("Consulta pelo pool: " + rs.getInt(1) + " produtos");
            }
        }
    }
}
