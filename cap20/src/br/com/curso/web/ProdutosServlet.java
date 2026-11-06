package br.com.curso.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/** Controlador: busca os dados (modelo) e encaminha para a página JSP (visão). */
@WebServlet("/produtos")
public class ProdutosServlet extends HttpServlet {

    private final List<Produto> catalogo = List.of(
            new Produto(1, "Teclado mecânico", new BigDecimal("349.90"), 12),
            new Produto(2, "Mouse sem fio", new BigDecimal("89.90"), 40),
            new Produto(3, "Monitor 24\"", new BigDecimal("899.00"), 0),
            new Produto(4, "Webcam Full HD", new BigDecimal("219.50"), 7));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("produtos", catalogo);           // dados para a visão
        req.setAttribute("titulo", "Catálogo");
        req.getRequestDispatcher("/WEB-INF/views/produtos.jsp").forward(req, resp);
    }
}
