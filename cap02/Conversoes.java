public class Conversoes {
    public static void main(String[] args) {
        // conversão automática (alargamento): int cabe em double
        int inteiro = 42;
        double decimal = inteiro;
        System.out.println("int -> double: " + decimal);

        // conversão explícita (cast): pode perder informação
        double nota = 8.9;
        int notaInteira = (int) nota;   // descarta a parte decimal
        System.out.println("(int) 8.9 = " + notaInteira);

        long grande = 3_000_000_000L;
        int cortado = (int) grande;     // não cabe em int!
        System.out.println("(int) 3000000000L = " + cortado);

        // estouro (overflow) silencioso
        int max = Integer.MAX_VALUE;
        System.out.println("MAX_VALUE + 1 = " + (max + 1));

        // divisão: o tipo dos operandos define o resultado
        int total = 7, partes = 2;
        System.out.println("7 / 2 = " + (total / partes));
        System.out.println("(double) 7 / 2 = " + ((double) total / partes));

        // de texto para número e de número para texto
        String textoIdade = "25";
        int idade = Integer.parseInt(textoIdade);
        double altura = Double.parseDouble("1.82");
        String s = String.valueOf(idade + 1);
        System.out.println("idade = " + idade + ", altura = " + altura + ", s = " + s);

        // arredondamentos
        System.out.println("Math.round(2.5) = " + Math.round(2.5));
        System.out.println("Math.round(2.4) = " + Math.round(2.4));
    }
}
