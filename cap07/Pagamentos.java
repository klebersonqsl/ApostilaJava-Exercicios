public class Pagamentos {

    // sealed: só os tipos listados em permits podem implementar a interface
    sealed interface Pagamento permits Pix, Cartao, Boleto {}

    record Pix(String chave, double valor) implements Pagamento {}
    record Cartao(String bandeira, double valor, int parcelas) implements Pagamento {}
    record Boleto(String codigo, double valor) implements Pagamento {}

    static String descrever(Pagamento p) {
        // switch com padrões de record (Java 21): testa o tipo e extrai os campos
        return switch (p) {
            case Pix(String chave, double valor) ->
                    "Pix de R$ %.2f para %s".formatted(valor, chave);
            case Cartao(String b, double v, int n) when n > 1 ->
                    "%s em %dx de R$ %.2f".formatted(b, n, v / n);
            case Cartao c -> c.bandeira() + " à vista";
            case Boleto b -> "Boleto " + b.codigo();
            // não precisa de default: o compilador sabe que todos os casos foram cobertos
        };
    }

    public static void main(String[] args) {
        Pagamento[] lista = {
            new Pix("ana@banco.com", 150),
            new Cartao("Visa", 900, 3),
            new Cartao("Elo", 45.9, 1),
            new Boleto("34191.79001 01043", 320)
        };
        for (Pagamento p : lista) {
            System.out.println(descrever(p));
        }
    }
}
