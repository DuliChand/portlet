define(['jquery', 'underscore', 'backbone', 'jquery.cookie', 'properties_url', 'properties_user'], function($, _, Backbone, jCookie, propertiesURL, propertiesUser) {

    'use strict';
    var Feedback = Backbone.Model.extend({
        validation: {
            orderFeedbackMessage: {
                required: true
            }
        },
        sync: function(method, model, options) {

            var data = model.toJSON(),
                json_data,

                fnyToken = $.cookie('FNY_TOKEN_ONE'),
                urlBase = URL_PROPERTIES.get('SERVICE_URL_POST_FEEDBACK'),
                urlRoot = urlBase.replace("{deviceId}", fnyToken),
                feedbackData = {
                    "feedbackWrapper": {
                        "feedbacks": [{
                            "name": data.name,
                            "email": data.email,
                            "phone": data.phone,
                            "feedbackType": data.feedbackType,
                            "message": data.orderFeedbackMessage
                        }]
                    }
                };

            json_data = JSON.stringify(feedbackData);

            var params = _.extend({
                type: 'POST',
                beforeSend: function(xhr) {
                    xhr.withCredentials = true;
                },
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                url: urlRoot,
                processData: false,
                data: json_data
            }, options);

            return $.ajax(params);
        }
    });
    return Feedback;
});