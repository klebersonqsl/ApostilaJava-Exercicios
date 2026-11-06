import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapaConcorrente {
    public static void main(String[] args) {
        String[] pedidos = {"café", "chá", "café", "suco", "café", "chá", "água"};
        Map<String, Integer> contagem = new ConcurrentHashMap<>();

        try (ExecutorService pool = Executors.newFixedThreadPool(4)) {
            for (int rodada = 0; rodada < 1000; rodada++) {
                for (String p : pedidos) {
                    // merge é atômico no ConcurrentHashMap
                    pool.submit(() -> contagem.merge(p, 1, Integer::sum));
                }
            }
        }
        System.out.println(new TreeMap<>(contagem));
    }
}
