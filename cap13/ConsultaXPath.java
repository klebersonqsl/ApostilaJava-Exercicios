import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ConsultaXPath {
    public static void main(String[] args) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new File("biblioteca.xml"));
        XPath xp = XPathFactory.newInstance().newXPath();

        String primeiro = xp.evaluate("/biblioteca/livro[1]/titulo", doc);
        System.out.println("Primeiro livro: " + primeiro);

        NodeList machado = (NodeList) xp.evaluate(
                "//livro[autor='Machado de Assis']/titulo", doc, XPathConstants.NODESET);
        System.out.println("Livros de Machado de Assis:");
        for (int i = 0; i < machado.getLength(); i++) {
            System.out.println("  " + machado.item(i).getTextContent());
        }

        String disponiveis = xp.evaluate("count(//livro[@disponivel='true'])", doc);
        String soma = xp.evaluate("sum(//livro/preco)", doc);
        String antigo = xp.evaluate("//livro[not(ano > ../livro/ano)]/titulo", doc);
        System.out.println("Disponíveis: " + disponiveis + " | soma: " + soma);
        System.out.println("Mais antigo: " + antigo);
    }
}
