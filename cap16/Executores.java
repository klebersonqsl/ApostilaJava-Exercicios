import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Executores {

    // Callable: como Runnable, mas devolve um resultado
    static Callable<Long> somaDosPrimos(int inicio, int fim) {
        return () -> {
            long soma = 0;
            for (int n = Math.max(inicio, 2); n <= fim; n++) {
                if (ehPrimo(n)) soma += n;
            }
            System.out.printf("  [%s] faixa %,d-%,d concluída%n",
                    Thread.currentThread().getName(), inicio, fim);
            return soma;
        };
    }

    static boolean ehPrimo(int n) {
        for (int d = 2; (long) d * d <= n; d++) {
            if (n % d == 0) return false;
        }
        return true;
    }

    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        // um pool fixo de 4 threads reaproveitadas para todas as tarefas
        try (ExecutorService pool = Executors.newFixedThreadPool(4)) {
            List<Future<Long>> resultados = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int ini = i * 2_500_000 + 1;
                resultados.add(pool.submit(somaDosPrimos(ini, ini + 2_499_999)));
            }
            long total = 0;
            for (Future<Long> f : resultados) {
                total += f.get();             // get espera o resultado ficar pronto
            }
            System.out.printf("Soma dos primos até 10 milhões: %,d%n", total);
        }   // ExecutorService é AutoCloseable (Java 19+): espera terminar e encerra
    }
}
