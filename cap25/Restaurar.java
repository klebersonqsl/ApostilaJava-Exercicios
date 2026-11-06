import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/** Confere e extrai um backup. Uso: java Restaurar backups/backup-....zip */
public class Restaurar {
    public static void main(String[] args) throws Exception {
        Path zip = Path.of(args[0]);
        Path destino = Path.of("restaurado").toAbsolutePath().normalize();

        try (ZipFile arquivo = new ZipFile(zip.toFile())) {
            // 1) lê o manifesto: nome do arquivo -> SHA-256 esperado
            Map<String, String> esperado = new HashMap<>();
            String manifesto = new String(arquivo.getInputStream(
                    arquivo.getEntry("manifesto.txt")).readAllBytes());
            System.out.println(manifesto.lines().findFirst().orElse(""));
            manifesto.lines().skip(1)
                    .forEach(l -> esperado.put(l.substring(66), l.substring(0, 64)));

            // 2) confere e extrai cada arquivo
            for (ZipEntry e : arquivo.stream().toList()) {
                if (e.getName().equals("manifesto.txt")) {
                    continue;
                }
                Path alvo = destino.resolve(e.getName()).normalize();
                if (!alvo.startsWith(destino)) {          // "zip slip": ../../ no nome
                    throw new SecurityException("Caminho suspeito no ZIP: "
                            + e.getName());
                }
                byte[] bytes = arquivo.getInputStream(e).readAllBytes();
                String hash = HexFormat.of().formatHex(
                        MessageDigest.getInstance("SHA-256").digest(bytes));
                boolean ok = hash.equals(esperado.get(e.getName()));
                System.out.printf("  %-28s %7d bytes  %s%n", e.getName(), bytes.length,
                        ok ? "íntegro" : "CORROMPIDO");
                if (ok) {
                    Files.createDirectories(alvo.getParent());
                    Files.write(alvo, bytes);
                }
            }
        }
        System.out.println("Arquivos restaurados em " + Path.of("restaurado"));
    }
}
