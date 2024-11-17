package br.com.araujomarcos.order.application.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.araujomarcos.order.application.enums.OrderStatus;

public record OrderResponseDTO(
		Long id,
		Long customerId,
		List<OrderItemResponseDTO> items, 
		BigDecimal valorTotal,
		OrderStatus status) {
}