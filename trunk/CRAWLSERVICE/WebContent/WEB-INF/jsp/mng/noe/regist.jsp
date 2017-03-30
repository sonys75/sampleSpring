<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="msg_id_empty"/>
<spring:message code="Process.confirm.relation.delete" var="delete_confirm"/>
<script type="text/javascript">
	var maxStpCnt = 30;					
	var maxMsgCnt = 40;
	
	function fncSave() {
		var f = document.F1;

		if (f.mode.value == "U") {
			if (f.msg_id.value == "") {
				alert("${msg_id_empty}");
				return false;
			}
		}

		if(f.msg_title.value==""){
			alert("공지제목을 입력해 주세요.");
			f.msg_title.focus();
			return false;
		}
		
		if(f.corp_id.value==""){
			alert("소유회사를 선택해 주세요.");
			openDivModal('회사검색','/cmm/sch/corp.do',500,400);
			return false;
		}

		if((f.msg_type.value=="S" || f.msg_type.value=="T") && f.view_tm.value=="00"){
			alert("공지시간을 설정해 주세요.");
			f.view_tm.focus();
			return false;
		}else if(f.msg_type.value=="G"){
			if(f.view_tm.value!="00"){
				alert("일반공지는 셋탑에 설정된 시간에 따라 나타납니다.");
				f.view_tm.value="00";
			}
		}

		if(f.stplistcnt.value=="0"){
			alert("공지할 셋탑이 없습니다.\n셋탑을 선택해 주세요.");
			fncAddStp();
			return false;
		}else if(f.stplistcnt.value=="1"){
			if(f.stp_id.value==""){
				alert("셋탑이 설정되지 않았습니다.\n셋탑을 선택해 주세요.");
				return false;
			}
		}else{
			for(var i=0;i<f.stp_id.length;i++){
				if(f.stp_id[i].value==""){
					alert(parseInt(i+1) + "번째 셋탑이 설정되지 않았습니다.\n셋탑을 선택해 주세요.");
					return false;
				}
			}
		}
		
		if(f.msglistcnt.value=="0"){
			alert("공지사항을 입력해 주세요.");
			fncAddMsg();
			return false;
		}else if(f.msglistcnt.value=="1"){
			if(f.msg.value==""){
				alert("공지사항을 입력해 주세요.");
				f.msg.focus();
				return false;
			}
		}else{
			for(var i=0;i<f.msg.length;i++){
				if(f.msg[i].value==""){
					alert(parseInt(i+1) + "번째 공지사항을 입력해 주세요.");
					f.msg[i].focus();
					return false;
				}
			}
		}
		
		f.target = "HiddenFrame";
		f.action = "/mng/noe/registProc.do";
		f.submit();
		f.target = "_self";
		f.action = "/mng/noe/regist.do";
		return true;
	}

	function fncDel() {
		var f = document.F1;

		if (f.msg_id.value == "") {
			alert("${msg_id_empty}");
			return false;
		}

		if (confirm("${delete_confirm}")) {
			f.target = "HiddenFrame";
			f.mode.value = "D";
			f.action = "/mng/noe/registProc.do";
			f.submit();
			f.target = "_self";
			f.action = "/mng/noe/regist.do";
			return true;
		} else {
			return false;
		}
	}

    //셋탑 추가 버튼
    function fncAddStp(){
        var f = document.F1;
			        	
        var objTbl	= document.getElementById("stplist");
        if(f.stplistcnt.value>=maxStpCnt || objTbl.rows.length>=maxStpCnt){
            alert("셋탑 등록은 최대 "+maxStpCnt+"개까지 가능합니다.");
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
        objCell.innerHTML   ="<input type=\"hidden\" name=\"stp_id\" id=\"stp_id"+parseInt(f.stplistcnt.value)+"\">"
        					+"<input type=\"text\" name=\"stp_title\" id=\"stp_title"+parseInt(f.stplistcnt.value)+"\" class=\"input w400\" readOnly=\"true\" onclick=\"openDivModal('셋탑검색','/cmm/sch/equip.do?seq="+parseInt(f.stplistcnt.value)+"',500,400);\"/>"
        					+" <span class=\"btn_pack small_sky\"><a onclick=\"openDivModal('셋탑검색','/cmm/sch/equip.do?seq="+parseInt(f.stplistcnt.value)+"',500,400);\" title=\"셋탑 선택\" style=\"cursor:pointer\">셋탑 선택</a></span>";
        f.stplistcnt.value=parseInt(f.stplistcnt.value)+1;
        
        return;
    }
    
    //셋탑 삭제 버튼
    function fncDelStp(){
        var f = document.F1;
        var objTbl	= document.getElementById("stplist");
        var objrow	= "";
        var nRowNo;
        
        if(objTbl.rows.length==0){
            return;
        }
        
        nRowNo = parseInt(f.stplistcnt.value) - 1;
        objrow = eval("document.getElementById('stp_id"+ nRowNo +"')");

        if(objrow.value != ""){
            if(!confirm("등록된 셋탑을 삭제하시겠습니까?")){
            	return;
            }
        }
        
        objTbl.deleteRow(objTbl.rows.length-1);
        f.stplistcnt.value=parseInt(f.stplistcnt.value)-1;
        
        return;
    }
	
    //공지내용 추가 버튼
    function fncAddMsg(){
        var f = document.F1;
			        	
        var objTbl	= document.getElementById("msglist");
        if(f.msglistcnt.value>=maxMsgCnt || objTbl.rows.length>=maxMsgCnt){
            alert("공지내용 등록은 최대 "+maxMsgCnt+"개까지 가능합니다.");
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
        objCell.innerHTML   ="<input type=\"hidden\" name=\"msg_seq\" id=\"msg_seq"+parseInt(f.msglistcnt.value)+"\">"
        					+"<input type=\"text\" name=\"msg\" id=\"msg"+parseInt(f.msglistcnt.value)+"\" class=\"input w500\" maxlength=\"30\"/>";
        f.msglistcnt.value=parseInt(f.msglistcnt.value)+1;
        return;
    }
    
    //공지내용 삭제 버튼
    function fncDelMsg(){
        var f = document.F1;
        var objTbl	= document.getElementById("msglist");
        var objrow	= "";
        var nRowNo;
        
        if(objTbl.rows.length==0){
            return;
        }
        
        nRowNo = parseInt(f.msglistcnt.value) - 1;
        objrow = eval("document.getElementById('msg_seq"+ nRowNo +"')");

        if(objrow.value != ""){
            if(!confirm("등록된 공지내용을 삭제하시겠습니까?")){
            	return;
            }
        }
        
        objTbl.deleteRow(objTbl.rows.length-1);
        f.msglistcnt.value=parseInt(f.msglistcnt.value)-1;
        
        return;
    }
</script>

				<div id="content">
					<div class="navi">홈 &gt; 기기관리 &gt; 공지관리</div>
					<form:form commandName="VoMessage" name="F1" id="F1" method="post" onsubmit="return fncSave();">
	        			<form:hidden path="mode"/>
	        			<form:hidden path="msg_id"/>
				        <form:hidden path="page_no"/>
				        <form:hidden path="sch_fld"/>
				        <form:hidden path="sch_word"/>
				        <input type="hidden" id="stplistcnt" name="stplistcnt" value="0"/>
				        <input type="hidden" id="msglistcnt" name="msglistcnt" value="0"/>
						<table class="tbl" summary="공지 정보">
			            <caption class="accessibility">공지 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                    <tr>
									<th>공지제목</th>
			                        <td>
			                            <form:input path="msg_title" maxlength="100" cssClass="w200"/>
			                        </td>
									<th>소유회사</th>
			                        <td>
			                            <form:hidden path="corp_id"/>
			                        	<form:input path="corp_nm" maxlength="20" title="회사명" cssClass="w200" readOnly="true" onclick="openDivModal('회사검색','/cmm/sch/corp.do',500,400);"/>
			                            <span class="btn_pack small_sky"><a style="cursor:pointer" onclick="openDivModal('회사검색','/cmm/sch/corp.do',500,400);" title="검색">검색</a></span>		        
							        </td>
			                    </tr>
			                    <tr>
			                    	<th>분류</th>
			                        <td>
		                       			<select name="msg_type" id="msg_type">
		                       				<option value="G"<c:if test="${VoMessage.msg_type=='G'}"> selected</c:if>>일반</option>
		                       				<option value="S"<c:if test="${VoMessage.msg_type=='S'}"> selected</c:if>>긴급</option>
		                       				<option value="T"<c:if test="${VoMessage.msg_type=='T'}"> selected</c:if>>테스트</option>
		                       			</select>
			                        </td>	
									<th>공지시간</th>
			                        <td>
			                        	<c:set var="mm" value="${VoMessage.view_tm}"/>
			                        	<select name="view_tm">
			                        	<c:forEach var="i" begin="0" end="60" step="5">
			                        		<c:set var="m" value="${i}"/>
											<c:if test="${m<10}"><c:set var="m" value="0${m}"/></c:if>
			                        		<option value="${m}"<c:if test="${mm == m}"> selected</c:if>>${m}</option>
								       	</c:forEach>
								       	</select>
								       	분
			                        </td>
			                    </tr>
		 	                    <tr>
			                    	<th>사용여부</th>
			                        <td colspan="3">
		                       			<select name="use_yn" id="use_yn">
		                       				<option value="Y"<c:if test="${VoMessage.use_yn=='Y'}"> selected</c:if>>사용</option>
		                       				<option value="N"<c:if test="${VoMessage.use_yn=='N'}"> selected</c:if>>중지</option>
		                       				<option value="S"<c:if test="${VoMessage.use_yn=='S'}"> selected</c:if>>대기</option>
		                       			</select>
			                        </td>	
			                    </tr>
			                    <tr>
									<th>셋탑등록</th>
			                        <td colspan="3">
			                        	<input type="button" value=" + " onclick="fncAddStp();">
			                            <input type="button" value=" - " onclick="fncDelStp();">
			                            <table id ='stplist'></table>
			                        </td>	                        
			                    </tr>
			                    <tr>
									<th>공지내용</th>
			                        <td colspan="3">
			                        	<input type="button" value=" + " onclick="fncAddMsg();">
			                            <input type="button" value=" - " onclick="fncDelMsg();">
			                            <table id ='msglist'></table>
			                        </td>	                        
			                    </tr>
			                </tbody>
			            </table>
			            <div class="btnC">
			            	<c:if test="${ChkAuth=='true'}">
				            	<a class="button" onclick="fncSave();" title="저장">저장</a>
				            	<c:if test="${VoMessage.mode=='U'}">
					            	<a class="button" href="/mng/noe/view.do<c:if test="${not empty retParam}">?${retParam}&msg_id=${VoMessage.msg_id}</c:if>" title="취소">취소</a>
					            	<a class="button" onclick="fncDel();" title="삭제">삭제</a>
				            	</c:if>
			            	</c:if>
			            	<a class="button" href="/mng/noe/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
					<c:if test="${not empty MsgStpList}">
						<c:forEach var="result" items="${MsgStpList}" varStatus="status">
							<script>
								fncAddStp();
								var trIdx = "${status.index}";
								document.getElementById("stp_id" + trIdx).value = "${result.stp_id}";
								document.getElementById("stp_title" + trIdx).value = "${result.stp_title}";
							</script>
						</c:forEach>
					</c:if>
					<c:if test="${not empty MsgInfoList}">
						<c:forEach var="result" items="${MsgInfoList}" varStatus="status">
							<script>
								fncAddMsg();
								var trIdx = "${status.index}";
								document.getElementById("msg_seq" + trIdx).value = "${result.msg_seq}";
								document.getElementById("msg" + trIdx).value = "${result.msg}";
							</script>
						</c:forEach>
					</c:if>		        
				</div>
