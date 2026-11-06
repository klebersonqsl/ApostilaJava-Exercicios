public class Escopo {
    public static void main(String[] args) {
        int saldo = 500;
        int saque = 200;

        if (saque <= saldo) {
            int restante = saldo - saque;   // só existe dentro deste bloco
            System.out.println("Saque liberado. Restam " + restante);
        }
        // System.out.println(restante);  // erro: restante não existe aqui

        // sem chaves, o if controla apenas a próxima instrução
        if (saque > 1000)
            System.out.println("Saque alto!");
        System.out.println("Esta linha sempre aparece.");
    }
}
