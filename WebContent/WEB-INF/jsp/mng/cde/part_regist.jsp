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
		}

		if (f.code_part_nm.value == "") {
			alert("${code_part_nm_required}");
			f.code_part_nm.focus();
			return false;
		}

		f.target = "HiddenFrame";
		f.action = "/mng/cde/part_registProc.do";
		f.submit();
		f.target = "_self";
		f.action = "/mng/cde/part_regist.do";
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
			f.action = "/mng/cde/part_registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/mng/cde/part_regist.do";
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
			            <table class="tbl" summary="분류 코드 정보">
			            <caption class="accessibility">분류 코드 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
			                    	<th>분류코드</th>
			                        <td>
			                        	<c:choose>
			                        		<c:when test="${VoCodeInfo.mode=='U'}">
			                        			<form:hidden path="code_part"/>
			                        			<c:out value="${VoCodeInfo.code_part}"/>
			                        		</c:when>
			                        		<c:otherwise>
			                        			<form:input path="code_part" maxlength="20" title="분류코드" cssClass="w200"/>
			                        		</c:otherwise>
			                        	</c:choose>
			                        </td>
			                        <th>분류코드명</th>
			                        <td>
			                        	<form:input path="code_part_nm" maxlength="30" title="분류코드명" cssClass="w200"/>
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
			            	<a class="button" href="/mng/cde/part_list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            	<c:if test="${VoCodeInfo.mode=='U'}">
			            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
			            	</c:if>
			            </div> 
			        </form:form>
				</div>
