<%-- 
    Document   : home
    Created on : May 16, 2014, 1:18:51 PM
    Author     : Sherman
--%>

<%@include file="../static/header.jsp" %> 
<%@include file="../static/navbar.jsp" %> 

<% if(session.getAttribute("twitterUser") != null){
%><script>var user = JSON.parse('<%=session.getAttribute("twitterUser")%>')</script><%
}%>
<body id="home">
    <div class="overlay"></div>
    <!-- side bar nav -->
    <div class="side_navbar">
        <div class="side_navbtns_wrapper">
             <div class="side_navdivider"></div>
            <div class="side_navbtns">
                <input type="text" id="search" value="" placeholder="    Search">
                <div class="sidebar_icons" id="find_tweets" ><i class="fa fa-search"></i></div>
            </div>
            <div class="side_navdivider"></div>
        </div>
    </div>
    <!-- end side bar nav -->
    
    <!--start tweet wrappers-->
    <div class="lesson_holder">
        
    </div>
    <!--end tweet wrappers-->
    
</body>

<%@include file="../static/footer.jsp" %> 
<script src="library/js/lesson.js" type="text/javascript"></script>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8">
