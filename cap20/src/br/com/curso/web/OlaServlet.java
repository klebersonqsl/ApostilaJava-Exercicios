package br.com.curso.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/ola")                       // a URL que este servlet atende
public class OlaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String nome = req.getParameter("nome");          // ?nome=Ana na URL
        if (nome == null || nome.isBlank()) {
            nome = "visitante";
        }
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang='pt-BR'><head><meta charset='UTF-8'>");
        out.println("<title>Olá</title>");
        out.println("<link rel='stylesheet' href='css/estilo.css'></head>");
        out.println("<body><header><h1>Curso Java Web</h1></header><main>");
        out.println("<h2>Olá, " + Html.escapar(nome) + "!</h2>");
        out.println("<p>Esta página foi gerada por um servlet às " + hora + ".</p>");
        out.println("<p>Idioma preferido do navegador: " + req.getLocale().toLanguageTag()
                + "</p>");
        out.println("</main></body></html>");
    }
}
