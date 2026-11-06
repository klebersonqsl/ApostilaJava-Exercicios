public class SwitchModerno {
    public static void main(String[] args) {
        String mes = "fevereiro";

        // switch com setas: sem break, sem fall-through, vários valores por case
        switch (mes) {
            case "dezembro", "janeiro", "fevereiro" -> System.out.println("Verão");
            case "março", "abril", "maio" -> System.out.println("Outono");
            case "junho", "julho", "agosto" -> System.out.println("Inverno");
            default -> System.out.println("Primavera");
        }

        // switch como expressão: devolve um valor
        int ano = 2024;
        int dias = switch (mes) {
            case "abril", "junho", "setembro", "novembro" -> 30;
            case "fevereiro" -> {
                boolean bissexto = (ano % 4 == 0 && ano % 100 != 0) || ano % 400 == 0;
                yield bissexto ? 29 : 28;   // yield devolve o valor de um bloco
            }
            default -> 31;
        };
        System.out.println(mes + " de " + ano + " tem " + dias + " dias.");
    }
}
