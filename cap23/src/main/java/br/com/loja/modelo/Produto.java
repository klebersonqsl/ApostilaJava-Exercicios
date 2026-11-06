package br.com.loja.modelo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/** JavaBean com as regras de validação (Bean Validation) junto dos dados. */
public class Produto implements Serializable {

    private Long id;

    @NotBlank(message = "Informe o nome.")
    @Size(min = 3, max = 80, message = "O nome deve ter de 3 a 80 letras.")
    private String nome;

    @NotNull(message = "Escolha a categoria.")
    private String categoria;

    @NotNull(message = "Informe o preço.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    private BigDecimal preco;

    @PositiveOrZero(message = "O estoque não pode ser negativo.")
    private int estoque;

    private boolean temFoto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
    public boolean isTemFoto() { return temFoto; }
    public void setTemFoto(boolean temFoto) { this.temFoto = temFoto; }
}
