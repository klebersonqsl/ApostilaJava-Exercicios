package br.com.curso.financas;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraFinanceira {

    /** Montante com juros compostos: capital * (1 + taxa)^meses. Taxa em % ao mês. */
    public BigDecimal montante(BigDecimal capital, double taxaMensal, int meses) {
        if (capital.signum() < 0 || meses < 0) {
            throw new IllegalArgumentException("Capital e meses não podem ser negativos");
        }
        double fator = Math.pow(1 + taxaMensal / 100, meses);
        return capital.multiply(BigDecimal.valueOf(fator))
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    /** Parcela fixa pela tabela Price. */
    public BigDecimal parcela(BigDecimal valor, double taxaMensal, int parcelas) {
        if (parcelas <= 0) {
            throw new IllegalArgumentException("Número de parcelas deve ser positivo");
        }
        if (taxaMensal == 0) {
            return valor.divide(BigDecimal.valueOf(parcelas), 2, RoundingMode.HALF_EVEN);
        }
        double i = taxaMensal / 100;
        double p = valor.doubleValue() * i / (1 - Math.pow(1 + i, -parcelas));
        return BigDecimal.valueOf(p).setScale(2, RoundingMode.HALF_EVEN);
    }
}
