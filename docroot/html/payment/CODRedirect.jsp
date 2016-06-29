<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="com.app.dto.OrderDTO"%>
<portlet:defineObjects />

<style type="text/css">
	.order-sum-box {
		display: none;
	}
</style>

<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="processLoading">
			<!-- <div class="companyLogo">
				<img src="/fny-theme/images/logo.png">
			</div> -->
			<div class="processText">
				<h2>Your Transaction is being processed</h2>
				<div class="loadingImg">Please Wait</div>
				<h3>Do not refresh or go back from this page</h3>
			</div>
		</div>
	</div>
</div>

<%
	OrderDTO order = (OrderDTO) renderRequest.getAttribute("orderBean");
%>
	
<form method="post" name="codForm" action="<%=order.getUrl()%>">		 
	 <input type="hidden" name="orderId" value= "<%=order.getOrderId()%>" class="formborder"/>
	 <input type="hidden" name="message" value= "<%=order.getMessage()%>" class="formborder"/>
	 <input type="hidden" name="deviceId" value= "<%=order.getDeviceId()%>" class="formborder"/>
	  <input type="hidden" name="amount" value= "<%=order.getAmount()%>" class="formborder"/>
	 <input type="hidden" name="pgType" value= "COD" class="formborder"/>
</form>
	
<script type="text/javascript">
    function onloadFormSubmit() {
       document.forms["codForm"].submit();
    }
    window.onload = onloadFormSubmit;
</script>		