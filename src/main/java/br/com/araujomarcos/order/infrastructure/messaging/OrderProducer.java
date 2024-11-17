package br.com.araujomarcos.order.infrastructure.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.araujomarcos.order.domain.event.OrderCreatedEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderProducer {

	private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
	//Exemplo de uma das formas de envio p/ o serviço externo B
	public void sendCreatedEvent(OrderCreatedEvent event) {
		kafkaTemplate.send("pedidos", event);
	}
}
