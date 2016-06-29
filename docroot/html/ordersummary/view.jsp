<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<style>
.loading-msg {
	display: table !important;
	margin: auto;
	padding: 5px;
	visibility: hidden;
	position: absolute;
	right: 60px;
	width: 100px;
}

.sum-item-promo, .sum-item-credits, .sum-item-del, .sum-item-tax{
display: none;
}
.sidebar-floating {
	top: 40px !important;
}
</style>
<portlet:defineObjects />

<div id="savingId"></div>
<portlet:resourceURL var="updateCart" id="updateCartId"></portlet:resourceURL>
<div id="left" class="sidebar-floating">
	<div class="order-sum-outer" id="sidebar">
		<div class="order-sum-box">
			<span class="sum-heading">Order More Saving</span>
			<div class="sum-content">
				<!-- redeem area starts here -->
				<div id="globalOffersBox" class="redeem-area global">
					<div class="loading-msg">
						<p>Please wait..</p>
					</div>
					<input type="hidden" value="<%=updateCart%>"
						id="Globalcartactionurl" />
					<div id="coupon-box">
						<div class="rightmark"></div>
						<form id="newformapply1" method="post"
							action="<portlet:resourceURL/>">
							<input type="hidden" name="action" value="updatePromoCoupon"
								id="actionCouponId" /> <input type="hidden"
								value="<%=updateCart%>" id="couponIdurl" /> <input type="text"
								value="" placeholder="Coupon Code" id="promoCouponId">

							<button class="apply-offer go" name="Add" type="button"
								id="appyPromoCupnCode">Apply</button>
						</form>
						<form id="newremoveformapply1" method="post"
							action="<portlet:resourceURL/>" style="display: none">
							<input type="hidden" name="action" value="removePromoCoupon"
								id="removeCouponId" /> <input type="hidden"
								value="<%=updateCart%>" id="couponIdurl" /> <input type="text"
								value="" placeholder="Coupon Code" id="globalVoucherRemovecoupn"
								data-value='' class="removecoupn" disabled="true">
							<button class="remove-offer go" name="Add" type="button"
								style="display: none">Remove</button>

						</form>

					</div>
					<span data-offer-type="coupon" class="coupon">Use Promo Code</span>
					<div class="clear"></div>
				</div>
				<div id="personalOffersBox" class="redeem-area personal">
					<div id="personal-voucher-box">
						<div class="loading-msg">
							<p>Please wait..</p>
						</div>
						<div id="coupon-box">
							<form id="newformapply1" method="post"
								action="<portlet:resourceURL/>">
								<input type="hidden" name="action" value="applyPersonalVoucher"
									id="actionVoucherId" /> <input type="hidden"
									value="<%=updateCart%>" id="VoucherUrl" /> <select
									data-type="PersonalVoucher" id="personnelVoucherId"
									name="personnelVoucher">
									<option value="">Select Voucher</option>

								</select>
								<button data-type="Voucher" class="apply-offer go"
									id="applyPersonalVoucher" type="button">Apply</button>
							</form>

							<form id="newremoveformapply1" method="post"
								action="<portlet:resourceURL/>" style="display: none">
								<input type="hidden" name="action" value="removePersonalVoucher"
									id="removePersonalVoucher" /> <input type="hidden"
									value="<%=updateCart%>" id="couponIdurl" /> <input type="text"
									value="" placeholder="Coupon Code"
									id="personalVoucherRemovecoupn" data-value=''
									class="removecoupn" disabled="true">
								<button class="remove-offer go" name="Add" type="button"
									style="display: none">Remove</button>

							</form>

						</div>

					</div>
					<span data-offer-type="personal-voucher"
						class="offer-option personal-voucher">Personal Voucher</span>
					<div class="clear"></div>
					<input type="hidden" value="<%=updateCart%>" id="actionPointUrl" />
					<div class="clear"></div>

					<div id="credits-box">
						<div class="loading-msg">
							<p>Please wait..</p>
						</div>
						<div id="coupon-box">
							<form method="post" action="<portlet:resourceURL/>"
								class="edit-offer-form" id="newformapply1">
								<input type="hidden" name="action" value="applyRewardCredits"
									id="actionCreditsId" /> <input type="hidden"
									value="<%=updateCart%>" id="actionCreditsIdUrl" /> <input
									type="text" value="" placeholder="Enter Credits"
									id="StoredCreditBoxId" onkeypress="return onlyNos(event,this);" />
								<button data-type="StoredCredit" class="apply-offer go"
									type="button">Apply</button>
							</form>

							<form id="newremoveformapply1" method="post"
								action="<portlet:resourceURL/>" style="display: none">
								<input type="hidden" name="action" value="removeRewardCredits"
									id="removeRewardCredits" /> <input type="hidden"
									value="<%=updateCart%>" id="couponIdurl" /> <input type="text"
									value="" placeholder="Coupon Code" id="promoCouponIdRmv"
									data-value='' class="removecoupn" disabled="true">
								<button class="remove-offer go" name="Add" type="button"
									style="display: none">Remove</button>
							</form>
						</div>
						<form id="newremoveformapply1" method="post"
							action="<portlet:resourceURL/>" style="display: none">
							<input type="hidden" name="action" value="getOrderSummaryDetails"
								id="cartorderDetails" /> <input type="hidden"
								value="<%=updateCart%>" id="cartactionurl" />
						</form>
					</div>
					<span data-offer-type="credits" class="offer-option credits">Redeem
						Credits <i data-value="0" id="creditsAvailableBalance">(Rs. 0)</i>
					</span>
					<div class="clear"></div>
				</div>
				<div class="moresaving sum-heading">Order Summary
				</div>
				<!-- <ul>
					<li><span class="product-value-label">Cart Total</span> <span
						class="product-value" id="total-prdt-val"> </span></li>
				</ul> -->
				<!-- <ul>
					<li><span class="product-value-label">Cart Discount</span> <span
						class="product-value" id="total-disc-val"> </span></li>
				</ul> -->
				<ul>
					<li><span class="product-value-label">Cart Total</span> <span
						class="product-value" id="total-grand-val"> </span></li>
				</ul>
				<ul class="sum-item-promo">
					<li><span class="product-value-label">Promo/Coupon</span> <span
						class="product-value" id="total-voucher-val"> </span></li>
				</ul>					
				<ul class="sum-item-credits">
					<li><span class="product-value-label">Credits</span> <span
						class="product-value" id="total-credits-val"> </span></li>
				</ul>
				<ul class="sum-item-del">
					<li><span class="product-value-label">Delivery Charges</span> <span
						class="product-value" id="total-del-val"> </span></li>
				</ul>
				<ul class="sum-item-tax">
					<li><span class="product-value-label">Taxes</span> <span
						class="product-value" id="total-tax-val"> </span></li>
				</ul>
				<!-- <ul>
					<li class="product-value-label"><span>You Save :</span> <span
						id="prdtSaveVal">(-) Rs. 0.00</span></li>
				</ul> -->
				<div class="total-area">
					<span>Net Payable Amount :</span> <span id="youPayVal"> Rs.
						0.00</span>
				</div>
				<!-- redeem area ends here -->
			</div>
		</div>

		<div class="check-avalible">
			<h3>Check serviceability at your location</h3>
			<div class="checkservice" id="checkservice">
				<input type="text" id="pincode"
					onkeypress="return onlyNos(event,this);" maxlength="6" /> <a
					href="javascript:void(0)" class="checkavalable go">Check</a> <span
					class="statusmsg"><span class="icon"></span><span
					class="text"></span></span>
			</div>
			<div class="clear"></div>
		</div>
		<!-- <a href="/billing" class="btn-cartpop fancybox" id="checkoutBtn"><i
			class="icon-basket"></i> checkout</a> -->
			<a href="${checkoutUrl}" class="btn-cartpop fancybox" id="checkoutBtn"><i class="icon-basket"></i> checkout</a>
	</div>
