<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_stp_id"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.stp_id.value==""){
		alert("${check_stp_id}");
		return false;
	}

	f.action = "/mng/eup/regist.do";
	f.submit();
	return true;
}
</script>
				<div id="content">
					<div class="navi">홈 &gt; 기기관리 &gt; 기기정보</div>
					<form:form commandName="VoStpInfo" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="stp_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="기기 정보">
			            <caption class="accessibility">기기 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th><a title="<c:out value="${VoStpInfo.stp_id}"/>">기기명칭</a></th>
			                        <td>
		                            	<c:choose>
		                            		<c:when test="${not empty VoStpInfo.stp_title}">
		                            			<c:out value="${VoStpInfo.stp_title}"/>
		                            		</c:when>
		                            		<c:otherwise>
		                            			명칭이 등록되지 않았습니다.
		                            		</c:otherwise>
		                            	</c:choose>
			                        </td>
									<th>소유회사</th>
			                        <td>
			                            <c:out value="${VoStpInfo.corp_nm}"/>			        
							        </td>
			                    </tr>
			                    <tr>
			                        <th>설치일</th>
			                        <td>
			                        	<c:out value="${VoStpInfo.stp_set_dt}"/>
			                        </td>
									<th>OS / APP Ver</th>
			                        <td>
			                        	<c:out value="${VoStpInfo.stp_os}"/> <c:if test="${not empty VoStpInfo.stp_app_ver}">/ <c:out value="${VoStpInfo.stp_app_ver}"/></c:if>
			                        </td>
			                    </tr>
			                    <tr>
									<th>설치유형</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoStpInfo.stp_type=='R'}">임대</c:when>
		                            		<c:when test="${VoStpInfo.stp_type=='S'}">판매</c:when>
		                            		<c:when test="${VoStpInfo.stp_type=='T'}">테스트</c:when>
		                            		<c:otherwise><span class="t_red"><b>대기중</b></span></c:otherwise>
		                            	</c:choose>
			                        </td>
			                        <th>모니터 배치</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoStpInfo.mnt_type=='0'}">가로</c:when>
		                            		<c:when test="${VoStpInfo.mnt_type=='180'}">역가로</c:when>
		                            		<c:when test="${VoStpInfo.mnt_type=='90'}">세로</c:when>
		                            		<c:when test="${VoStpInfo.mnt_type=='270'}">역세로</c:when>
		                            		<c:otherwise>기타</c:otherwise>
		                            	</c:choose>
			                        </td>	
			                    </tr>
			                    <tr>
			                    	<th>사용여부</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoStpInfo.use_yn=='Y'}">사용</c:when>
		                            		<c:when test="${VoStpInfo.use_yn=='N'}">중지</c:when>
		                            		<c:when test="${VoStpInfo.use_yn=='S'}">대기</c:when>
		                            		<c:otherwise>기타</c:otherwise>
		                            	</c:choose>
			                        </td>
									<th>접속시간</th>
			                        <td>
			                        	<c:choose>
			                            	<c:when test="${empty VoStpInfo.min_diff}">
			                            		
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 0 && VoStpInfo.min_diff < 60}">
			                            		<c:out value="${VoStpInfo.last_conn_dt}"/>
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 60 && VoStpInfo.min_diff < 720}">
			                            		<span class="t_blue"><c:out value="${VoStpInfo.last_conn_dt}"/></span>
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 720 && VoStpInfo.min_diff < 1440}">
			                            		<span class="t_blue"><b><c:out value="${VoStpInfo.last_conn_dt}"/></b></span>
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 1440 && VoStpInfo.min_diff < 10080}">
			                            		<span class="t_red"><c:out value="${VoStpInfo.last_conn_dt}"/></span>
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 10080}">
			                            		<span class="t_red"><b><c:out value="${VoStpInfo.last_conn_dt}"/></b></span>
			                            	</c:when>
			                            </c:choose>
			                        </td>
			                    </tr>
			                    <tr>
			                    	<th>자동종료</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoStpInfo.auto_off_yn=='Y'}">사용</c:when>
		                            		<c:otherwise>중지</c:otherwise>
		                            	</c:choose>
			                        </td>	
									<th>종료시간</th>
			                        <td>
			                        	<c:set var="hh" value="${fnc:substring(VoStpInfo.auto_off_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.auto_off_tm,2,4)}"/>
			                        	${hh}:${mm}
			                        </td>
			                    </tr>
			                    <tr>
			                    	<th>주요공지</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoStpInfo.noti_yn=='Y'}">사용</c:when>
		                            		<c:otherwise>중지</c:otherwise>
		                            	</c:choose>
			                        </td>	
									<th>주요공지시간</th>
			                        <td>
			                        	<c:set var="hh" value="${fnc:substring(VoStpInfo.noti_start_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.noti_start_tm,2,4)}"/>
			                        	${hh}:${mm}
			                        	~
			                        	<c:set var="hh" value="${fnc:substring(VoStpInfo.noti_end_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.noti_end_tm,2,4)}"/>
			                        	${hh}:${mm}
			                        </td>
			                    </tr>
			                    <tr>
			                    	<th>뉴스자막</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoStpInfo.news_yn=='Y'}">사용</c:when>
		                            		<c:otherwise>중지</c:otherwise>
		                            	</c:choose>
			                        </td>	
									<th>뉴스시간</th>
			                        <td>
			                        	<c:set var="hh" value="${fnc:substring(VoStpInfo.news_start_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.news_start_tm,2,4)}"/>
			                        	${hh}:${mm}
			                        	~
			                        	<c:set var="hh" value="${fnc:substring(VoStpInfo.news_end_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.news_end_tm,2,4)}"/>
			                        	${hh}:${mm}
			                        </td>
			                    </tr>
			                    <tr>
			                    	<th>자막크기</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoStpInfo.noti_size=='L'}">대</c:when>
		                            		<c:when test="${VoStpInfo.noti_size=='M'}">중</c:when>
		                            		<c:when test="${VoStpInfo.noti_size=='S'}">소</c:when>
		                            		<c:otherwise>소</c:otherwise>
		                            	</c:choose>
			                        </td>
			                        <th>원격아이디</th>
			                        <td>
			                        	<c:out value="${VoStpInfo.remote_id}"/>
			                        </td>
			                    </tr>
			                    <tr>
			                    	<th>CCTV</th>
			                        <td>
										<c:choose>
											<c:when test="${VoStpInfo.cctv_yn == 'Y'}">사용</c:when>
											<c:otherwise>미사용</c:otherwise>
										</c:choose>
			                        </td>
			                        <th>번호호출기</th>
			                        <td>
			                        	<c:choose>
											<c:when test="${empty VoStpInfo.call_cd}">없음</c:when>
											<c:otherwise>
												<c:forEach items="${codeList}" var="codeList">
													<c:if test="${codeList.code == VoStpInfo.call_cd}">
														${codeList.code_nm}
													</c:if>
												</c:forEach>
											</c:otherwise>
										</c:choose>
			                        </td>                       
			                    </tr>
			                    <c:if test="${not empty VoStpInfo.call_cd}">
			                    <tr>
									<th>호출기정보</th>
			                        <td colspan="3">
			                        	그룹번호 : <c:out value="${VoStpInfo.call_grp}"/>, 
										업체번호 : <c:out value="${VoStpInfo.call_prd}"/>, 
										번호자리수 : <c:out value="${VoStpInfo.call_num}"/>,
										안내종류 : 
										<c:forEach items="${soundCode}" var="soundCode">
											<c:if test="${soundCode.code == VoStpInfo.sound_type}">
												${soundCode.code_nm} ( ${soundCode.code_desc} )
											</c:if>
										</c:forEach>
			                        </td>	                        
			                    </tr>
			                    </c:if>
			                    <tr>
									<th>재생정보</th>
			                        <td colspan="3">
										<table class="plist" summary="재생정보">
							            <caption class="accessibility">재생정보</caption>
							            <colgroup>
											<col width="5%"/>
							                <col />
											<col width="15%"/>
											<col width="15%"/>
										</colgroup>
							            	<thead>
												<tr>
													<th>번호</th>
													<th>제목</th>
							                        <th>파일사이즈</th>
							                        <th>재생시간</th>
												</tr>
											</thead>
							                <tbody>
							                <c:choose>
							                	<c:when test="${not empty playList}">
		
							                		<c:forEach var="result" items="${playList}" varStatus="status">
							                		<c:set var="ply_ver_id" value="${result.ply_ver_id}" />
													<c:set var="ply_seq" value="${result.ply_seq}" />
													<c:set var="ply_title" value="${result.ply_title}" />
													
													<c:set var="shh" value="${fnc:substring(result.ply_start_tm,0,2)}"/>
													<c:set var="smm" value="${fnc:substring(result.ply_start_tm,2,4)}"/>
													<c:set var="ehh" value="${fnc:substring(result.ply_end_tm,0,2)}"/>
													<c:set var="emm" value="${fnc:substring(result.ply_end_tm,2,4)}"/>
							                        <tr>
							                            <td class="a_center"><fmt:formatNumber value="${status.index + 1}"/></td>
							                            <td><c:out value="${result.ply_title}"/></td>
							                            <td class="a_right">
								                            <c:choose>
							                            		<c:when test="${result.ply_size/1048 > 1048 && result.ply_size/1048 < 1048576}">
							                            			<fmt:formatNumber value="${result.ply_size/1048/1048}" maxFractionDigits="2"/>MB
							                            		</c:when>
							                            		<c:when test="${result.ply_size/1048 >= 1048576}">
							                            			<fmt:formatNumber value="${result.ply_size/1048/1048/1048}" maxFractionDigits="2"/>GB
							                            		</c:when>
							                            		<c:otherwise>
							                            			<fmt:formatNumber value="${result.ply_size/1048}" maxFractionDigits="0"/>KB
							                            		</c:otherwise>
							                            	</c:choose>
							                            </td>
							                            <td class="a_center">${shh}:${smm} ~ ${ehh}:${emm}</td>
							                        </tr>
							                        </c:forEach>
							                	</c:when>
							                	<c:otherwise>
							                        <tr>
							                            <td colspan="4" class="a_center">등록된 재생정보가 없습니다.</td>
							                        </tr>	                	
							                	</c:otherwise>
							                </c:choose>
							                </tbody>
							            </table>
			                        </td>	                        
			                    </tr>
			                </tbody>
			            </table>
						
						<table class="tbl" summary="기기 목록입니다.">
			            <caption class="accessibility">기기 목록</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="15%"/>
			                <col />
			                <col width="20%"/>
							<col width="10%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>타입</th>
									<th>맥주소</th>
			                        <th>접속시간</th>
			                        <th>사용여부</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty macList}">
			                		<c:forEach var="result" items="${macList}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${status.index + 1}"/></td>
			                            <td class="a_center"><c:out value="${result.mac_type}"/></td>
			                            <td><c:out value="${result.mac_adr}"/></td>
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
			                            <td colspan="5" class="a_center">등록된 맥어드레스가 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	</c:if>
			            	<a class="button" href="/mng/eup/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
