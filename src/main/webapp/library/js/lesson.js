/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var autorefresh = setInterval(loadNew, 5500);
$(document).ready(function() {
    search();


})
function search() {
    $('#find_tweets').click(function() {
        $('#overlay').show();
        initSearch();

    })
    $('#search').keydown(function(e) {
        if (e.keyCode == 13) {
            $('#overlay').show();
            initSearch();

        }
    })
}
function initSearch() {

    var keyword = $('input#search').val();

    $('<div class="tweets-wrapper"><div class="center tweet_header"><span class="searchword">' + keyword + '</span>\n\
<span class="tweetwrapper-icons"><span class="glyphicon player glyphicon-pause pause"></span>\n\
<span class="glyphicon glyphicon-remove delete"></span></span></div><div class="loadNewHolder"><span class="newLoad"></span> new tweets</div><div class="tweet_content"></div></div>').appendTo($(".lesson_holder"));
    $this = $('.tweets-wrapper').last().find($('.tweet_content'));
    $.post("getTweets", {keyword: keyword}, function(batch) {
        var tweets = batch.tweets;
        createTweetDisplay(tweets, $this, "append", "show");
        $this.css('height', $(window).height());
        console.log(batch.latest);
        $this.attr("data-latest", batch.latest);
        $this.attr("data-oldest", batch.oldest);
        update();
        enableTwitterLink();
        initWrapperActions();
        showAll();
        $('#overlay').hide();
        updateInterval();

    }, "json")

}
function updateInterval() {
    var time = Number(5500) * (Number($('.tweet_content').length) - Number($('.paused').length))
    clearInterval(autorefresh);
    autorefresh = setInterval(loadNew, time)
    console.log(autorefresh)
}
function showAll() {
    $('.loadNewHolder').click(function() {
        //console.log($(this).closest('.tweet-content').find(($('blockquotes'))));

        $(this).siblings($('.tweet-content')).find('blockquote').removeClass('hide');

        $(this).siblings($('.tweet-content')).animate({scrollTop: 0}, 1000)
        $(this).fadeOut()
        //$(this).next('.tweet-content').find('blockquotes').removeClass('hide');
    })
}
function initWrapperActions() {
    $(".delete , .player").unbind('click');
    $('.delete').click(function() {
        $(this).closest(".tweets-wrapper").remove();
        updateInterval();
    })
    $('.player').click(function() {
        if ($(this).hasClass('glyphicon-play play')) {
            //handle play
            $(this).addClass('glyphicon-pause pause').removeClass('glyphicon-play play');
            console.log("play")
            console.log($(this).closest('.tweet_header').siblings('.tweet_content'));
            $(this).closest('.tweet_header').siblings('.tweet_content').removeClass('paused');
            updateInterval();
        } else {
            //handle pause
            $(this).removeClass('glyphicon-pause pause').addClass('glyphicon-play play');
            console.log("pause")

            console.log($(this).closest('.tweet_header').siblings('.tweet_content'));
            $(this).closest('.tweet_header').siblings('.tweet_content').addClass('paused');
            updateInterval();

        }
    })



}
function enableTwitterLink() {
    $('blockquote').click(function() {
        window.open(this.id, '_blank')
    })
}
function createTweetDisplay(tweets, wrapper, insertionPt, display) {
    var count = 1;
    var tc = "";
    tweets.forEach(function(tweet) {

        var time = moment(tweet.created_at).zone("+8:00");

        var date = time._d;

        var dateSplit = String(date).split(" ");
        var date_created = dateSplit[2] + " " + dateSplit[1] + " " + dateSplit[0] + " " + dateSplit[3] + " " + dateSplit[4];
        var id = 'https://twitter.com/' + tweet.user.screen_name + "/statuses/" + tweet.id_str;
        var tweetURL = "";
        var tweetOriginURL = "";
        if (tweet.entities.urls[0]) {
            tweetURL = '<a href="' + tweet.entities.urls[0].expanded_url + '">' + tweet.entities.urls[0].display_url + '</a> '
        }
        if (tweet.user.entities.url) {
            tweetOriginURL = '<a href="' + tweet.user.entities.url.urls[0].expanded_url + ' target="_blank">';
        }
        var tweetwords = tweet.text.split(" ");
        var finalTweet = "";
        tweetwords.forEach(function(word) {
            if (word.charAt(0) == '#') {
                word = '<span class="green bold">' + word + "</span>";
            }
            finalTweet += word + " ";
        })
        if (tweet.entities.urls.length > 0) {
            //console.log(tweet.entities.urls[0].url + " -> " + "<a href='" + tweet.entities.urls[0].url + "' target='_blank'>" + tweet.entities.urls[0].display_url + '</a>' )
            finalTweet.replace(tweet.entities.urls[0].url, "<a href='" + tweet.entities.urls[0].url + "' target='_blank'>" + tweet.entities.urls[0].display_url + '</a>')
        }
        if (display == "show") {
            tc += '<blockquote id="' + id + '" class="twitter-tweet" lang="en">';
        } else {
            tc += '<blockquote id="' + id + '" class="twitter-tweet hide" lang="en">';
        }
        tc += '<div class="tweetuser_details">';
        tc += '<img src="' + tweet.user.profile_image_url_https + '" class="profilepic"/>'

        tc += '<div class="tweetuser_username">' + tweet.user.screen_name + "</div>"
        tc += '</div>';
        tc += '<p>' + finalTweet;
        tc += '</p>&mdash; ' + tweetOriginURL + tweet.user.name + '' + '</a><span class="tweet-created">' + date_created + '</span></blockquote>';
        count++;

    })
    if (insertionPt == "append") {
        $(tc).appendTo(wrapper);
    } else if (insertionPt == "prepend") {
        $(tc).prependTo(wrapper);
    }
}
function update() {

    $('.tweet_content').scroll(function() {
        var keyword = $(this).siblings('.tweet_header').find($('.searchword')).text();
        var oldest = $(this).find('blockquote').last().attr("id").split("/");
        if ($(this).scrollTop() + $(this).innerHeight() >= (0.9 * $(this)[0].scrollHeight)) {
            //console.log("previous:" + $(this).data("oldest"));
            $.get("getTweets", {keyword: keyword, load: oldest[oldest.length - 1]}, function(batch) {
                $(this).attr("data-oldest", batch.oldest)
                var tweets = batch.tweets;
                //  console.log("updated : " + batch.oldest)
                createTweetDisplay(tweets, $this, "append", "show");

            }, "json")
        }
    })
}

