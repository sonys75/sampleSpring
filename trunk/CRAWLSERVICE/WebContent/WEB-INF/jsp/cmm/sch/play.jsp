<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="imagetoolbar" content="no" />

<TITLE>재생목록 검색</TITLE>

<link rel="stylesheet" type="text/css" href="/css/common.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/css/smoothness/jquery-ui-1.10.3.custom.css" media="screen">
<link rel="stylesheet" type="text/css" href="/css/jquery.colorpicker.css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
</head>
<body>
<script type="text/javascript">
	function fncAddPlayVerId(ply_ver_id,ply_title){
        var ply_seq = document.getElementById("ply_seq").value;
        if(ply_seq==""){
        	alert("재생목록 적용 위치가 없습니다.\n창을 닫고 다시 검색해 주세요.");
        	return;
        }
        	
		//현재 재생목록 아이디
		var crnt_ply_ver_id = $("#ply_ver_id"+ply_seq,top.document).val();
		
		if(ply_ver_id != crnt_ply_ver_id){
			$("#ply_ver_id"+ply_seq,top.document).val(ply_ver_id);
			$("#ply_title"+ply_seq,top.document).val(ply_title);
			$("#playlistmodYn",top.document).val("Y");
		}
		
		$(".ui-button-text").click();
	}
	
    $('#search_btn').click(function() {
    	var f = document.ajaxform;
    	$(".ui-button-text").click();
    	openDivModal("재생목록 검색","/cmm/sch/play.do?ply_seq="+f.ply_seq.value+"&sch_fld="+f.sch_fld.value+"&sch_word="+f.sch_word.value+"",640,480);
    });

    
    function fncKeyPress(){
	    if( event.keyCode == 13){
	    	var f = document.ajaxform;
	    	$(".ui-button-text").click();
	    	openDivModal("재생목록 검색","/cmm/sch/play.do?ply_seq="+f.ply_seq.value+"&sch_fld="+f.sch_fld.value+"&sch_word="+f.sch_word.value+"",640,480);
	    }
	}
</script>
					<form name="ajaxform" id="ajaxform" method="post" action="/cmm/sch/play.do" onsubmit="return false;">
					<input type="hidden" id="ply_seq" value="${ply_seq}"/>
			        	<!-- 검색 Str -->
		                <table class="tbl">
		                <caption class="accessibility">목록검색</caption>
		                    <colgroup>
								<col />
		                    </colgroup>
		                    <tr>
		                        <td class="a_right">
		                        	<select name="sch_fld" id="sch_fld">
		                        		<option value="PLY_TITLE"<c:if test="${VoComm.sch_fld=='PLY_TITLE'}"> selected</c:if>>제목</option>
		                        		<option value="USER_ID"<c:if test="${VoComm.sch_fld=='USER_ID'}"> selected</c:if>>배포아이디</option>
		                        	</select>
		                            <input type="text" name="sch_word" id="sch_word" title="검색어 입력" value="${VoComm.sch_word}" onkeypress="fncKeyPress()"/>
		                            <span class="btn_pack small_sky" id="search_btn"><a style="cursor:pointer" title="검색">검색</a></span>
		                        </td>
		                    </tr>
		                </table>
			            <!-- 검색 End -->
			        </form>
					<div class="count">총 <b><fmt:formatNumber value="${totalCount}"/></b>건의 검색결과가 있습니다.</div>
		            <table class="tbl" summary="재생목록입니다.">
		            <caption class="accessibility">재생목록</caption>
		            <colgroup>
						<col width="15%"/>
		                <col />
					</colgroup>
		            	<thead>
							<tr>
								<th>번호</th>
								<th>재생목록</th>
							</tr>
						</thead>
		                <tbody>
		                <c:choose>
		                	<c:when test="${not empty list}">
		                		<c:forEach var="result" items="${list}" varStatus="status">
		                        <tr>
		                            <td class="a_center"><fmt:formatNumber value="${totalCount - status.index}"/></td>
		                            <td><a style="cursor:pointer" onclick="fncAddPlayVerId('${result.ply_ver_id}','${result.ply_title}');"><c:out value="${result.ply_title}"/></a></td>
		                        </tr>
		                        </c:forEach>
		                	</c:when>
		                	<c:otherwise>
		                        <tr>
		                            <td colspan="2">검색 결과가 없습니다.</td>
		                        </tr>	                	
		                	</c:otherwise>
		                </c:choose>
		                </tbody>
		            </table>
</body>
</html>