package br.com.curso.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/inscricao")
public class InscricaoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String nome = req.getParameter("nome").strip();
        String email = req.getParameter("email").strip();
        String trilha = req.getParameter("trilha");
        // checkbox: o parâmetro só é enviado se estiver marcado
        boolean certificado = req.getParameter("certificado") != null;

        List<String> erros = new ArrayList<>();
        if (nome.length() < 3) {
            erros.add("Informe o nome completo.");
        }
        if (!email.contains("@")) {
            erros.add("Informe um e-mail válido.");
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang='pt-BR'><head><meta charset='UTF-8'>");
        out.println("<title>Inscrição</title>");
        out.println("<link rel='stylesheet' href='css/estilo.css'>");
        out.println("</head><body><header><h1>Semana de Tecnologia</h1></header><main>");
        if (erros.isEmpty()) {
            System.out.println("Nova inscrição: " + nome + " (" + trilha + ")");
            out.println("<h2>Inscrição confirmada</h2><div class='ok'>Obrigado, "
                    + Html.escapar(nome) + "! Você está inscrito na trilha <b>"
                    + Html.escapar(trilha) + "</b>"
                    + (certificado ? ", com certificado." : ", sem certificado.")
                    + "</div>");
        } else {
            out.println("<h2>Não foi possível concluir</h2><div class='erro'><ul>");
            for (String e : erros) {
                out.println("<li>" + e + "</li>");
            }
            out.println("</ul></div><a class='botao' href='formulario.html'>Voltar</a>");
        }
        out.println("</main></body></html>");
    }
}
