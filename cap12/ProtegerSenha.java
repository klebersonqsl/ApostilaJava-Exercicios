import java.nio.file.Path;
import javax.crypto.AEADBadTagException;

public class ProtegerSenha {
    public static void main(String[] args) throws Exception {
        // a senha mestra NÃO fica no arquivo: vem de uma variável de ambiente
        String variavel = System.getenv("APP_CHAVE");
        if (variavel == null) {
            System.err.println("Defina a variável de ambiente APP_CHAVE.");
            System.exit(1);
        }
        char[] mestra = variavel.toCharArray();
        Path arquivo = Path.of("sistema.ini");

        // 1) criptografa a senha do banco, se ela ainda estiver em texto puro
        ArquivoIni ini = ArquivoIni.ler(arquivo);
        String senha = ini.get("banco", "senha", "");
        if (!Cripto.estaCriptografado(senha)) {
            String protegida = Cripto.criptografar(senha, mestra);
            ini.set("banco", "senha", protegida);
            ini.gravar(arquivo, "Senhas protegidas com AES-256-GCM");
            System.out.println("Senha criptografada e gravada:");
            System.out.println("  " + protegida);
        }

        // 2) o sistema lê o arquivo e descriptografa na hora de usar
        ArquivoIni lido = ArquivoIni.ler(arquivo);
        String valor = lido.get("banco", "senha", "");
        String original = Cripto.descriptografar(valor, mestra);
        System.out.println("Senha recuperada para conectar: " + original);

        // 3) sem a senha mestra certa, nada feito
        try {
            Cripto.descriptografar(valor, "chute123".toCharArray());
        } catch (AEADBadTagException e) {
            System.out.println("Senha mestra errada: " + e.getClass().getSimpleName());
        }

        // 4) qualquer alteração no texto cifrado também é detectada
        String adulterado = valor.substring(0, 20) + (valor.charAt(20) == 'A' ? 'B' : 'A')
                + valor.substring(21);
        try {
            Cripto.descriptografar(adulterado, mestra);
        } catch (AEADBadTagException e) {
            System.out.println("Conteúdo adulterado: " + e.getClass().getSimpleName());
        }

        // 5) a mesma senha criptografada de novo gera outro texto (sal e IV novos)
        System.out.println("Outra vez:  " + Cripto.criptografar(original, mestra));
        java.util.Arrays.fill(mestra, ' ');         // apaga a senha mestra da memória
    }
}
