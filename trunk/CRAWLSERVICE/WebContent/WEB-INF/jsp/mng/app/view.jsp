<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_app_id"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.app_id.value==""){
		alert("${check_app_id}");
		return false;
	}

	f.action = "/mng/app/regist.do";
	f.submit();
	return true;
}

</script>
				<div id="content">
					<div class="navi">홈 &gt; 사이트관리 &gt; APP관리</div>
					<form:form commandName="VoAppInfo" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
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
			                        	<c:out value="${VoAppInfo.app_title}" escapeXml="false"/>
			                        </td>
			                    </tr>
								<tr>
									<th>지원 OS</th>
			                        <td>
			                        	<c:out value="${VoAppInfo.app_os}"/>
			                        </td>
									<th>최소 OS 버전</th>
			                        <td>
			                        	<c:out value="${VoAppInfo.app_os_min_ver}"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>APP Ver</th>
			                        <td>
			                        	<c:out value="${VoAppInfo.app_ver}"/>
			                        </td>
									<th>APP 파일</th>
			                        <td>
			                        	<c:if test="${not empty VoAppInfo.file_nm}">
						  					<a href="/common/downLoadFile.do?type=app&app_id=${VoAppInfo.app_id}"><c:out value="${VoAppInfo.org_file_nm}"/></a>
			                        	</c:if>
			                        </td>
			                    </tr>
			                    <tr>
									<th>APP 상세</th>
			                        <td colspan="3">
			                        	<pre><c:out value="${VoAppInfo.app_desc}" escapeXml="false"/></pre>
			                        </td>
			                    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	<a class="button" href="/mng/app/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
