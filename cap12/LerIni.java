import java.io.IOException;
import java.nio.file.Path;

public class LerIni {
    public static void main(String[] args) throws IOException {
        Path arquivo = Path.of("sistema.ini");
        ArquivoIni ini = ArquivoIni.ler(arquivo);

        System.out.println("Seções: " + ini.secoes());
        System.out.println("Sistema: " + ini.get("geral", "nome", "?")
                + " " + ini.get("geral", "versao", "?"));
        System.out.println("Banco: " + ini.get("banco", "url", "não configurado"));
        // a barra invertida é lida como está (num .properties ela precisaria ser dobrada)
        System.out.println("Relatórios em: " + ini.get("relatorios", "pasta", "."));
        int copias = ini.getInt("relatorios", "copias", 1);
        System.out.println("Cópias: " + copias + " (o dobro: " + copias * 2 + ")");
        String idioma = ini.get("geral", "idioma", "pt-BR");   // chave ausente: padrão
        System.out.println("Idioma: " + idioma);

        System.out.println();
        for (String secao : ini.secoes()) {
            System.out.println("[" + secao + "] " + ini.chaves(secao).keySet());
        }

        ini.set("geral", "tema", "escuro");                 // altera
        ini.set("usuario", "ultimo", "maria");              // cria seção e chave
        ini.gravar(arquivo, "Atualizado pelo sistema");
        System.out.println("\nArquivo gravado com " + ini.secoes().size() + " seções.");
    }
}
