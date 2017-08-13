<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div region="north" style="white-space: nowrap;padding: 5px; height: 50px;">
		<div style="margin-bottom: 5px">
			<form id="sysReportInfoForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >查询日期：</td>
						<td class="tal" >
							<input class="easyui-datebox" name="currentTime" data-options="required:true"/>
						</td>
						<td class="tar" >查询类别：</td>
						<td class="tal" >
							<select name="type" class="easyui-combobox"  style="width: 150px;" editable=false>
								<option value="101">当日新增用户数</option>
								<option value="102">当日做任务总人数</option>
								<option value="103">当日完成任务总数</option>
								<option value="104">当日用户总收入</option>
								<option value="105">当日公司总收入</option>
							</select>
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
	<div data-options="region:'center'" border="false" style="overflow: hidden;width:85%">
    	<table id="datagrid"></table>
    </div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/cfdb/sysReportInfo.js?date=2016112516"></script>
</html>