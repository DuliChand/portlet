<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<portlet:defineObjects />
<style>
#backbone-portlet-cart-content,#backbone-portlet-cart-nav {
	display: none;
}

</style>

<portlet:resourceURL var="getCart" id="getCartId"></portlet:resourceURL>
<div data-firstRecord="<%=System.currentTimeMillis()%>" id="FnyFirstRecord"></div>
<div data-tokenId="${fnyTokenName}" id="FnyCustomToken"></div>
<div id="backbone-portlet-cart-nav"></div>
<div id="backbone-portlet-cart-content">
	<div class="cart-area">
		<div id="cartContent">
			<div class="product-loader">
				<span></span>
			</div>
		</div>
		<div id="newOrderSummary"></div>
		<div id="cartPromoData">${cartPagePromoContent}</div>
	</div>
</div>

<div id="latestOfferData" style="display: none">${latestOfferContent}</div>
<div id="freeProductPromoId" style="display:none">${freeProductPromoContent}</div>
<div id="fnyTokenCookie" style="display: none">${fnyTokenCookie}</div>
<div id="filterSatus" style="display: none" data-status="false"></div>
<div id="cartData" style="display: none">${cartdata}</div>
<div id="topHeaderData" style="display: none">${topHeaderContent}</div>
<div id="infoLinkData" style="display: none">${infoLinkContent}</div>
<div id="topBrandsData" style="display: none">${topBrandsContent}</div>
<div id="copyRightData" style="display: none">${copyRightContent}</div>
<div id="companyLogoData" style="display: none">${companyLogoContent}</div>
<div id="categoryvisted" syle="display:none" data-value=""></div>
<script src="<%=renderRequest.getContextPath()%>/js/jquery-min.js"></script>
<!--Google Analytics tag goes here-->
<script>
    
    
function cartProductsData(data){
	
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
					 var dateVal = false;
					if(productsObj[prop].expectedMaxDays){
							var days = parseInt(productsObj[prop].expectedMaxDays,10);
							var date = new Date();
						    date.setDate(date.getDate() + days);
						    dateVal =  $.datepicker.formatDate('dd M yy', new Date(date));
					}
				  var multiProductsObj = {
           				    "sKUId":productsObj[prop].sellerSku.sKUId,
				            "sellerId":productsObj[prop].sellerSku.sellerId,				                      
				            "deliveryDay":productsObj[prop].deliveryDay,
				            "maxAllowedQuantity":productsObj[prop].sellerSku.maxQuantity,
				            "maxAvailableQuantity":productsObj[prop].dropDownLimit,
				            "soldOut":productsObj[prop].soldOut,
				            "productName":productsObj[prop].title,
				            "quantity":productsObj[prop].quantity,
				            "status":cartObj.status,
				            "expireDuration":productsObj[prop].timeToExpire, ///---todo
				            "timeToExpire":productsObj[prop].expireTimeLimit,
				            "variant":productsObj[prop].variantCode,
				           // "costPrice":productsObj[prop].sellerSku.priceWithoutTax,
				            "salePrice":productsObj[prop].sellerSku.offerPrice,
				            "mrp":productsObj[prop].sellerSku.mrp,
				            //"vat":productsObj[prop].vat,
				    		"expectedTimeDelivery":productsObj[prop].expectedTimeDelivery,
				            "warehouseLocation":productsObj[prop].sellerSku.warehouseLocation,
				            "thumbnailUrl":productsObj[prop].image.thumbUrl,
				    		"createTime":productsObj[prop].createTime,
				    		"variants":productsObj[prop].variants,
				    		"eventId":productsObj[prop].sellerSku.event.eventId,
				    		"expectedMinDays":productsObj[prop].expectedMinDays,
				    		"expectedMaxDays":dateVal,
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
     console.log("return data--"+JSON.stringify(data))
			return data;
		}
		
	}
	else{
		
		return {"cart":{}};
	}
}; 

//Google Analytics----

var  pageURL = window.location.pathname.toLowerCase().split("/").pop(-1);

if(pageURL=='cart'){
	jQuery=$;
}else{
$.noConflict();	
}

