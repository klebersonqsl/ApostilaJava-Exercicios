// interface: um contrato. Quem implementa promete ter estes métodos.
public interface Tributavel {
    double ALIQUOTA_PADRAO = 0.10;      // constante: implicitamente public static final

    double valorBase();                     // método abstrato

    default double imposto() {              // método default: tem implementação
        return valorBase() * ALIQUOTA_PADRAO;
    }
}
