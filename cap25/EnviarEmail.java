import java.nio.file.Path;

/**
 * Com Gmail: defina GMAIL_CONTA e GMAIL_SENHA_APP. Sem elas, usa um servidor local
 * de testes na porta 2525.
 */
public class EnviarEmail {
    public static void main(String[] args) throws Exception {
        String conta = System.getenv("GMAIL_CONTA");
        Email email = conta != null
                ? Email.gmail(conta, System.getenv("GMAIL_SENHA_APP"))
                : Email.local(2525, "loja@exemplo.com.br");

        String html = """
                <h2 style="color:#1B3A5C">Pedido 2026-00153 confirmado</h2>
                <p>Olá, <b>Ana</b>! Recebemos o seu pedido no valor de
                <b>R$ 149,90</b>.</p>
                <p>O código de rastreio segue em anexo.</p>""";
        String texto = "Olá, Ana! Recebemos o seu pedido 2026-00153 "
                + "no valor de R$ 149,90.";

        email.enviar("ana.souza@exemplo.com.br", "Pedido 2026-00153 confirmado ✔",
                texto, html, Path.of("qrcode.png"));
        System.out.println("E-mail enviado para ana.souza@exemplo.com.br");
    }
}
