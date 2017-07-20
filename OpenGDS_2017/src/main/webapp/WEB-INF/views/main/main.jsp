<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common3.jsp" />
  <title>GeoDT Online</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src='${pageContext.request.contextPath}/resources/js/login/login.js'></script>
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }

    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}

    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }

    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }

    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;}
    }

		h1,h3,h4{
			color: white;
		}

  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<div id="fcontent" class="container-fluid text-center" style="background:url(resources/img/back.jpg)" >

	<div style="position:relative; margin:0 auto; ">
		<div id='textinfo'>
			<p style="font-size:300%;color:white"> Web기반 공간정보 검수솔루션</p>	<br><br>
			<h4> GeoDT-Online은 전부 오픈소스로 개발되어 졌으며 <br> 정위치, 구조화검수를 지원합니다. </h3>
			<br><br>
			<p><a class="btn btn-warning btn-lg" href="/opengds/builder.do" role="button"  >체험하기</a></p>
		</div>
	</div>
</div>
<nav id='footer' class="navbar navbar-default navbar-fixed-bottom">
<div class="container-fluid text-center" style="background-color:gray; padding-top:20px"  >
	<h4> 검수 대상파일을 업로드하고, 검수옵션을 설정하면 검수가 가능합니다. DXF, NGI, SHP 포맷을 지원합니다. <br><br>로그인 후 GeoDT-Online을 경험해보세요.</h3>
	<br><br>
</div>
<footer class="container-fluid text-center">
  <img class= "img-rounded" src="${pageContext.request.contextPath}/resources/img/openlayers.png" height="40px"  style="padding-left:15px">
	<img class= "img-rounded" src="${pageContext.request.contextPath}/resources/img/geoserver.png" height="40px" style="padding-left:15px">
	<img class= "img-rounded" src="${pageContext.request.contextPath}/resources/img/geotools.png" height="40px" style="padding-left:15px">
	<img class= "img-rounded" src="${pageContext.request.contextPath}/resources/img/postgresql.png" height="40px" style="padding-left:15px">
</footer>
</nav>

<script>
	/* $( window ).resize(function() {
		var headerHeight = $('#mainHeader').height();
		var footerHeight = $('#footer').height();
		var fcontentHeight = (window.innerHeight-headerHeight-footerHeight);
		
		$('#fcontent').css('height',fcontentHeight+'px');
	}); */
	$(document).ready(function() { 
		var headerHeight = $('#mainHeader').height();
		var footerHeight = $('#footer').height();
		var fcontentHeight = (window.innerHeight-headerHeight-footerHeight);
		var marginTop = fcontentHeight/2/2;
		
		
		$('#fcontent').css('height',fcontentHeight+'px');
		$('#textinfo').css('margin-top',marginTop+'px');
		
	}); 
</script>


</body>
</html>
