package br.com.loja.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Conexões com o banco SQLite da aplicação. */
public final class Banco {

    private static final String URL = "jdbc:sqlite:loja.db";

    private Banco() {
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabelas() throws SQLException {
        try (Connection con = conectar(); Statement st = con.createStatement()) {
            st.execute("""
                    CREATE TABLE IF NOT EXISTS produto (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome VARCHAR(80) NOT NULL, categoria VARCHAR(30) NOT NULL,
                        preco DECIMAL(10,2) NOT NULL, estoque INTEGER NOT NULL,
                        foto BLOB, foto_tipo VARCHAR(40))""");
            st.execute("""
                    CREATE TABLE IF NOT EXISTS documento (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome VARCHAR(200) NOT NULL, tipo VARCHAR(100), tamanho INTEGER,
                        enviado_em TIMESTAMP NOT NULL, conteudo BLOB NOT NULL)""");
        }
    }
}
