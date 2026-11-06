import java.util.Optional;

/** Qualquer serviço de CEP: o resto do sistema não precisa saber qual é. */
public interface BuscaCep {

    Optional<Endereco> buscar(String cep) throws Exception;

    /** Mantém só os dígitos e confere se são 8. */
    static String normalizar(String cep) {
        String digitos = cep.replaceAll("\\D", "");
        if (digitos.length() != 8) {
            throw new IllegalArgumentException("CEP deve ter 8 dígitos: " + cep);
        }
        return digitos;
    }
}
