<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.liferay.portal.util.PortalUtil,java.net.*"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<portlet:defineObjects />

<%
	/**
	 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
	 *
	 * This library is free software; you can redistribute it and/or modify it under
	 * the terms of the GNU Lesser General Public License as published by the Free
	 * Software Foundation; either version 2.1 of the License, or (at your option)
	 * any later version.
	 *
	 * This library is distributed in the hope that it will be useful, but WITHOUT
	 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
	 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
	 * details.
	 */
%>

<portlet:defineObjects />
<style>
#backbone-portlet-cart-content,#backbone-portlet-cart-nav {
	display: block !important;
}

#codOptionVal- ${payOption}
{
}
.marketing-popup .cart-nav {
	display: none !important;
}

#p_p_id_navigation_WAR_appportlet_,#p_p_id_cart_WAR_appportlet_ {
	display: none;
}
</style>
<main role="payment-main"
	data-context-path="${renderRequest.contextPath}"
	id="backbone-portlet-payment"></main>

<div class="col-sm-12 col-md-8">
	<div class="payment-section-area">
		<div class="payment-section">
			<ul>
				<c:forEach items="${paymentChannelWrapper.paymentChannels}"
						var="payment">
						<c:if test="${payment.paymentChannelCode eq 'PAYUCC' || payment.paymentChannelCode eq 'HDFCCC'}">
						<li class="dbit payListTab col-xs-6 col-sm-12 col-md-12"
							id="credit"
							onClick="setCreditCardGateway('${payment.paymentChannelCode}','CC');updateOrderSummary()"><a
							href="javascript:void(0);"><span class="icontype icon-debit"></span> Credit</a></li>
						<li class="dbit payListTab col-xs-6 col-sm-12 col-md-12"
							id="debit"
							onClick="setCreditCardGateway('${payment.paymentChannelCode}','DC');updateOrderSummary()"><a
							href="javascript:void(0);"><span class="icontype icon-debit"></span> Debit</a></li>
						</c:if>
						
				</c:forEach>
				
				<c:forEach items="${mode}" var="payMode">
					<c:if test="${payMode eq 'NB'}">
						<li id="netbanking"
							class="payListTab col-xs-6 col-sm-12 col-md-12"
							onClick="setPaymentMethod('NB');updateOrderSummary()"><a
							href="javascript:void(0);"><span class="icontype icon-netbanking"></span>
								NetBanking</a></li>
					</c:if>
					<c:if test="${payMode eq 'WALL'}">
						<li id="wallet" class="payListTab col-xs-6 col-sm-12 col-md-12"
							onclick="removeSelection();updateOrderSummary()"><a href="javascript:void(0);"><span
								class="icontype icon-wallet"></span> Wallet</a></li>
					</c:if>
					<!-- <c:if test="${payMode eq 'PAYTM'}">
						<li id="paytm" class="payListTab col-xs-6 col-sm-12 col-md-12"
							onclick="removeSelection();updateOrderSummary();showProceed(); setWalletMethod('PT');"><a href="javascript:void(0);"><span
								class="icontype icon-paytm"></span> Paytm</a></li>
					</c:if>-->
					<c:if test="${payMode eq 'COD' && payOption ne '1'}">
						<li id="cod" class="payListTab col-xs-6 col-sm-12 col-md-12"
							onClick="setPaymentMethod('COD');checkshippingmethod()"><a
							href="javascript:void(0);"><span class="icontype icon-cod"></span> COD</a></li>
					</c:if>

				</c:forEach>
			</ul>
		</div>
		<div class="container-payment">
			<div class="divcontent paymethod4" id="creditContent">
				<div class="paymentSecure">
					<span class="payUsing">Pay using</span>
					<ul class="PaySecureA">
					<li class="visaSecure"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/visa-a.png"></li>
					<li class="MstSecure"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/master-car.png"></li>
					<li class="AmerSecure"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/american.png"></li>
					<li class="doubSecure"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/dinners-club.png"></li>
					</ul>
			    </div>
				<p>Dear Customer.</p>				
				<p class="card-visa">Click 'Pay' to complete the transaction through a secure payment gateway.</p>

			</div>
			<div class="divcontent paymethod4" id="debitContent">
				<div class="paymentSecure">
					<!-- <span class="payUsing">Pay using</span> -->
					<ul class="PaySecureA">
					<li class="visaSecure"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/visa-a.png"></li>
					<li class="MstSecure"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/master-car.png"></li>
					<li class="AmerSecure"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/american.png"></li>
					<li class="doubSecure"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/dinners-club.png"></li>
					</ul>
			    </div>
				<p>Dear Customer.</p>				
				<p class="card-visa">Click 'Pay' to complete the transaction through a secure payment gateway.</p>

			</div>

			<div class="divcontent paymethod2 " id="netbankingContent">
				<div class="paymentSecure">
					<!-- <span class="payUsing">Pay using</span> -->
					<ul class="PaySecureA">
           				 <li><span class="payUsing">Select Your Bank</span></li>
					</ul>
			    </div>
				<div class="breakpoint2"><p class="bankP">Popular Banks</p>
					<c:forEach items="${priorBank}"	var="prBank">
						<c:if test="${not empty prBank}">
							<div class="col-md-4 col-sm-4 col-xs-6 ">
								<div class="col-Border">
									<div class="borderImg">
										<input type="radio" name="bankSelect" id="bankSelectId"
											value="${prBank.bankId}" class="radio"
											onchange="setBankCode(this.value);showProceed();removeBankSelection();" />
										<label class="bank-${prBank.bankId}"><span class="ie8-icon"></span></label>
									</div>
								</div>	
								</div>	
								</c:if>							
					</c:forEach>
				<h1 class="clear text-center orClass">OR</h1>
				
					<div class="bankSelection">
					<span class="text-Label">Select Other Bank</span>
						<select class="selectdrop"
							id="otherBankSelectId" name="bankSelect"
							onchange="setBankCode(this.value);showProceed();removeBankRadio(this.value);">
							<option value="None">Select Bank</option>
							<c:forEach items="${otherBank}"	var="othBnk">
								<c:if test="${not empty othBnk}">
									<option value="${othBnk.bankId}">${othBnk.bankName}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>

			<div class="divcontent paymethod4 " id="walletContent">
					<div class="paymentSecure">
						<!-- <span class="payUsing">Pay using</span> -->
						<ul class="PaySecureA">
	           				 <li><span class="payUsing">Select Your Wallet</span></li>
						</ul>
				    </div>
				<div class="breakpoint2">    
				<c:forEach items="${paymentChannelWrapper.paymentChannels}"
					var="payment">
					<c:if test="${payment.paymentChannelCode eq 'PAYTM'}">
						<div class="col-md-6 col-sm-6 col-xs-6  ">
						<div class="col-Border">
						<div class="borderImg1">
							<input type="radio" name="walletOption" id="paytmId"
								value="paytmId" class="radio"
								onchange="showProceed(); setWalletMethod('PT');" /> <label
								class="PAYTM-BUTTON" for="paytmId"><span
								class="ie8-icon"></span>Paytm</label>
						</div>
						</div>
						</div>
					</c:if>
					<c:if test="${payment.paymentChannelCode eq 'PAYUWALL'}">
						<div class="col-md-6 col-sm-6 col-xs-6 widthincrease ">
						<div class="col-Border">
						<div class="borderImg1">
							<input type="radio" name="walletOption" id="payUMoneyId"
								class="radio" onchange="showProceed(); setWalletMethod('PUM');" />
							<label class="payUMoneybutton" for="payUMoneyId"><span
								class="ie8-icon"></span>PayUMoney</label>
						</div>
						</div>
						</div> 
						
					</c:if>
					<c:if test="${payment.paymentChannelCode eq 'MWALL'}">
						<div class="col-md-6 col-sm-6 col-xs-6 ">
						<div class="col-Border">
						<div class="borderImg1">
							<input type="radio" name="walletOption" class="radio"
								id="mobiKwikId" onchange="showProceed(); setWalletMethod('WL');" />
							<label class="MOBIKWIK-BUTTON" for="mobiKwik"><span
								class="ie8-icon"></span>MobiKwik</label>
						</div>
						</div>
						</div>
					</c:if>
				</c:forEach>
				<div class="clearfix"></div>
				<p>Click 'Pay' to complete the transaction.<p>
				</div>	
			</div>
			<div class="divcontent paymethod7" id="paytmContent">
				<div class="paymentSecure">
					<ul class="PaySecureA">
           				 <li><span class="payUsing">Pay using </span><span class="PAYTM-BUTTON"></span></li>
					</ul></div>
					<div class="clear">
				</div>
				<p>Dear Customer.</p>	
				<p style="display:inline-flex">Pay with	<span class="PAYTM-BUTTON"></span> for extra 15% Cashback*.	</p>					
				<p class="card-visa">Click 'Pay' to complete the transaction through a secure payment gateway.</p>
			   
			</div>
			<div class="divcontent paymethod5" id="codContent">
				<div class="paymentSecure">
					<!-- <span class="payUsing">Pay using</span> -->
					<ul class="PaySecureA">
           				 <li><span class="payUsing">Select from Cash-On-Delivery</span></li>
					</ul>
			    </div>
			  <div class="payableAmount"><p><span class="money">&nbsp;</span>Amount Payable on delivery <span class="rupee"  id="codPayId">&nbsp;</span><span id="payAmountCodId"></span></p></div>
				<div class="payment-typebox">
					<ul>
						<li> <span class="IVRContact">&nbsp;</span>You will recieve an IVR call from <strong>+91-124 4132000</strong> to confirm your order. <p class="center"><strong>OR</strong></p></li>
						<li><span class="confirm">&nbsp;</span>Call us at <strong> +91-124-4125000 </strong> to confirm your order</li>
						<li><span Class="telNo">&nbsp;</span> Your contact number.<strong>+91-${customerPhoneNumber}</strong> </li>
					</ul>
					<div class="clear"></div>
					<p class="placing">By placing this order, you agree to the Terms of use of www.fashionandyou.com</p>
				</div>
			</div>
			<div class="goPayment btn-proceed pull-down">
				   <ul class="securePay">
   					    <li class="bgimg"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/versionsecure.png"></li>
					     <li class="bgimg"><img src="http://cdn1.fashionandyou.com/fny-static/app-images/safe-shopping.png"></li>
					    <li><a href="javascript:void(0);" onclick="submitPaymentForm()">PAY</a></li>
				  </ul>
			</div>
			
			
			<div class="cod-btn-proceed pull-down">
			<a href="javascript:void(0);" onclick="submitPaymentForm()">PLACE ORDER</a>
			</div>

		</div>
	</div>
