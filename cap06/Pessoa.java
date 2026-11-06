public class Pessoa {
    // atributos: o que cada objeto Pessoa "sabe"
    String nome;
    int anoNascimento;

    // métodos: o que cada objeto Pessoa "faz"
    int idadeEm(int ano) {
        return ano - anoNascimento;
    }

    void apresentar() {
        System.out.println("Olá, eu sou " + nome + ".");
    }
}
