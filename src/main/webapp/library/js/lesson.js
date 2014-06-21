/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    $('<div class="tweets-wrapper"><div class="center"><span class="searchword">' + keyword + '</span>\n\
<span class="tweetwrapper-icons"><i class="fa fa-cogs"></i>\n\
<i class="fa fa-times delete"></i></span></div></div>').appendTo($(".lesson_holder"));
    $.post("getTweets", {keyword: keyword}, function(tweets) {

        console.log("tweets length : " + tweets.length)
        var count = 1;
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
                finalTweet += word;
            })
            if (tweet.entities.urls.length > 0) {
                //console.log(tweet.entities.urls[0].url + " -> " + "<a href='" + tweet.entities.urls[0].url + "' target='_blank'>" + tweet.entities.urls[0].display_url + '</a>' )
                console.log(finalTweet)
                finalTweet.replace(tweet.entities.urls[0].url, "<a href='" + tweet.entities.urls[0].url + "' target='_blank'>" + tweet.entities.urls[0].display_url + '</a>')
                console.log(finalTweet)
            }
            var tc = "";
            tc += '<blockquote id="' + id + '" class="twitter-tweet" lang="en">';
            tc += '<div class="tweetuser_details">';
            tc += '<img src="' + tweet.user.profile_image_url_https + '" class="profilepic"/>'

            tc += '<div class="tweetuser_username">' + tweet.user.screen_name + "</div>"
            tc += '</div>';
            tc += '<p>' + finalTweet;
            tc += '</p>&mdash; ' + tweetOriginURL + tweet.user.name + '' + '</a><span class="tweet-created">' + date_created + '</span></blockquote>';
            count++;
            $(tc).appendTo($('.tweets-wrapper').last());
        })
        $('.tweets-wrapper').css('height', $(window).height());
        enableTwitterLink();
        initWrapperActions();
        $('#overlay').hide();
    }, "json")

}

function initWrapperActions() {
    $('.delete').click(function() {
        $(this).closest(".tweets-wrapper").remove();
    })
}
function enableTwitterLink() {
    $('blockquote').click(function() {
        console.log(this.id)
        window.open(this.id, '_blank')
    })
}