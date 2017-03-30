<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:if test="${not empty exception}">
<script type="text/javascript">
function onloadMessage(){
	if(window.name!=""){
		alert("오류가 발생했습니다.\n다시 시도해 주세요.\n오류가 반복될 경우 시스템 관리자에게 문의하세요.");
		if(!confirm("홈으로 가시려면 확인,\n다시 시도하시려면 취소를 누른 후\n다시 시도해 주세요.")){
			return false;
		}
	};
}
</script>
</c:if>
<!DOCTYPE HTML>
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Exception</TITLE>
<link rel="stylesheet" type="text/css" href="/css/common.css" media="screen" />
</HEAD>
<body onload="<c:if test='${not empty exception}'>onloadMessage();</c:if>">
	<div id="center_div" >
		<div class="content">
			<h3>Exception 오류가 발생했습니다.</h3>
			<p>
				<c:out value="${exception.message}"/>
				<br><br>
				아래 바로가기 버튼을 클릭 후 다시 이용해 주시기 바랍니다.
			</p>
		</div>
		<div class="btn_area">
			<a href="/" target="_top" class="button">홈으로 가기</a>
			<a href="javascript:history.back(-1);" target="_top" class="button">뒤로 가기</a>
		</div>
	</div>
</body>
</html>