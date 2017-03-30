<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:message code="Check.field.hidden.empty" var="check_seq"/>
<script type="text/javascript">
function fncReply(){
	var f=document.F1;

	if(f.seq.value==""){
		alert("${check_seq}");
		return false;
	}

	f.action = "/cmm/brd/reply.do";
	f.submit();
	return true;
}

function fncEdit(){
	var f=document.F1;

	if(f.seq.value==""){
		alert("${check_seq}");
		return false;
	}

	f.action = "/cmm/brd/regist.do";
	f.submit();
	return true;
}
</script>
				<div id="content">
					<div class="navi">홈 &gt; 게시판</div>
					<form:form commandName="VoBoard" name="F1" id="F1" method="post" onsubmit="return fncEdit();">
				        <form:hidden path="mode" />
						<form:hidden path="part" />
						<form:hidden path="seq" />
						<form:hidden path="rel_seq" />
						<form:hidden path="rpy_num" />
						<form:hidden path="rpy_step" />
						<form:hidden path="page_no" />
						<form:hidden path="sch_fld" />
						<form:hidden path="sch_word" />
			            <table class="tbl" summary="게시판 정보">
			            <caption class="accessibility">게시판 정보</caption>
			            <colgroup>
							<col width="10%"/>
							<col width="40%"/>
							<col width="10%"/>
							<col width="40%"/>
						</colgroup>
			                <tbody>
			                	<tr>
									<th>제목</th>
			                        <td colspan="3">
			                        	<c:out value="${VoBoard.subject}" escapeXml="false"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>조회수</th>
			                        <td>
			                        	<c:out value="${VoBoard.read_cnt}" escapeXml="false"/>
			                        </td>
			                        <th>등록자</th>
			                        <td>
			                        	<c:out value="${VoBoard.reg_id}" escapeXml="false"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>수정일</th>
			                        <td>
			                        	<c:out value="${VoBoard.mod_dt}" escapeXml="false"/>
			                        </td>
			                        <th>등록일</th>
			                        <td>
			                        	<c:out value="${VoBoard.reg_dt}" escapeXml="false"/>
			                        </td>
			                    </tr>
			                    <tr>
									<th>내용</th>
			                        <td colspan="3">
			                        	<pre><c:out value="${VoBoard.content}" escapeXml="false"/></pre>
			                        </td>
			                    </tr>
			                </tbody>
			            </table>
		
			            <div class="btnC">
			            	<a class="button" onclick="fncReply();" title="답글">답글</a>
			            	<c:if test="${bChkAuth==true}">
			            	<a class="button" onclick="fncEdit();" title="수정">수정</a>
			            	</c:if>
			            	<a class="button" href="/cmm/brd/list.do<c:if test="${not empty retParam}">?${retParam}</c:if>" title="목록">목록</a>
			            </div> 
			        </form:form>
				</div>
