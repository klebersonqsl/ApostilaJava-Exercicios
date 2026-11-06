public class TesteEnum {
    public static void main(String[] args) {
        StatusPedido s = StatusPedido.NOVO;
        while (!s.isFinalizado()) {
            System.out.println(s + " -> " + s.getDescricao());
            s = s.proximo();
        }
        System.out.println(s + " -> " + s.getDescricao());

        System.out.println("Todos os status:");
        for (StatusPedido st : StatusPedido.values()) {
            System.out.println("  " + st.ordinal() + " " + st.name());
        }
        StatusPedido lido = StatusPedido.valueOf("ENVIADO");   // de texto para enum
        System.out.println("Lido do texto: " + lido + ", igual a ENVIADO? "
                + (lido == StatusPedido.ENVIADO));
    }
}
