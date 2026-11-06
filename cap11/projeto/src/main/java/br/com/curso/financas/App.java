package br.com.curso.financas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        CalculadoraFinanceira calc = new CalculadoraFinanceira();
        BigDecimal valor = new BigDecimal("5000.00");
        double taxa = 1.99;
        int n = 12;

        BigDecimal parcela = calc.parcela(valor, taxa, n);
        BigDecimal total = parcela.multiply(BigDecimal.valueOf(n));
        Simulacao s = new Simulacao(valor, taxa, n, parcela, total);

        // Gson: biblioteca externa que converte objetos em JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(s));
    }
}
