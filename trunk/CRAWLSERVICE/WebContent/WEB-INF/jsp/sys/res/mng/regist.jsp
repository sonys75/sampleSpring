<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="reso_id_empty"/>
<spring:message code="Check.field.required_u" arguments="자원명" var="reso_nm_required"/>
<spring:message code="Check.field.required_u" arguments="패턴" var="reso_ptn_required"/>
<spring:message code="Check.field.required_l" arguments="정렬순서" var="reso_ord_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<script type="text/javascript">
	function fncSave() {
		var f = document.F1;

		if(f.mode.value == "U"){
			if (f.reso_id.value == "") {
				alert("${reso_id_empty}");
				history.back();
				return false;
			}
		}

		if (f.reso_nm.value == "") {
			alert("${reso_nm_required}");
			f.reso_nm.focus();
			return false;
		}
		if (f.reso_ptn.value == "") {
			alert("${reso_ptn_required}");
			f.reso_ptn.focus();
			return false;
		}
		
		if (f.reso_ord.value == "") {
			alert("${reso_ord_required}");
			f.reso_ord.focus();
			return false;
		}
		
		if(!fn_isNumber(f.reso_ord)){
			alert("정렬순서를 숫자로만 입력해 주세요.");
			f.reso_ord.focus();
			return false;
		}
		
		if(f.authlistcnt.value=="0"){
			alert("권한을 선택해 주세요.");
			fncAddStp();
			return false;
		}else if(f.authlistcnt.value=="1"){
			if(f.auth_id.value==""){
				alert("권한이 설정되지 않았습니다.\n권한을 선택해 주세요.");
				return false;
			}
		}else{
			for(var i=0;i<f.auth_id.length;i++){
				if(f.auth_id[i].value==""){
					alert(parseInt(i+1) + "번째 권한이 설정되지 않았습니다.\n권한을 선택해 주세요.");
					return false;
				}
			}
		}

		f.target = "HiddenFrame";
		//f.encoding = "multipart/form-data";
		f.action = "/sys/res/mng/registProc.do";
		f.submit();
		f.target = "_self";
		//f.encoding = "application/x-www-form-urlencoded";
		f.action = "/sys/res/mng/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.reso_id.value == "") {
			alert("${reso_id_empty}");
			history.back();
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.mode.value = "D";
			f.target = "HiddenFrame";
			f.action = "/sys/res/mng/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/sys/res/mng/regist.do";
			return true;
		} else {
			return false;
		}
	}
	
    //권한 추가 버튼
    function fncAddAuth(){
        var f = document.F1;
			        	
        var objTbl	= document.getElementById("authlist");

        if(/*@cc_on!@*/true){
            var objRow = document.createElement("TR");
            var objCell = document.createElement("TD");
            objTbl.appendChild(objRow);            
            objRow.appendChild(objCell);
        }else{
            var objRow 	= objTbl.insertRow();
            var objCell	= objRow.insertCell();
        }
        objCell.innerHTML   ="<input type=\"hidden\" name=\"auth_id\" id=\"auth_id"+parseInt(f.authlistcnt.value)+"\">"
        					+"<input type=\"text\" name=\"auth_nm\" id=\"auth_nm"+parseInt(f.authlistcnt.value)+"\" class=\"input w400 read\" readOnly=\"true\" onclick=\"openDivModal('권한검색','/cmm/sch/authAll.do?seq="+parseInt(f.authlistcnt.value)+"',500,400);\"/>"
        					+" <span class=\"btn_pack small_sky\"><a onclick=\"openDivModal('권한검색','/cmm/sch/authAll.do?seq="+parseInt(f.authlistcnt.value)+"',500,400);\" title=\"권한 선택\" style=\"cursor:pointer\">권한 선택</a></span>";
        f.authlistcnt.value=parseInt(f.authlistcnt.value)+1;
        
        return;
    }
    
    //권한 삭제 버튼
    function fncDelAuth(){
        var f = document.F1;
        var objTbl	= document.getElementById("authlist");
        var objrow	= "";
        var nRowNo;
        
        if(objTbl.rows.length==0){
            return;
        }
        
        nRowNo = parseInt(f.authlistcnt.value) - 1;
        objrow = eval("document.getElementById('auth_id"+ nRowNo +"')");

        if(objrow.value != ""){
            if(!confirm("등록된 권한을 삭제하시겠습니까?")){
            	return;
            }
        }
        
        objTbl.deleteRow(objTbl.rows.length-1);
        f.authlistcnt.value=parseInt(f.authlistcnt.value)-1;
        
        return;
    }
</script>

				<div id="content">
					<div class="navi">홈 &gt; 시스템관리 &gt; 자원관리</div>
					<form:form commandName="VoReso" name="F1" id="F1" method="post" onsubmit="return fncSave();">
	        			<form:hidden path="mode"/>
				        <form:hidden path="reso_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
				        <input type="hidden" id="authlistcnt" name="authlistcnt" value="0"/>
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
			                        	<c:choose>
			                        		<c:when test="${VoReso.mode=='U'}">
			                        			<c:out value="${VoReso.reso_id}"></c:out>
			                        		</c:when>
			                        		<c:otherwise>자동부여</c:otherwise>
			                        	</c:choose>
			                        </td>
									<th>자원명</th>
			                        <td>
			                        	<form:input path="reso_nm" maxlength="20" title="자원명" />
			                        </td>
			                    </tr>
			                    <tr>
									<th>패턴</th>
			                        <td>
			                        	<form:input path="reso_ptn" maxlength="50" title="패턴"/>
			                        </td>
									<th>정렬순서</th>
			                        <td>
			                        	<form:input path="reso_ord" maxlength="4" title="정렬순서" cssClass="w40 a_right"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>권한목록</th>
			                        <td colspan="3">
			                        	<input type="button" value=" + " onclick="fncAddAuth();">
			                            <input type="button" value=" - " onclick="fncDelAuth();">
			                            <table id ="authlist"></table>
			                        </td>	                        
			                    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncSave();" title="저장">저장</a>
			            	<c:if test="${VoReso.mode=='U'}">
				            	<a class="button" href="/sys/res/mng/view.do<c:if test="${not empty retParam}">?${retParam}&reso_id=${VoReso.reso_id}</c:if>" title="취소">취소</a>
			            		<a class="button" onclick="fncDel();" title="삭제">삭제</a>
			            	</c:if>
			            	<a class="button" href="/sys/res/mng/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            	
			            </div> 
			        </form:form>
					<c:if test="${not empty resoAuth}">
						<c:forEach var="result" items="${resoAuth}" varStatus="status">
							<script>
								fncAddAuth();
								var trIdx = "${status.index}";
								document.getElementById("auth_id" + trIdx).value = "${result.auth_id}";
								document.getElementById("auth_nm" + trIdx).value = "${result.auth_nm}";
							</script>
						</c:forEach>
					</c:if>
				</div>
