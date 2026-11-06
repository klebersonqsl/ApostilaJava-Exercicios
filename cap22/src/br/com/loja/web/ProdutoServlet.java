package br.com.loja.web;

import br.com.loja.modelo.Produto;
import br.com.loja.modelo.ProdutoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Controlador do cadastro: /produtos, /produtos/novo, /produtos/editar ... */
@WebServlet("/produtos/*")
@MultipartConfig(maxFileSize = 2 * 1024 * 1024)        // aceita upload de até 2 MB
public class ProdutoServlet extends HttpServlet {

    private static final List<String> CATEGORIAS =
            List.of("Cozinha", "Eletrônicos", "Esporte", "Papelaria");
    private final ProdutoDao dao = new ProdutoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String acao = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            switch (acao) {
                case "/novo" -> mostrarFormulario(req, resp, new Produto());
                case "/editar" -> {
                    long id = Long.parseLong(req.getParameter("id"));
                    Produto p = dao.porId(id).orElse(null);
                    if (p == null) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    } else {
                        mostrarFormulario(req, resp, p);
                    }
                }
                default -> {
                    String q = req.getParameter("q");
                    req.setAttribute("produtos", dao.buscar(q));
                    req.setAttribute("q", q);
                    req.getRequestDispatcher("/WEB-INF/jsp/produtos/lista.jsp")
                            .forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);          // vai para a página de erro 500
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            if ("/excluir".equals(req.getPathInfo())) {
                dao.excluir(Long.parseLong(req.getParameter("id")));
                Flash.mensagem(req, "Produto excluído.");
            } else if (!salvar(req, resp)) {
                return;                             // formulário reexibido com erros
            }
            resp.sendRedirect(req.getContextPath() + "/produtos");   // Post-Redirect-Get
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private boolean salvar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, SQLException {
        Produto p = new Produto();
        String id = req.getParameter("id");
        p.setId(id == null || id.isBlank() ? null : Long.valueOf(id));
        p.setNome(req.getParameter("nome").strip());
        p.setCategoria(req.getParameter("categoria"));
        Map<String, String> erros = new LinkedHashMap<>();
        if (p.getNome().length() < 3) {
            erros.put("nome", "O nome deve ter pelo menos 3 letras.");
        }
        try {
            p.setPreco(new BigDecimal(req.getParameter("preco").replace(",", ".")));
            if (p.getPreco().signum() <= 0) {
                erros.put("preco", "O preço deve ser maior que zero.");
            }
        } catch (NumberFormatException e) {
            erros.put("preco", "Preço inválido.");
        }
        try {
            p.setEstoque(Integer.parseInt(req.getParameter("estoque")));
        } catch (NumberFormatException e) {
            erros.put("estoque", "Informe um número inteiro.");
        }

        // upload da foto: um "Part" do formulário multipart
        Part foto = req.getPart("foto");
        byte[] bytes = null;
        String tipo = null;
        if (foto != null && foto.getSize() > 0) {
            tipo = foto.getContentType();
            if (tipo == null || !tipo.startsWith("image/")) {
                erros.put("foto", "Envie uma imagem (JPG, PNG ou GIF).");
            } else {
                bytes = foto.getInputStream().readAllBytes();
            }
        }

        if (!erros.isEmpty()) {
            if (p.getId() != null) {        // para continuar exibindo a foto atual
                dao.porId(p.getId()).ifPresent(atual -> p.setTemFoto(atual.isTemFoto()));
            }
            req.setAttribute("erros", erros);
            mostrarFormulario(req, resp, p);
            return false;
        }
        dao.salvar(p, bytes, tipo);
        Flash.mensagem(req, "Produto \"" + p.getNome() + "\" salvo com sucesso.");
        return true;
    }

    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp,
                                   Produto p) throws ServletException, IOException {
        req.setAttribute("produto", p);
        req.setAttribute("categorias", CATEGORIAS);
        req.getRequestDispatcher("/WEB-INF/jsp/produtos/form.jsp").forward(req, resp);
    }
}
