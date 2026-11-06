<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="pt_BR"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head><meta charset="UTF-8"><title>${titulo}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css"></head>
<body>
<header><h1>Loja do Curso</h1></header>
<main>
  <h2>${titulo}</h2>
  <table>
    <tr><th>Produto</th><th>Preço</th><th>Situação</th></tr>
    <c:forEach var="p" items="${produtos}">
      <tr>
        <td>${p.nome()}</td>
        <td class="num"><fmt:formatNumber value="${p.preco()}" type="currency"/></td>
        <td>${p.estoque() > 0 ? 'Em estoque' : 'Esgotado'}</td>
      </tr>
    </c:forEach>
  </table>
</main>
</body>
</html>
