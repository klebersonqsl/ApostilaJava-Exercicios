import java.math.BigDecimal;
import java.time.LocalDate;

public record Produto(Long id, String nome, String categoria, BigDecimal preco,
                      int estoque, LocalDate cadastro) {

    /** Cópia com o id preenchido (usada depois de inserir). */
    public Produto comId(long novoId) {
        return new Produto(novoId, nome, categoria, preco, estoque, cadastro);
    }
}
