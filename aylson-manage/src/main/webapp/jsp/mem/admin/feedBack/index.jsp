<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
<link href="<%=request.getContextPath()%>/resources/css/sys.css" rel="stylesheet" type="text/css" media="screen">
</head>
<body class="easyui-layout" fit="true">
	<!-- 角色管理 查询 --> 
	<div region="north"  id="feedBackArea" class="feedBackArea" style="white-space: nowrap;padding: 5px; height: 50px;">
		<div style="margin-bottom: 5px">
			<form id="feedBackSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >提交时间：</td>
						<td class="tal" >
							<input class="easyui-datebox" name="feedbackTimeStr" />
						</td>
						<td class="tar" >提交时间开始：</td>
						<td class="tal" >
							<input class="easyui-datebox" name="feedbackTimeBegin" />
						</td>
						<td class="tar" >提交时间介绍：</td>
						<td class="tal" >
							<input class="easyui-datebox" name="feedbackTimeEnd" />
						</td>
					    <td style="padding-left:20px">
					    	<input name="feedbackerType" type="hidden" value="${type}"/>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch()">搜索</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px" onclick="reset()">重置</a>
						</td> 
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="centerRegion" data-options="region:'center',title:''"  border="false" style="overflow: hidden;width:85%">
    	<table id="datagrid"></table>
    </div>
</body>
<input id="type" type="hidden" value="${type}"/>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/mem/feedBack.js?date=2016111805"></script>
</html>