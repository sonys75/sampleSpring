<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 권한관리</div>
					<form name="F1" id="F1" method="post" action="/sys/aut/mng/list.do" >
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td class="a_right">
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="AUTH_NM"<c:if test="${VoComm.sch_fld=='AUTH_NM'}"> selected</c:if>>권한명</option>
		                        		<option value="AUTH_ID"<c:if test="${VoComm.sch_fld=='AUTH_ID'}"> selected</c:if>>권한ID</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}"/>
		                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a></span>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->					

						<div class="count">총 <b><fmt:formatNumber value="${listPage.totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="권한 목록입니다.">
			            <caption class="accessibility">권한 목록</caption>
			            <colgroup>
							<col width="5%"/>
			                <col width="20%"/>
							<col width="20%"/>
			                <col width="20%"/>
			                <col/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>상위권한ID</th>
									<th>상위권한명</th>
			                        <th>권한ID</th>
									<th>권한명</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty list}">
			                		<c:forEach var="result" items="${list}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${listPage.totalCount - ( listPage.currentPage - 1 ) * listPage.perPage - status.index}"/></td>
			                            <td><a href="/sys/aut/mng/view.do?auth_no=${result.auth_no}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>"><c:out value="${result.up_auth_id}"/></a></td>
			                            <td><a href="/sys/aut/mng/view.do?auth_no=${result.auth_no}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>"><c:out value="${result.up_auth_nm}"/></a></td>
			                            <td><a href="/sys/aut/mng/view.do?auth_no=${result.auth_no}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>"><c:out value="${result.auth_id}"/></a></td>
			                            <td><a href="/sys/aut/mng/view.do?auth_no=${result.auth_no}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>"><c:out value="${result.auth_nm}"/></a></td>
			                        </tr>
			                        </c:forEach>
			                	</c:when>
			                	<c:otherwise>
			                        <tr>
			                            <td colspan="5" class="a_center">검색 결과가 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="page">
			            	${fnc:replace(listPage.pageSetHtml, crlf, br)}
			            </div>
			            <div class="btnR">
			            	<a class="button" title="조회" href="/sys/aut/mng/auth.do">조회</a>
			            	<a class="button" title="등록" href="/sys/aut/mng/regist.do?page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>">등록</a>
			            </div> 
					</form>
				</div>
