import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

/** Propriedades JavaFX permitem que a tabela perceba alterações automaticamente. */
public class Contato {
    private final StringProperty nome = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final IntegerProperty idade = new SimpleIntegerProperty();

    public Contato(String nome, String email, int idade) {
        this.nome.set(nome);
        this.email.set(email);
        this.idade.set(idade);
    }

    public StringProperty nomeProperty() { return nome; }
    public StringProperty emailProperty() { return email; }
    public IntegerProperty idadeProperty() { return idade; }
    public String getNome() { return nome.get(); }
}
