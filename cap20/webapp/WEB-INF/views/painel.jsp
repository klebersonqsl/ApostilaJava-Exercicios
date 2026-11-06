<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head><meta charset="UTF-8"><title>Painel</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css"></head>
<body>
<header><h1>Área do aluno
  <a href="${pageContext.request.contextPath}/logout">Sair</a></h1></header>
<main>
  <h2>Bem-vinda, ${sessionScope.usuarioLogado}!</h2>
  <p>Você abriu este painel <b>${visitas}</b> vez(es) nesta sessão.</p>
  <p>Identificador da sessão: <code>${pageContext.session.id}</code></p>
</main>
</body>
</html>
