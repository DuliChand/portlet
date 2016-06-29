
$(window).load(function() {	
setTimeout(function(){
if($.cookie('ngAppTmpl') === null || $.cookie('ngAppTmpl') === ""|| $.cookie('ngAppTmpl') === "null" || $.cookie('ngAppTmpl') === undefined)
{
$.cookie('ngAppTmpl',true)
}

},2000);
window.globalCartObject = {} ;
 /*page url entry-----*/
	var pathName =  window.location.pathname.split('/').pop(-1);
	
	if(!(pathName === 'register')){
		if(pathName != 'login'){
			//console.log("rester check");
			$.cookie('pageUrlData', window.location.href, {
				expires: 1,
				path: '/'
			});	
		}
	}
	
/*page url entry----- ends here*/	
	
var CustomefirstRecord = parseInt($('#FnyFirstRecord').attr('data-firstRecord'));
var firstDate = new Date(parseInt($.cookie('customerFirstRecord'))).getDate();
var currDate = new Date(CustomefirstRecord).getDate();
var ExistfirstREcord = $.cookie('customerFirstRecord');
if(ExistfirstREcord == "" ||ExistfirstREcord == null ||ExistfirstREcord == undefined ||firstDate != currDate ){
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

var FnyCustomToken = $('#FnyCustomToken').data('tokenid');
	//console.log('authtoken_upper');
	
	 if($.cookie($('#FnyCustomToken').data('tokenid')) === undefined || $.cookie($('#FnyCustomToken').data('tokenid')) === "" || $.cookie($('#FnyCustomToken').data('tokenid')) === ""){
		 var urlRoot = URL_PROPERTIES.get('CREATE_TOKEN');
		 
		 $.ajax({
			 type: "GET",
			 url: urlRoot,
			 data:""	 
			 })
			 .done(function(data) {
				 $.cookie(""+FnyCustomToken+"", data.access_token, {
                     expires: 30,
                     path: '/'
                 });

			 });
     } else {
         /*self.generateCart();*/
     }
		/*var authTokenView = new Webshop.Views.AuthTokenView();
    	authTokenView.render();*/
		/*
		if($.cookie($('#FnyCustomToken').data('tokenid')) === undefined || $.cookie($('#FnyCustomToken').data('tokenid')) === "" || $.cookie($('#FnyCustomToken').data('tokenid')) === null){
			$.cookie(FnyCustomToken,$('#fnyTokenCookie').html(),{
                expires: 365,
                path: '/'
            });
     	}*/
		//console.log("cart portlet initialized...");
   
	
    setTimeout(function(){	
    	
    	//if($.cookie('FNY_CART') || $.cookie('COOKIE_FNY_LOGIN_ID')){-------------------------------------comment for testing
			if(true){
		
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
					
					if(response.responseCode === "SUCCESS"){
						if(response.cart.products){
							var cartObjData = cartProductsData(response);
							globalCartObject = cartObjData;
							
							var cartNavView = new Webshop.Views.CartNavView();
							cartNavView.render();
							
							var cartView = new Webshop.Views.CartView();
							cartView.render(cartObjData);
							
							var cartLightBoxView = new Webshop.Views.CartLightBoxView();
							cartLightBoxView.render(cartObjData);

							var orderSummaryView = new Webshop.Views.OrderSummaryView();
							orderSummaryView.render(cartObjData);
						}
						else{
							var cartView = new Webshop.Views.CartView();
							cartView.render({"cart":{}});
						}
					}
					else{
						
						var cartView = new Webshop.Views.CartView();
						cartView.render({"cart":{}});
						
						var cartLightBoxView = new Webshop.Views.CartLightBoxView();
						cartLightBoxView.render({"cart":{}});
					}
					
					var allpagePixelView = new Webshop.Views.Pixlet();
					allpagePixelView.allPagePixel();
				}
			});
						   
		}
		else{
			var cartLightBoxView = new Webshop.Views.CartLightBoxView();
			cartLightBoxView.render({"cart":{}});
			
			var cartView = new Webshop.Views.CartView();
			cartView.render({"cart":{}});
			
			var allpagePixelView = new Webshop.Views.Pixlet();
			allpagePixelView.allPagePixel();
		}	
 },500);	
		

		var date = new Date();
		 var minutes = 20160;
		 date.setTime(date.getTime() + (minutes * 60 * 1000));
		
		 
		var utmData = window.location.href.split("?").slice(1).join("?");
			    var j = utmData.split('&');
					var referdata="";
					//var windowReferrer = document.referrer;
					
					$.each(j,function(index,data){
					  
					  if(data.indexOf('utm_source') >=0 || data.indexOf('utm_medium')>=0 || data.indexOf('utm_campaign')>=0 || data.indexOf('clickid')>=0){
					  referdata=referdata+"&"+data;
					  }
					});
		
		function checkFnyToken(){			
		if($.cookie($('#FnyCustomToken').data('tokenid')) === "" ||$.cookie($('#FnyCustomToken').data('tokenid')) === undefined || $.cookie($('#FnyCustomToken').data('tokenid')) === null){
			setTimeout(function(){
				checkFnyToken();
			},5000);
		}
			else{
				sendClickStream();
			}
		};
		
		checkFnyToken();
	
		function sendClickStream(){			
					
				if(document.referrer === ""){
					if(!(referdata === "" || referdata === undefined || referdata === null)){
				    	
				    	 $.cookie("UTM_DATA", referdata, { path: '/' , expires: date });
				    	
				    	
				    }


					var utmData1 = $.cookie('UTM_DATA');
					referdata1="";
					if(!(utmData1 === "" || utmData1 === undefined || utmData1 === null)){
					    
						referdata1=utmData1;
				    	
				    }
					if(pageURL.match(/confirmation$/) == null) {
						var click = {
						url : document.URL,
						referer : "",
						protocol : "GET",
						orderId : "",
						firstRecord : $.cookie('customerFirstRecord'),
						orderValue : "",
					deviceId : $.cookie($('#FnyCustomToken').data('tokenid'))

				}, json_data = JSON.stringify({
					click : click
				}), urlRoot = URL_PROPERTIES
						.get('CLICK_STREAM_DATA');

				/*$.ajax({
					url : urlRoot,
					type : 'POST',
					data : json_data,
					contentType : 'application/json; charset=utf-8',
					dataType : 'json',
					async : true
				});*/
			      //alert(json_data)
				} 
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
					
					var flag = document.referrer.indexOf("?");
					
					if(flag < 0){
						referdata1 = "?"+referdata1;
						
					}
					
					
				if(pageURL.match(/confirmation$/) == null) {
						var click = {
						url : document.URL,
						referer : document.referrer+referdata1,
						protocol : "GET",
						orderId : "",
						orderValue : "",
						firstRecord : $.cookie('customerFirstRecord'),
					deviceId : $.cookie($('#FnyCustomToken').data('tokenid'))

						}, json_data = JSON.stringify({
							click : click
						}), urlRoot = URL_PROPERTIES
								.get('CLICK_STREAM_DATA');

						/*$.ajax({
							url : urlRoot,
							type : 'POST',
							data : json_data,
							contentType : 'application/json; charset=utf-8',
							dataType : 'json',
							async : true
						});*/
			      //alert(json_data)
				} 
				}
		}
		
			
		
});