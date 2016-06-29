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
		<!-- 	<div class="companyLogo">
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
	
	<form method="post" name="mWalletpaymentForm" action="<%=order.getmWalletUrl()%>">
		 <input type="hidden" name="mid" value="<%= order.getMid()%>" class="formborder"/>
		 <input type="hidden" name="merchantname" value="<%=order.getMerchantName()%>"  class="formborder"/>
		 <input type="hidden" name="redirecturl" value="<%=order.getRedirecturl()%>"  class="formborder"/>
		 <input type="hidden" name="email" value="<%= order.getEmail() %>" class="formborder"/>
		 <input type="hidden" name="amount" value="<%=order.getAmount()%>" class="formborder"/>
		 <input type="hidden" name="cell" value="<%=order.getPhone()%>" class="formborder"/>
		 <input type="hidden" name="orderid" value= "<%=order.getOrderId()%>" class="formborder"/>
		 <input type="hidden" name="checksum" value= "<%=order.getmWalletChecksum()%>" class="formborder"/> 
	</form>
	
	<script type="text/javascript">
    function onloadFormSubmit()
    {
       document.forms["mWalletpaymentForm"].submit();
    }
    window.onload = onloadFormSubmit;
</script>		