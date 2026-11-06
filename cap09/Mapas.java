import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Mapas {
    public static void main(String[] args) {
        // Map: associa uma CHAVE a um VALOR
        Map<String, String> capitais = new HashMap<>();
        capitais.put("BA", "Salvador");
        capitais.put("PE", "Recife");
        capitais.put("SP", "São Paulo");
        capitais.put("PE", "Recife (PE)");       // chave repetida: substitui o valor

        System.out.println("Capital de SP: " + capitais.get("SP"));
        System.out.println("Capital de AM: " + capitais.get("AM"));    // não existe: null
        System.out.println("AM (padrão): " + capitais.getOrDefault("AM", "?"));
        System.out.println("Tem BA? " + capitais.containsKey("BA"));
        System.out.println("Tamanho: " + capitais.size());

        // percorrendo pares chave-valor
        for (Map.Entry<String, String> e : capitais.entrySet()) {
            System.out.println(e.getKey() + " = " + e.getValue());
        }

        // contagem de palavras com merge
        String texto = "o rato roeu a roupa do rei de roma e o rei riu do rato";
        Map<String, Integer> contagem = new TreeMap<>();     // TreeMap: chaves ordenadas
        for (String palavra : texto.split(" ")) {
            contagem.merge(palavra, 1, Integer::sum);         // soma 1 ao valor existente
        }
        System.out.println(contagem);
    }
}
