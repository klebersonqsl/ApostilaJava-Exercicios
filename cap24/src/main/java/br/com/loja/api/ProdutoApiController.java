package br.com.loja.api;

import br.com.loja.modelo.Produto;
import br.com.loja.servico.ProdutoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/** API REST: as mesmas operações, em JSON. */
@RestController
@RequestMapping("/api/produtos")
public class ProdutoApiController {

    private final ProdutoService servico;

    public ProdutoApiController(ProdutoService servico) {
        this.servico = servico;
    }

    @GetMapping
    public List<ProdutoDTO> listar(@RequestParam(defaultValue = "") String q) {
        return servico.buscar(q).stream().map(ProdutoDTO::de).toList();
    }

    @GetMapping("/{id}")
    public ProdutoDTO buscar(@PathVariable long id) {
        return ProdutoDTO.de(servico.porId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoEntrada entrada,
                                            UriComponentsBuilder uri) {
        Produto salvo = servico.salvar(entrada.paraProduto(), null, null);
        URI endereco = uri.path("/api/produtos/{id}")
                .buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(endereco).body(ProdutoDTO.de(salvo));   // 201
    }

    @PutMapping("/{id}")
    public ProdutoDTO alterar(@PathVariable long id,
                              @Valid @RequestBody ProdutoEntrada entrada) {
        Produto p = entrada.paraProduto();
        p.setId(id);
        return ProdutoDTO.de(servico.salvar(p, null, null));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)                                     // 204
    public void excluir(@PathVariable long id) {
        servico.excluir(id);
    }
}
