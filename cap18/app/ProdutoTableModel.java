import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/** Adapta uma lista de produtos para a JTable. */
public class ProdutoTableModel extends AbstractTableModel {

    private static final String[] COLUNAS =
            {"ID", "Nome", "Categoria", "Preço", "Estoque"};
    private List<Produto> produtos = new ArrayList<>();

    public void setProdutos(List<Produto> produtos) {
        this.produtos = new ArrayList<>(produtos);
        fireTableDataChanged();          // avisa a tabela que os dados mudaram
    }

    public Produto getProduto(int linha) {
        return produtos.get(linha);
    }

    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return COLUNAS[coluna];
    }

    @Override
    public Class<?> getColumnClass(int coluna) {   // alinhamento, formato e ordenação
        return switch (coluna) {
            case 0 -> Long.class;
            case 3 -> BigDecimal.class;
            case 4 -> Integer.class;
            default -> String.class;
        };
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Produto p = produtos.get(linha);
        return switch (coluna) {
            case 0 -> p.id();
            case 1 -> p.nome();
            case 2 -> p.categoria();
            case 3 -> p.preco();
            case 4 -> p.estoque();
            default -> null;
        };
    }
}
