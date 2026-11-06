package br.com.curso.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Protege tudo o que fica em /restrito/*: sem login, redireciona para o login. */
@WebFilter("/restrito/*")
public class AutenticacaoFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException {
        HttpSession sessao = req.getSession(false);   // false: não cria se não existir
        boolean logado = sessao != null && sessao.getAttribute("usuarioLogado") != null;
        if (logado) {
            chain.doFilter(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
