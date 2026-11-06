package br.com.curso.imagens;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/** As mesmas técnicas da seção "Miniaturas", agora devolvendo os bytes do JPEG. */
public final class Imagens {

    private Imagens() {
    }

    public static byte[] miniaturaJpeg(Path arquivo, int largura) throws IOException {
        BufferedImage original = ImageIO.read(arquivo.toFile());
        if (original == null) {
            throw new IOException("Formato não suportado: " + arquivo.getFileName());
        }
        int l = Math.min(largura, original.getWidth());        // nunca amplia
        int a = original.getHeight() * l / original.getWidth();
        BufferedImage mini = new BufferedImage(l, a, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = mini.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(original, 0, 0, l, a, null);
        g.dispose();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ImageIO.write(mini, "jpg", bytes);
        return bytes.toByteArray();
    }
}
