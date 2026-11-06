import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/** Controlador da tela definida em login.fxml. */
public class LoginController {

    @FXML private TextField usuario;          // injetados pelo FXMLLoader (fx:id)
    @FXML private PasswordField senha;
    @FXML private Label mensagem;

    @FXML
    private void initialize() {               // chamado depois que o FXML foi carregado
        mensagem.setText("");
    }

    @FXML
    private void entrar() {                   // onAction="#entrar"
        if (usuario.getText().equals("admin") && senha.getText().equals("1234")) {
            mensagem.getStyleClass().setAll("mensagem", "ok");
            mensagem.setText("Bem-vindo, " + usuario.getText() + "!");
        } else {
            mensagem.getStyleClass().setAll("mensagem", "erro");
            mensagem.setText("Usuário ou senha inválidos.");
        }
    }
}
