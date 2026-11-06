package br.com.loja.web;

import jakarta.servlet.http.HttpServletRequest;

/** Mensagem que sobrevive a um redirecionamento: guardada na sessão e lida uma vez. */
public final class Flash {
    private Flash() {
    }

    public static void mensagem(HttpServletRequest req, String texto) {
        req.getSession().setAttribute("flash", texto);
    }
}
