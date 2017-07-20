<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav id="mainHeader" class="navbar" style="margin-bottom: 0; border-radius: 0;">
	<div class="container-fluid">
		<div class="navbar-default">
			<a class="navbar-brand" href="/opengds/main.do"> <img src="${pageContext.request.contextPath}/resources/img/logo379.png"
				style="height: 22px; width: auto;">
			</a>
		</div>

		<div class="collapse navbar-collapse " id="myNavbar">
			<ul class="nav navbar-nav ">
				<li class="active"><a href="/opengds/main.do">Home</a></li>
				<li><a href="/opengds/builder.do">Map</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>	
					<c:when test="${user eq null}">
					 	<li><a href="/opengds/user/loginView.do"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
					</c:when>
					<c:when test="${user ne null}">
				 		<li><a href="javascript:logoutPopup()"><span class="glyphicon glyphicon-log-out"></span> Login-out</a></li>
				</c:when>
				</c:choose>
				<li><a href="http://www.git.co.kr/main/main.html" target="_blank"><img src="${pageContext.request.contextPath}/resources/img/geo.png" height="20px"></a></li>
			</ul>
		</div>
	</div>
</nav>