import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage palco) throws Exception {
        // carrega a tela do arquivo FXML (que fica junto das classes, no classpath)
        Parent raiz = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene cena = new Scene(raiz);
        cena.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());
        palco.setTitle("Login");
        palco.setScene(cena);
        palco.setResizable(false);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
