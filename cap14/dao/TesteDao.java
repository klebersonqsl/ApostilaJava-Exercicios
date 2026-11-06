import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteDao {
    public static void main(String[] args) {
        ProdutoDao dao = new ProdutoDaoJdbc();   // o resto só conhece a interface

        Produto novo = dao.salvar(new Produto(null, "Arroz tipo 1 5kg", "Mercearia",
                new BigDecimal("27.90"), 30, LocalDate.of(2026, 10, 20)));
        System.out.println("Salvo: " + novo);

        dao.buscarPorId(novo.id())
                .ifPresent(p -> System.out.println("Buscado: " + p.nome()));

        Produto alterado = new Produto(novo.id(), novo.nome(), novo.categoria(),
                new BigDecimal("25.90"), 28, novo.cadastro());
        System.out.println("Atualizou? " + dao.atualizar(alterado));

        System.out.println("Mercearia:");
        dao.buscarPorCategoria("Mercearia").forEach(p ->
                System.out.printf("  %-20s R$ %6.2f  estoque %d%n", p.nome(), p.preco(),
                        p.estoque()));

        System.out.println("Excluiu id 1? " + dao.excluir(1));
        System.out.println("Total cadastrado: " + dao.listar().size());
        System.out.println("Buscar id 1: " + dao.buscarPorId(1).map(Produto::nome)
                .orElse("não encontrado"));
    }
}
