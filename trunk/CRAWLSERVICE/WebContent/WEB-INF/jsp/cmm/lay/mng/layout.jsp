<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fnc" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="imagetoolbar" content="no" />

<TITLE><tiles:getAsString name="title" /></TITLE>

<link rel="stylesheet" type="text/css" href="/css/common.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/css/smoothness/jquery-ui-1.10.3.custom.css" media="screen">
<link rel="stylesheet" type="text/css" href="/css/jquery.colorpicker.css" media="screen" />
<script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#container").show();
	});
</script>
</head>
<body>
	<div id="container" style="display:none">
		<tiles:insertAttribute name="header" />
		<div id="content-container1">
			<div id="content-container2">
				<tiles:insertAttribute name="leftmenu" />
				<tiles:insertAttribute name="content" />
				<tiles:insertAttribute name="copyrights" />
			</div>
		</div>		
	</div>
</body>
</html>
