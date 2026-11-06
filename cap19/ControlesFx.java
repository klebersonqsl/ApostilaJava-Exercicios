import java.time.LocalDate;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControlesFx extends Application {

    @Override
    public void start(Stage palco) {
        TextField nome = new TextField();
        PasswordField senha = new PasswordField();
        ComboBox<String> estado = new ComboBox<>();
        estado.getItems().addAll("Bahia", "Ceará", "Pernambuco", "São Paulo");
        DatePicker nascimento = new DatePicker(LocalDate.of(2000, 5, 17));
        Spinner<Integer> dependentes = new Spinner<>(0, 10, 2);

        RadioButton pf = new RadioButton("Pessoa física");
        RadioButton pj = new RadioButton("Pessoa jurídica");
        ToggleGroup tipo = new ToggleGroup();             // só um selecionado por vez
        pf.setToggleGroup(tipo);
        pj.setToggleGroup(tipo);
        pf.setSelected(true);

        Slider volume = new Slider(0, 100, 40);
        Label valorVolume = new Label();
        // binding: o texto do rótulo acompanha o valor do slider automaticamente
        valorVolume.textProperty().bind(volume.valueProperty().asString("%.0f%%"));

        CheckBox aceito = new CheckBox("Li e aceito os termos");
        Button enviar = new Button("Enviar");
        // o botão só fica habilitado quando o nome foi preenchido e os termos aceitos
        enviar.disableProperty().bind(
                nome.textProperty().isEmpty().or(aceito.selectedProperty().not()));

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(8);
        g.setPadding(new Insets(14));
        g.addRow(0, new Label("Nome:"), nome);
        g.addRow(1, new Label("Senha:"), senha);
        g.addRow(2, new Label("Estado:"), estado);
        g.addRow(3, new Label("Nascimento:"), nascimento);
        g.addRow(4, new Label("Dependentes:"), dependentes);
        g.addRow(5, new Label("Tipo:"), new HBox(12, pf, pj));
        g.addRow(6, new Label("Volume:"), new HBox(8, volume, valorVolume));
        g.add(aceito, 1, 7);
        g.add(enviar, 1, 8);

        palco.setTitle("Controles do JavaFX");
        palco.setScene(new Scene(g));
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
