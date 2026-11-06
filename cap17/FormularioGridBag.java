import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FormularioGridBag {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(FormularioGridBag::criar);
    }

    static void criar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);          // espaço em volta de cada componente

        rotulo(p, c, "Nome:", 0);
        campo(p, c, new JTextField(25), 0, 3);

        rotulo(p, c, "E-mail:", 1);
        campo(p, c, new JTextField(), 1, 3);

        rotulo(p, c, "Cidade:", 2);
        campo(p, c, new JTextField(), 2, 1);
        c.gridx = 2;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        p.add(new JLabel("UF:"), c);
        campo(p, c, new JComboBox<>(new String[]{"PE", "BA", "SP", "RJ"}), 2, 1, 3);

        rotulo(p, c, "Observações:", 3);
        c.anchor = GridBagConstraints.NORTHEAST;
        JTextArea obs = new JTextArea(4, 20);
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;    // ocupa o espaço extra nas duas direções
        p.add(new JScrollPane(obs), c);

        JPanel botoes = new JPanel();
        botoes.add(new JButton("Salvar"));
        botoes.add(new JButton("Cancelar"));
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        p.add(botoes, c);

        JFrame f = new JFrame("Cadastro de cliente");
        f.add(p);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    static void rotulo(JPanel p, GridBagConstraints c, String texto, int linha) {
        c.gridx = 0;
        c.gridy = linha;
        c.gridwidth = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;          // rótulos alinhados à direita
        p.add(new JLabel(texto), c);
    }

    static void campo(JPanel p, GridBagConstraints c, java.awt.Component comp, int linha,
                      int largura) {
        campo(p, c, comp, linha, largura, 1);
    }

    static void campo(JPanel p, GridBagConstraints c, java.awt.Component comp, int linha,
                      int largura, int coluna) {
        c.gridx = coluna;
        c.gridy = linha;
        c.gridwidth = largura;
        c.weightx = 1;                               // a coluna dos campos cresce
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        p.add(comp, c);
    }
}
