<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head><meta charset="UTF-8"><title>Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css"></head>
<body>
<header><h1>Área do aluno</h1></header>
<main style="max-width:380px">
  <h2>Entrar</h2>
  <% if (request.getAttribute("erro") != null) { %>
    <div class="erro">${erro}</div>
  <% } %>
  <form method="post" action="${pageContext.request.contextPath}/login">
    <label>Usuário</label><input name="usuario" value="${param.usuario}">
    <label>Senha</label><input name="senha" type="password">
    <button type="submit">Entrar</button>
  </form>
</main>
</body>
</html>
