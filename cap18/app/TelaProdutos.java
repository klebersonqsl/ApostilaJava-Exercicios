import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

/** Visão: só monta e exibe a tela. Não acessa o banco nem tem regras de negócio. */
public class TelaProdutos extends JFrame {

    final ProdutoTableModel modeloTabela = new ProdutoTableModel();
    final JTable tabela = new JTable(modeloTabela);
    final TableRowSorter<ProdutoTableModel> ordenador =
            new TableRowSorter<>(modeloTabela);
    final JTextField busca = new JTextField(18);

    final JTextField nome = new JTextField(20);
    final JComboBox<String> categoria = new JComboBox<>(
            new String[]{"Cozinha", "Papelaria", "Eletrônicos", "Esporte"});
    final JTextField preco = new JTextField(8);
    final JSpinner estoque = new JSpinner(new SpinnerNumberModel(0, 0, 99_999, 1));
    final JLabel foto = new JLabel("sem foto", SwingConstants.CENTER);

    final JButton novo = new JButton("Novo");
    final JButton salvar = new JButton("Salvar");
    final JButton excluir = new JButton("Excluir");
    final JButton escolherFoto = new JButton("Escolher foto...");
    final JButton exportar = new JButton("Exportar CSV...");
    final JLabel status = new JLabel(" ");

    public TelaProdutos() {
        super("Cadastro de produtos");
        tabela.setRowSorter(ordenador);                    // clique no cabeçalho ordena
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(24);
        tabela.getColumnModel().getColumn(0).setMaxWidth(45);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(170);
        // preços formatados como moeda brasileira, alinhados à direita
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        tabela.setDefaultRenderer(BigDecimal.class, new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object valor) {
                setHorizontalAlignment(RIGHT);
                setText(valor == null ? "" : moeda.format(valor));
            }
        });

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Buscar:"));
        topo.add(busca);
        topo.add(exportar);

        foto.setPreferredSize(new Dimension(150, 150));
        foto.setBorder(BorderFactory.createEtchedBorder());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Produto"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;
        String[] rotulos = {"Nome:", "Categoria:", "Preço (R$):", "Estoque:"};
        java.awt.Component[] campos = {nome, categoria, preco, estoque};
        for (int i = 0; i < rotulos.length; i++) {
            c.gridx = 0;
            c.gridy = i;
            c.fill = GridBagConstraints.NONE;
            form.add(new JLabel(rotulos[i]), c);
            c.gridx = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            form.add(campos[i], c);
        }
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 4;
        form.add(foto, c);
        c.gridy = 4;
        c.gridheight = 1;
        form.add(escolherFoto, c);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(novo);
        botoes.add(excluir);
        botoes.add(salvar);

        JPanel sul = new JPanel(new BorderLayout());
        sul.add(form, BorderLayout.CENTER);
        sul.add(botoes, BorderLayout.SOUTH);

        JPanel raiz = new JPanel(new BorderLayout(6, 6));
        raiz.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        raiz.add(topo, BorderLayout.NORTH);
        raiz.add(new JScrollPane(tabela), BorderLayout.CENTER);
        raiz.add(sul, BorderLayout.SOUTH);
        add(raiz, BorderLayout.CENTER);
        status.setBorder(BorderFactory.createEmptyBorder(2, 8, 4, 8));
        add(status, BorderLayout.SOUTH);

        setSize(640, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /** Linha selecionada no modelo (convertendo a posição por causa da ordenação). */
    Produto selecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return null;
        }
        return modeloTabela.getProduto(tabela.convertRowIndexToModel(linha));
    }

    void filtrar(String texto) {
        // (?i) = ignora maiúsculas; procura nas colunas 1 (nome) e 2 (categoria)
        String regex = "(?i)" + java.util.regex.Pattern.quote(texto);
        ordenador.setRowFilter(texto.isBlank() ? null
                : RowFilter.regexFilter(regex, 1, 2));
    }

    void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção",
                JOptionPane.WARNING_MESSAGE);
    }

    boolean confirmar(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Confirmação",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
