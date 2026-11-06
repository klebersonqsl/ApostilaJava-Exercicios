package br.com.loja.web;

import br.com.loja.modelo.DocumentoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/** Upload de vários arquivos e download com o nome original. */
@WebServlet({"/documentos", "/documentos/baixar"})
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, maxRequestSize = 30 * 1024 * 1024)
public class DocumentoServlet extends HttpServlet {

    private final DocumentoDao dao = new DocumentoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            if (req.getServletPath().equals("/documentos/baixar")) {
                baixar(req, resp);
                return;
            }
            req.setAttribute("documentos", dao.listar());
            req.getRequestDispatcher("/WEB-INF/jsp/documentos/lista.jsp")
                    .forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int enviados = 0;
        try {
            for (Part parte : req.getParts()) {                 // todos os campos do form
                if (parte.getName().equals("arquivos") && parte.getSize() > 0) {
                    // alguns navegadores enviam o caminho inteiro: fica só o nome
                    String nome = Path.of(parte.getSubmittedFileName())
                            .getFileName().toString();
                    dao.salvar(nome, parte.getContentType(), parte.getSize(),
                            parte.getInputStream());
                    System.out.println("Upload: " + nome + ", " + parte.getSize() + " B");
                    enviados++;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        Flash.mensagem(req, enviados + " arquivo(s) enviado(s).");
        resp.sendRedirect(req.getContextPath() + "/documentos");
    }

    private void baixar(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Object[] doc = dao.conteudo(Long.parseLong(req.getParameter("id")));
        if (doc == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String nome = (String) doc[0];
        byte[] bytes = (byte[]) doc[2];
        String codificado = URLEncoder.encode(nome, StandardCharsets.UTF_8)
                .replace("+", "%20");
        String tipo = doc[1] == null ? "application/octet-stream" : (String) doc[1];
        resp.setContentType(tipo);
        resp.setContentLength(bytes.length);
        // attachment = "salvar como"; filename* permite acentos no nome do arquivo
        resp.setHeader("Content-Disposition",
                "attachment; filename*=UTF-8''" + codificado);
        resp.getOutputStream().write(bytes);
    }
}
