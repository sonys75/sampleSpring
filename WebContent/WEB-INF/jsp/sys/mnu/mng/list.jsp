<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 메뉴관리</div>
					<form name="F1" id="F1" method="post" action="/sys/mnu/mng/list.do" >
			            <table class="tbl" summary="메뉴 목록입니다.">
			            <caption class="accessibility">메뉴 목록</caption>
			            <colgroup>
							<col width="10%"/>
			                <col width="50%"/>
			                <col/>
						</colgroup>
			            	<thead>
								<tr>
									<th>레벨</th>
									<th>메뉴명</th>
									<th>URL</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty list}">
			                		<c:forEach var="result" items="${list}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><c:out value="${result.level}"/></td>
			                            <td>
			                            	<c:set var="space" value=""/>
				                            <c:forEach var="i" begin="1" end="${result.level-1}" step="1">
												<c:set var="space" value="${space}${'&nbsp;&nbsp;'}"/>
											</c:forEach>
				                            <c:out value="${space}" escapeXml="false"/>
			                            	<a href="/sys/mnu/mng/view.do?menu_id=${result.menu_id}"><c:out value="${result.menu_nm}"/></a>
			                            </td>
			                            <td><a href="/sys/mnu/mng/view.do?menu_id=${result.menu_id}"><c:out value="${result.menu_url}"/></a></td>
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
			            	<a class="button" title="등록" href="/sys/mnu/mng/regist.do">등록</a>
			            </div> 
					</form>
				</div>
