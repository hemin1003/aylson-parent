<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="overflow: hidden;">
			<div id="tt" class="easyui-tabs" fit="true" border="false" tabPosition="top">
		        <!-- <div title="角色用户">
		           <table id="usergrid"></table>
		        </div> -->
		        <div title="菜单授权">
		           <table id="menugrid" class="easyui-treegrid"></table>
		        </div>
		        <!-- <div title="资源授权">
		           <table id="resgrid" class="easyui-treegrid"></table>
		        </div> -->
		    </div>
		</div>
		<div region="west" border="false" title="系统角色" split="true" iconCls="icon-large-chart" style="overflow: hidden; width:150px;">
			<ul id="roletree" style="margin-top: 0px;"></ul>
		</div>
	</div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/sys/authorize.js?date=2016050807"></script>
</html>