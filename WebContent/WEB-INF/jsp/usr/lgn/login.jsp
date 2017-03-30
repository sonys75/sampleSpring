<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="imagetoolbar" content="no"/>
<title>로그인</title>
<link rel="stylesheet" type="text/css" href="/css/common.css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
<spring:message code="Check.field.required_l" arguments="아이디" var="id_required"/>
<spring:message code="Check.field.required_l" arguments="비밀번호" var="pw_required"/>
<spring:message code="Check.field.min" arguments="4" var="mins"/>
<c:if test="${not empty retMessage}">
<c:set var="retMsg" value=""/>
<c:choose>
	<c:when test="${retMessage=='notMember'}">
		<c:set var="retMsg" value="등록된 아이디가 아닙니다."/>
	</c:when>
	<c:when test="${retMessage=='access_use_no'}">
		<c:set var="retMsg" value="등록된 아이디가 중지상태 입니다.\n관리자에게 문의해 주세요."/>
	</c:when>
	<c:when test="${retMessage=='passwd_wrong'}">
		<c:choose>
			<c:when test="${failcnt>='5'}">
				<c:set var="retMsg" value="비밀번호가 총 5회 틀렸습니다.\n해당 아이디가 중지되었습니다.\n관리자에게 문의해 주세요."/>
			</c:when>
			<c:otherwise>
				<c:set var="retMsg" value="비밀번호를 확인해 주세요! 남은 횟수 ${5-failcnt}회 ( 총 5회 )"/>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>
</c:if>
<script type="text/javascript">
	$(document).ready(function() {
		InitFocus();
	});

	//로그인 페이지에 들어왔을때 UserID 폼에 포커스를 준다
	function InitFocus(){
	    var f = document.F1;
	    f.user_id.focus();
	    return;
	}
	
	//로그인 폼에서 엔터키를 경우 로그인 폼 체크함수로 보낸다
	function LogInKeyPress(){
	    var f = document.F1;
	    if( event.keyCode == 13){
	        if ( !LogInChk() )
	            return;
	        f.submit();
	    }
	}
	
	//로그인 폼 체크함수
	function LogInChk(){
	    var f=document.F1;
	    if ( f.user_id.value == "" || f.user_id.value.length<'${mins}'){
	        alert("${id_required}");
	        f.user_id.focus();
	        return false;
	    }
	    if ( f.user_pw.value == "" || f.user_pw.value.length<'${mins}'){
	    	alert("${pw_required}");
	        f.user_pw.focus();
	        return false;
	    }
	    f.action="/j_spring_security_check";
	    f.submit();
	    return ;
	}
</script>
<spring:message code='test' arguments='메시지테스트,ㅇㅇㅇ' var="testMessage"/>
<spring:message code='test1' var="testMessage1"/>
<!-- 
${testMessage}
${testMessage1}
-->
<body>
	<div id="center_div" >
		<div class="content">
		<fieldset>
			<legend class="accessibility">로그인</legend>
			<form name="F1" id="F1" method="post" onsubmit="return LogInChk();">
			<input type="hidden" name="returnUrl" id="returnUrl" value="${returnUrl}"/>
			<input type="hidden" name="returnParam" id="returnParam" value=""/>
				<table class="tbl">
					<colgroup>
						<col width="25%">
						<col width="50%">
						<col width="25%">
					</colgroup>
					<tr>
						<th colspan="3">로그인</th>
					</tr>
					<tr>
						<th>아이디</th>
						<td><input type="text" title="아이디" name="user_id" autocapitalize="off" onkeypress="LogInKeyPress()" maxlength="20"/></td>
						<td rowspan="2" class="a_center"><a onclick="LogInChk();" style="cursor:pointer" title="로그인" class="button big">로그인</a></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input type="password" title="비밀번호" name="user_pw" onkeypress="LogInKeyPress()" maxlength="20"/></td>
					</tr>
					<tr>
						<td colspan="3">&middot; 아이디, 비밀번호 분실하였거나 사용을 원하시는 분은 관리자에게 문의하여 주세요.</td>
					</tr>
					<c:if test="${not empty retMessage}">
				        <tr>
				            <td colspan="3">
				                <font color="red">${retMsg}</font>
				            </td>
				        </tr>
			        </c:if>					
				</table>
			</form> 
		</fieldset>
		</div>
	</div>
</body>
</html>
