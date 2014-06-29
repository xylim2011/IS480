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
    <div class="container topic" id="topic">
        <div id="gt-grid-selector" class="gt-grid-control">
            <span class="gt-grid-icon"></span>
            <div class="gt-grid-select">
                <div></div><div></div><div></div><div></div><div></div>
                <div></div><div></div><div></div><div></div><div></div>
                <div></div><div></div><div></div><div></div><div></div>
                <div></div><div></div><div></div><div></div><div></div>
                <div></div><div></div><div></div><div></div><div></div>
            </div>
        </div>
        <div id="gt-grid" class="gt-grid">
            <div><h1>#is480</h1></div>

            <div><h3>wakame</h3></div>
            <div><h3>lettuce water</h3></div>
            <div><h3>turnip maize</h3></div>
            <div><h3>wakame</h3></div>
            <div><h3>lettuce water</h3></div>
            <div><h3>turnip maize</h3></div>
            <div><h3>bunya nuts</h3></div>
            <div><h3>celery</h3></div>
            <div><h3>celery</h3></div>
            <div><h3>greens lotus</h3></div>
            <div><h3>arugula beet</h3></div>
            <div><h3>desert raisin</h3></div>
            <div><h3>mustard burdock</h3></div>
            <div><h3>potato bush</h3></div>
            <div><h3>catsear</h3></div>
            <div><h3>water chestnut</h3></div>
            <div><h3>bok choy</h3></div>
            <div><h3>pea sprouts</h3></div>
            <div><h3>gumbo parsley</h3></div>
            <div><h3>bitterleaf</h3></div>
            <div><h3>spinach rock</h3></div>
            <div><h3>tigernut garlic</h3></div>
            <div><h3>caulie</h3></div>
            <div><h3>seakale</h3></div>
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