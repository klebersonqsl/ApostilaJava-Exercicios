/** Endereço devolvido pelas buscas de CEP, já no formato do nosso sistema. */
public record Endereco(String cep, String logradouro, String bairro, String cidade,
                       String uf) {

    @Override
    public String toString() {
        return logradouro + ", " + bairro + " - " + cidade + "/" + uf
                + " (CEP " + cep + ")";
    }
}
