<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/common/common3.jsp" />
<meta charset="UTF-8">
<title>OpenGDS/Builder Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">

<link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Open+Sans'>

<link rel='stylesheet' href='${pageContext.request.contextPath}/resources/css/login/login.css'>
<script src='${pageContext.request.contextPath}/resources/js/login/login.js'></script>
</head>

<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="cont">
		<div class="demo">
			<div class="login">
				<div style="padding-top: 100px; text-align: center;">
					<img src="logo.png" class="logo" style="margin-top: 100px; margin-left: 20px"><img
						src="${pageContext.request.contextPath}/resources/img/ogds.png" style="width: 80%; height: auto;" class="logo">
				</div>

				<div class="login__form">
					<div class="login__row">
						<svg class="login__icon name svg-icon" viewBox="0 0 20 20">
            <path d="M0,20 a10,8 0 0,1 20,0z M10,0 a4,4 0 0,1 0,8 a4,4 0 0,1 0,-8" />
          </svg>
						<input type="text" id='id' class="login__input name" placeholder="Username" />
					</div>
					<div class="login__row">
						<svg class="login__icon pass svg-icon" viewBox="0 0 20 20">
            <path d="M0,20 20,20 20,8 0,8z M10,13 10,16z M4,8 a6,8 0 0,1 12,0" />
          </svg>
						<input type="password" id='pw' class="login__input pass" placeholder="Password" />
					</div>
					<button type="button" class="login__submit">Sign in</button>
					<p class="login__signup">
						Don't have an account? &nbsp;<a>Sign up</a>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
