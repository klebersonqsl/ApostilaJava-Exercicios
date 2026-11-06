<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="EL e JSTL na prática"/>
<%@ include file="/WEB-INF/jsp/comum/cabecalho.jspf" %>
<%-- \${...} com barra mostra a expressão sem avaliá-la --%>
<c:set var="preco" value="${19.9}"/>
<c:set var="frutas" value="${['maçã', 'banana', 'uva']}"/>
<jsp:useBean id="agora" class="java.util.Date"/>

<table>
  <tr><th>Expressão</th><th>Resultado</th></tr>
  <tr><td><code>\${param.nome}</code></td><td>${param.nome}</td></tr>
  <tr><td><code>\${empty param.cidade}</code></td><td>${empty param.cidade}</td></tr>
  <tr><td><code>\${param.idade >= 18 ? 'adulto' : 'menor'}</code></td>
      <td>${param.idade >= 18 ? 'adulto' : 'menor'}</td></tr>
  <tr><td><code>\${10 / 4} e \${10 mod 3}</code></td><td>${10 / 4} e ${10 mod 3}</td></tr>
  <tr><td><code>\${preco * 2}</code></td><td>${preco * 2}</td></tr>
  <tr><td><code>\${frutas[1]}</code></td><td>${frutas[1]}</td></tr>
  <tr><td><code>\${pageContext.request.method}</code></td>
      <td>${pageContext.request.method}</td></tr>
  <tr><td><code>\${fn:length(frutas)}</code></td><td>${fn:length(frutas)}</td></tr>
  <tr><td><code>\${fn:toUpperCase(param.nome)}</code></td>
      <td>${fn:toUpperCase(param.nome)}</td></tr>
  <tr><td><code>\${fn:substring('JavaServer Pages', 0, 4)}</code></td>
      <td>${fn:substring('JavaServer Pages', 0, 4)}</td></tr>
  <tr><td><code>\${'&lt;b&gt;oi&lt;/b&gt;'}</code></td><td>${'<b>oi</b>'}</td></tr>
  <tr><td><code>&lt;c:out value="&lt;b&gt;oi&lt;/b&gt;"/&gt;</code></td>
      <td><c:out value="<b>oi</b>"/></td></tr>
  <tr><td><code>&lt;fmt:formatNumber value="1234.5" type="currency"/&gt;</code></td>
      <td><fmt:formatNumber value="1234.5" type="currency"/></td></tr>
  <tr><td><code>&lt;fmt:formatNumber value="0.256" type="percent"/&gt;</code></td>
      <td><fmt:formatNumber value="0.256" type="percent"/></td></tr>
  <tr><td><code>&lt;fmt:formatDate value="\${agora}" dateStyle="long"/&gt;</code></td>
      <td><fmt:formatDate value="${agora}" dateStyle="long"/></td></tr>
</table>

<h3>c:forEach com varStatus</h3>
<ul>
  <c:forEach var="f" items="${frutas}" varStatus="s">
    <li>${s.count}. ${f}<c:if test="${s.first}"> (primeira)</c:if>
        <c:if test="${s.last}"> (última)</c:if></li>
  </c:forEach>
</ul>
<%@ include file="/WEB-INF/jsp/comum/rodape.jspf" %>
