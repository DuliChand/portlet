<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<portlet:defineObjects />
<c:if test="${pageType eq 'groupcategory' }">
	<div id="backbone-portlet-home"
		data-category-name="<%=renderRequest.getAttribute("categoryName")%>">
		<div id="heroBanner"></div>
		<div id="freshArrival"></div>
		<div id="weRecommend"></div>
		<div id="endingSoon"></div>

	</div>

	<div id="promotionalContent1" style="display: none">${promotionalContent1}</div>
	<div id="promotionalContent2" style="display: none">${promotionalContent2}</div>
	<div id="promotionalContent3" style="display: none">${promotionalContent3}</div>

	<div id="promotionalMediumContent1" style="display: none">${promotionalMediumContent1}</div>
	<div id="promotionalMediumContent2" style="display: none">${promotionalMediumContent2}</div>

	<div id="promotionalSmallContent1" style="display: none">${promotionalSmallContent1}</div>
	<div id="promotionalSmallContent2" style="display: none">${promotionalSmallContent2}</div>

	<script>
		window.onload = function() {
			//console.log("category portlet initialized...");
			var data = ${categoryData};
			var homeView = new Webshop.Views.HomeView();
			homeView.render(data);
		};
	</script>
</c:if>
<c:if test="${pageType eq 'categoryproductlisting' }">
	<h2>Cateoryproduct listing init---</h2>
	<form id="prdtListPaging" method="post" action="<portlet:resourceURL/>">
	</form>
	<div id="backbone-portlet-product-listing"
		data-context-path="${renderRequest.contextPath}"
		data-type="<%= renderRequest.getAttribute("type") %>"
		data-id="<%= renderRequest.getAttribute("id") %>">
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
		window.onload = function() {

			var currentUrldata = window.location.href;
			var prevousUrlData = $.cookie('pageUrlData') || "";
			var stringUrlfirst = currentUrldata.split('/').pop(-1), procductlistUrl = currentUrldata
					.split('/').pop(-1), stringUrlsecond = prevousUrlData
					.split('/')[5];
			if (stringUrlfirst === stringUrlsecond
					|| stringUrlfirst === procductlistUrl) {
				$('#filterSatus').attr('data-status', true);
			}

			console.log("product listing portlet initialized...");
			var data = ${productData};
			var eventFavData = ${isFavouriteEvents};

			var productListView = new Webshop.Views.ProductListView();
			productListView.render(data, eventFavData);

			var breadcrumbView = new Webshop.Views.BreadCrumbView();
			breadcrumbView.render(data);
		};
	</script>

</c:if>
<c:if test="${pageType eq 'productdetails' }">

	<div data-context-path="${renderRequest.contextPath}"
		id="backbone-portlet-product"
		data-event-id="<%= renderRequest.getAttribute("eventId") %>"
		data-product-id="<%= renderRequest.getAttribute("vendorProductId") %>"></div>
	<!-- breadcrumb starts here -->
	<div class="breadcrumb-container"></div>
	<div class="clearfix"></div>
	<div id="productDetail"></div>
	<div id="productPromoData">${productPagePromoContent}</div>
	<div id="recentlyBrowsedProducts"></div>
	<div id="productPriceData" style="display: none" data-product-detail="">${salesprice}</div>
	<div id="erpskuid" style="display: none" data-product-detail="">${erpskuno}</div>

	<script>
		window.onload = function() {

			var data = ${productDetailData};
			isFavProduct = ${isFavouriteProduct};
			browseHistoryData = ${browseHistoryData};
			//document.getElementById("productPriceData").innerHTML = data.vendorProductWrapper.liveinventory.salePriceWithVAT;

			var productView = new Webshop.Views.ProductView();
			productView.render(data, isFavProduct);

			var breadcrumbView = new Webshop.Views.BreadCrumbView();
			breadcrumbView.render(data);

			var recentlyBrowsedView = new Webshop.Views.RecentlyBrowsedView();
			recentlyBrowsedView.render(browseHistoryData);

			$(document).bind("contextmenu", function(e) {
				if (e.target.nodeName == 'IMG') {
					return false;
				}
			});

		};
	</script>
</c:if>
