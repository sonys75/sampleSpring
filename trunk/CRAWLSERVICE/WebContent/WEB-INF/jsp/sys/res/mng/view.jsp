<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_reso_id"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.reso_id.value==""){
		alert("${check_reso_id}");
		return false;
	}

	f.action = "/sys/res/mng/regist.do";
	f.submit();
	return true;
}

</script>
				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 자원관리</div>
					<form:form commandName="VoReso" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="reso_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="자원 정보">
			            <caption class="accessibility">자원 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
								<tr>
									<th>자원ID</th>
			                        <td>
			                        	<c:out value="${VoReso.reso_id}"></c:out>
			                        </td>
									<th>자원명</th>
			                        <td>
			                        	<c:out value="${VoReso.reso_nm}"></c:out>
			                        </td>
			                    </tr>
			                    <tr>
									<th>패턴</th>
			                        <td>
			                        	<c:out value="${VoReso.reso_ptn}"></c:out>
			                        </td>
									<th>정렬순서</th>
			                        <td>
			                        	<c:out value="${VoReso.reso_ord}"></c:out>
			                        </td>
			                    </tr>
							    <tr>
			                        <th>권한목록</th>
			                        <td colspan="3">
			                        	<table class="plist" style="table-layout:fixed">
								            <colgroup>
								            	<col width="10%">
								            	<col width="40%">
								                <col width="">
								            </colgroup>
								            <thead>
								            <tr>
								            	<th>순서</th>
								            	<th>권한ID</th>
								                <th>권한명</th>
								            </tr>
								            </thead>
								            <tbody>
								            <c:forEach var="result" items="${resoAuth}" varStatus="status">
									            <tr>
									            	<td class="a_center">${status.index+1}</td>
									            	<td>
									            		<c:out value="${result.auth_id}"></c:out>
									            	</td>
									            	<td>
									            		<c:out value="${result.auth_nm}"></c:out>
									            	</td>
									            </tr>
											</c:forEach>
								            </tbody>
							            </table>
			                        </td>
							    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	<a class="button" href="/sys/res/mng/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
