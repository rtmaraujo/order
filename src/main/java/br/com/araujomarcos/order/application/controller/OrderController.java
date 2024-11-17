package br.com.araujomarcos.order.application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.araujomarcos.order.application.dto.OrderRequestDTO;
import br.com.araujomarcos.order.application.dto.OrderResponseDTO;
import br.com.araujomarcos.order.application.service.OrderService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
		return ResponseEntity.ok(orderService.createOrder(orderRequest));
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
		return ResponseEntity.ok(orderService.getOrderById(id));
	}

	@GetMapping
	public ResponseEntity<List<OrderResponseDTO>> findAll() {
		return ResponseEntity.ok(orderService.findAll());
	}
}
