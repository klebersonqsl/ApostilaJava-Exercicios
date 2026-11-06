import java.time.LocalDate;

public record Venda(String vendedor, String regiao, String produto, int quantidade,
                    double valorUnitario, LocalDate data) {

    public double total() {
        return quantidade * valorUnitario;
    }
}
