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
    $('#search').focus(function() {
        $('#find_tweets').css('color', '#444')
    })
    $('#search').focusout(function() {
        $('#find_tweets').css('color', '#D8D8D8')
    })
}
function initSearch() {

    var keyword = $('input#search').val();

    $('<div class="tweets-wrapper"><div class="center tweet_header"><span class="searchword">' + keyword + '</span>\n\
<span class="tweetwrapper-icons"><span class="glyphicon player glyphicon-pause pause"></span>\n\
<span class="glyphicon glyphicon-remove delete"></span></span></div><div class="loadNewHolder"><span class="newLoad"></span> new tweets</div><div class="tweet_content"></div></div>').appendTo($(".lesson_holder"));
    $this = $('.tweets-wrapper').last().find($('.tweet_content'));
    $.get("getTweets", {keyword: keyword}, function(batch) {
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
}
function showAll() {
    $('.loadNewHolder').click(function() {
        //console.log($(this).closest('.tweet-content').find(($('blockquotes'))));
        $(this).siblings($('.tweet-content')).find('blockquote').removeClass('hide');
        $(this).siblings($('.tweet-content')).animate({scrollTop: 0}, 1000)
        $(this).fadeOut()
    })
}
function initWrapperActions() {
    $(".delete , .player,.topic").unbind('click');
    $('.delete').click(function() {
        $(this).closest(".tweets-wrapper").remove();
        updateInterval();
    })
    $('.player').click(function() {
        if ($(this).hasClass('glyphicon-play play')) {
            //handle play
            $(this).addClass('glyphicon-pause pause').removeClass('glyphicon-play play');
            $(this).closest('.tweet_header').siblings('.tweet_content').removeClass('paused');
            updateInterval();
        } else {
            //handle pause
            $(this).removeClass('glyphicon-pause pause').addClass('glyphicon-play play');
            $(this).closest('.tweet_header').siblings('.tweet_content').addClass('paused');
            updateInterval();

        }
    })
    $('.tweets-wrapper').click(function(){
        $('#topic').fadeIn(500);
        var word = $(this).find($('.searchword')).text();
        loadTopic(word);
    })



}

function loadTopic(word){
       $.get("getTweets", {keyword: keyword}, function(batch) {
           
       },"json");
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

/**
 * gridSelector.js v1.0.0
 * http://www.codrops.com
 *
 * Licensed under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 * 
 * Copyright 2013, Codrops
 * http://www.codrops.com
 */
;
(function(window) {

    'use strict';

    function extend(a, b) {
        for (var key in b) {
            if (b.hasOwnProperty(key)) {
                a[key] = b[key];
            }
        }
        return a;
    }

    function gridSelector(el, options) {
        this.el = el;
        this.options = extend(this.defaults, options);
        this._init();
    }

    gridSelector.prototype = {
        defaults: {
            rows: 5,
            columns: 5,
            maxcolumns: 5,
            onClick: function() {
                return false;
            }
        },
        _init: function() {
            this.trigger = this.el.querySelector('span.gt-grid-icon');
            this.gridItems = Array.prototype.slice.call(this.el.querySelectorAll('div.gt-grid-select > div'));
            this._setRowsColumns(this.options.rows, this.options.columns);
            this.maxcolumns = this.options.maxcolumns;
            this.gridItems.forEach(function(el, i) {
                classie.add(el, 'gt-grid-selected');
            });
            this._initEvents();
        },
        _initEvents: function() {

            var self = this;

            if (Modernizr.touch) {
                this.trigger.addEventListener('click', function(ev) {
                    classie.add(self.el, 'gt-grid-control-open');
                });
            }

            this.gridItems.forEach(function(el, i) {
                el.addEventListener('mouseover', function(ev) {
                    self._highlightGridItems(self.gridItems.indexOf(this));
                });

                el.addEventListener('click', function(ev) {
                    self._selectGridItems(self.gridItems.indexOf(this));
                    if (Modernizr.touch) {
                        classie.remove(self.el, 'gt-grid-control-open');
                    }
                });
            });

        },
        _highlightGridItems: function(pos) {

            // item's row and column
            var self = this,
                    itemRow = Math.floor(pos / this.options.maxcolumns),
                    itemColumn = pos - itemRow * this.options.maxcolumns;

            this.gridItems.forEach(function(el, i) {
                // el's row and column
                var elpos = self.gridItems.indexOf(el),
                        elRow = Math.floor(elpos / self.options.maxcolumns),
                        elColumn = elpos - elRow * self.options.maxcolumns;

                if (elRow <= itemRow && elColumn <= itemColumn) {
                    classie.add(el, 'gt-grid-hover');
                }
                else {
                    classie.remove(el, 'gt-grid-hover');
                }
            });

        },
        _selectGridItems: function(pos) {
            var self = this;
            this.gridItems.forEach(function(el, i) {
                if (classie.has(el, 'gt-grid-hover')) {
                    classie.add(el, 'gt-grid-selected');
                }
                else {
                    classie.remove(el, 'gt-grid-selected');
                }
            });
            var r = this.rows = Math.floor(pos / this.options.maxcolumns),
                    c = this.columns = pos - this.rows * this.options.maxcolumns;

            this._setRowsColumns(r + 1, c + 1);
            this.options.onClick();
        },
        _setRowsColumns: function(rows, columns) {
            this.rows = rows;
            this.columns = columns;
        }
    }

    // add to global namespace
    window.gridSelector = gridSelector;

})(window);
var Grid = (function() {

    function extend(a, b) {
        for (var key in b) {
            if (b.hasOwnProperty(key)) {
                a[key] = b[key];
            }
        }
        return a;
    }

    var config = {
        items: Array.prototype.slice.call(document.querySelectorAll('#gt-grid > div')),
        gridSel: new gridSelector(document.getElementById('gt-grid-selector'), {
            onClick: function() {
                initGrid();
            }
        })
    },
    defaults = {
        transition: false,
        speed: 300,
        delay: 0
    };

    function init(options) {
        config.options = extend(defaults, options);
        initGrid();
        if (config.options.transition) {
            setTimeout(function() {
                config.items.forEach(function(el, i) {
                    el.style.WebkitTransition = 'all ' + config.options.speed + 'ms ' + (i * config.options.delay) + 'ms';
                    el.style.MozTransition = 'all ' + config.options.speed + 'ms ' + (i * config.options.delay) + 'ms';
                    el.style.transition = 'all ' + config.options.speed + 'ms ' + (i * config.options.delay) + 'ms';
                });
            }, 25);
        }
    }

    function initGrid() {
        var rows = config.gridSel.rows,
                columns = config.gridSel.columns;

        config.items.forEach(function(el, i) {
            el.style.position = 'absolute';

            var elpos = config.items.indexOf(el),
                    current_row = Math.floor(elpos / config.gridSel.maxcolumns),
                    current_column = elpos - current_row * config.gridSel.maxcolumns,
                    width = 100 / columns,
                    height = 100 / rows;

            if (current_row < rows && current_column < columns) {
                //if( Modernizr.csscalc ) {
                //	el.style.width = '-webkit-calc(' + width + '% + 1px)';
                //	el.style.height = '-webkit-calc(' + height + '% + 1px)';
                //	el.style.width = '-moz-calc(' + width + '% + 1px)';
                //	el.style.height = '-moz-calc(' + height + '% + 1px)';
                //	el.style.width = 'calc(' + width + '% + 1px)';
                //	el.style.height = 'calc(' + height + '% + 1px)';
                //}
                //else  {
              // el.style.width = width + .5 + '%';
                //el.style.height = height + .5 + '%';
                 el.style.width = width + '%';
                el.style.height = height + '%';
              
                //}

                el.style.left = width * (current_column) + '%';
                el.style.top = height * (current_row) + '%';

                classie.remove(el, 'gt-hidden');
                classie.add(el, 'gt-visible');
            }
            else {
                classie.remove(el, 'gt-visible');
                classie.add(el, 'gt-hidden');
            }
        });
    }

    return {init: init};

})();

