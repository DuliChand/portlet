<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/order-result.min.css" type="text/css"/>
<style type="text/css">
	.order-sum-box {
		display: none;
	}
</style>
<div id="orderresult">
	<div class="row clearfix">
		<div id="result-box" class="col-sm-12 col-xs-12">
			
			<div class="unsceesTxt">
			<p class="left-side" >System Failed to Create Order</p>
			<div class="continue-shop"><a href="/payment" >Try again</a></div></div>
			<!-- 	<h4>or</h4>
			<div class="unsceesTxt"><p class="left-side">Continue Debit / Credit Card Transaction 
				</p><div class="continue-shop"><a href="/payment" >Credit/Debit</a></div></div> -->
		</div>
	</div>
</div>