</div>
<div class="cardetailschk" style="display: none">${cartDetails}</div>
<script type="text/javascript">
	//add , after 3 digits
	

function cartProductsImpl(data){
	
	if(data.cart){		
		var cartObj = data.cart;
		if(data.cart.products){
			var productsObj = data.cart.products,
      voucherObj;
      if(data.cart.vouchers){
        voucherObj = data.cart.vouchers
      }   
			var count = 0;
			var productsArry = [],vouchersArry =[];
			for(var prop in productsObj) {
				if(productsObj.hasOwnProperty(prop))
				  var multiProductsObj = {
           				    "sKUId":productsObj[prop].sellerSku.sKUId,
				            "sellerId":productsObj[prop].sellerSku.sellerId,				                      
				            "deliveryDay":productsObj[prop].deliveryDay,
				            "maxAllowedQuantity":productsObj[prop].sellerSku.maxQuantity,
				            "maxAvailableQuantity":productsObj[prop].sellerSku.inventory,
				            "productName":productsObj[prop].title,
				            "quantity":productsObj[prop].quantity,
				            "status":cartObj.status,
				            "expireDuration":productsObj[prop].expireTimeLimit, ///---todo
				            "timeToExpire":productsObj[prop].expireTimeLimit,
				            "variant":productsObj[prop].variantCode,
				         // "costPrice":productsObj[prop].sellerSku.priceWithoutTax,
				            "salePrice":productsObj[prop].sellerSku.offerPrice,
				            "mrp":productsObj[prop].sellerSku.mrp,
				            //"vat":productsObj[prop].vat,
				    		"expectedTimeDelivery":productsObj[prop].expectedTimeDelivery,
				    		"expectedMinDays":productsObj[prop].expectedMinDays,
				    		"expectedMaxDays":productsObj[prop].expectedMaxDays,
				            "warehouseLocation":productsObj[prop].sellerSku.warehouseLocation,
				            "thumbnailUrl":productsObj[prop].image.thumbUrl,
				    		"createTime":productsObj[prop].createTime,
				    		"variants":productsObj[prop].variants,
				    		"eventId":productsObj[prop].sellerSku.event.eventId,
				    		"vendorProductId":productsObj[prop].webshopId, ///------todo
				    		"currencyType":"Rs."           
					
				  };
				  productsArry.push(multiProductsObj);
				   ++count;
			}
			
      if(data.cart.vouchers){
        for(var prop in voucherObj) {
          if(voucherObj.hasOwnProperty(prop))
            var voucherNewObj = {
              "valueType":voucherObj[prop].valueType,
              "code":voucherObj[prop].code,
              "value":voucherObj[prop].value,
              "type":voucherObj[prop].type,
              "voucherAmount":voucherObj[prop].voucherAmount,
              "description":voucherObj[prop].description

            };
            vouchersArry.push(voucherNewObj)
            
        }
      }
			data.cart.products = productsArry;
      if(data.cart.vouchers){
        data.cart.vouchers = vouchersArry;
      }
   			return data;
		}
		
	}
	else{
		
		return {"cart":{}};
	}
}; 
	function onlyNos(e, t) {
            try {
                if (window.event) {
                    var charCode = window.event.keyCode;
                }
                else if (e) {
                    var charCode = e.which;
                }
                else { return true; }
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                return true;
            }
            catch (err) {
                consol.log(err.Description);
            }

        }  
		
