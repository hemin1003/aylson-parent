<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../resources/inc/meta.jsp"></jsp:include>
<jsp:include page="../../resources/inc/easyui.jsp"></jsp:include>
<jsp:include page="../../resources/inc/easyui-portal.jsp"></jsp:include>
<script type="text/javascript">
	var portal;
	var col;
	$(function() {
		col = $('#portal div').length;
		portal = $('#portal').portal({
			border : false,
			fit : true
		});
	});
</script>
</head>
<body class="easyui-layout" fit="true" style="/* background-color:#CDE4E7; */">
	<div region="center" style="overflow: hidden;text-align:center;background-attachment:fixed;" border="false">
		<div id="portal" style="position:relative;">
			<h3>欢迎回来，后台信息管理平台~</h3>
		</div>
	</div>
</body>
</html>