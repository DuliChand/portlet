<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portal/init.jsp"%>

<%
	int status = ParamUtil.getInteger(request, "status");

if (status > 0) {
	response.setStatus(status);
}

String exception = ParamUtil.getString(request, "exception");

String url = ParamUtil.getString(request, "previousURL");

if (Validator.isNull(url)) {
	url = PortalUtil.getCurrentURL(request);
}

url = themeDisplay.getPortalURL() + url;

boolean noSuchResourceException = false;

for (String key : SessionErrors.keySet(request)) {
	key = key.substring(key.lastIndexOf(StringPool.PERIOD) + 1);

	if (key.startsWith("NoSuch") && key.endsWith("Exception")) {
		noSuchResourceException = true;
	}
}

if (Validator.isNotNull(exception)) {
	exception = exception.substring(exception.lastIndexOf(StringPool.PERIOD) + 1);

	if (exception.startsWith("NoSuch") && exception.endsWith("Exception")) {
		noSuchResourceException = true;
	}
}
%>

<c:choose>
	<c:when
		test="<%=SessionErrors.contains(request, PrincipalException.class.getName())%>">
		<h3 class="alert alert-error">
			<liferay-ui:message key="forbidden" />
		</h3>

		<liferay-ui:message
			key="you-do-not-have-permission-to-access-the-requested-resource" />

		<br />
		<br />

		<code class="lfr-url-error"><%=HtmlUtil.escape(url)%></code>
	</c:when>
	<c:when
		test="<%=SessionErrors.contains(request, PortalException.class.getName()) || SessionErrors.contains(request, SystemException.class.getName())%>">
		<h3 class="alert alert-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message
			key="an-error-occurred-while-accessing-the-requested-resource" />

		<br />
		<br />

		<code class="lfr-url-error"><%=HtmlUtil.escape(url)%></code>
	</c:when>
	<c:when
		test="<%=SessionErrors.contains(request, TransformException.class.getName())%>">
		<h3 class="alert alert-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message
			key="an-error-occurred-while-processing-the-requested-resource" />

		<br />
		<br />

		<code class="lfr-url-error"><%=HtmlUtil.escape(url)%></code>

		<br />
		<br />

		<%
			TransformException te = (TransformException)SessionErrors.get(request, TransformException.class.getName());
		%>

		<div>
			<%=StringUtil.replace(te.getMessage(), new String[] {"<", "\n"}, new String[] {"&lt;", "<br />\n"})%>
		</div>
	</c:when>
	<c:when test="<%=noSuchResourceException%>">

		<%
		
			String prdtvalue = null;
            String navSource = ParamUtil.getString(request, "src");
            String navId = ParamUtil.getString(request, "id");
            String navEventId = null;
            String navVendorProductId = null;
           
            String catname = null;
            String lastprtnumber = null;
            String mainUrlSrc = "default";
            String[] temprdtdata = null;
            String tempdata = null;
            String productName = "";
            String mainUrl = HtmlUtil.escape(url);
            String[] tokens = mainUrl.split("/");
            if(tokens.length == 5){
                catname = tokens[3];
                tempdata = tokens[4];
                if(catname.equalsIgnoreCase("women") || catname.equalsIgnoreCase("men") || catname.equalsIgnoreCase("home_and_living") || catname.equalsIgnoreCase("kids") || catname.equalsIgnoreCase("lounge") || catname.equalsIgnoreCase("accessories") || catname.equalsIgnoreCase("shops") || catname.equalsIgnoreCase("clearance") || catname.equalsIgnoreCase("designers") || catname.equalsIgnoreCase("stores")){
                    mainUrlSrc = "productlisting";
                   
                }
               else if(catname.equalsIgnoreCase("products")) {

                 temprdtdata =  tempdata.split("_") ;
                    int size = temprdtdata.length;

                    lastprtnumber = temprdtdata[size-1];
                   if(!(lastprtnumber.equals(null))){
                       try {
                                Integer.parseInt(temprdtdata[temprdtdata.length-2]);
                                mainUrlSrc = "product";
                                navEventId = temprdtdata[size-2];
                                //System.out.println(navEventId);
                               
                                navVendorProductId = temprdtdata[size-1];
                                //System.out.println(navVendorProductId);
                               
                                for (int i = 0; i < size - 2; i++) {
                                    productName += temprdtdata[i];
                                    if(i < size-3) {
                                        productName += "_";
                                    }
                                }
                              //  System.out.println(productName);
                        }
                        catch(NumberFormatException e)
                        {
                               
                        }
                        catch(NullPointerException e)
                        {
                               
                        }
                    }else{
                    mainUrlSrc = "default";
                    }
                  
                }
               
            }
            else{
                mainUrlSrc = "default";
            }
			
			
		%>
		<%
			if(mainUrlSrc.equalsIgnoreCase("productlisting")){
		%>
		
		<div id="backbone-portlet-product-listing" class="productListingPage" data-page-value="productListingPage" data-context-path=""
			data-type="" data-id="" data-category-name="<%=catname%>" data-mainsrc="<%=mainUrlSrc%>">
			<!-- breadcrumb starts here -->
			<div class="breadcrumb-container"></div>
			<div class="clearfix"></div>
			<!-- breadcrumb ends here -->
			<div id="eventInfo"></div>
			<div class="prod-lstng-area">
				<div id="filters"></div>
				<div class="prod-area-right">
					<div class="sort-by">
						<div class="col-xs-12"></div>
					</div>
					<div class="row products" id="productListing"></div>
					<div class="product-loader">
						<span></span>
					</div>
					<div class="quickviewpopup clearfix" id="quickview1"></div>
				</div>
			</div>
		</div>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		<script>
		setTimeout(function(){
		  if($.cookie('ngAppTmpl') === null || $.cookie('ngAppTmpl') === ""|| $.cookie('ngAppTmpl') === "null" || $.cookie('ngAppTmpl') === undefined)
		  {
		    $.cookie('ngAppTmpl',true);
		  }

		},2000);
		
		 var titleName = "" ,
		 eventTempName = window.location.pathname.split('/')[2],
		 tempEvtData = eventTempName.split('-').join(' ').split('_'),
		 eventName = tempEvtData.slice(0, -1).join(' '),
		 categoryGroup = window.location.pathname.split('/')[1];
		 
		 titleName = 'Buy '+categoryGroup+' '+eventName+' Online: '+eventName+' for '+categoryGroup;
		
		 document.title = titleName;
			var filename = window.location.origin+'/app-theme/dist/styles/product-listing.css?v=0040';
			var fileref = document.createElement('link');
			fileref.setAttribute("type", "text/css");
			fileref.setAttribute("href", filename);
			fileref.setAttribute("rel", "stylesheet");
			
			document.body.appendChild(fileref);
			
			
			window.onload = function() {
				var prevousUrlData = "";
				var currentUrldata =  window.location.pathname.split('/').pop(-1);
				try{
					var prevousUrlData = getCookie('pageUrlData').split('/').pop(-1);
				}
				catch(e){
					//console.log(e);
					prevousUrlData = window.location.pathname.split('/').pop(-1);
				}
				var stringUrlfirst = currentUrldata.split('_').pop(-1),
				procductlistUrl = currentUrldata.split('/').pop(-1),
				tempStringUrlsecond = prevousUrlData.split('_');
				stringUrlsecond = tempStringUrlsecond[tempStringUrlsecond.length-2];
				if(stringUrlfirst === stringUrlsecond ){
				  $('#filterSatus').attr('data-status',true);
				}
				else{
					 $('#filterSatus').attr('data-status',false);
				}

				 	var ua = window.navigator.userAgent;
        			var msie = ua.indexOf("MSIE ");

				if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))
				        {
				        	console.log("ie 11 browser");
					var filename = window.location.origin+'/app-theme/dist/styles/product-listing.css?v=0040';
					var fileref = document.createElement('link');
					fileref.setAttribute("type", "text/css");
					fileref.setAttribute("href", filename);
					fileref.setAttribute("rel", "stylesheet");
					
					document.body.appendChild(fileref);
				}
				
				var urlData = window.location.pathname.split('/').pop(-1).split('_').pop(-1);
				var urlRoot = "", productFilterData = {};
				var eventFavData = {},
				deviceId = $.cookie($('#FnyCustomToken').data('tokenid')),
				eventId = $('#backbone-portlet-product-listing').data('id'),
				urlIsFavEvent = URL_PROPERTIES.get('IS_FAVOURITE_EVENT').replace("{deviceId}", deviceId).replace("{eventId}", urlData);
				
				
				if(!(isNaN(urlData))){

					console.log("nan event list");
					urlRoot = URL_PROPERTIES.get('EVENT_PRODUCTS');

					$('#backbone-portlet-product-listing').attr('data-id',urlData);
					$('#backbone-portlet-product-listing').attr('data-type','event');
					
					//urlRoot = 'http://172.16.2.208:8080k[/search-new/products/';	

					productFilterData = {
							"eventId" : urlData,
							"from": "0",
							"limit": "20",
							"sortBy": "asc",
							"attributeList": [],
							"priceFilters" : [] 		
					};					
				}
				else				
				{
					var urlData = window.location.pathname.split('/').pop(-1).split('_').pop(-1);
					 var groupname = $('#backbone-portlet-product-listing').data('category-name'),
					 subcategoryName = window.location.pathname.split('/').pop(-1).toLowerCase(),
					 urlRoot = URL_PROPERTIES.get('EVENT_PRODUCTS');
					 
					$('#backbone-portlet-product-listing').attr('data-type','category');
					$('#backbone-portlet-product-listing').attr('data-id',categoryMap[groupname][subcategoryName]);
					
					var catId = categoryMap[groupname][subcategoryName];
					if(catId === undefined || catId == "" ||catId == null )
						{
						var subCatArr = subcategoryName.split('_'),
						subCategory = "";
						for(var i=0; i<subCatArr.length;i++){
							subCategory = subCategory+subCatArr[i]+" ";
						}
							
						productFilterData = {
								"menuCatId" : "",
								"from": "0",
								"limit": "20",
								"sortBy": "asc",
								"searchQuery": groupname +" "+subCategory,
								"attributeList": [],
								"priceFilters" : [] 	
						};
						
						}
					else{
					productFilterData = {
							"menuCatId" : categoryMap[groupname][subcategoryName],
							"from": "0",
							"limit": "20",
							"sortBy": "asc",
							"attributeList": [],
							"priceFilters" : [] 	
					};
					}
						
				}
				
				
				/*var favouriteEvent = function(){
					$.ajax({
					method : "GET",
					url : urlIsFavEvent,
					dataType : 'json'
					
					}).done(function(data) {

						
						if(!(data == "" ||data == null ||data == undefined )){
						
							var productListView = new Webshop.Views.ProductListView();
							productListView.favEvtRender(data);
						}
						else{
							console.log('no data from favouriteEvent service--');
						}		

					

					});
				};*/


				$.ajax({
					method: 'POST',
                    beforeSend: function(xhr) {
                        xhr.withCredentials = true;
                    },
                    contentType: "application/json; charset=utf-8",
                    dataType: 'json',
                    url: urlRoot,
                    processData: false,
					data : JSON.stringify(productFilterData)
				}).done(function(data) {

					var productListView = new Webshop.Views.ProductListView();
					productListView.render(data);

					var breadcrumbView = new Webshop.Views.BreadCrumbView();
					breadcrumbView.render(data);
					
					
				});
				
				//Dynamic Meta tags
				
				
				var metaTag = document.getElementsByTagName('meta');
				for (var i=0; i < metaTag.length; i++) {
				  
				  if (metaTag[i].getAttribute("name")=='description'){
					   metaTag[i].content = 'Buy online '+eventName+'for '+categoryGroup+' in India at Fashionandyou.com';
				  }
					   
				  if (metaTag[i].getAttribute("name")=='keywords'){
						metaTag[i].content = eventName+","+eventName+"  India, buy "+categoryGroup+"'s "+eventName+", buy "+eventName+"  online, "+categoryGroup+"s "+eventName+", "+eventName+"  for "+categoryGroup+", "+eventName+"  for "+categoryGroup ;
				  }
				}
		};
		</script>	
		
		<%
			} else if(mainUrlSrc.equalsIgnoreCase("product")){
		%>

		<div data-context-path="" class="productDetailPage" data-page-value="productDetailPage" id="backbone-portlet-product"
			data-event-id="<%=navEventId%>" data-product-name="<%=productName%>"
			data-product-id="<%=navVendorProductId%>"></div>
		<!-- breadcrumb starts here -->
		<div class="breadcrumb-container"></div>
		<div class="clearfix"></div>
		<div id="productDetail"></div>
		<div id="productPromoData"></div>
		<div id="recentlyBrowsedProducts"></div>
		<div id="productPriceData" style="display: none"
			data-product-detail=""></div>
		<input type="hidden" id="erpskuid" style="display: none" value=""/>

		<script type="text/javascript" >
			window.productObj ;	
		    var tempProductName =  window.location.pathname.split('/')[2].split('-').join(' ').split('_'),
			productNameUrl = tempProductName.slice(0, -2).join(' '),
			titleName = 'Buy '+productNameUrl+'  online at best price in India';
			document.title = titleName;
			
			/*Loading Css for product detail*/
			var filename = window.location.origin+'/app-theme/dist/styles/product-detail.css?v=0040';
				var fileref = document.createElement('link');
				fileref.setAttribute("type", "text/css");
				fileref.setAttribute("href", filename);
				fileref.setAttribute("rel", "stylesheet");
				
				document.body.appendChild(fileref);
					
			window.onload = function() {
			
				var ua = window.navigator.userAgent;
        		var msie = ua.indexOf("MSIE ");

				if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))
				{
				//console.log("detial page");
				var filename = window.location.origin+'/app-theme/dist/styles/product-detail.css?v=0040';
				var fileref = document.createElement('link');
				fileref.setAttribute("type", "text/css");
				fileref.setAttribute("href", filename);
				fileref.setAttribute("rel", "stylesheet");
				
				document.body.appendChild(fileref);
			}
			
			

			var deviceId = $.cookie($('#FnyCustomToken').data('tokenid')) , browseHistoryData = {},isFavProduct = {},
			eventId = $('#backbone-portlet-product').data('event-id'),
			title = $('#backbone-portlet-product').data('product-name'),
			tempvendorProductId = $('#backbone-portlet-product').data('product-id'),
			vendorProductId = tempvendorProductId;
			if(window.location.href.indexOf("?") > -1){
				vendorProductId = tempvendorProductId.substring(0,tempvendorProductId.indexOf("?"));
			}
			else{
				//console.log('no ? found');
			}	
			
				var urlBase = URL_PROPERTIES.get('GET_PRODUCT'), 
				urlBase = urlBase.replace("{title}", title),
				//urlBase = urlBase.replace("{eventId}", title), 
				urlRoot = urlBase.replace("{vendorProductId}", vendorProductId);

				//console.log("print urlroot data" + urlRoot);
				
				var addProductBrowseUrl = URL_PROPERTIES.get('ADD_PRODUCT_BROWSING_HISTORY').replace("{deviceId}", deviceId),
				isFavProductUrl = URL_PROPERTIES.get('IS_FAVOURITE_PRODUCT').replace("{deviceId}", deviceId).replace("{vendorProductId}", vendorProductId),
				getBrowsingHistoryUrl =  URL_PROPERTIES.get('GET_PRODUCT_BROWSING_HISTORY').replace("{deviceId}", deviceId);	
				
				/*var IsFavProductView = function(data){
				
					$.ajax({
						method : "GET",
						url : isFavProductUrl,
						dataType : 'json',
						async : false
					})
					.done(function(isFavData) {
						isFavProduct = isFavData;
						var productView = new Webshop.Views.ProductView();
						productView.render(data , isFavProduct);

					});
				
				},*/
				var RecentlyBrowsedView = function(){
				
					$.ajax({
						method : "GET",
						url : getBrowsingHistoryUrl,
						dataType : 'json',
						async : false,
						cache : false
					})
					.done(function(data) {
						browseHistoryData = data
						var recentlyBrowsedView = new Webshop.Views.RecentlyBrowsedView();
						recentlyBrowsedView.render(browseHistoryData);

					});
				
				},
				
				BrowsingRender = function(productData){ 				
					$.ajax({
						method : "POST",
						url : addProductBrowseUrl,
						dataType : 'json',
						data : JSON.stringify(productData),
						async : false,
						cache : false
					})
					.done(function(data) {
						//console.log('Product added successfuly');

					});
						
				};

				//document.getElementById("productPriceData").innerHTML = data.vendorProductWrapper.liveinventory.salePriceWithVAT;
						
					$.ajax({
						method : "GET",
						url : urlRoot,
						contentType : 'application/json; charset=utf-8',
						dataType : 'json',
						async : false
					})
					.done(function(data) {	
													
						//var data = productSumData ;
						
						var productView = new Webshop.Views.ProductView();
						productView.render(data);

						var breadcrumbView = new Webshop.Views.BreadCrumbView();
						breadcrumbView.render(data);
						
						//addBrowsing Post data ----
						if(data.product){
							productData = {
								"product": [{
									"productName": data.product.name,
									"salePrice": data.product.productVariants[0].sellerSKUs[0].sp,
									"thumbnailUrl": data.product.images[0].thumbURL,
								"vendorProductId": data.product.webShopId,
									"eventId": data.product.productVariants[0].sellerSKUs[0].event.eventId
									
								}]
							};
						}
						else{
							productData = {
								"product": [{
									"productName": data.product.name,
									"salePrice": data.product.productVariants[0].sellerSKUs[0].sp,
									"thumbnailUrl": data.product.images[0].thumbURL,
								"vendorProductId": data.product.webShopId,
									"eventId": data.product.productVariants[0].sellerSKUs[0].event.eventId

								}]
							};
						}
						
						/*IsFavProductView();*/
						RecentlyBrowsedView();
						BrowsingRender(productData);
				
						});			
						
						//Dynamic metaTag for product
						
						var metaTag = document.getElementsByTagName('meta');
						for (var i=0; i < metaTag.length; i++) {
						  
						  if (metaTag[i].getAttribute("name")=='description'){
							   metaTag[i].content = 'Buy '+productNameUrl+'  online in India. We provide '+productNameUrl+' at best price in India at fashionandyou.com.';
						  }
							   
						  if (metaTag[i].getAttribute("name")=='keywords'){
								metaTag[i].content = 'Buy '+productNameUrl+', Online '+productNameUrl+', '+productNameUrl+', '+productNameUrl+'  India, Buy '+productNameUrl+'  online, Best Price '+productNameUrl;
						  }
						}
						
						

						$(document)
						.bind(
							"contextmenu",
							function(e) {
								if (e.target.nodeName == 'IMG') {
									return false;
								}
							});	
			};
		</script>
		
		
		<%
			} else if(mainUrlSrc.equalsIgnoreCase("default")) {
		%>

		<div id="errorpage">
			<div class="row clearfix">
				<h2>
					<img src="/app-theme/app/images/caution.png" /> Oops!
				</h2>
				<h3>Somthing went wrong. Please try to refresh this page</h3>
				<div class="cont-shopping">
					<a href="/">CONTINUE SHOPPING</a>
				</div>
			</div>
		</div>
		<script>
			window.location.replace(window.location.origin);
		</script>
		<%
			}
		%>

	</c:when>

	<c:otherwise>
		<h3 class="alert alert-error">
			<liferay-ui:message key="internal-server-error" />
		</h3>

		<liferay-ui:message
			key="an-error-occurred-while-accessing-the-requested-resource" />

		<br />
		<br />

		<code class="lfr-url-error"><%=HtmlUtil.escape(url)%></code>

		<%
			for (String key : SessionErrors.keySet(request)) {
					Object value = SessionErrors.get(request, key);

					if (value instanceof Exception) {
						Exception e = (Exception) value;

						_log.error(e.getMessage());

						if (_log.isDebugEnabled()) {
							_log.debug(e, e);
						}
					}
				}
		%>

	</c:otherwise>
</c:choose>

<div class="separator">
	<!-- -->
</div>

<%!private static Log _log = LogFactoryUtil
			.getLog("portal-web.docroot.html.portal.status_jsp");%>