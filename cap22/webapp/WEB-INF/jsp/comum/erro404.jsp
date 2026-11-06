<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="titulo" value="Página não encontrada"/>
<%@ include file="/WEB-INF/jsp/comum/cabecalho.jspf" %>
<p class="erro">O endereço
  <code><c:out value="${requestScope['jakarta.servlet.error.request_uri']}"/></code>
  não existe.</p>
<a class="botao" href="${raiz}/produtos">Voltar aos produtos</a>
<%@ include file="/WEB-INF/jsp/comum/rodape.jspf" %>
