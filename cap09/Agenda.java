import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Agenda {

    record Contato(String nome, String telefone, String grupo) {}

    private final Map<String, List<Contato>> porGrupo = new TreeMap<>();

    public void adicionar(Contato c) {
        porGrupo.computeIfAbsent(c.grupo(), g -> new ArrayList<>()).add(c);
    }

    public List<Contato> buscar(String trecho) {
        List<Contato> achados = new ArrayList<>();
        for (List<Contato> lista : porGrupo.values()) {
            for (Contato c : lista) {
                if (c.nome().toLowerCase().contains(trecho.toLowerCase())) {
                    achados.add(c);
                }
            }
        }
        return achados;
    }

    public void imprimir() {
        porGrupo.forEach((grupo, contatos) -> {
            System.out.println(grupo + " (" + contatos.size() + ")");
            for (Contato c : contatos) {
                System.out.printf("   %-16s %s%n", c.nome(), c.telefone());
            }
        });
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        agenda.adicionar(new Contato("Mãe", "(81) 99999-0001", "Família"));
        agenda.adicionar(new Contato("Carlos Mendes", "(11) 98888-1234", "Trabalho"));
        agenda.adicionar(new Contato("Juliana", "(21) 97777-4321", "Amigos"));
        agenda.adicionar(new Contato("Carla Souza", "(11) 96666-1111", "Trabalho"));
        agenda.adicionar(new Contato("Tio Beto", "(81) 95555-2222", "Família"));
        agenda.imprimir();
        System.out.println("Busca por 'car':");
        for (Contato c : agenda.buscar("car")) {
            System.out.println("   " + c.nome() + " - " + c.telefone());
        }
    }
}
