import java.util.concurrent.CompletableFuture;

public class Assincrono {

    static String buscarCliente(int id) {
        pausa(300);
        return "Cliente " + id + " (Ana Prado)";
    }

    static double buscarSaldo(int id) {
        pausa(400);
        return 1520.75;
    }

    static int buscarPontos(int id) {
        pausa(200);
        return 3400;
    }

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();

        // as três consultas começam ao mesmo tempo
        var cliente = CompletableFuture.supplyAsync(() -> buscarCliente(7));
        var saldo = CompletableFuture.supplyAsync(() -> buscarSaldo(7));
        var pontos = CompletableFuture.supplyAsync(() -> buscarPontos(7));

        // combina os resultados quando todos estiverem prontos
        CompletableFuture<String> resumo = cliente
                .thenCombine(saldo, (c, s) -> c + " | saldo R$ " + s)
                .thenCombine(pontos, (txt, p) -> txt + " | " + p + " pontos")
                .thenApply(String::toUpperCase);

        System.out.println(resumo.join());     // join espera e devolve o resultado
        long ms = System.currentTimeMillis() - inicio;
        System.out.println("Levou menos que a soma das três (900 ms)? " + (ms < 850));

        CompletableFuture<Integer> comErro = CompletableFuture
                .supplyAsync(() -> 10 / 0)
                .exceptionally(e -> {
                    System.out.println("Tratado: " + e.getCause().getMessage());
                    return -1;
                });
        System.out.println("Resultado com erro: " + comErro.join());
    }

    static void pausa(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
