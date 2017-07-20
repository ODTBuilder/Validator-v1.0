/**
 * URL 생성
 * @author seulgi.lee
 * @date 2016. 02.
 * @param url - ajax 요청주소
 * @return String
 */
function fixUrlPath(url) {
	if (url.indexOf(CONTEXT) < 0) {
		url = CONTEXT + url;
	}
	if (url.indexOf("?") >= 0) {
		url += "&t=" + (new Date()).getTime();
	} else {
		url += "?t=" + (new Date()).getTime();
	}
	return url;
}

function loadImageShow(){
	/**
	 * 웹페이지를 로딩할 때 초기화를 한다.
	 * 1. loadimage 초기화
	 * */
	$("#loadimage").css("left", $(window).width()/2-80).css("bottom", $(window).height()/2).css("display","block").css("z-index",999);
	$("#loadimage").show();
	$("#mask").show();
}

function loadImageHide(){
	$("#loadimage").hide();
	$("#mask").hide();
}

/**
 * 해당 path로 POST 요청한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param path - post 요청주소
 * @param params - 서버에 보내는 파라미터
 * @param target - post요청 target 유형
 */
function postToUrl(path, params, target) {
    var form = document.createElement("form");
//    form.setAttribute("method", "get");
    form.setAttribute("method", "post");
    form.setAttribute("action", path);
    for(var key in params) {
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", params[key]);
        form.appendChild(hiddenField);
    }
    form.target = target;
    document.body.appendChild(form);
    form.submit();
}

/**
 * 해당 path로 GET 요청한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param path - post 요청주소
 * @param params - 서버에 보내는 파라미터
 * @param target - get요청 target 유형
 */
function getToUrl(path, params, target) {
	var form = document.createElement("form");
	form.setAttribute("method", "get");
	form.setAttribute("action", path);
	for (var key in params) {
		var value = params[key];
		if (value instanceof Array) {
			for (var idx in value) {
				var hiddenField = document.createElement("input");
				hiddenField.setAttribute("type", "hidden");
				hiddenField.setAttribute("name", key);
				hiddenField.setAttribute("value", value[idx]);
				form.appendChild(hiddenField);
			}
		} else {
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", key);
			hiddenField.setAttribute("value", value);
			form.appendChild(hiddenField);
		}
	}
	form.target = target;
	document.body.appendChild(form);
	form.submit();
}

/**
 * 해당 URL로 GET형식 ajax 요청한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param url - ajax 요청주소
 * @param params - 서버에 보내는 파라미터
 * @param doneCallback - Return후 실행되는 함수
 * @returns JSONObject
 */
function sendJsonRequest(url, params, doneCallback){
	var deferredObj = 
		$.ajax({
		url : fixUrlPath(url),
//		type : "POST",
		type : "GET",
		contentType : "application/json; charset=UTF-8",
		dataType : "json",
		cache : false,
		data : params,
/*		beforeSend : function(){ //호출전실행
			loadImageShow();
		},
*/		traditional: true
	});
	deferredObj.done(function(data, textStatus, jqXHR) {
		processDone(data, textStatus, jqXHR, doneCallback);
	});
	deferredObj.fail(function(jqXHR, textStatus, errorThrown) {
		processFail(jqXHR, textStatus, errorThrown);
	});
	return deferredObj;
}

function sendZipcodeRequest(url, params, doneCallback) {
	var deferredObj = 
		$.ajax({
		url : url,
//		type : "POST",
		type : "GET",
		dataType : "json",
		cache : false,
		data : params,
		traditional: true
		
	});
	deferredObj.done(function(data, textStatus, jqXHR) {
		processDone(data, textStatus, jqXHR, doneCallback);
	});
	deferredObj.fail(function(jqXHR, textStatus, errorThrown) {
		processFail(jqXHR, textStatus, errorThrown);
	});
	return deferredObj;
}

/**
 * 해당 URL로 POST형식 ajax 요청한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param url - ajax 요청주소
 * @param params - 서버에 보내는 파라미터
 * @param doneCallback - Return후 실행되는 함수
 * @returns JSONObject
 */
function sendObjectRequest(url, params, doneCallback) {
	var deferredObj = 
		$.ajax({
		url : fixUrlPath(url),
		type : "POST",
//		type : "GET",
		dataType : "json",
		contentType : "application/json; charset=UTF-8",
		cache : false,
		data : JSON.stringify(params),
		/*beforeSend : function(){ //호출전실행
			loadImageShow();
		},*/
		traditional: true
	});
	deferredObj.done(function(data, textStatus, jqXHR) {
		processDone(data, textStatus, jqXHR, doneCallback);
	});
	deferredObj.fail(function(jqXHR, textStatus, errorThrown) {
		processFail(jqXHR, textStatus, errorThrown);
	});
	return deferredObj;
}


/**
 * ajax 성공시
 * @author seulgi.lee
 * @date 2016. 02.
 */
function processDone(data, textStatus, jqXHR, doneCallback) {
	if (typeof(data) !== 'undefined' && typeof(data.errorCode) !== 'undefined') {
		alertPopup("Inform",data.errorDesc);
	} else if (typeof(doneCallback) !== 'undefined') {
		doneCallback(data);
	}
}

/**
 * ajax 실패시
 * @author seulgi.lee
 * @date 2016. 02.
 */
