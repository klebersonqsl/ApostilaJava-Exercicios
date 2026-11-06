package br.com.escola.util;

/**
 * Funções de cálculo usadas no sistema escolar.
 */
public class Calculos {

    public static final double MEDIA_APROVACAO = 7.0;

    /**
     * Calcula a média aritmética das notas.
     *
     * @param notas uma ou mais notas
     * @return a média, ou 0 se nenhuma nota for informada
     */
    public static double media(double... notas) {
        if (notas.length == 0) {
            return 0;
        }
        double soma = 0;
        for (double n : notas) {
            soma += n;
        }
        return soma / notas.length;
    }

    /** Diz se a média é suficiente para aprovação. */
    public static boolean aprovado(double media) {
        return media >= MEDIA_APROVACAO;
    }
}
