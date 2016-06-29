package com.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orderHeader")
public class OrderHeaderDTO {

	private Long orderId;

	private Date createDate;

	private Integer modifiedBy;

	private Date modifiedDate;

	private String status;

	private BigDecimal totalBasePrice;

	private BigDecimal grandOrderValue;

	private BigDecimal totalAmountPaid;

	private BigDecimal totalShippingCharge;

	private BigDecimal totalStoredCredit;

	private OrderAddressDTO billingAddress;

	private Integer totalOrderItems;

	private BigDecimal totalRewardAmount;

	private CustomerDTO customer;

	private String userChannel;

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getStatus() {
		return status;
	}

	public BigDecimal getTotalBasePrice() {
		return totalBasePrice;
	}

	public BigDecimal getGrandOrderValue() {
		return grandOrderValue;
	}

	public BigDecimal getTotalAmountPaid() {
		return totalAmountPaid;
	}

	public BigDecimal getTotalShippingCharge() {
		return totalShippingCharge;
	}

	public BigDecimal getTotalStoredCredit() {
		return totalStoredCredit;
	}

	public OrderAddressDTO getBillingAddress() {
		return billingAddress;
	}

	public Integer getTotalOrderItems() {
		return totalOrderItems;
	}

	public BigDecimal getTotalRewardAmount() {
		return totalRewardAmount;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTotalBasePrice(BigDecimal totalBasePrice) {
		this.totalBasePrice = totalBasePrice;
	}

	public void setGrandOrderValue(BigDecimal grandOrderValue) {
		this.grandOrderValue = grandOrderValue;
	}

	public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}

	public void setTotalShippingCharge(BigDecimal totalShippingCharge) {
		this.totalShippingCharge = totalShippingCharge;
	}

	public void setTotalStoredCredit(BigDecimal totalStoredCredit) {
		this.totalStoredCredit = totalStoredCredit;
	}

	public void setBillingAddress(OrderAddressDTO billingAddress) {
		this.billingAddress = billingAddress;
	}

	public void setTotalOrderItems(Integer totalOrderItems) {
		this.totalOrderItems = totalOrderItems;
	}

	public void setTotalRewardAmount(BigDecimal totalRewardAmount) {
		this.totalRewardAmount = totalRewardAmount;
	}

	public String getUserChannel() {
		return userChannel;
	}

	public void setUserChannel(String userChannel) {
		this.userChannel = userChannel;
	}

}
