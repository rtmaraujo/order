//package br.com.araujomarcos.order.application.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import br.com.araujomarcos.order.application.dto.OrderRequestDTO;
//import br.com.araujomarcos.order.application.dto.OrderResponseDTO;
//import lombok.AllArgsConstructor;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/produto-externo-a")
//public class ProductExternalA {
//	private RestTemplate restTemplate;
//	//Exemplo de uma das formas de requisição do serviço externo A
//	@PostMapping
//	public ResponseEntity<OrderResponseDTO> criarPedido(@RequestBody OrderRequestDTO requestDTO) {
//		String url = "(link unavailable)";
//		return ResponseEntity.ok(restTemplate.postForObject(url, requestDTO, OrderResponseDTO.class));
//	}
//}