</div>
<div class="ordersumContainer">
	<div id="left" class="sidebar-floating">
		<div class="order-sum-outer" id="sidebar">
			<div class="order-sum-box"></div>
		</div>
	</div>
</div>

<form action="<portlet:actionURL />" method="post" id="paymentFormId">
	<input type="hidden" name="<portlet:namespace/>action"
		value="doPayment" id="actionID" /> <input type="hidden"
		name="<portlet:namespace/>orderId" value="${orderId}" id="orderID"
		readonly="readonly" /> <input type="hidden"
		name="<portlet:namespace/>deviceId" value="" id="deviceID" /> <input
		type="hidden" name="<portlet:namespace/>paymentChannel" value="${creditCardGateway}"
		id="paymentChannelID" /> <input type="hidden"
		name="<portlet:namespace/>paymentMethod" value="${cardGatewayName}"
		id="paymentMethodID" /> <input type="hidden"
		name="<portlet:namespace/>bankCode" value="" id="bankCodeID" /> <input
		type="hidden" name="<portlet:namespace/>pg" value="CC" id="pgID" />

</form>

<script type="text/javascript">
	var cartObjectVal ;
	function setBankCode(value) {
		$("#bankCodeID").val(value);
		$("#paymentMethodID").val("payU");
		$("#paymentChannelID").val("PAYUCC");
		$('.btn-proceed a').css('pointer-events','visible');
		$('.btn-proceed a').css('background-color','#d22573');
	}
	 
	function setCardType(){
		$("#pgID").val("CC");
		var cardType = $( "input:radio[name=debitCreditOption]:checked" ).val();
		$("#paymentChannelID").val(cardType); 
		if(cardType == "PAYUCC"){
			$("#paymentMethodID").val("payU");
			$("#paymentChannelID").val("PAYUCC");
			$("#bankCodeID").val("");
			}
		if(cardType == "HDFCCC"){
			$("#paymentMethodID").val("HDFC");	
			$("#paymentChannelID").val("HDFCCC");
			$("#bankCodeID").val("");
		}
		
	}
	function setCreditCardGateway(value, chn){					
		$('.cod-btn-proceed').hide();
		$('.btn-proceed').show();	
		$('.btn-proceed a').css('pointer-events','visible');
		$('.btn-proceed a').css('background-color','#d22573');
		if(value == "PAYUCC"){
			$("#paymentMethodID").val("payU");
			$("#paymentChannelID").val("PAYUCC");
			$("#bankCodeID").val("");
			$("#pgID").val(chn);
			}
		if(value == "HDFCCC"){
			$("#pgID").val("CC");
			$("#paymentMethodID").val("HDFC");	
			$("#paymentChannelID").val("HDFCCC");
			$("#bankCodeID").val("");
		}
		
	}
	function setPaymentMethod(value) {
		$(".container-payment input[type='radio']").removeAttr('checked');
		$(".container-payment select").val("None");
		
		$('.cod-btn-proceed').hide();
		
		sessionStorage.setItem("REWARD_POINTS","");
		sessionStorage.setItem("CREDITS","");
		sessionStorage.setItem("VOUCHERS","");
		if (value == "CC") {
			$("#paymentMethodID").val("payU");
			$("#pgID").val("CC");
			$("#paymentChannelID").val("PAYUCC");
			$('.btn-proceed').show();	
			$('.btn-proceed a').css('pointer-events','visible');
			$('.btn-proceed a').css('background-color','#d22573');
		}
		if (value == "NB") {
			$("#paymentMethodID").val("payU");
			$("#pgID").val("NB");
			$("#paymentChannelID").val("PAYUNB");
			$('.btn-proceed a').css('pointer-events','none');
			$('.btn-proceed a').css('background-color','#d36f9c');
			$('.btn-proceed').show();
		}
		if (value == "COD") {			
			$("#paymentMethodID").val("COD");
			$("#paymentChannelID").val("COD");
			$("#bankCodeID").val("");
			$("#pgID").val("");
			$('.btn-proceed').hide();
			$('.cod-btn-proceed').show();
		}
		
		if (value == "PUM") {			
			$("#paymentMethodID").val("PAYUMONEY");
			$("#bankCodeID").val("PAYUW");
			$("#pgID").val("WALLET");
			$("#paymentChannelID").val("PAYUMONEY");
		}
	}
	function removeSelection(){
		$(".container-payment input[type='radio']").removeAttr('checked');
		$(".container-payment select").val("None");
		$('.btn-proceed a').css('pointer-events','none');
		$('.btn-proceed a').css('background-color','#d36f9c');
		
		$('.btn-proceed').show();
		$('.cod-btn-proceed').hide();
		
	}
	function setWalletMethod(value) {
		
		sessionStorage.setItem("REWARD_POINTS","");
		sessionStorage.setItem("CREDITS","");
		sessionStorage.setItem("VOUCHERS","");
		
		if (value == "WL") {
			$("#paymentMethodID").val("MWALL");
			$("#pgID").val("");
			$("#paymentChannelID").val("MWALL");
			$('.btn-proceed a').css('pointer-events','visible');
			$('.btn-proceed a').css('background-color','#d22573');
		}
		if (value == "PT") {
			$("#paymentMethodID").val("PAYTM");
			$("#pgID").val("");
			$("#paymentChannelID").val("PAYTM");
			$('.btn-proceed a').css('pointer-events','visible');
			$('.btn-proceed a').css('background-color','#d22573');

		}
		if (value == "PUM") {			
			$("#paymentMethodID").val("PAYUMONEY");
			$("#bankCodeID").val("PAYUW");
			$("#pgID").val("WALLET");
			$("#paymentChannelID").val("PAYUMONEY");
			$('.btn-proceed a').css('pointer-events','visible');
			$('.btn-proceed a').css('background-color','#d22573');

		}
		
	}

	function submitPaymentForm() {
		$(".payment-section li").each(function(index) {
			
			if ($("li").hasClass("active")) {
				$(this).text();
				}
		});
		$("#deviceID").val("${cookie.FNY_TOKEN_ONE.value}");
		$("#paymentFormId").submit();
	}

	$('.payubutton').on('click', function() {
		$(this).toggleClass('label-checked');
	});
	
	$('.mobikwikbutton').on('click', function() {
		$(this).toggleClass('label-checked');
	});
	
	$('.payUMoneybutton').on('click', function() {
		$(this).toggleClass('label-checked');
	});
	
	function showProceed(){
		$('.btn-proceed').show();		
	} 
	
	function removeBankRadio(val)
	{
		
		$(".container-payment input[type='radio']").removeAttr('checked');
		if(val=='None'){
			$('.btn-proceed').show();	
			$('.btn-proceed a').css('pointer-events','none');
			$('.btn-proceed a').css('background-color','#d36f9c');
		}else{
			$('.btn-proceed').show();	
			$('.btn-proceed a').css('pointer-events','visible');
			$('.btn-proceed a').css('background-color','#d22573');
		}
	}
	
	function removeBankSelection()
	{
		$("#otherBankSelectId").val('None');
		$('.btn-proceed').show();	
				
	}
	
	
	 function updateOrderSummary(){
	 	var self = this;
	 	
	 	var shippingLimit = $('#shippingcalculation').attr('data-shipping-limit');
	 	var shippingVal = 0; 	
	 	
		var fnyToken = $.cookie($('#FnyCustomToken').data('tokenid')),
		urlBase = URL_PROPERTIES.get('GET_CART'),
		urlRoot = urlBase.replace("{deviceId}", fnyToken),
		cartPostData = {
			"deviceId":fnyToken,
			"pullInventory":true
		};
			
			$.ajax({
				url : urlRoot,
				type : 'POST',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				async : true,
				cache : false,
				data : JSON.stringify(cartPostData),
				success: function(response){	
				cartObjectVal = response;	
				if(response.cart.totalMrp < shippingLimit){			
					shippingVal = $('#shippingcalculation').attr('data-shipping-val');		
				}
				var totalWithShipping = response.cart.totalPayableAmount; 
				
				var dayMin,dayMax,htmlData;
				$("#payAmountCodId").html(response.cart.payableAmount);
				
				if(response.cart.overallExpectedTimeDelivery === 0){
					var credits = 0;
					if(response.cart.storedCreditUsed){ 
						credits = response.cart.storedCreditUsed;
					}
				 htmlData ='<div data-total-pay="'+ totalWithShipping +'" id="totalPay" style="display:none"></div>'
					 +'<span class="sum-heading moresaving">Order Summary</span>'
				            /*+'<ul>'
				              +'<li><span class="product-value-label">Cart Total</span>'
									+'<span class="product-value" id="total-prdt-val"> Rs.'+ response.cart.totalMrp+'</span>'
				              +'</li>' 
								    +'</ul>'
							 +'<ul><li><span class="product-value-label">Cart Discount</span>'
									+'<span class="product-value" id="total-disc-val"> Rs.'+ response.cart.totalDiscont+'</span>'
									+'</li>' 
									+'</ul>'*/
							+'<ul><li><span class="product-value-label">Cart Total</span>'
									+'<span class="product-value" id="total-grand-val"> Rs.'+ response.cart.baseAmount+'</span>'
									+'</li>' 
									+'</ul>';
							if(response.cart.youSavedAmount){
									htmlData = htmlData +'<ul><li><span class="product-value-label">Promo/Coupon</span>'
									+'<span class="product-value" id="total-youSave-val"> Rs.'+ response.cart.youSavedAmount+'</span>'
									+'</li>' 
									+'</ul>'
									}
							if(credits){
									htmlData = htmlData +'<ul><li><span class="product-value-label">Credits</span>'
									+'<span class="product-value" id="total-credits-val">' +credits+'</span>'
									+'</li>' 
									+'</ul>'
									}
							if(response.cart.shippingCost){
									htmlData = htmlData +'<ul><li>'
										+'<span class="product-value-label">Delivery Charge</span>'
										+'<span class="product-value shippingCharge" > Rs.  '+ response.cart.shippingCost +'</span>'
									+'</li></ul>'
								}
								htmlData = htmlData+'<ul style="display:none;" id="cod-list-id"><li>'
									+'<span class="product-value-label">COD Charge</span>'
									+'<span class="product-value codShippingCharge" > Rs. 0</span>'
								+'</li></ul>'
								if(response.cart.totalTax){
									htmlData = htmlData +'<ul><li>'
									+'<span class="product-value-label">Taxes</span>'
									+'<span class="product-value taxCharges" > Rs. '+ response.cart.totalTax+'</span>'
								+'</li></ul>'
								}/*
				            +'<ul><li class="product-value-label" ><span>You Save :</span> <span id="prdtSaveVal" class="product-value">(-) Rs.  '+response.cart.totalSaving+'</span></li></ul>'*/			    
							  htmlData = htmlData +'<div class="total-area">'
							    +'<span>Net Payable Amount :</span> <span id="youPayVal"  class="yoypayshipping"> Rs.  '+ totalWithShipping +'</span>'
							    +'</div>';

				}else{
				   dayMin = response.cart.overallExpectedTimeDelivery-1;
				  dayMax = response.cart.overallExpectedTimeDelivery+1
				  var credits = 0;
					if(response.cart.storedCreditUsed){ 
						credits = response.cart.storedCreditUsed;
					}
				  htmlData ='<div data-total-pay="'+ totalWithShipping +'" id="totalPay" style="display:none"></div>'
					 +'<span class="sum-heading moresaving">Order Summary</span><div class="sum-content">'
				              +'<span class="sum-heading">Order Summary</span>'
				            /*+'<ul>'
				              +'<li><span class="product-value-label">Cart Total</span>'
				+'<span class="product-value" id="total-prdt-val"> Rs.'+response.cart.totalMrp+'</span>'
				              +'</li>' 
								    +'</ul>'
							+'<ul><li><span class="product-value-label">Cart Discount</span>'
									+'<span class="product-value" id="total-disc-val"> Rs.'+ response.cart.totalDiscont+'</span>'
									+'</li>' 
									+'</ul>'*/
							+'<ul><li><span class="product-value-label">Cart Total</span>'
									+'<span class="product-value" id="total-grand-val"> Rs.'+ response.cart.baseAmount+'</span>'
									+'</li>' 
									+'</ul>';
							if(response.cart.youSavedAmount){
									htmlData = htmlData +'<ul><li><span class="product-value-label">Promo/Coupon</span>'
									+'<span class="product-value" id="total-youSave-val"> Rs.'+ response.cart.youSavedAmount+'</span>'
									+'</li>' 
									+'</ul>'
									}	
									if(credits){
									htmlData = htmlData +'<ul><li><span class="product-value-label">Credits</span>'
									+'<span class="product-value" id="total-credits-val">'+credits+'</span>'
									+'</li>' 
									+'</ul>'
									}
									if(response.cart.shippingCost){
									htmlData = htmlData +'<ul><li>'
									+'<span class="product-value-label">Delivery Charge</span>'
									+'<span class="product-value shippingCharge" > Rs.  '+ response.cart.shippingCost +'</span>'
								+'</li></ul>'}
						htmlData = htmlData+'<ul style="display:none;" id="cod-list-id"><li>'
									+'<span class="product-value-label">COD Charge</span>'
									+'<span class="product-value codShippingCharge" > Rs. 0</span>'
								+'</li></ul>'
						
						if(response.cart.totalTax){
									htmlData = htmlData +'<ul><li>'
								+'<span class="product-value-label">Taxes</span>'
								+'<span class="product-value taxCharges" > Rs. '+ response.cart.totalTax+'</span>'
							+'</li></ul>'
						}
				            /*+'<ul><li class="product-value-label" ><span>You Save :</span> <span id="prdtSaveVal" class="product-value">(-) Rs.  '+response.cart.totalSaving+'</span></li></ul>'			    
					+'<ul><li class="textbold">Products expected to be delivered within  '+dayMin+' to  '+dayMax+' days. </li></ul>'*/		    
				  +'<div class="total-area">'
							    +'<span>Net Payable Amount :</span> <span id="youPayVal" class="yoypayshipping"> Rs.  '+ totalWithShipping +'</span>'
							    +'</div></div>';

				}
				       
								//$('.order-sum-box').html(htmlData);
				    
				$('.order-sum-box').html(htmlData);
				
				var pageURL = window.location.pathname.toLowerCase();
				
				if((pageURL.match(/billing$/) != null) || (pageURL.match(/cart$/) != null)) {
            		$("#shippingCharges").remove();
            	}
            	
            	if((pageURL.match(/billing$/) != null) || (pageURL.match(/payment$/) != null)) {
					  $(".check-avalible").remove();
					  $("#checkoutBtn").remove();
					  $(".redeem-area").remove();
					  $(".offers-link").remove();
					  $(".remove-offer").remove();
				}
			}
		  });
	};
	 
	function checkshippingmethod(){
		var codLimit = $('#codcalculation').attr('data-cod-limit'),
		codVal = $('#codcalculation').attr('data-cod-val'),
		totalAmount = $('#totalPay').attr('data-total-pay');
		
		var totalWithShipping = parseFloat(parseFloat(codVal)+parseFloat(totalAmount)).toFixed(2);
		
		if(cartObjectVal.cart.baseAmount < codLimit){
			$(".codShippingCharge").html(" Rs. "+codVal);
			$(".yoypayshipping").text(" Rs. "+totalWithShipping);
			$("#payAmountCodId").html(totalWithShipping);
			$("#cod-list-id").show();
			
		}
		else{
			$("#payAmountCodId").html(parseFloat(totalAmount).toFixed(2));
		}
		
	};
