<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<portlet:defineObjects />

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/order-result.min.css" type="text/css"/>
<main role="after-sales-main" 
	data-context-path="${renderRequest.contextPath}" id="order-confirm"
	data-id="<%= renderRequest.getAttribute("orderId") %>"
	data-order="<%= request.getAttribute("orderDetail") %>"
	data-method="<%= request.getMethod() %>">
</main>
<style>

.lineThrough {
    text-decoration: line-through;
}

</style>
<div class="col-xs-12">
	<c:if test="${status ne 'SUCCESS' }">
	<c:if test="${message ne '' }">
		<h2 class="codMessage">${message}</h2>
	</c:if>
	<div id="orderresult">
		<div class="row clearfix">
			<div id="result-box" class="col-sm-12 col-xs-12">
			<h5>Transaction Unsuccessful !</h5>
			<c:if test="${message ne '' }">
				<div class="unsceesTxt">
					<p class="left-side">We regret that transaction was unsuccessful.</p>
					<div class="continue-shop"><a href="${checkout}">Try again with Debit / Credit Card</a></div></div>
			</c:if>
			<c:if test="${message eq '' }">
				<div class="unsceesTxt">
					<p class="left-side">We regret that transaction was unsuccessful.</p>
					<div class="continue-shop"><a href="${checkout}">Try again with COD</a></div>
				</div>
				<h4>or</h4>
				<div class="unsceesTxt">
					<p class="left-side">Continue Debit / Credit Card Transaction </p>
					<div class="continue-shop"><a href="${checkout}" >Credit/Debit</a></div>
				</div>
			</c:if>
			<script>
				window.onload = function() {
				var utmData = window.location.href.split("?").slice(1).join("?");
	   			var j = utmData.split('&');
				var referdata="";
				var windowReferrer = document.referrer;
			$.each(j,function(index,data){
			  
			  if(data.indexOf('utm_source') >=0 || data.indexOf('utm_medium')>=0 || data.indexOf('utm_campaign')>=0){
			  referdata=referdata+"&"+data;
			  }
			});
			
			
			if(windowReferrer === ""){
				if(!(referdata === "" || referdata === undefined || referdata === null)){
			    	
			    	
			    	
			    	
			    }


				var utmData1 = $.cookie('UTM_DATA');
				referdata1="";
				if(!(utmData1 === "" || utmData1 === undefined || utmData1 === null)){
				    
					referdata1=utmData1;
			    	
			    }
	
					var click = {
					url : document.URL,
					referer : document.URL+"?"+referdata1,
					protocol : "GET",
					orderId : "",
					orderValue : "",
				deviceId : $.cookie($('#FnyCustomToken').data('tokenid'))

			}, json_data = JSON.stringify({
				click : click
			}), urlRoot = URL_PROPERTIES
					.get('CLICK_STREAM_DATA');

			$.ajax({
				url : urlRoot,
				type : 'POST',
				data : json_data,
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				async : true
			});
		      //alert(json_data)
			
			}
			else
			{
				
				if(!(referdata === "" || referdata === undefined || referdata === null)){
			    	
			    	 $.cookie("UTM_DATA", referdata, { path: '/' , expires: date });
			    	
			    	
			    }


				var utmData1 = $.cookie('UTM_DATA');
				referdata1="";
				if(!(utmData1 === "" || utmData1 === undefined || utmData1 === null)){
				    
					referdata1=utmData1;
			    	
			    }
				
				var flag = windowReferrer.indexOf("?");
				
				if(flag < 0 ){
					referdata1 = "?"+referdata1;
					
				}
				
				
					var click = {
					url : document.URL,
					referer : document.referrer+referdata1,
					protocol : "GET",
					orderId : "",
					orderValue : "",
				deviceId : $.cookie($('#FnyCustomToken').data('tokenid'))

			}, json_data = JSON.stringify({
				click : click
			}), urlRoot = URL_PROPERTIES
					.get('CLICK_STREAM_DATA');

			$.ajax({
				url : urlRoot,
				type : 'POST',
				data : json_data,
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				async : true
			});
		      //alert(json_data)
			
			}
	
		};
</script>
			</div>
		
		
	</div>
</div>
			
			</c:if>
			<c:if test="${status eq 'SUCCESS' }">

			
