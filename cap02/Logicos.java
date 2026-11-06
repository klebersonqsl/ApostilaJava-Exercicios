public class Logicos {
    public static void main(String[] args) {
        int idade = 20;
        double renda = 2800.0;
        boolean temCadastro = true;

        // relacionais: ==  !=  >  <  >=  <=
        System.out.println("idade >= 18: " + (idade >= 18));
        System.out.println("idade == 20: " + (idade == 20));
        System.out.println("renda < 1500: " + (renda < 1500));

        // lógicos: && (E), || (OU), ! (NÃO)
        boolean podeFinanciar = idade >= 18 && renda >= 2000;
        boolean temDesconto = idade < 25 || renda < 1500;
        System.out.println("Pode financiar: " + podeFinanciar);
        System.out.println("Tem desconto: " + temDesconto);
        System.out.println("Sem cadastro: " + !temCadastro);

        // operador ternário: condição ? valorSeVerdadeiro : valorSeFalso
        String faixa = idade < 18 ? "menor de idade" : "maior de idade";
        System.out.println("Faixa: " + faixa);

        // curto-circuito: o lado direito não é avaliado se não precisar
        int divisor = 0;
        boolean seguro = divisor != 0 && 10 / divisor > 1;
        System.out.println("Divisão segura? " + seguro);
    }
}
