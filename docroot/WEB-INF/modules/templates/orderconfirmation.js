(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['orderconfirmation'] = template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Handlebars.helpers); data = data || {};
  var buffer = "", stack1, stack2, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n              <div class=\"gal-dis-prod\">\r\n                 <a href=\"/product-detail/product/"
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.event)),stack1 == null || stack1 === false ? stack1 : stack1.eventId)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "/";
  if (stack2 = helpers.vendorProductId) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = (depth0 && depth0.vendorProductId); stack2 = typeof stack2 === functionType ? stack2.call(depth0, {hash:{},data:data}) : stack2; }
  buffer += escapeExpression(stack2)
    + "\" class=\"gal-prod-img\">\r\n                  <img src=\"";
  if (stack2 = helpers.thumbnailUrl) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = (depth0 && depth0.thumbnailUrl); stack2 = typeof stack2 === functionType ? stack2.call(depth0, {hash:{},data:data}) : stack2; }
  buffer += escapeExpression(stack2)
    + "\" alt=\"";
  if (stack2 = helpers.productName) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = (depth0 && depth0.productName); stack2 = typeof stack2 === functionType ? stack2.call(depth0, {hash:{},data:data}) : stack2; }
  buffer += escapeExpression(stack2)
    + "\" title=\"";
  if (stack2 = helpers.productName) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = (depth0 && depth0.productName); stack2 = typeof stack2 === functionType ? stack2.call(depth0, {hash:{},data:data}) : stack2; }
  buffer += escapeExpression(stack2)
    + "\" />\r\n                 </a> \r\n                 <a href=\"#\" class=\"gal-prod-name\">";
  if (stack2 = helpers.productName) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = (depth0 && depth0.productName); stack2 = typeof stack2 === functionType ? stack2.call(depth0, {hash:{},data:data}) : stack2; }
  buffer += escapeExpression(stack2)
    + "</a> \r\n                 <!-- <a href=\"#\"><img src=\"/fny-theme/images/share-this.png\" /></a>  -->\r\n                 <div class=\"product-share\">\r\n					<div class=\"sharearea1\">\r\n						<span class=\"listing-share1\" title=\"Share\"></span>\r\n						<!-- <span class=\"sharetxt\">Share This</span> -->\r\n						<div class=\"shareslide1\">\r\n							<a title=\"Facebook\" class=\"ifacebook facebook social-share-link\" href=\"#\"></a>\r\n							<a title=\"Twitter\" class=\"itwitter twitter social-share-link\" href=\"#\"></a>\r\n							<!-- <a href=\"#\" class=\"iemail email social-share-link\" title=\"Email\"></a> -->\r\n							<a title=\"gplus\" class=\"igplus googleplus social-share-link\" href=\"#\"></a>\r\n							<a title=\"pintrest\" class=\"ipintrest pinterest social-share-link\" href=\"#\"></a>\r\n							\r\n							</div>\r\n					</div>\r\n				</div>\r\n              </div>\r\n            ";
  return buffer;
  }

  buffer += "<!-- feedback area starts here -->\r\n        <div class=\"col-xs-12 share-products\">\r\n            <h2>Share what you have bought with your friends \r\n              <!-- <div>share all : <a href=\"#\">\r\n                <img src=\"/fny-theme/images/facebook_share.jpg\" /></a> \r\n                <a href=\"#\"><img src=\"/fny-theme/images/twitter_share.jpg\" /></a> \r\n                <a href=\"#\"><img src=\"/fny-theme/images/email_share.jpg\" /></a>\r\n              </div> -->\r\n            </h2>\r\n\r\n            <div class=\"products-gallery\">\r\n            ";
  stack2 = helpers.each.call(depth0, ((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.domainResponse)),stack1 == null || stack1 === false ? stack1 : stack1.entitiesResponse)),stack1 == null || stack1 === false ? stack1 : stack1[0])),stack1 == null || stack1 === false ? stack1 : stack1.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.orderLines), {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n            </div>\r\n        </div>\r\n        \r\n        <div id=\"que-feedback\" class=\"col-sm-9\">\r\n        <h2>We value your questions and feedback</h2>\r\n        <div class=\"querybox\">\r\n          <form id=\"orderFeedbackForm\" method=\"post\" class=\"col-xs-12 col-sm-7\">\r\n          	<h3>Help us improve your shoping experience!</h3>\r\n          	<div class=\"control-group\">\r\n				<div class=\"controls row clear\">\r\n					<label for=\"orderFeedbackMessage\" style=\"display:none;\"></label>\r\n					<textarea name=\"orderFeedbackMessage\" id=\"orderFeedbackMessage\" data-error-style=\"inline\" placeholder=\"Feedback Message\"></textarea>\r\n				</div>\r\n			</div>\r\n          	<input type=\"submit\" id=\"submitFeedback\" value=\"SUBMIT\" class=\"go\" />\r\n          </form>\r\n          \r\n          <div class=\"success-message col-xs-12\">\r\n          	<p>Thank you for giving us valuable feedback.  <br/>We will incorporate your suggestions as soon as possible. </p>\r\n          </div>\r\n          <div class=\"error-message1 col-xs-12\">\r\n          	<p>Oops!  Something went wrong, please try again your suggestions are in valuable. </p>\r\n          </div>\r\n\r\n         <div class=\"col-xs-12 col-sm-5 question-box\">\r\n            <h3>Questions?</h3>\r\n            <p>Checkout our FAQs<br>\r\n                or call us at 0124-4488800 or Email us</p>\r\n          </div>\r\n          \r\n          <div class=\"clear\"></div>\r\n        </div>\r\n    </div>\r\n ";
  return buffer;
  });
})();