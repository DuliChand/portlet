package com.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {

	private Integer customerId;

	private String loginId;

	private String loginType;

	private Date registrationDate;

	private String title;

	private String firstName;

	private String lastName;

	private String gender;

	private Date dateOfBirth;

	private String password;

	private String status;

	private String registrationEmailSend;

	private String state;

	private String maritalStatus;

	private Date anniverseryDate;

	private Date createDate;

	private Date modifiedDate;

	private Integer modifiedBy;

	private Integer createdBy;

	private String siteLink;

	private BigDecimal totalStoredCredit;

	private BigDecimal totalPaidAmount;

	private String statusChangeReason;

	public Integer getCustomerId() {
		return customerId;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getLoginType() {
		return loginType;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public String getTitle() {
		return title;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getGender() {
		return gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public String getStatus() {
		return status;
	}

	public String getState() {
		return state;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public Date getAnniverseryDate() {
		return anniverseryDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public String getSiteLink() {
		return siteLink;
	}

	public BigDecimal getTotalStoredCredit() {
		return totalStoredCredit;
	}

	public BigDecimal getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public String getStatusChangeReason() {
		return statusChangeReason;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setAnniverseryDate(Date anniverseryDate) {
		this.anniverseryDate = anniverseryDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public void setSiteLink(String siteLink) {
		this.siteLink = siteLink;
	}

	public void setTotalStoredCredit(BigDecimal totalStoredCredit) {
		this.totalStoredCredit = totalStoredCredit;
	}

	public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}

	public String getRegistrationEmailSend() {
		return registrationEmailSend;
	}

	public void setRegistrationEmailSend(String registrationEmailSend) {
		this.registrationEmailSend = registrationEmailSend;
	}

}
