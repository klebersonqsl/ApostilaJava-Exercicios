import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PrimeiraJanela {
    public static void main(String[] args) {
        // toda a criação da interface acontece na thread do Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Minha primeira janela");
            JLabel mensagem = new JLabel("Olá, Swing!", SwingConstants.CENTER);
            janela.add(mensagem);

            janela.setSize(360, 160);                               // largura x altura
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // fechar = encerrar
            janela.setLocationRelativeTo(null);                     // centraliza na tela
            janela.setVisible(true);                                // mostra
        });
    }
}
