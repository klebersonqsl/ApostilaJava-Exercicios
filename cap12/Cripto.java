import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Criptografa e descriptografa textos com AES-256 no modo GCM.
 * A chave é derivada de uma senha mestra com PBKDF2. O resultado tem o formato
 * ENC(base64), com sal, IV e texto cifrado juntos.
 */
public final class Cripto {

    private static final int ITERACOES = 600_000;   // deixa ataques de força bruta lentos
    private static final int TAM_SAL = 16;          // bytes
    private static final int TAM_IV = 12;           // bytes (recomendado para o GCM)
    private static final int TAM_TAG = 128;         // bits do código de autenticação
    private static final SecureRandom ALEATORIO = new SecureRandom();

    private Cripto() {
    }

    public static String criptografar(String texto, char[] senhaMestra)
            throws GeneralSecurityException {
        byte[] sal = aleatorio(TAM_SAL);
        byte[] iv = aleatorio(TAM_IV);              // um novo a cada uso: nunca repita
        Cipher cifra = Cipher.getInstance("AES/GCM/NoPadding");
        cifra.init(Cipher.ENCRYPT_MODE, chave(senhaMestra, sal),
                new GCMParameterSpec(TAM_TAG, iv));
        byte[] cifrado = cifra.doFinal(texto.getBytes(StandardCharsets.UTF_8));
        byte[] tudo = ByteBuffer.allocate(sal.length + iv.length + cifrado.length)
                .put(sal).put(iv).put(cifrado).array();
        return "ENC(" + Base64.getEncoder().encodeToString(tudo) + ")";
    }

    public static String descriptografar(String valor, char[] senhaMestra)
            throws GeneralSecurityException {
        if (!estaCriptografado(valor)) {
            return valor;                           // valor comum, sem criptografia
        }
        ByteBuffer tudo = ByteBuffer.wrap(
                Base64.getDecoder().decode(valor.substring(4, valor.length() - 1)));
        byte[] sal = new byte[TAM_SAL];
        byte[] iv = new byte[TAM_IV];
        tudo.get(sal).get(iv);
        byte[] cifrado = new byte[tudo.remaining()];
        tudo.get(cifrado);
        Cipher cifra = Cipher.getInstance("AES/GCM/NoPadding");
        cifra.init(Cipher.DECRYPT_MODE, chave(senhaMestra, sal),
                new GCMParameterSpec(TAM_TAG, iv));
        // com a senha mestra errada ou o texto adulterado, lança AEADBadTagException
        return new String(cifra.doFinal(cifrado), StandardCharsets.UTF_8);
    }

    public static boolean estaCriptografado(String valor) {
        return valor != null && valor.startsWith("ENC(") && valor.endsWith(")");
    }

    private static SecretKey chave(char[] senha, byte[] sal)
            throws GeneralSecurityException {
        SecretKeyFactory fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(senha, sal, ITERACOES, 256);
        byte[] bytes = fabrica.generateSecret(spec).getEncoded();
        spec.clearPassword();
        return new SecretKeySpec(bytes, "AES");
    }

    private static byte[] aleatorio(int tamanho) {
        byte[] b = new byte[tamanho];
        ALEATORIO.nextBytes(b);
        return b;
    }
}
