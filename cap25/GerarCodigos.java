import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import java.nio.file.Files;
import java.nio.file.Path;

public class GerarCodigos {
    public static void main(String[] args) throws Exception {
        Path qr = Path.of("qrcode.png");
        Codigos.qrCode("https://www.exemplo.com.br/pedido/2026-00153", 300, qr);

        Path contato = Path.of("contato.png");   // vCard: o celular oferece salvar
        Codigos.qrCode("""
                BEGIN:VCARD
                VERSION:3.0
                N:Lopes;Kleberson
                FN:Kleberson Queiti Souza Lopes
                TITLE:Professor e Desenvolvedor
                EMAIL:professor@exemplo.com.br
                END:VCARD""", 300, contato);

        Path ean = Path.of("ean13.png");         // 12 dígitos + dígito verificador
        Codigos.codigoDeBarras("7891234567895", BarcodeFormat.EAN_13, 360, 120, ean);

        Path code128 = Path.of("code128.png");   // letras, números e símbolos
        Codigos.codigoDeBarras("PED-2026-00153", BarcodeFormat.CODE_128, 360, 120,
                code128);

        for (Path p : new Path[]{qr, contato, ean, code128}) {
            Result lido = Codigos.ler(p);
            String texto = lido.getText().replace("\n", " | ");
            System.out.printf("%-12s %6d bytes  %-12s %s%n", p, Files.size(p),
                    lido.getBarcodeFormat(), texto.length() > 48
                            ? texto.substring(0, 45) + "..." : texto);
        }

        try {                                    // o EAN-13 confere o verificador
            Codigos.codigoDeBarras("7891234567890", BarcodeFormat.EAN_13, 360, 120,
                    Path.of("errado.png"));
        } catch (IllegalArgumentException e) {
            System.out.println("EAN-13 inválido: " + e.getMessage());
        }
    }
}
