package br.com.loja.modelo;

import java.math.BigDecimal;

/** JavaBean: getters e setters permitem usar ${produto.nome} nas JSPs. */
public class Produto {
    private Long id;
    private String nome;
    private String categoria;
    private BigDecimal preco;
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
