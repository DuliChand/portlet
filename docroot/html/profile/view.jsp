<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />
<div id="backbone-portlet-my-account-nav"></div>
<div id="backbone-portlet-my-account-side-panel">
	<div class="product-loader"><span></span></div>
</div>
<script>
window.onload = function(){
	
	//console.log("profile portlet initialized...");
	var data = ${customerProfileData},
	subscriptionData = ${subscriptionData};
	
    var myAccountNavView = new Webshop.Views.MyAccountNavView();
    myAccountNavView.render();

    var myAccountSideView = new Webshop.Views.MyAccountSideView();
    myAccountSideView.render(data ,subscriptionData);
	
};
</script>