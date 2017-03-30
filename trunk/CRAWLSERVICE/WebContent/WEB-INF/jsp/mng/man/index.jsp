<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.apache.commons.dbcp.BasicDataSource" %>
<script>
	var commonJS = {
		newsRolling : function(autoRollingClass, autoBoolean) {
			var findElement = $("." + autoRollingClass).find(" > ol");
			var autoPlay = autoBoolean;
			var autoState = true;
			var timer;
			var speed = 3000;
			var move = findElement.children().height();
			var lastChild = findElement.children().eq(-1).clone(true);

			lastChild.prependTo(findElement);
			findElement.children().eq(-1).remove();

			if (findElement.children().length == 1) {
				autoPlay = false;
			} else {
				findElement.css("top", "-" + move + "px");
			}

			if (autoPlay) {
				timer = setInterval(moveNextSlide, speed);
			} else {
				autoState = false;
			}

			findElement.find(" > li").on({
				"mouseenter" : function() {
					clearInterval(timer);
				},
				"mouseleave" : function() {
					timer = setInterval(moveNextSlide, speed);
				}
			});

			function movePrevSlide() {
				var lastChild = findElement.children().filter(":last-child")
						.clone(true);
				lastChild.prependTo(findElement);
				findElement.css("top", "-" + move + "px");
				findElement.children().filter(":last-child").remove();
				findElement.animate({
					"top" : "0"
				}, "normal");
			}

			function moveNextSlide() {
				var firstChild = findElement.children().filter(":first-child")
						.clone(true);
				firstChild.appendTo(findElement);
				findElement.children().filter(":first-child").remove();
				findElement.css("top", "0");
				findElement.animate({
					"top" : "-" + move + "px"
				}, "normal");
			}
		}
	}

	$(document).ready(function() {
		commonJS.newsRolling("news", true);
	});
	
	//웹페이지 크롤러
	function fncCrawling(){
		
		var f = document.crawler;
		var crowlUrl = f.crowlUrl.value;
		var sPage = f.sPage.value;
		var ePage = f.ePage.value;

		var url = "/mng/cwl/crawling.do";
		
		$.ajax({
			url : url,
			type: "POST",
			data : {"crowlUrl" : crowlUrl, "sPage" : sPage, "ePage" : ePage},
			error : ajaxError,
			success:function(response){
				if(response.status=="success"){
					alert("success");
				}else{
					alert("failer");

				}
			}
		});
	}	
	
	//웹페이지 크롤러
	function fncCrawlContent(){
		
		var f = document.crawler;
		var page_no = f.page_no.value;

		var url = "/mng/cwl/crawlContent.do";
		
		$.ajax({
			url : url,
			type: "POST",
			data : {"page_no" : page_no},
			error : ajaxError,
			success:function(response){
				if(response.status=="success"){
					f.page_no.value=Number(page_no)+1;
					sleep(2000);
					fncCrawlContent();
				}else if(response.status=="done"){
						f.page_no.value=Number(page_no)-1;
				}else{
					alert("failer");

				}
			}
		});
	}
	
	//웹페이지 크롤러
	function fncCrawlContentMerge(){
		
		var f = document.crawler;
		var page_no = f.page_no1.value;

		var url = "/mng/cwl/crawlContentMerge.do";
		
		$.ajax({
			url : url,
			type: "POST",
			data : {"page_no" : page_no},
			error : ajaxError,
			success:function(response){
				if(response.status=="success"){
					f.page_no1.value=Number(page_no)+1;
					sleep(2000);
					fncCrawlContentMerge();
				}else if(response.status=="done"){
						f.page_no1.value=Number(page_no)-1;
				}else{
					alert("failer");

				}
			}
		});
	}

	//웹페이지 
	function fncMakeTxt(){
		
		var f = document.crawler;
		var page_no = f.page_no2.value;

		var url = "/mng/cwl/makeTxt.do";
		
		$.ajax({
			url : url,
			type: "POST",
			data : {"page_no" : page_no},
			error : ajaxError,
			success:function(response){
				if(response.status=="success"){
					f.page_no2.value=Number(page_no)+1;
					sleep(2000);
					fncMakeTxt();
				}else if(response.status=="done"){
						f.page_no2.value=Number(page_no)-1;
				}else{
					alert("failer");
				}
			}
		});
	}
	
	
	

	function sleep(num){
		var now = new Date();
		var stop = now.getTime() + num;
		while (true) {
			now = new Date();
			if (now.getTime() > stop)
				return;
		}
	}
