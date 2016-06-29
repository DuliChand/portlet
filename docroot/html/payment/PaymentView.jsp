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
#backbone-portlet-cart-content , #backbone-portlet-cart-nav{
display: block !important;
}

.marketing-popup .cart-nav {display:none!important;}

#p_p_id_navigation_WAR_appportlet_ ,#p_p_id_cart_WAR_appportlet_{display:none;}
</style>
<main role="payment-main" data-context-path="${renderRequest.contextPath}" id="backbone-portlet-payment"></main>
 
<div class="col-sm-12 col-md-8">
	<div class="payment-section-area">
		<div class="payment-section">
			<ul>
				
				<li class="dbit payListTab col-xs-6 col-sm-12 col-md-12" id="debitcredit"
					onClick="setPaymentMethod('CC');validateCartOnPaymentSelection('CC');"><a
					href="#"><span class="icontype icon-debit"></span> Credit /
						Debit</a></li>
				<li id="netbanking" class="payListTab col-xs-6 col-sm-12 col-md-12"
					onClick="setPaymentMethod('NB');validateCartOnPaymentSelection('NB');"><a
					href="#"><span class="icontype icon-netbanking"></span>
						NetBanking</a></li>

				<li id="wallet" class="payListTab col-xs-6 col-sm-12 col-md-12" onclick="removeSelection();">
					<a href="#"><span class="icontype icon-wallet"></span> Wallet</a></li>
				<c:if test="${payOption ne '1'}">				
				<li id="cod" class="payListTab col-xs-6 col-sm-12 col-md-12 "
					onClick="setPaymentMethod('COD');validateCartOnPaymentSelection('COD');"><a
					href="#"><span class="icontype icon-cod"></span> COD</a></li>
				</c:if>
				<c:forEach items="${paymentChannelWrapper.paymentChannels}"
					var="payment">
					<c:if test="${payment.paymentChannelCode eq 'PAYUWALL'}">			
						<li id="payUMoney" class="payListTab col-xs-6 col-sm-12 col-md-12"
						onClick="setPaymentMethod('PUM');validateCartOnPaymentSelection('PUM');"><a
						href="#"><span class="icontype icon-payUMoney"></span> PayUMoney</a></li>
					</c:if>
				</c:forEach>	
			</ul>
		</div>
		<div class="container-payment">
			<div class="divcontent paymethod4" 	id="debitcreditContent">
				<c:forEach items="${paymentChannelWrapper.paymentChannels}"
					var="payment">
					<c:if test="${payment.paymentChannelCode eq 'PAYUCC' || payment.paymentChannelCode eq 'HDFCCC'}">
						<div class="col-xs-4 breakpoint1">
							<input type="radio" name="debitCreditOption" onchange="showProceed();setCardType();"
								id="${payment.paymentChannelCode}"
								value="${payment.paymentChannelCode}" class="radio" />
								<label class="${payment.paymentChannelCode}-BUTTON" for="${payment.paymentChannelCode}"><span class="ie8-icon"></span>${payment.paymentChannelCode}</label>
								
								</div>
					</c:if>
				</c:forEach>
				
				<p class="card-visa">A bank-issued card that allows consumers to purchase goods or services from a merchant on credit e.g. : VISA, Master Card</p>
						
			</div>

			<div class="divcontent paymethod2 " id="netbankingContent">
				<div class="breakpoint2">
					<label>Select Other Bank list</label> <select class="selectdrop"
						id="bankSelectId" name="bankSelect"
						onchange="setBankCode(this.value);showProceed();">
						<option value="">- Select -</option>
						<c:forEach items="${paymentChannelWrapper.paymentChannels}"
							var="paymentChannel">
							<c:if test="${not empty paymentChannel.pgBankMappings}">
								<c:forEach items="${paymentChannel.pgBankMappings}"
									var="pgMapping">
									<c:if test="${pgMapping.pgType eq 'PAYUNB'}">
										<option value="${pgMapping.bankId}">${pgMapping.bankName}</option>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
					</select>
					
					<p>You can now pay for your transaction using Net Banking directly through your bank account. fashionandyou.com does not collect your banking information. On choosing Net Banking, you will be directed to your banks website for the payment process. After completing the payment process, you will be redirected back to fashionandyou.com.</p>
				</div>
			</div>

			<div class="divcontent paymethod4 " id="walletContent">
			<c:forEach items="${paymentChannelWrapper.paymentChannels}"
					var="payment">
				<c:if test="${payment.paymentChannelCode eq 'MWALL'}">				
				<div class="col-xs-4 breakpoint1">					
					<input type="radio" name="walletOption" class="radio" value="mobiKwik" id="mobiKwik" onchange="showProceed(); setWalletMethod('WL');"  name="debitCreditOption"> 
					<label class="MOBIKWIK-BUTTON" for="mobiKwik"><span class="ie8-icon"></span>MobiKwik</label>
					
				</div>
				</c:if>
				<c:if test="${payment.paymentChannelCode eq 'PAYTM'}">
				<div class="col-xs-4 breakpoint1">
					<input type="radio" name="walletOption" id="paytmId" value="paytmId" class="radio" onchange="showProceed(); setWalletMethod('PT');" />
					<label class="PAYTM-BUTTON" for="paytmId"><span class="ie8-icon"></span>Paytm</label>
				</div>
				</c:if>
				</c:forEach>
			</div>

			<div class="divcontent paymethod5" id="codContent">
				<div class="payment-typebox">
					<ul>
						<li><label for="orderPhone"> Phone No.</label> <input
							type="text" value="${customerPhoneNumber}" readonly="readonly"></input>
							
						</li>
						
								<li>We verify all Cash on Delivery orders over the phone after you have made a purchase and post which we process your order. Our Customer Support team will call you within 24 hours of your purchase to verify details; alternatively you can also call us to verify your order on +91-124-4125000. Failing verification of your order may result into cancellation. Therefore we request you to provide us with a mobile phone no. where we can best reach you within 24hrs.</li>
								<li><strong>In case you do not accept a verified Cash on Delivery order we may need to revoke the Cash on Delivery payment option from your account for future purchases.</strong></li>
								<li><strong>Shipping charge of INR 99/- would be applicable for net payment amount less than INR 999/-.</strong></li>
					</ul>
				</div>
			</div>
			<div class="divcontent paymethod6 " id="payUMoneyContent">
				<div class="breakpoint1">
					<input type="radio" name="payUMoneyOption" id="payUMoneyId" class="radio" onchange="showProceed();" />
					<label class="payUMoneybutton" for="payUMoneyId"><span class="ie8-icon"></span>PayUMoney</label>
				</div>
			</div>
			<div class="divcontent paymethod6" id="paybackContent">
				<div class="payment-typebox">
					<ul>
						<li><label>Name</label> <input type="text" value=""
							placeholder="Your Name"></li>
						<li class="check-point"><strong>Check your Packback
								points:</strong></li>
						<li><label>Payback / Mobile Number</label> <input type="text"
							value="" placeholder="Payback / Mobile Number" /> <a
							class="edit_btn" href="#"> Check </a></li>
						<li>You can now redeem your PAYBACK reward points against any
							purchase on <a href="#">fashionandyou.com.</a>
						</li>
						<li>Conversion Ratio: 4 Points = 1 Re.</li>
						<li><strong>You will be redirected to PAYBACK
								Payment Gateway for your transaction.</strong></li>
					</ul>
				</div>
			</div>

			<div class="btn-proceed pull-down">
				<a href="#cant-proceed" onclick="submitPaymentForm()">Proceed</a>
			</div>
			
		</div>
	</div>
