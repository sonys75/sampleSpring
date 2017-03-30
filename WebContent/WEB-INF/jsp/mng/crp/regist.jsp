<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="corp_id_empty"/>
<spring:message code="Check.field.required_u" arguments="회사명" var="corp_nm_required"/>
<spring:message code="Check.field.required_l" arguments="전화번호" var="corp_tel_required"/>
<spring:message code="Check.field.wrong_l" arguments="전화번호" var="corp_tel_wrong"/>
<spring:message code="Check.field.required_l" arguments="우편번호 검색을 통해 주소" var="corp_post_required"/>
<spring:message code="Check.field.required_l" arguments="상세주소" var="corp_adr_dsc_required"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>
<script type="text/javascript">
	function previewImage(targetObj, previewId) {
	    var preview = document.getElementById(previewId); //div id   
	    var ua = window.navigator.userAgent;
	    
	    preview.style.display="none";
	    
	    if (ua.indexOf("MSIE") > -1) {//ie일때
			targetObj.select();
	
			try {
				var src = document.selection.createRange().text; // get file full path 
	
				var ie_preview_error = document.getElementById("ie_preview_error_" + previewId);
	
				if (ie_preview_error) preview.removeChild(ie_preview_error); //error가 있으면 delete

				var img = document.getElementById(previewId); //이미지가 뿌려질 곳 
				
				//이미지 로딩, sizingMethod는 div에 맞춰서 사이즈를 자동조절 하는 역할
				img.style.filter ="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + src + "', sizingMethod='scale')"; 
			} catch (e) {
				if (!document.getElementById("ie_preview_error_" + previewId)) {
					var info = document.createElement("<p>");
					info.id = "ie_preview_error_" + previewId;
					info.innerHTML = "a";
					preview.insertBefore(info, null);
				}
			}
		} else { //ie가 아닐때
			var files = targetObj.files;
			for ( var i = 0; i < files.length; i++) {
				var file = files[i];
				var imageType = /image.*/; //이미지 파일일경우만.. 뿌려준다.
				
				if (!file.type.match(imageType)) continue;
	
				var prevImg = document.getElementById("prev_" + previewId); 
				//이전에 미리보기가 있다면 삭제
				if (prevImg) preview.removeChild(prevImg);
	
				var img = document.createElement("img"); //크롬은 div에 이미지가 뿌려지지 않는다. 그래서 자식Element를 만든다.
				img.id = "prev_" + previewId;
				img.classList.add("obj");
				
				img.file = file;
				img.style.width = '200px'; //기본설정된 div의 안에 뿌려지는 효과를 주기 위해서 div크기와 같은 크기를 지정해준다.
				img.style.height = '45px';
				
				preview.appendChild(img);
				
				if (window.FileReader) { // FireFox, Chrome, Opera 확인.
					var reader = new FileReader();
					reader.onloadend = (function(aImg) {
						return function(e) {
							aImg.src = e.target.result;
						};
					})(img);
					reader.readAsDataURL(file);
				} else { 
					//safari is not supported FileReader
					//alert('not supported FileReader');
					if (!document.getElementById("sfr_preview_error_"
							+ previewId)) {
						var info = document.createElement("p");
						info.id = "sfr_preview_error_" + previewId;
						info.innerHTML = "not supported FileReader";
						preview.insertBefore(info, null);
					}
				}
			}
		}
	    preview.style.display="";
	}

	function fncSave() {
		var f = document.F1;

		if (f.mode.value == "U") {
			if (f.corp_id.value == "") {
				alert("${corp_id_empty}");
				history.back();
				return false;
			}
		}

		if (f.corp_nm.value == "") {
			alert("${corp_nm_required}");
			f.corp_nm.focus();
			return false;
		}

		for ( var i = 0; i < document.F1.elements.length; i++) {
			if (document.F1.elements[i].type == "file") {
				if (eval("f." + document.F1.elements[i].name + ".value") != "") {
					retValue = fncCheckImgFile(eval("f." + document.F1.elements[i].name + ".value"));
					if (retValue != true) {
						alert(retValue);
						return false;
					}
					break;
				}
			}
		}

		if (f.corp_tel.value == "" || !fncChkPhone(f.corp_tel)) {
			alert("${corp_tel_required}");
			f.corp_tel.focus();
			return false;
		}

		if (!fncValidateTelePhone(f.corp_tel.value)) {
			alert("${corp_tel_wrong}");
			f.corp_tel.focus();
			return false;
		}
		/* 
		if (f.corp_post.value == "") {
			alert("${corp_post_required}");
			fncFindPostNo('corp');
			return false;
		}

		if (f.corp_adr.value == "") {
			alert("${corp_post_required}");
			fncFindPostNo('corp');
			return false;
		}

		if (f.corp_adr_dsc.value == "") {
			alert("${corp_adr_dsc_required}");
			f.corp_adr_dsc.focus();
			return false;
		}
		*/
		f.target = "HiddenFrame";
		f.encoding = "multipart/form-data";
		f.action = "/mng/crp/registProc.do";
		f.submit();
		f.target = "_self";
		f.encoding = "application/x-www-form-urlencoded";
		f.action = "/mng/crp/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.corp_id.value == "") {
			alert("${corp_id_empty}");
			history.back();
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.target = "HiddenFrame";
			f.mode.value = "D";
			f.action = "/mng/crp/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/mng/crp/regist.do";
			return true;
		} else {
			return false;
		}
	}
	/* 
	//우편번호 찾기
	function fncFindPostNo(sch_tag) {
		fncWinScrollOpen("/common/post.do?sch_tag=" + sch_tag, "FindPostNo",
				"450", "400");
	}
	*/
