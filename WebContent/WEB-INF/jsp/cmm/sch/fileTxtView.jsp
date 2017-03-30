<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="imagetoolbar" content="no" />

<TITLE>미리보기</TITLE>

<link rel="stylesheet" type="text/css" href="/css/common.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/css/smoothness/jquery-ui-1.10.3.custom.css" media="screen">
<link rel="stylesheet" type="text/css" href="/css/jquery.colorpicker.css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//document.getElementById("skinDiv").innerHTML="<img src=\"/common/downLoadFile.do?type=filemanage&file_id=${VoFileInfo.file_id}\" width=\"100%\"/>";
	});
</script>
</head>
<body>
<c:if test="${ChkAuth}">
	<table class="tbl" summary="안내정보 미리보기" style="width:640px;height:360px;">
		<caption class="accessibility">안내정보 미리보기</caption>
		<colgroup>
			<col/>
		</colgroup>
		<tbody>
			
			<c:choose>
           		<c:when test="${VoFileInfo.file_type=='I'}">
           			<c:set var="style" value="clear:both;background:url(/common/downLoadFile.do?type=filemanage&file_id=${VoFileInfo.file_id}) no-repeat; background-size:100% 100%;overflow: hidden;margin:0px"/>
           		</c:when>
           		<c:otherwise>
           			<c:set var="style" value="clear:both;;overflow:hidden;margin:0px"/>
           		</c:otherwise>
            </c:choose>
            <tr>
	            <td class="a_top" style="${style}">
		            <c:forEach var="result" items="${TxtInfoList}" varStatus="status">
		            	<c:choose>
		               		<c:when test="${result.ifo_size == 'B'}"><c:set var="size" value="font-size:40px;"/></c:when>
		               		<c:when test="${result.ifo_size == 'L'}"><c:set var="size" value="font-size:25px;"/></c:when>
		               		<c:when test="${result.ifo_size == 'M'}"><c:set var="size" value="font-size:15px;"/></c:when>
		               		<c:when test="${result.ifo_size == 'S'}"><c:set var="size" value="font-size:12px;"/></c:when>
		                </c:choose>
		                <c:set var="color" value="color:${result.ifo_clr};"/>
		                <c:set var="upadding" value="margin-top:${result.ifo_upad}px;"/>
						<c:choose>
		               		<c:when test="${result.ifo_aln == 'L'}"><c:set var="align" value="left"/></c:when>
		               		<c:when test="${result.ifo_aln == 'C'}"><c:set var="align" value="center"/></c:when>
		               		<c:when test="${result.ifo_aln == 'R'}"><c:set var="align" value="right"/></c:when>
		                </c:choose>
		                <c:choose>
		               		<c:when test="${result.ifo_aln == 'C'}"></c:when>
		               		<c:otherwise><c:set var="spadding" value="margin-${align}:${result.ifo_spad}px;"/></c:otherwise>
		                </c:choose>
						<c:set var="spadding" value="margin-left:${result.ifo_spad}px;margin-right:${result.ifo_spad}px;"/>
		            	<div style="${size}${color}${upadding}${spadding}text-align:${align};">${result.ifo_msg}</div>
		            </c:forEach>
				</td>
			</tr>
		</tbody>
	</table>
</c:if>
<%-- 
			
			<c:if test="${VoFileInfo.file_type=='I'}">이미지</c:if>
 --%>
</body>
</html>