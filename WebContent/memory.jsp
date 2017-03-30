<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*" %>
<%@ page import="org.apache.tomcat.dbcp.dbcp.*" %>
<%
	if(request.getParameter("gc") != null) {
		System.gc();
		System.runFinalization();
	}

	String interval = "300";
	
	if(request.getParameter("interval") != null) {
		interval = request.getParameter("interval");
	}
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월dd일 a hh시mm분 ss초", Locale.KOREA);
	
	Runtime rt = Runtime.getRuntime();
	long free = rt.freeMemory();
	long total = rt.totalMemory();
	long usedRatio = (total - free) * 100 / total;
	long unusedRatio = free * 100 / total;
	
	String unUsedColor = "#0066ff";
	if(unusedRatio>10 && unusedRatio<20){
		unUsedColor = "#f48720";
	}else if(unusedRatio>5 && unusedRatio<=10){
		unUsedColor = "#cc3366";
		//response.sendRedirect(request.getRequestURI()+"?gc=clear&interval="+interval);
		//return;
	}else if(unusedRatio<=5){
		//5% 이하로 남았을 경우 강제로 컬렉션을 비운다.
		//out.println("<script>location.href=\""+request.getRequestURI()+"?gc=clear&interval="+interval+"\";</script>");
		response.sendRedirect(request.getRequestURI()+"?gc=clear&interval="+interval);
		return;
	}
	
	/**
	*dbcp check
	*/
/* 	Context initContext = null;
	Context envContext = null;
	DataSource ds  = null;
	BasicDataSource bds = null;

	int bdsInitialize = -1;
	int bdsNumActive = -1;
	int bdsMaxActive = -1;
	int bdsNumIdle = -1;
	long bdsMaxWait = -10;
	
	try{
		initContext = new InitialContext();
		envContext = (Context)initContext.lookup("java:comp/env");
		ds = (DataSource)envContext.lookup("jdbc/dataSource");
		bds = (BasicDataSource)ds;

		bdsInitialize = bds.getInitialSize();
		bdsNumActive = bds.getNumActive();
		bdsMaxActive = bds.getMaxActive();
		bdsNumIdle = bds.getNumIdle();
		bdsMaxWait = bds.getMaxWait();

	}catch(Exception e){
		out.println(e.toString());
	}
	 */
%>
<c:set var="interval" value="<%=interval%>"/>
<!DOCTYPE HTML>
<html lang="ko">
<HEAD>
<TITLE>memory 관리</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Refresh" content="<%= interval %>;url=<%= request.getRequestURI()%>?interval=${interval}"/>
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" >
<meta http-equiv="imagetoolbar" content="no">
<style type="text/css">
html,body,select,input,textarea {font-size:12px;color:#000000; font-family:Tahoma,gulim}

a {cursor:pointer}
a,a:link,a:visited{color:#444;text-decoration:none;text-overflow:ellipsis;-o-text-overflow:ellipsis;}
a:hover,a:active{color:#767676; text-decoration:none;}

.a_left {text-align: left !important;}
.a_center {	text-align: center !important;}

table.memoryManage {margin-bottom:10px; width:450px; border-collapse:collapse;}
table.memoryManage th {color:#587491; background:url(/images/list_title.gif) repeat-x; background-color:#e5e5e5; height:40px;font-size:12px; font-family:Tahoma,gulim; border:solid 1px #c9d3de}
table.memoryManage td {border:solid 1px #d3d3d3; text-align:center; padding:5px;}
table.memoryManage td a:hover {color:#587491; text-decoration:none;}
table.memoryManage td.left {padding-left:10px;text-align:left}

</style>
</HEAD>
<body>
<table class="memoryManage">
<colgroup>
	<col width="50%">
    <col width="">
</colgroup>
<thead>
<tr>
	<th colspan="2" class="a_left">
		■ 측정시각 : <%= formatter.format(new Date()) %>
		<br>
		■ 호스트 : <%= InetAddress.getLocalHost().getHostName() %> (<%= InetAddress.getLocalHost().getHostAddress() %>)
	</th>
</tr>
</thead>
<tbody>
<tr>
	<td class="a_center" colspan="2">전체 가상머신 메모리 (<b><fmt:formatNumber><%= total/1024 %></fmt:formatNumber> KB</b>)</td>
</tr>
<tr bgcolor=#E3E3E3>
	<td align="center">사용중인 메모리 (<b><fmt:formatNumber><%= (total - free)/1024 %></fmt:formatNumber> KB</b>)</td>
	<td align="center">남아있는 메모리 (<b><fmt:formatNumber><%= free/1024 %></fmt:formatNumber> KB</b>)</td>
</tr>
<tr>
	<td class="left"><hr color="#9b9b9b" align=left size=10 width="<%= usedRatio %>%"> (<%= usedRatio %> %)</td>
	<td class="left"><hr color="<%=unUsedColor%>" align=left size=10 width="<%= unusedRatio %>%"> (<%= unusedRatio %> %)</td>
</tr>
<tr>
	<td>[<a onclick="location.href='<%= request.getRequestURI() %>?gc=clear&interval='+document.getElementById('interval').value;">가비지컬렉션 실행</a>]</td>
	<td>[<a onclick="location.href='<%= request.getRequestURI() %>?interval='+document.getElementById('interval').value;">다시 읽기</a>] <select id="interval"><option value="5"<c:if test="${interval=='5'}"> selected</c:if>>5</option><option value="10"<c:if test="${interval=='10'}"> selected</c:if>>10</option><option value="30"<c:if test="${interval=='30'}"> selected</c:if>>30</option><option value="300"<c:if test="${interval=='300'}"> selected</c:if>>300</option></select>초</td>
</tr>
<%-- <tr>
	<td class="a_center" colspan="2">초기화 크기 : <%=bdsInitialize%></td>
</tr>
<tr>
	<td class="left" colspan="2">Active Connections : <%=bdsNumActive%></td>
</tr>
<tr>
	<td class="left" colspan="2">Maximum Active Connections : <%=bdsMaxActive%></td>
</tr>
<tr>
	<td class="left" colspan="2">Idle Connections : <%=bdsNumIdle%></td>
</tr>
<tr>
	<td class="left" colspan="2">Maximum Wait period before timeout : <%=bdsMaxWait%></td>
</tr> --%>
<tbody>
</table>
</body>
</html>