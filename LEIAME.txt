APOSTILA DE JAVA - CÓDIGO-FONTE DOS EXEMPLOS
Kleberson Queiti Souza Lopes
=============================================

Uma pasta por capítulo (cap01 a cap25), com os mesmos nomes de arquivo usados no texto.
Requisito: JDK 21 ou mais recente (Java 21 LTS ou Java 25 LTS).
Arquivos de texto em UTF-8.


COMO EXECUTAR
-------------
Capítulos 1 a 10, 12, 16 e 17 e os programas da raiz de cap21 usam só a biblioteca
padrão do Java:

    cd cap02
    javac -d out *.java
    java -cp out Imc

  Um programa de um arquivo só também pode ser executado direto:  java Imc.java
  As pastas "dados" contêm os arquivos de entrada usados nos exemplos; execute os
  programas a partir da pasta do capítulo para que os caminhos relativos funcionem.
  Em cap05, os exemplos com pacotes estão na pasta "pacotes" (veja o capítulo 5).
  As pastas cap01/erros e cap08/erros contêm erros de propósito: são os exemplos de
  mensagens de erro do compilador mostrados no texto.

Capítulo 11: cap11/projeto é um projeto Maven.   cd cap11/projeto  e  mvn package

Capítulos 13, 14, 15, 18, 19 e 25 usam bibliotecas externas (JARs). Coloque-as em uma
pasta "lib" e use -cp, como mostra o capítulo 11, ou crie um projeto Maven com:

    cap13  com.fasterxml.jackson.core:jackson-databind:2.20.1
           com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.20.1
           com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.20.1
           com.google.code.gson:gson:2.13.2
    cap14  org.xerial:sqlite-jdbc:3.50.3.0
    cap15  org.xerial:sqlite-jdbc:3.50.3.0
           com.mysql:mysql-connector-j:9.5.0
           org.postgresql:postgresql:42.7.10
           org.firebirdsql.jdbc:jaybird:6.0.4
           com.microsoft.sqlserver:mssql-jdbc:13.6.0.jre11
           com.zaxxer:HikariCP:7.1.0
           (scripts SQL de criação das tabelas em cap15/sql)
    cap18  org.xerial:sqlite-jdbc:3.50.3.0          (cap18/app: o sistema completo)
    cap19  org.openjfx:javafx-controls:25 e org.openjfx:javafx-fxml:25
           org.openjfx:javafx-web:25   (para o WordPad Fx, na pasta cap19/editor)
           (veja no capítulo 19 o pom.xml com o javafx-maven-plugin)
    cap25  com.google.zxing:core:3.5.4 e com.google.zxing:javase:3.5.4
           org.eclipse.angus:angus-mail:2.0.5
           com.fasterxml.jackson.core:jackson-databind:2.20.1
           org.xerial:sqlite-jdbc:3.50.3.0

Capítulos 20, 21 (pasta cap21/web) e 22: aplicações web para o Tomcat 10.1 ou 11.
    src     classes Java (servlets, filtros, modelo)
    webapp  páginas, CSS e WEB-INF
  Crie um projeto web Maven (packaging war) na IDE, copie src para src/main/java e
  webapp para src/main/webapp, e acrescente as dependências mostradas nos capítulos
  (jakarta.servlet-api com escopo provided, JSTL e, no 22, sqlite-jdbc).
  Nos capítulos 21 e 22, copie a pasta dados/fotos para a pasta de onde o Tomcat é
  iniciado (são as imagens da galeria e dos produtos de exemplo).

Capítulo 23 (JSF): projeto Maven completo (pom.xml). mvn package gera target/loja.war;
  copie-o para a pasta webapps do Tomcat 10.1 e abra http://localhost:8080/loja/
  Como no capítulo 22, copie dados/fotos para a pasta de onde o Tomcat é iniciado.

Capítulo 24 (Spring Boot): projeto Maven completo.
    cd cap24
    mvn spring-boot:run          (ou rode LojaApplication na IDE)
  Abra http://localhost:8080/   A API fica em http://localhost:8080/api/produtos
  O banco H2 é criado na pasta cap24/dados.

Bancos de dados (capítulos 14, 15 e 18): os exemplos usam o usuário "aluno", a senha
"senha123" e o banco "curso"; ajuste nas classes Banco.java conforme a sua instalação.

Capítulo 25 (Extras): Correios, Gmail e WhatsApp exigem credenciais, lidas de
  variáveis de ambiente (veja cada seção do capítulo). Sem elas, teste com os
  simuladores locais incluídos, cada um em um terminal:
    java ServidorCepSimulado            depois  java ConsultaCep http://localhost:8095 http://localhost:8095
    java SimuladorMeta                  depois  java DemoWhatsApp http://localhost:8096
    python ferramentas/smtp_teste.py    depois  java EnviarEmail
  (o smtp_teste.py precisa do Python 3 e de:  pip install aiosmtpd)
  Antes do EnviarEmail, rode o GerarCodigos: o e-mail anexa o qrcode.png.
  O Backup lê o banco loja.db e a pasta config (copie dados/config para a pasta do
  capítulo); qualquer banco SQLite serve, por exemplo o do capítulo 14.
