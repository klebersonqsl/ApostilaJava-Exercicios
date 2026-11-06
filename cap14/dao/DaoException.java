/** Exceção não verificada que esconde o SQLException de quem usa o DAO. */
public class DaoException extends RuntimeException {
    public DaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
