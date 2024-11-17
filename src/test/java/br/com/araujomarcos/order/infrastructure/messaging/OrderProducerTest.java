package br.com.araujomarcos.order.infrastructure.messaging;

import br.com.araujomarcos.order.domain.event.OrderCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class OrderProducerTest {

    @Mock
    private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @InjectMocks
    private OrderProducer orderProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendCreatedEvent_ShouldSendEventSuccessfully() {
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setCustomerId(1L);
        orderProducer.sendCreatedEvent(event);
        verify(kafkaTemplate, times(1)).send("pedidos", event);
    }
}
