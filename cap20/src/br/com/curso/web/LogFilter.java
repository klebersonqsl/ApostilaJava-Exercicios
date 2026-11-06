package br.com.curso.web;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Filtro: executa antes e depois de qualquer requisição que combine com o padrão. */
@WebFilter("/*")
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        long inicio = System.nanoTime();

        chain.doFilter(request, response);    // segue para o servlet ou o próximo filtro

        long ms = (System.nanoTime() - inicio) / 1_000_000;
        int status = ((HttpServletResponse) response).getStatus();
        if (!req.getRequestURI().contains("/css/")) {
            System.out.printf("[log] %s %s -> %d (%s ms)%n", req.getMethod(),
                    req.getRequestURI(), status, ms < 50 ? "< 50" : String.valueOf(ms));
        }
    }
}
