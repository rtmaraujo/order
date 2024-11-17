package br.com.araujomarcos.order.infrastructure.messaging;

import org.springframework.stereotype.Component;

import br.com.araujomarcos.order.domain.event.OrderCreatedEvent;
import br.com.araujomarcos.order.domain.model.Order;
import br.com.araujomarcos.order.domain.model.OrderItem;
import br.com.araujomarcos.order.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderConsumer {
	private final OrderRepository orderRepository;
	
	//Exemplo de uma das formas de requisição do serviço externo
//	@KafkaListener(topics = "pedidos", containerFactory = "messageKafkaListenerContainerFactory")
	public void consumeOrderCreatedEvent(OrderCreatedEvent event) {

		Order order = new Order();
		order.setCustomerId(event.getCustomerId());
		order.setStatus(event.getStatus());
		order.setItems(event.getItems().stream()
                .map(c -> OrderItem.builder()
                        .productId(c.getProductId())
                        .productName(c.getProductName())
                        .quantity(c.getQuantity())
                        .price(c.getPrice())
                        .order(order)
                        .build())
                .toList());
		orderRepository.save(order);
	}
}
