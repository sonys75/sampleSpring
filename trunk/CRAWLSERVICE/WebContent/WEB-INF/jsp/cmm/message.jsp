<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>시스템 메세지</TITLE>
<meta http-equiv="Content-Script-Type" content="text/javascript" >
<meta http-equiv="Content-Style-Type" content="text/css" >
<meta http-equiv="X-UA-Compatible" content="IE=8" >
<meta http-equiv="imagetoolbar" content="no">
<script type="text/javascript" src="/js/common.js"></script>
<c:if test="${not empty VoCommon.retMessage || not empty VoCommon.retUrl}">
<c:set var="replaceurl" value="${fnc:replace(VoCommon.retUrl,'amp;','')}"/>
<c:set var="replacetarget" value="${fnc:replace(VoCommon.retTarget,'amp;','')}"/>
<script>
function onloadMessage(){
	var f=document.F1;
	
	if(f.retMessage.value!=""){
		alert(f.retMessage.value);
	}

	var sTarget = f.retTarget.value;
	var sUrl = f.retUrl.value;
	
	if(f.retUrl.value!=""){
		if(f.retTarget.value!=""){
			if(f.retTarget.value=="ajax"){
				$(".ui-button-text").click();
			}else if(f.retTarget.value=="parent"){
				parent.location.href = sUrl;
			}
		}else{
			location.href = sUrl;
		}
	}
}
</script>
</c:if>
</HEAD>
<body onLoad="<c:if test='${not empty VoCommon.retMessage || not empty VoCommon.retUrl}'>onloadMessage();</c:if>">
<form name="F1">
<input type="hidden" name="retMessage" value="${VoCommon.retMessage}"/>
<input type="hidden" name="retUrl" value="${replaceurl}"/>
<input type="hidden" name="retTarget" value="${replacetarget}"/>
</form>

<%-- 
<c:out value="${VoCommon.retMessage}"/><br/>
${VoCommon.retUrl}<br/>
${VoCommon.retTarget}<br/>
 --%>
<%-- 
	<c:if test="${not empty VoCommon.retMessage}">
		alert("<c:out value='${VoCommon.retMessage}'/>");
	</c:if>
	<c:if test="${not empty VoCommon.retUrl}">
		<c:choose>
			<c:when test="${not empty VoCommon.retTarget}">
				<c:out value="${replacetarget}"/>.location.href = '<c:out value="${replaceurl}"/>'.replace("amp;","").replace("amp;","");
			</c:when>
			<c:when test="${VoCommon.retTarget=='ajax'}">
				$(".ui-button-text").click();
			</c:when>
			<c:otherwise>
				location.href = "<c:out value='${replaceurl}'/>".replace("amp;","").replace("amp;","");
			</c:otherwise>
		</c:choose>
	</c:if>
 --%>	
</body>
</html>
