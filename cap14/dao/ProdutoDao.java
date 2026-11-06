import java.util.List;
import java.util.Optional;

/** Operações de persistência de produtos, sem detalhes de SQL. */
public interface ProdutoDao {
    Produto salvar(Produto p);

    Optional<Produto> buscarPorId(long id);

    List<Produto> listar();

    List<Produto> buscarPorCategoria(String categoria);

    boolean atualizar(Produto p);

    boolean excluir(long id);
}
