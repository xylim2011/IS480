<%@include file="../static/header.jsp" %> 
<%@include file="../static/navbar.jsp" %> 
<body>
    <div class="container">
        <div class="content">
            <div class="signin-wrapper">
                <a href="sign_in?type=twitter"> <img src="images/socialIcons/twitter-login-button.png"/></a>
                <a class="twitter-timeline" href="https://twitter.com/search?q=%23is480" data-widget-id="468294385923145730">Tweets about "#is480"</a>
            </div>
        </div>
    </div>
</body>

<%@include file="../static/footer.jsp" %> 
<script src="library/js//navbar/navbar.js" type="text/javascript"></script>

<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>


</html>