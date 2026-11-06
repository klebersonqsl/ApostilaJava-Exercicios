public class TesteConta {
    public static void main(String[] args) {
        ContaBancaria conta = new ContaBancaria("1234-5", "  Helena Prado ");
        conta.depositar(500);
        conta.depositar(-20);
        conta.sacar(120);
        conta.sacar(1000);

        System.out.println("Conta " + conta.getNumero() + " de " + conta.getTitular());
        System.out.printf("Saldo: R$ %.2f%n", conta.getSaldo());

        // conta.saldo = 1_000_000;  // erro de compilação: saldo tem acesso private
    }
}
