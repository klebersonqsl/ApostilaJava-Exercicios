public class ContaBancaria {
    // private: só a própria classe acessa diretamente
    private final String numero;
    private String titular;
    private double saldo;

    public ContaBancaria(String numero, String titular) {
        this.numero = numero;
        setTitular(titular);      // reaproveita a validação do setter
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("  [recusado] depósito deve ser positivo");
            return;
        }
        saldo += valor;
    }

    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldo) {
            System.out.println("  [recusado] saque de " + valor + " não permitido");
            return false;
        }
        saldo -= valor;
        return true;
    }

    // getters: leitura controlada
    public double getSaldo() {
        return saldo;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    // setter com validação: o objeto nunca fica em estado inválido
    public void setTitular(String titular) {
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("Titular obrigatório");
        }
        this.titular = titular.strip();
    }
    // não existe setSaldo: o saldo só muda por depósitos e saques
}
