// abstract: não pode ser instanciada; serve de base para outras classes
public abstract class Forma {
    private final String nome;

    protected Forma(String nome) {
        this.nome = nome;
    }

    public abstract double area();          // sem corpo: cada subclasse implementa

    public abstract double perimetro();

    // método concreto que usa os abstratos
    public String descricao() {
        return String.format("%-10s área = %6.2f   perímetro = %6.2f",
                nome, area(), perimetro());
    }
}
