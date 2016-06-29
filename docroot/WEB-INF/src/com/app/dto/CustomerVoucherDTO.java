package com.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customerVoucher")
public class CustomerVoucherDTO extends BaseDTO {

	private long customerVoucherId;

	private Date startDate;

	private Date endDate;

	private String valueType;

	private BigDecimal value;

	private String voucherCode;

	private String status;

	private Date createDate;

	private Date modifiedDate;

	private Integer createdBy;

	private Integer modifiedBy;

	private Integer vendorId;

	private String description;

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getValueType() {
		return valueType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public String getStatus() {
		return status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public String getDescription() {
		return description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCustomerVoucherId() {
		return customerVoucherId;
	}

	public void setCustomerVoucherId(long customerVoucherId) {
		this.customerVoucherId = customerVoucherId;
	}

}
