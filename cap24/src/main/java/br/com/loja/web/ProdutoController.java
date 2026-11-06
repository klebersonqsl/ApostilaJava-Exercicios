package br.com.loja.web;

import br.com.loja.modelo.Produto;
import br.com.loja.servico.ProdutoService;
import br.com.loja.servico.RegraDeNegocioException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Controlador das páginas HTML do cadastro de produtos. */
@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService servico;

    public ProdutoController(ProdutoService servico) {
        this.servico = servico;
    }

    /** Só estes campos podem ser preenchidos pelo formulário (a foto não). */
    @InitBinder("produto")
    void configurar(WebDataBinder binder) {
        binder.setAllowedFields("id", "nome", "categoria", "preco", "estoque");
    }

    /** Disponível em todas as páginas deste controlador. */
    @ModelAttribute("categorias")
    List<String> categorias() {
        return List.of("Cozinha", "Eletrônicos", "Esporte", "Papelaria");
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("produtos", servico.buscar(q));
        model.addAttribute("q", q);
        return "produtos/lista";              // templates/produtos/lista.html
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable long id, Model model) {
        model.addAttribute("produto", servico.porId(id));
        return "produtos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("produto") Produto produto,
                         BindingResult erros,
                         @RequestParam(required = false) MultipartFile arquivo,
                         RedirectAttributes redirect) throws IOException {
        boolean temArquivo = arquivo != null && !arquivo.isEmpty();
        if (temArquivo && !ehImagem(arquivo)) {
            erros.rejectValue("foto", "foto.invalida", "Envie uma imagem (JPG ou PNG).");
        }
        if (erros.hasErrors()) {
            return "produtos/form";            // reexibe o formulário com as mensagens
        }
        try {
            servico.salvar(produto, temArquivo ? arquivo.getBytes() : null,
                    temArquivo ? arquivo.getContentType() : null);
        } catch (RegraDeNegocioException e) {
            erros.rejectValue(e.getCampo(), "regra", e.getMessage());
            return "produtos/form";
        }
        redirect.addFlashAttribute("mensagem",
                "Produto \"" + produto.getNome() + "\" salvo.");
        return "redirect:/produtos";           // Post-Redirect-Get
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable long id, RedirectAttributes redirect) {
        servico.excluir(id);
        redirect.addFlashAttribute("mensagem", "Produto excluído.");
        return "redirect:/produtos";
    }

    /** A imagem do produto: <img th:src="@{/produtos/{id}/foto(id=${p.id})}"> */
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> foto(@PathVariable long id) {
        Produto p = servico.porId(id);
        if (!p.isTemFoto()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(p.getFotoTipo()))
                .cacheControl(CacheControl.maxAge(Duration.ofHours(1)))
                .body(p.getFoto());
    }

    private static boolean ehImagem(MultipartFile arquivo) {
        String tipo = arquivo.getContentType();
        return tipo != null && tipo.startsWith("image/");
    }
}
