import java.math.BigDecimal;
import java.math.RoundingMode;

public class Dinheiro {
    public static void main(String[] args) {
        // double usa binário e não representa exatamente alguns decimais
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));
        System.out.println("1.10 - 1.00 = " + (1.10 - 1.00));

        // BigDecimal: precisão exata, ideal para dinheiro
        BigDecimal a = new BigDecimal("0.1");
        BigDecimal b = new BigDecimal("0.2");
        System.out.println("BigDecimal 0.1 + 0.2 = " + a.add(b));

        BigDecimal preco = new BigDecimal("199.90");
        BigDecimal quantidade = new BigDecimal("3");
        BigDecimal total = preco.multiply(quantidade);
        BigDecimal desconto = total.multiply(new BigDecimal("0.07"))
                                   .setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Total: " + total);
        System.out.println("Desconto de 7%: " + desconto);
        System.out.println("A pagar: " + total.subtract(desconto));

        // divisão precisa informar escala e arredondamento
        BigDecimal parcela = total.divide(new BigDecimal("7"), 2, RoundingMode.HALF_EVEN);
        System.out.println("Em 7 parcelas de: " + parcela);
    }
}
