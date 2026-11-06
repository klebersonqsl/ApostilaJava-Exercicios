package br.com.loja.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;

@Entity
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    private long tamanho;
    private LocalDateTime enviadoEm;

    @Lob
    private byte[] conteudo;

    protected Documento() {             // exigido pelo JPA
    }

    public Documento(String nome, String tipo, byte[] conteudo) {
        this.nome = nome;
        this.tipo = tipo;
        this.conteudo = conteudo;
        this.tamanho = conteudo.length;
        this.enviadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public long getTamanho() { return tamanho; }
    public LocalDateTime getEnviadoEm() { return enviadoEm; }
    public byte[] getConteudo() { return conteudo; }
}
