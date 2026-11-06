import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadsVirtuais {
    public static void main(String[] args) {
        AtomicInteger concluidas = new AtomicInteger();
        long inicio = System.currentTimeMillis();

        // uma thread virtual por tarefa: dá para criar milhares sem esgotar a memória
        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10_000; i++) {
                exec.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));   // simula esperar uma resposta
                    concluidas.incrementAndGet();
                    return null;
                });
            }
        }   // espera todas terminarem

        long ms = System.currentTimeMillis() - inicio;
        System.out.printf("%,d tarefas de 1 segundo concluídas%n", concluidas.get());
        System.out.println("Levou menos de 5 segundos no total? " + (ms < 5000));

        Thread v = Thread.ofVirtual().start(() ->
                System.out.println("Sou virtual? " + Thread.currentThread().isVirtual()));
        try {
            v.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
