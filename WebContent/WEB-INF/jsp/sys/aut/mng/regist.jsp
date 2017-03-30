<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="auth_no_empty"/>
<spring:message code="Check.field.required_u" arguments="상위 권한" var="up_auth_no_required"/>
<spring:message code="Check.field.required_l" arguments="권한 ID" var="auth_id_required"/>
<spring:message code="Check.field.min" arguments="4" var="mins"/>
<spring:message code="Check.field.max" arguments="20" var="maxs"/>
<spring:message code="Check.field.range_l" arguments="권한 ID,4,20" var="auth_id_range"/>
<spring:message code="User.id.check.unique.languege" var="auth_id_languege_check"/>
<spring:message code="Check.field.required_u" arguments="권한명" var="auth_nm_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<script type="text/javascript">
	function fncSave() {
		var f = document.F1;

		if(f.mode.value == "U"){
			if (f.auth_no.value == "" || f.auth_id.value == "") {
				alert("${auth_no_empty}");
				history.back();
				return false;
			}
		}

		if (f.up_auth_no.value == "") {
			alert("${up_auth_no_required}");
			return false;
		}
		if (f.auth_id.value == "") {
			alert("${auth_id_required}");
			f.auth_id.focus();
			return false;
		}
		if(f.auth_id.value=="" || f.auth_id.value.length < '${mins}' || f.auth_id.value.length > '${maxs}'){
			alert("${auth_id_range}");
			f.auth_id.focus();
			return false;
		}
		if(!ChkOneByte(f.auth_id.value) || !ChkSpecialByte(f.auth_id.value)){
			alert("${auth_id_languege_check}");
			f.auth_id.focus();
			return false;
		}
		f.auth_id.value = f.auth_id.value.toUpperCase();
		if (f.auth_nm.value == "") {
			alert("${auth_nm_required}");
			f.auth_nm.focus();
			return false;
		}
 
		f.target = "HiddenFrame";
		//f.encoding = "multipart/form-data";
		f.action = "/sys/aut/mng/registProc.do";
		f.submit();
		f.target = "_self";
		//f.encoding = "application/x-www-form-urlencoded";
		f.action = "/sys/aut/mng/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.auth_no.value == "") {
			alert("${auth_no_empty}");
			history.back();
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.mode.value = "D";
			f.target = "HiddenFrame";
			f.action = "/sys/aut/mng/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/sys/aut/mng/regist.do";
			return true;
		} else {
			return false;
		}
	}
</script>

				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 권한관리</div>
					<form:form commandName="VoAuth" name="F1" id="F1" method="post" onsubmit="return fncSave();">
	        			<form:hidden path="mode"/>
				        <form:hidden path="auth_no"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="권한 정보">
			            <caption class="accessibility">권한 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>상위권한ID</th>
			                        <td>
			                        	<form:hidden path="up_auth_no"/>
			                        	<form:input path="up_auth_id" maxlength="20" title="상위권한ID" readonly="true" cssClass="read"/>
			                        </td>
									<th>상위권한명</th>
			                        <td>
			                        	<form:input path="up_auth_nm" maxlength="20" title="상위권한명" readonly="true" cssClass="read"/>
			                        	<c:if test="${UpAuthCnt==0}">
			                        		<span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModal('권한검색','/cmm/sch/auth.do',500,400);" title="검색">검색</a></span>
			                        	</c:if>
			                        </td>
			                    </tr>
			                    <tr>
									<th>권한ID</th>
			                        <td>
			                        	<c:choose>
			                        		<c:when test="${VoAuth.mode=='U' && UpAuthCnt==0}">
			                        			<form:input path="auth_id" maxlength="20" title="권한ID"/>
			                        		</c:when>
			                        		<c:when test="${VoAuth.mode=='U' && UpAuthCnt>0}">
			                        			<form:input path="auth_id" maxlength="20" title="권한ID" readonly="true" cssClass="read"/>
			                        		</c:when>
			                        		<c:otherwise>
			                        			<form:input path="auth_id" maxlength="20" title="권한ID"/>
			                        		</c:otherwise>
			                        	</c:choose>
			                        </td>
									<th>권한명</th>
			                        <td>
			                        	<form:input path="auth_nm" maxlength="20" title="권한명"/>
			                        </td>
			                    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncSave();" title="저장">저장</a>
			            	<c:if test="${VoAuth.mode=='U'}">
				            	<a class="button" href="/sys/aut/mng/view.do<c:if test="${not empty retParam}">?${retParam}&auth_no=${VoAuth.auth_no}</c:if>" title="취소">취소</a>
				            	<c:if test="${UpAuthCnt==0}">
				            		<a class="button" onclick="fncDel();" title="삭제">삭제</a>
				            	</c:if>
			            	</c:if>
			            	<a class="button" href="/sys/aut/mng/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            	
			            </div> 
			        </form:form>
				</div>