$(document).ready(function() {
	$.fn.digits = function(){ 
		return this.each(function(){ 
			$(this).text( $(this).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,") ); 
		})
	}

	var deviceId = $.cookie('FNY_TOKEN_5');
	//console.log($.cookie('FNY_TOKEN_5'));
	
// Cart validation for price check	
setTimeout(function(){	

		var self = this;
		
		var orderSumVar = this;
		
		//Promotion content goes cart value pop starts here
			orderSumVar.PromotionalFreeOffer = function(cartValue , storedCreditAmount , discountVal ,grandOrderVal){
				var promotionVal = $('#freeProductPromoId #promoFreeOffer').data('value');
				
				if(!(promotionVal == 0 ||promotionVal == null || promotionVal == undefined || promotionVal == null)){	
					if(!(cartValue > promotionVal)){
						$("#invalid-popup").removeClass('invalid');
						$("#invalid-popup span.icon").remove();
						$("#invalid-popup h2").text("Hey! Your Cart Value is now Rs. "+parseFloat(cartValue)+". Shop for Rs. "+parseFloat(promotionVal-cartValue)+" more & get a Selfie Stick Free! ");
						$("#invalid-popup .row p").text("");
						$.fancybox($('#invalid-popup'),{
							 helpers : { 
								  overlay : {closeClick: false}
								},
							 'afterShow'     : function () {
									   
										
									}
						});
					}
				}
			};
		//Promotion content goes cart value pop ends here	
	
	    if ($.cookie('COOKIE_FNY_LOGIN_ID') === undefined || $.cookie('COOKIE_FNY_LOGIN_ID') === "") {
	    	$('#personalOffersBox').css('display','none');
	    	
	    	var fnyToken = $.cookie($('#FnyCustomToken').data('tokenid'));
			
			var tempglobalcartData = ${cartDetails};
			
			var tempTwocartData = cartProductsImpl(tempglobalcartData.CartData);
			tempglobalcartData.CartData = tempTwocartData;
		    var globalCartData = tempglobalcartData,
		    cartaction = $('#cartorderDetails').val(),
		    cartActionUrl = $('#cartactionurl').val(),
		    Globalcartactionurl = $('#Globalcartactionurl').val();
					   
			if(globalCartData.CartData.cart.products){
				$('#rewardsAvailableBalance').html('('+globalCartData.RewardsData+' Points)');
				$('#creditsAvailableBalance').html('(Rs. '+globalCartData.CreditsData+' )');
				
				if(globalCartData.CartData.cart.youSavedAmount)
				{
					$('#total-voucher-val').html('(-) Rs. '+globalCartData.CartData.cart.youSavedAmount);
					$('.sum-item-promo').show();
				}else{
					$('.sum-item-promo').hide();
				}
				if(globalCartData.CartData.cart.shippingCost)
				{					
					$('#total-del-val').html('Rs. '+globalCartData.CartData.cart.shippingCost);
					$('.sum-item-del').show();
				}else{
					$('.sum-item-del').hide();
				}
				if(globalCartData.CartData.cart.storedCreditUsed){
					$('#total-credits-val').html(globalCartData.CartData.cart.storedCreditUsed);
					$('.sum-item-credits').show();
				}else{
					$('.sum-item-credits').hide();
				}				
				if(globalCartData.CartData.cart.totalTax){
					$('#total-tax-val').html('Rs. '+globalCartData.CartData.cart.totalTax);
					$('.sum-item-tax').show();
				}else{
					$('.sum-item-tax').hide();
				}
				
				$('#total-grand-val').html('Rs. '+globalCartData.CartData.cart.baseAmount);
				$('.total-area #youPayVal').html('Rs. '+globalCartData.CartData.cart.totalPayableAmount);
				$('.moresaving #moresaveVal').html('Rs. '+globalCartData.CartData.cart.totalSaving);
							
				if(globalCartData.CartData.cart.vouchers){
					for(i=0;i<globalCartData.CartData.cart.vouchers.length;i++){
						if(globalCartData.CartData.cart.vouchers[i].type === 'GLOBAL'){	
							$('#globalOffersBox .removecoupn').attr('placeholder',globalCartData.CartData.cart.vouchers[i].code);
							$('#globalOffersBox .removecoupn').attr('data-value',globalCartData.CartData.cart.vouchers[i].code);
							$('#globalOffersBox .remove-offer').css('display','block');
							$('#globalOffersBox #newformapply1').css('display','none');
							$('#globalOffersBox #newremoveformapply1').css('display','block');
							
						}
					}
					
				}
		    }
			
		    
	    } else {
	    	
	    	
	    	var fnyToken = $.cookie($('#FnyCustomToken').data('tokenid'));
		    var tempglobalcartData = ${cartDetails};
			var tempTwocartData = cartProductsImpl(tempglobalcartData.CartData);
			tempglobalcartData.CartData = tempTwocartData;
		    var globalCartData = tempglobalcartData,
		    cartaction = $('#cartorderDetails').val(),
		    cartActionUrl = $('#cartactionurl').val(),
		    Globalcartactionurl = $('#Globalcartactionurl').val();
		   
		    if(globalCartData.CartData.cart.baseAmount){	
		    	if(globalCartData.CartData.cart.youSavedAmount)
		    	{
		    		$('#total-voucher-val').html('(-) Rs. '+globalCartData.CartData.cart.youSavedAmount);
		    		$('.sum-item-promo').show();
		    	}else{
		    		$('.sum-item-promo').hide();
		    	}				
		    	if(globalCartData.CartData.cart.shippingCost)
		    	{					
		    		$('#total-del-val').html('Rs. '+globalCartData.CartData.cart.shippingCost);
		    		$('.sum-item-del').show();
		    	}else{
		    		$('.sum-item-del').hide();
		    	}				
		    	if(globalCartData.CartData.cart.storedCreditUsed){
		    		$('#total-credits-val').html(globalCartData.CartData.cart.storedCreditUsed);
		    		$('.sum-item-credits').show();
		    	}else{
		    		$('.sum-item-credits').hide();
		    	}				
		    	if(globalCartData.CartData.cart.totalTax){
		    		$('#total-tax-val').html('Rs. '+globalCartData.CartData.cart.totalTax);
		    		$('.sum-item-tax').show();
		    	}else{
		    		$('.sum-item-tax').hide();
		    	}
		    	
				$('#total-grand-val').html('Rs. '+globalCartData.CartData.cart.baseAmount);
								
				$('.total-area #youPayVal').html('Rs. '+globalCartData.CartData.cart.totalPayableAmount);
				$('.moresaving #moresaveVal').html('Rs. '+globalCartData.CartData.cart.totalSaving);
			}
			
			$('#rewardsAvailableBalance').html('('+globalCartData.RewardsData+' Points)');
			$('#creditsAvailableBalance').html('(Rs. '+globalCartData.CreditsData+' )'); 
 
 if(globalCartData.VouchersData.domainResponse.entitiesResponse){
	 if(globalCartData.VouchersData.domainResponse.entitiesResponse.errorMessage){
		 
	 }
	 else{
		 if(globalCartData.VouchersData.domainResponse.entitiesResponse.baseDTO){
			 $('#personnelVoucherId').append('<option value="'+globalCartData.VouchersData.domainResponse.entitiesResponse.baseDTO.voucherCode+'">'+globalCartData.VouchersData.domainResponse.entitiesResponse.baseDTO.description+'</option>');
			 }
			 else{
			   $.each(globalCartData.VouchersData.domainResponse.entitiesResponse, function(index , data){
			    
			 		 $('#personnelVoucherId').append('<option value="'+data.baseDTO.voucherCode+'">'+data.baseDTO.description+'</option>');
			 	 });
	
			 }
	 }
	
 }
 
	if(globalCartData.CartData.cart.vouchers){
		for(i=0;i<globalCartData.CartData.cart.vouchers.length;i++){
			if(globalCartData.CartData.cart.vouchers[i].type === 'GLOBAL'){	
				$('#globalOffersBox .removecoupn').attr('placeholder',globalCartData.CartData.cart.vouchers[i].code);
				$('#globalOffersBox .removecoupn').attr('data-value',globalCartData.CartData.cart.vouchers[i].code);
				$('#globalOffersBox .remove-offer').css('display','block');
				$('#globalOffersBox #newformapply1').css('display','none');
				$('#globalOffersBox #newremoveformapply1').css('display','block');
				
			}
			else if(globalCartData.CartData.cart.vouchers[i].type === 'PERSONAL'){	
				$('#personal-voucher-box .removecoupn').attr('placeholder',globalCartData.CartData.cart.vouchers[i].code);
				$('#personal-voucher-box .removecoupn').attr('data-value', globalCartData.CartData.cart.vouchers[i].code);
				
				$('#personal-voucher-box .remove-offer').css('display','block');
				$('#personal-voucher-box #newformapply1').css('display','none');
				$('#personal-voucher-box #newremoveformapply1').css('display','block');
				
				
			}
		}
		
	}
	
	if(globalCartData.CartData.cart.storedCreditUsed > 0){
		
		$('#credits-box .removecoupn').attr('placeholder', globalCartData.CartData.cart.storedCreditUsed);
		$('#credits-box .removecoupn').attr('data-value', globalCartData.CartData.cart.storedCreditUsed);
		
		if(globalCartData.CartData.cart.storedCreditUsed){
    		$('#total-credits-val').html(globalCartData.CartData.cart.storedCreditUsed);
    		$('.sum-item-credits').show();
    	}	
		$('#credits-box .remove-offer').css('display','block');
		$('#credits-box #newformapply1').css('display','none');
		$('#credits-box #newremoveformapply1').css('display','block');
		
		
	}
	
	if(globalCartData.CartData.cart.rewardAmount > 0){
		
		$('#rewards-box .removecoupn').attr('placeholder', globalCartData.CartData.cart.rewardAmount);
		$('#rewards-box .removecoupn').attr('data-value', globalCartData.CartData.cart.rewardAmount);
		
		$('#rewards-box .remove-offer').css('display','block');
		$('#rewards-box #newformapply1').css('display','none');
		$('#rewards-box #newremoveformapply1').css('display','block');
		
		
	}
	
	if(globalCartData.CartData.cart.promotions){
		if(globalCartData.CartData.cart.promotions[0]){
			   $.each(globalCartData.CartData.cart.promotions ,function(index , data){
				 if(data.promotionType.toLowerCase() === 'personalvoucher'){
				    $('#personal-voucher-box .removecoupn').attr('placeholder', data.voucherCode);
					$('#personal-voucher-box .removecoupn').attr('data-value', data.voucherCode);

				 }
			  });
			  
			$('#personal-voucher-box .remove-offer').css('display','block');
			$('#personal-voucher-box #newformapply1').css('display','none');
			$('#personal-voucher-box #newremoveformapply1').css('display','block');
		}
		else if(globalCartData.CartData.cart.promotions.promotionType.toLowerCase() === 'personalvoucher'){
		
		
			$('#personal-voucher-box .removecoupn').attr('placeholder', globalCartData.CartData.cart.promotions.voucherCode);
			$('#personal-voucher-box .removecoupn').attr('data-value', globalCartData.CartData.cart.promotions.voucherCode);
			
			$('#personal-voucher-box .remove-offer').css('display','block');
			$('#personal-voucher-box #newformapply1').css('display','none');
			$('#personal-voucher-box #newremoveformapply1').css('display','block');
		
		}
		
	}
	

	   }
	    

 
	    $('.order-sum-box form').submit(function(e){
	    	e.preventDefault();
	    	
	    });	    
	    
	   
	    
var removeoffer;
	    
$('#globalOffersBox #appyPromoCupnCode').on('click',function(){
	var self = this, 
	totalCartValue = $('.total-area #youPayVal').html().split(' ').pop(-1);
	var actionVal = document.getElementById('actionCouponId').value;
    var couponVal = document.getElementById('promoCouponId').value;
    var couponIdurl = URL_PROPERTIES.get('APPLY_NEW_VOCHER');
	var voucherBody = {
			"deviceId":$.cookie($('#FnyCustomToken').data('tokenid')),
			"voucherRequest":{
				"code":couponVal,
				"type":"GLOBAL"
			}
		  }
    $.ajax({
        url :couponIdurl,            
          data: JSON.stringify(voucherBody),
          type: "POST",
          dataType: "json",
		  contentType: "application/json",
          beforeSend: function(){
        	  $('#globalOffersBox .loading-msg').css('visibility','visible');
        	  $('#globalOffersBox #coupon-box').css('display','none');
        	
          },
		 success: function(data) {
        	$('#globalOffersBox .loading-msg').css('visibility','hidden');
        	$('#globalOffersBox #coupon-box , .coupon').css('display','block');
        	$(self).parent().parent().parent().find('.loading-msg ').css('visibility','hidden','position','absolute');
			$(self).parent().find('form').css('display','block');
			if (data.responseCode === "SUCCESS") {	
				$('#globalOffersBox #newformapply1').css('display','none');
				$('#globalOffersBox #newremoveformapply1').css('display','block');
				$(self).css('display','none');
				$('#globalOffersBox .remove-offer').css('display','block');
	        		
	        	var globalVouherData =  cartProductsImpl(data);	
				if(globalVouherData.cart.vouchers){
					for(i=0;i<globalVouherData.cart.vouchers.length;i++){
						if(globalVouherData.cart.vouchers[i].type === 'GLOBAL'){
							$('#globalOffersBox .removecoupn').attr('placeholder',globalVouherData.cart.vouchers[i].code);
							$('#globalOffersBox .removecoupn').attr('data-value',globalVouherData.cart.vouchers[i].code);	
						}
					}
				}
				
				if(data.cart.shippingCost)
				{					
					$('#total-del-val').html('Rs. '+data.cart.shippingCost);
					$('.sum-item-del').show();
				}else{
		    		$('.sum-item-del').hide();
		    	}								
				if(data.cart.storedCreditUsed)
				{
					$('#total-credits-val').html(data.cart.storedCreditUsed);
					$('.sum-item-credits').show();					
				}else{
		    		$('.sum-item-credits').hide();
		    	}				
				if(data.cart.totalTax)
				{
					$('#total-tax-val').html('Rs. '+data.cart.totalTax);
					$('.sum-item-tax').show();
				}else{
		    		$('.sum-item-tax').hide();
		    	}
				if(data.cart.youSavedAmount)
				{
					$('#total-voucher-val').html('(-) Rs. '+data.cart.youSavedAmount);					
					$('.sum-item-promo').show();
				}else{
		    		$('.sum-item-promo').hide();
		    	}
				$('.total-area #youPayVal').html('Rs. '+data.cart.totalPayableAmount);
				$('.moresaving #moresaveVal').html('Rs. '+data.cart.totalSaving);
				
				//light box fix 
				$('.cart-footer span.ng-binding').html('Rs. '+data.cart.totalPayableAmount)
        	    $('span.value.ng-binding').html(parseInt(data.cart.totalPayableAmount)).digits();

			} else {
					$("#invalid-popup h2").text('Invalid Voucher code or Insufficient cart balance');
					$("#invalid-popup .row p").text("");
					$.fancybox($('#invalid-popup'),{
						helpers : { 
							 overlay : {closeClick: false}
						   },
						'afterShow'     : function () {
								   $('#globalOffersBox #newformapply1 input[type="text"]').val('').focus();
								   
							   }
				   });
					
			}
             
		}	
			
	});	
    
});


$('#globalOffersBox .remove-offer').on('click',function(){
	var self = this;
	removeoffer = this;
	
	var actionVal = document.getElementById('removeCouponId').value;
    var couponVal = $('#globalVoucherRemovecoupn').attr('data-value');
    var couponIdurl = URL_PROPERTIES.get('REMOVE_NEW_VOCHER');
	var voucherBody = {
			"deviceId":$.cookie($('#FnyCustomToken').data('tokenid')),
			"voucherRequest":{
				"code":couponVal
			}
		  }
		
	 $.ajax({
	        url :couponIdurl,            
	          data: JSON.stringify(voucherBody),
	          type: "POST",
	          dataType: "json",
			  contentType: "application/json",
	          beforeSend: function(){
	        	  
	        	  $('#globalOffersBox .loading-msg').css('visibility','visible');
	        	  $('#globalOffersBox #coupon-box').css('display','none');
	        	
	          },
	        success: function(data) {
	        	$('#globalOffersBox .loading-msg').css('visibility','hidden');
	        	$('#globalOffersBox #coupon-box , .coupon').css('display','block');
	        	$(self).parent().parent().find('.loading-msg ').css('visibility','hidden','position','absolute');
				$(self).parent().find('form').css('display','block');
				if (data.responseCode === "SUCCESS") {
					$('#globalOffersBox #newremoveformapply1').css('display','none');
					$('#globalOffersBox #newformapply1').css('display','block');
					$(self).css('display','none');
					$('#globalOffersBox .apply-offer').css('display','block');
					$('#globalOffersBox #newformapply1 input[type="text"]').val('').focus();
		        			
					
					if(data.cart.shippingCost)
					{					
						$('#total-del-val').html('Rs. '+data.cart.shippingCost);
						$('.sum-item-del').show();
					}else{
			    		$('.sum-item-del').hide();
			    	}								
					if(data.cart.storedCreditUsed)
					{
						$('#total-credits-val').html(data.cart.storedCreditUsed);
						$('.sum-item-credits').show();					
					}else{
			    		$('.sum-item-credits').hide();
			    	}
					
					if(data.cart.totalTax)
					{
						$('#total-tax-val').html('Rs. '+data.cart.totalTax);
						$('.sum-item-tax').show();
					}else{
			    		$('.sum-item-tax').hide();
			    	}
					if(data.cart.youSavedAmount)
					{
						$('#total-voucher-val').html('(-) Rs. '+data.cart.youSavedAmount);					
						$('.sum-item-promo').show();
					}else{
			    		$('.sum-item-promo').hide();
			    	}
										
					$('#total-grand-val').html('Rs. '+data.cart.baseAmount);
										
					$('.total-area #youPayVal').html('Rs. '+data.cart.totalPayableAmount);
					$('.moresaving #moresaveVal').html('Rs. '+data.cart.totalSaving);
					
					//light box fix 
					$('.cart-footer span.ng-binding').html('Rs. '+data.cart.totalPayableAmount);
					$('span.value.ng-binding').html(parseInt(data.cart.totalPayableAmount)).digits();
		        	    

	        	} else 
	        	{
	        	    $("#invalid-popup h2").text('Invalid Voucher code or Insufficient cart balance');
	        	    $("#invalid-popup .row p").text("");
	        	    $.fancybox($("#invalid-popup"));
	        	}
	              
	        }
	    });
	
	
});







$('#credits-box .apply-offer').on('click',function(){
	var self = this;
	var actionVal = $('#credits-box #actionCreditsId').val();
    var couponVal = document.getElementById('StoredCreditBoxId').value;
    var couponIdurl = URL_PROPERTIES.get('APPLY_SOTRECREDITS');
	var creditBody = {
			"deviceId":$.cookie($('#FnyCustomToken').data('tokenid')),
			"customerId":$.cookie('COOKIE_FNY_CUSTOMER_ID'),
			"creditAmount":couponVal
			
		  }
	console.log("url"+couponIdurl+" json "+creditBody);	 
	
    $.ajax({
          url :couponIdurl,            
          data: JSON.stringify(creditBody),
          type: "POST",
          dataType: "json",
		  contentType: "application/json",
          beforeSend: function(){
        	  
        	  $('#credits-box .loading-msg').css('visibility','visible');
        	  $('#credits-box #coupon-box').css('display','none');
        	
          },
        success: function(data) {
        	$('#credits-box .loading-msg').css('visibility','hidden');
        	$('#credits-box #coupon-box , .coupon').css('display','block');
        	$(self).parent().parent().parent().find('.loading-msg ').css('visibility','hidden','position','absolute');
			$(self).parent().find('form').css('display','block');
			if (data.responseCode === "SUCCESS") {
				$('#credits-box #newformapply1').css('display','none');
				$('#credits-box #newremoveformapply1').css('display','block');
				$(self).css('display','none');
				$('#credits-box .remove-offer').css('display','block');
	        		
	        		$('#credits-box .removecoupn').attr('placeholder', data.cart.storedCreditUsed);
	        		
       	        	$('#credits-box .removecoupn').attr('data-value', data.cart.storedCreditUsed);
					console.log('credit box---')
					$.ajax({
					  url :URL_PROPERTIES.get('GET_CREDIT_BALANCE').replace('{customerId}',$.cookie('COOKIE_FNY_CUSTOMER_ID')).replace('{deviceId}',$.cookie($('#FnyCustomToken').data('tokenid'))),            
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  beforeSend: function(){
						  
						 
						
					  },
						success: function(data) {
							$('#creditsAvailableBalance').html('(Rs. '+data.domainResponse.message+' )');
							console.log(data.domainResponse.message)
						}
					});	
					
					if(data.cart.shippingCost)
					{					
						$('#total-del-val').html('Rs. '+data.cart.shippingCost);
						$('.sum-item-del').show();
					}else{
			    		$('.sum-item-del').hide();
			    	}								
					if(data.cart.storedCreditUsed)
					{
						$('#total-credits-val').html(data.cart.storedCreditUsed);
						$('.sum-item-credits').show();					
					}else{
			    		$('.sum-item-credits').hide();
			    	}
					
					if(data.cart.totalTax)
					{
						$('#total-tax-val').html('Rs. '+data.cart.totalTax);
						$('.sum-item-tax').show();
					}else{
			    		$('.sum-item-tax').hide();
			    	}
					if(data.cart.youSavedAmount)
					{
						$('#total-voucher-val').html('(-) Rs. '+data.cart.youSavedAmount);					
						$('.sum-item-promo').show();
					}else{
			    		$('.sum-item-promo').hide();
			    	}
									
					$('#total-grand-val').html('Rs. '+data.cart.baseAmount);
					$('.total-area #youPayVal').html('Rs. '+data.cart.totalPayableAmount);
					$('.moresaving #moresaveVal').html('Rs. '+data.cart.totalSaving);
					
					//light box fix 
					$('.cart-footer span.ng-binding').html('Rs. '+data.cart.totalPayableAmount)
					$('span.value.ng-binding').html(parseInt(data.cart.totalPayableAmount)).digits();
        	    

        	} else {
        		
        	    $("#invalid-popup h2").text(data.domainResponse.errorMessage);
        	    $("#invalid-popup .row p").text("");
        	    $.fancybox($('#invalid-popup'),{
                    helpers : { 
                         overlay : {closeClick: false}
                       },
                    'afterShow'     : function () {
                    	 $('#credits-box #newformapply1 input[type="text"]').val('').focus();
                               
                           }
               });
        	    
        	  
        	}
              
        }
    });
});


$('#credits-box .remove-offer').on('click',function(){
	var self = this;
	var actionVal = document.getElementById('removeRewardCredits').value;
	var couponVal = $('#credits-box .removecoupn').data('value');
    var couponIdurl = URL_PROPERTIES.get('REMOVE_SOTRECREDITS');
	var creditBody = {
			"customerId":$.cookie('COOKIE_FNY_CUSTOMER_ID'),
			"deviceId":$.cookie($('#FnyCustomToken').data('tokenid'))	
			
		  }
	
	 $.ajax({
	        url :couponIdurl,            
	          data: JSON.stringify(creditBody),
	          type: "POST",
	          dataType: "json",
			  contentType: "application/json",
	          beforeSend: function(){
	        	  
	        	  $('#credits-box .loading-msg').css('visibility','visible');
	        	  $('#credits-box #coupon-box').css('display','none');
	        	
	          },
	        success: function(data) {
	        	$('#credits-box .loading-msg').css('visibility','hidden');
	        	$('#credits-box #coupon-box , .coupon').css('display','block');
	        	$(self).parent().parent().find('.loading-msg ').css('visibility','hidden','position','absolute');
				$(self).parent().find('form').css('display','block');
				if (data.responseCode === "SUCCESS") {
					$('#credits-box #newremoveformapply1').css('display','none');
					$('#credits-box #newformapply1').css('display','block');
					$(self).css('display','none');
					$('#credits-box .apply-offer').css('display','block');
					$('#credits-box #newformapply1 input[type="text"]').val('').focus();
		        	$.ajax({
					  url :URL_PROPERTIES.get('GET_CREDIT_BALANCE').replace('{customerId}',$.cookie('COOKIE_FNY_CUSTOMER_ID')).replace('{deviceId}',$.cookie($('#FnyCustomToken').data('tokenid'))),            
					  type: "GET",
					  dataType: "json",
					  contentType: "application/json",
					  beforeSend: function(){
						  
						 
						
					  },
						success: function(data) {
							$('#creditsAvailableBalance').html('(Rs. '+data.domainResponse.message+' )');
							console.log(data.domainResponse.message)
						}
					});	
					if(data.cart.shippingCost)
					{					
						$('#total-del-val').html('Rs. '+data.cart.shippingCost);
						$('.sum-item-del').show();
					}else{
			    		$('.sum-item-del').hide();
			    	}								
					if(data.cart.storedCreditUsed)
					{
						$('#total-credits-val').html(data.cart.storedCreditUsed);
						$('.sum-item-credits').show();					
					}else{
			    		$('.sum-item-credits').hide();
			    	}
					
					if(data.cart.totalTax)
					{
						$('#total-tax-val').html('Rs. '+data.cart.totalTax);
						$('.sum-item-tax').show();
					}else{
			    		$('.sum-item-tax').hide();
			    	}
					if(data.cart.youSavedAmount)
					{
						$('#total-voucher-val').html('(-) Rs. '+data.cart.youSavedAmount);					
						$('.sum-item-promo').show();
					}else{
			    		$('.sum-item-promo').hide();
			    	}
					
					$('#creditsAvailableBalance').html('(Rs. '+data.cart.storedCreditUsed+' )');
					$('.total-area #youPayVal').html('Rs. '+data.cart.totalPayableAmount);
					$('.moresaving #moresaveVal').html('Rs. '+data.cart.totalSaving);	
		        		
					//light box fix 
					$('.cart-footer span.ng-binding').html('Rs. '+data.cart.totalPayableAmount)	
					$('span.value.ng-binding').html(parseInt(data.cart.totalPayableAmount)).digits();
		        	    

	        	} else 
	        	{
	        	    $("#invalid-popup h2").text(data.domainResponse.errorMessage);
	        	    $("#invalid-popup .row p").text("");
	        	    $.fancybox($("#invalid-popup"));
	        	    
	        	}
	              
	        }
	    });
	
	
});


$('#personal-voucher-box .apply-offer').on('click',function(){
	var self = this;
	var personalVouchData = $(self).parent().find('select option:selected').html();
	var actionVal = document.getElementById('actionVoucherId').value;
    var couponVal = document.getElementById('personnelVoucherId').value;
    var couponIdurl = URL_PROPERTIES.get('APPLY_NEW_VOCHER');
	var voucherBody = {
			"deviceId":$.cookie($('#FnyCustomToken').data('tokenid')),
			"customerId":$.cookie('COOKIE_FNY_CUSTOMER_ID'),
			"voucherRequest":{
				"code":couponVal,
				"type":"PERSONAL"
			}
		  }
    $.ajax({
        url :couponIdurl,            
          data: JSON.stringify(voucherBody),
          type: "POST",
          dataType: "json",
		  contentType: "application/json",
          beforeSend: function(){
        	  
        	  $('#personal-voucher-box .loading-msg').css('visibility','visible');
        	  $('#personal-voucher-box #coupon-box').css('display','none');
        	
          },
        success: function(data) {
        	$('#personal-voucher-box .loading-msg').css('visibility','hidden');
        	$('#personal-voucher-box #coupon-box , .coupon').css('display','block');
        	$(self).parent().parent().parent().find('.loading-msg ').css('visibility','hidden','position','absolute');
			$(self).parent().find('form').css('display','block');
			if (data.responseCode === "SUCCESS") {
				$('#personal-voucher-box #newformapply1').css('display','none');
				$('#personal-voucher-box #newremoveformapply1').css('display','block');
				$(self).css('display','none');
				$('#personal-voucher-box .remove-offer').css('display','block');
				
				var globalVouherData =  cartProductsImpl(data);	
				if(globalVouherData.cart.vouchers){
					for(i=0;i<globalVouherData.cart.vouchers.length;i++){
						if(globalVouherData.cart.vouchers[i].type === 'PERSONAL'){
							$('#personal-voucher-box .removecoupn').attr('placeholder', globalVouherData.cart.vouchers[i].code);
							$('#personal-voucher-box .removecoupn').attr('data-value',globalVouherData.cart.vouchers[i].code);
	        		
						}
					}
				}
				
				if(data.cart.shippingCost)
				{					
					$('#total-del-val').html('Rs. '+data.cart.shippingCost);
					$('.sum-item-del').show();
				}else{
		    		$('.sum-item-del').hide();
		    	}								
				if(data.cart.storedCreditUsed)
				{
					$('#total-credits-val').html(data.cart.storedCreditUsed);
					$('.sum-item-credits').show();					
				}else{
		    		$('.sum-item-cedits').hide();
		    	}
				
				if(data.cart.totalTax)
				{
					$('#total-tax-val').html('Rs. '+data.cart.totalTax);
					$('.sum-item-tax').show();
				}else{
		    		$('.sum-item-tax').hide();
		    	}
				if(data.cart.youSavedAmount)
				{
					$('#total-voucher-val').html('(-) Rs. '+data.cart.youSavedAmount);					
					$('.sum-item-promo').show();
				}else{
		    		$('.sum-item-promo').hide();
		    	}
				
				$('.total-area #youPayVal').html('Rs. '+data.cart.totalPayableAmount);
				$('.moresaving #moresaveVal').html('Rs. '+data.cart.totalSaving);
				
				
	        	//light box fix 
				$('.cart-footer span.ng-binding').html('Rs. '+data.cart.totalPayableAmount)	
				$('span.value.ng-binding').html(parseInt(data.cart.totalPayableAmount)).digits();	

        	    

        	} else {
        	    $("#invalid-popup h2").text('Invalid Voucher code or Insufficient cart balance');
        	    $("#invalid-popup .row p").text("");
        	    $.fancybox($("#invalid-popup"));
        	}
              
        }
    });
});


$('#personal-voucher-box .remove-offer').on('click',function(){
	var self = this;
	var actionVal = document.getElementById('removePersonalVoucher').value;
	var couponVal = $('#personalVoucherRemovecoupn').attr('data-value');
	var couponIdurl = URL_PROPERTIES.get('REMOVE_NEW_VOCHER');
	var voucherBody = {
			"deviceId":$.cookie($('#FnyCustomToken').data('tokenid')),
			"voucherRequest":{
				"code":couponVal
			}
		  }
	
	 $.ajax({
			  url :couponIdurl,            
	          data: JSON.stringify(voucherBody),
	          type: "POST",
	          dataType: "json",
			  contentType: "application/json",
	          beforeSend: function(){
	        	  
	        	  $('#personal-voucher-box .loading-msg').css('visibility','visible');
	        	  $('#personal-voucher-box #coupon-box').css('display','none');
	        	
	          },
	        success: function(data) {
	        	$('#personal-voucher-box .loading-msg').css('visibility','hidden');
	        	$('#personal-voucher-box #coupon-box , .coupon').css('display','block');
	        	$('#credits-box').css('display','block');
	        	$(self).parent().parent().find('.loading-msg ').css('visibility','hidden','position','absolute');
				$(self).parent().find('form').css('display','block');
				if (data.responseCode === "SUCCESS") {
					$('#personal-voucher-box #newremoveformapply1').css('display','none');
					$('#personal-voucher-box #newformapply1').css('display','block');
					$(self).css('display','none');
					$('#personal-voucher-box .apply-offer').css('display','block');
					
					$("#personal-voucher-box select option:selected").prop("selected", false);
					
					if(data.cart.shippingCost)
					{					
						$('#total-del-val').html('Rs. '+data.cart.shippingCost);
						$('.sum-item-del').show();
					}else{
			    		$('.sum-item-del').hide();
			    	}								
					if(data.cart.storedCreditUsed)
					{
						$('#total-credits-val').html(data.cart.storedCreditUsed);
						$('.sum-item-credits').show();					
					}else{
			    		$('.sum-item-credits').hide();
			    	}
					
					if(data.cart.totalTax)
					{
						$('#total-tax-val').html('Rs. '+data.cart.totalTax);
						$('.sum-item-tax').show();
					}else{
			    		$('.sum-item-tax').hide();
			    	}
					if(data.cart.youSavedAmount)
					{
						$('#total-voucher-val').html('(-) Rs. '+data.cart.youSavedAmount);					
						$('.sum-item-promo').show();
					}else{
			    		$('.sum-item-promo').hide();
			    	}
										
					$('.total-area #youPayVal').html('Rs. '+data.cart.totalPayableAmount);
					$('.moresaving #moresaveVal').html('Rs. '+data.cart.totalSaving);
		        		
						
					//light box fix 
					$('.cart-footer span.ng-binding').html('Rs. '+data.cart.totalPayableAmount)
		        	$('span.value.ng-binding').html(parseInt(data.cart.totalPayableAmount)).digits();    

	        	} else 
	        	{
	        	    $("#invalid-popup h2").text('Invalid Voucher code or Insufficient cart balance');
	        	    $("#invalid-popup .row p").text("");
	        	    $.fancybox($("#invalid-popup"));
	        	}
	              
	        }
	    });
	
	
});

$('#pincode').val('');
$('.checkavalable').on('click',function(){
	
	urlBase = URL_PROPERTIES.get('SERVICEABILITY'),
	urlRoot = urlBase.replace("{pinCode}", $('#pincode').val());
			$.ajax({
		      url :urlRoot,            
		      data: "",
		      type: "GET",
		      dataType: "json",
	     
		    success: function(data) {
		    	
		    	if(data.pinCodeWrapper.responseCode === "FAILURE"){
                    $(".checkservice .statusmsg").addClass("red");
                    $(".checkservice .statusmsg .text").text("Invalid pincode");
                    $(".checkservice .statusmsg").show();
                    
                } else {
                    if(data.pinCodeWrapper.pinCodes.deliveryAvailable) {
                    	$('.codcheck').remove();
                        $(".checkservice .statusmsg").removeClass("red").addClass("green");
                        $(".checkservice .statusmsg .text").text("Yes, your area is serviceable");
                        
                        if(data.pinCodeWrapper.pinCodes.serviceability == 1){
							
							$('.check-avalible .statusmsg ').after('<span class="statusmsg codcheck red "><span class="icon"></span><span class="text">COD not available</span></span>');
						}
                        $(".checkservice .statusmsg").show();
                        $.cookie('COOKIE_PINCODE_DELIVERABLE',$('#pincode').val(), {path: '/'});
                        
                        
                    } else {
                        $(".checkservice .statusmsg").addClass("red");
                        $(".checkservice .statusmsg .text").text("Sorry, your area is not serviceable");
                        $(".checkservice .statusmsg").show();
                    }
                }
		    	
		    	
		    }
		});
});


	   
 
},1000);


$('#checkoutBtn').on('click',function(e) {
    e.preventDefault();

    function isUserLoggedIn(){
        if ($.cookie('COOKIE_FNY_LOGIN_ID') === undefined || $.cookie('COOKIE_FNY_LOGIN_ID') === "") {
            return false;
        } else {
            return true;
        }
    }
    
    function isRedirectToCart(){
    	
    	var rslt = true;
    	urlBase = URL_PROPERTIES.get('CHECK_SOLD_OUT'),
    	urlRoot = urlBase.replace('{deviceId}',$.cookie($('#FnyCustomToken').data('tokenid')));
    	
    			$.ajax({
    		      url :urlRoot,            
    		      data: "",
    		      type: "GET",
    		      dataType: "json",
    		      async:false,
    	     
    		    success: function(data) {
    		    	if(data.responseCode == "SUCCESS"){
    		    		if(data.containsSoldOut == false){
    		    			rslt =  false;
    		    		 }
    		    	}
    		    }
    		    });
    			
    			return rslt;
    }
    
    var redirectURL = "",
        totalProductInCart = $("#backbone-portlet-cart-content .cart-details").length,
        deviceId =$.cookie($('#FnyCustomToken').data('tokenid'));
        console.log("billing page redirction" + deviceId);	

    if (totalProductInCart > 0) {
    	
    	if(isRedirectToCart()){
    		 redirectURL = window.location.origin + "/cart";
    	}else{
    		//urlCheck = URL_PROPERTIES.get("CHECKOUT_URL").replace('http','https'),
    		//redirectURL = urlCheck.replace('{deviceId}',$.cookie($('#FnyCustomToken').data('tokenid')));

    		redirectURL = window.location.origin + "/webfront/checkout/checkout-page?deviceId="+deviceId;
    		
    	}
    	/*else if (isUserLoggedIn()) {
            //redirectURL = window.location.origin + "/billing";
            redirectURL = window.location.origin + "/webfront/checkout/checkout-page?deviceId="+deviceId;
        } else {
            $(e.currentTarget).attr("href", "/login");
            redirectURL = window.location.origin + "/login";
        }*/
        window.location.replace(redirectURL);
    } else {
        $("#invalid-popup h2").text("Your Cart is empty.");
        $("#invalid-popup .row p").text("");
        $.fancybox($("#invalid-popup"));
    }
});
});

 /*
window.addEventListener("beforeunload", function (e) {
 var confirmationMessage = "\o/";


	$.cookie('pageUrlData', window.location.href, {
		expires: 1,
		path: '/'
	});                            //Webkit, Safari, Chrome etc.
});
*/
 </script>

