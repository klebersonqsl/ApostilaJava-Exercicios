import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ListaTarefas extends JFrame {

    // o modelo guarda os dados; a JList apenas os exibe
    private final DefaultListModel<String> modelo = new DefaultListModel<>();
    private final JList<String> lista = new JList<>(modelo);
    private final JTextField campo = new JTextField();
    private final JButton remover = new JButton("Remover");

    public ListaTarefas() {
        super("Tarefas");
        modelo.addElement("Estudar Swing");
        modelo.addElement("Revisar JDBC");

        JButton adicionar = new JButton("Adicionar");
        adicionar.addActionListener(e -> adicionar());
        campo.addActionListener(e -> adicionar());

        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        remover.setEnabled(false);
        // habilita "Remover" só quando há um item selecionado
        lista.addListSelectionListener(
                e -> remover.setEnabled(!lista.isSelectionEmpty()));
        remover.addActionListener(e -> modelo.remove(lista.getSelectedIndex()));

        JPanel topo = new JPanel(new BorderLayout(6, 6));
        topo.add(campo, BorderLayout.CENTER);
        topo.add(adicionar, BorderLayout.EAST);

        JPanel raiz = new JPanel(new BorderLayout(6, 6));
        raiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        raiz.add(topo, BorderLayout.NORTH);
        raiz.add(new JScrollPane(lista), BorderLayout.CENTER);
        raiz.add(remover, BorderLayout.SOUTH);
        add(raiz);
        setSize(340, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void adicionar() {
        String t = campo.getText().strip();
        if (!t.isEmpty()) {
            modelo.addElement(t);
            campo.setText("");
            campo.requestFocus();
        }
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(() -> new ListaTarefas().setVisible(true));
    }
}
