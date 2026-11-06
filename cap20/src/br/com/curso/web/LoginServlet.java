package br.com.curso.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, jakarta.servlet.ServletException {
        if (req.getServletPath().equals("/logout")) {
            req.getSession().invalidate();                    // encerra a sessão
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, jakarta.servlet.ServletException {
        String usuario = req.getParameter("usuario");
        String senha = req.getParameter("senha");
        if ("maria".equals(usuario) && "java123".equals(senha)) {
            HttpSession sessao = req.getSession();      // cria (ou recupera) a sessão
            sessao.setAttribute("usuarioLogado", usuario);
            resp.sendRedirect(req.getContextPath() + "/restrito/painel");   // PRG
        } else {
            req.setAttribute("erro", "Usuário ou senha inválidos.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
