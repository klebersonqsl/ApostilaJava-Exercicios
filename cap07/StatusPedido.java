public enum StatusPedido {
    // cada constante chama o construtor do enum
    NOVO("Aguardando pagamento", false),
    PAGO("Pagamento confirmado", false),
    ENVIADO("Em transporte", false),
    ENTREGUE("Entregue ao cliente", true),
    CANCELADO("Cancelado", true);

    private final String descricao;
    private final boolean finalizado;

    StatusPedido(String descricao, boolean finalizado) {
        this.descricao = descricao;
        this.finalizado = finalizado;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public StatusPedido proximo() {
        return switch (this) {
            case NOVO -> PAGO;
            case PAGO -> ENVIADO;
            case ENVIADO -> ENTREGUE;
            case ENTREGUE, CANCELADO -> this;
        };
    }
}
