package br.com.araujomarcos.order.infrastructure.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.araujomarcos.order.application.enums.OrderStatus;
import br.com.araujomarcos.order.domain.event.OrderCreatedEvent;
import br.com.araujomarcos.order.domain.model.Order;
import br.com.araujomarcos.order.domain.model.OrderItem;
import br.com.araujomarcos.order.domain.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderConsumerTest {

    @InjectMocks
    private OrderConsumer orderConsumer;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void shouldConsumeOrderCreatedEventAndSaveOrder() {
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setCustomerId(123L);
        event.setStatus(OrderStatus.PENDENTE);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setPrice(new BigDecimal("50.00"));
        orderItem.setProductName("DOVE");
        orderItem.setQuantity(2);
        event.setItems(List.of(orderItem));
        
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderConsumer.consumeOrderCreatedEvent(event);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        Order savedOrder = orderCaptor.getValue();

        assertNotNull(savedOrder);
        assertEquals(123, savedOrder.getCustomerId());
        assertEquals(OrderStatus.PENDENTE, savedOrder.getStatus());
        assertEquals(1, savedOrder.getItems().size());

    }
}