</script>


<script>
	
window.onload = function(){
	//validateCartOnPaymentSelection('CC');
	updateOrderSummary();
	var cardVal = "${creditCardGateway}";
	setCreditCardGateway(cardVal,'CC');
	/*setTimeout(function(){
	var fnyToken = $.cookie($('#FnyCustomToken').data('tokenid'));
	var urlBase = URL_PROPERTIES.get('GET_CART');
	var urlRoot = urlBase.replace("{deviceId}", fnyToken);
	
	$.ajax({
		url : urlRoot,
		type : 'GET',
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : true,
		success: function(response){
			
			var htmlData ='<span class="sum-heading">More Saving</span><div class="sum-content"><div class="moresaving">'
                +'<span class="product-value-label">Total Savings</span>'
                +'<span id="moresaveVal"> '+ response.cart.moreSavingValue +'</span>'
              +'</div>'
              +'<span class="sum-heading">Order Summary</span>'
            +'<ul>'
              +'<li><span class="product-value-label">Total</span>'
+'<span class="product-value" id="total-prdt-val"> '+ response.cart.grandOrderValue +'</span>'
              +'</li>' 
				    +'</ul>'
            +'<ul><li>'
						+'<span class="product-value-label">Shipping</span>'
						+'<span class="product-value"> INR  '+ response.cart.shippingCharge +'</span>'
					+'</li></ul>'
            +'<ul><li class="product-value-label" ><span>You Save :</span> <span id="prdtSaveVal">(-) INR  '+response.cart.youSaveValue+'</span></li></ul>'			    
			    +'<div class="total-area">'
			    +'<span>Net Payable Amount :</span> <span id="youPayVal"> INR  '+ response.cart.youPayValue +'</span>'
			    +'</div></div>';

			    
			$('.order-sum-box').html(htmlData);
		}
	});
	
	},1000);*/

	
	
};

</script>
