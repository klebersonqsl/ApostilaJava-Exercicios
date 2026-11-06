package editor;

import java.nio.file.Path;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** WordPad Fx: um editor de texto com formatação, visualização de impressão e temas. */
public class EditorApp extends Application {

    @Override
    public void start(Stage palco) throws Exception {
        // a tela foi desenhada no Scene Builder e gravada em editor.fxml
        FXMLLoader carregador = new FXMLLoader(getClass().getResource("editor.fxml"));
        Parent raiz = carregador.load();
        EditorController controlador = carregador.getController();
        controlador.iniciar(palco, new Scene(raiz, 900, 620));

        // opcional: um arquivo para abrir, passado na linha de comando
        List<String> args = getParameters().getRaw();
        if (!args.isEmpty()) {
            controlador.abrirArquivo(Path.of(args.get(0)));
        }
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
