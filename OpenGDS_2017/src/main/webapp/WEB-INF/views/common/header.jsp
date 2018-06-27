<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav id="mainHeader" class="navbar" style="margin-bottom: 0; border-radius: 0;">
	<div class="container-fluid">
		<div class="navbar-default">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/main.do"> <img
				src="${pageContext.request.contextPath}/resources/img/builder.png" style="height: 22px; width: auto;">
			</a>
		</div>

		<div class="collapse navbar-collapse " id="myNavbar">
			<ul class="nav navbar-nav ">
				<li class="active"><a href="${pageContext.request.contextPath}/main.do">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/builder.do">Map</a></li>
				<li><a href="#">Contact</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${user ne null}">
						<li><a href="#"><i class="fa fa-user-circle-o"></i>&nbsp;${user.id}</a></li>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${user eq null}">
						<li><a href="${pageContext.request.contextPath}/user/loginView.do"><span
								class="glyphicon glyphicon-log-in"></span> Login</a></li>
					</c:when>
					<c:when test="${user ne null}">
						<li><a href="javascript:logoutPopup()"><span class="glyphicon glyphicon-log-out"></span> Login-out</a></li>
					</c:when>
				</c:choose>
				<li><a href="http://www.git.co.kr/main/main.html" target="_blank"><img
						src="${pageContext.request.contextPath}/resources/img/geo.png" height="20px"></a></li>
			</ul>
		</div>
	</div>
</nav>