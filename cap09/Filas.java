import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

public class Filas {
    public static void main(String[] args) {
        // fila (FIFO): o primeiro a entrar é o primeiro a sair
        Queue<String> atendimento = new ArrayDeque<>();
        atendimento.offer("Senha 1");
        atendimento.offer("Senha 2");
        atendimento.offer("Senha 3");
        System.out.println("Próximo: " + atendimento.peek());   // olha sem remover
        while (!atendimento.isEmpty()) {
            System.out.println("Atendendo " + atendimento.poll()); // remove
        }

        // pilha (LIFO): o último a entrar é o primeiro a sair
        Deque<String> historico = new ArrayDeque<>();
        historico.push("página inicial");
        historico.push("produtos");
        historico.push("carrinho");
        String saiu = historico.pop();
        System.out.println("Voltar: " + saiu + " -> agora em " + historico.peek());

        // fila de prioridade: sai sempre o menor (ou o de maior prioridade)
        Queue<Integer> urgencias = new PriorityQueue<>();
        urgencias.addAll(java.util.List.of(5, 1, 4, 2, 3));
        System.out.print("Por prioridade: ");
        while (!urgencias.isEmpty()) {
            System.out.print(urgencias.poll() + " ");
        }
        System.out.println();
    }
}
