package br.com.curso.financas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraFinanceiraTest {

    private CalculadoraFinanceira calc;

    @BeforeEach                      // roda antes de CADA teste
    void preparar() {
        calc = new CalculadoraFinanceira();
    }

    @Test
    @DisplayName("Montante de 1000 a 1% por 12 meses")
    void montanteComJurosCompostos() {
        BigDecimal m = calc.montante(new BigDecimal("1000"), 1.0, 12);
        assertEquals(new BigDecimal("1126.83"), m);
    }

    @Test
    @DisplayName("Sem juros, a parcela é a divisão simples")
    void parcelaSemJuros() {
        BigDecimal p = calc.parcela(new BigDecimal("1000"), 0, 4);
        assertEquals(new BigDecimal("250.00"), p);
    }

    @ParameterizedTest(name = "{0} em {2}x a {1}% = {3}")
    @CsvSource({
        "1000, 2.0, 10, 111.33",
        "5000, 1.99, 12, 472.51",
        "1200, 1.0, 3, 408.03"
    })
    void parcelaPrice(String valor, double taxa, int n, String esperado) {
        BigDecimal p = calc.parcela(new BigDecimal(valor), taxa, n);
        assertEquals(new BigDecimal(esperado), p);
    }

    @Test
    @DisplayName("Zero parcelas lança exceção")
    void parcelasInvalidas() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> calc.parcela(new BigDecimal("100"), 1.0, 0));
        assertEquals("Número de parcelas deve ser positivo", e.getMessage());
    }
}
