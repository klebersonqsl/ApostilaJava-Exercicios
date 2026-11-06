package br.com.loja.api;

import br.com.loja.modelo.Produto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/** O que a API recebe no POST e no PUT, com as mesmas regras do formulário. */
public record ProdutoEntrada(
        @NotBlank(message = "Informe o nome.")
        @Size(min = 3, max = 80, message = "O nome deve ter de 3 a 80 letras.")
        String nome,
        @NotBlank(message = "Escolha a categoria.")
        String categoria,
        @NotNull(message = "Informe o preço.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal preco,
        @NotNull(message = "Informe o estoque.")
        @PositiveOrZero(message = "O estoque não pode ser negativo.")
        Integer estoque) {

    public Produto paraProduto() {
        Produto p = new Produto();
        p.setNome(nome);
        p.setCategoria(categoria);
        p.setPreco(preco);
        p.setEstoque(estoque);
        return p;
    }
}
