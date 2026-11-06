public class SemCasoBase {

    public static void main(String[] args) {
        System.out.println(somaAte(5));
    }

    static int somaAte(int n) {
        return n + somaAte(n - 1);   // esqueceu o caso base!
    }
}
