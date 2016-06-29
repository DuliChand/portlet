package com.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPaymentDTO {

	private Integer orderPaymentId;

	private String bankCode;

	private String bankGatewayId;

	private String bankRefNo;

	private String bankTransactionId;

	private String paymentErrorCode;

	private String paymentErrorMsg;

	private Date paymentRequestDate;

	private Date paymentResponseDate;

	private String paymentStatus;

	private String paymentUnMappedStatus;

	private String pgBankType;

	private String mihId;

	private BigDecimal paidAmount;

	private PaymentChannelDTO paymentChannel;

	private Date createDate;

	private Date modifiedDate;

	private Integer orderId;

	public Integer getOrderPaymentId() {
		return orderPaymentId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBankGatewayId() {
		return bankGatewayId;
	}

	public String getBankRefNo() {
		return bankRefNo;
	}

	public String getBankTransactionId() {
		return bankTransactionId;
	}

	public String getPaymentErrorCode() {
		return paymentErrorCode;
	}

	public String getPaymentErrorMsg() {
		return paymentErrorMsg;
	}

	public Date getPaymentRequestDate() {
		return paymentRequestDate;
	}

	public Date getPaymentResponseDate() {
		return paymentResponseDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public String getPaymentUnMappedStatus() {
		return paymentUnMappedStatus;
	}

	public String getPgBankType() {
		return pgBankType;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public PaymentChannelDTO getPaymentChannel() {
		return paymentChannel;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderPaymentId(Integer orderPaymentId) {
		this.orderPaymentId = orderPaymentId;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankGatewayId(String bankGatewayId) {
		this.bankGatewayId = bankGatewayId;
	}

	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}

	public void setBankTransactionId(String bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	public void setPaymentErrorCode(String paymentErrorCode) {
		this.paymentErrorCode = paymentErrorCode;
	}

	public void setPaymentErrorMsg(String paymentErrorMsg) {
		this.paymentErrorMsg = paymentErrorMsg;
	}

	public void setPaymentRequestDate(Date paymentRequestDate) {
		this.paymentRequestDate = paymentRequestDate;
	}

	public void setPaymentResponseDate(Date paymentResponseDate) {
		this.paymentResponseDate = paymentResponseDate;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public void setPaymentUnMappedStatus(String paymentUnMappedStatus) {
		this.paymentUnMappedStatus = paymentUnMappedStatus;
	}

	public void setPgBankType(String pgBankType) {
		this.pgBankType = pgBankType;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public void setPaymentChannel(PaymentChannelDTO paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getMihId() {
		return mihId;
	}

	public void setMihId(String mihId) {
		this.mihId = mihId;
	}

}
