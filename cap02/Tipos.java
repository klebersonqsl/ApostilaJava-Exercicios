public class Tipos {
    public static void main(String[] args) {
        byte b = 120;
        short s = 32_000;
        int i = 2_000_000_000;
        long populacao = 8_100_000_000L;   // L no fim: literal do tipo long
        float preco = 19.90f;               // f no fim: literal do tipo float
        double saldo = 1_250.75;
        char letra = 'J';                   // aspas simples: um único caractere
        boolean ativo = false;

        System.out.println("byte: " + b + "  short: " + s + "  int: " + i);
        System.out.println("long: " + populacao);
        System.out.println("float: " + preco + "  double: " + saldo);
        System.out.println("char: " + letra + "  boolean: " + ativo);

        System.out.println("Maior int:  " + Integer.MAX_VALUE);
        System.out.println("Menor int:  " + Integer.MIN_VALUE);
        System.out.println("Maior long: " + Long.MAX_VALUE);

        // outras bases numéricas
        int hexa = 0xFF;        // 255 em hexadecimal
        int binario = 0b1010;   // 10 em binário
        System.out.println("0xFF = " + hexa + ", 0b1010 = " + binario);

        // char é um número (código Unicode)
        char c = 'A';
        System.out.println("Código de 'A': " + (int) c + ", próximo: " + (char) (c + 1));
    }
}
