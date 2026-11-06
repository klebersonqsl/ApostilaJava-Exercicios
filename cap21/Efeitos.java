import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Efeitos {
    public static void main(String[] args) throws IOException {
        BufferedImage foto = ImageIO.read(new File("paisagem.jpg"));

        // 1) recorte: x, y, largura, altura
        BufferedImage recorte = foto.getSubimage(900, 250, 600, 400);
        ImageIO.write(recorte, "png", new File("recorte.png"));

        // 2) tons de cinza
        ColorSpace espacoCinza = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp cinza = new ColorConvertOp(espacoCinza, null);
        BufferedImage pb = cinza.filter(recorte, null);
        ImageIO.write(pb, "png", new File("cinza.png"));

        // 3) marca d'água: desenhando texto sobre a imagem
        BufferedImage marcada = new BufferedImage(recorte.getWidth(), recorte.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = marcada.createGraphics();
        g.drawImage(recorte, 0, 0, null);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 44));
        g.drawString("© Curso Java", 30, recorte.getHeight() - 30);
        g.dispose();
        ImageIO.write(marcada, "png", new File("marca.png"));

        System.out.println("Gerados: recorte.png, cinza.png e marca.png");
    }
}