</div>
<div class="ordersumContainer"><div id="left" class="sidebar-floating">
<div class="order-sum-outer" id="sidebar" >
<div class="order-sum-box"></div></div></div></div>

<form action="<portlet:actionURL />" method="post" id="paymentFormId">
		<input type="hidden" name="<portlet:namespace/>action" value="doPayment" id="actionID" />
		<input type="hidden" name="<portlet:namespace/>orderId" value="${orderId}" id="orderID" readonly="readonly"/>
		<input type="hidden" name="<portlet:namespace/>deviceId" value="" id="deviceID" />
		<input type="hidden" name="<portlet:namespace/>paymentChannel" value="PAYUCC" id="paymentChannelID" />
		<input type="hidden" name="<portlet:namespace/>paymentMethod" value="payU" id="paymentMethodID" />
		<input type="hidden" name="<portlet:namespace/>bankCode" value="" id="bankCodeID" />
		<input type="hidden" name="<portlet:namespace/>pg" value="CC" id="pgID" />
		
</form>


<script type="text/javascript">
	
	function setBankCode(value) {
		$("#bankCodeID").val(value);
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
	function setPaymentMethod(value) {
		
		$(".container-payment input[type='radio']").removeAttr('checked');
		$(".container-payment select").val("");
		
		$('.btn-proceed').hide();
		
		sessionStorage.setItem("REWARD_POINTS","");
		sessionStorage.setItem("CREDITS","");
		sessionStorage.setItem("VOUCHERS","");
		if (value == "CC") {
			$("#paymentMethodID").val("payU");
			$("#pgID").val("CC");
			$("#paymentChannelID").val("PAYUCC");
		}
		if (value == "NB") {
			$("#paymentMethodID").val("payU");
			$("#pgID").val("NB");
			$("#paymentChannelID").val("PAYUNB");
		}
		if (value == "COD") {			
			$("#paymentMethodID").val("COD");
			$("#paymentChannelID").val("COD");
			$("#bankCodeID").val("");
			$("#pgID").val("");
			
			$('.btn-proceed').show();
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
		$(".container-payment select").val("");
		
		$('.btn-proceed').hide();
		
	}
	function setWalletMethod(value) {
		
		sessionStorage.setItem("REWARD_POINTS","");
		sessionStorage.setItem("CREDITS","");
		sessionStorage.setItem("VOUCHERS","");
		
		if (value == "WL") {
			$("#paymentMethodID").val("MWALL");
			$("#pgID").val("");
			$("#paymentChannelID").val("MWALL");
		}
		if (value == "PT") {
			$("#paymentMethodID").val("PAYTM");
			$("#pgID").val("");
			$("#paymentChannelID").val("PAYTM");
		}
		
	}

	function submitPaymentForm() {
		$(".payment-section li").each(function(index) {
			/* console.log( index + ": " + $( this ).text() ); */
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
	
	function validateCartOnPaymentSelection(currPaymentChannel){
		var fnyToken = $.cookie($('#FnyCustomToken').data('tokenid')),
    	paymentChannel = currPaymentChannel,
		urlBase = URL_PROPERTIES.get('VALIDATE_CART_ON_PAYMENT'),
		urlRoot = urlBase.replace("{paymentChannel}", paymentChannel).replace("{deviceId}", fnyToken),
		
		cartData = {
        	cart : {
        		
        	}
        },
        
		json_data = JSON.stringify(cartData);

		$.ajax({
			url : urlRoot,
			type : 'POST',
			data : json_data,
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			async : true,
			success: function(response){
				if(response.domainResponse.responseCode === "SUCCESS") {
					updateOrderSummary();
				} 
				else {
              		/* console.log(response.domainResponse.errorMessage); */
              		$.fancybox($("#invalid-popup"));
              }
			}
		  });
	};
	
	function updateOrderSummary(){
		
		var fnyToken = $.cookie($('#FnyCustomToken').data('tokenid')),
		urlBase = URL_PROPERTIES.get('GET_CART'),
		urlRoot = urlBase.replace("{deviceId}", fnyToken);
		
		$.ajax({
			url : urlRoot,
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			async : true,
			success: function(response){
				//var response = {"cart":{"cartId":"820482c0-73eb-11e4-841c-e06995467be6","codCAPLimit":0,"deviceId":"","grandOrderValue":0,"moreSavingValue":0,"overallExpectedDeliveryDate1":1,"overallExpectedDeliveryDate2":0,"overallExpectedTimeDelivery":0,"rewardAmount":0,"shippingCharge":0,"status":"","storedCreditAmount":0,"totalBasePrice":0,"totalQuantity":0,"youPayValue":0,"youSaveValue":777}};
				var dayMin,dayMax,htmlData;
				//console.log(data);
				if(response.cart.overallExpectedTimeDelivery === 0){
				 htmlData ='<span class="sum-heading">More Saving</span><div class="moresaving">'
				                +'<span class="product-value-label">Total Savings</span>'
				                +'<span id="moresaveVal"> Rs.'+ response.cart.totalSaving +'</span>'
				              +'</div>'
				              +'<span class="sum-heading">Order Summary</span>'
				            +'<ul>'
				              +'<li><span class="product-value-label">Cart Total</span>'
				+'<span class="product-value" id="total-prdt-val"> Rs. '+ response.cart.totalMrp +'</span>'
				              +'</li>' 
								    +'</ul>'
				            +'<ul><li>'
										+'<span class="product-value-label">Delivery Charge</span>'
										+'<span class="product-value"> Rs.  '+ response.cart.shippingCost +'</span>'
									+'</li></ul>'
				            +'<ul><li class="product-value-label" ><span>You Save :</span> <span id="prdtSaveVal" class="product-value">(-) Rs.  '+response.cart.totalSaving+'</span></li></ul>'			    
							    +'<div class="total-area">'
							    +'<span>Net Payable Amount :</span> <span id="youPayVal"> Rs.  '+ response.cart.totalPayableAmount +'</span>'
							    +'</div>';

				}else{
				   dayMin = response.cart.overallExpectedTimeDelivery-1;
				  dayMax = response.cart.overallExpectedTimeDelivery+1
				  
				  htmlData ='<span class="sum-heading">More Saving</span><div class="sum-content"><div class="moresaving">'
				                +'<span class="product-value-label">Total Savings</span>'
				                +'<span id="moresaveVal"> Rs. '+response.cart.totalSaving+'</span>'
				              +'</div>'
				              +'<span class="sum-heading">Order Summary</span>'
				            +'<ul>'
				              +'<li><span class="product-value-label">Cart Total</span>'
				+'<span class="product-value" id="total-prdt-val"> Rs. '+response.cart.totalMrp+'</span>'
				              +'</li>' 
								    +'</ul>'
				            +'<ul><li>'
										+'<span class="product-value-label">Delivery Charge</span>'
										+'<span class="product-value"> Rs.  '+ response.cart.shippingCost +'</span>'
									+'</li></ul>'
				            +'<ul><li class="product-value-label" ><span>You Save :</span> <span id="prdtSaveVal" class="product-value">(-) Rs.  '+response.cart.totalSaving+'</span></li></ul>'			    
					+'<ul><li class="textbold">Products expected to be delivered within  '+dayMin+' to  '+dayMax+' days. </li></ul>'		    
				  +'<div class="total-area">'
							    +'<span>Net Payable Amount :</span> <span id="youPayVal"> Rs.  '+response.cart.totalPayableAmount+'</span>'
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
	
	
</script>


<script>
window.onload = function(){
	setTimeout(function(){
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
                +'<span id="moresaveVal"> Rs.'+ response.cart.totalSaving +'</span>'
              +'</div>'
              +'<span class="sum-heading">Order Summary</span>'
            +'<ul>'
              +'<li><span class="product-value-label">Cart Total</span>'
+'<span class="product-value" id="total-prdt-val"> Rs.'+ response.cart.totalMrp +'</span>'
              +'</li>' 
				    +'</ul>'
            +'<ul><li>'
						+'<span class="product-value-label">Delivery Charge</span>'
						+'<span class="product-value"> Rs.  '+ response.cart.shippingCost +'</span>'
					+'</li></ul>'
            +'<ul><li class="product-value-label" ><span>You Save :</span> <span id="prdtSaveVal" class="product-value">(-) Rs.  '+response.cart.totalSaving+'</span></li></ul>'			    
			    +'<div class="total-area">'
			    +'<span>Net Payable Amount :</span> <span id="youPayVal"> Rs.  '+ response.cart.totalPayableAmount +'</span>'
			    +'</div></div>';

			    
			$('.order-sum-box').html(htmlData);
		}
	});
	
	},2000);
	
};

</script>