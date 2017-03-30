<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="part_empty"/>
<spring:message code="Check.field.hidden.empty" var="seq_empty"/>
<spring:message code="Check.field.required_u" arguments="제목" var="subject_required"/>
<spring:message code="Check.field.required_u" arguments="내용" var="content_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<script type="text/javascript">
	function fncSave() {
		var f = document.F1;
		
		if (f.part.value == "") {
			alert("${part_empty}");
			history.back();
			return false;
		}
		
		if(f.mode.value == "U"){
			if (f.seq.value == "") {
				alert("${seq_empty}");
				history.back();
				return false;
			}
		}

		if (f.subject.value == "") {
			alert("${subject_required}");
			f.subject.focus();
			return false;
		}
		
		if (f.content.value == "") {
			alert("${content_required}");
			f.content.focus();
			return false;
		}

		/* 
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
		*/
		
		f.target = "HiddenFrame";
		f.encoding = "multipart/form-data";
		f.action = "/cmm/brd/registProc.do";
		f.submit();
		f.target = "_self";
		f.encoding = "application/x-www-form-urlencoded";
		f.action = "/cmm/brd/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.part.value == "") {
			alert("${part_empty}");
			history.back();
			return false;
		}
		
		if (f.seq.value == "") {
			alert("${seq_empty}");
			history.back();
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.mode.value = "D";
			f.target = "HiddenFrame";
			f.action = "/cmm/brd/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/cmm/brd/regist.do";
			return true;
		} else {
			return false;
		}
	}
</script>

			<div id="content">
				<div class="navi">홈 &gt; 게시판</div>
				<form:form commandName="VoBoard" name="F1" id="F1" method="post" onsubmit="return fncSave();" enctype="multipart/form-data">
					<form:hidden path="mode" />
					<form:hidden path="part" />
					<form:hidden path="seq" />
					<form:hidden path="rel_seq" />
					<form:hidden path="rpy_num" />
					<form:hidden path="rpy_step" />
					<form:hidden path="page_no" />
					<form:hidden path="sch_fld" />
					<form:hidden path="sch_word" />
					<table class="tbl" summary="게시판 정보">
						<caption class="accessibility">게시판 정보</caption>
						<colgroup>
							<col width="10%" />
							<col />
						</colgroup>
						<tbody>
							<tr>
								<th>제목</th>
								<td colspan="3">
									<form:input path="subject" maxlength="200" title="제목" cssClass="w95p" />
								</td>
							</tr>
							<tr>
								<th>내용</th>
								<td colspan="3">
									<form:textarea path="content" rows="10" cssClass="w100p" />
								</td>
							</tr>
							<!-- 
			                  <tr>
							<th>첨부파일</th>
			                      <td colspan="3">
			
			                      </td>
			                  </tr>
			                   -->
						</tbody>
					</table>
			
					<div class="btnC">
						<c:if test="${VoBoard.mode=='I'}">
							<a class="button" onclick="fncSave();" title="저장">저장</a>
						</c:if>
						<c:if test="${VoBoard.mode=='U'}">
							<a class="button" href="/cmm/brd/view.do<c:if test="${not empty retParam}">?${retParam}&seq=${VoBoard.seq}</c:if>" title="취소">취소</a>
							<c:if test="${bChkAuth==true}">
								<a class="button" onclick="fncSave();" title="저장">저장</a>
								<c:if test="${bChkDelAuth==true}">
								<a class="button" onclick="fncDel();" title="삭제">삭제</a>
								</c:if>
							</c:if>
						</c:if>
						<a class="button" href="/cmm/brd/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
					</div>
				</form:form>
			</div>
