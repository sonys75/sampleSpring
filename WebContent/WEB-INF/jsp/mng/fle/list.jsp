<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 파일관리 &gt; 파일관리</div>
					<form name="F1" id="F1" method="post" action="/mng/fle/list.do" >
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td class="a_right">
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="ORG_FILE_NM"<c:if test="${VoComm.sch_fld=='ORG_FILE_NM'}"> selected</c:if>>파일명</option>
		                        		<option value="FILE_EXT"<c:if test="${VoComm.sch_fld=='FILE_EXT'}"> selected</c:if>>확장자</option>
		                        		<option value="FILE_ID"<c:if test="${VoComm.sch_fld=='FILE_ID'}"> selected</c:if>>아이디</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}"/>
		                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a></span>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->					
						<div class="count">총 <b><fmt:formatNumber value="${listPage.totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="파일 목록입니다.">
			            <caption class="accessibility">파일 목록</caption>
			            <colgroup>
							<col width="5%"/>
							<col />
			                <col width="8%"/>
							<col width="12%"/>
			                <col width="8%"/>
			                <col width="8%"/>
			                <col width="8%"/>
			                <col width="10%"/>
			                <col width="10%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>파일명</th>
									<th>확장자</th>
			                        <th>파일타입</th>
			                        <th>파일크기</th>
			                        <th>공개범위</th>
			                        <th>사용</th>
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
			                            <td><a href='/mng/fle/view.do?file_id=${result.file_id}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.org_file_nm}"/></a></td>
			                            <td class="a_center"><c:out value="${result.file_ext}"/></td>
			                            <td class="a_center">
			                            	<c:choose>
			                            		<c:when test="${result.file_type=='M'}">동영상</c:when>
			                            		<c:when test="${result.file_type=='I'}">이미지</c:when>
			                            		<c:when test="${result.file_type=='A'}">오디오</c:when>
			                            		<c:when test="${result.file_type=='T'}">텍스트</c:when>
			                            		<c:when test="${result.file_type=='D'}">문서</c:when>
			                            		<c:when test="${result.file_type=='E'}">엑셀</c:when>
			                            		<c:otherwise>기타</c:otherwise>
			                            	</c:choose>
			                            	<c:if test="${result.txt_yn=='Y'}">, 안내</c:if>
			                            </td>
			                            <td class="a_right">
				                            <c:choose>
			                            		<c:when test="${result.file_size/1048 > 1048 && result.file_size/1048 < 1048576}">
			                            			<fmt:formatNumber value="${result.file_size/1048/1048}" maxFractionDigits="2"/>MB
			                            		</c:when>
			                            		<c:when test="${result.file_size/1048 >= 1048576}">
			                            			<fmt:formatNumber value="${result.file_size/1048/1048/1048}" maxFractionDigits="2"/>GB
			                            		</c:when>
			                            		<c:otherwise>
			                            			<fmt:formatNumber value="${result.file_size/1048}" maxFractionDigits="0"/>KB
			                            		</c:otherwise>
			                            	</c:choose>
			                            </td>
			                            <td class="a_center">
				                        	<c:choose>
			                            		<c:when test="${result.open_ran=='0'}">비공개</c:when>
			                            		<c:when test="${result.open_ran=='1'}">회사</c:when>
			                            		<c:when test="${result.open_ran=='9'}">전체</c:when>
			                            		<c:otherwise>기타</c:otherwise>
				                            </c:choose>	
			                            </td>
			                            <td class="a_center">
				                        	<c:choose>
			                            		<c:when test="${result.use_yn=='Y'}">사용</c:when>
			                            		<c:otherwise>중지</c:otherwise>
				                            </c:choose>	         	
			                            </td>
			                            <td class="a_center"><a title="${result.user_id} _ ${result.corp_nm}"><c:out value="${result.user_nm}"/></a></td>
			                            <td class="a_center"><c:out value="${result.reg_dt}"/></td>
			                        </tr>
			                        </c:forEach>
			                	</c:when>
			                	<c:otherwise>
			                        <tr>
			                            <td colspan="9" class="a_center">검색 결과가 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="page">
			            	${fnc:replace(listPage.pageSetHtml, crlf, br)}
			            </div>
			            <div class="btnR">
			            	<a class="button" title="등록" href='/mng/fle/regist.do?page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'>등록</a>
			            </div> 
					</form>
				</div>
