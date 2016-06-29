<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<div>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> -->
<script>
$(window).load(function(){
	var data = ${navigationData};
	 var previewnavigationView = new Webshop.Views.NavigationView();
	 previewnavigationView.render(data);
});
</script>

</div>
