define(['jquery', 'underscore', 'backbone', 'jquery.cookie', 'properties_url', 'properties_user'], function($, _, Backbone, jCookie, propertiesURL, propertiesUser) {
    'use strict';
    var CartLightBox = Backbone.Model.extend({

        sync: function(method, model, options) {
            var data = model.toJSON(),
                fnyToken = $.cookie('FNY_TOKEN_ONE'),
                /*customerId = ($.cookie('COOKIE_FNY_CUSTOMER_ID')) ? $.cookie('COOKIE_FNY_CUSTOMER_ID') : 0,*/
                urlBase = URL_PROPERTIES.get('SERVICE_URL_GET_CART'),
                /*cartInfo = 'deviceId=' + fnyToken + '&customerId=' + customerId,*/
                urlRoot = urlBase.replace("{deviceId}", fnyToken),

                params = _.extend({
                    type: 'GET',
                    dataType: 'json',
                    url: urlRoot,
                    processData: false,
                    data: $.param(data)
                }, options);

            return $.ajax(params);
        }
    });
    return CartLightBox;
});