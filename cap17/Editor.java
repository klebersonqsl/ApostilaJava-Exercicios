import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Editor extends JFrame {

    private static final int CTRL = InputEvent.CTRL_DOWN_MASK;
    private final JTextArea texto = new JTextArea();
    private final JLabel status = new JLabel(" ");

    public Editor() {
        super("Editor de notas");
        setJMenuBar(criarMenu());

        JToolBar barra = new JToolBar();
        barra.add(new JButton("Novo"));
        barra.add(new JButton("Abrir"));
        barra.add(new JButton("Salvar"));
        barra.addSeparator();
        JButton maiusc = new JButton("MAIÚSCULAS");
        maiusc.addActionListener(e -> texto.setText(texto.getText().toUpperCase()));
        barra.add(maiusc);

        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        // DocumentListener: avisa a cada alteração do texto
        texto.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { atualizarStatus(); }
            public void removeUpdate(DocumentEvent e) { atualizarStatus(); }
            public void changedUpdate(DocumentEvent e) { atualizarStatus(); }
        });
        status.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));

        add(barra, BorderLayout.NORTH);
        add(new JScrollPane(texto), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
        setSize(520, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        atualizarStatus();
    }

    private JMenuBar criarMenu() {
        JMenu arquivo = new JMenu("Arquivo");
        arquivo.setMnemonic('A');                           // Alt+A abre o menu
        JMenuItem novo = new JMenuItem("Novo");
        novo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, CTRL));    // Ctrl+N
        novo.addActionListener(e -> texto.setText(""));
        JMenuItem salvar = new JMenuItem("Salvar");
        salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, CTRL));  // Ctrl+S
        JMenuItem sair = new JMenuItem("Sair");
        sair.addActionListener(e -> dispose());
        arquivo.add(novo);
        arquivo.add(salvar);
        arquivo.addSeparator();
        arquivo.add(sair);

        JMenu ajuda = new JMenu("Ajuda");
        JMenuItem sobre = new JMenuItem("Sobre");
        sobre.addActionListener(e -> JOptionPane.showMessageDialog(this, "Editor 1.0"));
        ajuda.add(sobre);

        JMenuBar barra = new JMenuBar();
        barra.add(arquivo);
        barra.add(ajuda);
        return barra;
    }

    private void atualizarStatus() {
        String t = texto.getText();
        int palavras = t.isBlank() ? 0 : t.strip().split("\\s+").length;
        status.setText(t.length() + " caracteres, " + palavras + " palavras");
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(() -> new Editor().setVisible(true));
    }
}
