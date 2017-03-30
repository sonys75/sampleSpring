<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_menu_id"/>
<script type="text/javascript">
function fncEdit(){
	var f=document.F1;

	if(f.menu_id.value==""){
		alert("${check_menu_id}");
		return false;
	}

	f.action = "/sys/mnu/mng/regist.do";
	f.submit();
	return true;
}

</script>
				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 메뉴관리</div>
					<form:form commandName="VoMenu" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="menu_id"/>
				        <form:hidden path="up_menu_id"/>
			            <table class="tbl" summary="메뉴 정보">
			            <caption class="accessibility">메뉴 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
								<tr>
									<th>메뉴명</th>
			                        <td>
			                        	<c:out value="${VoMenu.menu_nm}"></c:out>
			                        </td>
									<th>상위메뉴명</th>
			                        <td>
			                        	<c:out value="${VoMenu.up_menu_nm}"></c:out>
			                        </td>
			                    </tr>
			                    <tr>
									<th>URL</th>
			                        <td colspan="3">
			                        	<c:out value="${VoMenu.menu_url}"></c:out>
			                        </td>
			                    </tr>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	<a class="button" href="/sys/mnu/mng/list.do" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
