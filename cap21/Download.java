import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class Download {

    static void baixar(HttpClient cliente, String url, Path destino)
            throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).build();
        HttpResponse<InputStream> resp = cliente.send(req,
                HttpResponse.BodyHandlers.ofInputStream());

        if (resp.statusCode() != 200) {
            resp.body().close();
            throw new IOException("Falha (HTTP " + resp.statusCode() + "): " + url);
        }
        long tamanho = resp.headers().firstValueAsLong("Content-Length").orElse(-1);
        long recebidos = 0;
        int ultimoPercentual = -1;
        try (InputStream in = resp.body();
             OutputStream out = Files.newOutputStream(destino)) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
                recebidos += n;
                int pct = (int) (recebidos * 100 / tamanho);
                if (pct / 25 != ultimoPercentual / 25) {      // mostra a cada 25%
                    System.out.printf("  %3d%% (%,d de %,d bytes)%n",
                            pct, recebidos, tamanho);
                    ultimoPercentual = pct;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient cliente = HttpClient.newHttpClient();
        Path destino = Path.of("baixada.jpg");

        System.out.println("Baixando paisagem.jpg...");
        baixar(cliente, "http://localhost:8080/arquivos/paisagem.jpg", destino);
        BufferedImage img = ImageIO.read(destino.toFile());
        System.out.println("Imagem baixada: " + img.getWidth() + " x " + img.getHeight());

        try {
            baixar(cliente, "http://localhost:8080/arquivos/nada.pdf", Path.of("x.pdf"));
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
