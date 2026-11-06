public class Apresentacao {
    public static void main(String[] args) {
        // println escreve o texto e pula para a próxima linha
        System.out.println("=== Ficha do aluno ===");

        // print escreve sem pular linha
        System.out.print("Nome: ");
        System.out.println("Ana Souza");

        // \t insere uma tabulação e \n uma quebra de linha
        System.out.println("Curso:\tJava\nTurma:\t2026-A");

        // printf formata valores: %s texto, %d inteiro, %.2f decimal com 2 casas
        System.out.printf("Idade: %d anos%n", 19);
        System.out.printf("Média: %.2f%n", 8.756);
        System.out.printf("%s tirou %.1f na prova%n", "Ana", 9.25);
    }
}
