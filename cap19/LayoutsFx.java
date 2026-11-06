import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class LayoutsFx extends Application {

    @Override
    public void start(Stage palco) {
        // GridPane: formulário em grade (coluna, linha)
        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.add(new Label("Título:"), 0, 0);
        form.add(new TextField("Relatório mensal"), 1, 0);
        form.add(new Label("Prioridade:"), 0, 1);
        ComboBox<String> prioridade = new ComboBox<>();
        prioridade.getItems().addAll("Baixa", "Média", "Alta");
        prioridade.setValue("Alta");
        form.add(prioridade, 1, 1);
        form.add(new Label("Descrição:"), 0, 2);
        TextArea desc = new TextArea("Consolidar as vendas de outubro.");
        desc.setPrefRowCount(3);
        form.add(desc, 1, 2);
        ColumnConstraints rotulos = new ColumnConstraints();
        rotulos.setHalignment(HPos.RIGHT);
        rotulos.setMinWidth(Region.USE_PREF_SIZE);           // nunca corta os rótulos
        ColumnConstraints campos = new ColumnConstraints();
        campos.setHgrow(Priority.ALWAYS);                    // a coluna dos campos cresce
        form.getColumnConstraints().addAll(rotulos, campos);

        // HBox: botões lado a lado
        HBox botoes = new HBox(8, new Button("Cancelar"), new Button("Salvar"));
        botoes.setStyle("-fx-alignment: center-right;");

        // ListView à esquerda
        ListView<String> lista = new ListView<>();
        lista.getItems().addAll("Relatório mensal", "Reunião de equipe", "Backup");
        lista.setPrefWidth(160);

        // BorderPane: topo, esquerda, centro, direita e base
        BorderPane raiz = new BorderPane();
        raiz.setLeft(lista);
        raiz.setCenter(form);
        raiz.setBottom(botoes);
        BorderPane.setMargin(form, new Insets(0, 0, 0, 12));
        BorderPane.setMargin(botoes, new Insets(12, 0, 0, 0));
        raiz.setPadding(new Insets(12));

        palco.setTitle("Tarefas");
        palco.setScene(new Scene(raiz, 560, 260));
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
