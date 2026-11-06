import java.math.BigDecimal;

/** Modelo: os dados de um produto. A foto é guardada como bytes (PNG). */
public record Produto(Long id, String nome, String categoria, BigDecimal preco,
                      int estoque, byte[] foto) {
}
