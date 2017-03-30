<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_ply_ver_id"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.ply_ver_id.value==""){
		alert("${check_ply_ver_id}");
		return false;
	}

	f.action = "/mng/ply/regist.do";
	f.submit();
	return true;
}
function fncSkinView(ply_ver_id){
	document.getElementById("skinDiv").innerHTML="<img src=\"/common/downLoadFile.do?type=plyskin&ply_ver_id="+ply_ver_id+"\" width=\"100%\" onclick=\"fncCloseView();\" title=\"닫기\"/>";
}

function fncFileView(file_id){
	document.getElementById("skinDiv").innerHTML="<img src=\"/common/downLoadFile.do?type=filemanage&file_id="+file_id+"\" width=\"100%\" onclick=\"fncCloseView();\" title=\"닫기\"/>";
}
function fncCloseView(){
	document.getElementById("skinDiv").innerHTML="";
}
</script>
				<div id="content">
					<div class="navi">홈 &gt; 파일관리 &gt; 재생목록관리</div>
					<form:form commandName="VoPlayVer" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="ply_ver_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="재생 목록 정보">
			            <caption class="accessibility">재생 목록 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>제목</th>
			                        <td class="a_left">
			                        	${VoPlayVer.ply_title}
			                        </td>
			                        <th>배포아이디</th>
			                        <td class="a_left">
			                        	${VoPlayVer.user_id}
			                        </td>
							    </tr>
							    <tr>
			                        <th>배포상태</th>
			                        <td class="a_left">
		                            	<c:choose>
		                            		<c:when test="${VoPlayVer.dist_stat=='I'}">배포중</c:when>
		                            		<c:when test="${VoPlayVer.dist_stat=='D'}">배포완료</c:when>
		                            		<c:when test="${VoPlayVer.dist_stat=='S'}">배포대기</c:when>
		                            		<c:otherwise>미분류</c:otherwise>
		                            	</c:choose>
			                        </td>
			                        <th>사용여부</th>
			                        <td class="a_left">
		                            	<c:choose>
		                            		<c:when test="${VoPlayVer.use_yn=='Y'}">사용중</c:when>
			                            </c:choose>	                          
			                        </td>
							    </tr>
							    <tr>
			                        <th>스킨</th>
			                        <td colspan="3" class="a_left">
			                        	<c:if test="${not empty VoPlayVer.skin_org_file_nm}">
											<a href="/common/downLoadFile.do?type=plyskin&ply_ver_id=${VoPlayVer.ply_ver_id}">${VoPlayVer.skin_org_file_nm}</a>
											<span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncSkinView('${VoPlayVer.ply_ver_id}');" title="보기">보기</a></span>
										</c:if>
										<div id="skinDiv" style="background-color:#d3d3d3;"></div>
			                        </td>
							    </tr>
							    <tr>
			                        <th>재생목록</th>
			                        <td colspan="3">
			                        	<table class="plist" style="table-layout:fixed">
								            <colgroup>
								            	<col width="5%">
								            	<col width="8%">
								                <col width="">
								                <col width="8%">
								                <col width="8%">
								                <col width="8%">
								                <col width="8%">
								                <col width="15%">
								            </colgroup>
								            <thead>
								            <tr>
								            	<th>순서</th>
								            	<th>재생시간</th>
								                <th>파일명</th>
								                <th>스킨</th>
								                <th>동작</th>
								                <th>동작값1</th>
								                <th>동작값2</th>
								                <th>파일타입</th>
								            </tr>
								            </thead>
								            <tbody>
								            <c:forEach var="playfile" items="${playFile}" varStatus="status">
									            <tr>
									            	<td class="a_center">${status.index+1}</td>
									            	<td class="a_right">
									            		<c:choose>
															<c:when test="${playfile.file_type=='M' || playfile.file_type=='A'}">-</c:when>
														    <c:otherwise>${playfile.ply_tm}초</c:otherwise>
													    </c:choose>	
									            	</td>
									            	<td style="word-break:break-all;"><c:if test="${playfile.txt_yn == 'Y'}">(안내) </c:if>${playfile.org_file_nm}</td>
									            	<td class="a_center">
												        <c:choose>
															<c:when test="${playfile.skin_yn=='Y'}">사용</c:when>
														    <c:otherwise>없음</c:otherwise>
													    </c:choose>							            	
									            	</td>
									            	<td class="a_center">
												        <c:choose>
															<c:when test="${playfile.ani_type=='ZOOM'}">확대</c:when>
														    <c:otherwise>없음</c:otherwise>
													    </c:choose>							            	
									            	</td>
									            	<td class="a_center">
									            		<c:out value="${playfile.ani_val1}"></c:out>
									            	</td>
									            	<td class="a_center">
									            		<c:out value="${playfile.ani_val2}"></c:out>
									            	</td>
									            	<td class="a_center">
												        <c:choose>
															<c:when test="${playfile.file_type=='M'}">동영상</c:when>
														    <c:when test="${playfile.file_type=='I' && playfile.txt_yn == 'Y'}">
															    <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModalResize('안내정보 보기','/cmm/sch/fileTxtView.do?type=txt&file_id=${playfile.file_id}',675,370,false);" title="안내정보 보기">안내정보 보기</a></span>
														    </c:when>
														    <c:when test="${playfile.file_type=='I' && playfile.txt_yn != 'Y'}">
															    <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModalResize('이미지 보기','/cmm/sch/fileTxtView.do?type=image&file_id=${playfile.file_id}',675,370,false);" title="이미지 보기">이미지 보기</a></span>
														    </c:when>
														    <c:when test="${playfile.file_type=='A'}">오디오</c:when>
														    <c:when test="${playfile.file_type=='T'}">텍스트</c:when>
														    <c:when test="${playfile.file_type=='D'}">문서</c:when>
														    <c:when test="${playfile.file_type=='E'}">엑셀</c:when>
														    <c:when test="${playfile.file_type=='X'}">기타</c:when>
														    <c:otherwise></c:otherwise>
													    </c:choose>							            	
									            	</td>
									            </tr>
											</c:forEach>
								            </tbody>
							            </table>
			                        </td>
							    </tr>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	</c:if>
			            	<a class="button" href="/mng/ply/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
