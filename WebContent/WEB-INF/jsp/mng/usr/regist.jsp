<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.min" arguments="4" var="mins"/>
<spring:message code="Check.field.max" arguments="20" var="maxs"/>
<spring:message code="Check.field.range_l" arguments="아이디,4,20" var="user_id_range"/>
<spring:message code="User.id.check.unique.languege" var="user_id_languege_check"/>
<spring:message code="User.id.check.unique.yes" var="UniqIDYes"/>
<spring:message code="User.id.check.unique.no" var="UniqIDNo"/>
<spring:message code="User.id.check.unique.required" var="UniqIDCheck"/>
<spring:message code="User.id.check.unique.compare" var="user_id_unique_compare"/>
<spring:message code="Check.field.required_u" arguments="이름" var="user_nm_required"/>
<spring:message code="Check.field.required_l" arguments="비밀번호" var="user_pw_required"/>
<spring:message code="Check.field.required_l" arguments="비밀번호" var="re_user_pw_required"/>
<spring:message code="Check.field.range_l" arguments="비밀번호,4,20" var="user_pw_range"/>
<spring:message code="Check.field.required_u" arguments="비밀번호 확인" var="re_user_pw_confirm_required"/>
<spring:message code="Check.field.compare" arguments="비밀번호" var="re_user_pw_compare"/>
<spring:message code="Common.fail.script.Exception" var="failException"/>
<spring:message code="Check.field.hidden.empty" var="user_id_empty"/>

