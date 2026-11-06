package br.com.curso.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/restrito/painel")
public class PainelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // contador guardado NA SESSÃO: cada usuário tem o seu
        Integer visitas = (Integer) req.getSession().getAttribute("visitas");
        visitas = visitas == null ? 1 : visitas + 1;
        req.getSession().setAttribute("visitas", visitas);
        req.setAttribute("visitas", visitas);
        req.getRequestDispatcher("/WEB-INF/views/painel.jsp").forward(req, resp);
    }
}
