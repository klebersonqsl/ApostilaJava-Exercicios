package br.com.loja.repositorio;

import br.com.loja.modelo.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** Só a interface: o Spring Data cria a implementação ao iniciar a aplicação. */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // consultas criadas a partir do nome do método
    List<Produto> findByCategoriaOrderByNome(String categoria);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);

    // consultas em JPQL: usam a entidade e seus atributos, não a tabela
    @Query("""
            select p from Produto p
            where lower(p.nome) like lower(concat('%', :texto, '%'))
               or lower(p.categoria) like lower(concat('%', :texto, '%'))
            order by p.nome""")
    List<Produto> buscar(String texto);

    @Query("select p from Produto p where p.estoque < :minimo order by p.estoque")
    List<Produto> comEstoqueAbaixoDe(int minimo);
}
