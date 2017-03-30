<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.required_l" arguments="변경 비밀번호" var="re_user_pw_required"/>
<spring:message code="Check.field.min" arguments="4" var="mins"/>
<spring:message code="Check.field.max" arguments="20" var="maxs"/>
<spring:message code="Check.field.range_l" arguments="변경 비밀번호,4,20" var="user_pw_range"/>
<spring:message code="Check.field.required_u" arguments="변경 비밀번호 확인" var="re_user_pw_confirm_required"/>
<spring:message code="Check.field.compare" arguments="변경 비밀번호" var="re_user_pw_compare"/>
<spring:message code="Check.field.required_l" arguments="이메일 아이디" var="email_id_required"/>
<spring:message code="Check.field.required_l" arguments="이메일 서버" var="email_host_required"/>
<spring:message code="Check.field.wrong_u" arguments="이메일" var="email_host_wrong"/>
<spring:message code="Check.field.required_l" arguments="휴대전화번호" var="user_hp_required"/>
<spring:message code="Check.field.wrong_l" arguments="휴대전화번호" var="user_hp_wrong"/>
<spring:message code="Check.field.required_l" arguments="일반전화번호" var="user_tel_required"/>
<spring:message code="Check.field.wrong_l" arguments="일반전화번호" var="user_tel_wrong"/>
<spring:message code="Check.field.required_l" arguments="현재 비밀번호" var="user_pw_required"/>
<script type="text/javascript">
$(document).ready(function() {
	fncChangeMailHost();
});
function fncChangeMailHost(){
	var f=document.F1;
	var sel = f.email_select;
	var email_host = f.email_host;

	// 직접 입력일 경우
	if(sel.options.selectedIndex == 0){
		//email_host.value="";
		email_host.readOnly = false;
	}else{
		email_host.value=sel.options[sel.options.selectedIndex].value;
		email_host.readOnly = true;
	}
}
function fncSave(){
	var f=document.F1;

	if(f.re_user_pw.value!="" || f.re_user_pw_confirm.value!=""){	//변경 비밀번호 필드가 하나라도 입력되어 있을 경우
		if(f.re_user_pw.value==""){
			alert("${re_user_pw_required}");
			f.re_user_pw.focus();
			return false;
		}
		if(f.re_user_pw.value.length < "${mins}" || f.re_user_pw.value.length > "${maxs}"){
			alert("${user_pw_range}");
			f.re_user_pw.focus();
			return false;
		}
		if(f.re_user_pw_confirm.value==""){
			alert("${re_user_pw_confirm_required}");
			f.re_user_pw_confirm.focus();
			return false;
		}
		if(f.re_user_pw.value != f.re_user_pw_confirm.value){
			alert("${re_user_pw_compare}");
			f.re_user_pw_confirm.focus();
			return false;
		}
	}

	if(f.email_id.value==""){
		alert("${email_id_required}");
		f.email_id.focus();
		return false;
	}
	
	if(f.email_host.value==""){
		alert("${email_host_required}");
		f.email_host.focus();
		return false;
	}
	
	if(!ChkEmail(f.email_id.value+"@"+f.email_host.value)){
		alert("${email_host_wrong}");
		f.email_host.focus();
		return false;
	}
	
	if(f.user_hp.value=="" || !fncChkPhone(f.user_hp)){
		alert("${user_hp_required}");
		f.user_hp.focus();
		return false;
	}
	
	if(!fncValidateCellPhone(f.user_hp.value)){
		alert("${user_hp_wrong}");
		f.user_hp.focus();
		return false;
	}
	/* 
	if(f.user_tel.value=="" || !fncChkPhone(f.user_tel)){
		alert("${user_tel_required}");
		f.user_tel.focus();
		return false;
	}
	
	if(!fncValidateTelePhone(f.user_tel.value)){
		alert("${user_tel_wrong}");
		f.user_tel.focus();
		return false;
	}
	 */
	if(f.user_pw.value==""){
		alert("${user_pw_required}");
		f.user_pw.focus();
		return false;
	}

	f.target = "HiddenFrame";
	f.action = "/usr/edt/mypageProc.do";
	f.submit();
	f.target = "_self";
	f.action = "/usr/edt/mypage.do";
	return true;
	
}
</script>
				<div id="content">
					<form:form commandName="VoUserInfo" name="F1" id="F1" method="post" onsubmit="return fncSave();">
			        	<c:if test="${not empty VoCorpInfo}">
			            <table class="tbl" summary="회사 정보">
			            <caption class="accessibility">회사 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>로고파일</th>
			                        <td colspan="3" class="a_left">
			                        	<c:choose>
			                        		<c:when test="${not empty VoCorpInfo.corp_logo_nm}">
			                        			<img src="${VoCorpInfo.corp_logo_path}/${VoCorpInfo.corp_logo_nm}" width="200" height="45"/>
			                        		</c:when>
			                        		<c:otherwise>
			                        			회사 로고가 등록되지 않았습니다.
			                        		</c:otherwise>
			                        	</c:choose>
			                        </td>
			                    </tr>
			                    <tr>
									<th>회사명</th>
			                        <td class="a_left">
										<c:out value="${VoCorpInfo.corp_nm}"/>
			                        </td>
									<th>회사아이디</th>
			                        <td class="a_left">
										<c:out value="${VoCorpInfo.corp_id}"/>
			                        </td>
			                    </tr>
								<tr>
									<th>전화번호</th>
			                        <td class="a_left">
			                        	<c:out value="${VoCorpInfo.corp_tel}"/>
			                        </td>
									<th>홈페이지</th>
			                        <td class="a_left">
			                        	<c:out value="${VoCorpInfo.corp_url}"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>주소</th>
			                        <td colspan="3" class="a_left">
			                        	<c:out value="${VoCorpInfo.corp_post}"/>
			                        	<c:out value="${VoCorpInfo.corp_adr}"/>
			                        	<c:out value="${VoCorpInfo.corp_adr_dsc}"/>
			                        </td>
							    </tr>
			                </tbody>
			            </table>
			            </c:if> 
			            
			            <table class="tbl" summary="개인 정보 수정">
			            <caption class="accessibility">개인 정보 수정 내용</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>권한</th>
			                        <td class="a_left">
		                            	<a title="${VoUserInfo.auth_id}">${VoUserInfo.auth_nm}</a>
			                        </td>
									<th>사용여부</th>
			                        <td class="a_left">
			                            <c:choose>
		                            		<c:when test="${VoUserInfo.use_yn=='Y'}">사용</c:when>
		                            		<c:when test="${VoUserInfo.use_yn=='N'}">중지</c:when>
		                            		<c:when test="${VoUserInfo.use_yn=='S'}">대기</c:when>
		                            		<c:otherwise>기타</c:otherwise>
		                            	</c:choose>				        
							        </td>
			                    </tr>
								<tr>
									<th>이름</th>
			                        <td class="a_left">
			                        	<c:out value="${VoUserInfo.user_nm}"/>
			                        </td>
									<th>아이디</th>
			                        <td class="a_left">
			                        	<c:out value="${VoUserInfo.user_id}"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>변경 비밀번호</th>
			                        <td class="a_left">
			                        	<form:password path="re_user_pw" maxlength="20"/><br/>
			                        	<font color="red">※ 비밀번호를 변경할 경우에 입력해 주세요.</font>	                            
			                        </td>
									<th>변경 비밀번호 확인</th>
			                        <td class="a_left">
			                        	<form:password path="re_user_pw_confirm" maxlength="20"/><br/>
			                            <font color="red">※ 비밀번호를 변경할 경우에 입력해 주세요.</font>
			                        </td>	                        
			                    </tr>
			                    <tr>
									<th>이메일</th>
			                        <td colspan="3" class="a_left">
			                        	<c:choose>
			                        		<c:when test="${fnc:indexOf(VoUserInfo.user_email, '@') > 0}">
			                        			<c:set var="email_id" value="${fnc:substring(VoUserInfo.user_email, 0, fnc:indexOf(VoUserInfo.user_email, '@'))}"/>
			                        			<c:set var="email_host" value="${fnc:substring(VoUserInfo.user_email, fnc:indexOf(VoUserInfo.user_email, '@')+1, fnc:length(VoUserInfo.user_email))}"/>
			                        		</c:when>
			                        		<c:otherwise>
			                        			<c:set var="email_id" value=""/>
			                        			<c:set var="email_host" value=""/>
			                        		</c:otherwise>
			                        	</c:choose>
		
			                        	<form:input path="email_id" title="이메일 아이디" value="${email_id}" cssStyle="ime-mode:disabled;" onkeyup="javascript:fncChkByte(this, 100);"/>
								    	@
								    	<form:input path="email_host" title="이메일 도메인" value="${email_host}" cssStyle="ime-mode:disabled;" onkeyup="javascript:fncChkByte(this, 100);" readonly="true"/>
										<select name="email_select" id="email_select" onchange="fncChangeMailHost();" title="이메일 선택">
											<option value="direct"<c:if test="${email_host==''}"> selected="selected"</c:if>>직접입력</option>
											<option value="naver.com"<c:if test="${email_host=='naver.com'}"> selected="selected"</c:if>>naver.com</option>
											<option value="daum.net"<c:if test="${email_host=='daum.net'}"> selected="selected"</c:if>>daum.net</option>
											<option value="hotmail.com"<c:if test="${email_host=='hotmail.com'}"> selected="selected"</c:if>>hotmail.com</option>
											<option value="nate.com"<c:if test="${email_host=='nate.com'}"> selected="selected"</c:if>>nate.com</option>
											<option value="yahoo.co.kr"<c:if test="${email_host=='yahoo.co.kr'}"> selected="selected"</c:if>>yahoo.co.kr</option>
											<option value="paran.com"<c:if test="${email_host=='paran.com'}"> selected="selected"</c:if>>paran.com</option>
											<option value="empas.com"<c:if test="${email_host=='empas.com'}"> selected="selected"</c:if>>empas.com</option>
											<option value="dreamwiz.com"<c:if test="${email_host=='dreamwiz.com'}"> selected="selected"</c:if>>dreamwiz.com</option>
											<option value="freechal.com"<c:if test="${email_host=='freechal.com'}"> selected="selected"</c:if>>freechal.com</option>
											<option value="lycos.co.kr"<c:if test="${email_host=='lycos.co.kr'}"> selected="selected"</c:if>>lycos.co.kr</option>
											<option value="korea.com"<c:if test="${email_host=='korea.com'}"> selected="selected"</c:if>>korea.com</option>
											<option value="gmail.com"<c:if test="${email_host=='gmail.com'}"> selected="selected"</c:if>>gmail.com</option>
											<option value="hanmir.com"<c:if test="${email_host=='hanmir.com'}"> selected="selected"</c:if>>hanmir.com</option>
										</select><br/>
										<!-- span class="red">※ 비밀번호 찾기시에 등록된 이메일 계정을 사용합니다. 정확한 이메일 계정을 입력하여 주십시오.</span -->
			                        </td>
							    </tr>
							    <tr>
									<th>휴대전화번호</th>
			                        <td class="a_left">
			                        	<form:input path="user_hp" maxlength="15"/>        
			                        	* 예)010-0000-0000                    
			                        </td>
									<th>일반전화번호</th>
			                        <td class="a_left">
			                        	<form:input path="user_tel" maxlength="15"/>
			                        	* 예)02-0000-0000 
			                        </td>	                        
			                    </tr>
							    <tr>
									<th>현재 비밀번호</th>
			                        <td colspan="3" class="a_left">
			                        	<form:password path="user_pw"/><br/>
			                            <font color="red">※ 입력된 정보 수정을 위해서는 현재 비밀번호를 입력하셔야 합니다.</font>
			                        </td>
							    </tr>
			                </tbody>
			            </table>

			            <div class="buttons">
							<a class="button" style="cursor:pointer" onclick="fncSave();" title="저장">저장</a>
							<a class="button" style="cursor:pointer" href="/" title="취소">취소</a>
						</div>
			        </form:form>				
				</div>