<div id="order-successfull">
			<h2>Your Transaction is successful</h2>
			<h3>Thank you for shopping with <a href="http://www.fashionandyou.com">fashionandyou.com</a><br />
<strong>Order No. <%=request.getAttribute("orderId") %></strong></h3>
<div class="product-loader" style="display:none">Please wait Loading..</div>			
<div id="ordersummary-holder" style="display:none;">
<h4>Your ORDER SUMMARY is provided below:</h4>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><strong>Product Name</strong></th>
    <th><strong>Quantity</strong></th>
    <th><strong>Max. Retail Price</strong></th>
    <th><strong>Sale Price</strong></th>
    <th><strong>Total(Rs.)</strong></th>
  </tr>

  <!-- <tr>
    <td id="productName" ></td>
    <td id="productQuantity"></td>
    <td id="untPrice"></td>
    <td id="totalPrice"></td>
  </tr> -->
  
  <!-- <tr>
    <td>Discount</td>
    <td></td>
    <td></td>
    <td id="discount"></td>
  </tr>
  <tr>
    <td>Credits</td>
    <td></td>
    <td></td>
    <td id="storeCredits"></td>
  </tr>
  
  <tr>
    <td>Taxes</td>
    <td></td>
    <td></td>
    <td id="totalTax"></td>
  </tr> 
  <tr>
    <td>Total Shipping Charges</td>
    <td></td>
    <td></td>
    <td id="shippingCharge">0.00</td>
  </tr>
   <tr>
    <td>Total COD Charges</td>
    <td></td>
    <td></td>
    <td id="codCharge">0.00</td>
  </tr>
  <tr>
    <td><strong>Net Payable Amount</strong></td>
    <td></td>
    <td></td>
    <td><strong id="grandTotal"></strong></td>
  </tr>

   <!--  <tr>


    <td><strong >*Rewards Points:</strong></td>
    <td></td>
    <td></td>
    <td id="rewardAmount"><%=request.getAttribute("rewardsPoint")%></td>
  </tr>-->
</table></br>
<h4>You Paid: <strong id="grandTotal"></strong></h4>
<a href="/orders">Details</a>
</div>
	<!-- only for COD -->	
<p>${showCODMsg}</p>
	<!-- only for COD end -->
			
<div class="continue-shop1">
<a href="/home" id="cont_shop" class="continue-shop"><i class="icon-plus"></i> CONTINUE SHOPPING</a><a href="#" id="logout_btn" class="continue-shop" onClick="logoutAccountMethod()" hidden><i class="icon-plus"></i>Done</a>
</div>

</div>
		
<div class="ordersumContainer"><div id="left" class="sidebar-floating">
<div class="order-sum-outer" id="sidebar" >
<div class="order-sum-box"></div></div></div></div>		
		
<div id="yebhi_tracking" style="display: none;"  >
                <iframe id="frm_yebhi_tracking" src=""></iframe>     
  </div>			
		
<script type="text/javascript">
     function disableBack() {
       history.pushState(null, null, location.href);
       window.onpopstate = function(event) {
           history.go(1);
       };
      }
      
