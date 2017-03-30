<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="app_id_empty"/>
<spring:message code="Check.field.required_u" arguments="APP 제목" var="app_title_required"/>
<spring:message code="Check.field.required_l" arguments="지원 OS" var="app_os_required"/>
<spring:message code="Check.field.required_u" arguments="최소 지원 OS 버전" var="app_os_min_ver_required"/>
<spring:message code="Check.field.required_u" arguments="어플리케이션 버전" var="app_ver_required"/>
<spring:message code="Check.field.required_u" arguments="파일" var="file_attach_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<script type="text/javascript">
	function fncSave() {
		var f = document.F1;

		if(f.mode.value == "U"){
			if (f.app_id.value == "") {
				alert("${app_id_empty}");
				history.back();
				return false;
			}
		}

		if (f.app_title.value == "") {
			alert("${app_title_required}");
			f.app_title.focus();
			return false;
		}
		
		if (f.app_os.value == "") {
			alert("${app_os_required}");
			f.app_os.focus();
			return false;
		}

		if (f.app_os_min_ver.value == "") {
			alert("${app_os_min_ver_required}");
			f.app_os_min_ver.focus();
			return false;
		}
		
		if (f.app_ver.value == "") {
			alert("${app_ver_required}");
			f.app_ver.focus();
			return false;
		}
		
		for ( var i = 0; i < document.F1.elements.length; i++) {
			if (document.F1.elements[i].type == "file") {
				var elementId=document.F1.elements[i].id;
								
				if (document.getElementById(elementId).value != "") {
					retValue = fncCheckFile(document.getElementById(elementId).value);
					if (retValue != true) {
						alert(retValue);
						return false;
						break;
					}
				}else{
					if(f.mode.value == "I"){
						alert("${file_attach_required}");
						document.getElementById(elementId).focus();
						return false;
						break;
					}
				}
			}
		}
		
		f.target = "HiddenFrame";
		f.encoding = "multipart/form-data";
		f.action = "/mng/app/registProc.do";
		f.submit();
		f.target = "_self";
		f.encoding = "application/x-www-form-urlencoded";
		f.action = "/mng/app/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.app_id.value == "") {
			alert("${app_id_empty}");
			history.back();
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.mode.value = "D";
			f.target = "HiddenFrame";
			f.action = "/mng/app/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/mng/app/regist.do";
			return true;
		} else {
			return false;
		}
	}
</script>

				<div id="content">
					<div class="navi">홈 &gt; 사이트관리 &gt; APP관리</div>
					<form:form commandName="VoAppInfo" name="F1" id="F1" method="post" onsubmit="return fncSave();" enctype="multipart/form-data">
	        			<form:hidden path="mode"/>
				        <form:hidden path="app_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="APP 정보">
			            <caption class="accessibility">APP 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                	<tr>
									<th>APP 제목</th>
			                        <td colspan="3">
			                        	<form:input path="app_title" maxlength="200" title="APP 제목" cssClass="w300"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>지원 OS</th>
			                        <td>
			                        	<form:select path="app_os" title="지원 OS">
											<form:option value="ANDROID" label="ANDROID" />
											<form:option value="WINDOWS" label="WINDOWS" />
											<form:option value="LINUX" label="LINUX" />
										</form:select>
			                        </td>
									<th>최소 OS 버전</th>
			                        <td>
			                        	<form:input path="app_os_min_ver" maxlength="20" title="최소 지원 OS 버전" cssClass="w200"/>
			                        </td>
			                    </tr>
								<tr>
									<th>APP 버전</th>
			                        <td>
			                        	<form:input path="app_ver" maxlength="20" title="APP 버전"/>
			                        </td>
									<th>APP 파일</th>
			                        <td>
				                        <c:if test="${not empty VoAppInfo.file_nm}">
						  					<a href="/common/downLoadFile.do?type=app&app_id=${VoAppInfo.app_id}">${VoAppInfo.org_file_nm}</a>
						  					<br>
			                        	</c:if>
			                        	<form:input path="file_nm" type="file" title="APP 파일"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>APP 상세</th>
			                        <td colspan="3">
			                        	<form:textarea path="app_desc" rows="10" cssClass="w100p"/>
			                        </td>
			                    </tr>
			                    
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncSave();" title="저장">저장</a>
			            	<c:if test="${VoAppInfo.mode=='U'}">
			            	<a class="button" href="/mng/app/view.do<c:if test="${not empty retParam}">?${retParam}&app_id=${VoAppInfo.app_id}</c:if>" title="취소">취소</a>
			            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
			            	</c:if>
			            	<a class="button" href="/mng/app/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            	
			            </div> 
			        </form:form>
				</div>
