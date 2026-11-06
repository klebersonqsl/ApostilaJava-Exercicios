import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImagemFx extends Application {

    private final ImageView visor = new ImageView();
    private final Label info = new Label();

    @Override
    public void start(Stage palco) {
        visor.setFitWidth(420);
        visor.setPreserveRatio(true);         // mantém a proporção ao redimensionar
        visor.setSmooth(true);
        carregar(new File("paisagem.jpg"));

        Button abrir = new Button("Abrir imagem...");
        abrir.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Escolha uma imagem");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg"));
            File f = fc.showOpenDialog(palco);
            if (f != null) {
                carregar(f);
            }
        });

        // efeito de brilho/saturação controlado por um slider
        ColorAdjust ajuste = new ColorAdjust();
        visor.setEffect(ajuste);
        Slider saturacao = new Slider(-1, 1, 0);
        ajuste.saturationProperty().bind(saturacao.valueProperty());

        HBox controles = new HBox(10, abrir, new Label("Saturação:"), saturacao, info);
        BorderPane raiz = new BorderPane(visor);
        raiz.setBottom(controles);
        BorderPane.setMargin(controles, new Insets(10, 0, 0, 0));
        raiz.setPadding(new Insets(10));
        palco.setTitle("Visualizador JavaFX");
        palco.setScene(new Scene(raiz));
        palco.show();
    }

    private void carregar(File arquivo) {
        Image img = new Image(arquivo.toURI().toString());
        visor.setImage(img);
        info.setText((int) img.getWidth() + " x " + (int) img.getHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
