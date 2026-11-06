import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/** Copia o banco e os arquivos de configuração para um único ZIP, com manifesto. */
public class Backup {

    private static final DateTimeFormatter NOME =
            DateTimeFormatter.ofPattern("'backup-'yyyy-MM-dd_HHmmss'.zip'");

    public static void main(String[] args) throws Exception {
        Path zip = criar(Path.of("loja.db"),
                List.of(Path.of("config/sistema.ini"),
                        Path.of("config/config.properties")),
                Path.of("backups"));
        System.out.println("Backup criado: " + zip + " (" + Files.size(zip) + " bytes)");
        manterUltimos(Path.of("backups"), 5);
    }

    public static Path criar(Path banco, List<Path> configs, Path pasta)
            throws Exception {
        Files.createDirectories(pasta);
        Path zip = pasta.resolve(LocalDateTime.now().format(NOME));

        // 1) cópia consistente do banco, mesmo com o sistema em uso
        Path copia = Files.createTempDirectory("bkp").resolve(banco.getFileName());
        copiarSqlite(banco, copia);

        List<String> manifesto = new ArrayList<>();
        manifesto.add("Backup de " + LocalDateTime.now().withNano(0));
        try (ZipOutputStream saida = new ZipOutputStream(Files.newOutputStream(zip))) {
            saida.setLevel(9);                            // compressão máxima
            adicionar(saida, copia, "banco/" + banco.getFileName(), manifesto);
            for (Path config : configs) {
                adicionar(saida, config, "config/" + config.getFileName(), manifesto);
            }
            // 2) manifesto: o que foi copiado e o SHA-256 de cada arquivo
            saida.putNextEntry(new ZipEntry("manifesto.txt"));
            saida.write(String.join("\n", manifesto).getBytes());
            saida.closeEntry();
        }
        Files.delete(copia);
        return zip;
    }

    /** SQLite: VACUUM INTO grava uma cópia íntegra, sem travar quem usa o banco. */
    static void copiarSqlite(Path banco, Path destino) throws Exception {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + banco);
             Statement st = c.createStatement()) {
            st.execute("VACUUM INTO '" + destino.toString().replace("'", "''") + "'");
        }
    }

    /** PostgreSQL: o pg_dump, que vem com o servidor, gera um script SQL do banco. */
    static void copiarPostgres(String banco, String usuario, String senha, Path destino)
            throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("pg_dump", "-h", "localhost",
                "-U", usuario,
                "--clean", "--if-exists", "-f", destino.toString(), banco);
        pb.environment().put("PGPASSWORD", senha);   // fora da linha de comando
        pb.redirectErrorStream(true);
        Process p = pb.start();
        String mensagens = new String(p.getInputStream().readAllBytes());
        if (p.waitFor() != 0) {
            throw new IOException("pg_dump falhou: " + mensagens);
        }
    }

    private static void adicionar(ZipOutputStream zip, Path arquivo, String nome,
                                  List<String> manifesto) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        ZipEntry entrada = new ZipEntry(nome);
        entrada.setLastModifiedTime(Files.getLastModifiedTime(arquivo));
        zip.putNextEntry(entrada);
        byte[] bytes = Files.readAllBytes(arquivo);
        zip.write(bytes);
        zip.closeEntry();
        manifesto.add(HexFormat.of().formatHex(sha.digest(bytes)) + "  " + nome);
        System.out.printf("  + %-28s %7d bytes%n", nome, bytes.length);
    }

    /** Apaga os backups mais antigos, mantendo só os N mais recentes. */
    static void manterUltimos(Path pasta, int quantos) throws IOException {
        try (Stream<Path> arquivos = Files.list(pasta)) {
            List<Path> antigos = arquivos
                    .filter(p -> p.getFileName().toString().matches("backup-.*\\.zip"))
                    .sorted((a, b) -> b.getFileName().compareTo(a.getFileName()))
                    .skip(quantos)                     // o nome tem a data: ordena certo
                    .toList();
            for (Path p : antigos) {
                Files.delete(p);
                System.out.println("Removido backup antigo: " + p.getFileName());
            }
        }
    }
}
