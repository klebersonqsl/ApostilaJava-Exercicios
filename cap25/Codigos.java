import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import javax.imageio.ImageIO;

/** Gera e lê QR Codes e códigos de barras com a biblioteca ZXing. */
public final class Codigos {

    private Codigos() {
    }

    /** QR Code quadrado, com correção de erros média (lido mesmo ~15% danificado). */
    public static void qrCode(String texto, int lado, Path arquivo)
            throws WriterException, IOException {
        Map<EncodeHintType, Object> opcoes = Map.of(
                EncodeHintType.CHARACTER_SET, "UTF-8",          // acentos corretos
                EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M,
                EncodeHintType.MARGIN, 2);                      // borda branca (módulos)
        BitMatrix matriz = new MultiFormatWriter()
                .encode(texto, BarcodeFormat.QR_CODE, lado, lado, opcoes);
        MatrixToImageWriter.writeToPath(matriz, "PNG", arquivo);
    }

    /** Código de barras linear: EAN-13 (produtos), CODE-128 (texto livre) etc. */
    public static void codigoDeBarras(String texto, BarcodeFormat formato, int largura,
                                      int altura, Path arquivo)
            throws WriterException, IOException {
        BitMatrix matriz = new MultiFormatWriter()
                .encode(texto, formato, largura, altura,
                        Map.of(EncodeHintType.MARGIN, 10));
        MatrixToImageWriter.writeToPath(matriz, "PNG", arquivo);
    }

    /** Lê qualquer formato suportado a partir de uma imagem. */
    public static Result ler(Path arquivo) throws Exception {
        BufferedImage imagem = ImageIO.read(arquivo.toFile());
        BinaryBitmap bitmap = new BinaryBitmap(
                new HybridBinarizer(new BufferedImageLuminanceSource(imagem)));
        return new MultiFormatReader().decode(bitmap,
                Map.of(DecodeHintType.TRY_HARDER, Boolean.TRUE));
    }
}
