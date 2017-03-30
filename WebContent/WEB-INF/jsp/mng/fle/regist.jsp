<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="Check.field.required_u" arguments="파일추가 버튼을 클릭한 후 파일" var="file_add_required"/>
<spring:message code="Check.field.required_u" arguments="파일" var="file_attach_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<script type="text/javascript">
var myImage;
var myImageId;

function fncSave() {
	var f = document.F1;

	if(f.multifilecnt.value=="0"){
		alert("${file_add_required}");
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
				alert("${file_attach_required}");
				document.getElementById(elementId).focus();
				return false;
				break;
			}
		}
	}

	f.target = "HiddenFrame";
	f.encoding = "multipart/form-data";
	f.action = "/mng/fle/registProc.do";
	f.submit();
	f.target = "_self";
	f.encoding = "application/x-www-form-urlencoded";
	f.action = "/mng/fle/regist.do";
	return true;
}

function fncChkImgSize(id,path){

	  var tmpImage = new Image();
	  var obj=document.getElementById("file"+id);
	    if(obj.value.indexOf("\\fakepath\\") < 0){
	      tmpImage.src = document.getElementById("file"+id).value;
	    }else{
	      obj.select();
	      tmpImage.src = document.selection.createRange().text.toString();
	      obj.blur();
	    }

	   alert("선택하신 이미지는 "+ tmpImage.width +" * "+ tmpImage.height +" 입니다.");
	   return;

}


//파일 추가 버튼
function fncAddFile(){
	
    var f = document.F1;
    
    var objTbl	= document.getElementById("multifile");
    if(f.multifilecnt.value>=10 || objTbl.rows.length>=10){
        alert("파일등록은 최대 10개까지 가능합니다.");
        return;
    }
    if(/*@cc_on!@*/true){
        var objRow = document.createElement("TR");
        var objCell = document.createElement("TD");
        objTbl.appendChild(objRow);            
        objRow.appendChild(objCell);
    }else{
        var objRow 	= objTbl.insertRow();
        var objCell	= objRow.insertCell();
    }
    objCell.innerHTML   ="<input type=\"file\" name=\"file_nm\" id=\"file"+parseInt(f.multifilecnt.value)+"\" class=\"input file\"/> "
    					+" 공개범위:<select name=\"open_ran\" id=\"open_ran"+parseInt(f.multifilecnt.value)+"\"><option value=\"9\">전체</option><option value=\"1\">회사</option><option value=\"0\">비공개</option></select>"
                        +" <span class=\"txt_small\" id=\"filespan"+parseInt(f.multifilecnt.value)+"\"></span>";
    f.multifilecnt.value=parseInt(f.multifilecnt.value)+1;
    return;
}

//파일 선택박스 삭제 버튼
function fncDelFile(){
    var f = document.F1;
    var objTbl	= document.getElementById("multifile");
    var objrow	= "";
    var nRowNo;
    if(objTbl.rows.length==0){
        return;
    }
    nRowNo = parseInt(f.multifilecnt.value) - 1;
    objrow  =   eval("document.getElementById('filespan"+ nRowNo +"')");

    if(objrow.innerHTML != ""){
        alert("첨부된 파일이 있어 셀을 삭제할 수 없습니다.");
        return;
    }
    objTbl.deleteRow(objTbl.rows.length-1);
    f.multifilecnt.value=parseInt(f.multifilecnt.value)-1;
    return;
}
</script>

				<div id="content">
					<div class="navi">홈 &gt; 파일관리 &gt; 파일관리</div>
					<form:form commandName="VoFileInfo" name="F1" id="F1" method="post" onsubmit="return fncSave();" enctype="multipart/form-data">
	        			<form:hidden path="mode"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
				        <input type="hidden" id="multifilecnt" name="multifilecnt" value="0"/>
			            <table class="tbl" summary="파일 정보">
			            <caption class="accessibility">파일 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="90%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>파일등록</th>
			                        <td>
			                        	<input type="button" value=" + " onclick="fncAddFile();">
			                            <input type="button" value=" - " onclick="fncDelFile();">
			                            <table id ='multifile'></table>
			                            <!-- 
			                        	<c:if test="${not empty VoFileInfo.file_nm}">
						  					<img src="${VoFileInfo.file_path}/${VoFileInfo.file_nm}" width="200" height="45"/>
						  					<br>
			                        	</c:if>
			                        	<div id='previewId' style='width:200px;height:45px;display:none'></div>
			                        	<form:input path="file_nm" type="file" onchange="previewImage(this,'previewId')"/>
			                        	 -->
			                        </td>
							    </tr>
			                </tbody>
			            </table>

			            <div class="btnC">
			            	<a class="button" onclick="fncSave();" title="저장">저장</a>
			            	<a class="button" href="/mng/fle/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
