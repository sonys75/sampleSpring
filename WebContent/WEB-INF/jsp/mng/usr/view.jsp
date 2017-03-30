<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_user_id"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.user_id.value==""){
		alert("${check_user_id}");
		return false;
	}

	f.action = "/mng/usr/regist.do";
	f.submit();
	return true;
}

</script>
				<div id="content">
					<div class="navi">홈 &gt; 회원관리 &gt; 회원관리</div>
					<form:form commandName="VoUserInfo" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="user_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="회원 정보">
			            <caption class="accessibility">회원 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
			                    	<th>아이디</th>
			                        <td>
			                        	<c:out value="${VoUserInfo.user_id}"/>
			                        </td>
			                        <th>이름</th>
			                        <td>
			                        	<c:out value="${VoUserInfo.user_nm}"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>권한</th>
			                        <td>
										<c:out value="${VoUserInfo.auth_nm}"/>
			                        </td>
									<th>회사명</th>
			                        <td>
										<c:out value="${VoUserInfo.corp_nm}"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>이메일</th>
			                        <td colspan="3">
			                        	<c:out value="${VoUserInfo.user_email}"/>
			                        </td>
							    </tr>
								<tr>
									<th>휴대전화번호</th>
			                        <td>
			                        	<c:out value="${VoUserInfo.user_hp}"/>
			                        </td>
									<th>일반전화번호</th>
			                        <td>
			                        	<c:out value="${VoUserInfo.user_tel}"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>사용여부</th>
			                        <td>
			                            <c:choose>
			                            	<c:when test="${VoUserInfo.use_yn=='Y'}">
			                            		<span class="t_blue">사용중</span>
			                            	</c:when>
			                            	<c:when test="${VoUserInfo.use_yn=='N'}">
			                            		<span class="t_red">중지</span>
			                            	</c:when>
			                            	<c:otherwise>
			                            		기타
			                            	</c:otherwise>
			                            </c:choose>
			                        </td>
									<th>로그인실패</th>
			                        <td>
			                        	<c:out value="${VoUserInfo.access_fail_cnt}"/>
			                        </td>
			                    </tr>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	</c:if>
			            	<a class="button" href="/mng/usr/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
