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
			<form id="couponSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >活动类型：</td>
						<td class="tal" >
							<select name="activityType" class="easyui-combobox"  style="width: 120px;" editable=false>
								<option value="">全部</option>
								<option value="1">线上活动</option>
								<option value="2">线下活动</option>
								<option value="3">电商活动</option>
							</select>
						</td>
						<td class="tar" >发布状态：</td>
						<td class="tal" >
							<select name="state" class="easyui-combobox"  style="width: 120px;" editable=false>
								<option value="">全部</option>
								<option value="0">未发布</option>
								<option value="1">已发布</option>
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
   <!-- <div class="easyui-tabs" id="tab_activiti" data-options="region:'center'" border="false" style="overflow: hidden;width:100%;height:1000px;">
    	<div title="总部活动" style="padding:10px">
    		<table id="datagrid_org"></table>
    	</div>
    	<div title="门店活动"  style="padding:10px">
    		<table id="datagrid_agent"></table>
    	</div>
    </div> -->
    <div id="auditOpitionDlg" class="easyui-dialog" title="审核意见" data-options="closed:true,resizable:true" style="width:600px;height:400px;">
		<div id="auditOpitionDiv" style="text-align:center;">
			<table class="tableForm" >
				<td>
				<input id="auditOpinion" data-options="multiline:true,required:true" prompt="请输入审核意见" style="height:310px;width:560px;"  value="" class="easyui-textbox" />
				</td>
			</table>
		</div>
	</div>
    <input type="hidden" id="curUserType"  value="${sessionInfo.user.type}"
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/sys/couponActivity.js?date=2016112516"></script>
</html>