package br.com.araujomarcos.order.application.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.araujomarcos.order.application.dto.OrderItemResponseDTO;
import br.com.araujomarcos.order.application.dto.OrderRequestDTO;
import br.com.araujomarcos.order.application.dto.OrderResponseDTO;
import br.com.araujomarcos.order.domain.event.OrderCreatedEvent;
import br.com.araujomarcos.order.domain.exception.OrderConflitException;
import br.com.araujomarcos.order.domain.exception.OrderNotFoundException;
import br.com.araujomarcos.order.domain.model.Order;
import br.com.araujomarcos.order.domain.model.OrderItem;
import br.com.araujomarcos.order.domain.repository.OrderRepository;
import br.com.araujomarcos.order.infrastructure.messaging.OrderProducer;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderProducer pedidoProducer;

	public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {

		Order order = new Order();
		order.setCustomerId(orderRequest.customerId());
		order.setStatus(orderRequest.status());
		order.setItems(orderRequest.items().stream().map(c -> OrderItem.builder().productId(c.productId())
				.productName(c.productName()).quantity(c.quantity()).price(c.price()).order(order).build()).toList());

        boolean hasDuplicateOrder = orderRepository.findByCustomerId(order.getCustomerId()).stream()
            .anyMatch(existingOrder -> isSameProductList(existingOrder.getItems(), order.getItems()));

        if (hasDuplicateOrder) {
        	throw new OrderConflitException();
        }

		var orderSave = orderRepository.save(order);
		pedidoProducer.sendCreatedEvent(new OrderCreatedEvent(orderSave));
		return mapToResponseDTO(orderSave);
	}
	
	public OrderResponseDTO getOrderById(Long id) {
		var order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
		return mapToResponseDTO(order);
	}

	public List<OrderResponseDTO> findAll() {
		return orderRepository.findAll().stream().map(this::mapToResponseDTO).toList();
	}
	
    private boolean isSameProductList(List<OrderItem> existingItems, List<OrderItem> newItems) {
        if (existingItems.size() != newItems.size()) {
            return false;
        }
        return newItems.stream().allMatch(newItem ->
            existingItems.stream().anyMatch(existingItem ->
                existingItem.getProductId().equals(newItem.getProductId()) &&
                Objects.equals(existingItem.getQuantity(), newItem.getQuantity()) &&
                existingItem.getPrice().compareTo(newItem.getPrice()) == 0
            )
        );
    }
	
	private OrderResponseDTO mapToResponseDTO(Order order) {
		return new OrderResponseDTO(order.getId(), order.getCustomerId(), order.getItems().stream()
				.map(c -> new OrderItemResponseDTO(c.getProductId(), c.getProductName(), c.getQuantity(), c.getPrice()))
				.toList(), order.getTotalAmount(), order.getStatus());
	}
}
