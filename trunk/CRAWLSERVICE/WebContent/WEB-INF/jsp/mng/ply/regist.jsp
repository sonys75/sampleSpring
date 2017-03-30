<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.required_u" arguments="파일추가 버튼을 클릭한 후 파일" var="file_add_required"/>
<spring:message code="Check.field.required_u" arguments="파일" var="file_attach_required"/>
<spring:message code="Check.field.required_l" arguments="회사전화번호" var="corp_tel_required"/>
<spring:message code="Check.field.wrong_l" arguments="회사전화번호" var="corp_tel_wrong"/>
<spring:message code="Check.field.required_l" arguments="상세주소" var="corp_adr_dsc_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>
<spring:message code="Check.field.hidden.empty" var="check_ply_ver_id"/>
<script type="text/javascript">
	$(document).ready(function() {
		$( "#filetabs" ).tabs();
	});
	
	var f=document.F1;
	var arrTab = new Array('M','I','A','T','D','E','F','X');

    function fncTabChange(tbl){
    	for(var i=0;i<arrTab.length;i++){
    		document.getElementById("tab"+arrTab[i]).className = "";
    		document.getElementById("file"+arrTab[i]).style.display = "none";
    	}

    	document.getElementById("tab"+tbl).className = "on";
    	document.getElementById("file"+tbl).style.display = "";
    }
    
    function fncMovePlayList(){
    	//선택된 탭
    	var chkTab = "";
    	var bChk = false;
    	var nChkTab;

    	nChkTab = $("#filetabs .ui-tabs-panel:visible").attr("id").replace("tabs-","");
    	
    	for(var i=0;i<arrTab.length;i++){
    		if(i == nChkTab-1){
    			chkTab=arrTab[i];
    			break;
    		}
    	}

    	for ( var i = 0; i < document.F1.elements.length; i++) {
			if(document.F1.elements[i].id.indexOf("chkfile"+chkTab) >=0){
				var chkElement = document.F1.elements[i].id;
				if(document.getElementById(chkElement).checked==true){
					bChk = true;
					//if(chkTab=="M" || chkTab=="A"){
						setPlayList(document.getElementById(chkElement).value);	
					//}else{
					//	setTimePlayList(document.getElementById(chkElement).value);
					//}
					document.getElementById(chkElement).checked=false;
				}
			}
		}
    	
    	if(!bChk){
    		alert("구성할 파일을 선택해 주세요.");
    	}
    }
    
	function fncMoveFileList(){
    	for(var i = 0; i< document.F1.elements.length; i++) {
			if(document.F1.elements[i].id.indexOf("chkPlayList") >=0){
				var chkElement = document.F1.elements[i].id;
				if(document.getElementById(chkElement).checked==true){
					bChk = true;
					removePlayList(chkElement.replace("chkPlayList",""));
					fncSetListSeq();
					setTimeout('fncMoveFileList()',100);
				}
			}
		}
    }

	/**
	 * setPlayList	
	 */
	function setPlayList(file_id){
		var idVar = new Array();
	    var i =0;

		if(file_id != null){
			jQuery("#playList").children("tr").each(function() {
	        	idVar[i]= jQuery(this).attr("id");
        		i++;
	    	});
	        
		    idVar=idVar.sort();

			i=idVar.length;
			
			if(i ==0 ){
				 var select = $("#playList");
			}else{
				 var select = $("#playList tr:last");
			}	        
			//alert(file_id);
			
			var sFileName = document.getElementById(file_id).innerText.toLowerCase();
			var sFileExt = sFileName.substring(sFileName.lastIndexOf(".")+1);
			
			var newElem = $('<tr id="MARK'+i+'">');
			$('<td class="a_center"><input type="hidden" name="ordr" value="'+(i+1)+'"/>'+(i+1)+'</td>').appendTo(newElem);
			
			if(fncCheckMovie(sFileExt)){
				$('<td class="a_center"><input type="hidden" name="playtime" id="MARK'+ i +'PT" value="0" maxlength="4"/>-</td>').appendTo(newElem);
			}else{
				$('<td class="a_center"><input type="text" style="width:30px" class="a_right" name="playtime" id="MARK'+ i +'PT" value="7" maxlength="4"/>초</td>').appendTo(newElem);
			}
			
			$('<td><input type="hidden" name="file_id" id="file_id'+ i +'" value="'+file_id+'"/>'+document.getElementById(file_id).innerText+'</td>').appendTo(newElem);
			$('<td class="a_center"><select name=\"skin_yn\" id=\"skin_yn'+i+'\"><option value=\"Y\">사용</option><option value=\"N\">없음</option></select></td>').appendTo(newElem);
			if(fncCheckPicture(sFileExt)){
				$('<td class="a_center"><select name=\"ani_type\" id=\"ani_type'+i+'\"><option value=\"N\">없음</option><option value=\"ZOOM\">확대</option></select></td>').appendTo(newElem);
				$('<td class="a_center"><input type="text" name="ani_val1" id="ani_val1'+ i +'" style="width:30px" class="a_right" value="33" maxlength="3"/>%</td>').appendTo(newElem);
				$('<td class="a_center"><input type="text" name="ani_val2" id="ani_val2'+ i +'" style="width:30px" class="a_right" value="55" maxlength="3"/>%</td>').appendTo(newElem);
			}else{
				$('<td class="a_center"><input type="hidden" name=\"ani_type\" id=\"ani_type'+i+'\" value="N"/>-</td>').appendTo(newElem);
				$('<td class="a_center"><input type="hidden" name="ani_val1" id="ani_val1'+ i +'" value="0"/>-</td>').appendTo(newElem);
				$('<td class="a_center"><input type="hidden" name="ani_val2" id="ani_val2'+ i +'" value="0"/>-</td>').appendTo(newElem);
			}
			$('<td class="a_center"><span class="btn_pack xsmall_green a_center" onclick=moveUpItem("MARK'+ i +'") title="MARK'+ i +'"><a>▲</a></span><span class="btn_pack xsmall_green a_center" onclick=moveDownItem("MARK'+ i +'") title="MARK'+ i +'"><a>▼</a></span></td>').appendTo(newElem);
			$('<td class="a_center"><input type="checkbox" name="chkPlayList" id="chkPlayList'+i+'" value="'+i+'"></td></td>').appendTo(newElem);
			$('</tr>').appendTo(newElem);

        	if(i==0 ){
				$(newElem).appendTo("#playList");
			}else{
				select.after(newElem);
			}
        	fncSetListSeq();
		}
	}

	/**
	 * 동영상 체크
	 */
	function fncCheckMovie(fileExt){
		var bMovie = true;
		if(fileExt!="mp4" && fileExt!="avi" && fileExt!="wmv"){
			bMovie = false;
		}
		return bMovie;
	}
	
	/**
	 * 이미지 체크
	 */
	function fncCheckPicture(fileExt){
		var bPicture = true;
		if(fileExt!="jpg" && fileExt!="gif" && fileExt!="bmp" && fileExt!="jpeg" && fileExt!="png"){
			bPicture = false;
		}
		return bPicture;
	}

	/**
	 * removePlayList	
	 */
	function removePlayList(currentMark){
		var idStr='#MARK'+ currentMark;
		$(idStr).remove();

		return;
	}
	
	/**
	 * 순번 다시 생성.
	 */
	function fncSetListSeq() {
		
	    var cnt = 1;
	    jQuery("#playList").children("tr").each(function() {
	        var html ='<input type="hidden" style="width:15px" name="ordr" value="'+(cnt)+'"/>'+(cnt);
	        cnt++;
	        jQuery(this).children().eq(0).html(html);
	    });
	    return;
	}

	//순서변경(위로이동)
	function moveUpItem(currentMark) {
		var idStr='#' + currentMark;
	    
		var prevHtml=$(idStr).prev().html();
	    
		if( prevHtml == null){
	    	return;
	    }

	    var prevcurrentMark=$(idStr).prev().attr("id");
	    var currcurrentMark=$(idStr).attr("id");
	    var currHtml=$(idStr).html();
	    
	    var crntTime = document.getElementById(currentMark+"PT").value;
	    var prevTime = document.getElementById(prevcurrentMark+"PT").value;
	    
	  	//값 변경
	    $(idStr).html(prevHtml);
	    $(idStr).prev().html(currHtml);
	    fncSetListSeq();
	  	//id 값도 변경
	    $(idStr).prev().attr("id","TEMP_TR");
	    $(idStr).attr("id",prevcurrentMark);
	    $("#TEMP_TR").attr("id",currcurrentMark);
	    
	    document.getElementById(prevcurrentMark+"PT").value = prevTime;
	    document.getElementById(currentMark+"PT").value = crntTime;

	}

	//순서변경(아래로 이동)
	function moveDownItem(currentMark) {
		
		var idStr='#' + currentMark;
		var nextHtml=$(idStr).next().html();
		if( nextHtml == null){
		    return;
		}
		var nextcurrentMark=$(idStr).next().attr("id");
		var currcurrentMark=$(idStr).attr("id");
		var currHtml=$(idStr).html();

	    var crntTime = document.getElementById(currentMark+"PT").value;
	    var nextTime = document.getElementById(nextcurrentMark+"PT").value;
	    
		$(idStr).next().html(currHtml);

		  //값 변경
		$(idStr).html(nextHtml);
		fncSetListSeq();
		//id 값도 변경
		$(idStr).next().attr("id","TEMP_TR");
		$(idStr).attr("id",nextcurrentMark);
		$("#TEMP_TR").attr("id",currcurrentMark);
		
		document.getElementById(nextcurrentMark+"PT").value = nextTime;
		document.getElementById(currentMark+"PT").value = crntTime;
	}

	//저장
	function fncSave(){
		var f=document.F1;
	    var cnt = 0;
	    jQuery("#playList").children("tr").each(function() {
	        cnt++;
	    });
	    
	    if(cnt==0){
	    	alert("저장할 내용이 없습니다.\n재생목록을 구성한 후 저장 버튼을 클릭하세요.");
	    	return false;
	    }else if(cnt=="1"){
			if(f.playtime.value=="" || !fn_isNumber(f.playtime)){
				alert("시간을 숫자로만 입력해 주세요.");
				f.playtime.focus();
				return false;
			}
			if(f.ani_val1.value=="" || !fn_isNumber(f.ani_val1)){
				alert("동작값1을 숫자로만 입력해 주세요.");
				f.ani_val1.focus();
				return false;
			}
			if(f.ani_val2.value=="" || !fn_isNumber(f.ani_val2)){
				alert("동작값2를 숫자로만 입력해 주세요.");
				f.ani_val2.focus();
				return false;
			}
		}else{
			for(var i=0;i<f.playtime.length;i++){
				if(f.playtime[i].value=="" || !fn_isNumber(f.playtime[i])){
					alert(parseInt(i+1) + "번째 시간을 숫자로만 입력해 주세요.");
					f.playtime[i].focus();
					return false;
				}
				if(f.ani_val1[i].value=="" || !fn_isNumber(f.ani_val1[i])){
					alert(parseInt(i+1) + "번째 동작값1을 숫자로만 입력해 주세요.");
					f.ani_val1[i].focus();
					return false;
				}
				if(f.ani_val2[i].value=="" || !fn_isNumber(f.ani_val2[i])){
					alert(parseInt(i+1) + "번째 동작값2를 숫자로만 입력해 주세요.");
					f.ani_val2[i].focus();
					return false;
				}
			}
		}
	    
	    if(f.play_title.value==""){
	    	if(!confirm("재생목록 제목이 비어 있습니다.\n제목을 자동생성 하시겠습니까?")){
	    		f.play_title.focus();
	    		return false;
	    	}
	    	f.play_title.value = getCurrentTime();
	    }
	    
	    if(f.file_nm.value!=""){
	    	
			retValue = fncCheckSkinFile(f.file_nm.value);
			//alert(retValue);
			if (retValue != true) {
				alert(retValue);
				return false;
			}
		}
    	if(!confirm("구성된 내용대로 재생목록을 생성하시겠습니까?")){
    		return false;
    	}
    	
    	f.target = "HiddenFrame";
    	f.encoding = "multipart/form-data";
    	f.action = "/mng/ply/registProc.do";
    	f.submit();
    	f.target = "_self";
    	f.encoding = "application/x-www-form-urlencoded";
    	f.action = "/mng/ply/regist.do";
    	return true;
	}
	
	/**
	 * updatePlayList
	 * 수정할 경우 목록을 생성한다.
	 */
	function updatePlayList(fileName){
		var idVar = new Array();
	    var i =0;

		jQuery("#playList").children("tr").each(function() {
        	idVar[i]= jQuery(this).attr("id");
       		i++;
    	});
        
	    idVar=idVar.sort();

		i=idVar.length;
		
		if(i ==0 ){
			 var select = $("#playList");
		}else{
			 var select = $("#playList tr:last");
		}	        
		
		var sFileName = fileName.toLowerCase();
		var sFileExt = sFileName.substring(sFileName.lastIndexOf(".")+1);
		
		var newElem = $('<tr id="MARK'+i+'">');

		$('<td class="a_center"><input type="hidden" name="ordr" value="'+(i+1)+'"/>'+(i+1)+'</td>').appendTo(newElem);
		if(fncCheckMovie(sFileExt)){
			$('<td class="a_center"><input type="hidden" name="playtime" id="MARK'+ i +'PT" value="0" maxlength="4"/>-</td>').appendTo(newElem);
		}else{
			$('<td class="a_center"><input type="text" style="width:30px" class="a_right" name="playtime" id="MARK'+ i +'PT" value="7" maxlength="4"/>초</td>').appendTo(newElem);
		}
		$('<td><input type="hidden" name="file_id" id="file_id'+ i +'"/><span id="file_nm'+ i +'"></span></td>').appendTo(newElem);
		$('<td class="a_center"><select name=\"skin_yn\" id=\"skin_yn'+i+'\"><option value=\"Y\">사용</option><option value=\"N\">없음</option></select></td>').appendTo(newElem);
		if(fncCheckPicture(sFileExt)){
			$('<td class="a_center"><select name=\"ani_type\" id=\"ani_type'+i+'\"><option value=\"N\">없음</option><option value=\"ZOOM\">확대</option></select></td>').appendTo(newElem);
			$('<td class="a_center"><input type="text" name="ani_val1" id="ani_val1'+ i +'" style="width:30px" class="a_right" value="33" maxlength="3"/>%</td>').appendTo(newElem);
			$('<td class="a_center"><input type="text" name="ani_val2" id="ani_val2'+ i +'" style="width:30px" class="a_right" value="55" maxlength="3"/>%</td>').appendTo(newElem);
		}else{
			$('<td class="a_center"><input type="hidden" name=\"ani_type\" id=\"ani_type'+i+'\" value="N"/>-</td>').appendTo(newElem);
			$('<td class="a_center"><input type="hidden" name="ani_val1" id="ani_val1'+ i +'" value="0"/>-</td>').appendTo(newElem);
			$('<td class="a_center"><input type="hidden" name="ani_val2" id="ani_val2'+ i +'" value="0"/>-</td>').appendTo(newElem);
		}
		$('<td class="a_center"><span class="btn_pack xsmall_green a_center" onclick=moveUpItem("MARK'+ i +'") title="MARK'+ i +'"><a>▲</a></span><span class="btn_pack xsmall_green a_center" onclick=moveDownItem("MARK'+ i +'") title="MARK'+ i +'"><a>▼</a></span></td>').appendTo(newElem);
		$('<td class="a_center"><input type="checkbox" name="chkPlayList" id="chkPlayList'+i+'" value="'+i+'"></td></td>').appendTo(newElem);
		$('</tr>').appendTo(newElem);

       	if(i==0 ){
			$(newElem).appendTo("#playList");
		}else{
			select.after(newElem);
		}
       	fncSetListSeq();
	}	
	
	function fncDel() {
		var f = document.F1;
		var ua = window.navigator.userAgent;

		if (f.ply_ver_id.value == "") {
			alert("${check_ply_ver_id}");
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
			f.action = "/mng/ply/registProc.do";
			f.submit();
			f.target = "_self";
			f.encoding = "application/x-www-form-urlencoded";
			f.action = "/mng/ply/regist.do";
			return true;
		} else {
			return false;
		}
	}
	
	//스킨 삭제
	function fncPlayVerSkinDel(){
		var f = document.F1;
		var sBeforeMode = f.mode.value;

		if (f.ply_ver_id.value == "") {
			alert("${check_ply_ver_id}");
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.target = "HiddenFrame";
			f.mode.value = "S";
			f.encoding = "multipart/form-data";
			f.action = "/mng/ply/registProc.do";
			f.submit();
			f.target = "_self";
			f.mode.value = sBeforeMode;
			f.encoding = "application/x-www-form-urlencoded";
			f.action = "/mng/ply/regist.do";
			return true;
		} else {
			return false;
		}
	}
