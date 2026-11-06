package br.com.loja.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;

/** Entidade JPA: cada objeto corresponde a uma linha da tabela "produto". */
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe o nome.")
    @Size(min = 3, max = 80, message = "O nome deve ter de 3 a 80 letras.")
    @Column(nullable = false, length = 80)
    private String nome;

    @NotBlank(message = "Escolha a categoria.")
    @Column(nullable = false, length = 30)
    private String categoria;

    @NotNull(message = "Informe o preço.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @NumberFormat(pattern = "#,##0.00")          // "1.234,50" no formulário
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "Informe o estoque.")
    @PositiveOrZero(message = "O estoque não pode ser negativo.")
    private Integer estoque;

    @Lob
    @Column(length = 5_000_000)
    private byte[] foto;

    private String fotoTipo;

    public Produto() {
    }

    public Produto(String nome, String categoria, String preco, int estoque) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = new BigDecimal(preco);
        this.estoque = estoque;
    }

    public boolean isTemFoto() {
        return foto != null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
    public byte[] getFoto() { return foto; }
    public void setFoto(byte[] foto) { this.foto = foto; }
    public String getFotoTipo() { return fotoTipo; }
    public void setFotoTipo(String fotoTipo) { this.fotoTipo = fotoTipo; }
}
