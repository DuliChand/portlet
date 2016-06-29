<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />
<style>
#backbone-portlet-cart-nav{
	display: block!important;
}
</style>
<div id="backbone-portlet-cart-nav"></div>

<div id="backbone-portlet-billing-form">
	<div class="cart-area">
		<div id="billingContent">
			<div class="product-loader"><span></span></div>
		</div>
		<div id="orderSummary"></div>
	</div>
</div>

<form id="billingCheckoutUrl" method="post" action="<portlet:resourceURL/>">
							
							
</form>	

<script>
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
window.onload = function(){

 var i = new Webshop.Views.BillingViewBak();
i.render();
 /*	var lastOrderAddress = ${lastOrderAddress},
	billingCityData = ${billingCityData} ,
	shippingCityData = ${shippingCityData};
	
	var billingView = new Webshop.Views.BillingView();
    billingView.render(lastOrderAddress, billingCityData, shippingCityData);*/
};

</script>

