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

<TITLE>회사 검색</TITLE>

<link rel="stylesheet" type="text/css" href="/css/common.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/css/smoothness/jquery-ui-1.10.3.custom.css" media="screen">
<link rel="stylesheet" type="text/css" href="/css/jquery.colorpicker.css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
</head>
<body>
<script type="text/javascript">
	function fncSetCorpId(corp_id,corp_nm){
		//alert($("#up_corp_nm",top.document).val());
		//현재 회사 아이디
		var crnt_corp_id = $("#corp_id",top.document).val();
		
		if($("#up_corp_nm",top.document).val() == undefined){
			if(corp_id != crnt_corp_id){
				$("#corp_id",top.document).val(corp_id);
				$("#corp_nm",top.document).val(corp_nm);
			}
		}else{
			if(corp_id == crnt_corp_id){
				alert("현재 회사와 상위 회사가 같을 수 없습니다.");
				return false;
			}
			$("#up_corp_id",top.document).val(corp_id);
			$("#up_corp_nm",top.document).val(corp_nm);
		}
		$(".ui-button-text").click();
	}
</script>
					<div class="count">총 <b><fmt:formatNumber value="${totalCount}"/></b>건의 검색결과가 있습니다.</div>
		            <table class="tbl" summary="회사 목록입니다.">
		            <caption class="accessibility">회사 목록</caption>
		            <colgroup>
						<col width="15%"/>
		                <col />
					</colgroup>
		            	<thead>
							<tr>
								<th>번호</th>
								<th>회사명</th>
							</tr>
						</thead>
		                <tbody>
		                <c:choose>
		                	<c:when test="${not empty list}">
		                		<c:forEach var="result" items="${list}" varStatus="status">
		                        <tr>
		                            <td class="a_center"><fmt:formatNumber value="${totalCount - status.index}"/></td>
		                            <td>
		                            <c:set var="space" value=""/>
		                            <c:forEach var="i" begin="1" end="${result.level-1}" step="1">
										<c:set var="space" value="${space}${'&nbsp;&nbsp;'}"/>
									</c:forEach>
		                            <c:out value="${space}" escapeXml="false"/>
		                            <a style="cursor:pointer" onclick="fncSetCorpId('${result.corp_id}','${fnc:trim(result.corp_nm)}');"><c:out value="${result.corp_nm}"/></a></td>
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