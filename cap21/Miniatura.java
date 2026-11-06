import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class Miniatura {

    /** Redimensiona mantendo a proporção. */
    static BufferedImage redimensionar(BufferedImage original, int largura) {
        int altura = original.getHeight() * largura / original.getWidth();
        BufferedImage nova =
                new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = nova.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(original, 0, 0, largura, altura, null);
        g.dispose();
        return nova;
    }

    /** Grava um JPEG com qualidade entre 0.0 e 1.0. */
    static void gravarJpeg(BufferedImage img, File destino, float qualidade)
            throws IOException {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(qualidade);
        try (ImageOutputStream out = ImageIO.createImageOutputStream(destino)) {
            writer.setOutput(out);
            writer.write(null, new IIOImage(img, null, null), param);
        } finally {
            writer.dispose();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedImage foto = ImageIO.read(new File("paisagem.jpg"));
        BufferedImage mini = redimensionar(foto, 400);
        System.out.println("Miniatura: " + mini.getWidth() + " x " + mini.getHeight());

        ImageIO.write(mini, "png", new File("mini.png"));           // PNG: sem perdas
        gravarJpeg(mini, new File("mini-alta.jpg"), 0.9f);
        gravarJpeg(mini, new File("mini-baixa.jpg"), 0.2f);

        for (String nome : new String[]{"paisagem.jpg", "mini.png", "mini-alta.jpg",
                "mini-baixa.jpg"}) {
            double kb = Files.size(Path.of(nome)) / 1024.0;
            System.out.printf("%-15s %7.1f KB%n", nome, kb);
        }
    }
}
