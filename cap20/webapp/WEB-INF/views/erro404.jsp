<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head><meta charset="UTF-8"><title>Página não encontrada</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css"></head>
<body>
<header><h1>Curso Java Web</h1></header>
<main>
  <h2>Ops! Página não encontrada (404)</h2>
  <p>O endereço <code>${requestScope['jakarta.servlet.error.request_uri']}</code>
     não existe.</p>
  <a class="botao" href="${pageContext.request.contextPath}/">Voltar ao início</a>
</main>
</body>
</html>
