<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

				<div id="content">
					<div class="navi">홈 &gt; 기기관리 &gt; 기기정보</div>
					<form name="F1" id="F1" method="post" action="/mng/eup/list.do" >
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td class="a_right">
		                        	정렬 :
		                        	<select name="ord_fld" id="ord_fld">
		                        		<option value="REG_DT"<c:if test="${VoComm.ord_fld=='REG_DT'}"> selected</c:if>>등록일</option>
		                        		<option value="LAST_CONN_DT"<c:if test="${VoComm.ord_fld=='LAST_CONN_DT'}"> selected</c:if>>최종접속일</option>
		                        		<option value="STP_TITLE"<c:if test="${VoComm.ord_fld=='STP_TITLE'}"> selected</c:if>>기기명칭</option>
		                        		<option value="CORP_ID"<c:if test="${VoComm.ord_fld=='CORP_ID'}"> selected</c:if>>소유회사</option>
		                        	</select>
		                        	<select name="ord_sort" id="ord_sort">
		                        		<option value="DESC"<c:if test="${VoComm.ord_sort=='DESC'}"> selected</c:if>>역순서</option>
		                        		<option value="ASC"<c:if test="${VoComm.ord_sort=='ASC'}"> selected</c:if>>정순서</option>
		                        	</select>
		                        	, 검색 :
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="STP_TITLE"<c:if test="${VoComm.sch_fld=='STP_TITLE'}"> selected</c:if>>기기명칭</option>
		                        		<option value="CORP_ID"<c:if test="${VoComm.sch_fld=='CORP_ID'}"> selected</c:if>>소유회사</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}"/>
		                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncSearch();" title="검색">검색</a></span>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->					
						<div class="count">총 <b><fmt:formatNumber value="${listPage.totalCount}"/></b>건의 검색결과가 있습니다.</div>
			            <table class="tbl" summary="재생 목록입니다.">
			            <caption class="accessibility">재생 목록</caption>
			            <colgroup>
							<col width="5%"/>
							<col />
			                <col width="10%"/>
							<col width="10%"/>
			                <col width="20%"/>
			                <col width="10%"/>
			                <col width="10%"/>
			                <col width="10%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>기기명칭</th>
									<th>설치유형</th>
									<th>소유회사</th>
									<th>접속시간</th>
									<th>사용여부</th>
									<th>설치일</th>
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
				                            <c:choose>
			                            		<c:when test="${not empty result.stp_title}">
			                            			<a href='/mng/eup/view.do?stp_id=${result.stp_id}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.stp_title}"/></a>
			                            		</c:when>
			                            		<c:otherwise>
			                            			<a href='/mng/eup/view.do?stp_id=${result.stp_id}&page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'><c:out value="${result.stp_id}"/></a>
			                            		</c:otherwise>
			                            	</c:choose>
			                            </td>
			                            <td class="a_center">
				                            <c:choose>
			                            		<c:when test="${result.stp_type=='R'}">임대</c:when>
			                            		<c:when test="${result.stp_type=='S'}">판매</c:when>
			                            		<c:when test="${result.stp_type=='T'}">테스트</c:when>
			                            		<c:otherwise><span class="t_red"><b>대기중</b></span></c:otherwise>
			                            	</c:choose>
			                            </td>
			                            <td class="a_center"><c:out value="${result.corp_nm}"/></td>
			                            <td class="a_center">
											<c:choose>
				                            	<c:when test="${empty result.min_diff}">
				                            		
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 0 && result.min_diff < 60}">
				                            		<c:out value="${result.last_conn_dt}"/>
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 60 && result.min_diff < 720}">
				                            		<span class="t_blue"><c:out value="${result.last_conn_dt}"/></span>
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 720 && result.min_diff < 1440}">
				                            		<span class="t_blue"><b><c:out value="${result.last_conn_dt}"/></b></span>
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 1440 && result.min_diff < 10080}">
				                            		<span class="t_red"><c:out value="${result.last_conn_dt}"/></span>
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 10080}">
				                            		<span class="t_red"><b><c:out value="${result.last_conn_dt}"/></b></span>
				                            	</c:when>
				                            </c:choose>
			                            </td>
			                            <td class="a_center">
				                        	<c:choose>
			                            		<c:when test="${result.use_yn=='Y'}">사용중</c:when>
			                            		<c:otherwise>사용중지</c:otherwise>
				                            </c:choose>	                            
			                            </td>
			                            <td class="a_center"><c:out value="${result.stp_set_dt}"/></td>
			                            <td class="a_center"><c:out value="${result.reg_dt}"/></td>
			                        </tr>
			                        </c:forEach>
			                	</c:when>
			                	<c:otherwise>
			                        <tr>
			                            <td colspan="8" class="a_center">검색 결과가 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="page">
			            	${fnc:replace(listPage.pageSetHtml, crlf, br)}
			            </div>
			            <div class="btnR">
			            	<a class="button" title="등록" href='/mng/eup/regist.do?page_no=${listPage.currentPage}<c:if test="${not empty listPage.commonPara}">&${listPage.commonPara}</c:if>'>등록</a>
			            </div> 
					</form>
				</div>
