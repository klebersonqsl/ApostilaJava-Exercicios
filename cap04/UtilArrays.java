import java.util.Arrays;

public class UtilArrays {
    public static void main(String[] args) {
        int[] valores = {42, 7, 19, 3, 25, 11};

        // imprimir um array diretamente mostra só um "endereço"
        System.out.println("println direto: " + valores);
        System.out.println("Arrays.toString: " + Arrays.toString(valores));

        Arrays.sort(valores);
        System.out.println("Ordenado: " + Arrays.toString(valores));

        int pos = Arrays.binarySearch(valores, 19);   // busca binária (array ordenado)
        System.out.println("O 19 está na posição " + pos);

        int[] copia = Arrays.copyOf(valores, 8);       // copia e aumenta o tamanho
        System.out.println("Cópia maior: " + Arrays.toString(copia));

        int[] meio = Arrays.copyOfRange(valores, 1, 4); // posições 1, 2 e 3
        System.out.println("Trecho: " + Arrays.toString(meio));

        char[] linha = new char[10];
        Arrays.fill(linha, '-');
        System.out.println("Preenchido: " + new String(linha));

        int[] outro = {3, 7, 11, 19, 25, 42};
        System.out.println("São iguais? " + Arrays.equals(valores, outro));

        String[] nomes = {"Pedro", "ana", "Lucas", "Beatriz"};
        Arrays.sort(nomes, String.CASE_INSENSITIVE_ORDER);
        System.out.println("Nomes: " + Arrays.toString(nomes));
    }
}
