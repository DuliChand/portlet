(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['payment'] = template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Handlebars.helpers); data = data || {};
  var buffer = "", stack1, stack2, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n        	";
  stack1 = helpers.each.call(depth0, (depth0 && depth0.paymentChannels), {hash:{},inverse:self.noop,fn:self.program(2, program2, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n        ";
  return buffer;
  }
function program2(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n                <div class=\"select-payment row\">\r\n                    <div class=\"col-xs-12\">\r\n                        <input type=\"radio\" id=\"paymentChannel";
  if (stack1 = helpers.paymentChannelId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.paymentChannelId); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" name=\"paymentChannel\" value=\"";
  if (stack1 = helpers.paymentChannelCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.paymentChannelCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" class=\"payment-channel\" data-haspg=\"";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.pgBankMappings), {hash:{},inverse:self.noop,fn:self.program(3, program3, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\" />\r\n                        <label for=\"paymentChannel";
  if (stack2 = helpers.paymentChannelId) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = (depth0 && depth0.paymentChannelId); stack2 = typeof stack2 === functionType ? stack2.call(depth0, {hash:{},data:data}) : stack2; }
  buffer += escapeExpression(stack2)
    + "\">";
  if (stack2 = helpers.paymentName) { stack2 = stack2.call(depth0, {hash:{},data:data}); }
  else { stack2 = (depth0 && depth0.paymentName); stack2 = typeof stack2 === functionType ? stack2.call(depth0, {hash:{},data:data}) : stack2; }
  buffer += escapeExpression(stack2)
    + "</label>\r\n                    </div>\r\n                    <!--<div class=\"col-xs-5\">\r\n                        ";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.pgBankMappings), {hash:{},inverse:self.noop,fn:self.program(5, program5, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n                    </div>\r\n                    -->\r\n                </div>\r\n            ";
  return buffer;
  }
function program3(depth0,data) {
  
  var stack1;
  return escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.pgBankMappings)),stack1 == null || stack1 === false ? stack1 : stack1[0])),stack1 == null || stack1 === false ? stack1 : stack1.pgType)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1));
  }

