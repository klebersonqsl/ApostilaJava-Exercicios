import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class InfoImagem {
    public static void main(String[] args) throws IOException {
        Path arq = Path.of("paisagem.jpg");
        BufferedImage img = ImageIO.read(arq.toFile());
        if (img == null) {
            System.out.println("Formato não suportado");
            return;
        }
        System.out.println("Arquivo: " + arq + " (" + Files.size(arq) / 1024 + " KB)");
        System.out.println("Dimensões: " + img.getWidth() + " x " + img.getHeight());

        int rgb = img.getRGB(100, 50);                // cor de um pixel
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        System.out.printf("Pixel (100, 50): R=%d G=%d B=%d%n", r, g, b);

        System.out.println("Formatos que o Java lê: "
                + Arrays.toString(ImageIO.getReaderFormatNames()));
    }
}
