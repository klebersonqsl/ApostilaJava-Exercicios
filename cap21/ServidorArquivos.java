import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Servidor HTTP mínimo: lista, entrega (download) e recebe (upload) arquivos. */
public class ServidorArquivos {

    static final Path PASTA = Path.of("arquivos");
    static final String TEXTO = "text/plain; charset=utf-8";

    public static void main(String[] args) throws IOException {
        Files.createDirectories(PASTA);
        HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);
        servidor.createContext("/lista", ServidorArquivos::lista);
        servidor.createContext("/arquivos/", ServidorArquivos::download);
        servidor.createContext("/upload", ServidorArquivos::upload);
        servidor.start();
        log("Servidor no ar em http://localhost:8080");
    }

    static void lista(HttpExchange ex) throws IOException {
        String nomes;
        try (Stream<Path> s = Files.list(PASTA)) {
            nomes = s.map(p -> p.getFileName().toString()).sorted()
                     .collect(Collectors.joining("\n"));
        }
        responder(ex, 200, TEXTO, nomes.getBytes(StandardCharsets.UTF_8));
    }

    static void download(HttpExchange ex) throws IOException {
        String nome = ex.getRequestURI().getPath().substring("/arquivos/".length());
        Path arq = PASTA.resolve(nome).normalize();
        if (!arq.startsWith(PASTA) || !Files.isRegularFile(arq)) {   // evita "../../"
            responder(ex, 404, "text/plain", "Arquivo não encontrado".getBytes());
            return;
        }
        String tipo = Files.probeContentType(arq);
        responder(ex, 200, tipo == null ? "application/octet-stream" : tipo,
                Files.readAllBytes(arq));
    }

    static void upload(HttpExchange ex) throws IOException {
        if (!ex.getRequestMethod().equals("POST")) {
            responder(ex, 405, "text/plain", "Use POST".getBytes());
            return;
        }
        String query = ex.getRequestURI().getRawQuery();          // nome=foto.png
        String nome = URLDecoder.decode(query.split("=", 2)[1], StandardCharsets.UTF_8);
        Path destino = PASTA.resolve(Path.of(nome).getFileName());
        try (InputStream corpo = ex.getRequestBody()) {
            Files.copy(corpo, destino, StandardCopyOption.REPLACE_EXISTING);
        }
        String msg = "Recebido " + destino.getFileName()
                + " (" + Files.size(destino) + " bytes)";
        responder(ex, 201, TEXTO, msg.getBytes(StandardCharsets.UTF_8));
    }

    static void responder(HttpExchange ex, int status, String tipo, byte[] corpo)
            throws IOException {
        ex.getResponseHeaders().set("Content-Type", tipo);
        ex.sendResponseHeaders(status, corpo.length);
        try (OutputStream out = ex.getResponseBody()) {
            out.write(corpo);
        }
        log(ex.getRequestMethod() + " " + ex.getRequestURI() + " -> " + status);
    }

    static void log(String msg) {
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("[" + hora + "] " + msg);
    }
}
