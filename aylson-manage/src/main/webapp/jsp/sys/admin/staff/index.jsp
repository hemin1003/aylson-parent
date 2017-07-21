<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<!-- 门店展示管理 查询 --> 
	<div region="north"  id="staffArea" style="white-space: nowrap;padding: 5px; height: 50px;">
		<div style="margin-bottom: 5px">
			<form id="staffSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >姓名：</td>
						<td class="tal" >
							<input name="staffNameLike" class="easyui-textbox" style="" prompt="模糊查询"/>
						</td>
						<td class="tar" >电话：</td>
						<td class="tal" >
							<input name="staffPhoneLike" class="easyui-textbox" style="" prompt="模糊查询"/>
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
</body>
<input type="hidden" value="${sessionInfo.user.type}" id="userType"/>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/sys/staff.js?date=2017021303"></script>
</html>