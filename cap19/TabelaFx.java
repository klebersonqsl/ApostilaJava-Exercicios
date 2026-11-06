import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class TabelaFx extends Application {

    // lista observável: a tabela se atualiza sozinha quando ela muda
    private final ObservableList<Contato> contatos = FXCollections.observableArrayList(
            new Contato("Ana Lima", "ana@mail.com", 31),
            new Contato("Bruno Reis", "bruno@mail.com", 27),
            new Contato("Carla Dias", "carla@mail.com", 44));

    @Override
    public void start(Stage palco) {
        TableView<Contato> tabela = new TableView<>();
        TableColumn<Contato, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());
        TableColumn<Contato, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(c -> c.getValue().emailProperty());
        TableColumn<Contato, Number> colIdade = new TableColumn<>("Idade");
        colIdade.setCellValueFactory(c -> c.getValue().idadeProperty());
        colNome.setPrefWidth(150);
        colEmail.setPrefWidth(170);
        tabela.getColumns().add(colNome);
        tabela.getColumns().add(colEmail);
        tabela.getColumns().add(colIdade);

        // filtro: uma "visão" da lista original
        FilteredList<Contato> filtrada = new FilteredList<>(contatos);
        tabela.setItems(filtrada);
        TextField busca = new TextField();
        busca.setPromptText("buscar por nome");
        busca.textProperty().addListener((obs, antigo, novo) -> filtrada.setPredicate(
                ct -> ct.getNome().toLowerCase().contains(novo.toLowerCase())));

        TextField nome = new TextField();
        nome.setPromptText("nome");
        TextField email = new TextField();
        email.setPromptText("e-mail");
        TextField idade = new TextField();
        idade.setPromptText("idade");
        idade.setPrefColumnCount(4);
        Button adicionar = new Button("Adicionar");
        adicionar.setOnAction(e -> {
            contatos.add(new Contato(nome.getText(), email.getText(),
                    Integer.parseInt(idade.getText())));
            nome.clear();
            email.clear();
            idade.clear();
        });
        Button remover = new Button("Remover");
        var selecao = tabela.getSelectionModel();
        remover.disableProperty().bind(selecao.selectedItemProperty().isNull());
        remover.setOnAction(e -> contatos.remove(selecao.getSelectedItem()));

        Label total = new Label();
        total.textProperty().bind(javafx.beans.binding.Bindings.size(contatos)
                .asString("%d contato(s)"));

        HBox topo = new HBox(8, new Label("Buscar:"), busca, total);
        HBox.setHgrow(busca, Priority.ALWAYS);
        HBox novoContato = new HBox(8, nome, email, idade, adicionar, remover);

        BorderPane raiz = new BorderPane(tabela);
        raiz.setTop(topo);
        raiz.setBottom(novoContato);
        BorderPane.setMargin(topo, new Insets(0, 0, 8, 0));
        BorderPane.setMargin(novoContato, new Insets(8, 0, 0, 0));
        raiz.setPadding(new Insets(10));

        palco.setTitle("Contatos");
        palco.setScene(new Scene(raiz, 640, 300));
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
