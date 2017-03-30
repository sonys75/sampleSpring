<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 권한관리</div>
					<form name="F1" id="F1" method="post" action="/sys/aut/mng/auth.do" >
						<div class="count">총 <b><fmt:formatNumber value="${totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="권한 목록입니다.">
			            <caption class="accessibility">권한 목록</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="10%"/>
			                <col/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>레벨</th>
									<th>권한명</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty list}">
			                		<c:forEach var="result" items="${list}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${totalCount - status.index}"/></td>
			                            <td class="a_center"><c:out value="${result.level}"/></td>
			                            <td>
			                            <c:set var="space" value=""/>
			                            <c:forEach var="i" begin="1" end="${result.level-1}" step="1">
											<%-- <c:set var="space" value="${space}${'&nbsp;&nbsp;'}"/> --%>
											
											<c:choose>
												<c:when test="${i==1 && totalCount - status.index == 1}"><c:set var="space" value="${space}${'┖'}"/></c:when>
												<c:when test="${i==1 && totalCount - status.index != 1}"><c:set var="space" value="${space}${'┠'}"/></c:when>
												<c:otherwise><c:set var="space" value="${space}${'━'}"/></c:otherwise>
											</c:choose>
										</c:forEach>
			                            <c:out value="${space}" escapeXml="false"/>
			                            <c:out value="${result.auth_nm}"/>
			                            [ <c:out value="${result.auth_id}"/> ]
			                            </td>
			                        </tr>
			                        </c:forEach>
			                	</c:when>
			                	<c:otherwise>
			                        <tr>
			                            <td colspan="3" class="a_center">검색 결과가 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="btnR">
			            	<a class="button" title="목록" href="/sys/aut/mng/list.do">목록</a>
			            </div> 
					</form>
				</div>
