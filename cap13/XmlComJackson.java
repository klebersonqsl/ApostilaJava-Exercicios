import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

public class XmlComJackson {

    @JacksonXmlRootElement(localName = "turma")
    public static class Turma {
        @JacksonXmlProperty(isAttribute = true)          // vira atributo, não elemento
        public String codigo;
        public String professor;
        @JacksonXmlElementWrapper(localName = "alunos")  // elemento que envolve a lista
        @JacksonXmlProperty(localName = "aluno")         // nome de cada item
        public List<String> alunos;
    }

    public static void main(String[] args) throws Exception {
        XmlMapper xml = new XmlMapper();
        xml.enable(SerializationFeature.INDENT_OUTPUT);

        Turma t = new Turma();
        t.codigo = "JAVA-26A";
        t.professor = "Prof. Helena";
        t.alunos = List.of("Ana", "Bruno", "Caio");
        String texto = xml.writeValueAsString(t);
        System.out.println(texto);

        Turma lida = xml.readValue(texto, Turma.class);
        System.out.println("Lido de volta: " + lida.alunos.size() + " alunos com "
                + lida.professor);
    }
}
