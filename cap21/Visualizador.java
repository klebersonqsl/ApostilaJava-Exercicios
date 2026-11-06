import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Visualizador {
    public static void main(String[] args) throws IOException {
        BufferedImage foto = ImageIO.read(new File("paisagem.jpg"));
        // getScaledInstance: forma simples de reduzir para exibição
        Image reduzida = foto.getScaledInstance(480, -1, Image.SCALE_SMOOTH);

        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Visualizador - paisagem.jpg");
            JLabel imagem = new JLabel(new ImageIcon(reduzida));
            String tamanho = foto.getWidth() + " x " + foto.getHeight() + " pixels";
            JLabel legenda = new JLabel(tamanho, SwingConstants.CENTER);
            legenda.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
            janela.add(imagem, BorderLayout.CENTER);
            janela.add(legenda, BorderLayout.SOUTH);
            janela.pack();
            janela.setLocationRelativeTo(null);
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setVisible(true);
        });
    }
}
