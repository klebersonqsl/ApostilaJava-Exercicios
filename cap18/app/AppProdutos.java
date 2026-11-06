import java.io.File;
import java.math.BigDecimal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AppProdutos {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        ProdutoDao dao = new ProdutoDao("produtos.db");
        if (dao.vazio()) {
            popular(dao);
        }
        SwingUtilities.invokeLater(() -> {
            TelaProdutos tela = new TelaProdutos();
            ProdutoController controle = new ProdutoController(tela, dao);
            tela.setVisible(true);
            controle.carregar();
        });
    }

    /** Dados iniciais para a demonstração. */
    static void popular(ProdutoDao dao) throws Exception {
        Object[][] dados = {
            {"Caneca de cerâmica", "Cozinha", "34,90", 25, "caneca.jpg"},
            {"Caderno 10 matérias", "Papelaria", "29,50", 60, "caderno.jpg"},
            {"Garrafa térmica 1L", "Esporte", "79,00", 12, "garrafa.jpg"},
            {"Fone de ouvido", "Eletrônicos", "149,90", 8, "fone.jpg"},
            {"Lapiseira 0,7mm", "Papelaria", "12,00", 140, null},
        };
        for (Object[] d : dados) {
            byte[] foto = d[4] == null ? null
                    : Imagens.miniaturaPng(new File("fotos", (String) d[4]), 240);
            BigDecimal preco = ProdutoDao.preco((String) d[2]);
            dao.salvar(new Produto(null, (String) d[0], (String) d[1], preco,
                    (Integer) d[3], foto));
        }
    }
}
