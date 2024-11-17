package br.com.araujomarcos.order.application.dto;

import java.math.BigDecimal;

public record OrderItemRequestDTO(
		Long productId, 
		String productName, 
		Integer quantity, 
		BigDecimal price) {
}
