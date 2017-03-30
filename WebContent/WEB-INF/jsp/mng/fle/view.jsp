<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_file_id"/>
<c:if test="${ChkAuth}">
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.file_id.value==""){
		alert("${check_file_id}");
		return false;
	}

	f.action = "/mng/fle/modify.do";
	f.submit();
	return true;
}
</script>
</c:if>
				<div id="content">
					<div class="navi">홈 &gt; 파일관리 &gt; 파일관리</div>
					<form:form commandName="VoFileInfo" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="file_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="파일 정보">
			            <caption class="accessibility">파일 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>파일 아이디</th>
			                        <td>
			                        	${VoFileInfo.file_id}
			                        </td>
			                        <th>파일위치</th>
			                        <td>
			                        	${VoFileInfo.file_path}
			                        </td>
							    </tr>
							    <tr>
			                        <th>파일명</th>
			                        <td>
			                        	<a href="/common/downLoadFile.do?type=filemanage&file_id=${VoFileInfo.file_id}">${VoFileInfo.org_file_nm}</a>
			                        </td>
			                        <th>타입 및 확장자</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoFileInfo.file_type=='I'}">이미지</c:when>
		                            		<c:when test="${VoFileInfo.file_type=='T'}">문서</c:when>
		                            		<c:when test="${VoFileInfo.file_type=='E'}">엑셀</c:when>
		                            		<c:when test="${VoFileInfo.file_type=='M'}">동영상</c:when>
		                            		<c:when test="${VoFileInfo.file_type=='A'}">오디오</c:when>
		                            		<c:otherwise>미분류</c:otherwise>
			                            </c:choose>
			                            [ ${VoFileInfo.file_cont_type} ]
			                        	- ${VoFileInfo.file_ext}
			                        </td>
							    </tr>
							    <tr>
									<th>파일사이즈</th>
			                        <td>
		                            	<c:choose>
		                            		<c:when test="${VoFileInfo.file_size/1048 > 1048 && VoFileInfo.file_size/1048 < 1048576}">
		                            			<fmt:formatNumber value="${VoFileInfo.file_size/1048/1048}" maxFractionDigits="2"/>MB
		                            		</c:when>
		                            		<c:when test="${VoFileInfo.file_size/1048 >= 1048576}">
		                            			<fmt:formatNumber value="${VoFileInfo.file_size/1048/1048/1048}" maxFractionDigits="2"/>GB
		                            		</c:when>
		                            		<c:otherwise>
		                            			<fmt:formatNumber value="${VoFileInfo.file_size/1048}" maxFractionDigits="0"/>KB
		                            		</c:otherwise>
		                            	</c:choose>	                        
			                        </td>
			                        <th>공개범위</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoFileInfo.open_ran=='0'}">비공개</c:when>
		                            		<c:when test="${VoFileInfo.open_ran=='1'}">회사</c:when>
		                            		<c:when test="${VoFileInfo.open_ran=='9'}">전체</c:when>
		                            		<c:otherwise>기타</c:otherwise>
			                            </c:choose>
			                        </td>
							    </tr>
							    <tr>
									<th>사용여부</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoFileInfo.use_yn=='Y'}">사용</c:when>
		                            		<c:otherwise>중지</c:otherwise>
			                            </c:choose>
			                        </td>
			                        <th>삭제여부</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoFileInfo.del_yn=='Y'}">삭제</c:when>
		                            		<c:when test="${VoFileInfo.del_yn=='D'}">삭제대기</c:when>
		                            		<c:otherwise>해당없음</c:otherwise>
			                            </c:choose>
			                        </td>
							    </tr>
							    <tr>
									<th>회사명</th>
			                        <td>
			                        	${VoFileInfo.corp_nm}
			                        </td>
			                        <th>권한명</th>
			                        <td>
			                        	${VoFileInfo.auth_nm}
			                        </td>
							    </tr>
							    <tr>
									<th>아이디</th>
			                        <td>
			                        	${VoFileInfo.user_id}
			                        </td>
			                        <th>이름</th>
			                        <td>
			                        	${VoFileInfo.user_nm}
			                        </td>
							    </tr>
							    <tr>
									<th>최종수정자</th>
			                        <td>
			                        	${VoFileInfo.mod_id}
			                        </td>
			                        <th>최종수정일</th>
			                        <td>
			                        	${VoFileInfo.mod_dt}
			                        </td>
							    </tr>
							    <tr>
			                        <th>등록일</th>
			                        <td colspan="3">
			                        	${VoFileInfo.reg_dt}
			                        </td>
							    </tr>
							    <tr>
									<th>안내정보여부</th>
			                        <td colspan="3">
			                        	<c:choose>
		                            		<c:when test="${VoFileInfo.txt_yn == 'Y' && ChkAuth}"><span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModalResize('안내정보 보기','/cmm/sch/fileTxtView.do?type=txt&file_id=${VoFileInfo.file_id}',675,370,false);" title="안내정보 보기">안내정보 보기</a></span></c:when>
		                            		<c:when test="${VoFileInfo.txt_yn == 'Y' && !ChkAuth}">있음</c:when>
		                            		<c:when test="${VoFileInfo.txt_yn != 'Y'}">없음</c:when>
		                            		<c:otherwise>해당없음</c:otherwise>
			                            </c:choose>
			                        </td>
							    </tr>
			                </tbody>
			            </table>
						<c:if test="${not empty TxtInfoList}">
						<table class="plist" summary="안내 정보"
							style="display:<c:if test="${VoFileInfo.txt_yn!='Y'}">none</c:if>">
							<caption class="accessibility">안내 정보</caption>
							<colgroup>
								<col width="5%">
								<col width="">
								<col width="8%">
								<col width="8%">
								<col width="8%">
								<col width="8%">
								<col width="8%">
								<col width="8%">
								<col width="8%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>안내메세지</th>
									<th>글자크기</th>
									<th>글자두께</th>
									<th>아웃라인</th>
									<th>색상</th>
									<th>상단여백</th>
									<th>측면정렬</th>
									<th>측면여백</th>
								</tr>
							</thead>
							<tbody id="ifolist" class="scroll">
								<c:forEach var="result" items="${TxtInfoList}" varStatus="status">
								<tr>
									<td class="a_center">${result.ifo_seq}</td>
									<td>${result.ifo_msg}</td>
									<td class="a_center">
										<c:choose>
		                            		<c:when test="${result.ifo_size == 'B'}">가장크게</c:when>
		                            		<c:when test="${result.ifo_size == 'L'}">크게</c:when>
		                            		<c:when test="${result.ifo_size == 'M'}">보통</c:when>
		                            		<c:when test="${result.ifo_size == 'S'}">작게</c:when>
		                            		<c:otherwise>해당없음</c:otherwise>
			                            </c:choose>
									</td>
									<td class="a_center">
										<c:choose>
											<c:when test="${result.ifo_bold == 'G'}">일반</c:when>
		                            		<c:when test="${result.ifo_bold == 'B'}">두껍게</c:when>
		                            		<c:when test="${result.ifo_bold == 'I'}">이탤릭</c:when>
		                            		<c:otherwise>해당없음</c:otherwise>
			                            </c:choose>
									</td>
									<td class="a_center">
										${result.ifo_line_px}px
									</td>
									<td class="a_center" style="background:${result.ifo_clr}">${result.ifo_clr}</td>
									<td class="a_center">${result.ifo_upad}</td>
									<td class="a_center">
										<c:choose>
		                            		<c:when test="${result.ifo_aln == 'L'}">좌측</c:when>
		                            		<c:when test="${result.ifo_aln == 'C'}">중앙</c:when>
		                            		<c:when test="${result.ifo_aln == 'R'}">우측</c:when>
		                            		<c:otherwise>해당없음</c:otherwise>
			                            </c:choose>
									</td>
									<td class="a_center">${result.ifo_spad}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						</c:if>
						<br>
						<div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
			            		<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	</c:if>
			            	<a class="button" href="/mng/fle/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