function loadNew() {

    $('.tweets-wrapper').each(function(index) {
        //      console.log("index: " + index
        var $this = $(this).find($('.tweet_content'));
        if (!$this.hasClass('paused')) {

            var keyword = $(this).find('.searchword').text();
            // console.log(keyword)
            var latest = null;
            if ($this.find('blockquote').length > 0) {
                latest = $this.find('blockquote').first().attr("id").split("/");
                $.get("getTweets", {keyword: keyword, update: latest[latest.length - 1]}, function(batch) {
                    $this.attr("data-latest", batch.latest)
                    // console.log(keyword + " : " + batch.tweets.length);
                    if (batch.tweets.length >= 0 && $this.scrollTop() == 0) {
                        //$this.siblings('.loadNewHolder').text(batch.tweets.length + " new tweets")
                        //  $this.siblings('.loadNewHolder').show();
                        createTweetDisplay(batch.tweets, $this, "prepend", "show");
                        //   $this.siblings('.loadNewHolder').delay(2000).hide();
                    } else {

                        var existingNew = $this.siblings('.loadNewHolder').find('.newLoad').text();
                        if (existingNew == '') {
                            $this.siblings('.loadNewHolder').find('.newLoad').text(batch.tweets.length);
                        } else {

                            $this.siblings('.loadNewHolder').find('.newLoad').text(Number(existingNew) + Number(batch.tweets.length));
                        }
                        $this.siblings('.loadNewHolder').show();
                        createTweetDisplay(batch.tweets, $this, "prepend", "hide");
                    }

                }, "json")
            }
        }


    })

}
