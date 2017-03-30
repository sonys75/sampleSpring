<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0" />
<meta name="format-detection" content="telephone=no" />

<meta name="description" content="<spring:message code="Header.title"/>" />
<meta property="og:title" content="<spring:message code="Header.title"/>">
<meta property="og:description" content="<spring:message code="Header.title"/>">
<meta property="keyword" content="<spring:message code="Header.title"/>">
<spring:message code="Content.step.3.alert.2.placeholder" var="alertPlaceholder"/>
<spring:message code="Script.step.3.chk.no.hp" var="alertTelInput" />
<spring:message code="Script.step.3.sms.send.success" var="alertSuccess" />
<spring:message code="Script.step.3.sms.send.fail" var="alertFail" />
<spring:message code="Script.step.3.sms.send.over" var="alertSendOver" />
<spring:message code="Script.step.3.auth.match.fail" var="alertMatchFail"/>
<spring:message code="Script.step.3.sms.send.msg" var="smsMsg"/>

<title><spring:message code="Header.title" /></title>

<link rel="stylesheet" href="/css/default.css">
<link href="/css/font-awesome.min.css" rel="stylesheet">
<script src="/js/jquery-1.4.4.min.js"></script>
<script src="/js/jquery.mobile-1.0a2.min.js"></script>
<script type="text/javascript">
function fncConfirm(){
	var f=document.F1;

	var database = f.database.value;
	var ip = f.ip.value;
	var port = f.port.value;
	var sid = f.sid.value;
	var id = f.id.value;
	var password = f.password.value;

	var url = "/dbTest.do";

	$.ajax({
		type : "POST",
		url : url,
		data : ({"database" : database, "ip" : ip, "port" : port, "sid" : sid, "id" : id, "password" : password}),
		cache: false,
		success:function(response){
			document.getElementById("result").innerText = response.msg;
		}
	});
}

</script>
</head>
<body>
	<div id="wrapper">
		<form name="F1" id="F1" method="post">
			<div id="content">
				<div class="alert">데이터베이스선택</div>
				<div class="alignC"> 
					<select name="database">
						<option value="oracle">ORACLE</option>
						<option value="mysql">MySQL</option>
					</select>
				</div>
				<div class="alert">아이피</div>
				<div class="button">
					<input type="text" id="ip" name="ip" placeholder="127.0.0.1">
				</div>
				<div class="alert">포트</div>
				<div class="button">
					<input type="text" id="port" name="port" placeholder="1521">
				</div>
				<div class="alert">SID</div>
				<div class="button">
					<input type="text" id="sid" name="sid" placeholder="ORCL 또는 dbname?characterEncoding=utf8">
				</div>
				<div class="alert">아이디</div>
				<div class="button">
					<input type="text" id="id" name="id" placeholder="데이터베이스 유저 아이디">
				</div>
				<div class="alert">패스워드</div>
				<div class="button">
					<input type="text" id="password" name="password" placeholder="데이터베이스 유저 패스워드">
					<input type="button" onclick="fncConfirm();" value="테스트">
				</div>
				<div class="alert alignC" id="result"></div>
			</div>
		</form>
	</div>
</body>
</html>
