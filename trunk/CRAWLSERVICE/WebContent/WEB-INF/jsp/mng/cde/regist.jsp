<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="Check.field.hidden.empty" var="code_part_empty"/>

<spring:message code="Check.field.required_l" arguments="분류코드" var="code_part_required"/>
<spring:message code="Check.field.code.languege.english" var="code_part_languege_check"/>
<spring:message code="Check.field.required_u" arguments="분류코드명" var="code_part_nm_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<script type="text/javascript">
	function fncSave() {
		var f = document.F1;
/* 
		if(f.mode.value == "I") {
			if (f.code_part.value == "") {
				alert("${code_part_required}");
				f.code_part.focus();
				return false;
			}

			if(!ChkOneByte(f.code_part.value) || !ChkSpecialByte(f.code_part.value)){
				alert("${code_part_languege_check}");
				f.code_part.focus();
				return false;
			}
			
			f.code_part.value = f.code_part.value.toUpperCase();
			
		}else if(f.mode.value == "U") {
			if (f.code_part.value == "") {
				alert("${code_part_empty}");
				history.back();
				return false;
			}
		} */

		if (f.code_part.value == "") {
			alert("코드 분류를 선택해 주세요.");
			f.code_part.focus();
			return false;
		}
		
		if(f.code.value==""){
			alert("코드를 입력해 주세요.");
			f.code.focus();
			return false;
		}
		/* 
		if(!ChkOneByte(f.code.value) || !ChkSpecialByte(f.code.value)){
			alert("코드를 영문과 숫자로만 입력해 주세요.");
			f.code.focus();
			return false;
		}
		 */
		f.code.value = f.code.value.toUpperCase();
		
		if(f.code_nm.value==""){
			alert("코드명을 입력해 주세요.");
			f.code_nm.focus();
			return false;
		}
		
		if (f.code_ord.value == "") {
			alert("코드 순번을 입력해 주세요.");
			f.code_ord.focus();
			return false;
		}
		
		if(!fn_isNumber(f.code_ord)){
			alert("코드 순번을 숫자로만 입력해 주세요.");
			f.code_ord.focus();
			return false;
		}

		f.target = "HiddenFrame";
		f.action = "/mng/cde/registProc.do";
		f.submit();
		f.target = "_self";
		f.action = "/mng/cde/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.code_part.value == "") {
			alert("${code_part_empty}");
			history.back();
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.target = "HiddenFrame";
			f.mode.value = "D";
			f.action = "/mng/cde/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/mng/cde/regist.do";
			return true;
		} else {
			return false;
		}
	}

</script>

				<div id="content">
					<div class="navi">홈 &gt; 사이트관리 &gt; 코드관리</div>
					<form:form commandName="VoCodeInfo" name="F1" id="F1" method="post" onsubmit="return fncSave();">
	        			<form:hidden path="mode"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="코드 정보">
			            <caption class="accessibility">코드 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
			                    	<th>분류코드</th>
			                        <td colspan="3">
			                        	<c:choose>
			                        		<c:when test="${VoCodeInfo.mode=='U'}">
			                        			<form:hidden path="code_part"/>
			                        			<c:out value="${VoCodeInfo.code_part_nm}"/>
			                        		</c:when>
			                        		<c:otherwise>
			                        			<select name="code_part" id="code_part">
					                        	<option value="">선택하세요</option>
					                        	<c:forEach var="result" items="${CodePartList}" varStatus="status">
					                        		<option value="${result.code_part}"<c:if test="${result.code_part == VoCodeInfo.code_part}"> selected</c:if>>${result.code_part_nm}</option>
					                        	</c:forEach>
					                        	</select>	
			                        		</c:otherwise>
			                        	</c:choose>
			                        </td>
			                    </tr>
			                    <tr>
									<th>코드</th>
			                        <td>
			                        	<c:choose>
			                        		<c:when test="${VoCodeInfo.mode=='U'}">
			                        			<form:hidden path="code"/>
			                        			<c:out value="${VoCodeInfo.code}"/>
			                        		</c:when>
			                        		<c:otherwise>
			                        			<form:input path="code" maxlength="20" title="코드" cssClass="w200"/>
			                        		</c:otherwise>
			                        	</c:choose>
			                        </td>
									<th>코드명</th>
			                        <td>
			                        	<form:input path="code_nm" maxlength="200" title="코드명" cssClass="w200"/>
			                        </td>	
			                    </tr>
			                    <tr>
									<th>코드설명</th>
			                        <td>
			                        	<form:input path="code_desc" maxlength="500" title="코드설명" cssClass="w200"/>
			                        </td>
									<th>코드순번</th>
			                        <td>
			                        	<form:input path="code_ord" maxlength="5" title="코드순번" cssClass="w80"/>
			                        </td>	
			                    </tr>
							    <c:if test="${VoCodeInfo.mode=='U'}">
								<tr>
									<th>수정일</th>
			                        <td>
			                        	<c:out value="${VoCodeInfo.mod_dt}"/>
			                        </td>
									<th>등록일</th>
			                        <td>
			                        	<c:out value="${VoCodeInfo.reg_dt}"/>
			                        </td>	
			                    </tr>
			                    </c:if>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<a class="button" onclick="fncSave();" title="저장">저장</a>
			            	<a class="button" href="/mng/cde/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            	<c:if test="${VoCodeInfo.mode=='U'}">
			            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
			            	</c:if>
			            </div> 
			        </form:form>
				</div>
