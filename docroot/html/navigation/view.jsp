<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<div>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> -->
<script>
var navData = ${navigationData};

	window.categoryMap = {};
	var categoyid = [];
	var groupname = [];
	var catname_id = {};

	for(i=0; i< navData.categories.length;i++){ 
	  if(!(navData.categories[i].categoryName == 'Lounge' || navData.categories[i].categoryName == 'Shops' || navData.categories[i].categoryName == 'Designers' || navData.categories[i].categoryName == 'Stores' )){	  
	  		//for(j=0;j< navData.categories[i].categories.length;j++){ 	
			//console.log("nav--"+navData.categories[i].categories);
				for(k= 0; k < navData.categories[i].categories[0].categories.length; k++)
					{
						//console.log("catname--"+navData.categories[i].categories[0].categories[k].categoryName);
						catname_id[navData.categories[i].categories[0].categories[k].categoryName.split(' ').join('_').split('&').join('and').toLowerCase()] = navData.categories[i].categories[0].categories[k].categoryId;
					}
				//}			
			categoryMap[navData.categories[i].categoryName.split(' ').join('_').split('&').join('and').toLowerCase()] = catname_id;
		
		groupname =[];
		catname_id = {};
	  }
	}

$(window).load(function(){
	
	 var navigationView = new Webshop.Views.NavigationView();
     navigationView.render(navData);
});
</script>

</div>
