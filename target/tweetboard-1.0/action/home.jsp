<%-- 
    Document   : home
    Created on : May 16, 2014, 1:18:51 PM
    Author     : Sherman
--%>

<%@include file="../static/header.jsp" %> 
<%@include file="../static/navbar.jsp" %> 
<body>
    <div class="container">
        <div class="content ">
            <div class="tweet-input center">
                <input class="form-control span40" type="text" id="topic">
                <div class="margin10">
                    <button class="btn btn-success" id="find_tweets">Find Tweets</button>
                </div>
            </div>
            <div class="tweets-wrapper">

            </div>
        </div>
    </div>
</body>

<%@include file="../static/footer.jsp" %> 

<script>
    $('#find_tweets').click(function() {
        var keyword = $('#topic').val();
        $('.tweets-wrapper').html("");
        $.post("getTweetsByKeyword", {keyword: keyword}, function(tweets) {

            console.log(tweets.length);

            tweets.forEach(function(tweet) {

                var time = moment(tweet.created_at).zone("+8:00");

                var date = time._d;console.log(date);
                var dateSplit = String(date).split(" ");
                var date_created =  dateSplit[2] + " " + dateSplit[1] + " " + dateSplit[0] + " " +  dateSplit[3] + " " + dateSplit[4];
        var id = 'https://twitter.com/' + tweet.user.screen_name + "/statuses/" + tweet.id_str;
        var tweetURL = "";
                var tweetOriginURL = "";
                if (tweet.entities.urls[0]) {
                    tweetURL = '<a href="' + tweet.entities.urls[0].expanded_url + '">' + tweet.entities.urls[0].display_url + '</a> '
                }
                if (tweet.user.entities.url) {
                    tweetOriginURL = '<a href="' + tweet.user.entities.url.urls[0].expanded_url + ' target="_blank">';
                }

                var tc = "";
                tc += '<blockquote id="' + id + '" class="twitter-tweet" lang="en"><p>' + tweet.text + tweetURL;
                tc += '</p>&mdash; ' + tweetOriginURL + tweet.user.name + '' + '</a><span class="tweet-created">' + date_created + '</span></blockquote>';
                $(tc).appendTo($('.tweets-wrapper'));
            })

            enableTwitterLink();
        }, "json")
    });
    
    function enableTwitterLink(){
        $('blockquote').click(function(){
            console.log(this.id)
            window.open(this.id,'_blank')
        })
    }
</script>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8">
    < /html>