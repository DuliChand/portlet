<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ page import="java.util.List"%>
<portlet:defineObjects />
<link href="/static/css/less/cmspages.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/static/js/jquery.js"></script>
<script type="text/javascript" src="/static/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/static/js/jquery.mCustomScrollbar.min.js"></script>

<div class="main-container">
  <!-- wrapper starts here -->
<div class="wrapper">
<div class="col-xs-12" id="cmsarea">
 <div class="col-xs-12 col-md-12 col-sm-12">
<div id="breadcrumb-container">
<ul class="breadcrumb">
	<li><a href="/home">Home</a></li>
	<li class="active">Press Releases</li>
</ul>
</div>
</div>
 <div class="col-xs-12 col-sm-6 col-md-6" id="press-cat">
<ul>
	
	<li><a href="/print" id="presstab2" >Print</a></li>
	<li><a href="/digital" class="active" id="presstab3" >Digital</a></li>
	<li><a href="/electronic" id="presstab4">Electronic</a></li>
	<!-- <li><a href="/awards" id="presstab5">Awards</a></li> -->
	<li><a href="/press-releases" id="presstab1">Press Releases</a></li>
</ul>


</div>
 
<div class="clearfix">&nbsp;</div>
<div class="col-xs-12 col-sm-12 col-md-12" id="press-release-right">

	<div class="cmstxt privacy-txt">
		<div class="accordian-area">		
		
		</div>
	</div>
</div>
		
<script type="text/javascript">
 var monthlistarr = ${accordData};
var year = ${accordYr};
var obj = {};
var finalarr = [];
$.each(year,function(index ,yeardata){

  $.each(monthlistarr,function(index,data){
    var j = data.toString(); 
    if(j.substr(0, 4) == yeardata){
           finalarr.push(j.substr(4, 2))
         }
  });
  
  obj[yeardata] = finalarr;
  finalarr = [];
})

 for (var i=0;i<year.length;++i) {  
 	$('.accordian-area').append('<h3>'+year[i]+'</h3><div class="according-box"><div class="according-content"><ul id="monthsList'+year[i]+'">')
	 var numData = obj[year[i]].sort(function(a, b){return a-b});
 	 for(var j=0;j< numData.length;j++)
 	 {
   			if(numData[j] == 1){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="1">January</a></li>');
		   }
		   else if(numData[j] == 2){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="2">Febuary</a></li>');
		   }
		   else if(numData[j] == 3){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="3">March</a></li>');
		   }
		   else if(numData[j] == 4){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="4">April</a></li>');
		   }
		   else if(numData[j] == 5){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="5">May</a></li>');
		   }
		   else if(numData[j] == 6){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="6">June</a></li>');
		   }
		   else if(numData[j] == 7){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="7">July</a></li>');
		   }
		   else if(numData[j] == 8){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="8">August</a></li>');
		   }
		   else if(numData[j] == 9){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="9">September</a></li>');
		   }
		   else if(numData[j] == 10){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="10">October</a></li>');
		   }
		   else if(numData[j] == 11){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="11">November</a></li>');
		   }
		   else if(numData[j] == 12){
		     $('#monthsList'+year[i]).append(' <li><a href="javascript:void(0)" data-val="12">December</a></li>');
		   }
   
 	}
 $('.accordian-area').append('</ul></div></div>'); 

}
</script>

<div class="col-xs-12 col-sm-12 col-md-12" id="press-release-left">

	<%
		List<JournalArticle> journalArticles =(List<JournalArticle>) renderRequest.getAttribute("journalArticles");
		if(journalArticles != null){     
			for(JournalArticle journalArticle : journalArticles){
	%>
		<div class="format-new">
			<span class="press-title-new"><%=journalArticle.getTitle("en_US")%></span>
			<%-- <span class="press-date-new"><%=journalArticle.getDisplayDate()%></span> --%>
		</div> </br>
	<div><%=journalArticle.getContent()%></div>
	</br>
	<%
		}
		}
	%>


</div>

</div></div></div>
<div class="clearfix">&nbsp;</div>
<!-- wrapper ends here -->
<form action="<portlet:actionURL />" method="post" id="articleFormId">
	<input type="hidden" name="<portlet:namespace/>articleYear" id="artYrId" /> 
	<input type="hidden" name="<portlet:namespace/>articleMonth" id="artMnId" />

</form>
<script>
	$(".accordian-area h3").click(function() {
		
		$("#artYrId").val($(this).html());
	});

	$(".according-content li").click(function() {
		
		$("#artMnId").val($(this).find("a").data("val"));
		$("#articleFormId").submit();
	});
</script>




