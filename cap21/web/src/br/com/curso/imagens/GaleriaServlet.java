package br.com.curso.imagens;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/** Página com as miniaturas de todas as imagens da pasta "fotos". */
@WebServlet("/galeria")
public class GaleriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        List<Path> fotos;
        try (Stream<Path> arquivos = Files.list(MiniaturaServlet.PASTA)) {
            fotos = arquivos
                    .filter(p -> p.toString().toLowerCase().matches(".*\\.(jpe?g|png)"))
                    .sorted()
                    .toList();
        }
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String raiz = req.getContextPath();
        out.println("""
                <!DOCTYPE html>
                <html lang="pt-BR"><head><meta charset="UTF-8"><title>Galeria</title>
                <link rel="stylesheet" href="%s/css/estilo.css"></head>
                <body><header><h1>Galeria de imagens</h1></header><main>
                <h2>%d imagens</h2><div class="galeria">"""
                .formatted(raiz, fotos.size()));
        for (Path foto : fotos) {
            String nome = foto.getFileName().toString();
            String param = URLEncoder.encode(nome, StandardCharsets.UTF_8);
            out.printf("""
                    <figure><a href="%1$s/miniatura?nome=%2$s&largura=800">
                    <img src="%1$s/miniatura?nome=%2$s&largura=300" alt=""></a>
                    <figcaption>%3$s<br>%4$d KB</figcaption></figure>
                    """, raiz, param, escapar(nome), Files.size(foto) / 1024);
        }
        out.println("</div></main></body></html>");
    }

    /** Nomes de arquivo podem conter < ou &: escape antes de colocar no HTML. */
    private static String escapar(String texto) {
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
