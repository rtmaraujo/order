package br.com.araujomarcos.order.application.enums;

public enum OrderStatus {
	PENDENTE("Pendente"), 
	EM_PROCESSAMENTO("Em processamento"), 
	CONCLUIDO("Concluído"), 
	CANCELADO("Cancelado");

	private final String descricao;

	OrderStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
