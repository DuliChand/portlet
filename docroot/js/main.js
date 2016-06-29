$(document).ready(function(){
    
	var mainPaymentOptions = $('.payListTab'),
		firstPaymentOption = mainPaymentOptions.first();
	
	firstPaymentOption.addClass('active');
	$("#" + firstPaymentOption.attr('id') + "Content").show();
	
	$(".emi-option").on("click", function() {
    	//console.log($(this).val());
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
	 // console.log("mobile...");*/
	  $("#cod").trigger("click");
	  
	}
	
});