<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 기기관리 &gt; 공지관리</div>
					<form name="F1" id="F1" method="post" action="/mng/noe/list.do" >
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td class="a_right">
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="MSG_TITLE"<c:if test="${VoComm.sch_fld=='MSG_TITLE'}"> selected</c:if>>공지제목</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}"/>
		                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a></span>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->					
						<div class="count">총 <b><fmt:formatNumber value="${listPage.totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="공지 관리 목록">
			            <caption class="accessibility">공지 관리 목록</caption>
			            <colgroup>
							<col width="5%"/>
			                <col />
			                <col width="10%"/>
			                <col width="10%"/>
			                <col width="5%"/>
							<col width="10%"/>
							<col width="10%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>공지유형</th>
									<th>소유회사</th>
			                        <th>배포수</th>
			                        <th>공지수</th>
			                        <th>사용여부</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty list}">
			                		<c:forEach var="result" items="${list}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${listPage.totalCount - ( listPage.currentPage - 1 ) * listPage.perPage - status.index}"/></td>
			                            <td>
			                            	<c:choose>
			                            		<c:when test="${not empty result.msg_title}">
			                            			<a href='/mng/noe/view.do?msg_id=${result.msg_id}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.msg_title}"/></a>
			                            		</c:when>
			                            		<c:otherwise>
			                            			<a href='//mng/noe/view.do?msg_id=${result.msg_id}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.msg_id}"/></a>
			                            		</c:otherwise>
			                            	</c:choose>
			                            </td>
			                            <td class="a_center">
			                            	<c:choose>
			                            		<c:when test="${result.msg_type=='G'}">일반</c:when>
			                            		<c:when test="${result.msg_type=='S'}">긴급</c:when>
			                            		<c:when test="${result.msg_type=='T'}">테스트</c:when>
			                            		<c:otherwise>기타</c:otherwise>
			                            	</c:choose>
			                            </td>
			                            <td class="a_center"><c:out value="${result.user_id}"/></td>
			                            <td class="a_center"><c:out value="${result.stp_cnt}"/></td>
			                            <td class="a_center"><c:out value="${result.msg_cnt}"/></td>
			                            <td class="a_center">
			                            	<c:choose>
			                            		<c:when test="${result.use_yn=='Y'}">사용</c:when>
			                            		<c:when test="${result.use_yn=='N'}">중지</c:when>
			                            		<c:when test="${result.use_yn=='S'}">대기</c:when>
			                            		<c:otherwise>기타</c:otherwise>
			                            	</c:choose>
			                            </td>
			                        </tr>
			                        </c:forEach>
			                	</c:when>
			                	<c:otherwise>
			                        <tr>
			                            <td colspan="7" class="a_center">검색 결과가 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="page">
			            	${fnc:replace(listPage.pageSetHtml, crlf, br)}
			            </div>
			            <div class="btnR">
			            	<a class="button" title="등록" href='/mng/noe/regist.do?page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'>등록</a>
			            </div> 
					</form>
				</div>
