<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	//치환 변수 선언
	pageContext.setAttribute("cr", "\r"); //Space
	pageContext.setAttribute("cn", "\n"); //Enter
	pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
	pageContext.setAttribute("br", "<br/>"); //br 태그
%> 
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="/css/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="/css/common.css">
<script src="/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	$(document).bind("mobileinit", function(){
		$.mobile.ajaxLinksEnabled = false;
		$.mobile.ajaxFormsEnabled = false;
		$.mobile.ajaxEnabled = false;
	});
</script>
<script src="/js/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
	function fncSearch() {
		var f = document.F1;

		if (f.part.value == "") {
			alert("게시판 분류를 선택하세요.");
			f.part.focus();
			return false;
		}
		
		f.action = "/nvl/brd/list.do";
		f.submit();
	}
</script>
</head>
<body>
	<div data-role="page" id="pageone">
		<form name="F1" id="F1" method="post" action="/nvl/brd/list.do" data-ajax="false">
			<div data-role="header" style="overflow:hidden;">
				<h1>
				<c:if test="${not empty VoNovel.content}">
					${VoNovel.subject}
				</c:if>
				<c:if test="${empty VoNovel.content}">
					게시판
				</c:if>
				</h1>
			</div>
			<div data-role="main" class="ui-content" id="ui-content">
				<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
				    <select name="part" id="part" data-inline="true" data-mini="true">
						<option value="">선택하세요.</option>
						<c:forEach var="result" items="${PartList}" varStatus="status">
							<option value="${result.part}" <c:if test="${result.part == VoComm.part}"> selected</c:if>>${result.title}</option>
						</c:forEach>
					</select>

				    <select name="sort" id="sort">
				        <option value="ASC" <c:if test="${'ASC' == VoComm.sort}"> selected</c:if>>정순</option>
				        <option value="DESC" <c:if test="${'DESC' == VoComm.sort}"> selected</c:if>>역순</option>
				    </select>

					<a onclick="fncSearch()" class="ui-btn" style="cursor:pointer">검색</a>
				</fieldset>

	            
	            <c:if test="${not empty VoNovel.content}">
		            <hr>	
					<table style="border:0px;width: 100%; style="text-overflow: ellipsis;"">
	                    <colgroup>
							<col width="50%"/>
							<col />
	                    </colgroup>
	                    <tr>
	                        <td class="a_center">
	                        	<c:if test="${not empty VoNovelPre}">
	                        		<c:set var="chkPreList" value="false"/>
	                        		<c:forEach var="result" items="${list}" varStatus="status">
	                        			<c:if test="${result.seq == VoNovelPre.seq}"><c:set var="chkPreList" value="true"/></c:if>
	                        		</c:forEach>
	                        		<c:choose>
		                            	<c:when test="${chkPreList == false}"><c:set var="prePage" value="${listPage.currentPage-1}"/></c:when>
		                            	<c:when test="${chkPreList == true}"><c:set var="prePage" value="${listPage.currentPage}"/></c:when>
		                            </c:choose>
	                            	<a href="/nvl/brd/list.do?seq=${VoNovelPre.seq}&page_no=${prePage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>">이전 글</a>
	                            </c:if>
	                        </td>
	                        <td class="a_center">
	                        	<c:if test="${not empty VoNovelNext}">
	                        		<c:set var="chkNextList" value="false"/>
	                        		<c:forEach var="result" items="${list}" varStatus="status">
	                        			<c:if test="${result.seq == VoNovelNext.seq}"><c:set var="chkNextList" value="true"/></c:if>
	                        		</c:forEach>
	                        		<c:choose>
		                            	<c:when test="${chkNextList == false}"><c:set var="nextPage" value="${listPage.currentPage+1}"/></c:when>
		                            	<c:when test="${chkNextList == true}"><c:set var="nextPage" value="${listPage.currentPage}"/></c:when>
		                            </c:choose>
		                            <a href="/nvl/brd/list.do?seq=${VoNovelNext.seq}&page_no=${nextPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>">다음 글</a>
	                            </c:if>
	                        </td>
	                    </tr>
	                </table>
	                <hr>
					<div>
					
					<c:out value="${fnc:replace(VoNovel.content, cn, br) }"  escapeXml="false"/>
					</div>
					<hr>
					<table style="border:0px;width: 100%; style="text-overflow: ellipsis;"">
	                    <colgroup>
							<col width="50%"/>
							<col />
	                    </colgroup>
	                    <tr>
	                        <td class="a_center">
	                        	<c:if test="${not empty VoNovelPre}">
	                            	<a href="/nvl/brd/list.do?seq=${VoNovelPre.seq}&page_no=${prePage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>"><c:out value="${VoNovelPre.subject}"/></a>
	                            </c:if>
	                        </td>
	                        <td class="a_center">
	                        	<c:if test="${not empty VoNovelNext}">
		                            <a href="/nvl/brd/list.do?seq=${VoNovelNext.seq}&page_no=${nextPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>"><c:out value="${VoNovelNext.subject}"/></a>
	                            </c:if>
	                        </td>
	                    </tr>
	                </table>
				</c:if>
				
				<hr>
				
				<table class="tbl" summary="소설 목록">
	            <colgroup>
					<col width="15%"/>
	                <col/>
				</colgroup>
	            	<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
						</tr>
					</thead>
	                <tbody>
	                <c:choose>
	                	<c:when test="${not empty list}">
	                		<c:forEach var="result" items="${list}" varStatus="status">
	                        <tr>
	                            <td class="a_center"><c:out value="${listPage.totalCount - ( listPage.currentPage - 1 ) * listPage.perPage - status.index}"/></td>
	                            <td>
		                            <c:choose>
		                            	<c:when test="${VoNovel.seq == result.seq}"><c:out value="${result.subject}"/></c:when>
		                            	<c:otherwise><a href="/nvl/brd/list.do?seq=${result.seq}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>"><c:out value="${result.subject}"/></a></c:otherwise>
		                            </c:choose>
	                            </td>
	                        </tr>
	                        </c:forEach>
	                	</c:when>
	                	<c:otherwise>
	                        <tr>
	                            <td colspan="2" class="a_center">검색 결과가 없습니다.</td>
	                        </tr>	                	
	                	</c:otherwise>
	                </c:choose>
	                </tbody>
	            </table>
	            <div class="page">
	            	${fnc:replace(listPage.pageSetHtml, crlf, br)}
	            </div>
			</div>
			<div data-role="footer">
				<h1>TEST MOBLILE</h1>
			</div>
		</form>
	</div>

</body>
</html>