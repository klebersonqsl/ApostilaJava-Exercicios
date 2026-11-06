import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/** Funções de imagem usadas pela tela. */
public final class Imagens {

    private Imagens() {
    }

    /** Lê um arquivo, reduz para no máximo 'lado' pixels e devolve os bytes em PNG. */
    public static byte[] miniaturaPng(File arquivo, int lado) throws IOException {
        BufferedImage original = ImageIO.read(arquivo);
        if (original == null) {
            throw new IOException("Formato não suportado: " + arquivo.getName());
        }
        double escala = Math.min(1.0, (double) lado / Math.max(original.getWidth(),
                original.getHeight()));
        int l = (int) (original.getWidth() * escala);
        int a = (int) (original.getHeight() * escala);
        BufferedImage mini = new BufferedImage(l, a, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = mini.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(original, 0, 0, l, a, null);
        g.dispose();
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        ImageIO.write(mini, "png", saida);
        return saida.toByteArray();
    }

    /** Converte bytes em um ícone do tamanho pedido, para exibir em um JLabel. */
    public static ImageIcon icone(byte[] bytes, int lado) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
            return new ImageIcon(img.getScaledInstance(lado, -1, Image.SCALE_SMOOTH));
        } catch (IOException | NullPointerException e) {
            return null;
        }
    }
}
