import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Agendamento {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService agenda = Executors.newScheduledThreadPool(1);
        AtomicInteger execucoes = new AtomicInteger();

        // executa após 0 ms e depois a cada 300 ms
        Runnable verificar = () -> {
            int n = execucoes.incrementAndGet();
            System.out.println("Verificando pedidos... #" + n);
        };
        agenda.scheduleAtFixedRate(verificar, 0, 300, TimeUnit.MILLISECONDS);

        agenda.schedule(() -> System.out.println("Lembrete único após 1 segundo"),
                1, TimeUnit.SECONDS);

        Thread.sleep(1400);
        agenda.shutdown();                       // não aceita novas tarefas
        agenda.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("Agenda encerrada.");
    }
}