</script>		
<script>
	window.onload = function() {
		disableBack();
	var FnyCustomToken = $('#FnyCustomToken').data('tokenid');	
		
setTimeout(function(){
	var fnyToken = $.cookie($('#FnyCustomToken').data('tokenid')),
	orderNumber = <%= renderRequest.getAttribute("orderId") %>;
	var urlAggrtData = URL_PROPERTIES.get('ORDER_SUMMARY'),
	urlSummary = urlAggrtData.replace("{deviceId}", fnyToken).replace("{orderNumber}", orderNumber)
		
	 $.ajax({
	        url :urlSummary,            
	          data: "",
	          type: "GET",
	          dataType: "json",
	          beforeSend: function(){
	        	$('#orderresult').css('display','none');
	        	$('.product-loader').css('display','block');
	        	
	          },
	          
	          success: function(data) {
	        	  
	        	  if(data.domainResponse.responseCode === 'SUCCESS'){
	        		  
	        		  $('.product-loader').css('display','none');
		        	  $('#ordersummary-holder').css('display','block')
		        	  $('#orderresult').css('display','block');
	        		  
	        		  if(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.actualAmountPaid){
	        			  var totalDiscountPrice ;
	        			  var discountpriceVal = parseInt(data.domainResponse.entitiesResponse.baseDTO.totalBasePrice - data.domainResponse.entitiesResponse.baseDTO.grandOrderValue) ;
	        			  if(discountpriceVal < 0){
	        				  totalDiscountPrice = 0 ;
	        			  }
	        			  else{
	        				  totalDiscountPrice =  discountpriceVal ;
	        			  }
		        		  var baseDTO = data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines;
		        		  $('#ordersummary-holder table tr:first-child').after('<tr><td>'+baseDTO.productName+'</td><td>'+baseDTO.quantity+'</td><td class="lineThrough">'+baseDTO.mrp+'</td><td>'+baseDTO.salePriceExclVat+'</td><td>'+((parseFloat(baseDTO.quantity))*(parseFloat(baseDTO.salePriceExclVat)))+'</td></tr>');
		        		 // $('#rewardPoitsUsed').html(data.domainResponse.entitiesResponse.baseDTO.totalRewardAmount);
		        		 // $('#storeCredits').html(data.domainResponse.entitiesResponse.baseDTO.totalStoredCredit);
		        		  /* $('#rewardAmount').html(data.domainResponse.entitiesResponse.baseDTO.totalRewardAmount); */
		        		  //$('#grandTotal').html(data.domainResponse.entitiesResponse.baseDTO.totalAmountPaid);
		        		 // $('#totalTax').html(data.domainResponse.entitiesResponse.baseDTO.totalTax);
		        		 // $('#shippingCharge').html(data.domainResponse.entitiesResponse.baseDTO.totalShippingCharge);
		        		 // $('#codCharge').html(data.domainResponse.entitiesResponse.baseDTO.totalCodCharge);
		        		 // $('#discount').html(totalDiscountPrice);
						  
						  //var totalPriceData = JSON.parse($('#orderAggregatedData').html());
						  $('#grandTotal').html(data.domainResponse.entitiesResponse.baseDTO.totalAmountPaid);
	        		}	  
	        	  else if(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.length > 0) { 
	        		 // var totalDiscountPrice ;
        			  var discountpriceVal = parseInt(data.domainResponse.entitiesResponse.baseDTO.totalBasePrice - data.domainResponse.entitiesResponse.baseDTO.grandOrderValue);
        			  if(discountpriceVal < 0){
        				  totalDiscountPrice = 0 ;
        			  }
        			  else{
        				  totalDiscountPrice =  discountpriceVal ;
        			  }
	        		  totalItems = data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines.length
	        			var totalVat = 0;	
	        		 $.each(data.domainResponse.entitiesResponse.baseDTO.inboundOrderLines ,function(index ,data){
	        			 $('#ordersummary-holder table tr:first-child').after('<tr><td>'+data.productName+'</td><td>'+data.quantity+'</td><td class="lineThrough">'+data.mrp+'</td><td>'+data.salePriceExclVat+'</td><td>'+((parseFloat(data.quantity))*(parseFloat(data.salePriceExclVat)))+'</td></tr>');
	        			 totalVat += data.tax;
	        		 }); 
	        		 
	        		 
	        		 // $('#storeCredits').html(data.domainResponse.entitiesResponse.baseDTO.totalStoredCredit);
	        		  //$('#rewardPoitsUsed').html(data.domainResponse.entitiesResponse.baseDTO.totalRewardAmount);
	        		  /* $('#rewardAmount').html(data.domainResponse.entitiesResponse.baseDTO.totalRewardAmount); */
		
	        		  //$('#grandTotal').html(parseInt(data.domainResponse.entitiesResponse.baseDTO.totalAmountPaid -  data.domainResponse.entitiesResponse.baseDTO.totalShippingCharge));
	        		 // $('#discount').html(totalDiscountPrice);
	        		 // $('#shippingCharge').html(data.domainResponse.entitiesResponse.baseDTO.totalShippingCharge);
	        		//  $('#codCharge').html(data.domainResponse.entitiesResponse.baseDTO.totalCodCharge);
	        		//  $('#totalTax').html(data.domainResponse.entitiesResponse.baseDTO.totalTax);
					  
					  //var totalPriceData = JSON.parse($('#orderAggregatedData').html());
					  $('#grandTotal').html(data.domainResponse.entitiesResponse.baseDTO.totalAmountPaid);
	        		 
	        		  
	        		  
	        		}
	        		      		
	        		  var totalDiscountOrder ;
        			  var discountpriceOrder = parseInt(data.domainResponse.entitiesResponse.baseDTO.totalBasePrice - data.domainResponse.entitiesResponse.baseDTO.grandOrderValue);
        			  if(discountpriceOrder < 0){
        				  totalDiscountOrder = 0 ;
        			  }
        			  else{
        				  totalDiscountOrder =  discountpriceOrder ;
        			  }
	        		  
	        		  

	        		setTimeout(function(){
	        		  var totalPriceData = JSON.parse($('#orderAggregatedData').html());
					  
	        		  htmlData = '<span class="sum-heading">Shipping Address :</span>' 
						    + '<div class="sum-content"><ul><li style="border:none;">'+data.domainResponse.entitiesResponse.baseDTO.billingAddress.address1+'</li></ul></div>';
						   
						    /* '<span class="total-items">Total Items</span><span class="total-items totalitems">'+data.domainResponse.entitiesResponse.baseDTO.totalOrderItems+'</span>'
	        		  + '<span class="sum-heading">More Saving</span><div class="moresaving">'
			                +'<span class="product-value-label">Order Summary</span>'
			               +'</div>'
			            +'<div class="moresaving"><ul>'
			              +'<li><span class="product-value-label">Total</span>'
						+'<span class="product-value" id="total-prdt-val"> INR '+data.domainResponse.entitiesResponse.baseDTO.grandOrderValue+'</span>'
			              +'</li>' 
							    +'</ul></div>'
			            +'<div class="moresaving"><ul><li>'
									+'<span class="product-value-label">Shipping</span>'
									+'<span class="product-value"> INR '+data.domainResponse.entitiesResponse.baseDTO.totalShippingCharge+'</span>'
								+'</li></ul></div>'
			            +'<div class="moresaving"><ul><li class="product-value-label" ><span>You Save :</span> <span id="prdtSaveVal">(-) INR '+totalDiscountOrder+' <span></li></ul></div>'			    
						    +'<div class="sum-heading">'
						    +'<span>Net Payable Amount :</span> <span id="youPayVal"> INR  '+totalPriceData.domainResponse.entitiesResponse.baseDTO.totalAmountPaid+' </span>'
						    +'</div>'*/
						   


        		             
        		          $('.order-sum-box').html(htmlData);  

								

        		           },500); 
						
							//freePromooffer goes here---
							
							var promotionVal = $('#freeProductPromoId #promoFreeOffer').data('value');
				
							if(!(promotionVal == 0 ||promotionVal == null || promotionVal == undefined || promotionVal == null)){
							
								if(data.domainResponse.entitiesResponse.baseDTO.grandOrderValue >= promotionVal ){
									$("#invalid-popup").removeClass('invalid').addClass('continue-shop1');
									$("#invalid-popup span.icon").remove();
									$("#invalid-popup h2").text("Smile! Your Cart Value has exceeded Rs.1999,  & you are now eligible to get a Free Selfie Stick!");
									$("#invalid-popup .row p").html("<a href='"+$('#freeProductPromoId #promoFreeOffer').data('link')+"' class='moreDetail  continue-shop'>"
									+"<i class='icon-plus'></i>View More</a>");
									$.fancybox($('#invalid-popup'),{
										 helpers : { 
											  overlay : {closeClick: false}
											},
										 'afterShow'     : function () {
												   
													
												}
									});
														
								}

									
							}
	        	  }
	        	  else{
	        		  
	        		$("#invalid-popup h2").text(data.domainResponse.errorMessage);
	          	    $("#invalid-popup .row p").text("");
	          	    $.fancybox($('#invalid-popup'));
	        		  
	        	  }
	          }
	 });
			
		
		
		
		
	
		    
		
		
		},1000);
		
		if(($.cookie('FNY_CCE_EXECUTIVE')) != undefined){
			var orderNumber = <%= renderRequest.getAttribute("orderId") %>;
			var customerLoginId = $.cookie('COOKIE_FNY_LOGIN_ID');
			$('#cont_shop').hide();
			$('#logout_btn').show();
			$.removeCookie('COOKIE_FNY_CART', {
                path: '/'
            });
			var login = {
				loginId : $.cookie('FNY_CCE_EXECUTIVE')}, 
			json_data = JSON.stringify({
				login : login
			}), 
			urlTemp = URL_PROPERTIES.get('CCE_LOGIN_LOG'),
			urlRoot = urlTemp.replace("{customerLoginId}", customerLoginId).replace("{orderNumber}", orderNumber);

			$.ajax({
				url : urlRoot,
				type : 'POST',
				data : json_data,
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success: function(data) {
					
				},
				async : true
			});
		}
		
		var CustomefirstRecord = $('#FnyFirstRecord').attr('data-firstRecord');
		var ExistfirstREcord = $.cookie('customerFirstRecord');
		if(ExistfirstREcord == "" ||ExistfirstREcord == null ||ExistfirstREcord == undefined){
			var date = new Date();
			var recordminutes = 30;
			date.setTime(date.getTime() + (recordminutes * 60 * 1000));
			$.cookie('customerFirstRecord', CustomefirstRecord, {
			    expires: date,
			    path: '/'
			});
		}
		else if(ExistfirstREcord != "" ||ExistfirstREcord != null ||ExistfirstREcord != undefined){
			var date = new Date();
			var recordminutes = 30;
			date.setTime(date.getTime() + (recordminutes * 60 * 1000));
			$.cookie('customerFirstRecord', ExistfirstREcord, {
			    expires: date,
			    path: '/'
			});
		}

var utmData = window.location.href.split("?").slice(1).join("?");
var j = utmData.split('&');
	var referdata="";
	var windowReferrer = document.referrer;
	
	$.each(j,function(index,data){
	  
	  if(data.indexOf('utm_source') >=0 || data.indexOf('utm_medium')>=0 || data.indexOf('utm_campaign')>=0){
	  referdata=referdata+"&"+data;
	  }
	});
	
	
	if(windowReferrer === ""){
		if(!(referdata === "" || referdata === undefined || referdata === null)){
	    	
	    	 $.cookie("UTM_DATA", referdata, { path: '/' , expires: date });
	    	
	    	
	    }


		var utmData1 = $.cookie('UTM_DATA');
		referdata1="";
		if(!(utmData1 === "" || utmData1 === undefined || utmData1 === null)){
		    
			referdata1=utmData1;
	    	
	    }
			var click = {
			url : document.URL,
			referer : document.URL+"?"+referdata1,
			protocol : "GET",
			firstRecord : $.cookie('customerFirstRecord'),
			orderId : "<%=request.getAttribute("orderId")%>",
			orderValue : "<%=request.getAttribute("amount")%>",
			deviceId : $.cookie($('#FnyCustomToken').data('tokenid'))

	}, json_data = JSON.stringify({
		click : click
	}), urlRoot = URL_PROPERTIES
			.get('CLICK_STREAM_DATA');

	$.ajax({
		url : urlRoot,
		type : 'POST',
		data : json_data,
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : true
	});
      //alert(json_data)
	
	}
	else
	{
		
		if(!(referdata === "" || referdata === undefined || referdata === null)){
	    	
	    	 
	    	
	    	
	    }


		var utmData1 = $.cookie('UTM_DATA');
		referdata1="";
		if(!(utmData1 === "" || utmData1 === undefined || utmData1 === null)){
		    
			referdata1=utmData1;
	    	
	    }
		
		var flag = windowReferrer.indexOf("?");
		
		if(flag < 0 ){
			referdata1 = "?"+referdata1;
			
		}
		
		

			var click = {
			url : document.URL,
			referer : document.referrer+referdata1,
			protocol : "GET",
			firstRecord : $.cookie('customerFirstRecord'),
			orderId : "<%=request.getAttribute("orderId")%>",
			orderValue : "<%=request.getAttribute("amount")%>",
		deviceId : $.cookie($('#FnyCustomToken').data('tokenid'))

	}, json_data = JSON.stringify({
		click : click
	}), urlRoot = URL_PROPERTIES
			.get('CLICK_STREAM_DATA');

	$.ajax({
		url : urlRoot,
		type : 'POST',
		data : json_data,
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : true
	});
      //alert(json_data)
    var pxlOrderId = <%=request.getAttribute("orderId")%> ,
    pxlOrderAmount = <%=request.getAttribute("amount")%> ;
	var temCustomerData = <%=request.getAttribute("orderDetail")%>;
    var customerId = temCustomerData.domainResponse.entitiesResponse.baseDTO.customer.customerId;
    
    var pixelet = new Webshop.Views.Pixlet();
    pixelet.render(pxlOrderId , pxlOrderAmount , customerId);
	
	}
		};
		
		function logoutAccountMethod(){
				$.cookie('FNY_CCE_EXECUTIVE',"",{path: '/'});
				var customerLoginId = $.cookie('COOKIE_FNY_LOGIN_ID');
				var customerId = $.cookie('COOKIE_FNY_CUSTOMER_ID');
				var logout = {"customer":{"loginId":customerLoginId,"customerId":customerId},"incident":"LOGOUT_CLICKED"},
				json_data = JSON.stringify({
					logout : logout
				}), 
				urlTemp = URL_PROPERTIES
				.get('LOGOUT_USER'),
				urlRoot = urlTemp.replace("{deviceId}", $.cookie($('#FnyCustomToken').data('tokenid')));

				$.ajax({
					url : urlRoot,
					type : 'POST',
					data : json_data,
					contentType : 'application/json; charset=utf-8',
					dataType : 'json',
					success: function(data) {
						$.removeCookie('COOKIE_FNY_CUSTOMER_ID', {
                            path: '/'
                        });
                        $.removeCookie('COOKIE_FNY_LOGIN_ID', {
                            path: '/'
                        });
                        $.removeCookie('FNY_CCE_EXECUTIVE', {
                            path: '/'
                        });
                        $.removeCookie('_FUI', {
                            path: '/'
                        });
                        $.removeCookie('_SC', {
                            path: '/'
                        });
                        
						 var redirectURL = window.location.origin +"/admin/customerCare.htm";
				         window.location.replace(redirectURL);
					},
					async : true
				});
		}
