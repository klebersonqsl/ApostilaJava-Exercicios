package br.com.loja.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Trata erros de todos os controladores HTML. */
@ControllerAdvice
public class ErrosWeb {

    /** Arquivo maior que spring.servlet.multipart.max-file-size. */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String arquivoGrande(RedirectAttributes redirect) {
        redirect.addFlashAttribute("erro", "Arquivo grande demais: o limite é 10 MB.");
        return "redirect:/documentos";
    }
}
