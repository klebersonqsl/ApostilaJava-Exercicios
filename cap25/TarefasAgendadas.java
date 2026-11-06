import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Tarefas repetidas e com atraso, sem tela: ScheduledExecutorService. */
public class TarefasAgendadas {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("HH:mm:ss.S");

    static void log(String texto) {
        System.out.println("[" + LocalTime.now().format(F) + "] " + texto);
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService agenda = Executors.newSingleThreadScheduledExecutor();
        log("Agendando tarefas");

        // a cada 1 segundo, começando agora
        agenda.scheduleAtFixedRate(() -> log("tique-taque"), 0, 1, TimeUnit.SECONDS);

        // uma única vez, daqui a 2,5 segundos
        agenda.schedule(() -> log("lembrete: fazer o backup!"),
                2500, TimeUnit.MILLISECONDS);

        Thread.sleep(3_200);                    // deixa o programa rodar por 3,2 s
        agenda.shutdown();                      // não aceita novas execuções
        agenda.awaitTermination(1, TimeUnit.SECONDS);
        log("Agenda encerrada");
    }
}
