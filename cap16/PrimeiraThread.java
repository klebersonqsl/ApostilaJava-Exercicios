public class PrimeiraThread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Início, na thread: " + Thread.currentThread().getName());

        Runnable tarefa = () -> {
            String nome = Thread.currentThread().getName();
            for (int i = 1; i <= 3; i++) {
                System.out.println(nome + " passo " + i);
                dormir(100);
            }
        };

        Thread t1 = new Thread(tarefa, "Trabalhadora-1");
        Thread t2 = new Thread(tarefa, "Trabalhadora-2");
        t1.start();          // start: executa em paralelo (run rodaria na mesma thread)
        t2.start();

        System.out.println("main continua enquanto as outras trabalham...");
        t1.join();           // espera t1 terminar
        t2.join();
        System.out.println("Todas terminaram.");
    }

    static void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
