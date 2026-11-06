public class Construtor {
    public static void main(String[] args) {
        // String é imutável: cada + cria um objeto novo
        String s = "Java";
        s.toUpperCase();                 // o resultado é descartado!
        System.out.println(s);           // continua "Java"
        s = s.toUpperCase();             // é preciso guardar o retorno
        System.out.println(s);

        // StringBuilder: texto que pode ser alterado, ideal em laços
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append(i);
            if (i < 5) {
                sb.append(", ");
            }
        }
        System.out.println("Lista: " + sb);

        sb.insert(0, "[").append("]");
        System.out.println("Com colchetes: " + sb);
        System.out.println("Invertido: " + new StringBuilder("roma").reverse());

        sb.setLength(0);                 // limpa
        sb.append("Olá").append(' ').append("mundo").append('!');
        System.out.println(sb.toString() + " (" + sb.length() + " caracteres)");
    }
}
