package com.app.dto;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentChannelDTO {

	private Integer paymentChannelId;

	private String paymentName;

	private String paymentChannelCode;

	private Double priceExclVat;

	private Double priceInclVat;

	private Double priceVatInPercent;

	private String status;

	private Integer visibilityWeighting;

	private Integer isVisible;

	private Integer isApproved;
	
	private Integer creditDebitChannel;

	private List<PgBankMappingDTO> pgBankMappings;

	public Integer getPaymentChannelId() {
		return paymentChannelId;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public String getPaymentChannelCode() {
		return paymentChannelCode;
	}

	public Double getPriceExclVat() {
		return priceExclVat;
	}

	public Double getPriceInclVat() {
		return priceInclVat;
	}

	public Double getPriceVatInPercent() {
		return priceVatInPercent;
	}

	public String getStatus() {
		return status;
	}

	public Integer getVisibilityWeighting() {
		return visibilityWeighting;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setPaymentChannelId(Integer paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public void setPaymentChannelCode(String paymentChannelCode) {
		this.paymentChannelCode = paymentChannelCode;
	}

	public void setPriceExclVat(Double priceExclVat) {
		this.priceExclVat = priceExclVat;
	}

	public void setPriceInclVat(Double priceInclVat) {
		this.priceInclVat = priceInclVat;
	}

	public void setPriceVatInPercent(Double priceVatInPercent) {
		this.priceVatInPercent = priceVatInPercent;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setVisibilityWeighting(Integer visibilityWeighting) {
		this.visibilityWeighting = visibilityWeighting;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Integer getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Integer isApproved) {
		this.isApproved = isApproved;
	}

	public List<PgBankMappingDTO> getPgBankMappings() {
		return pgBankMappings;
	}

	public void setPgBankMappings(List<PgBankMappingDTO> pgBankMappings) {
		this.pgBankMappings = pgBankMappings;
	}

	public Integer getCreditDebitChannel() {
		return creditDebitChannel;
	}

	public void setCreditDebitChannel(Integer creditDebitChannel) {
		this.creditDebitChannel = creditDebitChannel;
	}
	
}
