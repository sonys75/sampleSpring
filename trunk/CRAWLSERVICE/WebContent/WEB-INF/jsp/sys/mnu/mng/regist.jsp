<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="menu_id_empty"/>
<spring:message code="Check.field.required_u" arguments="메뉴명" var="menu_nm_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<script type="text/javascript">
	function fncSave() {
		var f = document.F1;

		if(f.mode.value == "U"){
			if (f.menu_id.value == "") {
				alert("${menu_id_empty}");
				history.back();
				return false;
			}
		}

		if (f.menu_nm.value == "") {
			alert("${menu_nm_required}");
			f.menu_nm.focus();
			return false;
		}

		f.target = "HiddenFrame";
		f.action = "/sys/mnu/mng/registProc.do";
		f.submit();
		f.target = "_self";
		f.action = "/sys/mnu/mng/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.menu_id.value == "") {
			alert("${menu_id_empty}");
			history.back();
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.mode.value = "D";
			f.target = "HiddenFrame";
			f.action = "/sys/mnu/mng/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/sys/mnu/mng/regist.do";
			return true;
		} else {
			return false;
		}
	}
	
	function fncUpMenuClear(){
		var f = document.F1;
		
		if (confirm("상위 메뉴를 초기화 하시겠습니까?")) {
			f.up_menu_id.value = "";
			f.up_menu_nm.value = "";
		}
		return;
	}
</script>

				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 메뉴관리</div>
					<form:form commandName="VoMenu" name="F1" id="F1" method="post" onsubmit="return fncSave();">
	        			<form:hidden path="mode"/>
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
			                        	<form:input path="menu_nm" maxlength="20" title="메뉴명" />
			                        </td>
									<th>상위메뉴명</th>
			                        <td>
			                        	<form:input path="up_menu_nm" maxlength="20" title="상위메뉴명" readonly="true" cssClass="read"/>
			                        	
			                        		<span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModal('메뉴검색','/cmm/sch/menu.do',500,400);" title="검색">검색</a></span>
			                        		<span class="btn_pack small_sky"><a style="cursor:pointer" onclick="fncUpMenuClear();" title="초기화">초기화</a></span>
			                        	<c:if test="${VoMenu.mode=='U'}"></c:if>
			                        </td>
			                    </tr>
			                    <tr>
									<th>URL</th>
			                        <td colspan="3">
			                        	<form:input path="menu_url" maxlength="50" title="패턴" cssClass="w200"/>
			                        </td>	                        
			                    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncSave();" title="저장">저장</a>
			            	<c:if test="${VoMenu.mode=='U'}">
				            	<a class="button" href="/sys/mnu/mng/view.do?menu_id=${VoMenu.menu_id}" title="취소">취소</a>
				            	<c:if test="${cntMenu==0}">
			            		<a class="button" onclick="fncDel();" title="삭제">삭제</a>
			            		</c:if>
			            	</c:if>
			            	<a class="button" href="/sys/mnu/mng/list.do" title="목록">목록</a>
			            	
			            </div> 
			        </form:form>
				</div>
