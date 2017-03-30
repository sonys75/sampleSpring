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

<TITLE>권한 검색</TITLE>

<link rel="stylesheet" type="text/css" href="/css/common.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/css/smoothness/jquery-ui-1.10.3.custom.css" media="screen">
<link rel="stylesheet" type="text/css" href="/css/jquery.colorpicker.css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
</head>
<body>
<script type="text/javascript">
	function fncSetAuthId(auth_id, auth_nm){
		var seq = document.getElementById("seq").value;
        if(seq==""){
        	alert("아이디 적용 위치가 없습니다.\n창을 닫고 다시 검색해 주세요.");
        	return;
        }
        
        var authInputLen = 0; 
        authInputLen = $("#authlistcnt",top.document).val();
        
        if(authInputLen == "0"){
        	alert("등록할 위치를 찾을 수 없습니다.\n창을 닫고 다시 검색해 주세요.");
        	return;
        }
		
        for(var i=0 ; i < authInputLen; i++){
    		if($("#auth_id"+i,top.document).val() == auth_id){

    			alert("중복되는 권한이 존재합니다.\n다른 권한을 선택해 주세요.");
    			return;
    			break;
    		}
    	}
        
        if($("#auth_id"+seq,top.document).val() != auth_id){
        	$("#auth_id"+seq,top.document).val(auth_id);
        	$("#auth_nm"+seq,top.document).val(auth_nm);
        }
        
		$(".ui-button-text").click();
	}
</script>
					<div class="count">총 <b><fmt:formatNumber value="${totalCount}"/></b>건의 검색결과가 있습니다.</div>
		            <table class="tbl" summary="권한 목록입니다.">
		            <input type="hidden" id="seq" value="${seq}"/>
		            <caption class="accessibility">권한 목록</caption>
		            <colgroup>
						<col width="15%"/>
		                <col />
					</colgroup>
		            	<thead>
							<tr>
								<th>번호</th>
								<th>권한명</th>
							</tr>
						</thead>
		                <tbody>
		                <c:choose>
		                	<c:when test="${not empty list}">
		                		<c:forEach var="result" items="${list}" varStatus="status">
		                        <tr>
		                            <td class="a_center"><fmt:formatNumber value="${totalCount - status.index}"/></td>
		                            <td>
		                            <a style="cursor:pointer" onclick="fncSetAuthId('${result.auth_id}','${fnc:trim(result.auth_nm)}');"><c:out value="${result.auth_nm}"/></a></td>
		                        </tr>
		                        </c:forEach>
		                	</c:when>
		                	<c:otherwise>
		                        <tr>
		                            <td colspan="2">검색 결과가 없습니다.</td>
		                        </tr>	                	
		                	</c:otherwise>
		                </c:choose>
		                </tbody>
		            </table>
</body>
</html>