import java.util.Arrays;

public class Passagem {

    public static void main(String[] args) {
        int x = 10;
        dobrar(x);
        System.out.println("x depois de dobrar(x): " + x);       // não mudou
        x = dobrado(x);
        System.out.println("x = dobrado(x): " + x);               // mudou pelo retorno

        int[] valores = {1, 2, 3};
        dobrarTodos(valores);
        System.out.println("array depois: " + Arrays.toString(valores));  // mudou!

        String nome = "ana";
        maiusculas(nome);
        System.out.println("nome depois: " + nome);               // String é imutável
    }

    static void dobrar(int n) {        // recebe uma CÓPIA do valor
        n = n * 2;
    }

    static int dobrado(int n) {
        return n * 2;
    }

    static void dobrarTodos(int[] a) { // recebe uma cópia da REFERÊNCIA ao mesmo array
        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * 2;
        }
    }

    static void maiusculas(String s) {
        s = s.toUpperCase();           // cria outra String; a original não muda
    }
}
