import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TarefaFx extends Application {

    @Override
    public void start(Stage palco) {
        ProgressBar barra = new ProgressBar(0);
        barra.setPrefWidth(300);
        Label status = new Label("Pronto para importar.");
        Button importar = new Button("Importar 50 arquivos");

        importar.setOnAction(e -> {
            // Task: trabalho em segundo plano que informa progresso e mensagem
            Task<Integer> tarefa = new Task<>() {
                @Override
                protected Integer call() throws Exception {
                    int total = 50;
                    for (int i = 1; i <= total; i++) {
                        Thread.sleep(60);                    // simula o processamento
                        updateProgress(i, total);
                        updateMessage("Importando arquivo " + i + " de " + total + "...");
                    }
                    return total;
                }
            };
            barra.progressProperty().bind(tarefa.progressProperty());
            status.textProperty().bind(tarefa.messageProperty());
            importar.disableProperty().bind(tarefa.runningProperty());
            tarefa.setOnSucceeded(ev -> {                     // roda na thread do JavaFX
                status.textProperty().unbind();
                status.setText("Concluído: " + tarefa.getValue() + " arquivos.");
            });
            new Thread(tarefa).start();
        });

        VBox raiz = new VBox(12, importar, barra, status);
        raiz.setPadding(new Insets(16));
        palco.setTitle("Importação");
        palco.setScene(new Scene(raiz, 360, 150));
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
