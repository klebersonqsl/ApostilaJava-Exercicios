// uma classe pode implementar várias interfaces
public class Servico implements Tributavel, Descritivel {
    private final String nome;
    private final double horas;
    private final double valorHora;

    public Servico(String nome, double horas, double valorHora) {
        this.nome = nome;
        this.horas = horas;
        this.valorHora = valorHora;
    }

    @Override
    public double valorBase() {
        return horas * valorHora;
    }

    @Override
    public double imposto() {                 // serviços pagam ISS de 5%
        return valorBase() * 0.05;
    }

    @Override
    public String descricao() {
        return "Serviço: " + nome;
    }
}
