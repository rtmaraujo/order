package br.com.araujomarcos.order.domain.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BusinessException {
	private static final long serialVersionUID = 1L;
	private static final String ORDER_NOT_FOUND = "Pedido não encontrado";
	
	public OrderNotFoundException() {
		super(HttpStatus.NOT_FOUND, ORDER_NOT_FOUND);
	}
}

