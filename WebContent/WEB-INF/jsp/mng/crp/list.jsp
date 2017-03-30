<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 회원관리 &gt; 회사관리</div>
					<form name="F1" id="F1" method="post" action="/mng/crp/list.do" >
						<%-- 
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td>${VoCommon.sch_fld}
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="CORP_NM"<c:if test="${VoComm.sch_fld=='CORP_NM'}"> selected</c:if>>회사명</option>
		                        		<option value="CORP_TEL"<c:if test="${VoComm.sch_fld=='CORP_TEL'}"> selected</c:if>>전화번호</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}"/>
		                            <a class="button" style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->					
						 --%>
						<div class="count">총 <b><fmt:formatNumber value="${listPage.totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="회사 목록입니다.">
			            <caption class="accessibility">회사 목록</caption>
			            <colgroup>
							<col width="5%"/>
			                <col width="30%"/>
							<col width="20%"/>
			                <col />
			                <col width="10%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>회사명</th>
									<th>전화번호</th>
			                        <th>주소</th>
									<th>등록일</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty list}">
			                		<c:forEach var="result" items="${list}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${listPage.totalCount - ( listPage.currentPage - 1 ) * listPage.perPage - status.index}"/></td>
			                            <td>
			                            <c:set var="space" value=""/>
			                            <c:forEach var="i" begin="1" end="${result.level-1}" step="1">
											<c:set var="space" value="${space}${'&nbsp;&nbsp;'}"/>
										</c:forEach>
			                            <c:out value="${space}" escapeXml="false"/>
			                            <a href='/mng/crp/view.do?corp_id=${result.corp_id}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.corp_nm}"/></a></td>
			                            <td class="a_center"><c:out value="${result.corp_tel}"/></td>
			                            <td><c:out value="${result.corp_adr}"/> <c:out value="${result.corp_adr_dsc}"/></td>
			                            <td class="a_center"><c:out value="${result.reg_dt}"/></td>
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
			            	<a class="button" title="등록" href='/mng/crp/regist.do?page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'>등록</a>
			            </div> 
					</form>
				</div>
