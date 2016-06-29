define(['jquery','underscore','backbone', 'properties_url', 'properties_user'], function($, _, Backbone, propertiesURL, propertiesUser) {
  'use strict';
  var Payment = Backbone.Model.extend({
	 
      sync: function (method, model, options) {
        var data = model.toJSON(),
        	urlRoot =URL_PROPERTIES.get('SERVICE_URL_PAYMENT_CHANNELS'),
        
        	params = _.extend({
	            type: 'GET',
	            dataType: 'json',
	            url: urlRoot,
	            processData: false,
	            data: ""
	        }, options);
        return $.ajax(params);
      }
    });
  return Payment;
});