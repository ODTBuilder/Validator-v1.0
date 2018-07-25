<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<%-- 제이쿼리 --%>
<script src="${ctx}/resources/js/jquery/jquery-2.2.2.min.js"></script>

<%-- 공통함수 --%>
<script src="${ctx}/resources/js/common/common.js"></script>

<%-- 업데이트중 - SG.Lee --%>
<script src="${ctx}/resources/js/gitbuilder/fileUpload.js"></script>

<%-- 제이쿼리UI --%>
<script src="${ctx}/resources/js/jqueryui/jquery-ui.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/js/jqueryui/jquery-ui.min.css">

<%-- 스펙트럼 --%>
<script src="${ctx}/resources/js/spectrum/spectrum.js"></script>
<link rel="stylesheet" href="${ctx}/resources/js/spectrum/spectrum.css">

<%-- 오픈 레이어즈3 --%>
<script src="${ctx}/resources/js/ol3/ol-debug.js"></script>
<link rel="stylesheet" href="${ctx}/resources/js/ol3/ol.css">

<%-- PROS4JS --%>
<script src="${ctx}/resources/js/proj4js/dist/proj4-src.js"></script>

<%-- JSTS --%>
<script src="${ctx}/resources/js/jsts/jsts.min.js"></script>

<%-- 부트스트랩 --%>
<script src="${ctx}/resources/js/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/js/bootstrap/css/bootstrap.min.css">

<%-- 데이터 테이블 --%>
<script type="text/javascript"
	src="${ctx}/resources/js/datatables/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/js/datatables/css/jquery.dataTables.min.css" />

<%-- 폰트어썸 --%>
<link rel="stylesheet"
	href="${ctx}/resources/css/fontawesome/css/font-awesome.min.css" />

<%-- jsTree openlayers3--%>
<script type="text/javascript"
	src="${ctx}/resources/js/jsTree-openlayers3/jstree.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/js/jsTree-openlayers3/themes/default/style.css" />
<script type="text/javascript"
	src="${ctx}/resources/js/jsTree-openlayers3/jstree-visibility.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jsTree-openlayers3/jstree-layerproperties.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jsTree-openlayers3/jstree-legends.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jsTree-openlayers3/jstree-functionmarker.js"></script>

<%-- jsTree geoserver--%>
<script type="text/javascript"
	src="${ctx}/resources/js/jsTree-geoserver/jstree.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/js/jsTree-geoserver/themes/default/style.css" />
<script type="text/javascript"
	src="${ctx}/resources/js/jsTree-geoserver/jstree-geoserver.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jsTree-geoserver/jstree-functionmarker.js"></script>

<%-- 베이스맵 변경 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/changebase.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/js/gitbuilder/gitbuilder2017.css">

<%-- 2.0 레이어 코드 정의 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerdefinition20_page.js"></script>

<%-- 1.0 레이어 코드 정의 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerdefinition10.js"></script>

<%-- 가중치 정의 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerweight.js"></script>

<%-- 검수창 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/validation.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/js/gitbuilder/gitbuilder2017.css">

<%-- 검수 편집 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/qaedit.js"></script>

<%-- 검수 현황 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/qastatus.js"></script>

<%-- 일반화 현황 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/genstatus.js"></script>

<%-- wms편집이력 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/edithistory.js"></script>

<%-- 레이어 편집이력 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerhistory.js"></script>

<%-- 편집이력 전송 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gb/edit/recordtransfer.js"></script>

<%-- 레이어 정보 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/modifylayer.js"></script>

<%-- 도엽 정보 객체--%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/mapsheetinfo.js"></script>

<%-- 레이어 정보 객체--%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/layerinfo.js"></script>

<%-- 애트리뷰트 정보 객체 --%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/attributeinfo.js"></script>

<%-- 클라이언트 레이어 생성--%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/createvectorlayer.js"></script>

<%-- 클라이언트 레이어 수정--%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/modifylayerprop.js"></script>

<%-- 지오서버 레이어 생성--%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/createlayer.js"></script>

<%-- 지오서버 그룹 레이어 삭제--%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/deletegrouplayer.js"></script>

<%-- 지오서버 에러 레이어 삭제--%>
<script
	src="${pageContext.request.contextPath}/resources/js/gitbuilder/deletelayer.js"></script>

<%-- 파일업로드 --%>
<script src="${ctx}/resources/js/dropzone/dropzone.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/css/fileupload/jquery.fileupload-ui.css">

<%-- 지오코더 --%>
<link href="${ctx}/resources/js/ol3-geocoder/ol3-geocoder.css"
	rel="stylesheet" />
<script src="${ctx}/resources/js/ol3-geocoder/ol3-geocoder.js"></script>

<%-- 알림 --%>
<link rel="stylesheet"
	href="${ctx}/resources/js/sweetalert/sweetalert.css">
<script src="${ctx}/resources/js/sweetalert/sweetalert.min.js"></script>
<script src="${ctx}/resources/js/sweetalert/sweetalert.js"></script>

<!-- sockjs -->
<%-- <script src="${ctx}/resources/js/sockjs/sockjs.min.js"></script> --%>

<!-- stomp -->
<%-- <script src="${ctx}/resources/js/stomp/stomp.min.js"></script> --%>

<script type="text/javascript">
	var CONTEXT = "${pageContext.request.contextPath}";
</script>

<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/img/favi32.png" />
<!-- 오픈레이어스 3 오버라이딩 -->
<!-- gb.overriding.olinteractiondraw -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/overriding/olinteractiondraw.js"></script>
<!-- gb.overriding.olinteractionselect -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/overriding/olinteractionselect.js"></script>

<!-- 프로토타입 코딩 -->
<!-- gb css -->
<link href="${ctx}/resources/js/gb/css/gb.css" rel="stylesheet" />
<!-- gb 네임스페이스-->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/gb_debug.js"></script>
<!-- gb.panel.Base -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/panel/base.js"></script>
<!-- gb.panel.EditingTool -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/panel/editingtool.js"></script>
<!-- gb.panel.LayerStyle -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/panel/layerstyle.js"></script>
<!-- gb.interaction.SelectWMS -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/interaction/selectwms.js"></script>
<!-- gb.interaction.MultiTransform -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/interaction/multitransform.js"></script>
<!-- gb.modal.Base -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/modal/base.js"></script>
<!-- gb.modal.Generalization -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/modal/generalization.js"></script>
<!-- gb.modal.ValidationDefinition -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/modal/validationdefinition.js"></script>
<!-- gb.modal.BaseCRS -->
<script
	src="${pageContext.request.contextPath}/resources/js/gb/modal/basecrs.js"></script>