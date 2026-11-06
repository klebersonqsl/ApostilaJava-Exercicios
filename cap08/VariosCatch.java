public class VariosCatch {
    public static void main(String[] args) {
        String[] entradas = {"10", "0", "abc", null, "7"};
        for (String e : entradas) {
            dividirCemPor(e);
        }
    }

    static void dividirCemPor(String texto) {
        try {
            int n = Integer.parseInt(texto.strip());
            System.out.println("100 / " + n + " = " + (100 / n));
        } catch (ArithmeticException e) {
            System.out.println("Divisão por zero: " + e.getMessage());
        } catch (NumberFormatException | NullPointerException e) {   // multi-catch
            System.out.println("Entrada inválida (" + e.getClass().getSimpleName() + ")");
        } finally {
            System.out.println("  -> processado: " + texto);  // executa SEMPRE
        }
    }
}
