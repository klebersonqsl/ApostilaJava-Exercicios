package br.com.loja.servico;

/** Violação de uma regra do negócio, como cadastrar dois produtos com o mesmo nome. */
public class RegraDeNegocioException extends RuntimeException {

    private final String campo;

    public RegraDeNegocioException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
