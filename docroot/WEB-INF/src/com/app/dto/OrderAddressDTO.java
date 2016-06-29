package com.app.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderAddressDTO {

	private Integer orderAddressId;

	private String title = "";

	private String fullName = "";

	private String address1 = "";

	private String address2 = "";

	private String address3 = "";

	private String addressType = "";

	private String email = "";

	private String landMark = "";

	private String mobileNo;

	private String alternateNo;

	public Integer getOrderAddressId() {
		return orderAddressId;
	}

	public String getTitle() {
		return title;
	}

	public String getFullName() {
		return fullName;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getAddress3() {
		return address3;
	}

	public String getAddressType() {
		return addressType;
	}

	public String getEmail() {
		return email;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setOrderAddressId(Integer orderAddressId) {
		this.orderAddressId = orderAddressId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getAlternateNo() {
		return alternateNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setAlternateNo(String alternateNo) {
		this.alternateNo = alternateNo;
	}

}
