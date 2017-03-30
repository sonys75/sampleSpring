<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:message code="Check.field.hidden.empty" var="check_file_id"/>
<spring:message code="Process.confirm.relation.modify" var="modify_confirm"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>
<script src="/js/jquery.colorpicker.js"></script>
<style type="text/css">
div.colorPicker-picker {
  height: 16px;
  width: 16px;
  padding: 0 !important;
  border: 1px solid #ccc;
  background: url(/images/arrow.gif) no-repeat top right;
  cursor: pointer;
  line-height: 16px;
  font-size:0.75em;
  font-weight:bold;
  text-align: center;
}

div.colorPicker-palette {
  width: 110px;
  position: absolute;
  border: 1px solid #598FEF;
  background-color: #EFEFEF;
  padding: 2px;
  z-index: 9999;
}

div.colorPicker_hexWrap {width: 100%; float:left }
div.colorPicker_hexWrap label {font-size: 95%; color: #2F2F2F; margin: 5px 2px; width: 25%}
div.colorPicker_hexWrap input {margin: 5px 2px; padding: 0; font-size: 95%; border: 1px solid #000; width: 65%; }

div.colorPicker-swatch {
  height: 12px;
  width: 12px;
  border: 1px solid #000;
  margin: 2px;
  float: left;
  cursor: pointer;
  line-height: 12px;
}
</style>
<script type="text/javascript">
var maxIfoCnt = 10;

$(document).ready(function() {
	fncShowTextInfo();
});
 
function fncSave() {
	var f = document.F1;

	if (f.file_id.value == ""){
		alert("${check_file_id}");
		return false;
	}

	if(f.file_nm.value!=""){
		retValue = fncCheckFile(f.file_nm.value);
		if (retValue != true) {
			alert(retValue);
			return false;
		}
	}
	
	if(f.corp_id.value==""){
		alert("소유회사를 선택해 주세요.");
		openDivModal('회사검색','/cmm/sch/corp.do',500,400);
		return false;
	}
	
	if(f.txt_yn.value=='Y'){
		if(f.ifolistcnt.value=="0"){
			alert("안내메세지를 입력해 주세요.");
			fncAddIfo();
			return false;
		}else if(f.ifolistcnt.value=="1"){
			if(f.ifo_msg.value==""){
				alert("안내메세지를 입력해 주세요.");
				f.ifo_msg.focus();
				return false;
			}
			if(f.ifo_line_px.value=="" || !fn_isNumber(f.ifo_line_px)){
				alert("아웃라인을 숫자로만 입력해 주세요.");
				f.ifo_line_px.focus();
				return false;
			}
			if(f.ifo_upad.value=="" || !fn_isNumber(f.ifo_upad)){
				alert("상단여백을 숫자로만 입력해 주세요.");
				f.ifo_upad.focus();
				return false;
			}
			if(f.ifo_spad.value=="" || !fn_isNumber(f.ifo_spad)){
				alert("측면여백을 숫자로만 입력해 주세요.");
				f.ifo_spad.focus();
				return false;
			}
		}else{
			for(var i=0;i<f.ifo_msg.length;i++){
				if(f.ifo_msg[i].value==""){
					alert(parseInt(i+1) + "번째 안내메세지를 입력해 주세요.");
					f.ifo_msg[i].focus();
					return false;
				}
				if(f.ifo_line_px[i].value=="" || !fn_isNumber(f.ifo_line_px[i])){
					alert(parseInt(i+1) + "번째 아웃라인을 숫자로만 입력해 주세요.");
					f.ifo_line_px[i].focus();
					return false;
				}
				if(f.ifo_upad[i].value=="" || !fn_isNumber(f.ifo_upad[i])){
					alert(parseInt(i+1) + "번째 상단여백을 숫자로만 입력해 주세요.");
					f.ifo_upad[i].focus();
					return false;
				}
				if(f.ifo_spad[i].value=="" || !fn_isNumber(f.ifo_spad[i])){
					alert(parseInt(i+1) + "번째 측면여백을 숫자로만 입력해 주세요.");
					f.ifo_spad[i].focus();
					return false;
				}
			}
		}
	}

	if(!confirm("${modify_confirm}")){
		return false;
	}
	
	f.target = "HiddenFrame";
	f.encoding = "multipart/form-data";
	f.action = "/mng/fle/modifyProc.do";
	f.submit();
	f.target = "_self";
	f.encoding = "application/x-www-form-urlencoded";
	f.action = "/mng/fle/modify.do";
	return true;
}

function fncDel() {
	var f = document.F1;
	var ua = window.navigator.userAgent;

	if (f.file_id.value == "") {
		alert("${check_file_id}");
		return false;
	}
	
    if(f.file_nm.value!=""){
    	if (ua.indexOf("MSIE") > -1) {
	    	filefield =eval("f.file0");
	    	filefield.select();
	        document.selection.clear();
    	}else{
    		f.file_nm.value="";
    	}
	}

    if (confirm("${delete_confirm}")) {
		f.mode.value = "D";
		f.target = "HiddenFrame";
		f.encoding = "multipart/form-data";
		f.action = "/mng/fle/modifyProc.do";
		f.submit();
		f.target = "_self";
		f.encoding = "application/x-www-form-urlencoded";
		f.action = "/mng/fle/modify.do";
		return true;
	} else {
		return false;
	}
}

function fncShowTextInfo(){
	var f = document.F1;
	if(f.txt_yn.value=="Y"){
		document.getElementById("tblTextInfo").style.display="";
	}else{
		document.getElementById("tblTextInfo").style.display="none";
	}
}

function openColorPicker(id){
	$('#'+id).colorPicker({
		showHexField: false
		,pickerDefault: "ffffff" 
		,colors: ["FFFFFF","FFDFDF","FFBFBF","FF9F9F","FF7F7F","FF5F5F","FF3F3F","FF1F1F","FF0000","DF1F00","C33B00","A75700","8B7300","6F8F00","53AB00","37C700","1BE300","00FF00","00DF1F","00C33B","00A757","008B73","006F8F","0053AB","0037C7","001BE3","0000FF","0000df","0000c3","0000a7","00008b","00006f","000053","000037","00001b","000000"]
		, transparency: true
	}); 
}


//안내 추가 버튼
function fncAddIfo(){
    var f = document.F1;
		        	
    var objTbl	= document.getElementById("ifolist");
    if(f.ifolistcnt.value>=maxIfoCnt || objTbl.rows.length>=maxIfoCnt){
        alert("안내 등록은 최대 "+maxIfoCnt+"개까지 가능합니다.");
        return;
    }
    
    var nRowLen = objTbl.rows.length +1;

    if(/*@cc_on!@*/true){
        var objRow = document.createElement("TR");
        var objCell = document.createElement("TD");
        objTbl.appendChild(objRow);            
        objRow.appendChild(objCell);
    }else{
        var objRow 	= objTbl.insertRow();
        var objCell	= objRow.insertCell();
    }
    objRow.innerHTML   ="<tr><td class=\"a_center\"><input type=\"hidden\" name=\"ifo_seq\" id=\"ifo_seq"+parseInt(f.ifolistcnt.value)+"\" value=\""+ nRowLen +"\">"+ nRowLen +"</td>"
    					+"<td><input type=\"text\" name=\"ifo_msg\" id=\"ifo_msg"+parseInt(f.ifolistcnt.value)+"\" class=\"input w300\" maxlength=\"30\"/></td>"
    					+"<td class=\"a_center\"><select name=\"ifo_size\" id=\"ifo_size"+parseInt(f.ifolistcnt.value)+"\"><option value=\"S\">작게</option><option value=\"M\" selected>보통</option><option value=\"L\">크게</option><option value=\"B\">가장크게</option></select></td>"
    					+"<td class=\"a_center\"><select name=\"ifo_bold\" id=\"ifo_bold"+parseInt(f.ifolistcnt.value)+"\"><option value=\"G\" selected>일반</option><option value=\"B\">두껍게</option><option value=\"I\">이탤릭</option></select></td>"
    					+"<td class=\"a_right\"><input type=\"text\" name=\"ifo_line_px\" id=\"ifo_line_px"+parseInt(f.ifolistcnt.value)+"\" class=\"input w30 a_right\" maxlength=\"1\" value=\"0\"/>px</td>"
    					+"<td class=\"a_center\"><input name=\"ifo_clr\" id=\"ifo_clr"+parseInt(f.ifolistcnt.value)+"\" type=\"text\"/></td>"
    					+"<td class=\"a_right\"><input type=\"text\" name=\"ifo_upad\" id=\"ifo_upad"+parseInt(f.ifolistcnt.value)+"\" class=\"input w30 a_right\" maxlength=\"4\" value=\"0\"/></td>"
    					+"<td class=\"a_center\"><select name=\"ifo_aln\" id=\"ifo_aln"+parseInt(f.ifolistcnt.value)+"\"><option value=\"L\">좌측</option><option value=\"C\">중앙</option><option value=\"R\">우측</option></select></td>"
    					+"<td class=\"a_right\"><input type=\"text\" name=\"ifo_spad\" id=\"ifo_spad"+parseInt(f.ifolistcnt.value)+"\" class=\"input w30 a_right\" maxlength=\"4\" value=\"0\"/></td></tr>";
    openColorPicker("ifo_clr"+parseInt(f.ifolistcnt.value));
    					
    f.ifolistcnt.value= nRowLen ;
    f.ifolistmodYn.value = "Y";
    
    return;
}

//안내 삭제 버튼
function fncDelIfo(){
    var f = document.F1;
    var objTbl	= document.getElementById("ifolist");
    var objrow	= "";
    var nRowNo;
    
    if(objTbl.rows.length==0){
        return;
    }
    
    nRowNo = parseInt(f.ifolistcnt.value) - 1;
    objrow = eval("document.getElementById('ifo_seq"+ nRowNo +"')");

    if(objrow.value != ""){
        if(!confirm("등록된 안내를 삭제하시겠습니까?")){
        	return;
        }
        f.ifolistmodYn.value = "Y";
    }
    
    objTbl.deleteRow(objTbl.rows.length-1);
    f.ifolistcnt.value=parseInt(f.ifolistcnt.value)-1;
    
    return;
}
</script>
				<div id="content">
					<div class="navi">홈 &gt; 파일관리 &gt; 파일관리</div>
					<form:form commandName="VoFileInfo" name="F1" id="F1" method="post" onsubmit="return fncSave();">
						<form:hidden path="mode"/>
				        <form:hidden path="file_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
				        <input type="hidden" id="ifolistcnt" name="ifolistcnt" value="0"/>
				        <input type="hidden" id="ifolistmodYn" name="ifolistmodYn"/>
			            <table class="tbl" summary="파일 정보">
			            <caption class="accessibility">파일 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>파일 아이디</th>
			                        <td>
			                        	${VoFileInfo.file_id}
			                        </td>
			                        <th>파일위치</th>
			                        <td>
			                        	${VoFileInfo.file_path}
			                        </td>
							    </tr>
							    <tr>
			                        <th>파일명</th>
			                        <td>
			                        	<a href="/common/downLoadFile.do?type=filemanage&file_id=${VoFileInfo.file_id}">${VoFileInfo.org_file_nm}</a>
			                        </td>
			                        <th>타입 및 확장자</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoFileInfo.file_type=='I'}">이미지</c:when>
		                            		<c:when test="${VoFileInfo.file_type=='T'}">문서</c:when>
		                            		<c:when test="${VoFileInfo.file_type=='E'}">엑셀</c:when>
		                            		<c:when test="${VoFileInfo.file_type=='M'}">동영상</c:when>
		                            		<c:when test="${VoFileInfo.file_type=='A'}">오디오</c:when>
		                            		<c:otherwise>미분류</c:otherwise>
			                            </c:choose>
			                            [ ${VoFileInfo.file_cont_type} ]
			                        	- ${VoFileInfo.file_ext}
			                        </td>
							    </tr>
							    <tr>
			                        <th>수정파일</th>
			                        <td colspan="3">
			                        	<input type="file" name="file_nm" id="file0" class="input file"/>
			                        </td>
							    </tr>
							    <tr>
									<th>파일사이즈</th>
			                        <td>
		                            	<c:choose>
		                            		<c:when test="${VoFileInfo.file_size/1048 > 1048 && VoFileInfo.file_size/1048 < 1048576}">
		                            			<fmt:formatNumber value="${VoFileInfo.file_size/1048/1048}" maxFractionDigits="2"/>MB
		                            		</c:when>
		                            		<c:when test="${VoFileInfo.file_size/1048 >= 1048576}">
		                            			<fmt:formatNumber value="${VoFileInfo.file_size/1048/1048/1048}" maxFractionDigits="2"/>GB
		                            		</c:when>
		                            		<c:otherwise>
		                            			<fmt:formatNumber value="${VoFileInfo.file_size/1048}" maxFractionDigits="0"/>KB
		                            		</c:otherwise>
		                            	</c:choose>	                        
			                        </td>
			                        <th>공개범위</th>
			                        <td>
			                        	<select id="open_ran" name="open_ran">
			                        		<option value="0"<c:if test="${VoFileInfo.open_ran=='0'}"> selected</c:if>>비공개</option>
			                        		<option value="1"<c:if test="${VoFileInfo.open_ran=='1'}"> selected</c:if>>회사</option>
			                        		<option value="9"<c:if test="${VoFileInfo.open_ran=='9'}"> selected</c:if>>전체</option>
			                        	</select>
			                        </td>
							    </tr>
							    <tr>
									<th>사용여부</th>
			                        <td>
			                            <select id="use_yn" name="use_yn">
			                        		<option value="Y"<c:if test="${VoFileInfo.use_yn=='Y'}"> selected</c:if>>사용</option>
			                        		<option value="N"<c:if test="${VoFileInfo.use_yn!='Y'}"> selected</c:if>>중지</option>
			                        	</select>
			                        </td>
			                        <th>삭제여부</th>
			                        <td>
			                        	<c:choose>
		                            		<c:when test="${VoFileInfo.del_yn=='Y'}">삭제</c:when>
		                            		<c:when test="${VoFileInfo.del_yn=='D'}">삭제대기</c:when>
		                            		<c:otherwise>해당없음</c:otherwise>
			                            </c:choose>
			                        </td>
							    </tr>
							    <tr>
									<th>회사명</th>
			                        <td>
			                        	<form:hidden path="corp_id"/>
			                        	<sec:authorize access="hasRole('SITEADMIN')">
			                       			<form:input path="corp_nm" maxlength="20" title="회사명" cssClass="w200" readOnly="true" onclick="openDivModal('회사검색','/cmm/sch/corp.do',500,400);"/>
			                            	<span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModal('회사검색','/cmm/sch/corp.do',500,400);" title="검색">검색</a></span>
		                       			</sec:authorize>
		                       			<sec:authorize access="!hasRole('SITEADMIN')">
		                       				<form:hidden path="corp_nm"/>
			                       			${VoFileInfo.corp_nm}
		                       			</sec:authorize>
			                        </td>
			                        <th>권한명</th>
			                        <td>
			                        	${VoFileInfo.auth_nm}
			                        </td>
							    </tr>
							    <tr>
									<th>아이디</th>
			                        <td>
			                        	${VoFileInfo.user_id}
			                        </td>
			                        <th>이름</th>
			                        <td>
			                        	${VoFileInfo.user_nm}
			                        </td>
							    </tr>
							    <tr>
									<th>최종수정자</th>
			                        <td>
			                        	${VoFileInfo.mod_id}
			                        </td>
			                        <th>최종수정일</th>
			                        <td>
			                        	${VoFileInfo.mod_dt}
			                        </td>
							    </tr>
							    <tr>
			                        <th>등록일</th>
			                        <td colspan="3">
			                        	${VoFileInfo.reg_dt}
			                        </td>
							    </tr>
							    <tr>
									<th>안내정보여부</th>
			                        <td colspan="3">
			                            <select id="txt_yn" name="txt_yn" onchange="fncShowTextInfo();">
			                        		<option value="Y"<c:if test="${VoFileInfo.txt_yn == 'Y'}"> selected</c:if>>있음</option>
			                        		<option value="N"<c:if test="${VoFileInfo.txt_yn != 'Y'}"> selected</c:if>>없음</option>
			                        	</select>
			                        </td>
							    </tr>
			                </tbody>
			            </table>
			            
			            <table class="tbl" summary="안내 정보" id="tblTextInfo" style="display:<c:if test="${VoFileInfo.txt_yn!='Y'}">none</c:if>">
			            <caption class="accessibility">안내 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="90%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>안내 정보</th>
			                        <td>
			                        	<input type="button" value=" + " onclick="fncAddIfo();">
			                            <input type="button" value=" - " onclick="fncDelIfo();">
			                        </td>
							    </tr>
							    <tr>
			                        <td colspan="2">
			                            <table class="plist">
							            <colgroup>
							            	<col width="5%">
							                <col width="">
							                <col width="8%">
							                <col width="8%">
							                <col width="8%">
							                <col width="8%">
							                <col width="8%">
							                <col width="8%">
							                <col width="8%">
							            </colgroup>
							            <thead>
							            <tr>
							            	<th>번호</th>
							            	<th>안내메세지</th>
							                <th>글자크기</th>
							                <th>글자두께</th>
											<th>아웃라인</th>
							                <th>색상</th>
							                <th>상단여백</th>
							                <th>측면정렬</th>
							                <th>측면여백</th>
							            </tr>
							            </thead>
							            <tbody id="ifolist" class="scroll">
							            </tbody>
						            </table>
			                        </td>
							    </tr>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
				            	<a class="button" onclick="fncSave();" title="저장">저장</a>
				            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
			            	</c:if>
			            	<a class="button" href="/mng/fle/view.do<c:if test="${not empty retParam}">?${retParam}&file_id=${VoFileInfo.file_id}</c:if>" title="취소">취소</a>
			            	<a class="button" href="/mng/fle/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
			        <c:if test="${not empty TxtInfoList}">
			            <c:forEach var="result" items="${TxtInfoList}" varStatus="status">
							<script>
								fncAddIfo();
								var trIdx = "${status.index}";
								document.getElementById("ifo_seq" + trIdx).value = "${result.ifo_seq}";
								document.getElementById("ifo_msg" + trIdx).value = "${result.ifo_msg}";
								document.getElementById("ifo_size" + trIdx).value = "${result.ifo_size}";
								document.getElementById("ifo_bold" + trIdx).value = "${result.ifo_bold}";
								document.getElementById("ifo_line_px" + trIdx).value = "${result.ifo_line_px}";
								document.getElementById("ifo_clr" + trIdx).value = "${result.ifo_clr}";
								document.getElementById("ifo_upad" + trIdx).value = "${result.ifo_upad}";
								document.getElementById("ifo_aln" + trIdx).value = "${result.ifo_aln}";
								document.getElementById("ifo_spad" + trIdx).value = "${result.ifo_spad}";
								$("#ifo_clr" + trIdx).val("${result.ifo_clr}");   
							    $("#ifo_clr" + trIdx).change();
							</script>
						</c:forEach>
			        </c:if>	
				</div>
