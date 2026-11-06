public class TesteRetangulo {
    public static void main(String[] args) {
        Retangulo sala = new Retangulo(5, 4);
        Retangulo quarto = new Retangulo(3.5);    // quadrado de lado 3,5

        System.out.printf("Sala: %.1f m², perímetro %.1f m%n",
                sala.area(), sala.perimetro());
        System.out.printf("Quarto: %.2f m²%n", quarto.area());
        System.out.println("A sala é maior que o quarto? " + sala.maiorQue(quarto));
    }
}
