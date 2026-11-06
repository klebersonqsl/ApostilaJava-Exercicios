<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Produtos"/>
<%@ include file="/WEB-INF/jsp/comum/cabecalho.jspf" %>

<form class="busca" method="get" action="${raiz}/produtos">
  <input type="search" name="q" value="${fn:escapeXml(q)}"
         placeholder="Buscar por nome ou categoria">
  <button type="submit">Buscar</button>
  <a class="botao secundario" href="${raiz}/produtos/novo">+ Novo produto</a>
</form>

<c:choose>
  <c:when test="${empty produtos}">
    <p class="erro">Nenhum produto encontrado para "<c:out value="${q}"/>".</p>
  </c:when>
  <c:otherwise>
    <table>
      <tr><th>Foto</th><th>Produto</th><th>Categoria</th>
          <th class="num">Preço</th><th class="num">Estoque</th><th></th></tr>
      <c:forEach var="p" items="${produtos}" varStatus="s">
        <tr>
          <td>
            <c:choose>
              <c:when test="${p.temFoto}">
                <img class="miniatura" src="${raiz}/foto?id=${p.id}" alt="">
              </c:when>
              <c:otherwise><div class="miniatura"></div></c:otherwise>
            </c:choose>
          </td>
          <td><c:out value="${p.nome}"/></td>
          <td><c:out value="${p.categoria}"/></td>
          <td class="num"><fmt:formatNumber value="${p.preco}" type="currency"/></td>
          <%-- estoque baixo em destaque --%>
          <td class="num ${p.estoque < 10 ? 'baixo' : ''}">${p.estoque}</td>
          <td class="acoes">
            <a class="botao secundario"
               href="${raiz}/produtos/editar?id=${p.id}">Editar</a>
            <form method="post" action="${raiz}/produtos/excluir"
                  onsubmit="return confirm('Excluir este produto?')">
              <input type="hidden" name="id" value="${p.id}">
              <button class="perigo">Excluir</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
    <p>${fn:length(produtos)} produto(s)<c:if test="${not empty q}">
       para "<c:out value="${q}"/>"</c:if>.</p>
  </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/comum/rodape.jspf" %>
