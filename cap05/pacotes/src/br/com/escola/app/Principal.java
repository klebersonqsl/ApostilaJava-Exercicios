package br.com.escola.app;

import br.com.escola.util.Calculos;

import static java.lang.Math.max;   // import static: usa max(...) sem escrever Math.

public class Principal {

    public static void main(String[] args) {
        double m = Calculos.media(6.5, 8.0, 7.5);
        System.out.printf("Média: %.2f%n", m);
        System.out.println("Aprovado? " + Calculos.aprovado(m));
        System.out.println("Média mínima: " + Calculos.MEDIA_APROVACAO);
        System.out.println("Maior entre 3 e 8: " + max(3, 8));
    }
}
