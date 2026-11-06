import java.time.LocalDate;

public class Registros {

    // record: classe imutável de dados em uma linha
    record Ponto(int x, int y) {
        double distanciaOrigem() {
            return Math.sqrt(x * x + y * y);
        }
    }

    record Cliente(String nome, String email, LocalDate cadastro) {
        // construtor compacto: valida os dados
        Cliente {
            if (nome == null || nome.isBlank()) {
                throw new IllegalArgumentException("nome obrigatório");
            }
            email = email.toLowerCase();
        }
    }

    public static void main(String[] args) {
        Ponto p = new Ponto(3, 4);
        System.out.println(p);                                   // toString automático
        System.out.println("x = " + p.x() + ", distância = " + p.distanciaOrigem());
        System.out.println("Iguais? " + p.equals(new Ponto(3, 4)));   // equals automático

        Cliente c = new Cliente("Tânia", "TANIA@EMAIL.COM", LocalDate.of(2025, 3, 10));
        System.out.println(c);
        try {
            new Cliente(" ", "x@y.com", LocalDate.now());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
