define(['jquery','underscore','backbone','handlebars', 'handlebarshelpers',
	'/payment-portlet/modules/models/orderSummary.js',
	'/payment-portlet/modules/models/updatePaymentStatus.js',
	'/payment-portlet/modules/models/cartLightBox.js',
	'/payment-portlet/modules/models/feedback.js',
	'/payment-portlet/modules/templates/orderconfirmation.js',
	'/cart-lightbox-portlet/modules/templates/cartlightbox.js'
], function ($, _, Backbone, Handlebars, handlebarshelpers, OrderSummary, UpdatePaymentStatus, CartLightBox, Feedback) {
  'use strict';
  var AfterSalesView = Backbone.View.extend({
	  el: '#orderInfo',
      events: { 
    	  'click .social-share-link' : 'socialShareHandler',
          'click #submitFeedback' : 'submitOrderFeedback'
      },
      initialize: function () {

        _.bindAll(this, 'render');
        this.getOrderSummary = new OrderSummary();
        this.cartLightBox = new CartLightBox();
        this.feedback = new Feedback();
        
        Backbone.Validation.bind(this, {model: this.feedback});
        
      },
      render: function () {
    	  
    	  var orderData = $('#orderData').text(),
    	  	  data = JSON.parse(orderData);
    	  
    	  /*console.log(data);*/
    	  $('.sharing-area').html(Handlebars.templates.orderconfirmation(data));
    	  
      },
      socialShareHandler : function(e) {
			
    	  	e.preventDefault();
			
			/*console.log("clicked");
			console.log(e);*/
			
			var classesList = $(e.currentTarget).attr("class").split(' '),
				curProduct = $(e.currentTarget).closest(".gal-dis-prod"),
				sharingData = {
					url : window.location.origin + curProduct.find(".gal-prod-img").attr("href"),
					title : curProduct.find(".gal-prod-img img").attr("alt"),
					imageUrl : curProduct.find(".gal-prod-img img").attr("src"),
					description : "I found an amazing deal at fashionandyou.com and I bet you'll love it too. Check it out!",
					handlerName : classesList[1]
			};
			
			socialShare(e, sharingData);
		},
		submitOrderFeedback : function(e) {
			
			e.preventDefault();
			
			var self = this,
				currForm = $("#orderFeedbackForm"),
				userData = JSON.parse($.cookie('_FUI')),
				feedbackData = {
					name : userData.baseDTO.firstName + " " + userData.baseDTO.lastName,
					email : userData.baseDTO.loginId,
					phone : "",
		        	feedbackType : "",
		        	orderFeedbackMessage : currForm.find("#orderFeedbackMessage").val()
				};

			if(self.feedback.clear().set(feedbackData)) {
				self.feedback.save({}, {
					error : function(response) {
						alert("some error occured...");
					},
					success : function(model, response) {
				
						console.log(response);
						
						if(response.feedbackWrapper.responseCode === "SUCCESS") {

							$("#orderFeedbackForm").hide();
							$(".success-message").show();
							
							setTimeout(function(){
								window.location.replace(window.location.origin + "/home");
							}, 2000);							
							
						} else {
							$("#orderFeedbackForm").hide();
							$(".error-message1").show();
						}
						
					}
				});
			}
		}
  });
  return AfterSalesView;
});