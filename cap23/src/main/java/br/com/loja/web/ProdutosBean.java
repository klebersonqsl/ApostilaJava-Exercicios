package br.com.loja.web;

import br.com.loja.modelo.Produto;
import br.com.loja.modelo.ProdutoDao;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/** Controlador da página produtos.xhtml (lista, busca e exclusão). */
@Named("produtos")              // nome usado nas expressões: #{produtos.lista}
@ViewScoped                     // vive enquanto o usuário estiver nesta página
public class ProdutosBean implements Serializable {

    @Inject
    private ProdutoDao dao;     // o CDI entrega a instância pronta

    private String filtro;
    private List<Produto> lista;

    @PostConstruct              // executado uma vez, depois das injeções
    public void iniciar() throws SQLException {
        buscar();
    }

    /** Chamado pelo f:ajax a cada tecla digitada na busca. */
    public void buscar() throws SQLException {
        lista = dao.buscar(filtro);
    }

    public void excluir(Produto p) throws SQLException {
        dao.excluir(p.getId());
        Mensagens.info("Produto \"" + p.getNome() + "\" excluído.");
        buscar();
    }

    public String getFiltro() { return filtro; }
    public void setFiltro(String filtro) { this.filtro = filtro; }
    public List<Produto> getLista() { return lista; }
}
