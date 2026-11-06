public class Recursos {

    // qualquer classe que implemente AutoCloseable pode ser usada no try-with-resources
    static class Conexao implements AutoCloseable {
        private final String nome;

        Conexao(String nome) {
            this.nome = nome;
            System.out.println("Abrindo " + nome);
        }

        void enviar(String msg) {
            if (msg.isEmpty()) {
                throw new IllegalStateException("mensagem vazia");
            }
            System.out.println(nome + " enviou: " + msg);
        }

        @Override
        public void close() {
            System.out.println("Fechando " + nome);
        }
    }

    public static void main(String[] args) {
        // os recursos são fechados automaticamente, na ordem inversa da abertura
        try (Conexao a = new Conexao("banco"); Conexao b = new Conexao("fila")) {
            a.enviar("olá");
            b.enviar("");                 // lança exceção...
            System.out.println("não chega aqui");
        } catch (IllegalStateException e) {
            // ...que só chega aqui depois de tudo fechado
            System.out.println("Erro tratado: " + e.getMessage());
        }
    }
}
