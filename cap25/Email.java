import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.Date;
import java.util.Properties;

/** Envio de e-mails com Jakarta Mail: texto, HTML e anexos. */
public class Email {

    private final Session sessao;
    private final String remetente;

    private Email(Properties config, String usuario, String senha, String remetente) {
        Authenticator autenticador = usuario == null ? null : new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        };
        this.sessao = Session.getInstance(config, autenticador);
        this.remetente = remetente;
    }

    /** Gmail: porta 587 com STARTTLS e uma "senha de app" de 16 letras. */
    public static Email gmail(String conta, String senhaDeApp) {
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");      // cifra a conexão
        p.put("mail.smtp.starttls.required", "true");    // e recusa se não puder cifrar
        p.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
        p.put("mail.smtp.connectiontimeout", "10000");
        p.put("mail.smtp.timeout", "10000");
        return new Email(p, conta, senhaDeApp, conta);
    }

    /** Servidor de testes local, sem senha e sem criptografia. */
    public static Email local(int porta, String remetente) {
        Properties p = new Properties();
        p.put("mail.smtp.host", "localhost");
        p.put("mail.smtp.port", String.valueOf(porta));
        return new Email(p, null, null, remetente);
    }

    /** Envia uma mensagem em HTML, com versão em texto puro e anexos opcionais. */
    public void enviar(String para, String assunto, String texto, String html,
                       Path... anexos)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = new MimeMessage(sessao);
        msg.setFrom(new InternetAddress(remetente, "Sistema da Loja", "UTF-8"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
        msg.setSubject(assunto, "UTF-8");
        msg.setSentDate(new Date());

        // "alternative": o leitor de e-mail mostra o HTML ou, se não puder, o texto
        MimeMultipart corpo = new MimeMultipart("alternative");
        MimeBodyPart parteTexto = new MimeBodyPart();
        parteTexto.setText(texto, "UTF-8");
        MimeBodyPart parteHtml = new MimeBodyPart();
        parteHtml.setContent(html, "text/html; charset=UTF-8");
        corpo.addBodyPart(parteTexto);
        corpo.addBodyPart(parteHtml);

        // "mixed": o corpo acima mais os arquivos anexos
        MimeMultipart tudo = new MimeMultipart("mixed");
        MimeBodyPart parteCorpo = new MimeBodyPart();
        parteCorpo.setContent(corpo);
        tudo.addBodyPart(parteCorpo);
        for (Path anexo : anexos) {
            MimeBodyPart parte = new MimeBodyPart();
            parte.setDataHandler(new DataHandler(new FileDataSource(anexo.toFile())));
            parte.setFileName(anexo.getFileName().toString());
            tudo.addBodyPart(parte);
        }
        msg.setContent(tudo);
        Transport.send(msg);
    }
}