function processFail(jqXHR, textStatus, errorThrown) {
	if (typeof (console) !== 'undefined' && typeof (console.log) !== 'undefined') {
		console.log(textStatus + " - " + jqXHR.status + " (" + errorThrown + ")");
	}
	if (jqXHR.status == 500) {
		alert("내부 시스템 에러.");
	} else if (jqXHR.status == 404) {
		alert("경로가 잘못 되었습니다.");
	} else if (jqXHR.status == 408) {
		alert("잠시 후 다시 시도해 주세요.");
	}
	if (jqXHR.getResponseHeader("SESSION_EXPIRED") != null) {
		alert("세션이 만료되어 메인페이지로 이동합니다.");
		window.location.href = "/main.do";
	}
}

/*
*//**
 * layer popup open 처리
 *//*
$.fn.xShowPopup = function() {
	$(this).each(function() {
		$("#mask").show();
		var tempm = $(this).height()/2 ;
		$(this).css('margin-top', '-'+tempm+'px');
		$(this).show();
	});
};
*//**
 * layer popup close 처리
 *//*
$.fn.xHidePopup = function(e) {
	if (typeof(e) !== "undefined"){
		e.preventDefault();
	}
	$(this).each(function() {
		$(this).hide();
	});
};*/

function NoDoubleClick(func){
	var _self = this;
	_self._callfunc = func;
	_self.called = false;
	
	_self.runFunction = function(e){
		if(_self.called){
			if (typeof(e) !== "undefined"){
				e.preventDefault();
			}
			return;
		}
		_self.called = true;
		_self._callfunc(e);
		setTimeout(function(){_self.called = false;}, 500);
	};
	
};

$.fn.noDupClick = function(func){
	$.each($(this), function(index, o){
		var _noDuble = new NoDoubleClick(func);
		$(o).click(_noDuble.runFunction);
	});
};

/**
 * 알림 팝업창을 띄운다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param type - 알림타입(success or warning)
 * @param title - 알림주제
 * @param sub - 알림내용
 * @returns 
 */
function alertPopup(type,title,sub){
	var btnType;
	if(type==='success'){
		btnType='btn btn-success';
	}
	else if(type == 'warning'){
		btnType='btn btn-warning'
	}
	else if(type == 'error'){
		btnType='btn btn-error'
	} 
	else if(type == 'info'){
		btnType='btn btn-info'
	} 
	else if(type == 'question'){
		btnType='btn btn-question'
	} 
	
    swal({
	  title: title,
	  text: sub,
	  type: type,
	  confirmButtonClass: btnType,
	  confirmButtonText: "OK",
	  closeOnConfirm: true
	});
}


/**
 * 확인버튼이 포함된 알림
 * @param 	content  
 *         {
		    title : '로그아웃 하시겠습니까?',
		    text : 'This work will disappear when you move the page.',
		    type : 'warning',
		    showCancelButton : true,
		    confirmButtonColor : '#3085d6',
		    confirmButtonText : 'Yes',
		    confirmButtonClass : 'btn btn-success',
		    buttonsStyling : false
		  };
 * @param confirmFunc - OK버튼 눌렀을시 실행
		 *  function(isConfirm){
				    if (isConfirm) {
					 var path = CONTEXT+"/user/logout.do";
					    var params = {};
					    var target = "_self";
					    getToUrl(path, params, target);
				  }
			}
 * @returns
 */
function alertConfirmPopup(content,confirmFunc) {
    swal(content,confirmFunc);
}


function showModal(target){
    $('#'+target+'').modal('show');
}

function closeModal(target){
    $('#'+target+'').modal('hide');
}

/**
 * showProgress 처리
 */
$.fn.showProgress = function() { 
	$(this).each(function() {
		$("#mask").show();
		$(this).show();
	});
};

/**
 *  hideProgress 처리
 */
$.fn.hideProgress = function(e) {
	if (typeof(e) !== "undefined"){
		e.preventDefault();
	}
	$(this).each(function() {
		$("#mask").hide();
		$(this).hide();
	});
};


/**
 * 천단위를 구분한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param n - 천단위 구분할 문자열
 * @returns Boolean
 */
function commify(n) {
	var reg = /(^[+-]?\d+)(\d{3})/; // 정규식
	n += ''; // 숫자를 문자열로 변환

	while (reg.test(n))
		n = n.replace(reg, '$1' + ',' + '$2');

	return n;
}

/**
 * 숫자를 구분한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param s - 비교할 문자열
 * @returns Boolean
 */
function isNumber(s) {
    s += ''; // 문자열로 변환
    s = s.replace(/^\s*|\s*$/g, ''); // 좌우 공백 제거
    if (s == '' || isNaN(s)) return false;
    return true;
}



/**
 * 공백체크를 한다.
 * @author seulgi.lee
 * @date 2016. 02.
 * @param validCase - 공백 체크할 파라미터
 * @returns Boolean
 */
function validation(validCase) {
	var errorCnt = 0;
	$.each(validCase, function(key, val) {
		if(val.required) {
			
			var str = "";
			
			if(val.type == "text") {
				str = $.trim( $('#'+key).val() );
			} else if( val.type == "radio"){
				str = $('input[name='+key+']:checked').val();
			}
			if(!str) {
            			    if(val.type == "text") {
            				alertPopup("warning",val.name+" 입력해주세요.");
            			} else if( val.type == "radio"){
            				alertPopup("warning",val.name+" 입력해주세요.");
            			}
				errorCnt++;
				return false;
			}
		}
	});
	
	if (errorCnt == 0) {
		return true;
	} else {
		return false;
	}
}


