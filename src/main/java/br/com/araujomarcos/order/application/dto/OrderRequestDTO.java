package br.com.araujomarcos.order.application.dto;

import java.util.List;

import br.com.araujomarcos.order.application.enums.OrderStatus;

public record OrderRequestDTO(
		Long customerId,
		List<OrderItemRequestDTO> items,
		OrderStatus status) {
}
