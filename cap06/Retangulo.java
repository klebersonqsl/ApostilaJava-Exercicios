public class Retangulo {
    double largura;
    double altura;

    // construtor: mesmo nome da classe, sem tipo de retorno
    Retangulo(double largura, double altura) {
        this.largura = largura;   // this.largura é o atributo; largura é o parâmetro
        this.altura = altura;
    }

    // construtor sobrecarregado: um quadrado
    Retangulo(double lado) {
        this(lado, lado);         // chama o outro construtor
    }

    double area() {
        return largura * altura;
    }

    double perimetro() {
        return 2 * (largura + altura);
    }

    boolean maiorQue(Retangulo outro) {
        return this.area() > outro.area();
    }
}
