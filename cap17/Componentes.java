import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Componentes {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(Componentes::criar);
    }

    static void criar() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 8, 8));   // 2 colunas
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        painel.add(new JLabel("JTextField:"));
        painel.add(new JTextField("texto de uma linha"));

        painel.add(new JLabel("JPasswordField:"));
        painel.add(new JPasswordField("segredo"));

        painel.add(new JLabel("JComboBox:"));
        painel.add(new JComboBox<>(new String[]{"Recife", "Salvador", "Fortaleza"}));

        painel.add(new JLabel("JSpinner:"));
        painel.add(new JSpinner(new SpinnerNumberModel(3, 1, 10, 1)));

        painel.add(new JLabel("JCheckBox:"));
        JCheckBox check = new JCheckBox("Receber novidades");
        check.setSelected(true);
        painel.add(check);

        painel.add(new JLabel("JRadioButton:"));
        JPanel radios = new JPanel();
        JRadioButton pf = new JRadioButton("Física", true);
        JRadioButton pj = new JRadioButton("Jurídica");
        ButtonGroup grupo = new ButtonGroup();      // só um do grupo fica marcado
        grupo.add(pf);
        grupo.add(pj);
        radios.add(pf);
        radios.add(pj);
        painel.add(radios);

        painel.add(new JLabel("JSlider:"));
        painel.add(new JSlider(0, 100, 70));

        painel.add(new JLabel("JTextArea:"));
        JTextArea area = new JTextArea("Texto de\nvárias linhas", 3, 20);
        painel.add(new JScrollPane(area));          // barra de rolagem quando precisar

        painel.add(new JLabel("JButton:"));
        painel.add(new JButton("Confirmar"));

        JFrame janela = new JFrame("Componentes básicos");
        janela.add(painel);
        janela.pack();                              // ajusta o tamanho ao conteúdo
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}
