import java.time.LocalDate;
import java.util.List;

public class Dados {
    static List<Venda> vendas() {
        return List.of(
            new Venda("Ana", "Sul", "Notebook", 2, 3800.0, LocalDate.of(2026, 1, 12)),
            new Venda("Bruno", "Norte", "Mouse", 15, 45.0, LocalDate.of(2026, 1, 15)),
            new Venda("Ana", "Sul", "Monitor", 3, 1100.0, LocalDate.of(2026, 2, 3)),
            new Venda("Carla", "NE", "Notebook", 1, 3900.0, LocalDate.of(2026, 2, 8)),
            new Venda("Bruno", "Norte", "Teclado", 8, 120.0, LocalDate.of(2026, 2, 20)),
            new Venda("Carla", "NE", "Mouse", 30, 42.0, LocalDate.of(2026, 3, 1)),
            new Venda("Diego", "Sul", "Monitor", 2, 1150.0, LocalDate.of(2026, 3, 9)),
            new Venda("Ana", "Sul", "Teclado", 5, 125.0, LocalDate.of(2026, 3, 18)));
    }
}
