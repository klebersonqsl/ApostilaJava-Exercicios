public class Funcionario {
    // protected: acessível nas subclasses
    protected String nome;
    protected double salarioBase;

    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
    }

    public double calcularSalario() {
        return salarioBase;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        String tipo = getClass().getSimpleName();     // nome da classe real do objeto
        return String.format("%-12s %-8s R$ %9.2f", tipo, nome, calcularSalario());
    }
}
