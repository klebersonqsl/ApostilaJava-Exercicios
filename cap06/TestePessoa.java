public class TestePessoa {
    public static void main(String[] args) {
        Pessoa p1 = new Pessoa();       // cria um objeto (instância) da classe Pessoa
        p1.nome = "Luiza";
        p1.anoNascimento = 2001;

        Pessoa p2 = new Pessoa();       // outro objeto, com seus próprios atributos
        p2.nome = "Marcos";
        p2.anoNascimento = 1988;

        p1.apresentar();
        p2.apresentar();
        System.out.println(p1.nome + " terá " + p1.idadeEm(2026) + " anos em 2026.");
        System.out.println(p2.nome + " terá " + p2.idadeEm(2026) + " anos em 2026.");

        Pessoa p3 = p1;                 // p3 aponta para o MESMO objeto que p1
        p3.nome = "Luiza Andrade";
        System.out.println("p1.nome agora é: " + p1.nome);

        Pessoa ninguem = null;          // referência que não aponta para nenhum objeto
        System.out.println("ninguem é null? " + (ninguem == null));
        ninguem.apresentar();           // NullPointerException!
    }
}
