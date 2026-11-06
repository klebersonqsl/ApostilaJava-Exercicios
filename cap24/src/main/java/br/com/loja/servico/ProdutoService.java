package br.com.loja.servico;

import br.com.loja.modelo.Produto;
import br.com.loja.repositorio.ProdutoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Regras de negócio do cadastro. Os controladores (HTML e REST) usam esta classe. */
@Service
public class ProdutoService {

    private final ProdutoRepository repositorio;

    // injeção pelo construtor: o Spring passa o repositório ao criar o serviço
    public ProdutoService(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional(readOnly = true)
    public List<Produto> buscar(String texto) {
        return repositorio.buscar(texto == null ? "" : texto.strip());
    }

    @Transactional(readOnly = true)
    public Produto porId(long id) {
        return repositorio.findById(id)
                .orElseThrow(() ->
                        new NaoEncontradoException("Produto " + id + " não existe."));
    }

    /** Inclui ou altera; a foto só é trocada quando uma nova é enviada. */
    @Transactional
    public Produto salvar(Produto produto, byte[] foto, String tipoFoto) {
        String nome = produto.getNome();
        boolean repetido = produto.getId() == null
                ? repositorio.existsByNomeIgnoreCase(nome)
                : repositorio.existsByNomeIgnoreCaseAndIdNot(nome, produto.getId());
        if (repetido) {
            throw new RegraDeNegocioException("nome", "Já existe produto com esse nome.");
        }
        if (foto != null) {
            produto.setFoto(foto);
            produto.setFotoTipo(tipoFoto);
        } else if (produto.getId() != null) {
            Produto atual = porId(produto.getId());          // mantém a foto atual
            produto.setFoto(atual.getFoto());
            produto.setFotoTipo(atual.getFotoTipo());
        }
        return repositorio.save(produto);
    }

    @Transactional
    public void excluir(long id) {
        repositorio.delete(porId(id));
    }
}
