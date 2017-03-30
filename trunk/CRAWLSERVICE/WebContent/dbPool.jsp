<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.apache.commons.dbcp.BasicDataSource" %>
<%
	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
	BasicDataSource dataSource = (BasicDataSource) context.getBean("dataSource");
	
	if(request.getParameter("gc") != null) {
		System.gc();
		System.runFinalization();
	}
	
	String interval = "300";
	
	if(request.getParameter("interval") != null) {
		interval = request.getParameter("interval");
	}
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 a hh시 mm분 ss초", Locale.KOREA);
	
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
	}else if(unusedRatio<=5){
		//5% 이하로 남았을 경우 강제로 컬렉션을 비운다.
		response.sendRedirect(request.getRequestURI()+"?gc=clear&interval="+interval);
		return;
	}
%>
<c:set var="interval" value="<%=interval%>"/>
<!DOCTYPE HTML>
<html lang="ko">
  <head>
    <title>Status</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Refresh" content="<%= interval %>;url=<%= request.getRequestURI()%>?interval=${interval}"/>
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<style type="text/css">
		html,body,select,input,textarea {font-size:12px;color:#000000; font-family:Tahoma,gulim}
		
		a {cursor:pointer}
		a,a:link,a:visited{color:#444;text-decoration:none;text-overflow:ellipsis;-o-text-overflow:ellipsis;}
		a:hover,a:active{color:#767676; text-decoration:none;}
		
		.a_left {text-align: left !important;}
		.a_center {	text-align: center !important;}
		
		table.memoryManage {margin-bottom:10px; width:450px; border-collapse:collapse;}
		table.memoryManage th {color:#587491; background-color:#e5e5e5; height:40px;font-size:12px; font-family:Tahoma,gulim; border:solid 1px #c9d3de}
		table.memoryManage td {border:solid 1px #d3d3d3; text-align:center; padding:5px;}
		table.memoryManage td a:hover {color:#587491; text-decoration:none;}
		table.memoryManage td.left {padding-left:10px;text-align:left}
		table.memoryManage td.right {padding-right:10px;text-align:right}
	</style>
	<script type="text/javascript">
		var nConnectSeconds = '${interval}';
		function setCurrentTime(){
		    var target = document.getElementById("currentTime");

			if(nConnectSeconds>0){
		    	target.innerHTML = " (남은시간: "+ nConnectSeconds +"초)";
			}
			
			nConnectSeconds--;
		    setTimeout("setCurrentTime();",1000);
		}
	</script>
  </head>
  <body>
	<table class="memoryManage">
	<colgroup>
		<col width="50%">
	    <col width="">
	</colgroup>
	<thead>
	<tr>
		<th colspan="2" class="a_left">
			■ 측정시각 : <%= formatter.format(new Date()) %><span id="currentTime"></span>
			<br>
			■ 호스트 : <%= InetAddress.getLocalHost().getHostName() %> (<%= InetAddress.getLocalHost().getHostAddress() %>)
		</th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td class="left" colspan="2"></td>
	</tr>
	<tr bgcolor=#E3E3E3>
		<td colspan="2"><b>Memory Info</b></td>
	</tr>
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
		<td class="left" colspan="2"></td>
	</tr>
	<tr bgcolor=#E3E3E3>
		<td colspan="2"><b>Database Pool Info</b></td>
	</tr>
	<tr>
		<td class="left" colspan="2">JDBC URL : <%= dataSource.getUrl() %></td>
	</tr>
	<%-- 
	<tr>
		<td class="left" colspan="2">JDBC User: <%= dataSource.getUsername() %></td>
	</tr>
	 --%>
	<tr>
		<td class="left" colspan="2">
			<a title="connection이 생성될 때 초기에 생성될 pool의 개수">Pool initial size</a> 
			: <%= dataSource.getInitialSize() %>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="커넥션 풀에 유지하고 있어야 하는 idle 상태 커넥션의 최소 개수로, DBCP는 evictor 스레드를 통해 적어도 minIdle 개수만큼은 유지시킨다.">
			Pool min idle connections</a>
   			: <%= dataSource.getMinIdle() %>
   		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="커넥션 풀에 유지될 수 있는 idle 상태 커넥션의 최대 개수로, 그 이상의 커넥션들에 대해서는 반환 시에 커넥션 풀로 반환되는 것이 아니라 제거(real destroy)된다.">
			Pool max idle connections</a> 
			: <%= dataSource.getMaxIdle() %>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="서비스에서 동시에 사용될 수 있는 최대 커넥션 개수">Pool max active</a> 
			: <%= dataSource.getMaxActive() %>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="이미 커넥션 풀에서 사용 중인 커넥션의 개수가 maxActive 개수인 경우 getConnection 요청은 설정된 maxWait만큼 기다리게 된다. 만약 maxWait 후에도 사용할 수 있는 여분의 커넥션이 없을 경우는 ‘Cannot get a connection...’ 같은 에러를 발생시키게 된다.">
			Pool max wait</a> 
			: <%= dataSource.getMaxWait()%>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="사용할 수 있는 커넥션이 부족해진다면 DBCP는 버려진 커넥션을 찾아 복구하고 재생한다. 디폴트는 false">
			Remove Abandoned</a> 
			: <%= dataSource.getRemoveAbandoned() %>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="버려진 커넥션을 제거하는데 기본적으로 정해진 타임아웃 시간">
			Remove Abandoned Timeout</a> 
			: <%= dataSource.getRemoveAbandonedTimeout()%>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="pool에서 커넥션을 얻어올 때 테스트 실행, 기본 값은 true">
			Test On Borrow</a> 
			: <%= dataSource.getTestOnBorrow()%>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title=" pool로 커넥션을 반환할 때 테스트 실행, 기본 값은 false">
			Test On Return</a>  
			: <%= dataSource.getTestOnReturn()%>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="evictor 스레드가 실행될 때 (timeBetweenEviction RunMillis > 0) pool 안에 있는 idle 상태의 커넥션을 대상으로 테스트 실행, 기본 값은 false">
			Test While Idle</a> 
			: <%= dataSource.getTestWhileIdle()%>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="놀고 있는 connection을 pool에서 제거하는 시간
설정된 시간동안 놀고 있는 connection을 minIdle&maxIdel 설정값을 고려하여 제거한다.
기본값은 -1이며, 단위는 1/1000초이다.">Time Between Eviction Runs Millisecond</a> 
			: <%= dataSource.getTimeBetweenEvictionRunsMillis()%>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">
			<a title="connection 유효성 검사시에 사용할 쿼리">validationQuery</a> 
			: <%= dataSource.getValidationQuery()%>
		</td>
	</tr>
	<tr>
		<td class="left" colspan="2">Number of active connections : <%= dataSource.getNumActive() %></td>
	</tr>
	<tr>
		<td class="left" colspan="2">Number of idle connections : <%= dataSource.getNumIdle() %></td>
	</tr>
	<tr>
		<td class="left" colspan="2"></td>
	</tr>
	<tr>
		<td>
			[<a onclick="location.href='<%= request.getRequestURI() %>?gc=clear&interval='+document.getElementById('interval').value;">가비지컬렉션 실행</a>]
		</td>
		<td>
			<select id="interval">
		    <option value="5"<c:if test="${interval=='5'}"> selected</c:if>>5</option>
		    <option value="10"<c:if test="${interval=='10'}"> selected</c:if>>10</option>
		    <option value="30"<c:if test="${interval=='30'}"> selected</c:if>>30</option>
		    <option value="60"<c:if test="${interval=='60'}"> selected</c:if>>60</option>
		    <option value="120"<c:if test="${interval=='120'}"> selected</c:if>>120</option>
		    <option value="180"<c:if test="${interval=='180'}"> selected</c:if>>180</option>
		    <option value="240"<c:if test="${interval=='240'}"> selected</c:if>>240</option>
		    <option value="300"<c:if test="${interval=='300'}"> selected</c:if>>300</option>
		    </select>초
		    [<a onclick="location.href='<%= request.getRequestURI() %>?interval='+document.getElementById('interval').value;" style="cursor:pointer">Reload</a>]
		</td>
	</tr>
	<tbody>
	</table>
  </body>
</html>
<script type = "text/javascript">setCurrentTime();</script>