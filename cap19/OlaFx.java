import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OlaFx extends Application {

    @Override
    public void start(Stage palco) {                 // Stage = a janela
        Label pergunta = new Label("Qual é o seu nome?");
        TextField nome = new TextField();
        nome.setPromptText("digite aqui");
        Label resposta = new Label();
        Button botao = new Button("Cumprimentar");
        botao.setOnAction(e -> resposta.setText("Olá, " + nome.getText() + "!"));

        VBox raiz = new VBox(10, pergunta, nome, botao, resposta);  // um embaixo do outro
        raiz.setPadding(new Insets(16));
        raiz.setAlignment(Pos.CENTER_LEFT);

        Scene cena = new Scene(raiz, 320, 190);      // Scene = o conteúdo da janela
        palco.setTitle("Olá, JavaFX");
        palco.setScene(cena);
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);                                // inicia o JavaFX e chama start()
    }
}
