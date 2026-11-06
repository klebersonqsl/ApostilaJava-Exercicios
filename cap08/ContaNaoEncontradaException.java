// exceção não verificada (unchecked): estende RuntimeException
public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(String numero) {
        super("Conta não encontrada: " + numero);
    }
}
