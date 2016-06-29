package com.app.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPaymentWrapper extends BaseWrapper {

	private List<OrderPaymentDTO> orderPayment;

	public List<OrderPaymentDTO> getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(List<OrderPaymentDTO> orderPayment) {
		this.orderPayment = orderPayment;
	}

}
