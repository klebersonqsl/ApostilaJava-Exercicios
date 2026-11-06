public class Recursao {

    public static void main(String[] args) {
        System.out.println("5! = " + fatorial(5));
        System.out.print("Fibonacci: ");
        for (int i = 0; i < 12; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();
        System.out.println("2^10 = " + potencia(2, 10));
        contagem(3);
    }

    // n! = n * (n-1)!   e   0! = 1 (caso base)
    static long fatorial(int n) {
        if (n <= 1) {
            return 1;               // caso base: para a recursão
        }
        return n * fatorial(n - 1); // chamada recursiva com um problema menor
    }

    static int fibonacci(int n) {
        if (n < 2) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    static long potencia(long base, int exp) {
        return exp == 0 ? 1 : base * potencia(base, exp - 1);
    }

    static void contagem(int n) {
        if (n == 0) {
            System.out.println("Fogo!");
            return;
        }
        System.out.println(n + "...");
        contagem(n - 1);
    }
}
