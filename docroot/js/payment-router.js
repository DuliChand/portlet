define(['jquery','underscore','backbone',
	'/payment-portlet/modules/models/payment.js',
	'/payment-portlet/modules/views/paymentView.js'	
], function ($, _, Backbone,Payment,PaymentView) {
  'use strict';
  /*
    * Application's Router file
    * 
    */
  var AppRouter = Backbone.Router.extend({
      initialize: function () {
    	  var paymentView = new PaymentView({model: Payment});
    	  paymentView.render();
      }
    });
  return AppRouter;
});
