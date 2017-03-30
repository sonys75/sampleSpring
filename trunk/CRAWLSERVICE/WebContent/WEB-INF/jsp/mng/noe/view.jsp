<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_msg_id"/>
<c:if test="${ChkAuth}">
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.msg_id.value==""){
		alert("${check_msg_id}");
		return false;
	}

	f.action = "/mng/noe/regist.do";
	f.submit();
	return true;
}

function fncConvertDown(msg_id, stp_id){
	var f=document.F1;
	if(f.use_yn.value=="N"){
		alert("공지 상태가 중지중입니다.");
		return false;
	}
	
	if(!confirm("해당 셋탑을 미완료로 전환하시겠습니까?")){
		return false;
	}
	
	$.ajax({
		url : "/mng/noe/convertProc.do",
		data : {"msgId":msg_id,"stpId":stp_id},
	    type : "POST",
		success : function(data, textStatus) {
			if(data.status=="success"){
				alert(data.retMsg);
				document.getElementById(stp_id).innerText = "미완료";
			}else{
				alert(data.retMsg);
			}
		},
		error: function (jqXHR, status) {
			ajaxError();
        }
	});
	return false;
}
</script>
</c:if>
				<div id="content">
					<div class="navi">홈 &gt; 기기관리 &gt; 공지관리</div>
					<form:form commandName="VoMessage" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="msg_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
				        <form:hidden path="use_yn"/>
			            <table class="tbl" summary="공지 정보">
			            <caption class="accessibility">공지 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>공지제목</th>
			                        <td>
			                            <c:out value="${VoMessage.msg_title}"/>
			                        </td>
									<th>소유회사</th>
			                        <td>
			                            <c:out value="${VoMessage.corp_nm}"/>			        
							        </td>
			                    </tr>
			                    <tr>
			                    	<th>분류</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoMessage.msg_type=='G'}">일반</c:when>
		                            		<c:when test="${VoMessage.msg_type=='S'}">긴급</c:when>
		                            		<c:when test="${VoMessage.msg_type=='T'}">테스트</c:when>
		                            		<c:otherwise>기타</c:otherwise>
		                            	</c:choose>
			                        </td>	
									<th>공지시간</th>
			                        <td>
								       	<c:out value="${VoMessage.view_tm}"/>
								       	분
			                        </td>
			                    </tr>
		 	                    <tr>
			                    	<th>사용여부</th>
			                        <td colspan="3">
			                        	<c:choose>
		                            		<c:when test="${VoMessage.use_yn=='Y'}">사용</c:when>
		                            		<c:when test="${VoMessage.use_yn=='N'}"><span class="t_red">중지</span></c:when>
		                            		<c:when test="${VoMessage.use_yn=='S'}">대기</c:when>
		                            		<c:otherwise>기타</c:otherwise>
		                            	</c:choose>
			                        </td>	
			                    </tr>
			                    <tr>
									<th>셋탑목록</th>
			                        <td colspan="3">
										<table class="plist" summary="셋탑목록">
							            <caption class="accessibility">셋탑목록</caption>
							            <colgroup>
											<col width="5%"/>
											<col width="15%"/>
							                <col />
							                <col width="10%"/>
										</colgroup>
							            	<thead>
												<tr>
													<th>번호</th>
													<th>셋탑번호</th>
													<th>셋탑명</th>
													<th>다운로드</th>
												</tr>
											</thead>
							                <tbody>
							                <c:choose>
							                	<c:when test="${not empty MsgStpList}">
							                		<c:forEach var="result" items="${MsgStpList}" varStatus="status">
							                        <tr>
							                            <td class="a_center"><fmt:formatNumber value="${status.index + 1}"/></td>
							                            <td class="a_center"><c:out value="${result.stp_id}"/></td>
							                            <td><c:out value="${result.stp_title}"/></td>
							                            <td class="a_center" id="${result.stp_id}">
								                            <c:choose>
							                            		<c:when test="${result.down_yn=='Y' && ChkAuth}"><span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncConvertDown('${VoMessage.msg_id}','${result.stp_id}');" title="미완료변경">완료</a></span></c:when>
							                            		<c:when test="${result.down_yn=='Y' && !ChkAuth}">완료</c:when>
							                            		<c:otherwise>미완료</c:otherwise>
							                            	</c:choose>
							                            </td>
							                        </tr>
							                        </c:forEach>
							                	</c:when>
							                	<c:otherwise>
							                        <tr>
							                            <td colspan="4" class="a_center">등록된 셋탑이 없습니다.</td>
							                        </tr>	                	
							                	</c:otherwise>
							                </c:choose>
							                </tbody>
							            </table>
			                        </td>	                        
			                    </tr>
			                    <tr>
									<th>공지내용</th>
			                        <td colspan="3">
										<table class="plist" summary="공지내용">
							            <caption class="accessibility">공지내용</caption>
							            <colgroup>
											<col width="5%"/>
							                <col />
										</colgroup>
							            	<thead>
												<tr>
													<th>번호</th>
													<th>공지내용</th>
												</tr>
											</thead>
							                <tbody>
							                <c:choose>
							                	<c:when test="${not empty MsgInfoList}">
							                		<c:forEach var="result" items="${MsgInfoList}" varStatus="status">
							                        <tr>
							                            <td class="a_center"><fmt:formatNumber value="${status.index + 1}"/></td>
							                            <td><c:out value="${result.msg}"/></td>
							                        </tr>
							                        </c:forEach>
							                	</c:when>
							                	<c:otherwise>
							                        <tr>
							                            <td colspan="2" class="a_center">등록된 공지내용이 없습니다.</td>
							                        </tr>	                	
							                	</c:otherwise>
							                </c:choose>
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
			            	<a class="button" href="/mng/noe/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
