package br.com.loja;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** Sobe a aplicação inteira e faz requisições simuladas, sem abrir uma porta de rede. */
@SpringBootTest
@AutoConfigureMockMvc
class ProdutoApiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void buscaProdutoPeloNome() throws Exception {
        mvc.perform(get("/api/produtos").param("q", "fone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Fone de ouvido"))
                .andExpect(jsonPath("$[0].temFoto").value(true));
    }

    @Test
    void recusaProdutoInvalido() throws Exception {
        mvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome": "X", "categoria": "Cozinha", "preco": -1}"""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros.nome")
                        .value("O nome deve ter de 3 a 80 letras."))
                .andExpect(jsonPath("$.erros.estoque").value("Informe o estoque."));
    }

    @Test
    void produtoInexistenteDa404() throws Exception {
        mvc.perform(get("/api/produtos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Produto 999 não existe."));
    }
}