jQuery(window).load(function(){
	var  pageURL = window.location.pathname.toLowerCase().split("/").pop(-1);
	
	   
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	
	  ga('create', 'UA-59276234-1', 'auto');
	  ga('send', 'pageview');
	  ga('require', 'ecommerce', 'ecommerce.js');
	  
	  if(pageURL == 'cart'){
		  
		var cartTagData = document.getElementById('cartData').innerHTML
		if(cartTagData != ""){
			var cartproductsId = [],
			cartproductName =[];
			var tempCartData = JSON.parse(cartTagData),
			cartObj = cartProductsData(tempCartData);
			var cartData = cartObj;
			if(tempCartData.responseCode !="FAILURE"){
				if(tempCartData.cart.products){
					if(cartData.cart){
						if(cartData.cart.products.length){
							for(var i=0;i<cartData.cart.products.length;i++){
								cartproductsId.push(cartData.cart.products[i].sKUId);
								cartproductName.push(cartData.cart.products[i].productName);
							}
						}
						else{
							cartproductsId.push(cartData.cart.products.sKUId);
							cartproductName.push(cartData.cart.products.productName);
						}
					}
				}	
			} 
	
			 //ga('ecommerce:send');	
	
		}	
			
	  }
	  
	  else if(pageURL == 'confirmation'){
			var data = JSON.parse($('#orderAggregatedData').html());
			var products = [],
			transactionId = $('#order-confirm').attr('data-id');
			affiliationData = [],cartproductName =[],skuId = [],categories = [];
			if(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.length){
				for(var i=0;i<data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.length;i++){
					products.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines[i].vendorProductId);
					affiliationData.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines[i].eventId);
					cartproductName.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines[i].productName);
					skuId.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines[i].erpSku);
					categories.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines[i].categoryGroup+'-'+data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines[i].categoryL1+'-'+data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines[i].categoryL2);
				}
			}
			else{
				products.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.vendorProductId);
				affiliationData.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.eventId);
				cartproductName.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.productName);
				skuId.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.erpSku);
				categories.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.categoryGroup+'-'+data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.categoryL1+'-'+data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.categoryL2);
			}
			
			ga('ecommerce:addItem', {
				  'id':transactionId,                     
				  'name':cartproductName,    
				  'sku':skuId,                 
				  'category':categories,         
				  'price':data.domainResponse.entitiesResponse.baseDTO.grandOrderValue,                 
				  'quantity':data.domainResponse.entitiesResponse.baseDTO.totalOrderItems
			 });
			
			ga('ecommerce:addTransaction', {
				  'id':transactionId,
				  'affiliation':affiliationData,   
				  'revenue':data.domainResponse.entitiesResponse.baseDTO.grandOrderValue,               
				  'shipping':data.domainResponse.entitiesResponse.baseDTO.totalShippingCharge,                  
				  'tax':""                     
			});
	
	
			 ga('ecommerce:send');	
	
		}
});
</script>

<%
	
%>

<c:if test="${srcPageName eq 'product'}">
	
	<script>
		window.productSumData = {};
		productSumData = ${productDetailData};
		
		
		//console.log("data---"+JSON.stringify(productSumData));
	</script>
	
	<script>
		var productData  = ${productDetailData};
		var erpsku = productData.vendorProductWrapper.liveinventory.erpSku.split('-')[0];
		google_tag_params = {
			ecomm_prodid: erpsku,
			ecomm_pagetype: 'product',
			ecomm_totalvalue: '0',
		};
		
		/* <![CDATA[ */
		var google_conversion_id = 993839940;
		var google_custom_params = google_tag_params;
		var google_remarketing_only = true;
		/* ]]> */
	
	</script>
	
	
	<noscript>
	<div style="display:inline;">
	<img height="1" width="1" style="border-style:none;" alt="" src="//googleads.g.doubleclick.net/pagead/viewthroughconversion/993839940/?value=0&amp;guid=ON&amp;script=0"/>
	</div>
	</noscript>

</c:if>
<c:if test="${mainSrcName ne 'status'}">	

<!-- Google Code for Remarketing Tag -->
<!--
Remarketing tags may not be associated with personally identifiable information or placed on pages related to sensitive categories. See more information and instructions on how to setup the tag on:http://google.com/ads/remarketingsetup
-->
<script type="text/javascript">
var  pageURL = window.location.pathname.toLowerCase().split("/").pop(-1),
 resultpage = window.location.pathname.toLowerCase().match(/result/),
 categorypage = window.location.pathname.toLowerCase().match(/categories/),
 productpage = window.location.pathname.toLowerCase().match(/product-detail/);
 
