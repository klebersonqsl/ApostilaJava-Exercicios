import java.util.List;
import java.util.Optional;

public class Opcionais {

    record Cliente(String nome, String email) {}

    static final List<Cliente> CLIENTES = List.of(
            new Cliente("Ana", "ana@mail.com"),
            new Cliente("Beto", null));

    // em vez de devolver null, devolve um Optional
    static Optional<Cliente> buscar(String nome) {
        return CLIENTES.stream().filter(c -> c.nome().equals(nome)).findFirst();
    }

    public static void main(String[] args) {
        Optional<Cliente> ana = buscar("Ana");
        Optional<Cliente> zeca = buscar("Zeca");

        System.out.println("Achou Ana? " + ana.isPresent());
        System.out.println("Achou Zeca? " + zeca.isPresent());
        ana.ifPresent(c -> System.out.println("E-mail da Ana: " + c.email()));

        String nome = zeca.map(Cliente::nome).orElse("cliente não encontrado");
        System.out.println(nome);

        // encadeando: o e-mail pode ser nulo também
        String email = buscar("Beto")
                .map(Cliente::email)       // Optional vazio se o e-mail for null
                .orElse("sem e-mail cadastrado");
        System.out.println("Beto: " + email);

        try {
            buscar("Zeca").orElseThrow(
                    () -> new IllegalStateException("Zeca não existe"));
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
