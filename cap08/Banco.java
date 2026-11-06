import java.util.HashMap;
import java.util.Map;

public class Banco {
    private final Map<String, Double> saldos = new HashMap<>();

    public void abrir(String numero, double saldoInicial) {
        saldos.put(numero, saldoInicial);
    }

    public void sacar(String numero, double valor) throws SaldoInsuficienteException {
        Double saldo = saldos.get(numero);
        if (saldo == null) {
            throw new ContaNaoEncontradaException(numero);
        }
        if (valor > saldo) {
            throw new SaldoInsuficienteException(saldo, valor);
        }
        saldos.put(numero, saldo - valor);
    }

    public double saldo(String numero) {
        return saldos.getOrDefault(numero, 0.0);
    }

    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.abrir("001", 300);

        String[][] operacoes = {{"001", "120"}, {"001", "500"}, {"999", "10"}};
        for (String[] op : operacoes) {
            try {
                banco.sacar(op[0], Double.parseDouble(op[1]));
                System.out.println("Saque de " + op[1] + " na conta " + op[0] + ": OK");
            } catch (SaldoInsuficienteException e) {
                System.out.println(e.getMessage());
                System.out.printf("  Faltam R$ %.2f%n", e.getFaltam());
            } catch (ContaNaoEncontradaException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Saldo final da 001: " + banco.saldo("001"));
    }
}
