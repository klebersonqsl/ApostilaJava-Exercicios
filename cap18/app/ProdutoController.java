import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/** Controlador: liga a tela ao modelo e trata as ações do usuário. */
public class ProdutoController {

    private final TelaProdutos tela;
    private final ProdutoDao dao;
    private Long idEmEdicao;
    private byte[] fotoEmEdicao;

    public ProdutoController(TelaProdutos tela, ProdutoDao dao) {
        this.tela = tela;
        this.dao = dao;
        tela.novo.addActionListener(e -> limpar());
        tela.salvar.addActionListener(e -> salvar());
        tela.excluir.addActionListener(e -> excluir());
        tela.escolherFoto.addActionListener(e -> escolherFoto());
        tela.exportar.addActionListener(e -> exportar());
        tela.tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                editar(tela.selecionado());
            }
        });
        tela.busca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrar(); }
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            public void changedUpdate(DocumentEvent e) { }
        });
        tela.excluir.setEnabled(false);
    }

    private void filtrar() {
        tela.filtrar(tela.busca.getText());
    }

    /** Carrega os produtos em segundo plano, sem travar a tela. */
    public void carregar() {
        tela.status.setText("Carregando...");
        new SwingWorker<List<Produto>, Void>() {
            @Override
            protected List<Produto> doInBackground() throws Exception {   // outra thread
                return dao.listar();
            }

            @Override
            protected void done() {                                     // de volta na EDT
                try {
                    List<Produto> lista = get();
                    tela.modeloTabela.setProdutos(lista);
                    tela.status.setText(lista.size() + " produto(s) cadastrado(s)");
                } catch (Exception e) {
                    tela.mostrarErro("Erro ao carregar: " + e.getMessage());
                }
            }
        }.execute();
    }

    private void editar(Produto p) {
        if (p == null) {
            return;
        }
        idEmEdicao = p.id();
        fotoEmEdicao = p.foto();
        tela.nome.setText(p.nome());
        tela.categoria.setSelectedItem(p.categoria());
        tela.preco.setText(String.format("%.2f", p.preco()));
        tela.estoque.setValue(p.estoque());
        mostrarFoto();
        tela.excluir.setEnabled(true);
    }

    private void limpar() {
        idEmEdicao = null;
        fotoEmEdicao = null;
        tela.tabela.clearSelection();
        tela.nome.setText("");
        tela.preco.setText("");
        tela.estoque.setValue(0);
        tela.categoria.setSelectedIndex(0);
        mostrarFoto();
        tela.excluir.setEnabled(false);
        tela.nome.requestFocus();
    }

    private void salvar() {
        String nome = tela.nome.getText().strip();
        if (nome.isEmpty()) {
            tela.mostrarErro("Informe o nome do produto.");
            return;
        }
        BigDecimal preco;
        try {
            preco = ProdutoDao.preco(tela.preco.getText());
        } catch (NumberFormatException e) {
            tela.mostrarErro("Preço inválido: " + tela.preco.getText());
            return;
        }
        String cat = (String) tela.categoria.getSelectedItem();
        int qtd = (Integer) tela.estoque.getValue();
        Produto p = new Produto(idEmEdicao, nome, cat, preco, qtd, fotoEmEdicao);
        try {
            dao.salvar(p);
            limpar();
            carregar();
        } catch (Exception e) {
            tela.mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    private void excluir() {
        Produto p = tela.selecionado();
        if (p != null && tela.confirmar("Excluir \"" + p.nome() + "\"?")) {
            try {
                dao.excluir(p.id());
                limpar();
                carregar();
            } catch (Exception e) {
                tela.mostrarErro("Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private void escolherFoto() {
        JFileChooser seletor = new JFileChooser(new File("fotos"));
        seletor.setDialogTitle("Escolha a foto do produto");
        seletor.setFileFilter(
                new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif"));
        if (seletor.showOpenDialog(tela) == JFileChooser.APPROVE_OPTION) {
            try {
                fotoEmEdicao = Imagens.miniaturaPng(seletor.getSelectedFile(), 240);
                mostrarFoto();
            } catch (IOException e) {
                tela.mostrarErro(e.getMessage());
            }
        }
    }

    private void mostrarFoto() {
        if (fotoEmEdicao == null) {
            tela.foto.setIcon(null);
            tela.foto.setText("sem foto");
        } else {
            tela.foto.setText(null);
            tela.foto.setIcon(Imagens.icone(fotoEmEdicao, 140));
        }
    }

    private void exportar() {
        JFileChooser seletor = new JFileChooser();
        seletor.setSelectedFile(new File("produtos.csv"));
        if (seletor.showSaveDialog(tela) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File destino = seletor.getSelectedFile();
        try (PrintWriter out = new PrintWriter(destino, StandardCharsets.UTF_8)) {
            out.println("id;nome;categoria;preco;estoque");
            for (Produto p : dao.listar()) {
                out.printf("%d;%s;%s;%s;%d%n", p.id(), p.nome(), p.categoria(),
                        p.preco().toPlainString().replace('.', ','), p.estoque());
            }
            tela.status.setText("Exportado para " + destino.getName());
        } catch (Exception e) {
            tela.mostrarErro("Erro ao exportar: " + e.getMessage());
        }
    }
}
