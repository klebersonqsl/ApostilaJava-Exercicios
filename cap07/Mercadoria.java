public class Mercadoria implements Tributavel, Descritivel {
    private final String nome;
    private final double preco;

    public Mercadoria(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    @Override
    public double valorBase() {
        return preco;
    }
    // imposto() não foi sobrescrito: usa o default de 10%

    @Override
    public String descricao() {
        return "Mercadoria: " + nome;
    }
}
