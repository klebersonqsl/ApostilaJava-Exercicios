import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Grava uma imagem em uma coluna binária e a recupera. Uso: url usuario senha */
public class ImagensNoBanco {

    public static void main(String[] args) throws SQLException, IOException {
        Banco banco = Banco.daUrl(args[0]);
        Path original = Path.of("paisagem.jpg");

        try (Connection con = DriverManager.getConnection(args[0], args[1], args[2])) {
            try (Statement st = con.createStatement()) {
                st.execute(banco.apagarTabela("foto"));
                st.execute("CREATE TABLE foto (id INTEGER PRIMARY KEY, "
                        + "nome VARCHAR(100), tipo VARCHAR(50), "
                        + "dados " + banco.tipoBinario() + ")");
            }

            // gravando: os bytes do arquivo vão para a coluna binária
            String insert = "INSERT INTO foto (id, nome, tipo, dados) VALUES (?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setInt(1, 1);
                ps.setString(2, original.getFileName().toString());
                ps.setString(3, Files.probeContentType(original));
                ps.setBytes(4, Files.readAllBytes(original));
                ps.executeUpdate();
            }
            System.out.println("Gravado: " + Files.size(original) + " bytes");

            // lendo de volta para outro arquivo
            Path copia = Path.of("recuperada.jpg");
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT nome, tipo, dados FROM foto WHERE id = ?")) {
                ps.setInt(1, 1);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        byte[] bytes = rs.getBytes("dados");
                        Files.write(copia, bytes);
                        System.out.println("Lido: " + rs.getString("nome") + " ("
                                + rs.getString("tipo") + ", " + bytes.length + " bytes)");
                    }
                }
            }
            System.out.println("Arquivo idêntico ao original? "
                    + (Files.mismatch(original, copia) == -1));
        }
    }
}
