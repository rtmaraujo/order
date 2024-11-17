package br.com.araujomarcos.order.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.araujomarcos.order.application.dto.OrderRequestDTO;
import br.com.araujomarcos.order.application.dto.OrderResponseDTO;
import br.com.araujomarcos.order.application.enums.OrderStatus;
import br.com.araujomarcos.order.domain.event.OrderCreatedEvent;
import br.com.araujomarcos.order.domain.exception.OrderNotFoundException;
import br.com.araujomarcos.order.domain.model.Order;
import br.com.araujomarcos.order.domain.model.OrderItem;
import br.com.araujomarcos.order.domain.repository.OrderRepository;
import br.com.araujomarcos.order.infrastructure.messaging.OrderProducer;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProducer orderProducer;

    @Test
    void shouldCreateOrderSuccessfully() {
        OrderRequestDTO requestDTO = new OrderRequestDTO(
                1L,
                List.of(),
                OrderStatus.PENDENTE
        );

        when(orderRepository.findByCustomerId(1L)).thenReturn(List.of());
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        OrderResponseDTO responseDTO = orderService.createOrder(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.customerId());
        assertEquals(OrderStatus.PENDENTE, responseDTO.status());

        verify(orderProducer, times(1)).sendCreatedEvent(any(OrderCreatedEvent.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }


    @Test
    void shouldGetOrderByIdSuccessfully() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId(1L);
        order.setItems(List.of(
                OrderItem.builder()
                        .productId(1L)
                        .productName("Product 1")
                        .quantity(2)
                        .price(new BigDecimal("50.00"))
                        .build()
        ));
        order.setStatus(OrderStatus.PENDENTE);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponseDTO responseDTO = orderService.getOrderById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals(1L, responseDTO.customerId());
        assertEquals(1, responseDTO.items().size());
        assertEquals(OrderStatus.PENDENTE, responseDTO.status());
    }

    @Test
    void shouldThrowOrderNotFoundExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }
}