<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/order-result.min.css"
	type="text/css" />
<style type="text/css">
.order-sum-box {
	display: none;
}
</style>
<div id="orderresult">
	<div class="row clearfix">
		<div id="result-box" class="col-sm-12 col-xs-12">
			<div class="processText">
				<h2>${errorMessage}</h2>
				<h2>Please click on submit button and place order again!</h2>
				<h2>Apologies for the inconvenience!</h2>
			</div>

			<div class="continue-shop">
				<a href="/cart">Submit</a>
			</div>
		</div>

	</div>
</div>
