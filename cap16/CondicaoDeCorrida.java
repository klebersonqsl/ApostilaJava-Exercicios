import java.util.concurrent.atomic.AtomicInteger;

public class CondicaoDeCorrida {

    static int contadorSimples = 0;
    static int contadorSincronizado = 0;
    static final AtomicInteger contadorAtomico = new AtomicInteger();
    static final Object TRAVA = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable tarefa = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                contadorSimples++;                  // ler+somar+gravar: não atômico
                synchronized (TRAVA) {              // só uma thread por vez entra aqui
                    contadorSincronizado++;
                }
                contadorAtomico.incrementAndGet();  // operação atômica
            }
        };
        Thread a = new Thread(tarefa);
        Thread b = new Thread(tarefa);
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println("Esperado:      2000000");
        System.out.println("Simples:       " + contadorSimples
                + (contadorSimples == 2_000_000 ? "" : "   <- perdeu incrementos!"));
        System.out.println("Sincronizado:  " + contadorSincronizado);
        System.out.println("AtomicInteger: " + contadorAtomico.get());
    }
}
