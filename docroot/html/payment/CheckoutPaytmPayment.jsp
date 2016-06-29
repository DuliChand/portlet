<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="com.app.dto.OrderDTO"%>
<portlet:defineObjects />

<%
	OrderDTO order = (OrderDTO) renderRequest.getAttribute("orderBean");
%>
<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="processLoading">
			<div class="processText">
				<h2>Your Transaction is being processed</h2>
				<div class="loadingImg">Please Wait</div>
				<h3>Do not refresh or go back from this page</h3>
			</div>
		</div>
	</div>
</div>
	<form method="post" name="paymentForm" action="<%=order.getUrl()%>">
		
		 <input type="hidden" name="MID" value="<%=order.getMid()%>"  class="formborder" readonly="readonly"/>
		 <input type="hidden" name="ORDER_ID" value="<%=order.getOrderId()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="CUST_ID" value="<%=order.getCustomerId()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="TXN_AMOUNT" value="<%=order.getAmount()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="CHANNEL_ID" value= "<%=order.getChannelId()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="INDUSTRY_TYPE_ID" value= "<%=order.getIndustryType()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="WEBSITE" value="<%=order.getWebsite()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="MOBILE_NO" value="<%=order.getPhone()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="EMAIL" value="<%=order.getEmail()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="CALLBACK_URL" value="<%=order.getRedirecturl()%>" class="formborder" readonly="readonly"/>
		 <input type="hidden" name="CHECKSUMHASH" value="<%=order.getChecksum()%>" class="formborder" readonly="readonly"/>
	 	  
	</form>
	
	<script type="text/javascript">
    function onloadFormSubmit()
    {
      document.forms["paymentForm"].submit();
    }
    window.onload = onloadFormSubmit;
</script>		
