package br.com.loja.web;

import br.com.loja.modelo.Banco;
import br.com.loja.modelo.Produto;
import br.com.loja.modelo.ProdutoDao;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

/** Executado quando a aplicação sobe: cria as tabelas e insere dados de exemplo. */
@WebListener
public class Inicializacao implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        try {
            Banco.criarTabelas();
            ProdutoDao dao = new ProdutoDao();
            if (dao.buscar("").isEmpty()) {
                inserir(dao, "Caneca de cerâmica", "Cozinha", "34.90", 25, "caneca.jpg");
                inserir(dao, "Caderno 10 matérias", "Papelaria", "29.50", 60,
                        "caderno.jpg");
                inserir(dao, "Fone de ouvido", "Eletrônicos", "149.90", 8, "fone.jpg");
                inserir(dao, "Lapiseira 0,7mm", "Papelaria", "12.00", 140, null);
            }
            System.out.println("Banco de dados pronto.");
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao iniciar o banco", e);
        }
    }

    private void inserir(ProdutoDao dao, String nome, String cat, String preco,
                         int estoque, String foto) throws Exception {
        Produto p = new Produto();
        p.setNome(nome);
        p.setCategoria(cat);
        p.setPreco(new BigDecimal(preco));
        p.setEstoque(estoque);
        byte[] bytes = foto == null ? null : Files.readAllBytes(Path.of("fotos", foto));
        dao.salvar(p, bytes, foto == null ? null : "image/jpeg");
    }
}
