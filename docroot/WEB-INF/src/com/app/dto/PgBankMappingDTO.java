package com.app.dto;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PgBankMappingDTO {

	private Integer pgBankMappingId;

	private String pgType;

	private String bankId;

	private String bankName;

	private String bankCodeNonPayu;
	
	private String logoUrl;
	
	private Integer sortOrder;

	public Integer getPgBankMappingId() {
		return pgBankMappingId;
	}

	public void setPgBankMappingId(Integer pgBankMappingId) {
		this.pgBankMappingId = pgBankMappingId;
	}

	public String getPgType() {
		return pgType;
	}

	public void setPgType(String pgType) {
		this.pgType = pgType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCodeNonPayu() {
		return bankCodeNonPayu;
	}

	public void setBankCodeNonPayu(String bankCodeNonPayu) {
		this.bankCodeNonPayu = bankCodeNonPayu;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	
	
	
}
