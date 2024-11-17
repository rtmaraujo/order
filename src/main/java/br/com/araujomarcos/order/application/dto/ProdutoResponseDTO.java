package br.com.araujomarcos.order.application.dto;

public record ProdutoResponseDTO(
		Long id, 
		String nome, 
		Double preco, 
		Integer quantidadeEmEstoque) {

}
