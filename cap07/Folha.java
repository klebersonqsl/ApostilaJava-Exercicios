public class Folha {
    public static void main(String[] args) {
        Vendedor v = new Vendedor("Paula", 2500);
        v.registrarVenda(18_000);
        v.registrarVenda(7_500);

        // polimorfismo: variáveis do tipo Funcionario guardam qualquer subclasse
        Funcionario[] equipe = {
            new Funcionario("Jorge", 2200),
            new Gerente("Sílvia", 6000, 1800),
            v
        };

        double total = 0;
        for (Funcionario f : equipe) {
            System.out.println(f);                 // toString chama calcularSalario()
            total += f.calcularSalario();          // cada objeto usa a SUA versão
        }
        System.out.printf("Total da folha: R$ %.2f%n", total);

        // instanceof com padrão: testa o tipo e já converte
        for (Funcionario f : equipe) {
            if (f instanceof Vendedor vend) {
                vend.registrarVenda(1000);
                System.out.println(vend.getNome() + " ganhou mais uma venda.");
            }
        }
    }
}
