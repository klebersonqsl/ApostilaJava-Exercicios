public class BreakContinue {
    public static void main(String[] args) {
        // continue: pula para a próxima volta do laço
        System.out.print("Ímpares até 15: ");
        for (int i = 1; i <= 15; i++) {
            if (i % 2 == 0) {
                continue;
            }
            System.out.print(i + " ");
        }
        System.out.println();

        // break: sai do laço imediatamente
        int soma = 0;
        int n = 0;
        while (true) {
            n++;
            soma += n;
            if (soma > 100) {
                break;
            }
        }
        System.out.println("1 + 2 + ... + " + n + " = " + soma + " (passou de 100)");

        // break com rótulo: sai dos dois laços de uma vez
        externo:
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (i * j == 42) {
                    System.out.println("Primeiro par com produto 42: " + i + " x " + j);
                    break externo;
                }
            }
        }
    }
}
