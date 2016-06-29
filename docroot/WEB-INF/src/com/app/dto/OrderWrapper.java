package com.app.dto;



import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "orderWrapper")
public class OrderWrapper extends BaseWrapper {

	private List<OrderHeaderDTO> orderHeaders;

	public List<OrderHeaderDTO> getOrderHeaders() {
		return orderHeaders;
	}

	public void setOrderHeaders(List<OrderHeaderDTO> orderHeaders) {
		this.orderHeaders = orderHeaders;
	}

}
