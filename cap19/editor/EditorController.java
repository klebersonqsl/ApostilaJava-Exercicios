package editor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** Controlador da janela principal (fx:controller do editor.fxml). */
public class EditorController {

    private static final String VAZIO = "<html><head></head><body></body></html>";

    @FXML private HTMLEditor editor;
    @FXML private Label statusArquivo;
    @FXML private Label statusPalavras;
    @FXML private RadioMenuItem itemClaro;
    @FXML private RadioMenuItem itemEscuro;
    @FXML private ToggleButton botaoTema;

    private Stage palco;
    private Scene cena;
    private Path arquivoAtual;

    void iniciar(Stage palco, Scene cena) {
        this.palco = palco;
        this.cena = cena;
        palco.setScene(cena);
        cena.getRoot().applyCss();          // cria as partes internas do HTMLEditor
        aplicarTema(false);
        editor.setOnKeyReleased(e -> atualizarStatus());
        atualizarTitulo();
    }

    // ------------------------------------------------------------ menu Arquivo
    @FXML
    void novo() {
        editor.setHtmlText(VAZIO);
        arquivoAtual = null;
        atualizarTitulo();
        atualizarStatus();
    }

    @FXML
    void abrir() {
        File arquivo = escolhedor("Abrir documento").showOpenDialog(palco);
        if (arquivo != null) {
            abrirArquivo(arquivo.toPath());
        }
    }

    void abrirArquivo(Path arquivo) {
        try {
            String conteudo = Files.readString(arquivo);
            if (arquivo.toString().toLowerCase().endsWith(".txt")) {   // texto puro
                conteudo = "<html><body><p>" + escapar(conteudo).replace("\n", "<br>")
                        + "</p></body></html>";
            }
            editor.setHtmlText(conteudo);
            arquivoAtual = arquivo;
            atualizarTitulo();
            atualizarStatus();
        } catch (IOException e) {
            mensagem(Alert.AlertType.ERROR, "Não foi possível abrir:\n" + e.getMessage());
        }
    }

    @FXML
    void salvar() {
        if (arquivoAtual == null) {
            salvarComo();
        } else {
            gravar(arquivoAtual);
        }
    }

    @FXML
    void salvarComo() {
        FileChooser escolhedor = escolhedor("Salvar documento");
        escolhedor.setInitialFileName("documento.html");
        File arquivo = escolhedor.showSaveDialog(palco);
        if (arquivo != null) {
            gravar(arquivo.toPath());
        }
    }

    private void gravar(Path arquivo) {
        try {
            Files.writeString(arquivo, editor.getHtmlText());
            arquivoAtual = arquivo;
            atualizarTitulo();
            statusArquivo.setText("Salvo em " + arquivo.getFileName());
        } catch (IOException e) {
            mensagem(Alert.AlertType.ERROR, "Erro ao gravar:\n" + e.getMessage());
        }
    }

    @FXML
    void visualizarImpressao() {
        new VisualizacaoImpressao(palco, editor.getHtmlText(), nomeDocumento())
                .mostrar();
    }

    @FXML
    void imprimir() {
        VisualizacaoImpressao.imprimir(palco, editor.getHtmlText());
    }

    @FXML
    void sair() {
        Platform.exit();
    }

    // ------------------------------------------------------------ temas
    @FXML
    void temaClaro() {
        aplicarTema(false);
    }

    @FXML
    void temaEscuro() {
        aplicarTema(true);
    }

    @FXML
    void alternarTema() {
        aplicarTema(botaoTema.isSelected());
    }

    private void aplicarTema(boolean escuro) {
        // 1) os controles (menus, barras, botões) seguem a folha de estilo da cena
        cena.getStylesheets().setAll(recurso(escuro ? "escuro.css" : "claro.css"));
        // 2) o texto fica dentro de uma WebView, que recebe uma folha de estilo própria
        WebView pagina = (WebView) editor.lookup(".web-view");
        pagina.getEngine().setUserStyleSheetLocation(
                recurso(escuro ? "documento-escuro.css" : "documento-claro.css"));
        // 3) menu e botão sempre mostram o tema atual
        itemEscuro.setSelected(escuro);
        itemClaro.setSelected(!escuro);
        botaoTema.setSelected(escuro);
        botaoTema.setText(escuro ? "Tema claro" : "Tema escuro");
    }

    // ------------------------------------------------------------ ajuda
    @FXML
    void sobre() {
        mensagem(Alert.AlertType.INFORMATION,
                "WordPad Fx 1.0\nExemplo do capítulo 19 da Apostila de Java.");
    }

    // ------------------------------------------------------------ auxiliares
    private void atualizarTitulo() {
        palco.setTitle(nomeDocumento() + " - WordPad Fx");
        statusArquivo.setText(arquivoAtual == null ? "Documento novo"
                                                   : arquivoAtual.toString());
    }

    private void atualizarStatus() {
        String texto = editor.getHtmlText()
                .replaceAll("(?s)<[^>]*>", " ")           // tira as tags
                .replace("&nbsp;", " ");
        long palavras = Arrays.stream(texto.split("\\s+"))
                .filter(s -> !s.isBlank())
                .count();
        statusPalavras.setText(palavras + " palavra(s)");
    }

    private String nomeDocumento() {
        return arquivoAtual == null ? "Documento" : arquivoAtual.getFileName().toString();
    }

    private FileChooser escolhedor(String titulo) {
        FileChooser escolhedor = new FileChooser();
        escolhedor.setTitle(titulo);
        escolhedor.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Documentos (*.html)", "*.html", "*.htm"),
                new FileChooser.ExtensionFilter("Texto (*.txt)", "*.txt"));
        return escolhedor;
    }

    private String recurso(String nome) {
        return getClass().getResource(nome).toExternalForm();
    }

    private void mensagem(Alert.AlertType tipo, String texto) {
        Alert alerta = new Alert(tipo, texto);
        alerta.initOwner(palco);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    private static String escapar(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
