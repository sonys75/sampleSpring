<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 사이트관리 &gt; 코드관리</div>
					<form name="F1" id="F1" method="post" action="/mng/cde/list.do" >
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td class="a_right">
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="CODE_PART"<c:if test="${VoComm.sch_fld=='CODE_PART'}"> selected</c:if>>코드분류</option>
		                        		<option value="CODE"<c:if test="${VoComm.sch_fld=='CODE'}"> selected</c:if>>코드</option>
		                        		<option value="CODE_NM"<c:if test="${VoComm.sch_fld=='CODE_NM'}"> selected</c:if>>코드명</option>
		                        		<option value="CODE_DESC"<c:if test="${VoComm.sch_fld=='CODE_DESC'}"> selected</c:if>>코드설명</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}"/>
		                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a></span>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->					
						<div class="count">총 <b><fmt:formatNumber value="${listPage.totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="코드 목록입니다.">
			            <caption class="accessibility">코드 목록</caption>
			            <colgroup>
							<col width="5%"/>
			                <col width="10%"/>
							<col width="10%"/>
			                <col width="18%"/>
			                <col width="5%"/>
			                <col />
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>분류코드</th>
									<th>코드</th>
			                        <th>코드명</th>
			                        <th>순번</th>
			                        <th>코드상세</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty list}">
			                		<c:forEach var="result" items="${list}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${listPage.totalCount - ( listPage.currentPage - 1 ) * listPage.perPage - status.index}"/></td>
			                            <td><a href='/mng/cde/regist.do?code_part=${result.code_part}&code=${result.code}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.code_part}"/></a></td>
			                            <td><a href='/mng/cde/regist.do?code_part=${result.code_part}&code=${result.code}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.code}"/></a></td>
			                            <td><c:out value="${result.code_nm}"/></td>
			                            <td class="a_center"><c:out value="${result.code_ord}"/></td>
			                            <td><c:out value="${result.code_desc}"/></td>
			                        </tr>
			                        </c:forEach>
			                	</c:when>
			                	<c:otherwise>
			                        <tr>
			                            <td colspan="6" class="a_center">검색 결과가 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="page">
			            	${fnc:replace(listPage.pageSetHtml, crlf, br)}
			            </div>
			            <div class="btnR">
			            	<a class="button" title="분류목록" href='/mng/cde/part_list.do'>분류목록</a>
			            	<a class="button" title="코드등록" href='/mng/cde/regist.do?page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'>코드등록</a>
			            </div> 
					</form>
				</div>
