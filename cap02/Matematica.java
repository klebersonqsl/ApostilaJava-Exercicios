public class Matematica {
    public static void main(String[] args) {
        System.out.println("2 elevado a 10: " + Math.pow(2, 10));
        System.out.println("Raiz de 81: " + Math.sqrt(81));
        System.out.println("Valor absoluto de -7: " + Math.abs(-7));
        System.out.println("Maior entre 4 e 9: " + Math.max(4, 9));
        System.out.println("Menor entre 4 e 9: " + Math.min(4, 9));
        System.out.println("Teto de 4.1: " + Math.ceil(4.1));
        System.out.println("Piso de 4.9: " + Math.floor(4.9));
        System.out.println("PI: " + Math.PI);

        // área de um círculo de raio 3, com duas casas
        double area = Math.PI * Math.pow(3, 2);
        System.out.printf("Área do círculo: %.2f%n", area);

        // número aleatório entre 1 e 6 (um dado)
        int dado = (int) (Math.random() * 6) + 1;
        System.out.println("Dado sorteado entre 1 e 6: " + (dado >= 1 && dado <= 6));
    }
}
