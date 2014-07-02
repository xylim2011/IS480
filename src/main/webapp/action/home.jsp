<%-- 
    Document   : home
    Created on : May 16, 2014, 1:18:51 PM
    Author     : Sherman
--%>

<%@include file="../static/header.jsp" %> 
<%@include file="../static/navbar.jsp" %> 

<div id="box"></div>

<%@include file="../static/footer.jsp" %>
<%@ page import="org.json.*" %>
<script>
$.get("get_class",{userID:"2551885110"},function(data){
	var items = [];
	$.each( data, function( key, val ) {
	  items.push( "<li id='" + key + "'>" + val + "</li>" );
	});
	$( "<ul/>", {
	  "class": "my-new-list",
	  html: items.join( "" )
	}).appendTo( "body" );
	console.log(data);
},"json");
</script>
</html>
