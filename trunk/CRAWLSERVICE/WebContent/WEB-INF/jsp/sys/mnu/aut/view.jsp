<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_auth_id"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.auth_id.value==""){
		alert("${check_auth_id}");
		return false;
	}

	f.action = "/sys/mnu/aut/regist.do";
	f.submit();
	return true;
}

</script>
				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 메뉴권한관리</div>
					<form:form commandName="VoMenu" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="auth_id"/>
			            <table class="tbl" summary="메뉴권한 목록입니다.">
			            <caption class="accessibility">메뉴권한 목록</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="10%"/>
			                <col/>
						</colgroup>
			            	<thead>
			            		<tr>
			                        <th>권한</th>
		                            <td colspan="2"><c:out value="${VoMenu.auth_nm}"/></td>
			                    </tr>
								<tr>
									<th>번호</th>
									<th>레벨</th>
									<th>메뉴명</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty MenuList}">
			                		<c:forEach var="result" items="${MenuList}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${totalCount - status.index}"/></td>
			                            <td class="a_center"><c:out value="${result.level}"/></td>
			                            <td>
			                            <c:set var="space" value=""/>
			                            <c:forEach var="i" begin="1" end="${result.level-1}" step="1">
											<c:set var="space" value="${space}${'&nbsp;&nbsp;'}"/>
										</c:forEach>
			                            <c:out value="${space}" escapeXml="false"/>
			                            <c:out value="${result.menu_nm}"/>
			                            </td>
			                        </tr>
			                        </c:forEach>
			                	</c:when>
			                	<c:otherwise>
			                        <tr>
			                            <td colspan="3" class="a_center">메뉴권한이 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	<a class="button" href="/sys/mnu/aut/list.do" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
