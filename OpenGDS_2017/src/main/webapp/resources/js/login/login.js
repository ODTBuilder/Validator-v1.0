$(document).ready(function() {
	$('.name').keyup(function(e) {
		if (e.keyCode == 9) {
			$(this).next().focus();
		}
	});
	$('.login__input').keyup(function(e) {
		if (e.keyCode == 13) {
			var validCase = {
				id : {
					type : "text",
					name : "아이디를",
					required : true
				},
				pw : {
					type : "text",
					name : "패스워드를",
					required : true
				},
			}

			if (validation(validCase)) {
				var id = $('#id').val();
				var pw = $('#pw').val();
				var params = {
					id : id,
					pw : pw,
				};

				var url = CONTEXT + "/user/login.ajax";
				sendObjectRequest(url, params, function(result) {
					var user = result.user;
					if (user) {
						var path = CONTEXT + "/main.do";
						var params = {};
						var target = "_self";
						getToUrl(path, params, target);
					} else {
						alertPopup('warning', '로그인 실패', '아이디 또는 비밀번호를 다시 확인하세요.')
					}
				});
			}
		}
	});

	$(document).on("click", ".login__submit", function(e) {
		/*
		 * if (animating) return; animating = true; var that = this;
		 * ripple($(that), e); $(that).addClass("processing");
		 * setTimeout(function() { $(that).addClass("success");
		 * setTimeout(function() { $app.show(); $app.css("top");
		 * $app.addClass("active"); }, submitPhase2 - 70); setTimeout(function() {
		 * $login.hide(); $login.addClass("inactive"); animating = false;
		 * $(that).removeClass("success processing"); }, submitPhase2); },
		 * submitPhase1);
		 */

		var validCase = {
			id : {
				type : "text",
				name : "아이디를",
				required : true
			},
			pw : {
				type : "text",
				name : "패스워드를",
				required : true
			},
		}

		if (validation(validCase)) {
			var id = $('#id').val();
			var pw = $('#pw').val();
			var params = {
				id : id,
				pw : pw,
			};

			var url = CONTEXT + "/user/login.ajax";
			sendObjectRequest(url, params, function(result) {
				var user = result.user;
				if (user) {
					var path = CONTEXT + "/main.do";
					var params = {};
					var target = "_self";
					getToUrl(path, params, target);
				} else {
					alertPopup('warning', '로그인 실패', '아이디 또는 비밀번호를 다시 확인하세요.')
				}
			});
		}
	});
});

function logoutPopup() {
	swal({
		title : '로그아웃',
		text : '정말 로그아웃을 하시겠습니까?',
		type : 'warning',
		showCancelButton : true,
		confirmButtonColor : '#3085d6',
		cancelButtonColor : '#d33',
		confirmButtonText : '확인',
		cancelButtonText : '취소',
		confirmButtonClass : 'btn btn-success',
		cancelButtonClass : 'btn btn-danger',
		buttonsStyling : false
	}, function(isConfirm) {
		if (isConfirm) {
			var path = CONTEXT + "/user/logout.do";
			var params = {};
			var target = "_self";
			getToUrl(path, params, target);
		}
	});
}