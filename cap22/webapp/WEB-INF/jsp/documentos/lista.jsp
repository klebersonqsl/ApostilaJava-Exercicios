<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Documentos"/>
<%@ include file="/WEB-INF/jsp/comum/cabecalho.jspf" %>

<form method="post" action="${raiz}/documentos" enctype="multipart/form-data">
  <label for="arquivos">Enviar arquivos (até 10 MB cada)</label>
  <input id="arquivos" name="arquivos" type="file" multiple>
  <button type="submit">Enviar</button>
</form>

<h3>Arquivos guardados</h3>
<c:if test="${empty documentos}">
  <p>Nenhum documento enviado ainda.</p>
</c:if>
<c:if test="${not empty documentos}">
  <table>
    <tr><th>Arquivo</th><th>Tipo</th><th class="num">Tamanho</th><th>Enviado em</th></tr>
    <c:forEach var="d" items="${documentos}">
      <tr>
        <td><a href="${raiz}/documentos/baixar?id=${d.id}">
              <c:out value="${d.nome}"/></a></td>
        <td>${d.tipo}</td>
        <td class="num">${d.tamanhoFormatado}</td>
        <td>${d.dataFormatada}</td>
      </tr>
    </c:forEach>
  </table>
</c:if>

<%@ include file="/WEB-INF/jsp/comum/rodape.jspf" %>
