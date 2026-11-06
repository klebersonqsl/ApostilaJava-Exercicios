import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/** StAX: lê o XML como um fluxo de eventos, sem carregar tudo na memória. */
public class LerXmlStax {
    public static void main(String[] args) throws Exception {
        XMLInputFactory f = XMLInputFactory.newInstance();
        try (InputStream in = new FileInputStream("biblioteca.xml")) {
            XMLStreamReader r = f.createXMLStreamReader(in);
            String tagAtual = "";
            while (r.hasNext()) {
                int evento = r.next();
                if (evento == XMLStreamConstants.START_ELEMENT) {
                    tagAtual = r.getLocalName();
                    if (tagAtual.equals("livro")) {
                        String id = r.getAttributeValue(null, "id");
                        System.out.print("Livro " + id + ": ");
                    }
                } else if (evento == XMLStreamConstants.CHARACTERS
                        && tagAtual.equals("titulo")) {
                    System.out.println(r.getText());
                    tagAtual = "";
                }
            }
            r.close();
        }
    }
}
