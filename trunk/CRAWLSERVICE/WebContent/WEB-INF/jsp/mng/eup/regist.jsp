<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:message code="Check.field.hidden.empty" var="stp_id_empty"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>

<script type="text/javascript">
	$(document).ready(function() {
		fncShowCallInfo();
	});
	var strSelectHour =  "<option value=\"00\">00</option>"
					   	+"<option value=\"01\">01</option>"
					   	+"<option value=\"02\">02</option>"
					   	+"<option value=\"03\">03</option>"
					   	+"<option value=\"04\">04</option>"
					   	+"<option value=\"05\">05</option>"
					   	+"<option value=\"06\">06</option>"
					   	+"<option value=\"07\">07</option>"
					   	+"<option value=\"08\">08</option>"
					   	+"<option value=\"09\">09</option>"
					   	+"<option value=\"10\">10</option>"
					   	+"<option value=\"11\">11</option>"
					   	+"<option value=\"12\">12</option>"
					   	+"<option value=\"13\">13</option>"
					   	+"<option value=\"14\">14</option>"
					   	+"<option value=\"15\">15</option>"
					   	+"<option value=\"16\">16</option>"
					   	+"<option value=\"17\">17</option>"
					   	+"<option value=\"18\">18</option>"
					   	+"<option value=\"19\">19</option>"
					   	+"<option value=\"20\">20</option>"
					   	+"<option value=\"21\">21</option>"
					   	+"<option value=\"22\">22</option>"
					   	+"<option value=\"23\">23</option>"
					   	+"<option value=\"24\">24</option>";
	
	var strSelectMin =   "<option value=\"00\">00</option>"
						+"<option value=\"05\">05</option>"
						+"<option value=\"10\">10</option>"
						+"<option value=\"15\">15</option>"
						+"<option value=\"20\">20</option>"
						+"<option value=\"25\">25</option>"
						+"<option value=\"30\">30</option>"
						+"<option value=\"35\">35</option>"
						+"<option value=\"40\">40</option>"
						+"<option value=\"45\">45</option>"
						+"<option value=\"50\">50</option>"
						+"<option value=\"55\">55</option>";
						
	var maxPlayCnt = 24;
	
	function fncSave() {
		var f = document.F1;
		var tmpStart,tmpEnd,tmpBefore;
		
		if (f.mode.value == "U") {
			if (f.stp_id.value == "") {
				alert("${stp_id_empty}");
				return false;
			}
		}

		if(f.noti_yn.value=="Y"){
			tmpStart = f.noti_start_h.value+""+f.noti_start_m.value;
			tmpEnd = f.noti_end_h.value+""+f.noti_end_m.value;
			if(parseInt(tmpStart)>=parseInt(tmpEnd)){
				alert("주요공지 시작시간과 종료시간을 확인해 주세요.\n종료시간은 시작시간보다 커야 합니다.");
				return false;
			}
		}

		if(f.news_yn.value=="Y"){
			tmpStart = f.news_start_h.value+""+f.news_start_m.value;
			tmpEnd = f.news_end_h.value+""+f.news_end_m.value;
			if(parseInt(tmpStart)>=parseInt(tmpEnd)){
				alert("뉴스 시작시간과 종료시간을 확인해 주세요.\n종료시간은 시작시간보다 커야 합니다.");
				return false;
			}
		}
		
		if(f.playlistcnt.value=="0"){
			alert("재생 목록이 없습니다.\n재생목록을 선택해 주세요.");
			fncAddPlay();
			return false;
		}else if(f.playlistcnt.value=="1"){
			if(f.ply_ver_id.value==""){
				alert("재생 목록을 추가한 후 재생목록 선택해 주세요.");
				return false;
			}
			tmpStart = f.ply_start_h.value+""+f.ply_start_m.value;
			tmpEnd = f.ply_end_h.value+""+f.ply_end_m.value;
			if(parseInt(tmpStart)>=parseInt(tmpEnd)){
				alert("재생목록의 시작시간과 종료시간을 확인해 주세요.\n종료시간은 시작시간보다 커야 합니다.");
				return false;
			}
		}else{
			tmpBefore = "";
			for(var i=0;i<f.ply_ver_id.length;i++){
				if(f.ply_ver_id[i].value==""){
					alert("재생 목록을 추가한 후 재생목록 선택해 주세요.");
					return false;
				}
				tmpStart = f.ply_start_h[i].value+""+f.ply_start_m[i].value;
				tmpEnd = f.ply_end_h[i].value+""+f.ply_end_m[i].value;

				if(parseInt(tmpStart)>=parseInt(tmpEnd)){
					alert( parseInt(i+1) + "번째 재생목록의 시작시간과 종료시간을 확인해 주세요.\n종료시간은 시작시간보다 커야 합니다.");
					return false;
				}
				
				if(tmpBefore!=""){
					if(tmpStart != tmpBefore){
						alert( parseInt(i+1) + "번째 재생목록의 시작시간과 "+parseInt(i)+"번째 종료시간을 확인해 주세요.\n이전 종료시간과 다음 시작시간은 같아야 합니다.");
						return false;
					}
				}
				tmpBefore = tmpEnd;
			}
		}
		
		f.target = "HiddenFrame";
		f.action = "/mng/eup/registProc.do";
		f.submit();
		f.target = "_self";
		f.action = "/mng/eup/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.stp_id.value == "") {
			alert("${stp_id_empty}");
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.target = "HiddenFrame";
			f.mode.value = "D";
			f.action = "/mng/eup/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/mng/eup/regist.do";
			return true;
		} else {
			return false;
		}
	}

	//소유자 찾기
	function fncFindUser(){
		fncWinScrollOpen("/user/user_find.do", "FindWindow", "450", "400");
	}
	
	//재생목록 찾기
	function fncFindPlayVer(seq){
		fncWinScrollOpen("/equip/play_find.do?ply_seq="+seq, "FindWindow", "450", "400");
	}
	
	//기기이전
	function fncEquipTrans(mac_seq){
		var stp_id = document.F1.stp_id.value;
		if(stp_id=="" || mac_seq==""){
			alert("기기 이전할 데이터가 없습니다.");
			return false;
		}
		fncWinScrollOpen("/mng/eup/equip_trans.do?stp_id="+stp_id+"&mac_seq="+mac_seq, "EquipTrans", "450", "400");
	}
	
	//맥 삭제
	function fncEquipMacDel(mac_seq){
		var f = document.F1;

		if (f.stp_id.value == "") {
			alert("${stp_id_empty}");
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.target = "HiddenFrame";
			f.mode.value = "M";
			f.mac_seq.value = mac_seq;
			f.action = "/mng/eup/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/mng/eup/regist.do";
			return true;
		} else {
			return false;
		}
	}
	
	//호출기세팅정보
	function fncShowCallInfo(){
		var f = document.F1;
		if(f.call_cd.value!=""){
			document.getElementById("callInfo").style.display="";
		}else{
			document.getElementById("callInfo").style.display="none";
		}
	}
	
    //재생목록 추가 버튼
    function fncAddPlay(){
        var f = document.F1;
			        	
        var objTbl	= document.getElementById("playlist");
        if(f.playlistcnt.value>=maxPlayCnt || objTbl.rows.length>=maxPlayCnt){
            alert("재생목록 등록은 최대 "+maxPlayCnt+"개까지 가능합니다.");
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
        objCell.innerHTML   ="<input type=\"hidden\" name=\"ply_ver_id\" id=\"ply_ver_id"+parseInt(f.playlistcnt.value)+"\">"
        					+"<input type=\"hidden\" name=\"ply_seq\" id=\"ply_seq"+parseInt(f.playlistcnt.value)+"\">"
        					+"<input type=\"text\" name=\"ply_title\" id=\"ply_title"+parseInt(f.playlistcnt.value)+"\" style=\"width:300px\"class=\"input file\" readOnly=\"true\" onclick=\"openDivModal('재생목록 검색','/cmm/sch/play.do?ply_seq="+parseInt(f.playlistcnt.value)+"',640,480);\"/>"
        					+" <span class=\"btn_pack small_sky\"><a onclick=\"openDivModal('재생목록 검색','/cmm/sch/play.do?ply_seq="+parseInt(f.playlistcnt.value)+"',640,480);\" title=\"재생목록 선택\" style=\"cursor:pointer\">재생목록 선택</a></span>"
        					+" <select onchange=\"fncListModYn();\" name=\"ply_start_h\" id=\"ply_start_h"+parseInt(f.playlistcnt.value)+"\">"+strSelectHour+"</select>"
        					+" <select onchange=\"fncListModYn();\" name=\"ply_start_m\" id=\"ply_start_m"+parseInt(f.playlistcnt.value)+"\">"+strSelectMin+"</select>"
        					+" ~"
        					+" <select onchange=\"fncListModYn();\" name=\"ply_end_h\" id=\"ply_end_h"+parseInt(f.playlistcnt.value)+"\">"+strSelectHour+"</select>"
        					+" <select onchange=\"fncListModYn();\" name=\"ply_end_m\" id=\"ply_end_m"+parseInt(f.playlistcnt.value)+"\">"+strSelectMin+"</select>";

		//재생목록이 1개 이상이면 그 전 재생목록의 종료시간을 지금 재생목록의 시작시간으로 맞춘다. 
		if(f.playlistcnt.value>0){
			var nPrevNo = parseInt(f.playlistcnt.value)-1;
			var nThisNo = parseInt(f.playlistcnt.value);
			//현재 목록 시작시간 변경			
			document.getElementById("ply_start_h"+nThisNo).value = document.getElementById("ply_end_h"+nPrevNo).value;
			document.getElementById("ply_start_m"+nThisNo).value = document.getElementById("ply_end_m"+nPrevNo).value;
			//현재 목록 종료시간 변경
			document.getElementById("ply_end_h"+nThisNo).value = document.getElementById("ply_start_h"+nThisNo).value;
			document.getElementById("ply_end_m"+nThisNo).value = document.getElementById("ply_start_m"+nThisNo).value;
		}

        f.playlistcnt.value=parseInt(f.playlistcnt.value)+1;
        return;
    }
    
    //재생목록 삭제 버튼
    function fncDelPlay(){
        var f = document.F1;
        var objTbl	= document.getElementById("playlist");
        var objrow	= "";
        var nRowNo;
        
        if(objTbl.rows.length==0){
            return;
        }
        
        nRowNo = parseInt(f.playlistcnt.value) - 1;
        objrow  =   eval("document.getElementById('ply_seq"+ nRowNo +"')");

        if(objrow.value != ""){
            if(!confirm("등록된 재생목록을 삭제하시겠습니까?")){
            	return;
            }
            fncListModYn();
        }
        
        objTbl.deleteRow(objTbl.rows.length-1);
        f.playlistcnt.value=parseInt(f.playlistcnt.value)-1;
        
        return;
    }
    
	//재생목록 수정 여부 등록
    function fncListModYn(){
    	var f = document.F1;
    	f.playlistmodYn.value = "Y";
	}
	

	jQuery(function($) {
		$.datepicker.regional['ko'] = {
			closeText : '닫기',
			prevText : '이전',
			nextText : '다음',
			currentText : '오늘',
			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
					'9월', '10월', '11월', '12월' ],
			monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'10', '11', '12' ],
			dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
			weekHeader : 'Wk',
			dateFormat : 'yy-mm-dd',
			firstDay : 0,
			isRTL : false,
			showMonthAfterYear : true,
			yearSuffix : ''
		};
		$.datepicker.setDefaults($.datepicker.regional['ko']);

		$('#stp_set_dt').datepicker();

	});
