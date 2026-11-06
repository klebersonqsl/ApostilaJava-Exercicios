package br.com.loja.web;

import br.com.loja.modelo.ProdutoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/** Entrega a foto de um produto: <img src="/loja/foto?id=3"> */
@WebServlet("/foto")
public class FotoServlet extends HttpServlet {

    private final ProdutoDao dao = new ProdutoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Object[] foto = dao.foto(Long.parseLong(req.getParameter("id")));
            if (foto == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            byte[] bytes = (byte[]) foto[0];
            resp.setContentType((String) foto[1]);             // image/jpeg, image/png...
            resp.setContentLength(bytes.length);
            resp.setHeader("Cache-Control", "max-age=3600");    // guardada por 1 hora
            resp.getOutputStream().write(bytes);
        } catch (SQLException | NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
