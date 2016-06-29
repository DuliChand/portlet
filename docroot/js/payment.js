$(window).load(function() {	

	
	//console.log("payment portlet initialized...");
	
	/*var cartNavView = new Webshop.Views.CartNavView();
	cartNavView.render();*/
	
	/*var orderSummaryView = new Webshop.Views.OrderSummaryView();
	orderSummaryView.render();*/
	
	$("#payment-options li").click(function(){
		var value = $(this).data("id");
		setPaymentMethod(value);
		validateCartOnPaymentSelection(value);
	});
	
	$("#debitcreditContent input[type='radio'],#mobiKwik").change(function() {
		showProceed();
	});
	
	$("#bankSelectId").change(function() {
		setBankCode(this.value);
		showProceed();
	});
	
	$("#proceedBtn").click(function(){
		submitPaymentForm();
	});

	
	

	$('.payubutton').on('click', function() {
		$(this).toggleClass('label-checked');
	});
	
	$('.mobikwikbutton').on('click', function() {
		$(this).toggleClass('label-checked');
	});
	
	function setBankCode(value) {
		$("#bankCodeID").val(value);
	}
	
	function setPaymentMethod(value) {
		
		
		$(".container-payment input[type='radio']").removeAttr('checked');
		$(".container-payment select").val("");
		
		$('.btn-proceed').hide();
		
		$("#paymentMethodID").val("payU");
		$("#paymentChannelID").val("PAYUCC");
		$("#bankCodeID").val("");
		$("#pgID").val("CC");
		
		sessionStorage.setItem("REWARD_POINTS","");
		sessionStorage.setItem("CREDITS","");
		sessionStorage.setItem("VOUCHERS","");

		if (value == "NB") {
			$("#pgID").val("NB");
			$("#paymentChannelID").val("PAYUNB");
		}
		if (value == "WL") {
			$("#paymentMethodID").val("MWALL");
			$("#pgID").val("");
			$("#paymentChannelID").val("MWALL");
		}
		if (value == "COD") {
			
			$("#paymentMethodID").val("COD");
			$("#paymentChannelID").val("COD");
			$("#bankCodeID").val("");
			$("#pgID").val("");
			
			$('.btn-proceed').show();
		}

	}

	
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
		loadjscssfile("/app-portlet/js/ordersummary.js", "js");
		
		$.ajax({
			url : urlRoot,
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			async : true,
			success: function(response){
				$('#backbone-portlet-order-summary').html(Handlebars.templates.ordersummary(response));
				
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
	
	function submitPaymentForm() {
		$(".payment-section li").each(function(index) {
			/* console.log( index + ": " + $( this ).text() ); */
			if ($("li").hasClass("active")) {
				$(this).text();
			}
		});
		$("#deviceID").val($.cookie($('#FnyCustomToken').data('tokenid')));
		$("#paymentFormId").submit();
	}

    
});


// Navigation of Payment-Options Tab

$(document).ready(function(){

	var FnyCustomToken = $('#FnyCustomToken').data('tokenid');
    
	var mainPaymentOptions = $('.payListTab'),
		firstPaymentOption = mainPaymentOptions.first();
	
	firstPaymentOption.addClass('active');
	$("#" + firstPaymentOption.attr('id') + "Content").show();
	
	$(".emi-option").on("click", function() {
    	console.log($(this).val());
		$("#" + $(this).val() + "EmiTable").show().siblings().hide();
    });
	
	$(".maintab").on("click", function() {
		
	});
	
	$( ".payListTab" ).on("click", function(e) {
		
		var currPaymentOption = e.currentTarget,
			currPaymentOptionLabel = $(currPaymentOption).attr("id");
		
		$(currPaymentOption).addClass("active").siblings().removeClass("active");
		$("#" + currPaymentOptionLabel + "Content").show().siblings('.divcontent').hide();
			
	});
	
	var paymentOptionList = $(".payListTab"),
		totalpaymentOptions = paymentOptionList.length,
		inactivePaymentOptions = 0;
	
	/*console.log(paymentOptionList);
	console.log("totalpaymentOptions: " + totalpaymentOptions);
	console.log("inactivePaymentOptions: " + inactivePaymentOptions);*/
	
	$(paymentOptionList).each(function (index, obj) {
	  
	  /*console.log(index + 1);
	  console.log($(obj).css("display"));
	  console.log($(obj).css("display") === "none");*/
	  if($(obj).css("display") === "none") {
	    inactivePaymentOptions += 1;
	  }
	
	});
	
	/*console.log("inactivePaymentOptions: " + inactivePaymentOptions);
	
	console.log(((totalpaymentOptions - inactivePaymentOptions) === 1));
	console.log(($("#cod").css("display") === "block"));*/
	
	
	if(((totalpaymentOptions - inactivePaymentOptions) === 1) && ($("#cod").css("display") === "block")) {
	/* 
	  console.log("mobile...");*/
	  $("#cod").trigger("click");
	  
	}
	
	$(window).load(function(){
		$('.portlet-body').prepend($('#backbone-portlet-cart-nav').html());
		$('.col-sm-12.col-md-8').after($('#orderSummary').html());
		$('#left').css('top','46px');
	});
	
	
	
});