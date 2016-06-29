define(['jquery', 'underscore', 'backbone', 'jquery.cookie', 'properties_url', 'properties_user'], function($, _, Backbone, jCookie, propertiesURL, propertiesUser) {
    'use strict';
    var CreateOrder = Backbone.Model.extend({

        sync: function(method, model, options) {

            var data = model.toJSON(),
                orderId = data.orderId,
                paymentChannel = data.paymentCode,
                fnyToken = $.cookie('FNY_TOKEN_ONE'),
                urlBase = URL_PROPERTIES.get('SERVICE_URL_CREATE_ORDER'),
                urlBase = urlBase.replace("{deviceId}", fnyToken),
                urlRoot = urlBase.replace("{paymentChannel}", paymentChannel),

                params = _.extend({
                    type: 'POST',
                    beforeSend: function(xhr) {
                        xhr.withCredentials = true;
                    },
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    url: urlRoot,
                    processData: false,
                    data: ""
                }, options);

            return $.ajax(params);
        }
    });
    return CreateOrder;
});