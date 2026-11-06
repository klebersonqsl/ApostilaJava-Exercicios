package editor;

import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;

/** Mostra o documento dividido em folhas A4, lado a lado, antes de imprimir. */
public class VisualizacaoImpressao {

    // folha A4 em pixels de tela (96 por polegada): 210 x 297 mm, margens de 20 mm
    private static final double MM = 96 / 25.4;
    private static final int LARGURA = (int) Math.round(210 * MM);
    private static final int ALTURA = (int) Math.round(297 * MM);
    private static final int MARGEM = (int) Math.round(20 * MM);
    private static final int ESPACO = 24;                  // entre uma folha e outra

    private final Stage janela = new Stage();
    private final WebView folhas = new WebView();
    private final Label info = new Label("Calculando páginas...");

    public VisualizacaoImpressao(Window dono, String html, String titulo) {
        janela.initOwner(dono);
        janela.setTitle("Visualizar impressão - " + titulo);

        Button imprimir = new Button("Imprimir...");
        imprimir.setOnAction(e -> imprimir(janela, html));
        ComboBox<String> zoom = new ComboBox<>(
                FXCollections.observableArrayList("50%", "75%", "100%"));
        zoom.setValue("50%");
        zoom.setOnAction(e -> {
            int porcento = Integer.parseInt(zoom.getValue().replace("%", ""));
            folhas.setZoom(porcento / 100.0);
        });
        folhas.setZoom(0.5);
        Button fechar = new Button("Fechar");
        fechar.setOnAction(e -> janela.close());
        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        ToolBar barra = new ToolBar(imprimir, new Separator(), new Label("Zoom:"), zoom,
                new Separator(), info, espaco, fechar);

        // quando o documento terminar de carregar, conta as folhas
        WebEngine motor = folhas.getEngine();
        motor.getLoadWorker().stateProperty().addListener((obs, antes, estado) -> {
            if (estado == Worker.State.SUCCEEDED) {
                Object n = motor.executeScript("paginar()");
                info.setText(n + " página(s) A4");
            }
        });
        motor.loadContent(emFolhas(html));
        BorderPane raiz = new BorderPane(folhas);
        raiz.setTop(barra);
        janela.setScene(new Scene(raiz, 980, 640));
    }

    public void mostrar() {
        janela.show();
    }

    /**
     * Acrescenta ao documento um CSS que distribui o texto em colunas do tamanho da área
     * útil de uma folha. Cada coluna vira uma "folha", desenhada com um fundo branco
     * que se repete a cada LARGURA + ESPACO pixels.
     */
    private static String emFolhas(String html) {
        int util = LARGURA - 2 * MARGEM;
        int periodo = LARGURA + ESPACO;
        String estilo = """
                <style>
                html { background: #6b6f76; }
                body { margin: 24px; box-sizing: content-box;
                       height: %1$dpx; padding: %2$dpx; width: %3$dpx;
                       -webkit-column-width: %3$dpx; column-width: %3$dpx;
                       -webkit-column-gap: %4$dpx; column-gap: %4$dpx;
                       -webkit-column-fill: auto; column-fill: auto;
                       background: repeating-linear-gradient(to right,
                           white 0px, white %5$dpx, #6b6f76 %5$dpx, #6b6f76 %6$dpx); }
                </style>
                <script>
                function paginar() {
                    var n = Math.max(1, Math.ceil(
                            (document.documentElement.scrollWidth - 24) / %6$d));
                    document.body.style.width = (n * %6$d - %7$d - 2 * %2$d) + 'px';
                    return n;
                }
                </script>
                """.formatted(ALTURA - 2 * MARGEM, MARGEM, util, 2 * MARGEM + ESPACO,
                LARGURA, periodo, ESPACO);
        return html.contains("</head>")
                ? html.replace("</head>", estilo + "</head>")
                : estilo + html;
    }

    /** Imprime com o WebEngine, que quebra o texto em páginas sozinho. */
    public static void imprimir(Window dono, String html) {
        PrinterJob trabalho = PrinterJob.createPrinterJob();
        if (trabalho == null) {
            new Alert(Alert.AlertType.WARNING, "Nenhuma impressora instalada.")
                    .showAndWait();
            return;
        }
        if (!trabalho.showPrintDialog(dono)) {
            return;                                  // o usuário cancelou
        }
        WebEngine motor = new WebEngine();
        motor.getLoadWorker().stateProperty().addListener((o, antes, estado) -> {
            if (estado == Worker.State.SUCCEEDED) {
                motor.print(trabalho);
                trabalho.endJob();
            }
        });
        motor.loadContent(html);
    }
}
