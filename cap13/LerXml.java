import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LerXml {
    public static void main(String[] args) throws Exception {
        DocumentBuilder builder =
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new File("biblioteca.xml")); // carrega tudo

        Element raiz = doc.getDocumentElement();
        System.out.println("Raiz: <" + raiz.getTagName() + ">, cidade = "
                + raiz.getAttribute("cidade"));

        NodeList livros = doc.getElementsByTagName("livro");
        System.out.println("Livros encontrados: " + livros.getLength());
        double total = 0;
        for (int i = 0; i < livros.getLength(); i++) {
            Element livro = (Element) livros.item(i);
            String titulo = texto(livro, "titulo");
            String autor = texto(livro, "autor");
            double preco = Double.parseDouble(texto(livro, "preco"));
            boolean disponivel = Boolean.parseBoolean(livro.getAttribute("disponivel"));
            System.out.printf("  #%s %-15s %-17s R$ %6.2f %s%n", livro.getAttribute("id"),
                    titulo, autor, preco, disponivel ? "" : "(emprestado)");
            total += preco;
        }
        System.out.printf("Valor do acervo: R$ %.2f%n", total);
    }

    /** Texto do primeiro elemento filho com a tag informada. */
    static String texto(Element pai, String tag) {
        return pai.getElementsByTagName(tag).item(0).getTextContent().strip();
    }
}
