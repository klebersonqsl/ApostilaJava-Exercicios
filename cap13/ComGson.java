import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class ComGson {

    record Tarefa(String titulo, int prioridade, boolean concluida) {}

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Tarefa> tarefas = List.of(new Tarefa("Estudar JSON", 1, false),
                new Tarefa("Revisar XML", 2, true));

        String json = gson.toJson(tarefas);
        System.out.println(json);

        var tipo = new TypeToken<List<Tarefa>>() {}.getType();   // List<Tarefa>
        List<Tarefa> volta = gson.fromJson(json, tipo);
        System.out.println("Concluídas: "
                + volta.stream().filter(Tarefa::concluida).map(Tarefa::titulo).toList());
    }
}
