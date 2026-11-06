package br.com.curso.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/contador")
public class CicloDeVidaServlet extends HttpServlet {

    // um único objeto atende TODAS as requisições: atributos são compartilhados
    private final AtomicInteger acessos = new AtomicInteger();

    @Override
    public void init() {
        System.out.println("[contador] init: servlet criado (uma única vez)");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int n = acessos.incrementAndGet();
        String thread = Thread.currentThread().getName();
        System.out.println("[contador] doGet na thread " + thread);
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getWriter().println("Esta página foi acessada " + n + " vez(es).");
    }

    @Override
    public void destroy() {
        System.out.println("[contador] destroy: servlet removido");
    }
}
