import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Layouts {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        SwingUtilities.invokeLater(() -> {
            // FlowLayout: um ao lado do outro, quebrando linha quando falta espaço
            JPanel fluxo = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
            for (String t : new String[]{"Novo", "Abrir", "Salvar", "Imprimir", "Sair"}) {
                fluxo.add(new JButton(t));
            }
            mostrar("FlowLayout", fluxo, 330, 130, 40, 60);

            // BorderLayout: cinco regiões
            JPanel bordas = new JPanel(new BorderLayout(6, 6));
            bordas.add(new JButton("NORTH"), BorderLayout.NORTH);
            bordas.add(new JButton("SOUTH"), BorderLayout.SOUTH);
            bordas.add(new JButton("WEST"), BorderLayout.WEST);
            bordas.add(new JButton("EAST"), BorderLayout.EAST);
            bordas.add(new JButton("CENTER"), BorderLayout.CENTER);
            mostrar("BorderLayout", bordas, 330, 200, 420, 60);

            // GridLayout: grade de células do mesmo tamanho
            JPanel grade = new JPanel(new GridLayout(3, 3, 4, 4));
            for (int i = 1; i <= 9; i++) {
                grade.add(new JButton(String.valueOf(i)));
            }
            mostrar("GridLayout(3, 3)", grade, 260, 200, 800, 60);
        });
    }

    static void mostrar(String titulo, JPanel conteudo, int l, int a, int x, int y) {
        JFrame f = new JFrame(titulo);
        f.add(conteudo);
        f.setSize(l, a);
        f.setLocation(x, y);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
