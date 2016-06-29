package com.app.dto;

public class OrderDTO {

	private String key;

	private String hash;

	private String txnid;

	private String amount;

	private String firstname;

	private String email;

	private String phone;

	private String productinfo;

	private String surl;

	private String furl;

	private String paymentCode;

	private String drop_category;

	private String url;

	private String pg;

	private String bankCode;

	private String lastName;

	private String address1;

	private String city;

	private String country;

	private String zipcode;

	private String offer_key;

	private String vpc_User;

	private String vpc_Merchant;

	private String vpc_AccessCode;

	private String securesecret;

	private String enforceMethod;

	private String udf2;

	private String udf4;

	private String message;

	private String grandPrice;

	private String deviceId;

	private String totalBasePrice;

	private String quantity;
	/*------------MWallet ------------------*/

	private String merchantName;

	private String redirecturl;

	private String mWalletUrl;

	private String mid;

	private String orderId;

	private String mWalletChecksum;

	private String mWalletTestUrl;

	/*--------------------Paytm------------------*/
	private String checksum;

	private String customerId;
	
	private String channelId;
	
	private String industryType;
	
	private String website;
	
	/*------------------store credit--------------*/
	
	private String credits;
	
	private String rewards;
	
	public String getTotalBasePrice() {
		return totalBasePrice;
	}

	public void setTotalBasePrice(String totalBasePrice) {
		this.totalBasePrice = totalBasePrice;
	}

	public String getGrandPrice() {
		return grandPrice;
	}

	public void setGrandPrice(String grandPrice) {
		this.grandPrice = grandPrice;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPg() {
		return pg;
	}

	public void setPg(String pg) {
		this.pg = pg;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getOffer_key() {
		return offer_key;
	}

	public void setOffer_key(String offer_key) {
		this.offer_key = offer_key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getDrop_category() {
		return drop_category;
	}

	public void setDrop_category(String drop_category) {
		this.drop_category = drop_category;
	}

	public String getVpc_User() {
		return vpc_User;
	}

	public void setVpc_User(String vpc_User) {
		this.vpc_User = vpc_User;
	}

	public String getVpc_Merchant() {
		return vpc_Merchant;
	}

	public void setVpc_Merchant(String vpc_Merchant) {
		this.vpc_Merchant = vpc_Merchant;
	}

	public String getVpc_AccessCode() {
		return vpc_AccessCode;
	}

	public void setVpc_AccessCode(String vpc_AccessCode) {
		this.vpc_AccessCode = vpc_AccessCode;
	}

	public String getSecuresecret() {
		return securesecret;
	}

	public void setSecuresecret(String securesecret) {
		this.securesecret = securesecret;
	}

	public String getEnforceMethod() {
		return enforceMethod;
	}

	public void setEnforceMethod(String enforceMethod) {
		this.enforceMethod = enforceMethod;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getRedirecturl() {
		return redirecturl;
	}

	public void setRedirecturl(String redirecturl) {
		this.redirecturl = redirecturl;
	}

	public String getmWalletUrl() {
		return mWalletUrl;
	}

	public void setmWalletUrl(String mWalletUrl) {
		this.mWalletUrl = mWalletUrl;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getmWalletChecksum() {
		return mWalletChecksum;
	}

	public void setmWalletChecksum(String mWalletChecksum) {
		this.mWalletChecksum = mWalletChecksum;
	}

	public String getmWalletTestUrl() {
		return mWalletTestUrl;
	}

	public void setmWalletTestUrl(String mWalletTestUrl) {
		this.mWalletTestUrl = mWalletTestUrl;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

}
