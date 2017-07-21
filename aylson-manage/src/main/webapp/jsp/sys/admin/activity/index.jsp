<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div region="north"  id="activityArea" style="white-space: nowrap;padding: 5px; height: 50px;">
		<div style="margin-bottom: 5px">
			<form id="activitySearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >活动主题：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="actThemeLike" prompt="模糊查询"/>
						</td>
					    <td style="padding-left:20px">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch()">搜索</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px" onclick="reset()">重置</a>
						</td> 
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'center'"  border="false" style="overflow: hidden;width:85%">
    	<table id="datagrid"></table>
    </div>
    <div id="dlg" class="easyui-dialog" title="查看活动报名情况" data-options="closed:true,resizable:true" style="width:840px;height:400px;">
		<table id="datagridRegister"></table>
	</div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/sys/activity.js?date=2016101817"></script>
</html>