</script>

				<div id="content">
					<div class="navi">홈 &gt; 파일관리 &gt; 재생목록관리</div>
					<form:form commandName="VoPlayVer" name="F1" id="F1" method="post" onsubmit="return fncSave();">
	        			<form:hidden path="mode"/>
	        			<form:hidden path="ply_ver_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
			            <table class="tbl" summary="재생 목록 정보">
			            <caption class="accessibility">재생 목록 정보</caption>
				            <colgroup>
								<col width="10%"/>
								<col width="90%"/>
							</colgroup>
			                <tbody>
							    <tr>
									<td class="a_top">
										<div id="filetabs">
											<ul>
												<li><a href="#tabs-1">동영상</a></li>
												<li><a href="#tabs-2">이미지</a></li>
												<li><a href="#tabs-3">오디오</a></li>
												<li><a href="#tabs-4">텍스트</a></li>
												<li><a href="#tabs-5">문서</a></li>
												<li><a href="#tabs-6">엑셀</a></li>
												<li><a href="#tabs-7">안내</a></li>
												<li><a href="#tabs-8">기타</a></li>
											</ul>
											<div id="tabs-1" style="height:220px;" class="scroll">
												<table class="plist">
										            <colgroup>
										            	<col width="5%">
										                <col width="">
										            </colgroup>
										            <thead>
										            <tr>
										            	<th>선택</th>
										                <th>파일명</th>
										            </tr>
										            </thead>
										            <tbody>
										            <c:forEach var="playfile" items="${fileList}" varStatus="status">
														<c:if test="${playfile.file_type=='M'}">
											            <tr>
											            	<td class="a_center"><input type="checkbox" name="chkfile" id="chkfile${playfile.file_type}_${playfile.file_id}" value="${playfile.file_id}"></td>
											            	<td style="word-break:break-all;"><label for="chkfile${playfile.file_type}_${playfile.file_id}"><span id="${playfile.file_id}">${playfile.org_file_nm}</span></label></td>
											            </tr>
											            </c:if>
													</c:forEach>
										            </tbody>
									            </table>
											</div>
											<div id="tabs-2" style="height:220px;" class="scroll">
												<table class="plist" style="table-layout:fixed">
										            <colgroup>
										            	<col width="5%">
										                <col width="">
										            </colgroup>
										            <thead>
										            <tr>
										            	<th>선택</th>
										                <th>파일명</th>
										            </tr>
										            </thead>
										            <tbody>
										            <c:forEach var="playfile" items="${fileList}" varStatus="status">
														<c:if test="${playfile.file_type=='I'}">
											            <tr>
											            	<td class="a_center"><input type="checkbox" name="chkfile" id="chkfile${playfile.file_type}_${playfile.file_id}" value="${playfile.file_id}"></td>
											            	<td style="word-break:break-all;"><label for="chkfile${playfile.file_type}_${playfile.file_id}"><span id="${playfile.file_id}">${playfile.org_file_nm}</span></label></td>
											            </tr>
											            </c:if>
													</c:forEach>
										            </tbody>
									            </table>
											</div>
											<div id="tabs-3" style="height:220px;" class="scroll">
												<table class="plist">
										            <colgroup>
										            	<col width="5%">
										                <col width="">
										            </colgroup>
										            <thead>
										            <tr>
										            	<th>선택</th>
										                <th>파일명</th>
										            </tr>
										            </thead>
										            <tbody>
										            <c:forEach var="playfile" items="${fileList}" varStatus="status">
														<c:if test="${playfile.file_type=='A'}">
											            <tr>
											            	<td class="a_center"><input type="checkbox" name="chkfile" id="chkfile${playfile.file_type}_${playfile.file_id}" value="${playfile.file_id}"></td>
											            	<td style="word-break:break-all;"><label for="chkfile${playfile.file_type}_${playfile.file_id}"><span id="${playfile.file_id}">${playfile.org_file_nm}</span></label></td>
											            </tr>
											            </c:if>
													</c:forEach>
										            </tbody>
									            </table>
											</div>
											<div id="tabs-4" style="height:220px;" class="scroll">
												<table class="plist" style="table-layout:fixed">
										            <colgroup>
										            	<col width="5%">
										                <col width="">
										            </colgroup>
										            <thead>
										            <tr>
										            	<th>선택</th>
										                <th>파일명</th>
										            </tr>
										            </thead>
										            <tbody>
										            <c:forEach var="playfile" items="${fileList}" varStatus="status">
														<c:if test="${playfile.file_type=='T'}">
											            <tr>
											            	<td class="a_center"><input type="checkbox" name="chkfile" id="chkfile${playfile.file_type}_${playfile.file_id}" value="${playfile.file_id}"></td>
											            	<td style="word-break:break-all;"><label for="chkfile${playfile.file_type}_${playfile.file_id}"><span id="${playfile.file_id}">${playfile.org_file_nm}</span></label></td>
											            </tr>
											            </c:if>
													</c:forEach>
										            </tbody>
									            </table>
											</div>
											<div id="tabs-5" style="height:220px;" class="scroll">
												<table class="plist" style="table-layout:fixed">
										            <colgroup>
										            	<col width="5%">
										                <col width="">
										            </colgroup>
										            <thead>
										            <tr>
										            	<th>선택</th>
										                <th>파일명</th>
										            </tr>
										            </thead>
										            <tbody>
										            <c:forEach var="playfile" items="${fileList}" varStatus="status">
														<c:if test="${playfile.file_type=='D'}">
											            <tr>
											            	<td class="a_center"><input type="checkbox" name="chkfile" id="chkfile${playfile.file_type}_${playfile.file_id}" value="${playfile.file_id}"></td>
											            	<td style="word-break:break-all;"><label for="chkfile${playfile.file_type}_${playfile.file_id}"><span id="${playfile.file_id}">${playfile.org_file_nm}</span></label></td>
											            </tr>
											            </c:if>
													</c:forEach>
										            </tbody>
									            </table>
											</div>
											<div id="tabs-6" style="height:220px;" class="scroll">
												<table class="plist" style="table-layout:fixed">
										            <colgroup>
										            	<col width="5%">
										                <col width="">
										            </colgroup>
										            <thead>
										            <tr>
										            	<th>선택</th>
										                <th>파일명</th>
										            </tr>
										            </thead>
										            <tbody>
										            <c:forEach var="playfile" items="${fileList}" varStatus="status">
														<c:if test="${playfile.file_type=='E'}">
											            <tr>
											            	<td class="a_center"><input type="checkbox" name="chkfile" id="chkfile${playfile.file_type}_${playfile.file_id}" value="${playfile.file_id}"></td>
											            	<td style="word-break:break-all;"><label for="chkfile${playfile.file_type}_${playfile.file_id}"><span id="${playfile.file_id}">${playfile.org_file_nm}</span></label></td>
											            </tr>
											            </c:if>
													</c:forEach>
										            </tbody>
									            </table>
											</div>
											<div id="tabs-7" style="height:220px;" class="scroll">
												<table class="plist" style="table-layout:fixed">
										            <colgroup>
										            	<col width="5%">
										                <col width="">
										            </colgroup>
										            <thead>
										            <tr>
										            	<th>선택</th>
										                <th>파일명</th>
										            </tr>
										            </thead>
										            <tbody>
										            <c:forEach var="playfile" items="${fileList}" varStatus="status">
														<c:if test="${playfile.txt_yn=='Y'}">
											            <tr>
											            	<td class="a_center"><input type="checkbox" name="chkfile" id="chkfileF_${playfile.file_id}" value="F_${playfile.file_id}"></td>
											            	<td style="word-break:break-all;"><label for="chkfileF_${playfile.file_id}"><span id="F_${playfile.file_id}">(안내) ${playfile.org_file_nm}</span></label></td>
											            </tr>
											            </c:if>
													</c:forEach>
										            </tbody>
									            </table>
											</div>
											<div id="tabs-8" style="height:220px;" class="scroll">
												<table class="plist" style="table-layout:fixed">
										            <colgroup>
										            	<col width="5%">
										                <col width="">
										            </colgroup>
										            <thead>
										            <tr>
										            	<th>선택</th>
										                <th>파일명</th>
										            </tr>
										            </thead>
										            <tbody>
										            <c:forEach var="playfile" items="${fileList}" varStatus="status">
														<c:if test="${playfile.file_type=='X'}">
											            <tr>
											            	<td class="a_center"><input type="checkbox" name="chkfile" id="chkfile${playfile.file_type}_${playfile.file_id}" value="${playfile.file_id}"></td>
											            	<td style="word-break:break-all;"><label for="chkfile${playfile.file_type}_${playfile.file_id}"><span id="${playfile.file_id}">${playfile.org_file_nm}</span></label></td>
											            </tr>
											            </c:if>
													</c:forEach>
										            </tbody>
									            </table>
											</div>
										</div>
									</td>
								</tr>
							    <tr>
									<td class="a_center">
										<span class="btn_pack small_sky a_center" onclick="fncMovePlayList();"><a>▼</a></span>
										<span class="btn_pack small_sky a_center" onclick="fncMoveFileList();"><a>▲</a></span>
									</td>
								</tr>
			                    <tr>
			                        <th>재생목록</th>
							    </tr>						
							    <tr>
			                        <td class="a_top">
			                        	<table class="plist">
								            <colgroup>
								            	<col width="10%">
								                <col width="90%">
								            </colgroup>
								            <tbody>
								            <tr>
								            	<th>제목</th>
								                <td><input type="text" name="play_title" class="w95p" value="${VoPlayVer.ply_title}"></td>
								            </tr>
								            <tr>
								            	<th>스킨</th>
								                <td>
								                	<input type="file" name="file_nm" id="file0" class="input file"/>
								                	<c:if test="${not empty VoPlayVer.skin_org_file_nm}">
								                		<a href="/common/downLoadFile.do?type=plyskin&ply_ver_id=${VoPlayVer.ply_ver_id}">${VoPlayVer.skin_org_file_nm}</a>
								                		<span class="btn_pack small_sky"><a onclick="fncPlayVerSkinDel();" title="삭제" style="cursor:pointer">삭제</a></span>
								                	</c:if>
								                </td>
								            </tr>
								            <c:if test="${VoPlayVer.mode=='U'}">
								            <tr>
								            	<th>사용여부</th>
								                <td>
								                	<select name="use_yn">
								                		<option value="Y"<c:if test="${VoPlayVer.use_yn=='Y'}"> selected</c:if>>사용중</option>
								                		<option value="N"<c:if test="${VoPlayVer.use_yn!='Y'}"> selected</c:if>>사용중지</option>
								                	</select>
								                </td>
								            </tr>
								            </c:if>
								            </tbody>
							            </table>
			                        	<div style="height:220px" class="scroll">
				                        <table class="plist">
								            <colgroup>
								            	<col width="5%">
								            	<col width="8%">
								                <col width="">
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
								            	<th>시간</th>
								                <th>파일명</th>
								                <th>스킨</th>
								                <th>동작</th>
								                <th>동작값1</th>
								                <th>동작값2</th>
								                <th>순서</th>
								                <th>삭제</th>
								            </tr>
								            </thead>
								            <tbody id="playList" class="scroll">
								            </tbody>
							            </table>
							            </div>
			                        </td>
							    </tr>
			                </tbody>
			            </table>

			            <div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
				            	<a class="button" onclick="fncSave();" title="저장">저장</a>
				            	<c:if test="${VoPlayVer.mode=='U'}">
					            	<a class="button" href="/mng/ply/view.do<c:if test="${not empty retParam}">?${retParam}&ply_ver_id=${VoPlayVer.ply_ver_id}</c:if>" title="취소">취소</a>
					            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
				            	</c:if>
			            	</c:if>
			            	<a class="button" href="/mng/ply/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
			        <c:if test="${not empty playFile}">
			            <c:forEach var="playfile" items="${playFile}" varStatus="status">
			            	<c:set var="file_id" value="${playfile.file_id}"/>
			            	<c:set var="org_file_nm" value="${playfile.org_file_nm}"/>
			            	<c:if test="${playfile.txt_yn=='Y'}">
			            		<c:set var="file_id" value="F_${playfile.file_id}"/>
			            		<c:set var="org_file_nm" value="(안내) ${playfile.org_file_nm}"/>
			            	</c:if>
				            <script>
				            	updatePlayList('${playfile.org_file_nm}');
				            	var trIdx ="${status.index}";
				            	document.getElementById("MARK"+ trIdx +"PT").value="${playfile.ply_tm}";
				            	document.getElementById("file_id"+ trIdx).value="${file_id}";
				            	document.getElementById("file_nm"+ trIdx).innerText="${org_file_nm}";
				            	document.getElementById("skin_yn"+ trIdx).value="${playfile.skin_yn}";
				            	document.getElementById("ani_type"+ trIdx).value="${playfile.ani_type}";
				            	document.getElementById("ani_val1"+ trIdx).value="${playfile.ani_val1}";
				            	document.getElementById("ani_val2"+ trIdx).value="${playfile.ani_val2}";
				            	
				            </script>
						</c:forEach>
			        </c:if>			        
				</div>
