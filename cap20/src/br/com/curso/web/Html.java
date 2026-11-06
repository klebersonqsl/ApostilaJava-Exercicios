package br.com.curso.web;

/** Escapa caracteres especiais para evitar que texto do usuário vire HTML (XSS). */
public final class Html {
    private Html() {
    }

    public static String escapar(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
