package br.com.loja.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Documento implements Serializable {
    private long id;
    private String nome;
    private String tipo;
    private long tamanho;
    private LocalDateTime enviadoEm;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public long getTamanho() { return tamanho; }
    public void setTamanho(long tamanho) { this.tamanho = tamanho; }
    public LocalDateTime getEnviadoEm() { return enviadoEm; }
    public void setEnviadoEm(LocalDateTime enviadoEm) { this.enviadoEm = enviadoEm; }

    /** Tamanho legível, para a página: "12,3 KB". */
    public String getTamanhoFormatado() {
        return tamanho < 1024 ? tamanho + " bytes"
                : String.format("%.1f KB", tamanho / 1024.0);
    }

    public String getDataFormatada() {
        return enviadoEm.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
