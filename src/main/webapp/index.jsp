<%@include file="../static/header.jsp"%>
    <!-- Fonts -->
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href='//fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='//fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
    
    <!-- Custom Theme CSS -->
    <link href="library/css/landing/custom.css" rel="stylesheet">
    
    <!-- Flex slider CSS -->
    <link rel="stylesheet" href="library/css/landing/flexslider.css">
    
    <body id="page-top" data-spy="scroll" data-target=".navbar-custom">
    
        <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header page-scroll">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                        <i class="fa fa-bars"></i>
                    </button>
                    <a class="navbar-brand" href="#page-top">
                        <img src="images/tweetboard.png"  alt="">
                    </a>
                </div>
    
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
                    <ul class="nav navbar-nav">
                        <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                        <li class="hidden">
                            <a href="#page-top"></a>
                        </li>
                        <li class="page-scroll">
                            <a href="#about">About</a>
                        </li>
                        <li class="page-scroll">
                            <a href="#download">Download</a>
                        </li>
                        <li class="page-scroll">
                            <a href="#contact">Contact</a>
                        </li>
                        <li>
                            <a href="sign_in?type=twitter">
                                <i class="top-signin-btn"></i>
                            </a>
                        </li>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container -->
        </nav>
    
        <section class="intro">
            <div class="intro-body">
                <div class="container">
                    <div class="row">
                        <div class="col-md-8 col-md-offset-2">
                            <h1 class="brand-heading">TweetBoard</h1>
                            <p class="intro-text">A free, premium quality, responsive one page Bootstrap theme created by Start Bootstrap.</p>
                            <div class="page-scroll">
                                <a href="sign_in?type=twitter" class="btn">
                                    <i class="signin-btn"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    
        <section id="about" class="container content-section text-center">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">
                    <h2>About TweetBoard</h2>
                    <p>TweetBoard is a web-based application that aims to create a vibrant and highly interactive learning environment for students and instructors through the use of Twitter. Instructors would choose the top retweets to discuss in class.</p>
                    <p>It has been identified that there is a need for allowing students in <a href="http://www.smu.edu.sg/">Singapore Management University</a> to participate in class discussions by posting tweets, nominating the interesting ones and voting for them as discussion points to facilitate and stimulate in-class conversations and debates. At the same time, this is a research topic for both professors to look into higher learning through social media platform.</p>
                    <p>Our primary target is teaching staffs that require a platform that allows flexibility in discussions.</p>
                </div>
            </div>
        </section>
    
        <section id="download" class="content-section text-center">
            <div class="download-section">
                <div class="container">
                    <div class="col-lg-8 col-lg-offset-2">
                        <h2>Download Grayscale</h2>
                        <iframe class="col-lg-12 col-lg-offset-0 col-md-8 col-md-offset-2" src="http://www.youtube.com/embed/NyI5GPbCNfY" height="315" frameborder="0" allowfullscreen></iframe>
                        <p/>
                        <a href="http://startbootstrap.com/grayscale" class="btn btn-default btn-lg">Visit Download Page</a>
                    </div>
                </div>
            </div>
        </section>
    
        <section id="contact" class="container content-section text-center">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">
                    <h2>Contact Start Bootstrap</h2>
                    <p>Feel free to email us to provide some feedback on our templates, give us suggestions for new templates and themes, or to just say hello!</p>
                    <p>feedback@startbootstrap.com</p>
                    <ul class="list-inline banner-social-buttons">
                        <li><a href="https://twitter.com/SBootstrap" class="btn btn-default btn-lg"><i class="fa fa-twitter fa-fw"></i> <span class="network-name">Twitter</span></a>
                        </li>
                        <li><a href="https://github.com/IronSummitMedia/startbootstrap" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                        </li>
                        <li><a href="https://plus.google.com/+Startbootstrap/posts" class="btn btn-default btn-lg"><i class="fa fa-google-plus fa-fw"></i> <span class="network-name">Google+</span></a>
                        </li>
                    </ul>
                </div>
            </div>
        </section>
    
        <div id="map"></div>
    
        <%@include file="../static/footer.jsp"%>
    
        <!-- JQuery Scrolling Files -->
        <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
        <script src="library/js/landing/jquery.nicescroll.js" type="text/javascript"></script>
     
        <!-- Custom Theme JavaScript -->
        <script type="text/javascript" src="library/js/landing/custom.js"></script>
    
        <!-- Google Maps API Key - You will need to use your own API key to use the map feature -->
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRngKslUGJTlibkQ3FkfTxj3Xss1UlZDA&sensor=false"></script>
    
        <!-- JQuery Flex slider -->
        <script src="library/js/landing/jquery.flexslider.js" type="text/javascript"></script>
    
    </body>

</html>
