package br.com.curso.imagens;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Gera miniaturas sob demanda: /imagens/miniatura?nome=paisagem.jpg&largura=300 */
@WebServlet("/miniatura")
public class MiniaturaServlet extends HttpServlet {

    static final Path PASTA = Path.of("fotos").toAbsolutePath().normalize();

    // cada miniatura é gerada uma vez e guardada na memória (chave: nome e largura)
    private final Map<String, byte[]> cache = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nome = req.getParameter("nome");
        int largura;
        try {
            largura = Integer.parseInt(req.getParameter("largura"));
        } catch (NumberFormatException e) {
            largura = 200;                                    // padrão
        }
        if (nome == null || largura < 50 || largura > 1200) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros inválidos");
            return;
        }
        // nunca use o nome vindo do usuário sem conferir se ele continua dentro da pasta
        Path arquivo = PASTA.resolve(nome).normalize();
        if (!arquivo.startsWith(PASTA) || !Files.isRegularFile(arquivo)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        final int l = largura;
        byte[] jpeg;
        try {
            jpeg = cache.computeIfAbsent(nome + "@" + l, k -> gerar(arquivo, l));
        } catch (UncheckedIOException e) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Não é imagem");
            return;
        }
        resp.setContentType("image/jpeg");
        resp.setContentLength(jpeg.length);
        resp.setHeader("Cache-Control", "max-age=86400");      // guardada por 1 dia
        resp.getOutputStream().write(jpeg);
    }

    private byte[] gerar(Path arquivo, int largura) {
        try {
            byte[] jpeg = Imagens.miniaturaJpeg(arquivo, largura);
            log("Miniatura gerada: " + arquivo.getFileName() + " com " + largura + " px, "
                    + jpeg.length / 1024 + " KB");
            return jpeg;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
