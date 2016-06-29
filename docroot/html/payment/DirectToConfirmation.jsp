<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="com.app.dto.OrderDTO"%>
<portlet:defineObjects />

<style type="text/css">
	.order-sum-box {
		display: none;
	}
</style>

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
     
    	$("#invalid-popup").removeClass('invalid').addClass('cartload');
    	$("#invalid-popup h2").text("Congratulations! You just fooled the MRP. All the items in your cart are now free. Enjoy");
    	
    	$.fancybox($('#invalid-popup'),{
    		 helpers : { 
    			  overlay : {closeClick: false}
    			},
    		 'afterShow'     : function () {
    		            $('.fancybox-close').hide();
    		            
    		        }
    	});
    	setTimeout(function(){
    		document.forms["codForm"].submit();
    	},3000);
    }
    window.onload = onloadFormSubmit;
</script>		