<spring:message code="Check.field.required_l" arguments="아이디" var="user_id_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<spring:message code="Check.field.required_l" arguments="전화번호" var="corp_tel_required"/>
<spring:message code="Check.field.wrong_l" arguments="전화번호" var="corp_tel_wrong"/>
<spring:message code="Check.field.required_l" arguments="우편번호 검색을 통해 주소" var="corp_post_required"/>
<spring:message code="Check.field.required_l" arguments="상세주소" var="corp_adr_dsc_required"/>


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

	function fncSave() {
		var f = document.F1;

		if (f.mode.value == "U") {
			if (f.user_id.value == "") {
				alert("${user_id_empty}");
				history.back();
				return false;
			}
		}
		
		if (f.mode.value == "I") {
			if(f.user_id.value=="" || f.user_id.value.length < '${mins}' || f.user_id.value.length > '${maxs}'){
				alert("${user_id_range}");
				f.user_id.focus();
				return false;
			}
			if(!ChkOneByte(f.user_id.value) || !ChkSpecialByte(f.user_id.value)){
				alert("${user_id_languege_check}");
				f.user_id.focus();
				return false;
			}			
			if(f.uniqId.value!="yes"){
				alert("${UniqIDCheck}");
				return false;
			}
			if(f.temp_id.value!=f.user_id.value){
				alert("${user_id_unique_compare}");
				f.uniqId.value="";
				f.temp_id.value="";
				f.user_id.focus();
				return false;
			}
			if (f.re_user_pw.value == "") {
				alert("${user_pw_required}");
				f.re_user_pw.focus();
				return false;
			}
		}

		if(f.re_user_pw.value!="" || f.re_user_pw_confirm.value!=""){	//변경 비밀번호 필드가 하나라도 입력되어 있을 경우
			if(f.re_user_pw.value==""){
				alert("${re_user_pw_required}");
				f.re_user_pw.focus();
				return false;
			}
			if(f.re_user_pw.value.length < '${mins}' || f.re_user_pw.value.length > '${maxs}'){
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
		
		if (f.user_nm.value == "") {
			alert("${user_nm_required}");
			f.user_nm.focus();
			return false;
		}

		f.target = "HiddenFrame";
		f.encoding = "multipart/form-data";
		f.action = "/mng/usr/registProc.do";
		f.submit();
		f.target = "_self";
		f.encoding = "application/x-www-form-urlencoded";
		f.action = "/mng/usr/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.user_id.value == "") {
			alert("${user_id_empty}");
			history.back();
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.target = "HiddenFrame";
			f.mode.value = "D";
			f.action = "/mng/usr/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/mng/usr/regist.do";
			return true;
		} else {
			return false;
		}
	}

	//아이디 중복체크
	function fncIdDupCheck(){
		
		var f = document.F1;
		var user_id = f.user_id.value;
		
		if(user_id=="" || user_id.length < '${mins}' || user_id.length > '${maxs}'){
			alert("${user_id_range}");
			f.user_id.focus();
			return false;
		}
		
		f.uniqId.value="";
		f.temp_id.value = user_id;
		var url = "/mng/usr/idDupCheck.do";
		$.ajax({
			url : url,
			data : {"user_id" : user_id},
			error : ajaxError,
			success:function(response){
				if(response.status=="success"){
					if(response.uniqueID=="yes"){
						alert("${UniqIDYes}");
						f.uniqId.value="yes";
					}else{
						alert("${UniqIDNo}");
						f.uniqId.value="";
						f.temp_id.value="";
					}
				}else{
					alert("${failException}");
					f.uniqId.value="";
					f.temp_id.value="";
				}
			}
		});
	}
	
	//로그인 실패 횟수 초기화
	function fncInitFailCnt(){
		document.getElementById("fail_cnt").innerText = "0";
	}
</script>

				<div id="content">
					<div class="navi">홈 &gt; 회원관리 &gt; 회원관리</div>
					<form:form commandName="VoUserInfo" name="F1" id="F1" method="post" onsubmit="return fncSave();" enctype="multipart/form-data">
	        			<form:hidden path="mode"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
				        <input type="hidden" name="temp_id" id="temp_id"/>
				        <input type="hidden" name="uniqId" id="uniqId"/>
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
			                        	<c:choose>
			                        		<c:when test="${VoUserInfo.mode=='U'}">
			                        			<form:hidden path="user_id"/>
			                        			<c:out value="${VoUserInfo.user_id}"/>
			                        		</c:when>
			                        		<c:otherwise>
			                        			<form:input path="user_id" maxlength="20" title="아이디" cssClass="w200"/>
			                        			<span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncIdDupCheck();" title="아이디중복체크">아이디중복체크</a></span>
			                        		</c:otherwise>
			                        	</c:choose>
			                        </td>
			                        <th>이름</th>
			                        <td>
			                        	<form:input path="user_nm" maxlength="20" title="이름" cssClass="w200"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th><c:if test="${VoUserInfo.mode=='U'}">변경 </c:if>비밀번호</th>
			                        <td>
			                        	<form:password path="re_user_pw" maxlength="20"/><br/>
			                        	<c:if test="${VoUserInfo.mode=='U'}">* 비밀번호를 변경할 경우에 입력해 주세요.</c:if>	                            
			                        </td>
									<th><c:if test="${VoUserInfo.mode=='U'}">변경 </c:if>비밀번호 확인</th>
			                        <td>
			                        	<form:password path="re_user_pw_confirm" maxlength="20"/><br/>
			                            <c:if test="${VoUserInfo.mode=='U'}">* 비밀번호를 변경할 경우에 입력해 주세요.</c:if>
			                        </td>	                        
	                    		</tr>
			                    <tr>
									<th>권한</th>
			                        <td>
			                        	<select name="auth_id" id="auth_id">
			                        	<c:forEach var="result" items="${AuthList}" varStatus="status">
			                        		<c:set var="space" value=""/>
				                            <c:forEach var="i" begin="1" end="${result.level-1}" step="1">
												<c:set var="space" value="${space}${'&nbsp;&nbsp;'}"/>
											</c:forEach>
			                        		<option value="${result.auth_id}"<c:if test="${result.auth_id == VoUserInfo.auth_id}"> selected</c:if>><c:out value="${space}" escapeXml="false"/>${result.auth_nm}</option>
			                        	</c:forEach>
			                        	</select>										
			                        </td>
									<th>회사명</th>
			                        <td>
			                        	<select name="corp_id" id="corp_id">
			                        	<c:forEach var="result" items="${CorpList}" varStatus="status">
			                        		<c:set var="space" value=""/>
				                            <c:forEach var="i" begin="1" end="${result.level-1}" step="1">
												<c:set var="space" value="${space}${'&nbsp;&nbsp;'}"/>
											</c:forEach>
			                        		<option value="${result.corp_id}"<c:if test="${result.corp_id == VoUserInfo.corp_id}"> selected</c:if>><c:out value="${space}" escapeXml="false"/>${result.corp_nm}</option>
			                        	</c:forEach>
			                        	</select>
			                        </td>
			                    </tr>
			                    <tr>
									<th>이메일</th>
			                        <td colspan="3">
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
										</select>
			                        </td>
							    </tr>
								<tr>
									<th>휴대전화번호</th>
			                        <td>
			                        	<form:input path="user_hp" maxlength="15"/>        
			                        	* 예)010-0000-0000                    
			                        </td>
									<th>일반전화번호</th>
			                        <td>
			                        	<form:input path="user_tel" maxlength="15"/>
			                        	* 예)02-0000-0000 
			                        </td>	
			                    </tr>
			                    <tr>
									<th>사용여부</th>
			                        <td>
			                        	<select name="use_yn" id="use_yn">
				                        	<option value="Y"<c:if test="${VoUserInfo.use_yn == 'Y'}"> selected</c:if>>사용중</option>
				                        	<option value="N"<c:if test="${VoUserInfo.use_yn == 'N'}"> selected</c:if>>중지</option>
			                        	</select>
			                        </td>
									<th>로그인실패</th>
			                        <td>
			                        	<form:hidden path="access_fail_cnt"/>
			                        	<span id="fail_cnt">${VoUserInfo.access_fail_cnt}</span>
			                        	<c:choose>
			                        		<c:when test="${VoUserInfo.mode=='U'}">
			                        			<c:if test="${VoUserInfo.access_fail_cnt > 0}">
			                        			<a class="button" onclick="fncInitFailCnt();" title="초기화">초기화</a>
			                        			</c:if>
			                        		</c:when>
			                        		<c:otherwise>
			                        			0
			                        		</c:otherwise>
			                        	</c:choose>
			                        </td>
			                    </tr>
			                </tbody>
			            </table>

			            <div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
			            	<a class="button" onclick="fncSave();" title="저장">저장</a>
			            	</c:if>
			            	<c:if test="${VoUserInfo.mode=='U'}">
			            	<a class="button" href="/mng/usr/view.do<c:if test="${not empty retParam}">?${retParam}&user_id=${VoUserInfo.user_id}</c:if>" title="취소">취소</a>
			            	</c:if>
			            	<a class="button" href="/mng/usr/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            	<c:if test="${VoUserInfo.user_id != userInfoVO.user_id && ChkAuth=='true'}">
			            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
			            	</c:if>
			            </div> 
			        </form:form>
				</div>
