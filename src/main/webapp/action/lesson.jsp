<%-- 
    Document   : home
    Created on : May 16, 2014, 1:18:51 PM
    Author     : Sherman
--%>

<%@include file="../static/header.jsp" %> 
<%@include file="../static/navbar.jsp" %> 

<% if (session.getAttribute("twitterUser") != null) {
%><script>var user = JSON.parse('<%=session.getAttribute("twitterUser")%>')</script><%
    }%>
<body id="home">
    <div class="overlay"></div>
    <div class="fixed" id="lesson">
        <!--start tweet wrappers-->
        <div class="lesson_holder">

        </div>
    </div>

    <!--end tweet wrappers-->
    <div class="topic" id="topic">
        <div id="gt-grid-selector" class="gt-grid-control">
            <span class="glyphicon glyphicon-th topic_menu"></span>
            <div class="gt-grid-select">
                <div></div><div></div><div></div><div></div><div></div>
                <div></div><div></div><div></div><div></div><div></div>
                <div></div><div></div><div></div><div></div><div></div>
                <div></div><div></div><div></div><div></div><div></div>
                <div></div><div></div><div></div><div></div><div></div>
            </div>
        </div>
        <div id="gt-grid" class="gt-grid">
            <div><h1>#is480</h1>
                <span class="close_full">X</span>
            </div>

            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
            <div><h3></h3></div>
        </div>

    </div>
</body>

<%@include file="../static/footer.jsp" %> 
<script src="library/js/lesson.js" type="text/javascript"></script>

<script>
    Grid.init({
        transition: true
    });
</script>