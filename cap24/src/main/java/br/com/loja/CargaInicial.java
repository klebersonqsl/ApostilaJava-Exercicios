package br.com.loja;

import br.com.loja.modelo.Produto;
import br.com.loja.repositorio.ProdutoRepository;
import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/** Executado uma vez, logo depois que a aplicação sobe: insere produtos de exemplo. */
@Component
public class CargaInicial implements CommandLineRunner {

    private final ProdutoRepository repositorio;

    public CargaInicial(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void run(String... args) throws IOException {
        if (repositorio.count() > 0) {
            return;
        }
        repositorio.save(comFoto(
                new Produto("Caneca de cerâmica", "Cozinha", "34.90", 25), "caneca.jpg"));
        repositorio.save(comFoto(
                new Produto("Caderno 10 matérias", "Papelaria", "29.50", 60),
                "caderno.jpg"));
        repositorio.save(comFoto(
                new Produto("Fone de ouvido", "Eletrônicos", "149.90", 8), "fone.jpg"));
        repositorio.save(new Produto("Lapiseira 0,7mm", "Papelaria", "12.00", 140));
    }

    private Produto comFoto(Produto p, String arquivo) throws IOException {
        // src/main/resources/fotos vai para dentro do JAR e é lido do classpath
        p.setFoto(new ClassPathResource("fotos/" + arquivo).getContentAsByteArray());
        p.setFotoTipo("image/jpeg");
        return p;
    }
}
