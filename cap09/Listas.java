import java.util.ArrayList;
import java.util.List;

public class Listas {
    public static void main(String[] args) {
        // List é a interface; ArrayList é a implementação mais usada
        List<String> tarefas = new ArrayList<>();
        tarefas.add("Estudar Java");
        tarefas.add("Pagar contas");
        tarefas.add("Ir à academia");
        tarefas.add(1, "Ligar para o banco");      // insere na posição 1

        System.out.println(tarefas);
        System.out.println("Quantidade: " + tarefas.size());
        System.out.println("Primeira: " + tarefas.get(0));
        System.out.println("Tem 'Pagar contas'? " + tarefas.contains("Pagar contas"));
        System.out.println("Posição de 'Ir à academia': "
                + tarefas.indexOf("Ir à academia"));

        tarefas.set(0, "Estudar coleções");          // substitui
        tarefas.remove("Pagar contas");              // remove pelo valor
        tarefas.remove(0);                           // remove pela posição

        for (String t : tarefas) {
            System.out.println("- " + t);
        }

        List<Integer> numeros = new ArrayList<>(List.of(8, 3, 15, 4, 42, 7));
        numeros.removeIf(n -> n % 2 == 0);          // remove os pares (lambda, cap. 10)
        numeros.sort(null);                           // ordem natural
        System.out.println("Ímpares ordenados: " + numeros);

        List<String> fixa = List.of("A", "B", "C");   // lista imutável
        try {
            fixa.add("D");
        } catch (UnsupportedOperationException e) {
            System.out.println("List.of não pode ser alterada.");
        }
    }
}
