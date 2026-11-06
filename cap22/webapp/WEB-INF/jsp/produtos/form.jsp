<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="${empty produto.id ? 'Novo produto' : 'Editar produto'}"/>
<%@ include file="/WEB-INF/jsp/comum/cabecalho.jspf" %>

<c:if test="${not empty erros}">
  <p class="erro">Corrija os ${fn:length(erros)} campo(s) indicados abaixo.</p>
</c:if>

<%-- reexibe o que foi digitado (param) ou os dados do produto --%>
<fmt:formatNumber var="precoProduto" value="${produto.preco}"
                  minFractionDigits="2" maxFractionDigits="2" groupingUsed="false"/>
<c:set var="preco" value="${empty param.preco ? precoProduto : param.preco}"/>
<c:set var="estoque" value="${empty param.estoque ? produto.estoque : param.estoque}"/>

<%-- enctype multipart/form-data é obrigatório para enviar arquivos --%>
<form method="post" action="${raiz}/produtos/salvar" enctype="multipart/form-data">
  <input type="hidden" name="id" value="${produto.id}">

  <label for="nome">Nome</label>
  <input id="nome" name="nome" value="${fn:escapeXml(produto.nome)}">
  <span class="campo-erro">${erros.nome}</span>

  <div class="linha">
    <div>
      <label for="categoria">Categoria</label>
      <select id="categoria" name="categoria">
        <c:forEach var="cat" items="${categorias}">
          <option ${cat == produto.categoria ? 'selected' : ''}>${cat}</option>
        </c:forEach>
      </select>
    </div>
    <div>
      <label for="preco">Preço (R$)</label>
      <input id="preco" name="preco" value="${fn:escapeXml(preco)}" inputmode="decimal">
    </div>
    <div>
      <label for="estoque">Estoque</label>
      <input id="estoque" name="estoque" value="${fn:escapeXml(estoque)}">
    </div>
  </div>
  <span class="campo-erro">${erros.preco} ${erros.estoque}</span>

  <label for="foto">Foto</label>
  <c:if test="${produto.temFoto}">
    <img class="foto-atual" src="${raiz}/foto?id=${produto.id}" alt="Foto atual">
    <br><small>Escolha um arquivo apenas se quiser trocar a foto.</small>
  </c:if>
  <input id="foto" name="foto" type="file" accept="image/*">
  <span class="campo-erro">${erros.foto}</span>

  <div>
    <button type="submit">Salvar</button>
    <a class="botao secundario" href="${raiz}/produtos">Cancelar</a>
  </div>
</form>

<%@ include file="/WEB-INF/jsp/comum/rodape.jspf" %>
