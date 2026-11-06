package br.com.loja.web;

import br.com.loja.modelo.Produto;
import br.com.loja.modelo.ProdutoDao;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/** Controlador da página produto.xhtml (inclusão e alteração). */
@Named("produtoForm")
@ViewScoped
public class ProdutoFormBean implements Serializable {

    private static final long TAMANHO_MAXIMO = 2 * 1024 * 1024;   // 2 MB

    @Inject
    private ProdutoDao dao;

    private Long id;                       // vem da URL: produto.xhtml?id=3
    private Produto produto = new Produto();
    private transient Part foto;           // o arquivo enviado (Part não é serializável)

    /** f:viewAction: carrega o produto antes de exibir a página. */
    public void carregar() throws SQLException, IOException {
        if (id == null) {
            return;                        // inclusão: produto vazio
        }
        Produto encontrado = dao.porId(id).orElse(null);
        if (encontrado == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().responseSendError(404, "Produto não encontrado");
            fc.responseComplete();
        } else {
            produto = encontrado;
        }
    }

    /** Validador do campo de foto: aceita só imagens de até 2 MB. */
    public void validarFoto(FacesContext ctx, UIComponent campo, Object valor) {
        Part arquivo = (Part) valor;
        if (arquivo == null || arquivo.getSize() == 0) {
            return;                        // foto é opcional
        }
        String tipo = arquivo.getContentType();
        if (tipo == null || !tipo.startsWith("image/")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Envie uma imagem (JPG, PNG ou GIF).", null));
        }
        if (arquivo.getSize() > TAMANHO_MAXIMO) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "A imagem deve ter no máximo 2 MB.", null));
        }
    }

    /** Ação do botão Salvar: só é chamada se todas as validações passarem. */
    public String salvar() throws SQLException, IOException {
        byte[] bytes = null;
        String tipo = null;
        if (foto != null && foto.getSize() > 0) {
            bytes = foto.getInputStream().readAllBytes();
            tipo = foto.getContentType();
        }
        dao.salvar(produto, bytes, tipo);
        Mensagens.infoAposRedirecionar("Produto \"" + produto.getNome() + "\" salvo.");
        return "produtos?faces-redirect=true";      // navegação com redirecionamento
    }

    public List<String> getCategorias() {
        return List.of("Cozinha", "Eletrônicos", "Esporte", "Papelaria");
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Produto getProduto() { return produto; }
    public Part getFoto() { return foto; }
    public void setFoto(Part foto) { this.foto = foto; }
}
