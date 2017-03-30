<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
				<div id="footer">Copyrights © EVANSOFT DID MANAGEMENT SYSTEM, 2015</div>
				<c:choose>
					<c:when test="${VoUserInfo.auth_id=='SUPERADMIN'}">
						<iframe name="HiddenFrame" width="100%" height="200"></iframe>
					</c:when>
					<c:otherwise>
						<iframe name="HiddenFrame" style="display:none"></iframe>
					</c:otherwise>
				</c:choose>