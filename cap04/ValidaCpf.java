public class ValidaCpf {
    public static void main(String[] args) {
        String[] testes = {"529.982.247-25", "123.456.789-00", "111.111.111-11"};
        for (String cpf : testes) {
            System.out.println(cpf + " -> " + (valido(cpf) ? "válido" : "inválido"));
        }
    }

    static boolean valido(String cpf) {
        String num = cpf.replaceAll("[^0-9]", "");      // mantém só os dígitos
        if (num.length() != 11 || num.chars().distinct().count() == 1) {
            return false;                               // tamanho errado ou todos iguais
        }
        int[] d = new int[11];
        for (int i = 0; i < 11; i++) {
            d[i] = num.charAt(i) - '0';                 // converte o char em número
        }
        return d[9] == digito(d, 9) && d[10] == digito(d, 10);
    }

    /** Calcula o dígito verificador a partir das 'qtd' primeiras posições. */
    static int digito(int[] d, int qtd) {
        int soma = 0;
        for (int i = 0; i < qtd; i++) {
            soma += d[i] * (qtd + 1 - i);
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}