</script>

				<div id="content">
					<script type="text/javascript">
						$(function() {
							//뉴스 수집시간을 보여준다.
							getNewsTime();
						});
						
						function getNewsTime(){
							var sNewsTime = document.getElementById("newsTime").value;
							var year,mon,day,hour,min,sec;
							
							if(sNewsTime!=""){
								year =	sNewsTime.substring(0,4);
								mon =	sNewsTime.substring(4,6);
								day =	sNewsTime.substring(6,8);
								hour =	sNewsTime.substring(8,10);
								min =	sNewsTime.substring(10,12);
								sec =	sNewsTime.substring(12,14);
								
								document.getElementById("displayTime").innerText= "[ 수집시간 : "+ year + '년 ' + mon + '월 ' + day + '일 ' + hour + ":" + min + ":" + sec+" ]";
							}
						}
					</script>
					<h2 class="a_left">최신뉴스</h2>
					<div class="count a_left"><b><fmt:formatNumber value="${newsCount}"/></b>건의 최신 뉴스가 검색되었습니다. <span id="displayTime"></span></div>
					<div class="newsList">
					  <div class="news a_left">
					    <ol>
					    	<c:if test="${not empty newsList}">
		                		<c:forEach var="result" items="${newsList}" varStatus="newsIdx">
		                			<c:if test="${newsIdx.index==0}"><c:set var="newsTime" value="${result.news_time}"/></c:if>
		                            <li><a href="${result.link}" target="_blank" title="${fnc:replace(result.pubdate,' +0900','')} <c:out value="${result.subject}"/>"><fmt:formatNumber value="${newsIdx.index+1}"/>. <c:out value="${result.subject}"/></a></li>
		                        </c:forEach>
		                	</c:if>
					    </ol>
					  </div>
					</div>
					<%-- 
		            <table class="tbl" summary="뉴스 목록입니다.">
		            <caption class="accessibility">뉴스 목록</caption>
		            <colgroup>
						<col width="8%"/>
		                <col />
					</colgroup>
		            	<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
							</tr>
						</thead>
		                <tbody>
		                <c:set var="newsTime" value=""/>
		                <c:choose>
		                	<c:when test="${not empty newsList}">
		                		<c:forEach var="result" items="${newsList}" varStatus="status">
		                		<c:if test="${status.index==0}"><c:set var="newsTime" value="${result.news_time}"/></c:if>
		                        <tr>
		                            <td class="a_center"><fmt:formatNumber value="${status.index+1}"/></td>
		                            <td class="a_left"><a href="${result.link}" target="_blank" title="${fnc:replace(result.pubdate,' +0900','')} ${result.subject}"><c:out value="${result.subject}"/></a></td>
		                        </tr>
		                        </c:forEach>
		                	</c:when>
		                	<c:otherwise>
		                        <tr>
		                            <td colspan="2" class="a_center">검색 결과가 없습니다.</td>
		                        </tr>	                	
		                	</c:otherwise>
		                </c:choose>
		                </tbody>
		            </table>
		             --%>
		            <input type="hidden" id="newsTime" value="${newsTime}"/>
					<hr>
					<sec:authorize access="hasRole('SUPERADMIN')">
						<c:if test="${not empty fileChkList}">
						<h2 class="a_left">파일오류 정보</h2>
						<div class="count a_left t_red"><b>긴급히 수정해야 할 파일이 존재합니다.</b></div>
			            <table class="tbl" summary="파일오류 목록">
			            <caption class="accessibility">파일오류 목록</caption>
			            <colgroup>
							<col width="8%"/>
			                <col />
			                <col width="40%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
			                        <th>오류내용</th>
								</tr>
							</thead>
			                <tbody>
		                		<c:forEach var="result" items="${fileChkList}" varStatus="status">
		                        <tr>
		                            <td class="a_center"><fmt:formatNumber value="${result.chk_seq}"/></td>
		                            <td class="a_left">
		                            <c:choose>
		                            	<c:when test="${result.chk_table=='APP_INFO'}">
		                            		APP 관리 ==> 
		                            		<a href="/mng/app/view.do?app_id=${result.chk_table_id}">${result.chk_table_id}</a>
		                            		<span class="btn_pack small_sky"><a href="/common/downLoadFile.do?type=app&app_id=${result.chk_table_id}" title="다운로드">다운로드</a></span>
		                            	</c:when>
		                            	<c:when test="${result.chk_table=='CORP_INFO'}">
		                            		회사관리 ==>
		                            		<a href="/mng/crp/view.do?corp_id=${result.chk_table_id}">${result.chk_table_id}</a>
		                            		<span class="btn_pack small_sky"><a href="/common/downLoadFile.do?type=corp_logo&corp_id=${result.chk_table_id}" title="다운로드">다운로드</a></span>
		                            	</c:when>
		                            	<c:when test="${result.chk_table=='FILE_INFO'}">
		                            		파일관리 ==>
		                            		<a href="/mng/fle/view.do?file_id=${result.chk_table_id}">${result.chk_table_id}</a>
		                            		<span class="btn_pack small_sky"><a href="/common/downLoadFile.do?type=filemanage&file_id=${result.chk_table_id}" title="다운로드">다운로드</a></span>
		                            	</c:when>
		                            </c:choose>
		                            </td>
		                            <td class="a_left"><c:out value="${result.chk_err_desc}"/></td>
		                        </tr>
		                        </c:forEach>
			                </tbody>
			            </table>
			            <hr>
			            </c:if>
			            
			            <h2 class="a_left">셋탑 접속 정보</h2>
			            <table class="tbl" summary="셋탑 접속 정보">
			            <caption class="accessibility">셋탑 접속 정보</caption>
			            <colgroup>
			                <col />
			                <col width="17%"/>
			                <col width="17%"/>
			                <col width="17%"/>
			                <col width="17%"/>
			                <col width="17%"/>
						</colgroup>
			            	<thead>
								<tr>
									<th>전체</th>
									<th>1시간</th>
									<th>12시간</th>
									<th>1일</th>
									<th>7일</th>
									<th>7일이상</th>
								</tr>
							</thead>
			                <tbody>
		                        <tr>
		                            <td class="a_right"><a href="/mng/eup/list.do" title="기기관리 바로가기"><fmt:formatNumber value="${conStpInfo.tot_cnt}"/></a></td>
		                            <td class="a_right"><a href="/mng/eup/list.do" title="기기관리 바로가기"><fmt:formatNumber value="${conStpInfo.cnt1}"/></a></td>
		                            <td class="a_right"><a href="/mng/eup/list.do" title="기기관리 바로가기"><span class="t_blue"><fmt:formatNumber value="${conStpInfo.cnt2}"/></span></a></td>
		                            <td class="a_right"><a href="/mng/eup/list.do" title="기기관리 바로가기"><span class="t_blue"><b><fmt:formatNumber value="${conStpInfo.cnt3}"/></b></span></a></td>
		                            <td class="a_right"><a href="/mng/eup/list.do" title="기기관리 바로가기"><span class="t_red"><fmt:formatNumber value="${conStpInfo.cnt4}"/></span></a></td>
		                            <td class="a_right"><a href="/mng/eup/list.do" title="기기관리 바로가기"><span class="t_red"><b><fmt:formatNumber value="${conStpInfo.cnt5}"/></b></span></a></td>
		                        </tr>
			                </tbody>
			            </table>
			            <hr>

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
						<meta http-equiv="Refresh" content="<%= interval %>;url=<%= (String)request.getAttribute("javax.servlet.forward.request_uri")%>?interval=${interval}"/>
						<style type="text/css">
							a {cursor:pointer}
							.a_left {text-align: left !important;}
							.a_center {	text-align: center !important;}
							table.memoryManage {margin-bottom:10px; width: 100%; border-collapse:collapse;}
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
							$(function() {
								setCurrentTime();
							});
						</script>
						<h2 class="a_left">메모리정보</h2>
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
									■ 호스트 : <%= InetAddress.getLocalHost().getHostName() %>
									<br>
									■ IP : <%= InetAddress.getLocalHost().getHostAddress() %>
								</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td colspan="2"></td>
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
								<td colspan="2"></td>
							</tr>
							<tr bgcolor=#E3E3E3>
								<td colspan="2"><b>Database Pool Info</b></td>
							</tr>
							<%-- 
							<tr>
								<td class="left" colspan="2">JDBC URL : <%= dataSource.getUrl() %></td>
							</tr>
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
									<a title="커넥션 풀에 유지하고 있어야 하는 idle 상태 커넥션의 최소 개수로, DBCP는 evictor 스레드를 통해 적어도 min Idle 개수만큼은 유지시킨다.">
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
									<a title="이미 커넥션 풀에서 사용 중인 커넥션의 개수가 maxActive 개수인 경우 getConnection 요청은 설정된 max Wait만큼 기다리게 된다. 만약 max Wait 후에도 사용할 수 있는 여분의 커넥션이 없을 경우는 ‘Cannot get a connection...’ 같은 에러를 발생시키게 된다.">
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
									[<a onclick="location.href='<%= (String)request.getAttribute("javax.servlet.forward.request_uri")%>?gc=clear&interval='+document.getElementById('interval').value;">가비지컬렉션 실행</a>]
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
								    [<a onclick="location.href='<%= (String)request.getAttribute("javax.servlet.forward.request_uri")%>?interval='+document.getElementById('interval').value;" style="cursor:pointer">Reload</a>]
								</td>
							</tr>
							<tbody>
						</table>	
						<hr>
						<form name="crawler" method="post">
						<table class="tbl">
							<colgroup>
								<col width="50%">
								<col width="20%">
								<col width="20%">
								<col>
							</colgroup>
							<tr>
								<th>URL</th>
								<th>시작페이지</th>
								<th>종료페이지</th>
								<th>실행</th>
							</tr>
							<tr>
								<!-- <td><input type="text" name="crowlUrl" value="http://www.munhwa.com/news/series.html?secode=677&page=" style="width:300px"/></td> -->
								<td><input type="text" name="crowlUrl" value="http://www.ssulwar.com/index.php?mid=ssulbkbssss&page=" style="width:300px"/></td>
								<td class="a_center"><input type="text" name="sPage" value="1" style="width:20px"/></td>
								<td class="a_center"><input type="text" name="ePage" value="733" style="width:20px"/></td>
								<td class="a_center"><a href="javascript:fncCrawling();" class="button">실행</a></td>
							</tr>
							<tr>
								<td>내용</td>
								<td class="a_center"><input type="text" name="page_no" value="1" style="width:20px"/></td>
								<td class="a_center" colspan="2"><a href="javascript:fncCrawlContent();" class="button">내용 수집 실행</a></td>
							</tr>
							<tr>
								<td>내용</td>
								<td class="a_center"><input type="text" name="page_no1" value="1" style="width:20px"/></td>
								<td class="a_center" colspan="2"><a href="javascript:fncCrawlContentMerge();" class="button">내용 병합 실행</a></td>
							</tr>
							<tr>
								<td>내용</td>
								<td class="a_center"><input type="text" name="page_no2" value="1" style="width:20px"/></td>
								<td class="a_center" colspan="2"><a href="javascript:fncMakeTxt();" class="button">txt make</a></td>
							</tr>
						</table>
						</form>
					</sec:authorize>
					<%-- 				
					<sec:authorize access="hasRole('SUPERADMIN')">
						<script type="text/javascript">
							$(function() {
								// Link to open the dialog
								$("#btn_searchCorp").click(function( event ) {
									$("#CorpList").dialog("open");
									event.preventDefault();
								});
								
								$("#CorpList").dialog({
									autoOpen: false,
									width: 400,
									buttons: [
										{
											text: "닫기",
											click: function() {
												$(this).dialog("close");
											}
										}
									]
								});
							});
						</script>					
						<h2>Page heading , 한글 테스트</h2>
						<p>Lorem ipsum dolor sit amet consect etuer adipi scing elit
							sed diam nonummy nibh euismod tinunt ut laoreet dolore magna
							aliquam erat volut. Ut wisi enim ad minim veniam, quis nostrud
							exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea
							commodo consequat. Duis autem vel eum iriure dolor in hendrerit in
							vulputate velit esse molestie consequat, vel illum dolore eu
							feugiat nulla facilisis at vero eros et accumsan et iusto odio
							dignissim qui blandit praesent luptatum zzril delenit augue duis
							dolore te feugait nulla facilisi.</p>
						<p>Ut wisi enim ad minim veniam, quis nostrud exerci tation
							ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo
							consequat. Duis autem vel eum iriure dolor in hendrerit in
							vulputate velit esse molestie consequat, vel illum dolore eu
							feugiat nulla facilisis at vero eros et accumsan et iusto odio
							dignissim qui blandit praesent luptatum zzril delenit augue duis
							dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet,
							consectetuer adipiscing elit, sed diam nonummy nibh euismod
							tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
						<p>Duis autem vel eum iriure dolor in hendrerit in vulputate
							velit esse molestie consequat, vel illum dolore eu feugiat nulla
							facilisis at vero eros et accumsan et iusto odio dignissim qui
							blandit praesent luptatum zzril delenit augue duis dolore te
							feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer
							adipiscing elit, sed diam nonummy nibh euismod tincidunt ut
							laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim
							veniam, quis nostrud exerci tation ullamcorper suscipit lobortis
							nisl ut aliquip ex ea commodo consequat.</p>
	
						<table class="tbl">
							<colgroup>
								<col span="2">
								<col>
							</colgroup>
							<tr>
								<th>ISBN</th>
								<th>Title</th>
								<th>Title</th>
								<th>Price</th>
							</tr>
							<tr>
								<td>3476896</td>
								<td class="a_left"><a href="#">Link Test</a></td>
								<td class="a_left">My first HTML</td>
								<td class="a_right">$53</td>
							</tr>
						</table>
						<div class="buttons">
							<a href="#" class="button">Stand Alone</a><a href="#"
								class="button left">Left</a><a href="#" class="button middle">Middle</a><a
								href="#" class="button middle">Middle</a><a href="#"
								class="button right">Right</a><a href="#" class="button">Stand
								Alone</a>
						</div>
						<div class="buttons" style="padding-bottom: 40px;">
							<a href="#" class="button big">Stand Alone</a><a href="#"
								class="button left big">Left</a><a href="#"
								class="button middle big">Middle</a><a href="#"
								class="button middle big">Middle</a><a href="#"
								class="button right big">Right</a><a href="#" class="button big">Stand
								Alone</a>
						</div>
	
						<div class="buttons">
							<a href="#" class="button save">Save</a><a href="#"
								class="button add">Add Item</a><a href="#" class="button delete">Delete</a><a
								href="#" class="button flag">Flag</a><a href="#"
								class="button left up">&nbsp;</a><a href="#"
								class="button right down">&nbsp;</a>
						</div>
	
						<div class="buttons">
							<a href="#" class="button save-big">Save</a><a href="#"
								class="button add-big">Add Item</a><a href="#"
								class="button delete-big">Delete</a><a href="#"
								class="button flag-big">Flag</a><a href="#"
								class="button left up-big">&nbsp;</a><a href="#"
								class="button right down-big">&nbsp;</a>
						</div>
						
						<script type="text/javascript">
						    function openDialog(title,url) {
						        $('.opened-dialogs').dialog("close");
						
						        $('<div class="opened-dialogs">').dialog({
						            //position:  ['center',20],
						            open: function () {
						                $(this).load(url);
						            },
						            close: function(event, ui) {
								        $(this).remove();
						            },
						            title: title,
						            minWidth: 600,
						            minHeight: 600,
					                modal: true
						        });
						
						        return false;
						    }
						</script>
					
						<li><button class="btn" onClick="openDialog('New Type','/mng/man/index.do')">Dialog1</button></li>
	
					    <script type="text/javascript">
					    $(function (){
					        $('a.ajax').click(function() {
					            var url = this.href;
					            // show a spinner or something via css
					            //alert(url);
					            var dialog = $('<div style="display:none" class="loading"></div>').appendTo('body');
					            // open the dialog
					            //dialog.load(url).dialog({modal:true}); 
					            dialog.dialog({
					            	//position:  ['center',20],
					                // add a close listener to prevent adding multiple divs to the document
					                close: function(event, ui) {
					                    // remove div with all data and events
					                    dialog.remove();
					                },
					/*                 open :function () {
					                    $(this).load(url);
					
					                }, */
					                title: "test",
					                modal: true
					            });
					            // load remote content
					            dialog.load(
					                url, 
					                {}, // omit this param object to issue a GET request instead a POST request, otherwise you may provide post parameters within the object
					                function (responseText, textStatus, XMLHttpRequest) {
					                    // remove the loading class
					                    dialog.removeClass('loading');
					                }
					            );
					            //prevent the browser to follow the link
					            return false;
					        });
					    });
					    </script>
					
						<li><a class="ajax" href="http://cafe.naver.com">Dialog2-Open as dialog</a></li>
						
						<script type="text/javascript">
						    function openDialog3(title,url) {
						    	$('.opened-dialogs').dialog("close");
						    	
						        $('<div class="opened-dialogs">').load(url).dialog({
						            position:  [205,95],
						            close: function(event, ui) {
								        $(this).remove();
						            },
						    		title: title,
						    		minWidth: 480,
						    		modal:true     
						        });
						
						        return false;
					
						    }
						</script>
						<li><button class="btn" onClick="openDialog3('Memory측정','/memory.jsp')">Dialog3-Memory측정</button></li>	
						<div id="CorpList" title="회사목록">
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
						</div>	
						<a class="button" id="btn_searchCorp" title="검색">검색</a>	
					</sec:authorize>
					 --%>			
				</div>
				