function program5(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n                        <select name=\"select"
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.pgBankMappings)),stack1 == null || stack1 === false ? stack1 : stack1[0])),stack1 == null || stack1 === false ? stack1 : stack1.pgType)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\" id=\"select"
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.pgBankMappings)),stack1 == null || stack1 === false ? stack1 : stack1[0])),stack1 == null || stack1 === false ? stack1 : stack1.pgType)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\" class=\"select-bank\">\r\n                            <option value=\"\">--------Select----------</option>\r\n                            ";
  stack2 = helpers.each.call(depth0, ((stack1 = (depth0 && depth0.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.pgBankMappings), {hash:{},inverse:self.noop,fn:self.program(6, program6, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n                        </select>\r\n                        ";
  return buffer;
  }
function program6(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n                                <option value=\"";
  if (stack1 = helpers.bankId) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.bankId); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">";
  if (stack1 = helpers.bankName) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.bankName); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "</option>\r\n                            ";
  return buffer;
  }

function program8(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n        	<div class=\"select-payment row\">\r\n                    <div class=\"col-xs-12\">\r\n                        <input type=\"radio\" id=\"paymentChannel"
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.paymentChannels)),stack1 == null || stack1 === false ? stack1 : stack1.paymentChannelId)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\" name=\"paymentChannel\" value=\""
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.paymentChannels)),stack1 == null || stack1 === false ? stack1 : stack1.paymentChannelCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\" class=\"payment-channel\" data-haspg=\"";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.pgBankMappings), {hash:{},inverse:self.noop,fn:self.program(3, program3, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\" />\r\n                        <label for=\"paymentChannel"
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.paymentChannels)),stack1 == null || stack1 === false ? stack1 : stack1.paymentChannelId)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">"
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.paymentChannels)),stack1 == null || stack1 === false ? stack1 : stack1.paymentName)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</label>\r\n                    </div>\r\n                    <!--<div class=\"col-xs-5\">\r\n                        ";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.baseDTO)),stack1 == null || stack1 === false ? stack1 : stack1.pgBankMappings), {hash:{},inverse:self.noop,fn:self.program(5, program5, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n                    </div>\r\n                    -->\r\n                </div>\r\n        ";
  return buffer;
  }

  buffer += "<div class=\"payement-options col-xs-12\">\r\n  <div class=\"payement-box\">\r\n    <div class=\"secure_shopping_banner\"> 100% Secure Shopping </div>\r\n    <div class=\"payement-box-text\">\r\n        fashionandyou.com now brings you multiple payment options like Credit Cards<span class=\"disply-none-cc\">, ATM</span>/Debit Cards<span class=\"disply-none-cc\">, Netbanking</span> and Cash on Delivery.\r\n    </div>\r\n    <div class=\"mode-payment\">\r\n        <span>\r\n            <strong>Select Mode of Payment</strong>\r\n        </span> <div class=\"hidethis\">How does it Work ?\r\n        <a class=\"paymentMode tooltip2\" href=\"#\" shape=\"rect\">Cards\r\n            <b class=\"second\"><span class=\"heading-tooltip\"> Credit / Debit </span>\r\n                <br clear=\"none\">\r\n                A bank-issued card that allows consumers to purchase goods or services from a merchant on credit e.g. : VISA, Master Card\r\n            </b>\r\n        </a>,\r\n        <a class=\"paymentMode tooltip2 disply-none-cc \" href=\"#\" shape=\"rect\">Netbanking\r\n            <b class=\"second\">\r\n                <!-- <em class=\"outer\"></em>\r\n                <em class=\"inner\"></em> -->\r\n                <span class=\"heading-tooltip\"> What is Net Banking? </span>\r\n                <br clear=\"none\">\r\n                You can now pay for your transaction using Net Banking directly through your bank account. fashionandyou.com does not collect your banking information. On choosing Net Banking, you will be directed to your banks website for the payment process. After completing the payment process, you will be redirected back to fashionandyou.com.\r\n            </b>\r\n        </a>,\r\n        <a class=\"paymentMode tooltip3\" href=\"#\" shape=\"rect\">Cash on Delivery\r\n            <b class=\"second\">\r\n                <!-- <em class=\"outer\"></em>\r\n                <em class=\"inner\"></em> -->\r\n                <span class=\"heading-tooltip\">What is COD ?</span>\r\n                <br clear=\"none\">\r\n                <br clear=\"none\">\r\n                We verify all Cash on Delivery orders over the phone after you have made a purchase and post which we process your order. Our Customer Support team will call you within 24 hours of your purchase to verify details; alternatively you can also call us to verify your order on +91-124-4125000. Failing verification of your order may result into cancellation. Therefore we request you to provide us with a mobile phone no. where we can best reach you within 24hrs.<br clear=\"none\"><br clear=\"none\">\r\n                In case you do not accept a verified Cash on Delivery order we may need to revoke the Cash on Delivery payment option from your account for future purchases.\r\n            </b>\r\n        </a>\r\n        </div>\r\n    </div>\r\n    <form id=\"paymentChannelForm\">\r\n        ";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.paymentChannels)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(8, program8, data),fn:self.program(1, program1, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n        <div id=\"codPaymentInfo\" class=\"cod-info-box\">\r\n            <div class=\"payment-typebox\">\r\n                <ul class=\"bullet\">\r\n                    <li style=\"background-position:5px 14px;\">\r\n                        <div style=\"float:left;padding-top:0;\" class=\"row1\">\r\n                            <span style=\"float:left;padding:6px 10px 6px 0px;\">Phone No. </span>\r\n                            <span class=\"txt3 phoneNumber\" style=\"float:left;\">7345654123</span>\r\n                            <span style=\"float:left;\">\r\n                                <a href=\"/checkout/address\" class=\"edit_btn\">Edit</a>\r\n                            </span>\r\n                        </div>\r\n                    </li>\r\n                    <li>Why prepaid is better for you:\r\n                        <ul>\r\n                            <li>Your transaction is secured by Verisign.</li>\r\n                            <li>You need not be physically present at the shipping address to make a payment at delivery.</li>\r\n                            <li>You don't need to confirm your order via phone, once your order is placed.</li>\r\n                            <li>Hassle free and quicker refunds as card payments are credited directly to your account, whereas a cheque is mailed in case of Cash on Delivery.</li>\r\n                        </ul>\r\n                    </li>\r\n                    <li>In case you do not accept a verified Cash on Delivery order we may need to revoke the Cash on Delivery payment option from your account for future purchases.</li>\r\n                </ul>\r\n            </div>\r\n        </div>\r\n        <div class=\"form_control\">\r\n            <div class=\"form_control_child\">\r\n                <input type=\"submit\" name=\"payNowBtn\" id=\"payNowBtn\" class=\"btn-paynow btn-cartpop pull-right\" value=\"PAY NOW\" />\r\n                <a href=\"/billing\" shape=\"rect\" class=\"btn-back btn-cartpop pull-right\"><i class=\"icon-left\"></i> BACK</a>\r\n            </div>\r\n        </div>\r\n    </form>\r\n  </div>\r\n</div>";
  return buffer;
  });
})();