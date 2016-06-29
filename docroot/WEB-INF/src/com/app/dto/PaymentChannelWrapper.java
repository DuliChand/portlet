package com.app.dto;

import java.util.List;


public class PaymentChannelWrapper extends BaseWrapper {

	private List<PaymentChannelDTO> paymentChannels;

	public List<PaymentChannelDTO> getPaymentChannels() {
		return paymentChannels;
	}

	public void setPaymentChannels(List<PaymentChannelDTO> paymentChannels) {
		this.paymentChannels = paymentChannels;
	}

}