import java.util.List;
import java.util.Optional;

/**
 * Tenta a API oficial dos Correios e, se ela falhar, usa o ViaCEP.
 * Uso: java ConsultaCep [urlCorreios] [urlViaCep]
 * Credenciais nas variáveis CORREIOS_USUARIO, CORREIOS_CODIGO e CORREIOS_CARTAO.
 */
public class ConsultaCep {
    public static void main(String[] args) throws Exception {
        String urlCorreios = args.length > 0 ? args[0] : "https://api.correios.com.br";
        String urlViaCep = args.length > 1 ? args[1] : "https://viacep.com.br";

        List<BuscaCep> servicos = List.of(
                new CepCorreios(urlCorreios, System.getenv("CORREIOS_USUARIO"),
                        System.getenv("CORREIOS_CODIGO"),
                        System.getenv("CORREIOS_CARTAO")),
                new CepViaCep(urlViaCep));

        for (String cep : List.of("01001-000", "70040-010", "99999-999", "123")) {
            System.out.println("CEP " + cep + ":");
            System.out.println("  " + consultar(servicos, cep)
                    .map(Endereco::toString).orElse("não encontrado"));
        }
    }

    /** Pergunta a cada serviço, na ordem; passa ao próximo se um deles falhar. */
    static Optional<Endereco> consultar(List<BuscaCep> servicos, String cep) {
        for (BuscaCep servico : servicos) {
            try {
                return servico.buscar(cep);
            } catch (IllegalArgumentException e) {
                System.out.println("  " + e.getMessage());
                return Optional.empty();        // CEP mal digitado: nem adianta tentar
            } catch (Exception e) {
                System.out.println("  " + servico.getClass().getSimpleName()
                        + " indisponível (" + e.getMessage()
                        + "), tentando o próximo...");
            }
        }
        return Optional.empty();
    }
}
