<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="com.app.dto.OrderDTO"%>
<portlet:defineObjects />
<style type="text/css">

.order-sum-box{
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

	<form method="post" name="paymentForm" action="<%=order.getUrl()%>">
		 <input type="hidden" name="key" value="<%=order.getKey()%>" class="formborder"/>
		 <input type="hidden" name="amount" value="<%=order.getAmount()%>"  class="formborder"/>
		 <input type="hidden" name="txnid" value="<%=order.getTxnid()%>" class="formborder"/>
		 <input type="hidden" name="surl" value="<%=order.getSurl()%>" class="formborder"/>
		 <input type="hidden" name="furl" value="<%=order.getFurl()%>" class="formborder"/>
		 <input type="hidden" name="firstname" value= "<%=order.getFirstname()%>" class="formborder"/>
		 <input type="hidden" name="productinfo" value= "<%=order.getProductinfo()%>" class="formborder"/>
		 <input type="hidden" name="hash" value="<%=order.getHash()%>" class="formborder"/>
	 	 <input type="hidden" name="drop_category" value="COD" class="formborder"/>
	 	 <input type="hidden" name="pg" value="<%=order.getPg()%>" class="formborder"/>
	 	 <input type="hidden" name="bankcode" value="<%=order.getBankCode()%>" class="formborder"/>
	 	 <input type="hidden" name="lastname" value="<%=order.getLastName()%>" class="formborder"/>
         <input type="hidden" name="address1" value= "<%=order.getAddress1()%>" class="formborder"/>
         <input type="hidden" name="city" value="<%=order.getCity()%>" class="formborder"/>
         <input type="hidden" name="country" value="<%=order.getCountry()%>" class="formborder"/>
 		 <input type="hidden" name="zipcode" value="<%=order.getZipcode()%>" class="formborder"/> 
 		 <input type="hidden" name="udf2" value="<%=order.getUdf2()%>" class="formborder"/> 
 		 <input type="hidden" name="udf4" value="<%=order.getUdf4()%>" class="formborder"/> 
 		 <input type="hidden" name="offer_key" value="<%=order.getOffer_key()%>" class="formborder"/> 
 		 		  		
 		  		 
 		  <!-- for amex-->
 		 <input type="hidden" name="enforce_paymethod" value= "<%=order.getEnforceMethod()%>" class="formborder"/>
 		 <input type="hidden" name="vpc_User" value= "<%=order.getVpc_User()%>" class="formborder"/>
         <input type="hidden" name="vpc_Merchant" value="<%=order.getVpc_Merchant()%>" class="formborder"/>
         <input type="hidden" name="vpc_AccessCode" value="<%=order.getVpc_AccessCode()%>" class="formborder"/>
 		 <input type="hidden" name="securesecret" value="<%=order.getSecuresecret()%>" class="formborder"/> 
	</form>
	
	<script type="text/javascript">
    function onloadFormSubmit()
    {
       document.forms["paymentForm"].submit();
    }
    window.onload = onloadFormSubmit;
</script>		
