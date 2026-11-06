import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Lê e grava arquivos .ini: seções [nome] com linhas chave = valor. */
public class ArquivoIni {

    // seção -> (chave -> valor); LinkedHashMap mantém a ordem do arquivo
    private final Map<String, Map<String, String>> secoes = new LinkedHashMap<>();

    public static ArquivoIni ler(Path arquivo) throws IOException {
        ArquivoIni ini = new ArquivoIni();
        String secao = "";                      // chaves antes de qualquer [seção]
        int numero = 0;
        for (String linha : Files.readAllLines(arquivo)) {
            numero++;
            linha = linha.strip();
            if (linha.isEmpty() || linha.startsWith(";") || linha.startsWith("#")) {
                continue;                                   // linha vazia ou comentário
            }
            if (linha.startsWith("[") && linha.endsWith("]")) {
                secao = linha.substring(1, linha.length() - 1).strip();
                ini.secoes.putIfAbsent(secao, new LinkedHashMap<>());
                continue;
            }
            int igual = linha.indexOf('=');
            if (igual < 0) {
                throw new IOException("Linha " + numero + " inválida: " + linha);
            }
            String chave = linha.substring(0, igual).strip();
            String valor = tirarAspas(linha.substring(igual + 1).strip());
            ini.set(secao, chave, valor);
        }
        return ini;
    }

    public Optional<String> get(String secao, String chave) {
        return Optional.ofNullable(secoes.getOrDefault(secao, Map.of()).get(chave));
    }

    public String get(String secao, String chave, String padrao) {
        return get(secao, chave).orElse(padrao);
    }

    public int getInt(String secao, String chave, int padrao) {
        return get(secao, chave).map(Integer::parseInt).orElse(padrao);
    }

    public void set(String secao, String chave, String valor) {
        secoes.computeIfAbsent(secao, s -> new LinkedHashMap<>()).put(chave, valor);
    }

    public List<String> secoes() {
        return new ArrayList<>(secoes.keySet());
    }

    public Map<String, String> chaves(String secao) {
        return secoes.getOrDefault(secao, Map.of());
    }

    public void gravar(Path arquivo, String comentario) throws IOException {
        List<String> linhas = new ArrayList<>();
        linhas.add("; " + comentario);
        secoes.forEach((secao, valores) -> {
            linhas.add("");
            if (!secao.isEmpty()) {
                linhas.add("[" + secao + "]");
            }
            valores.forEach((chave, valor) -> linhas.add(chave + " = " + valor));
        });
        Files.write(arquivo, linhas);
    }

    private static String tirarAspas(String v) {
        boolean aspas = v.length() >= 2 && v.startsWith("\"") && v.endsWith("\"");
        return aspas ? v.substring(1, v.length() - 1) : v;
    }
}
