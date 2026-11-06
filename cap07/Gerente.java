public class Gerente extends Funcionario {      // Gerente É UM Funcionario
    private final double gratificacao;

    public Gerente(String nome, double salarioBase, double gratificacao) {
        super(nome, salarioBase);                // chama o construtor da superclasse
        this.gratificacao = gratificacao;
    }

    @Override                                    // redefine o comportamento herdado
    public double calcularSalario() {
        return super.calcularSalario() + gratificacao;
    }
}
