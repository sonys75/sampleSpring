<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%String sMenuUri = (String)request.getAttribute("javax.servlet.forward.request_uri");%>
<c:set var="menuUri" value="<%=sMenuUri%>"/>
<c:set var="dirName" value="${fnc:split(menuUri, '/')}" />
<c:set var="menuDir" value=""/>
<c:set var="menuNumber" value="0"/>
<c:set var="activeMenu" value="100"/>

<script type="text/javascript">
$(function() {
	var activeMenu = document.getElementById("activeMenu").value;
	initMenu(activeMenu);
});

function initMenu(idx){
	$( "#accordion" ).accordion({
		heightStyle: "content",
		active:Number(idx)
	});
}
</script>
				<div id="section-navigation">
					<div id="accordion">
					<%-- 
						<sec:authorize access="hasRole('RESTADMIN')">
							<h3>회원관리</h3>
							<div>
								<ul>
									<li><a href="/mng/crp/list.do">회사관리</a></li>
									<li><a href="/mng/usr/list.do">회원관리</a></li>
								</ul>
							</div>
							<c:if test="${fnc:contains(menuUri, '/mng/crp/') || fnc:contains(menuUri, '/mng/usr/')}">
								<c:set var="activeMenu" value="${menuNumber}"/>
							</c:if>
						<c:set var="menuNumber" value="${menuNumber+1}"/>
						</sec:authorize>
						<sec:authorize access="hasRole('RESTUSER')">
							<h3>파일관리</h3>
							<div>
								<ul>
									<li><a href="/mng/fle/list.do">파일관리</a></li>
									<li><a href="/mng/ply/list.do">재생목록관리</a></li>
								</ul>
							</div>
							<c:if test="${fnc:contains(menuUri, '/mng/fle/') || fnc:contains(menuUri, '/mng/ply/')}">
								<c:set var="activeMenu" value="${menuNumber}"/>
							</c:if>
							<c:set var="menuNumber" value="${menuNumber+1}"/>
						</sec:authorize>
						<sec:authorize access="hasRole('RESTUSER')">
							<h3>기기관리</h3>
							<div>
								<ul>
									<li><a href="/mng/eup/list.do">기기정보</a></li>
									<li><a href="/mng/noe/list.do">공지관리</a></li>
								</ul>
							</div>
							<c:if test="${fnc:contains(menuUri, '/mng/eup/') || fnc:contains(menuUri, '/mng/noe/')}">
								<c:set var="activeMenu" value="${menuNumber}"/>
							</c:if>
							<c:set var="menuNumber" value="${menuNumber+1}"/>
						</sec:authorize>
						<sec:authorize access="hasRole('SITEADMIN')">
							<h3>사이트관리</h3>
							<div>
								<ul>
									<li><a href="/mng/cde/list.do">코드 관리</a></li>
									<li><a href="/mng/app/list.do">APP 관리</a></li>
								</ul>
							</div>
							<c:if test="${fnc:contains(menuUri, '/mng/cde/') || fnc:contains(menuUri, '/mng/app/')}">
								<c:set var="activeMenu" value="${menuNumber}"/>
							</c:if>
							<c:set var="menuNumber" value="${menuNumber+1}"/>
						</sec:authorize>
						<sec:authorize access="hasRole('SUPERADMIN')">
							<h3>시스템관리</h3>
							<div>
								<ul>
									<li><a href="/sys/aut/mng/list.do">권한관리</a></li>
									<li><a href="/sys/res/mng/list.do">자원관리</a></li>
									<li><a href="/sys/mnu/mng/list.do">메뉴관리</a></li>
									<li><a href="/sys/mnu/aut/list.do">메뉴권한관리</a></li>
								</ul>
							</div>
							<c:if test="${fnc:contains(menuUri, '/sys/')}">
								<c:set var="activeMenu" value="${menuNumber}"/>
							</c:if>
							<c:set var="menuNumber" value="${menuNumber+1}"/>
						</sec:authorize>
						 --%>

						<c:forEach items="${dirName}" varStatus="status">
							<c:choose>
								<c:when test="${status.index==fnc:length(dirName)-1}"></c:when>
								<c:otherwise>
								<c:set var="menuDir" value="${menuDir}/${dirName[status.index]}"/>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<c:set var="beforeLevel" value="0"/>
						<c:forEach var="result" items="${MenuList}" varStatus="status">
							<c:choose>
								<c:when test="${result.level==1}">
									<c:if test="${beforeLevel!='0' && beforeLevel!='1'}">
										<c:set var="menuNumber" value="${menuNumber+1}"/>
										</ul></div>
									</c:if>
									<h3><c:out value="${result.menu_nm}"/></h3>
									<c:set var="beforeLevel" value="${result.level}"/>
								</c:when>
								<c:otherwise>
									<c:if test="${beforeLevel=='0' || beforeLevel=='1'}">
										<div><ul>
									</c:if>
									<li><a href="<c:out value="${result.menu_url}"/>"><c:out value="${result.menu_nm}"/></a></li>
									<c:set var="beforeLevel" value="${result.level}"/>
									<c:if test="${fnc:contains(result.menu_url,menuDir)}">
										<c:set var="activeMenu" value="${menuNumber}"/>
									</c:if>
								</c:otherwise>
							</c:choose>
                        </c:forEach>
                        <c:set var="menuNumber" value="${menuNumber+1}"/>
                        </ul></div>
					</div>
					<input type="hidden" id="activeMenu" value="${activeMenu}">
				</div>
