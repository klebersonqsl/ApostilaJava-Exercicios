package br.com.loja.web;

import br.com.loja.modelo.Documento;
import br.com.loja.repositorio.DocumentoRepository;
import br.com.loja.servico.NaoEncontradoException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoRepository repositorio;

    public DocumentoController(DocumentoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("documentos", repositorio.findAllByOrderByNome());
        return "documentos/lista";
    }

    /** Vários arquivos no mesmo campo: <input type="file" name="arquivos" multiple> */
    @PostMapping
    public String enviar(@RequestParam List<MultipartFile> arquivos,
                         RedirectAttributes redirect) throws IOException {
        int enviados = 0;
        for (MultipartFile arquivo : arquivos) {
            if (!arquivo.isEmpty()) {
                String original = Objects.requireNonNullElse(
                        arquivo.getOriginalFilename(), "arquivo");
                String nome = Path.of(original).getFileName().toString();  // só o nome
                repositorio.save(new Documento(nome, arquivo.getContentType(),
                        arquivo.getBytes()));
                enviados++;
            }
        }
        redirect.addFlashAttribute("mensagem", enviados + " arquivo(s) enviado(s).");
        return "redirect:/documentos";
    }

    @GetMapping("/{id}/baixar")
    public ResponseEntity<byte[]> baixar(@PathVariable long id) {
        Documento d = repositorio.findById(id)
                .orElseThrow(() ->
                        new NaoEncontradoException("Documento " + id + " não existe."));
        // attachment = baixar; o Spring codifica nomes com acentos (filename*=UTF-8''...)
        ContentDisposition disposicao = ContentDisposition.attachment()
                .filename(d.getNome(), StandardCharsets.UTF_8)
                .build();
        MediaType tipo = d.getTipo() == null ? MediaType.APPLICATION_OCTET_STREAM
                                             : MediaType.parseMediaType(d.getTipo());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposicao.toString())
                .contentType(tipo)
                .body(d.getConteudo());
    }
}
