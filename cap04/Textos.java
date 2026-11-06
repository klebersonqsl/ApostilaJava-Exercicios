public class Textos {
    public static void main(String[] args) {
        String frase = "  Java é uma linguagem orientada a objetos  ";
        String s = frase.strip();   // remove espaços do início e do fim

        System.out.println("[" + s + "]");
        System.out.println("Tamanho: " + s.length());
        System.out.println("Maiúsculas: " + s.toUpperCase());
        System.out.println("Primeiro caractere: " + s.charAt(0));
        System.out.println("Posição de \"linguagem\": " + s.indexOf("linguagem"));
        System.out.println("Trecho (0 a 4): " + s.substring(0, 4));
        System.out.println("A partir da posição 11: " + s.substring(11));
        System.out.println("Contém \"objetos\"? " + s.contains("objetos"));
        System.out.println("Começa com \"Java\"? " + s.startsWith("Java"));
        System.out.println("Termina com \".\"? " + s.endsWith("."));
        System.out.println("Trocando: " + s.replace("Java", "Kotlin"));
        System.out.println("Vazia? " + "".isEmpty() + "  Em branco? " + "   ".isBlank());
        System.out.println("Repetida: " + "ab".repeat(3));

        // split: divide em partes; join: junta
        String csv = "Ana;23;Recife;PE";
        String[] partes = csv.split(";");
        System.out.println("Partes: " + partes.length + " -> cidade = " + partes[2]);
        System.out.println("Juntando: " + String.join(" | ", partes));

        // percorrendo caractere a caractere
        int vogais = 0;
        for (char ch : s.toLowerCase().toCharArray()) {
            if ("aeiouáéíóúâêôãõ".indexOf(ch) >= 0) {
                vogais++;
            }
        }
        System.out.println("Vogais: " + vogais);
    }
}
