// exceção verificada (checked): estende Exception
public class SaldoInsuficienteException extends Exception {
    private final double faltam;

    public SaldoInsuficienteException(double saldo, double pedido) {
        super(String.format("Saldo insuficiente: saldo R$ %.2f, pedido R$ %.2f",
                saldo, pedido));
        this.faltam = pedido - saldo;
    }

    public double getFaltam() {
        return faltam;
    }
}
