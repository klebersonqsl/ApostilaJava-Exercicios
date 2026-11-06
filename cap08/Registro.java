import java.util.logging.Level;
import java.util.logging.Logger;

public class Registro {
    private static final Logger LOG = Logger.getLogger(Registro.class.getName());

    public static void main(String[] args) {
        LOG.info("Sistema iniciado");
        LOG.fine("Detalhe de depuração (não aparece no nível padrão)");
        try {
            int[] v = new int[2];
            v[5] = 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.log(Level.WARNING, "Falha ao gravar posição: " + e.getMessage());
        }
        LOG.severe("Erro grave: banco de dados indisponível");
    }
}
