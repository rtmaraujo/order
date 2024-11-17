package br.com.araujomarcos.order.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import br.com.araujomarcos.order.application.dto.OrderRequestDTO;
import br.com.araujomarcos.order.application.dto.OrderResponseDTO;
import br.com.araujomarcos.order.application.enums.OrderStatus;
import br.com.araujomarcos.order.application.service.OrderService;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() {
    	OrderRequestDTO orderRequest = new OrderRequestDTO(1L, List.of(), OrderStatus.CONCLUIDO);
        OrderResponseDTO expectedResponse = new OrderResponseDTO(1L, 1L, List.of(), BigDecimal.TEN, 
        		OrderStatus.CONCLUIDO);
        when(orderService.createOrder(orderRequest)).thenReturn(expectedResponse);

        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(orderRequest);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(orderService, times(1)).createOrder(orderRequest);
    }

    @Test
    void getOrder_ShouldReturnOrderById() {
        Long orderId = 1L;
        OrderResponseDTO expectedResponse = new OrderResponseDTO(1L, 1L, List.of(), BigDecimal.TEN, 
        		OrderStatus.CONCLUIDO);
        when(orderService.getOrderById(orderId)).thenReturn(expectedResponse);

        ResponseEntity<OrderResponseDTO> response = orderController.getOrder(orderId);

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void findAll_ShouldReturnAllOrders() {
        List<OrderResponseDTO> expectedOrders = List.of(new OrderResponseDTO(1L, 1L, List.of(), BigDecimal.TEN, 
        		OrderStatus.CONCLUIDO));
        when(orderService.findAll()).thenReturn(expectedOrders);

        ResponseEntity<List<OrderResponseDTO>> response = orderController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(expectedOrders, response.getBody());
        verify(orderService, times(1)).findAll();
    }
}
