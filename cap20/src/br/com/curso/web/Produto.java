package br.com.curso.web;

import java.math.BigDecimal;

public record Produto(int id, String nome, BigDecimal preco, int estoque) {
}
