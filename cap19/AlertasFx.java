import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertasFx extends Application {

    @Override
    public void start(Stage palco) {
        Button excluir = new Button("Excluir cliente");
        excluir.setOnAction(e -> {
            Alert confirma = new Alert(Alert.AlertType.CONFIRMATION);
            confirma.initOwner(palco);
            confirma.setTitle("Confirmação");
            confirma.setHeaderText("Excluir o cliente Marcos Pereira?");
            confirma.setContentText("Esta operação não pode ser desfeita.");
            Optional<ButtonType> r = confirma.showAndWait();     // espera a resposta
            if (r.isPresent() && r.get() == ButtonType.OK) {
                Alert info = new Alert(Alert.AlertType.INFORMATION, "Cliente excluído.");
                info.initOwner(palco);
                info.setHeaderText(null);
                info.showAndWait();
            }
        });
        Button renomear = new Button("Renomear...");
        renomear.setOnAction(e -> {
            TextInputDialog d = new TextInputDialog("Marcos Pereira");
            d.initOwner(palco);
            d.setHeaderText("Novo nome do cliente:");
            d.showAndWait().ifPresent(n -> System.out.println("Novo nome: " + n));
        });
        VBox raiz = new VBox(10, excluir, renomear);
        raiz.setStyle("-fx-padding: 20;");
        palco.setTitle("Clientes");
        palco.setScene(new Scene(raiz, 420, 300));
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
