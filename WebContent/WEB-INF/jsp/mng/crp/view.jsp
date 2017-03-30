<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_corp_id"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.corp_id.value==""){
		alert("${check_corp_id}");
		return false;
	}

	f.action = "/mng/crp/regist.do";
	f.submit();
	return true;
}

</script>
				<div id="content">
					<div class="navi">홈 &gt; 회원관리 &gt; 회사관리</div>
					<form:form commandName="VoCorpInfo" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="corp_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
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
									<th>로고</th>
			                        <td colspan="3">
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
			                        <td>
										<c:out value="${VoCorpInfo.corp_nm}"/>
			                        </td>
			                        <th>상위 회사명</th>
			                        <td>
			                        	<c:out value="${VoCorpInfo.up_corp_nm}"/>
			                        </td>
			                    </tr>
								<tr>
									<th>전화번호</th>
			                        <td>
			                        	<c:out value="${VoCorpInfo.corp_tel}"/>
			                        </td>
									<th>홈페이지</th>
			                        <td>
			                        	<c:out value="${VoCorpInfo.corp_url}"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>주소</th>
			                        <td colspan="3">
			                        	<c:out value="${VoCorpInfo.corp_post}"/>
			                        	<c:out value="${VoCorpInfo.corp_adr}"/>
			                        	<c:out value="${VoCorpInfo.corp_adr_dsc}"/>
			                        </td>
							    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	<a class="button" href="/mng/crp/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
