package br.com.loja.web;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/** Atalhos para mensagens exibidas pelo <h:messages> do layout. */
public final class Mensagens {

    private Mensagens() {
    }

    public static void info(String texto) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    /** A mensagem sobrevive ao redirecionamento (fica no "flash"). */
    public static void infoAposRedirecionar(String texto) {
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        info(texto);
    }
}