</script>
			
	

			
			</c:if>
			<!-- <h4><img src="/fny-theme/images/icon_facebook1.png" /> Earn <strong>10 bonus</strong> rewards points by sharing your purchase on facebook!!</h4> -->
			
		


<span id="orderData" style="display: none;"><%=request.getAttribute("orderDetail")%></span>
<span id="orderAggregatedData" style="display: none;"><%=request.getAttribute("aggregatedOrderDetail")%></span>

		<div class="row sharing-area" id="orderInfo"></div>
		<div id="pixletdatainfo"></div>
</div>



<div style="display:none;">
<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/993839940/?value=1.00&amp;currency_code=INR&amp;label=1TSKCLTDhAMQxJbz2QM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>

<!-- DGM Script -->
 
<!-- TYROO SCRIPT -->



<!-- Advantage Script -->


<!-- Komli Script -->



<!-- Facebook Script -->


<noscript>
<img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/offsite_event.php?id=6006387383214&amp;value=<%=request.getAttribute("amount")%>" />
</noscript>

<!-- Facebook sokrati -->

          

<!-- Yebhi -->

<!--Yahoo Search Pixel-->

<script>
(function(w,d,t,r,u){var f,n,i;w[u]=w[u]||[],f=function(){var o={ti:"4041030"};o.q=w[u],w[u]=new UET(o),w[u].push("pageLoad")},n=d.createElement(t),n.src=r,n.async=1,n.onload=n.onreadystatechange=function(){var s=this.readyState;s&&s!=="loaded"&&s!=="complete"||(f(),n.onload=n.onreadystatechange=null)},i=d.getElementsByTagName(t)[0],i.parentNode.insertBefore(n,i)})(window,document,"script","//bat.bing.com/bat.js","uetq");
</script>
<noscript>
<img src="//bat.bing.com/action/0?ti=4041030&Ver=2" height="0" width="0" style="display:none; visibility: hidden;" />
</noscript>
<!--Yahoo Search Pixel Ends-->



  

  
