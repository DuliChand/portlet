define(['jquery','underscore','backbone',
	'/payment-portlet/modules/views/afterSalesView.js'
], function ($, _, Backbone, AfterSalesView) {
  'use strict';
  /*
    * Application's Router file
    * 
    */
  var AppRouter = Backbone.Router.extend({
      initialize: function () {
    	  var afterSalesView = new AfterSalesView();
    	  afterSalesView.render();
      }
    });
  return AppRouter;
});
