(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['ordersummary'] = template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Handlebars.helpers); data = data || {};
  var buffer = "", stack1, stack2, options, functionType="function", escapeExpression=this.escapeExpression, self=this, helperMissing=helpers.helperMissing;

function program1(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n							";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(6, program6, data),fn:self.program(2, program2, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n						";
  return buffer;
  }
function program2(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n								";
  stack2 = helpers.each.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions), {hash:{},inverse:self.noop,fn:self.program(3, program3, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							";
  return buffer;
  }
function program3(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n									";
  options = {hash:{},inverse:self.noop,fn:self.program(4, program4, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, (depth0 && depth0.promotionType), "voucher", options) : helperMissing.call(depth0, "lowerEqual", (depth0 && depth0.promotionType), "voucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n								";
  return buffer;
  }
function program4(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n										<form id=\"formedit1\" class=\"apply-offer-form\">\r\n											<label>";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "</label>\r\n											<a id=\"editbutton1\" class=\"edit-offer go\" data-type=\"Voucher\" data-value=\"";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">Edit</a>\r\n										</form>\r\n									";
  return buffer;
  }

function program6(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n								";
  options = {hash:{},inverse:self.noop,fn:self.program(7, program7, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "voucher", options) : helperMissing.call(depth0, "lowerEqual", ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "voucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							";
  return buffer;
  }
function program7(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n									<form id=\"formedit1\" class=\"apply-offer-form\">\r\n										<label>"
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</label>\r\n										<a id=\"editbutton1\" class=\"edit-offer go\" data-type=\"Voucher\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">Edit</a>\r\n									</form>\r\n								";
  return buffer;
  }

function program9(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n							";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(14, program14, data),fn:self.program(10, program10, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n						";
  return buffer;
  }
function program10(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n								";
  stack2 = helpers.each.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions), {hash:{},inverse:self.noop,fn:self.program(11, program11, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							";
  return buffer;
  }
function program11(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n									";
  options = {hash:{},inverse:self.noop,fn:self.program(12, program12, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, (depth0 && depth0.promotionType), "personalvoucher", options) : helperMissing.call(depth0, "lowerEqual", (depth0 && depth0.promotionType), "personalvoucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n								";
  return buffer;
  }
function program12(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n										<form id=\"formedit4\" class=\"apply-offer-form\">\r\n											<label>";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "</label>\r\n											<a id=\"editbutton4\" class=\"edit-offer go\" data-type=\"PersonalVoucher\" data-value=\"";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">Edit</a>\r\n										</form>\r\n									";
  return buffer;
  }

function program14(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n								";
  options = {hash:{},inverse:self.noop,fn:self.program(15, program15, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "personalvoucher", options) : helperMissing.call(depth0, "lowerEqual", ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "personalvoucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							";
  return buffer;
  }
function program15(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n									<form id=\"formedit4\" class=\"apply-offer-form\">\r\n										<label>"
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</label>\r\n										<a id=\"editbutton4\" class=\"edit-offer go\" data-type=\"PersonalVoucher\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">Edit</a>\r\n									</form>\r\n								";
  return buffer;
  }

function program17(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n							<form id=\"formedit2\" class=\"apply-offer-form\">\r\n								<label>"
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</label>\r\n								<a id=\"editbutton2\" class=\"edit-offer go\" data-type=\"Reward\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">Edit</a>\r\n							</form>	\r\n						";
  return buffer;
  }

function program19(depth0,data) {
  
  
  return "\r\n							<form id=\"formapply2\" class=\"edit-offer-form\">\r\n								<input type=\"text\" id=\"RewardBox\" placeholder=\"Enter Points\" value=\"\" />\r\n								<!-- <a href=\"#\" id=\"applyRewards\" class=\"apply-offer go\" data-type=\"Reward\">Apply</a> -->\r\n								<button type=\"button\" id=\"applyRewards\" class=\"apply-offer go\" data-type=\"Reward\">Apply</button>\r\n							</form>\r\n						";
  }

function program21(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n							<form id=\"formedit3\" class=\"apply-offer-form\">\r\n								<label>"
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</label>\r\n								<a id=\"editbutton3\" class=\"edit-offer go\" data-type=\"StoredCredit\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">Edit</a>\r\n							</form>\r\n						";
  return buffer;
  }

function program23(depth0,data) {
  
  
  return "\r\n							<form id=\"formapply3\" class=\"edit-offer-form\">\r\n								<input type=\"text\" id=\"StoredCreditBox\" placeholder=\"Enter Credits\" value=\"\" />\r\n								<button type=\"button\" class=\"apply-offer go\" data-type=\"StoredCredit\">Apply</button>\r\n							</form>\r\n						";
  }

function program25(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n						";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(42, program42, data),fn:self.program(26, program26, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					";
  return buffer;
  }
function program26(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n							";
  stack2 = helpers.each.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products), {hash:{},inverse:self.noop,fn:self.program(27, program27, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n						";
  return buffer;
  }
function program27(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n								";
  stack1 = helpers['if'].call(depth0, (depth0 && depth0.promotions), {hash:{},inverse:self.noop,fn:self.program(28, program28, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n							";
  return buffer;
  }
function program28(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n									";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(36, program36, data),fn:self.program(29, program29, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n								";
  return buffer;
  }
function program29(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n										";
  stack1 = helpers.each.call(depth0, (depth0 && depth0.promotions), {hash:{},inverse:self.noop,fn:self.program(30, program30, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\r\n									";
  return buffer;
  }
function program30(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n											";
  options = {hash:{},inverse:self.program(33, program33, data),fn:self.program(31, program31, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, (depth0 && depth0.promotionType), "voucher", options) : helperMissing.call(depth0, "lowerEqual", (depth0 && depth0.promotionType), "voucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n										";
  return buffer;
  }
function program31(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n												<li id=\"couponrow\">\r\n													<span class=\"product-value-label\">Coupon (";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + ")</span> \r\n													<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"voucher\" data-value=\"";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">remove</a>\r\n													<span class=\"product-value\" id=\"couponValue\">INR ";
  if (stack1 = helpers.value) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.value); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span>\r\n												</li>\r\n											";
  return buffer;
  }

function program33(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n												";
  options = {hash:{},inverse:self.noop,fn:self.program(34, program34, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, (depth0 && depth0.promotionType), "personalvoucher", options) : helperMissing.call(depth0, "lowerEqual", (depth0 && depth0.promotionType), "personalvoucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n											";
  return buffer;
  }
function program34(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n													<li id=\"couponrow\">\r\n														<span class=\"product-value-label\">Coupon (";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + ")</span> \r\n														<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"voucher\" data-value=\"";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">remove</a>\r\n														<span class=\"product-value\" id=\"couponValue\">INR ";
  if (stack1 = helpers.value) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.value); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span>\r\n													</li>\r\n												";
  return buffer;
  }

function program36(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n										";
  options = {hash:{},inverse:self.program(39, program39, data),fn:self.program(37, program37, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "voucher", options) : helperMissing.call(depth0, "lowerEqual", ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "voucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n										\r\n									";
  return buffer;
  }
function program37(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n											<li id=\"couponrow\">\r\n												<span class=\"product-value-label\">Coupon ("
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + ")</span> \r\n												<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"voucher\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">remove</a>\r\n												<span class=\"product-value\" id=\"couponValue\">INR "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.value)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n											</li>\r\n										";
  return buffer;
  }

function program39(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n											";
  options = {hash:{},inverse:self.noop,fn:self.program(40, program40, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "personalvoucher", options) : helperMissing.call(depth0, "lowerEqual", ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "personalvoucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n										";
  return buffer;
  }
function program40(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n												<li id=\"couponrow\">\r\n													<span class=\"product-value-label\">Coupon ("
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + ")</span> \r\n													<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"voucher\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">remove</a>\r\n													<span class=\"product-value\" id=\"couponValue\">INR "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.value)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n												</li>\r\n											";
  return buffer;
  }

function program42(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n							";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions), {hash:{},inverse:self.noop,fn:self.program(43, program43, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n						";
  return buffer;
  }
function program43(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n								";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(50, program50, data),fn:self.program(44, program44, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							";
  return buffer;
  }
function program44(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n									";
  stack2 = helpers.each.call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions), {hash:{},inverse:self.noop,fn:self.program(45, program45, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n								";
  return buffer;
  }
function program45(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n										";
  options = {hash:{},inverse:self.program(48, program48, data),fn:self.program(46, program46, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, (depth0 && depth0.promotionType), "voucher", options) : helperMissing.call(depth0, "lowerEqual", (depth0 && depth0.promotionType), "voucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n									";
  return buffer;
  }
function program46(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n											<li id=\"couponrow\">\r\n												<span class=\"product-value-label\">Coupon (";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + ")</span> \r\n												<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"voucher\" data-value=\"";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">remove</a>\r\n												<span class=\"product-value\" id=\"couponValue\">INR ";
  if (stack1 = helpers.value) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.value); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span>\r\n											</li>\r\n										";
  return buffer;
  }

function program48(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n											";
  options = {hash:{},inverse:self.noop,fn:self.program(31, program31, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, (depth0 && depth0.promotionType), "personalvoucher", options) : helperMissing.call(depth0, "lowerEqual", (depth0 && depth0.promotionType), "personalvoucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n										";
  return buffer;
  }

function program50(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n									";
  options = {hash:{},inverse:self.program(53, program53, data),fn:self.program(51, program51, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "voucher", options) : helperMissing.call(depth0, "lowerEqual", ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "voucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n								";
  return buffer;
  }
function program51(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n										<li id=\"couponrow\">\r\n											<span class=\"product-value-label\">Coupon ("
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + ")</span> \r\n											<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"voucher\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">remove</a>\r\n											<span class=\"product-value\" id=\"couponValue\">INR "
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.value)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n										</li>\r\n									";
  return buffer;
  }

function program53(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n										";
  options = {hash:{},inverse:self.noop,fn:self.program(54, program54, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "personalvoucher", options) : helperMissing.call(depth0, "lowerEqual", ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "personalvoucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n									";
  return buffer;
  }
function program54(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n											<li id=\"couponrow\">\r\n												<span class=\"product-value-label\">Coupon ("
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + ")</span> \r\n												<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"voucher\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">remove</a>\r\n												<span class=\"product-value\" id=\"couponValue\">INR "
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.value)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n											</li>\r\n										";
  return buffer;
  }

function program56(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n						";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(64, program64, data),fn:self.program(57, program57, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					";
  return buffer;
  }
function program57(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n							";
  stack2 = helpers.each.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions), {hash:{},inverse:self.noop,fn:self.program(58, program58, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n						";
  return buffer;
  }
function program58(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n								";
  options = {hash:{},inverse:self.program(61, program61, data),fn:self.program(59, program59, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, (depth0 && depth0.promotionType), "voucher", options) : helperMissing.call(depth0, "lowerEqual", (depth0 && depth0.promotionType), "voucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							";
  return buffer;
  }
function program59(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n									<li id=\"couponrow\">\r\n											<span class=\"product-value-label\">Coupon (";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + ")</span> \r\n											<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"";
  if (stack1 = helpers.promotionType) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.promotionType); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" data-value=\"";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">remove</a>\r\n											<span class=\"product-value\" id=\"couponValue\">INR ";
  if (stack1 = helpers.value) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.value); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span>\r\n									</li>\r\n								";
  return buffer;
  }

function program61(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n									";
  options = {hash:{},inverse:self.noop,fn:self.program(62, program62, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, (depth0 && depth0.promotionType), "personalvoucher", options) : helperMissing.call(depth0, "lowerEqual", (depth0 && depth0.promotionType), "personalvoucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n								";
  return buffer;
  }
function program62(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n										<li id=\"couponrow\">\r\n												<span class=\"product-value-label\">Coupon (";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + ")</span> \r\n												<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\"";
  if (stack1 = helpers.promotionType) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.promotionType); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\" data-value=\"";
  if (stack1 = helpers.voucherCode) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.voucherCode); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "\">remove</a>\r\n												<span class=\"product-value\" id=\"couponValue\">INR ";
  if (stack1 = helpers.value) { stack1 = stack1.call(depth0, {hash:{},data:data}); }
  else { stack1 = (depth0 && depth0.value); stack1 = typeof stack1 === functionType ? stack1.call(depth0, {hash:{},data:data}) : stack1; }
  buffer += escapeExpression(stack1)
    + "</span>\r\n										</li>\r\n									";
  return buffer;
  }

function program64(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n							";
  options = {hash:{},inverse:self.program(67, program67, data),fn:self.program(65, program65, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "voucher", options) : helperMissing.call(depth0, "lowerEqual", ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "voucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							\r\n							\r\n						";
  return buffer;
  }
function program65(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n							<li id=\"couponrow\">\r\n								<span class=\"product-value-label\">Coupon ("
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + ")</span> \r\n								<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\""
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">remove</a>\r\n								<span class=\"product-value\" id=\"couponValue\">INR "
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.value)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n							</li>\r\n							";
  return buffer;
  }

function program67(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n								";
  options = {hash:{},inverse:self.noop,fn:self.program(68, program68, data),data:data};
  stack2 = ((stack1 = helpers.lowerEqual || (depth0 && depth0.lowerEqual)),stack1 ? stack1.call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "personalvoucher", options) : helperMissing.call(depth0, "lowerEqual", ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType), "personalvoucher", options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							";
  return buffer;
  }
function program68(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n								<li id=\"couponrow\">\r\n									<span class=\"product-value-label\">Coupon ("
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + ")</span> \r\n									<a href=\"#\" title=\"Remove Coupon\" class=\"remove-offer del-but\" data-type=\""
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.promotionType)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.voucherCode)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">remove</a>\r\n									<span class=\"product-value\" id=\"couponValue\">INR "
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions)),stack1 == null || stack1 === false ? stack1 : stack1.value)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n								</li>\r\n								";
  return buffer;
  }

function program70(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n						";
  options = {hash:{},inverse:self.noop,fn:self.program(71, program71, data),data:data};
  stack2 = ((stack1 = helpers.notEqual || (depth0 && depth0.notEqual)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount), 0, options) : helperMissing.call(depth0, "notEqual", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount), 0, options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					";
  return buffer;
  }
function program71(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n							<li id=\"rewardrow\">\r\n								<span class=\"product-value-label\">Rewards ("
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + " Points)</span> \r\n								<a href=\"#confirmation-popup\" title=\"Remove Reward Points\" class=\"remove-offer del-but\" data-type=\"Reward\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">remove</a>\r\n								<span class=\"product-value\" id=\"rewardsValue\">INR "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n							</li>\r\n						";
  return buffer;
  }

function program73(depth0,data) {
  
  var buffer = "", stack1, stack2, options;
  buffer += "\r\n						";
  options = {hash:{},inverse:self.noop,fn:self.program(74, program74, data),data:data};
  stack2 = ((stack1 = helpers.notEqual || (depth0 && depth0.notEqual)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount), 0, options) : helperMissing.call(depth0, "notEqual", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount), 0, options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					";
  return buffer;
  }
function program74(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n							<li id=\"creditrow\">\r\n								<span class=\"product-value-label\">Credits (INR "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + ")</span> \r\n								<a href=\"#confirmation-popup\" title=\"Remove Store Credits\" class=\"remove-offer del-but\" data-type=\"StoredCredit\" data-value=\""
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "\">remove</a>\r\n								<span class=\"product-value\" id=\"creditsValue\">INR "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n							</li>	\r\n						";
  return buffer;
  }

function program76(depth0,data) {
  
  var buffer = "", stack1;
  buffer += " "
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1[0])),stack1 == null || stack1 === false ? stack1 : stack1.currencyType)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + " ";
  return buffer;
  }

function program78(depth0,data) {
  
  var buffer = "", stack1;
  buffer += " "
    + escapeExpression(((stack1 = ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.currencyType)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + " ";
  return buffer;
  }

function program80(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\r\n					<li>\r\n						<span class=\"product-value-label\">Shipping <i>Add 100 points more to get free Shipping</i></span> \r\n						<span class=\"product-value\"> INR "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.shippingCharge)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n					</li>\r\n					";
  return buffer;
  }

function program82(depth0,data) {
  
  
  return "\r\n					";
  }

function program84(depth0,data) {
  
  var buffer = "", stack1, stack2;
  buffer += "\r\n						";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(87, program87, data),fn:self.program(85, program85, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					";
  return buffer;
  }
function program85(depth0,data) {
  
  var buffer = "", stack1, options;
  buffer += "\r\n							<li class=\"textbold\">Products expected to be delivered within ";
  options = {hash:{},data:data};
  buffer += escapeExpression(((stack1 = helpers.subtract || (depth0 && depth0.subtract)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), options) : helperMissing.call(depth0, "subtract", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), options)))
    + " to ";
  options = {hash:{},data:data};
  buffer += escapeExpression(((stack1 = helpers.add || (depth0 && depth0.add)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), options) : helperMissing.call(depth0, "add", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), options)))
    + " days. </li>\r\n						";
  return buffer;
  }

function program87(depth0,data) {
  
  var buffer = "", stack1, options;
  buffer += "\r\n							<li class=\"textbold\">Product expected to be delivered within ";
  options = {hash:{},data:data};
  buffer += escapeExpression(((stack1 = helpers.subtract || (depth0 && depth0.subtract)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), options) : helperMissing.call(depth0, "subtract", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), options)))
    + " to ";
  options = {hash:{},data:data};
  buffer += escapeExpression(((stack1 = helpers.add || (depth0 && depth0.add)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), options) : helperMissing.call(depth0, "add", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), options)))
    + " days. </li>\r\n						";
  return buffer;
  }

  buffer += "<div id=\"left\" class=\"sidebar-floating\">\r\n	<div id=\"sidebar\" class=\"order-sum-outer\">\r\n		<div class=\"order-sum-box\">\r\n			<span class=\"sum-heading\">order summary</span>\r\n			<div class=\"sum-content\">\r\n				<!-- redeem area starts here -->\r\n				<div class=\"redeem-area global\" id=\"globalOffersBox\">\r\n					<div id=\"coupon-box\">\r\n						<div class=\"rightmark\"></div>\r\n						<form id=\"formapply1\">\r\n							<input type=\"text\" id=\"voucherBox\" placeholder=\"Coupon Code\" value=\"\" />\r\n							<a href=\"#\" class=\"apply-offer go\" data-type=\"voucher\">Apply</a>\r\n						</form>\r\n						";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions), {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					</div>\r\n					<span class=\"coupon\" data-offer-type=\"coupon\">Use Promo Code</span>\r\n					<div class=\"clear\"></div>\r\n				</div>\r\n				\r\n				<span class=\"offers-link personal\">Click here to get more saving <span class=\"questionmark\">? <span class=\"offertooltip\">click here to apply personal coupon store credits and rewards points</span></span></span>\r\n				<div class=\"redeem-area personal\" id=\"personalOffersBox\">\r\n					<div id=\"personal-voucher-box\">\r\n						<form id=\"formapply4\">\r\n							<select class=\"select\" id=\"PersonalVoucherBox\" data-type=\"PersonalVoucher\">\r\n								<option value=\"\">Select Voucher</option>\r\n							</select>\r\n						</form>\r\n						";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions), {hash:{},inverse:self.noop,fn:self.program(9, program9, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					</div>					\r\n					<span class=\"offer-option personal-voucher\" data-offer-type=\"personal-voucher\">Personal Coupon</span>\r\n					<div class=\"clear\"></div>\r\n					\r\n					<div id=\"rewards-box\">\r\n						";
  options = {hash:{},inverse:self.program(19, program19, data),fn:self.program(17, program17, data),data:data};
  stack2 = ((stack1 = helpers.notEqual || (depth0 && depth0.notEqual)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount), 0, options) : helperMissing.call(depth0, "notEqual", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount), 0, options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					</div>\r\n					<span class=\"offer-option rewards\" data-offer-type=\"rewards\">Redeem Rewards <i id=\"rewardsAvailableBalance\" data-value=\"0\">(0 Points)</i></span>\r\n					<div class=\"clear\"></div>\r\n\r\n					<div id=\"credits-box\">\r\n						";
  options = {hash:{},inverse:self.program(23, program23, data),fn:self.program(21, program21, data),data:data};
  stack2 = ((stack1 = helpers.if_gt || (depth0 && depth0.if_gt)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount), 0, options) : helperMissing.call(depth0, "if_gt", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount), 0, options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					</div>\r\n					<span class=\"offer-option credits\" data-offer-type=\"credits\">Redeem Credits <i id=\"creditsAvailableBalance\" data-value=\"0\">(INR 0)</i></span>\r\n					<div class=\"clear\"></div>\r\n\r\n				</div>\r\n				<!-- redeem area ends here -->\r\n\r\n				<ul>\r\n					";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products), {hash:{},inverse:self.noop,fn:self.program(25, program25, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n							\r\n					";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.promotions), {hash:{},inverse:self.noop,fn:self.program(56, program56, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					\r\n					";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.rewardAmount), {hash:{},inverse:self.noop,fn:self.program(70, program70, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					\r\n					";
  stack2 = helpers['if'].call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.storedCreditAmount), {hash:{},inverse:self.noop,fn:self.program(73, program73, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					\r\n					<li>\r\n						<span class=\"product-value-label\">Sub total</span> \r\n						<span class=\"product-value\">";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(78, program78, data),fn:self.program(76, program76, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += " "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.grandOrderValue)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n					</li>\r\n					<li id=\"shippingCharges\">\r\n						<span class=\"product-value-label\">Shipping <!-- <i>Add 100 points more to get free Shipping</i> --></span> \r\n						<span class=\"product-value\">";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(78, program78, data),fn:self.program(76, program76, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += " "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.shippingCharge)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n					</li>\r\n					<!-- ";
  options = {hash:{},inverse:self.noop,fn:self.program(80, program80, data),data:data};
  stack2 = ((stack1 = helpers.notEqual || (depth0 && depth0.notEqual)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.shippingCharge), 0, options) : helperMissing.call(depth0, "notEqual", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.shippingCharge), 0, options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += " -->\r\n				</ul>\r\n\r\n				<ol>\r\n					<li><span><b>You Save :</b></span> <span>";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(78, program78, data),fn:self.program(76, program76, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += " "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.youSaveValue)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span></li>\r\n					";
  options = {hash:{},inverse:self.program(84, program84, data),fn:self.program(82, program82, data),data:data};
  stack2 = ((stack1 = helpers.equal || (depth0 && depth0.equal)),stack1 ? stack1.call(depth0, ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), 0, options) : helperMissing.call(depth0, "equal", ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.overallExpectedTimeDelivery), 0, options));
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += "\r\n					\r\n				</ol>\r\n\r\n				<div class=\"total-area\">\r\n					<span>total :</span> <span>";
  stack2 = helpers['if'].call(depth0, ((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.products)),stack1 == null || stack1 === false ? stack1 : stack1.length), {hash:{},inverse:self.program(78, program78, data),fn:self.program(76, program76, data),data:data});
  if(stack2 || stack2 === 0) { buffer += stack2; }
  buffer += " "
    + escapeExpression(((stack1 = ((stack1 = (depth0 && depth0.cart)),stack1 == null || stack1 === false ? stack1 : stack1.youPayValue)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</span>\r\n				</div>\r\n			</div>\r\n		</div>\r\n		\r\n     	<div class=\"check-avalible\">\r\n              <h3>Check serviceability at your location</h3> \r\n              <div class=\"checkservice\" id=\"checkservice\">\r\n	              <input type=\"text\" id=\"pincode\" value=\"\" />\r\n	              <a href=\"#\" class=\"checkavalable go\">Check</a> \r\n	              <span class=\"statusmsg\"><span class=\"icon\"></span><span class=\"text\"></span></span>\r\n              </div>\r\n              <div class=\"clear\"></div>\r\n            </div>\r\n            <a href=\"/billing\" class=\"btn-cartpop fancybox\" id=\"checkoutBtn\"><i class=\"icon-basket\"></i> checkout</a> 	\r\n	</div>\r\n</div>";
  return buffer;
  });
})();