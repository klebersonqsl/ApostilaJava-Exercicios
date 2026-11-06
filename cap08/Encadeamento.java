public class Encadeamento {

    static class ImportacaoException extends Exception {
        ImportacaoException(String msg, Throwable causa) {
            super(msg, causa);                    // guarda a exceção original como causa
        }
    }

    static int lerQuantidade(String linha) {
        String[] campos = linha.split(";");
        return Integer.parseInt(campos[1]);
    }

    static void importar(String[] linhas) throws ImportacaoException {
        for (int i = 0; i < linhas.length; i++) {
            try {
                int qtd = lerQuantidade(linhas[i]);
                System.out.println("Linha " + (i + 1) + ": quantidade " + qtd);
            } catch (NumberFormatException e) {
                throw new ImportacaoException("Erro na linha " + (i + 1), e);
            }
        }
    }

    public static void main(String[] args) throws ImportacaoException {
        importar(new String[]{"caneta;10", "lápis;20", "borracha;dez"});
    }
}
