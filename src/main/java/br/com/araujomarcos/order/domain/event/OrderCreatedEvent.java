package br.com.araujomarcos.order.domain.event;

import java.math.BigDecimal;
import java.util.List;

import br.com.araujomarcos.order.application.enums.OrderStatus;
import br.com.araujomarcos.order.domain.model.Order;
import br.com.araujomarcos.order.domain.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
	private Long pedidoId;
	private Long customerId;
	private List<OrderItem> items;
	private BigDecimal valorTotal;
	private OrderStatus status;

	public OrderCreatedEvent(Order order) {
		this.pedidoId = order.getId();
		this.customerId = order.getCustomerId();
		this.items = order.getItems();
		this.valorTotal = order.getTotalAmount();
		this.status = order.getStatus();
	}
}
