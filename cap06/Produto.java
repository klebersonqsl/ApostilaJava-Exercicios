import java.util.Objects;

public class Produto {
    // static: um único valor, compartilhado por todos os objetos da classe
    private static int totalCriados = 0;

    private final String codigo;
    private final String descricao;
    private double preco;

    public Produto(String codigo, String descricao, double preco) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        totalCriados++;
    }

    public static int getTotalCriados() {      // método static: chamado pela classe
        return totalCriados;
    }

    public void reajustar(double percentual) {
        preco = preco * (1 + percentual / 100);
    }

    public String getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }

    // texto que representa o objeto (usado por println e na concatenação)
    @Override
    public String toString() {
        return String.format("Produto[%s, %s, R$ %.2f]", codigo, descricao, preco);
    }

    // dois produtos são iguais se tiverem o mesmo código
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto outro)) return false;
        return codigo.equals(outro.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
