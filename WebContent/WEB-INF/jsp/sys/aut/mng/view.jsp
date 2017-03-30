<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_auth_no"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.auth_no.value==""){
		alert("${check_auth_no}");
		return false;
	}

	f.action = "/sys/aut/mng/regist.do";
	f.submit();
	return true;
}

</script>
				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 권한관리</div>
					<form:form commandName="VoAuth" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
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
			                        	${VoAuth.up_auth_id}
			                        </td>
									<th>상위권한명</th>
			                        <td>
			                        	${VoAuth.up_auth_nm}
			                        </td>
			                    </tr>
			                    <tr>
									<th>권한ID</th>
			                        <td>
			                        	${VoAuth.auth_id}
			                        </td>
									<th>권한명</th>
			                        <td>
			                        	${VoAuth.auth_nm}
			                        </td>
			                    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	<a class="button" href="/sys/aut/mng/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
