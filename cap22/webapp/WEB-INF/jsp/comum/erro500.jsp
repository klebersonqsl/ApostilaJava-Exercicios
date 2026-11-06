<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="titulo" value="Erro no servidor"/>
<%@ include file="/WEB-INF/jsp/comum/cabecalho.jspf" %>
<p class="erro">Desculpe, ocorreu um erro inesperado. A equipe foi avisada.</p>
<%-- o detalhe vai para o log, nunca para o usuário --%>
<% application.log("Erro em " + request.getAttribute("jakarta.servlet.error.request_uri"),
                   exception); %>
<a class="botao" href="${raiz}/produtos">Voltar aos produtos</a>
<%@ include file="/WEB-INF/jsp/comum/rodape.jspf" %>
