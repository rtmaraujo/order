package br.com.araujomarcos.order.domain.exception;

import org.springframework.http.HttpStatus;

public class OrderConflitException extends BusinessException {

	private static final long serialVersionUID = 1L;
	private static final String ORDER_EXIST = "Pedido já existe";

	public OrderConflitException() {
		super(HttpStatus.CONFLICT, ORDER_EXIST);
	}

}