</script>

				<div id="content">
					<div class="navi">홈 &gt; 기기관리 &gt; 기기정보</div>
					<form:form commandName="VoStpInfo" name="F1" id="F1" method="post" onsubmit="return fncSave();">
	        			<form:hidden path="mode"/>
	        			<form:hidden path="stp_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
				        <input type="hidden" id="mac_seq" name="mac_seq"/>
				        <input type="hidden" id="playlistcnt" name="playlistcnt" value="0"/>
				        <input type="hidden" id="playlistmodYn" name="playlistmodYn"/>
			            <table class="tbl" summary="기기 정보">
			            <caption class="accessibility">기기 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th><a title="<c:out value="${VoStpInfo.stp_id}"/>">기기명칭</a></th>
			                        <td>
			                            <form:input path="stp_title" maxlength="50" cssClass="w200"/>
			                        </td>
									<th>소유회사</th>
			                        <td>
			                            <form:hidden path="corp_id"/>
			                        	<form:input path="corp_nm" maxlength="20" title="회사명" cssClass="w200" readOnly="true" onclick="openDivModal('회사검색','/cmm/sch/corp.do',500,400);"/>
			                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModal('회사검색','/cmm/sch/corp.do',500,400);" title="검색">검색</a></span>			        
							        </td>
			                    </tr>
			                    <c:if test="${VoStpInfo.mode=='U'}">
								<tr>
			                        <th>설치일</th>
			                        <td>
			                        	<sec:authorize access="hasRole('SITEADMIN')">
			                        	<form:input path="stp_set_dt" maxlength="8" title="설치일" cssClass="w80" readOnly="true"/>
			                        	</sec:authorize>
			                        	<sec:authorize access="!hasRole('SITEADMIN')">
			                        	<c:out value="${VoStpInfo.stp_set_dt}"/>
			                        	<form:hidden path="stp_set_dt" maxlength="8" title="설치일" readOnly="true"/>
			                        	</sec:authorize>
			                        </td>
									<th>OS / APP Ver</th>
			                        <td>
			                        	<c:out value="${VoStpInfo.stp_os}"/> <c:if test="${not empty VoStpInfo.stp_app_ver}">/ <c:out value="${VoStpInfo.stp_app_ver}"/></c:if>
			                        </td>
			                    </tr>
			                    </c:if>
							    <tr>
									<th>설치유형</th>
			                        <td>
			                        	<sec:authorize access="hasRole('SITEADMIN')">
		                       			<select name="stp_type" id="stp_type">
		                       				<option value="R"<c:if test="${VoStpInfo.stp_type=='R'}"> selected</c:if>>임대</option>
		                       				<option value="S"<c:if test="${VoStpInfo.stp_type=='S'}"> selected</c:if>>판매</option>
		                       				<option value="T"<c:if test="${VoStpInfo.stp_type=='T'}"> selected</c:if>>테스트</option>
		                       			</select>
		                       			</sec:authorize>
		                       			<sec:authorize access="!hasRole('SITEADMIN')">
			                       			<input type="hidden" name="stp_type" id="stp_type" value="${VoStpInfo.stp_type}"/>
			                       			<c:choose>
			                       				<c:when test="${VoStpInfo.stp_type=='R'}">임대</c:when>
			                       				<c:when test="${VoStpInfo.stp_type=='S'}">판매</c:when>
			                       				<c:when test="${VoStpInfo.stp_type=='T'}">테스트</c:when>
			                       				<c:when test="${VoStpInfo.stp_type=='W'}">대기중</c:when>
			                       			</c:choose>
		                       			</sec:authorize>
			                        </td>
			                        <th>모니터 배치</th>
			                        <td>
		                       			<select name="mnt_type" id="mnt_type">
		                       				<option value="0"<c:if test="${VoStpInfo.mnt_type=='0'}"> selected</c:if>>가로</option>
		                       				<option value="180"<c:if test="${VoStpInfo.mnt_type=='180'}"> selected</c:if>>역가로</option>
		                       				<option value="90"<c:if test="${VoStpInfo.mnt_type=='90'}"> selected</c:if>>세로</option>
		                       				<option value="270"<c:if test="${VoStpInfo.mnt_type=='270'}"> selected</c:if>>역세로</option>
		                       			</select>
			                        </td>	
			                    </tr>
			                    <tr>
			                    	<th>사용여부</th>
			                        <td>
		                       			<select name="use_yn" id="use_yn">
		                       				<option value="Y"<c:if test="${VoStpInfo.use_yn=='Y'}"> selected</c:if>>사용</option>
		                       				<option value="N"<c:if test="${VoStpInfo.use_yn=='N'}"> selected</c:if>>중지</option>
		                       				<option value="S"<c:if test="${VoStpInfo.use_yn=='S'}"> selected</c:if>>대기</option>
		                       			</select>
			                        </td>	
									<th>접속시간</th>
			                        <td>
			                        	<c:choose>
			                            	<c:when test="${empty VoStpInfo.min_diff}">
			                            		
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 0 && VoStpInfo.min_diff < 60}">
			                            		<c:out value="${VoStpInfo.last_conn_dt}"/>
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 60 && VoStpInfo.min_diff < 720}">
			                            		<span class="t_blue"><c:out value="${VoStpInfo.last_conn_dt}"/></span>
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 720 && VoStpInfo.min_diff < 1440}">
			                            		<span class="t_blue"><b><c:out value="${VoStpInfo.last_conn_dt}"/></b></span>
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 1440 && VoStpInfo.min_diff < 10080}">
			                            		<span class="t_red"><c:out value="${VoStpInfo.last_conn_dt}"/></span>
			                            	</c:when>
			                            	<c:when test="${VoStpInfo.min_diff >= 10080}">
			                            		<span class="t_red"><b><c:out value="${VoStpInfo.last_conn_dt}"/></b></span>
			                            	</c:when>
			                            </c:choose>
			                        </td>
			                    </tr>
			                    <tr>
			                    	<th>자동종료</th>
			                        <td>
		                       			<select name="auto_off_yn" id="auto_off_yn">
		                       				<option value=""<c:if test="${VoStpInfo.auto_off_yn!='Y'}"> selected</c:if>>중지</option>
		                       				<option value="Y"<c:if test="${VoStpInfo.auto_off_yn=='Y'}"> selected</c:if>>사용</option>
		                       			</select>
			                        </td>	
									<th>종료시간</th>
			                        <td>
			                        	<c:set var="hh" value="${fnc:substring(VoStpInfo.auto_off_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.auto_off_tm,2,4)}"/>
			                        	<select name="auto_off_h">
			                        	<c:forEach var="i" begin="0" end="24">
			                        		<c:set var="h" value="${i}"/>
			                        		<c:if test="${h<10}"><c:set var="h" value="0${h}"/></c:if>
			                        		<option value="${h}"<c:if test="${hh == h}"> selected</c:if>>${h}</option>
								       	</c:forEach>
								       	</select>
			                        	<select name="auto_off_m">
			                        	<c:forEach var="i" begin="0" end="55" step="5">
			                        		<c:set var="m" value="${i}"/>
											<c:if test="${m<10}"><c:set var="m" value="0${m}"/></c:if>
			                        		<option value="${m}"<c:if test="${mm == m}"> selected</c:if>>${m}</option>
								       	</c:forEach>
								       	</select>
			                        </td>
			                    </tr>
			                    <tr>
			                    	<th>주요공지</th>
			                        <td>
		                       			<select name="noti_yn" id="noti_yn">
		                       				<option value=""<c:if test="${VoStpInfo.noti_yn!='Y'}"> selected</c:if>>중지</option>
		                       				<option value="Y"<c:if test="${VoStpInfo.noti_yn=='Y'}"> selected</c:if>>사용</option>
		                       			</select>
		                       			* 뉴스보다 우선 적용됩니다.
			                        </td>	
									<th>주요공지시간</th>
			                        <td>
			                        	<c:set var="hh" value="${fnc:substring(VoStpInfo.noti_start_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.noti_start_tm,2,4)}"/>
			                        	<select name="noti_start_h">
			                        	<c:forEach var="i" begin="0" end="24">
			                        		<c:set var="h" value="${i}"/>
			                        		<c:if test="${h<10}"><c:set var="h" value="0${h}"/></c:if>
			                        		<option value="${h}"<c:if test="${hh == h}"> selected</c:if>>${h}</option>
								       	</c:forEach>
								       	</select>
			                        	<select name="noti_start_m">
			                        	<c:forEach var="i" begin="0" end="55" step="5">
			                        		<c:set var="m" value="${i}"/>
											<c:if test="${m<10}"><c:set var="m" value="0${m}"/></c:if>
			                        		<option value="${m}"<c:if test="${mm == m}"> selected</c:if>>${m}</option>
								       	</c:forEach>
								       	</select>
								       	~
								       	<c:set var="hh" value="${fnc:substring(VoStpInfo.noti_end_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.noti_end_tm,2,4)}"/>
			                        	<select name="noti_end_h">
			                        	<c:forEach var="i" begin="0" end="24">
			                        		<c:set var="h" value="${i}"/>
			                        		<c:if test="${h<10}"><c:set var="h" value="0${h}"/></c:if>
			                        		<option value="${h}"<c:if test="${hh == h}"> selected</c:if>>${h}</option>
								       	</c:forEach>
								       	</select>
			                        	<select name="noti_end_m">
			                        	<c:forEach var="i" begin="0" end="55" step="5">
			                        		<c:set var="m" value="${i}"/>
											<c:if test="${m<10}"><c:set var="m" value="0${m}"/></c:if>
			                        		<option value="${m}"<c:if test="${mm == m}"> selected</c:if>>${m}</option>
								       	</c:forEach>
								       	</select>
			                        </td>
			                    </tr>
			                    <tr>
			                    	<th>뉴스자막</th>
			                        <td>
		                       			<select name="news_yn" id="news_yn">
		                       				<option value=""<c:if test="${VoStpInfo.news_yn!='Y'}"> selected</c:if>>중지</option>
		                       				<option value="Y"<c:if test="${VoStpInfo.news_yn=='Y'}"> selected</c:if>>사용</option>
		                       			</select>
			                        </td>	
									<th>뉴스시간</th>
			                        <td>
			                        	<c:set var="hh" value="${fnc:substring(VoStpInfo.news_start_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.news_start_tm,2,4)}"/>
			                        	<select name="news_start_h">
			                        	<c:forEach var="i" begin="0" end="24">
			                        		<c:set var="h" value="${i}"/>
			                        		<c:if test="${h<10}"><c:set var="h" value="0${h}"/></c:if>
			                        		<option value="${h}"<c:if test="${hh == h}"> selected</c:if>>${h}</option>
								       	</c:forEach>
								       	</select>
			                        	<select name="news_start_m">
			                        	<c:forEach var="i" begin="0" end="55" step="5">
			                        		<c:set var="m" value="${i}"/>
											<c:if test="${m<10}"><c:set var="m" value="0${m}"/></c:if>
			                        		<option value="${m}"<c:if test="${mm == m}"> selected</c:if>>${m}</option>
								       	</c:forEach>
								       	</select>
								       	~
								       	<c:set var="hh" value="${fnc:substring(VoStpInfo.news_end_tm,0,2)}"/>
			                        	<c:set var="mm" value="${fnc:substring(VoStpInfo.news_end_tm,2,4)}"/>
			                        	<select name="news_end_h">
			                        	<c:forEach var="i" begin="0" end="24">
			                        		<c:set var="h" value="${i}"/>
			                        		<c:if test="${h<10}"><c:set var="h" value="0${h}"/></c:if>
			                        		<option value="${h}"<c:if test="${hh == h}"> selected</c:if>>${h}</option>
								       	</c:forEach>
								       	</select>
			                        	<select name="news_end_m">
			                        	<c:forEach var="i" begin="0" end="55" step="5">
			                        		<c:set var="m" value="${i}"/>
											<c:if test="${m<10}"><c:set var="m" value="0${m}"/></c:if>
			                        		<option value="${m}"<c:if test="${mm == m}"> selected</c:if>>${m}</option>
								       	</c:forEach>
								       	</select>
			                        </td>
			                    </tr>
			                    <tr>
									<th>자막크기</th>
			                        <td>
			                        	<select name="noti_size">
			                        		<option value="S"<c:if test="${VoStpInfo.noti_size == '' || VoStpInfo.noti_size == 'S'}"> selected</c:if>>소</option>
			                        		<option value="M"<c:if test="${VoStpInfo.noti_size == 'M'}"> selected</c:if>>중</option>
			                        		<option value="L"<c:if test="${VoStpInfo.noti_size == 'L'}"> selected</c:if>>대</option>
								       	</select>
			                        </td>
			                        <th>원격아이디</th>
			                        <td>
			                        	<sec:authorize access="hasRole('SITEADMIN')">
			                        	<form:input path="remote_id" maxlength="20" title="원격아이디" cssClass="w150"/>
			                        	</sec:authorize>
			                        	<sec:authorize access="!hasRole('SITEADMIN')">
			                        	<c:out value="${VoStpInfo.remote_id}"/>
			                        	<form:hidden path="remote_id" maxlength="20" title="원격아이디"/>
			                        	</sec:authorize>
			                        </td>	                        
			                    </tr>
			                    <tr>
			                    	<th>CCTV</th>
			                        <td>
			                        	<sec:authorize access="hasRole('SITEADMIN')">
			                        	<select name="cctv_yn">
		                        			<option value=""<c:if test="${VoStpInfo.cctv_yn != 'Y'}"> selected</c:if>>미사용</option>
		                        			<option value="Y"<c:if test="${VoStpInfo.cctv_yn == 'Y'}"> selected</c:if>>사용</option>
										</select>
			                        	</sec:authorize>
			                        	<sec:authorize access="!hasRole('SITEADMIN')">
			                        	<c:choose>
			                        		<c:when test="${VoStpInfo.cctv_yn == 'Y'}">사용</c:when>
			                        		<c:otherwise>미사용</c:otherwise>
			                        	</c:choose>
			                        	<form:hidden path="cctv_yn" maxlength="20" title="CCTV"/>
			                        	</sec:authorize>
			                        </td>
									<th>번호호출기</th>
			                        <td>
		                       			<select name="call_cd" onchange="fncShowCallInfo();">
		                        			<option value="">없음</option>
											<c:forEach items="${codeList}" var="codeList">
												<option value="${codeList.code}" ${codeList.code ==VoStpInfo.call_cd ? 'selected' : ''}>${codeList.code_nm} </option>
											</c:forEach>
										</select>
			                        </td>	                        
			                    </tr>
			                    <tr id="callInfo" style="display:<c:if test="${VoStpInfo.call_cd==''}">none</c:if>">
									<th>호출기정보</th>
			                        <td colspan="3">
			                        	그룹번호 : <form:input path="call_grp" maxlength="2" cssStyle="width:30px" cssClass="a_right"/> 
										업체번호 : <form:input path="call_prd" maxlength="2" cssStyle="width:30px" cssClass="a_right"/> 
										번호자리수 : <form:input path="call_num" maxlength="1" cssStyle="width:30px" cssClass="a_right"/>
										안내종류 : 
										<select name="sound_type">
											<c:forEach items="${soundCode}" var="soundCode">
												<option value="${soundCode.code}" ${soundCode.code ==VoStpInfo.sound_type ? 'selected' : ''}>${soundCode.code_nm} ( ${soundCode.code_desc} )</option>
											</c:forEach>
										</select>
			                        </td>	                        
			                    </tr>
			                    <tr>
									<th>재생정보</th>
			                        <td colspan="3">
			                        	<input type="button" value=" + " onclick="fncAddPlay();">
			                            <input type="button" value=" - " onclick="fncDelPlay();">
			                            <table id ='playlist'  ></table>
			                        </td>	                        
			                    </tr>
			                </tbody>
			            </table>
			            
						<table class="tbl" summary="기기 목록입니다.">
			            <caption class="accessibility">기기 목록</caption>
			            <colgroup>
							<col width="5%"/>
							<col width="10%"/>
			                <col />
			                <col width="20%"/>
							<col width="10%"/>
							<col width="10%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>타입</th>
									<th>맥주소</th>
			                        <th>접속시간</th>
			                        <th>사용여부</th>
			                        <th>삭제</th>
								</tr>
							</thead>
			                <tbody>
			                <c:choose>
			                	<c:when test="${not empty macList}">
			                		<c:forEach var="result" items="${macList}" varStatus="status">
			                        <tr>
			                            <td class="a_center"><fmt:formatNumber value="${status.index + 1}"/></td>
			                            <td class="a_center"><c:out value="${result.mac_type}"/></td>
			                            <td><c:out value="${result.mac_adr}"/></td>
			                            <td class="a_center">
											<c:choose>
				                            	<c:when test="${empty result.min_diff}">
				                            		
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 0 && result.min_diff < 60}">
				                            		<c:out value="${result.last_conn_dt}"/>
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 60 && result.min_diff < 720}">
				                            		<span class="t_blue"><c:out value="${result.last_conn_dt}"/></span>
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 720 && result.min_diff < 1440}">
				                            		<span class="t_blue"><b><c:out value="${result.last_conn_dt}"/></b></span>
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 1440 && result.min_diff < 10080}">
				                            		<span class="t_red"><c:out value="${result.last_conn_dt}"/></span>
				                            	</c:when>
				                            	<c:when test="${result.min_diff >= 10080}">
				                            		<span class="t_red"><b><c:out value="${result.last_conn_dt}"/></b></span>
				                            	</c:when>
				                            </c:choose>			                            
			                            </td>
			                            <td class="a_center">
			                            	<c:choose>
			                            		<c:when test="${result.use_yn=='Y'}">사용</c:when>
			                            		<c:when test="${result.use_yn=='N'}">중지</c:when>
			                            		<c:when test="${result.use_yn=='S'}">대기</c:when>
			                            		<c:otherwise>기타</c:otherwise>
			                            	</c:choose>
			                            </td>
			                            <td class="a_center"><span class="btn_pack small_sky"><a onclick="fncEquipMacDel('${result.mac_seq}');" title="삭제" style="cursor:pointer">삭제</a></span></td>
			                        </tr>
			                        </c:forEach>
			                	</c:when>
			                	<c:otherwise>
			                        <tr>
			                            <td colspan="7" class="a_center">등록된 맥어드레스가 없습니다.</td>
			                        </tr>	                	
			                	</c:otherwise>
			                </c:choose>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
				            	<a class="button" onclick="fncSave();" title="저장">저장</a>
				            	<c:if test="${VoStpInfo.mode=='U'}">
					            	<a class="button" href="/mng/eup/view.do<c:if test="${not empty retParam}">?${retParam}&stp_id=${VoStpInfo.stp_id}</c:if>" title="취소">취소</a>
					            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
				            	</c:if>
			            	</c:if>
			            	<a class="button" href="/mng/eup/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
					<c:if test="${not empty playList}">
						<c:forEach var="stpPlay" items="${playList}" varStatus="status">
							<c:set var="ply_ver_id" value="${stpPlay.ply_ver_id}" />
							<c:set var="ply_seq" value="${stpPlay.ply_seq}" />
							<c:set var="ply_title" value="${stpPlay.ply_title}" />
							<c:set var="shh" value="${fnc:substring(stpPlay.ply_start_tm,0,2)}"/>
							<c:set var="smm" value="${fnc:substring(stpPlay.ply_start_tm,2,4)}"/>
							<c:set var="ehh" value="${fnc:substring(stpPlay.ply_end_tm,0,2)}"/>
							<c:set var="emm" value="${fnc:substring(stpPlay.ply_end_tm,2,4)}"/>
							<script>
								fncAddPlay();
								var trIdx = "${status.index}";
								document.getElementById("ply_ver_id" + trIdx).value = "${ply_ver_id}";
								document.getElementById("ply_seq" + trIdx).value = "${ply_seq}";
								document.getElementById("ply_title" + trIdx).value = "${ply_title}";
								document.getElementById("ply_start_h" + trIdx).value = "${shh}";
								document.getElementById("ply_start_m" + trIdx).value = "${smm}";
								document.getElementById("ply_end_h" + trIdx).value = "${ehh}";
								document.getElementById("ply_end_m" + trIdx).value = "${emm}";
							</script>
						</c:forEach>
					</c:if>			        
				</div>
