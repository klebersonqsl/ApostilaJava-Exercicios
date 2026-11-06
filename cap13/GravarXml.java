import java.io.File;
import java.time.LocalDate;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GravarXml {

    record Item(String produto, int quantidade, double preco) {}

    public static void main(String[] args) throws Exception {
        Item[] itens = {new Item("Teclado", 2, 149.0),
                        new Item("Monitor 24\"", 1, 899.9)};

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();
        Element pedido = doc.createElement("pedido");
        pedido.setAttribute("numero", "1042");
        pedido.setAttribute("data", LocalDate.of(2026, 10, 26).toString());
        doc.appendChild(pedido);

        Element cliente = doc.createElement("cliente");
        cliente.setTextContent("Marta & Filhos Ltda.");   // & será escapado
        pedido.appendChild(cliente);

        Element lista = doc.createElement("itens");
        pedido.appendChild(lista);
        for (Item i : itens) {
            Element item = doc.createElement("item");
            item.setAttribute("quantidade", String.valueOf(i.quantidade()));
            item.setAttribute("preco", String.valueOf(i.preco()));
            item.setTextContent(i.produto());
            lista.appendChild(item);
        }

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.transform(new DOMSource(doc), new StreamResult(new File("pedido.xml")));
        System.out.println("pedido.xml gravado.");
    }
}
