import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GraficosFx extends Application {

    @Override
    public void start(Stage palco) {
        PieChart pizza = new PieChart(FXCollections.observableArrayList(
                new PieChart.Data("Sul", 13825),
                new PieChart.Data("Nordeste", 5160),
                new PieChart.Data("Norte", 1635)));
        pizza.setTitle("Vendas por região");
        pizza.setLegendVisible(false);

        CategoryAxis meses = new CategoryAxis();
        NumberAxis valores = new NumberAxis();
        valores.setLabel("R$ mil");
        BarChart<String, Number> barras = new BarChart<>(meses, valores);
        barras.setTitle("Vendas por mês");
        XYChart.Series<String, Number> ano2025 = new XYChart.Series<>();
        ano2025.setName("2025");
        XYChart.Series<String, Number> ano2026 = new XYChart.Series<>();
        ano2026.setName("2026");
        String[] m = {"Jan", "Fev", "Mar", "Abr"};
        int[] v25 = {42, 38, 51, 47};
        int[] v26 = {48, 45, 56, 61};
        for (int i = 0; i < m.length; i++) {
            ano2025.getData().add(new XYChart.Data<>(m[i], v25[i]));
            ano2026.getData().add(new XYChart.Data<>(m[i], v26[i]));
        }
        barras.getData().add(ano2025);
        barras.getData().add(ano2026);

        palco.setTitle("Painel de vendas");
        palco.setScene(new Scene(new HBox(pizza, barras), 820, 360));
        palco.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
