public class Vendedor extends Funcionario {
    private double vendasDoMes;
    private static final double COMISSAO = 0.05;

    public Vendedor(String nome, double salarioBase) {
        super(nome, salarioBase);
    }

    public void registrarVenda(double valor) {
        vendasDoMes += valor;
    }

    @Override
    public double calcularSalario() {
        return salarioBase + vendasDoMes * COMISSAO;
    }
}
