<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
		<div id="header">
			<h1>${VoUserInfo.corp_nm} DID 관리시스템</h1>
		</div>
		<div id="navigation">
			<ul>
				<li><a href="/" title="홈페이지 바로가기">Home</a></li>

				<sec:authorize access="isAuthenticated()">
				<li><a href="/usr/edt/mypage.do" title="${comUserInfo.user_nm}님의 개인정보를 수정합니다.">개인정보수정</a></li>
				</sec:authorize>
				<li><a href="/usr/lgn/logout.do" title="로그아웃">로그아웃</a></li>
				<li><%-- ${comUserInfo.user_id} " : " ${comUserInfo.user_nm} " : " ${comUserInfo.user_auth} --%></li>
			</ul>
		</div>