</script>

				<div id="content">
					<div class="navi">홈 &gt; 회원관리 &gt; 회사관리</div>
					<form:form commandName="VoCorpInfo" name="F1" id="F1" method="post" onsubmit="return fncSave();" enctype="multipart/form-data">
	        			<form:hidden path="mode"/>
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
									<th>로고파일</th>
			                        <td colspan="3">
			                        	<c:if test="${not empty VoCorpInfo.corp_logo_nm}">
						  					<a href="/common/downLoadFile.do?type=corp_logo&corp_id=${VoCorpInfo.corp_id}"><img src="${VoCorpInfo.corp_logo_path}/${VoCorpInfo.corp_logo_nm}" width="200" height="45"/></a>

						  					<input type="checkbox" name="delLogo" value="Y"> 로고삭제
						  					<br>
			                        	</c:if>
			                        	<div id='previewId' style='width:200px;height:45px;display:none'></div>
			                        	<form:input path="corp_logo_file" type="file" onchange="previewImage(this,'previewId')"/>
			                        </td>
							    </tr>
			                    <tr>
									<th>회사명</th>
			                        <td>
			                        	<form:input path="corp_nm" maxlength="20" title="회사명" cssClass="w200"/>
			                        </td>
			                        <th>상위 회사명</th>
			                        <td>
			                        	<form:hidden path="up_corp_id"/>
			                        	<form:input path="up_corp_nm" maxlength="20" title="회사명" cssClass="w200" readOnly="true"/>
			                        	<c:if test="${VoCorpInfo.corp_id != userInfoVO.corp_id && LowCorpCnt == 0}">
			                        		<span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModal('회사검색','/cmm/sch/corp.do',500,400);" title="검색">검색</a></span>
			                        	</c:if>
			                        </td>
			                    </tr>
								<tr>
									<th>전화번호</th>
			                        <td>
			                        	<form:input path="corp_tel" maxlength="15" title="전화번호"/>
			                        	* 예)02-0000-0000
			                        </td>
									<th>홈페이지</th>
			                        <td>
			                        	<form:input path="corp_url" maxlength="50" title="홈페이지" cssClass="w200"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>주소</th>
			                        <td colspan="3">
			                        	<table class="plist" style="table-layout:fixed">
								            <colgroup>
								            	<col width="10%">
								                <col width="">
								            </colgroup>
								            <tbody>
								            <tr>
								            	<th>우편번호</th>
								            	<td><form:input path="corp_post" maxlength="7" title="우편번호" cssClass="w80"/></td>
								            </tr>
								            <tr>
								            	<th>주 소</th>
								            	<td><form:input path="corp_adr" maxlength="25" title="주소" cssClass="w300"/></td>
								            </tr>
								            <tr>
								            	<th>상세주소</th>
								            	<td><form:input path="corp_adr_dsc" maxlength="50" title="상세주소" cssClass="w400"/></td>
								            </tr>
								            </tbody>
								        </table>
			                        </td>
							    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncSave();" title="저장">저장</a>
			            	<c:if test="${VoCorpInfo.mode=='U'}">
			            	<a class="button" href="/mng/crp/view.do<c:if test="${not empty retParam}">?${retParam}&corp_id=${VoCorpInfo.corp_id}</c:if>" title="취소">취소</a>
			            	</c:if>
			            	<a class="button" href="/mng/crp/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            	<c:if test="${VoCorpInfo.corp_id != userInfoVO.corp_id && LowCorpCnt == 0}">
			            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
			            	</c:if>
			            </div> 
			        </form:form>
				</div>
