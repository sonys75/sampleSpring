<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 회원관리 &gt; 회원관리</div>
					<form name="F1" id="F1" method="post" action="/mng/usr/list.do" >
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td class="a_right">
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="USER_NM"<c:if test="${VoComm.sch_fld=='USER_NM'}"> selected</c:if>>이름</option>
		                        		<option value="USER_ID"<c:if test="${VoComm.sch_fld=='USER_ID'}"> selected</c:if>>아이디</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}"/>
		                            <!-- <a class="button" style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a> -->
		                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a></span>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->					
						<div class="count">총 <b><fmt:formatNumber value="${listPage.totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="회원 목록입니다.">
			            <caption class="accessibility">회원 목록</caption>
			            <colgroup>
							<col width="5%"/>
			                <col width="10%"/>
							<col width="10%"/>
			                <col />
			                <col width="12%"/>
			                <col width="8%"/>
			                <col width="10%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>아이디</th>
									<th>성명</th>
			                        <th>회사</th>
			                        <th>권한</th>
			                        <th>사용</th>
									<th>등록일</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty list}">
			                		<c:forEach var="result" items="${list}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${listPage.totalCount - ( listPage.currentPage - 1 ) * listPage.perPage - status.index}"/></td>
			                            <td><a href='/mng/usr/view.do?user_id=${result.user_id}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.user_id}"/></a></td>
			                            <td><a href='/mng/usr/view.do?user_id=${result.user_id}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.user_nm}"/></a></td>
			                            <td><c:out value="${result.corp_nm}"/></td>
			                            <td class="a_center"><c:out value="${result.auth_nm}"/></td>
			                            <td class="a_center">
				                            <c:choose>
				                            	<c:when test="${result.use_yn=='Y'}">
				                            		<span class="t_blue">사용중</span>
				                            	</c:when>
				                            	<c:when test="${result.use_yn=='N'}">
				                            		<span class="t_red">중지</span>
				                            	</c:when>
				                            	<c:otherwise>
				                            		기타
				                            	</c:otherwise>
				                            </c:choose>
			                            </td>
			                            <td class="a_center"><c:out value="${result.reg_dt}"/></td>
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
			            	<a class="button" title="등록" href='/mng/usr/regist.do?page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'>등록</a>
			            </div> 
					</form>
				</div>
