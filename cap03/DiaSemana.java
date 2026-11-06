public class DiaSemana {
    public static void main(String[] args) {
        int dia = 3;

        // switch clássico: cada case termina com break
        switch (dia) {
            case 1:
                System.out.println("Domingo");
                break;
            case 2:
                System.out.println("Segunda-feira");
                break;
            case 3:
                System.out.println("Terça-feira");
                break;
            default:
                System.out.println("Outro dia");
        }

        // sem break, a execução "cai" nos cases seguintes (fall-through)
        switch (dia) {
            case 3:
                System.out.println("caso 3");
            case 4:
                System.out.println("caso 4 (também executou!)");
                break;
            case 5:
                System.out.println("caso 5");
        }
    }
}
