package br.com.loja.web;

import br.com.loja.modelo.Documento;
import br.com.loja.modelo.DocumentoDao;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

/** Controlador de documentos.xhtml: envio de vários arquivos e download. */
@Named("documentos")
@ViewScoped
public class DocumentosBean implements Serializable {

    @Inject
    private DocumentoDao dao;

    private transient List<Part> arquivos;   // h:inputFile multiple="true"
    private List<Documento> lista;

    @PostConstruct
    public void iniciar() throws SQLException {
        lista = dao.listar();                 // consulta uma vez, e não a cada getLista()
    }

    public String enviar() throws Exception {
        int enviados = 0;
        if (arquivos != null) {
            for (Part parte : arquivos) {
                String nome = Path.of(parte.getSubmittedFileName())
                        .getFileName().toString();
                dao.salvar(nome, parte.getContentType(), parte.getSize(),
                        parte.getInputStream());
                enviados++;
            }
        }
        Mensagens.infoAposRedirecionar(enviados + " arquivo(s) enviado(s).");
        return "documentos?faces-redirect=true";
    }

    /** Download: escreve o arquivo direto na resposta HTTP. */
    public void baixar(Documento d) throws SQLException, IOException {
        Object[] doc = dao.conteudo(d.getId());
        String nome = (String) doc[0];
        byte[] bytes = (byte[]) doc[2];
        String codificado = URLEncoder.encode(nome, StandardCharsets.UTF_8)
                .replace("+", "%20");

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();                       // descarta o que já foi escrito
        ec.setResponseContentType(doc[1] == null ? "application/octet-stream"
                                                 : (String) doc[1]);
        ec.setResponseContentLength(bytes.length);
        ec.setResponseHeader("Content-Disposition",
                "attachment; filename*=UTF-8''" + codificado);
        ec.getResponseOutputStream().write(bytes);
        fc.responseComplete();                    // avisa: a resposta já está pronta
    }

    public List<Documento> getLista() {
        return lista;
    }

    public List<Part> getArquivos() { return arquivos; }
    public void setArquivos(List<Part> arquivos) { this.arquivos = arquivos; }
}