var productUrlone = window.location.pathname.toLowerCase().split("/").pop(-1).split('_').pop(-1),
tempproductUrltwo = window.location.pathname.toLowerCase().split("/").pop(-1).split('_'),
productUrltwo = tempproductUrltwo[tempproductUrltwo.length-2],
productpagestatus ;


if(!(isNaN(productUrlone))){
  if(!(isNaN(productUrltwo))){
    productpagestatus = "product";
  }
}

var google_tag_params ;
if(pageURL == 'cart'){
	var cartTagData = document.getElementById('cartData').innerHTML
	if(cartTagData != ""){
		var cartproductsId = [];
		var tempCartData = JSON.parse(cartTagData),
		cartObj = cartProductsData(tempCartData);
		var cartData = cartObj;
		if(tempCartData.responseCode !="FAILURE"){
			if(tempCartData.cart.products){
				if(cartData.cart){
					if(cartData.cart.products.length){
						for(var i=0;i<cartData.cart.products.length;i++){
							//var erpsku = cartData.cart.products[i].erpSku.toString();
							//cartproductsId.push(erpsku.split('-')[0]);
							var sku = cartData.cart.products[i].sKUId.toString();
							cartproductsId.push(sku);
						}
					}
					else{
						//var erpsku = cartData.cart.products.erpSku.toString();
						//cartproductsId.push(erpsku.split('-')[0]);
						var erpsku = cartData.cart.products.sKUId.toString();
						cartproductsId.push(sKUId);
					}
				}
				
				google_tag_params = {
				ecomm_prodid: cartproductsId,
				ecomm_pagetype: 'cart',
				//ecomm_totalvalue: cartData.cart.grandOrderValue,
				ecomm_totalvalue: cartData.cart.grandAmount,

				};
			}
			
		}	
	}	
	
	
}

else if(pageURL == 'home'){

	google_tag_params = {
	ecomm_prodid: "",
	ecomm_pagetype: 'home',
	ecomm_totalvalue: '0',
	};
}
else if(resultpage == 'result'){

	google_tag_params = {
	ecomm_prodid: "",
	ecomm_pagetype: 'searchresults',
	ecomm_totalvalue: '0',
	};
}
else if(categorypage == 'categories'){

	google_tag_params = {
	ecomm_prodid: "",
	ecomm_pagetype: 'category',
	ecomm_totalvalue: '0',
	};
}
else if(productpage == 'product-detail'){
	var productid = document.getElementById('erpskuid').innerHTML.split("-")[0];
	var priceData = document.getElementById('productPriceData').innerHTML;
	
	google_tag_params = {
	ecomm_prodid: productid,
	ecomm_pagetype: 'product',
	ecomm_totalvalue: priceData,
	};
}
else if(pageURL == 'confirmation'){
	var data = JSON.parse($('#orderData').html());
	var products = [];
	if(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.length){
		for(var i=0;i<data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.length;i++){
			products.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines[i].vendorProductId);
		}
	}
	else{
		products.push(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.vendorProductId);
	}
	google_tag_params = {
	ecomm_prodid: products,
	ecomm_pagetype: 'purchase ',
	ecomm_totalvalue: data.domainResponse.entitiesResponse.baseDTO.grandOrderValue,
	};
}
else {

	google_tag_params = {
	ecomm_prodid: "",
	ecomm_pagetype: 'other',
	ecomm_totalvalue: '0',
	};
}
/* <![CDATA[ */
var google_conversion_id = 993839940;
var google_custom_params = google_tag_params;
var google_remarketing_only = true;
/* ]]> */
</script>
<script type="text/javascript">
		/*var invite_referrals = window.invite_referrals || {}; 
		(function() { 
			invite_referrals.auth = { bid_e : '6452D8CE6B668ECD28C8DD1F471165EA', bid : '6875', t : '420', email : '', userParams : {'fname': ''}}; 
			var script = document.createElement('script');script.async = true;
			script.src = (document.location.protocol == 'https:' ? "//d11yp7khhhspcr.cloudfront.net" : "//cdn.invitereferrals.com") + '/js/invite-referrals-1.0.js';
			var entry = document.getElementsByTagName('script')[0];
			entry.parentNode.insertBefore(script, entry); 
		})();
*/
</script>



<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="//googleads.g.doubleclick.net/pagead/viewthroughconversion/993839940/?value=0&amp;guid=ON&amp;script=0"/>
</div>
</noscript>
</c:if>
