<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 게시판</div>
					<form name="F1" id="F1" method="post" action="/cmm/brd/list.do" >
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td class="a_right">
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="SUBJECT"<c:if test="${VoComm.sch_fld=='SUBJECT'}"> selected</c:if>>제목</option>
		                        		<option value="CONTENT"<c:if test="${VoComm.sch_fld=='CONTENT'}"> selected</c:if>>내용</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}"/>
		                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a></span>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->					

						<div class="count">총 <b><fmt:formatNumber value="${listPage.totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="게시판 목록입니다.">
			            <caption class="accessibility">게시판 목록</caption>
			            <colgroup>
							<col width="5%"/>
							<col />
			                <col width="5%"/>
			                <col width="10%"/>
			                <col width="10%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>조회수</th>
									<th>등록자</th>
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
			                            <c:forEach var="i" begin="1" end="${result.rpy_step}" step="1">
											<c:set var="space" value="${space}${'&nbsp;&nbsp;'}"/>
										</c:forEach>
			                            <c:out value="${space}" escapeXml="false"/>
			                            <a href="/cmm/brd/view.do?seq=${result.seq}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>"><c:out value="${result.subject}"/></a>
			                            </td>
			                            <td class="a_center"><c:out value="${result.read_cnt}"/></td>
			                            <td class="a_center"><c:out value="${result.reg_id}"/></td>
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
			            	<a class="button" title="등록" href='/cmm/brd/regist.do?page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'>등록</a>
			            </div> 
					</form>
				</div>
