package br.com.curso.financas;

import java.math.BigDecimal;

public record Simulacao(BigDecimal valor, double taxaMensal, int parcelas,
                        BigDecimal parcela, BigDecimal totalPago) {
}
