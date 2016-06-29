package com.app.dto;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDTO {

	private String cartId = "";

	private String deviceId = "";

	private BigDecimal grandOrderValue = new BigDecimal(0);

	private BigDecimal youPayValue = new BigDecimal(0);

	private BigDecimal totalBasePrice = new BigDecimal(0);

	private BigDecimal youSaveValue = new BigDecimal(0);

	private BigDecimal moreSavingValue = new BigDecimal(0);

	private BigDecimal rewardAmount = new BigDecimal(0);

	private String status = "";

	private Integer totalQuantity = 0;

	private BigDecimal shippingCharge = new BigDecimal(0);

	private BigDecimal storedCreditAmount = new BigDecimal(0);

	private List<OrderAddressDTO> orderAddresses;

	private CustomerDTO customer;

	private String errorCode;

	private String errorMessage;

	private BigDecimal codCAPLimit = new BigDecimal(0);

	private String paymentChannel;

	private String orderId;

	private String createdTime;

	private long overallExpectedTimeDelivery;
	
	private long overallExpectedDeliveryDate1;

	private long overallExpectedDeliveryDate2;

	private String userChannel;
	
	public CartDTO() {
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	
	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public BigDecimal getTotalBasePrice() {
		return totalBasePrice;
	}

	public void setTotalBasePrice(BigDecimal totalBasePrice) {
		this.totalBasePrice = totalBasePrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public BigDecimal getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(BigDecimal shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public boolean setRuleShippingCharge(String shippingCharge,
			String paymentChannel) {
		BigDecimal shippingChargeValue = new BigDecimal(shippingCharge);
		if (shippingChargeValue.compareTo(new BigDecimal(0)) > 0) {
			this.shippingCharge = this.getShippingCharge().add(
					shippingChargeValue);
		}
		return true;
	}

	public BigDecimal getStoredCreditAmount() {
		return storedCreditAmount;
	}

	public void setStoredCreditAmount(BigDecimal storedCreditAmount) {
		this.storedCreditAmount = storedCreditAmount;
	}

	public List<OrderAddressDTO> getOrderAddresses() {
		return orderAddresses;
	}

	public void setOrderAddresses(List<OrderAddressDTO> orderAddresses) {
		this.orderAddresses = orderAddresses;
	}

	public BigDecimal getGrandOrderValue() {
		return grandOrderValue;
	}

	public void setGrandOrderValue(BigDecimal grandOrderValue) {
		this.grandOrderValue = grandOrderValue;
	}

	public BigDecimal getYouSaveValue() {
		return youSaveValue;
	}

	public void setYouSaveValue(BigDecimal youSaveValue) {
		this.youSaveValue = youSaveValue;
	}

	public BigDecimal getMoreSavingValue() {
		return moreSavingValue;
	}

	public void setMoreSavingValue(BigDecimal moreSavingValue) {
		this.moreSavingValue = moreSavingValue;
	}

	public BigDecimal getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(BigDecimal rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getCodCAPLimit() {
		return codCAPLimit;
	}

	public void setCodCAPLimit(BigDecimal codCAPLimit) {
		this.codCAPLimit = codCAPLimit;
	}

	public boolean setRuleErrorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return true;
	}

	public boolean setRuleCodCAPLimit(String codCAPLimit) {
		if (codCAPLimit != null && codCAPLimit.length() > 0) {
			this.setCodCAPLimit(new BigDecimal(codCAPLimit));
		}
		return true;
	}

	

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public BigDecimal getYouPayValue() {
		return youPayValue;
	}

	public void setYouPayValue(BigDecimal youPayValue) {
		this.youPayValue = youPayValue;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public long getOverallExpectedTimeDelivery() {
		return overallExpectedTimeDelivery;
	}

	public void setOverallExpectedTimeDelivery(long overallExpectedTimeDelivery) {
		this.overallExpectedTimeDelivery = overallExpectedTimeDelivery;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public long getOverallExpectedDeliveryDate1() {
		return overallExpectedDeliveryDate1;
	}

	public void setOverallExpectedDeliveryDate1(long overallExpectedDeliveryDate1) {
		this.overallExpectedDeliveryDate1 = overallExpectedDeliveryDate1;
	}

	public long getOverallExpectedDeliveryDate2() {
		return overallExpectedDeliveryDate2;
	}

	public void setOverallExpectedDeliveryDate2(long overallExpectedDeliveryDate2) {
		this.overallExpectedDeliveryDate2 = overallExpectedDeliveryDate2;
	}
	public String getUserChannel() {
		return userChannel;
	}

	public void setUserChannel(String userChannel) {
		this.userChannel = userChannel;
	}

}
