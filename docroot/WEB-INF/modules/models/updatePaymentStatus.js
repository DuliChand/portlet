define(['jquery', 'underscore', 'backbone', 'jquery.cookie', 'properties_url', 'properties_user'], function($, _, Backbone, jCookie, propertiesURL, propertiesUser) {

    'use strict';
    var UpdatePaymentStatus = Backbone.Model.extend({

        sync: function(method, model, options) {

            var data = model.toJSON(),
                orderId = data.orderId,
                paymentCode = data.paymentCode,
                paymentStatus = data.paymentStatus,
                fnyToken = $.cookie('FNY_TOKEN_ONE'),
                urlBase = URL_PROPERTIES.get('SERVICE_URL_UPDATE_PAYMENT_STATUS'),
                urlBase = urlBase.replace("{orderId}", orderId),
                urlRoot = urlBase.replace("{deviceId}", fnyToken),

                orderPayment = {
                    "paymentStatus": paymentStatus,
                    "paymentChannel": {
                        "paymentChannelCode": paymentCode
                    }
                },

                jsonData = JSON.stringify({
                    orderPayment: orderPayment
                }),

                params = _.extend({
                    type: 'POST',
                    beforeSend: function(xhr) {
                        xhr.withCredentials = true;
                    },
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    url: urlRoot,
                    processData: false,
                    data: jsonData
                }, options);

            return $.ajax(params);
        }
    });
    return UpdatePaymentStatus;
});