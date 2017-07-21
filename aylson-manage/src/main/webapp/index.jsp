<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>管理平台</title>
<link rel="Shortcut Icon" href="resources/images/favicon.ico">
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
	<body id="indexLayout" class="easyui-layout" fit="true">
		<div region="north" href="<%=request.getContextPath()%>/north"	style="height:90px;width:100%;overflow:hidden;background: url('<%=request.getContextPath()%>/resources/images/bg/top.png')"></div>
		<div region="west" href="<%=request.getContextPath()%>/west" title="系统导航" split="true" iconCls="icon-tip"	style="width: 200px; overflow: hidden;"></div>
		<div region="center" href="<%=request.getContextPath()%>/center" title="" style="overflow: hidden;"></div>
		<div region="south" href="<%=request.getContextPath()%>/south" style="height: 30px; overflow: hidden;"></div>
	</body>
</html>
