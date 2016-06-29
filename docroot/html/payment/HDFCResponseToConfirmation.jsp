<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects />

<form action="${redirectURL}" method="post"	name="hdfcRedirectForm">
		<input type="hidden" name="pgType" value="HDFC" readonly="readonly" />
	    <input type="hidden" name="responseError" value="${responseError}" readonly="readonly" /> 
		<input type="hidden" name="amount" value="${amount}" readonly="readonly" /> 
		<input type="hidden" name="deviceId" value="${deviceId}" readonly="readonly" /> 
		<input type="hidden" name="orderId" value="${orderId}" readonly="readonly" /> 
		<input type="hidden" name="status" value="${status}" readonly="readonly" />
		<input type="hidden" name="rewardsPoint" value="${rewardsPoint}" readonly="readonly" />
</form>
<script type="text/javascript">
	function onloadFormSubmit() {
		document.forms["hdfcRedirectForm"].submit();
	}
	window.onload = onloadFormSubmit;
</script>


