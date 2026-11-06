package br.com.loja.api;

import br.com.loja.modelo.Produto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

/** O que a API devolve: só os dados públicos (sem os bytes da foto). */
@JsonPropertyOrder({"id", "nome", "categoria", "preco", "estoque", "temFoto"})
public record ProdutoDTO(Long id, String nome, String categoria, BigDecimal preco,
                         Integer estoque, boolean temFoto) {

    public static ProdutoDTO de(Produto p) {
        return new ProdutoDTO(p.getId(), p.getNome(), p.getCategoria(), p.getPreco(),
                p.getEstoque(), p.isTemFoto());
    }
}
