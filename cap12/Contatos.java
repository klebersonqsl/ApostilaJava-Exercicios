import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Cadastro de contatos que persiste os dados em um arquivo CSV. */
public class Contatos {

    record Contato(String nome, String telefone) {}

    private static final Path ARQUIVO = Path.of("contatos.csv");

    static List<Contato> carregar() throws IOException {
        List<Contato> lista = new ArrayList<>();
        if (Files.exists(ARQUIVO)) {
            for (String l : Files.readAllLines(ARQUIVO, StandardCharsets.UTF_8)) {
                String[] p = l.split(";", 2);
                lista.add(new Contato(p[0], p[1]));
            }
        }
        return lista;
    }

    static void salvar(List<Contato> lista) throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Contato c : lista) {
            linhas.add(c.nome() + ";" + c.telefone());
        }
        Files.write(ARQUIVO, linhas, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Contato> contatos = carregar();
        System.out.println(contatos.size() + " contato(s) carregado(s) do arquivo.");
        while (true) {
            System.out.print("[L]istar, [N]ovo, [S]air: ");
            String op = sc.nextLine().strip().toUpperCase();
            if (op.equals("S")) {
                break;
            } else if (op.equals("L")) {
                for (Contato c : contatos) {
                    System.out.println("  " + c.nome() + " - " + c.telefone());
                }
            } else if (op.equals("N")) {
                System.out.print("  Nome: ");
                String nome = sc.nextLine();
                System.out.print("  Telefone: ");
                contatos.add(new Contato(nome, sc.nextLine()));
                salvar(contatos);
                System.out.println("  Salvo.");
            }
        }
    }
}
