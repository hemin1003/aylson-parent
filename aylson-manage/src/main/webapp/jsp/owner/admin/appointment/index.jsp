<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
<link href="<%=request.getContextPath()%>/resources/css/aylson/owner/appointment.css?v_date=2016122903" rel="stylesheet" type="text/css" media="screen">
</head>
<body class="easyui-layout" fit="true">
	<div region="north"  id="appointmentArea" style="white-space: nowrap;padding: 5px; height: 80px;">
		<div style="margin-bottom: 5px">
			<form id="appointmentSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >预约时间：</td>
						<td class="tal" >
							<input class="easyui-datebox" name="appointDate"  style="width: 120px;"/>
						</td> 
						<td class="tar" >装修项目：</td>
						<td class="tal" >
							<select  class="easyui-combobox" name="decorateProjectLike" style="width: 100px;" editable=false>
								<option value="">全部</option>
								<option value="1">门</option>
								<option value="2">窗</option>
								<option value="3">阳光房</option>
							</select>
						</td> 
						<td class="tar" >上门设计师姓名：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="designerLike" style="width: 120px;" prompt="模糊查询"/>
						</td> 
						<td class="tar" >流水单号：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="billCodeLike" style="width: 120px;" prompt="模糊查询"/>
						</td> 
					</tr>
					<tr>
						<td class="tar" >上门时间：</td>
						<td class="tal" >
							<input class="easyui-datebox" name="homeTime"  style="width: 120px;"/>
						</td> 
						<td class="tar" >预约人电话：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="mobilePhoneLike" style="width: 120px;" prompt="模糊查询"/>
						</td> 
						<td class="tar" >上门设计师电话：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="designerPhoneLike" style="width: 120px;" prompt="模糊查询"/>
						</td> 
						<td class="tar" >所在城市：</td>
						<td clospan="3">
							<input  id="provinceId_s" name="provinceId" 
							data-options="editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=1', valueField:'regionId', textField:'regionName', method:'GET',
							onSelect:function(record){
								$('#cityId_s').combobox('clear');
								$('#areaId_s').combobox('clear');
								$('#cityId_s').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
							}
							" class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
							<input  id="cityId_s" name="cityId"  
							data-options="editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=2', valueField:'regionId', textField:'regionName', method:'GET',
							onSelect:function(record){
								$('#areaId_s').combobox('clear');
								$('#areaId_s').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
							}
							" class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
							<input  id="areaId_s" name="areaId" 
							data-options="editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=3', valueField:'regionId', textField:'regionName', method:'GET'" 
							class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
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
	<div data-options="region:'center'"  border="false" style="overflow: hidden;width:80%">
    	<table id="datagrid"></table>
    </div>
    <div id="ft" region="south" title="设计信息表" border="false" split="true" style="height:250px;">
 	  	 <table id="datagridDesign"></table>
	</div>
  	<div id="viewDetailDrawDlg" class="easyui-dialog" title="查看附件" data-options="closed:true,resizable:true" style="width:60%;height:60%;">
		<div id="viewDetailDraw" style="text-align:center;">
		</div>
	</div>
  	<div id="viewDrawDlg" class="easyui-dialog" title="查看大样图" data-options="closed:true,resizable:true" style="width:80%;height:80%;">
		<div id="viewDraw" style="text-align:center;">
		</div>
	</div>
	<div id="goodAmountDlg" class="easyui-dialog" title="配件金额明细" data-options="closed:true,resizable:true" style="width:450px;height:600px;">
		<div id="goodAmountDetail" style="text-align:center;">
			<form id="goodAmountForm">
			<table class="tableForm" id="goodAmountTable">
				<tr id="goodAmountTable_head"><th style="width:120px;">配件名称</th><th style="width:120px;">配件金额</th><th><a id="goodAmountTable_head_a" href="#" class="easyui-linkbutton c8" style="width:80px;" onclick="addGoodAmountDetai()">添加</a></th></tr>
				<!-- <tr>
				<td><input class="easyui-textbox goodName" data-options="required:true" style="width: 120px;" /></td>
				<td><input class="easyui-numberbox goodAmount" data-options="precision:2,min:1,required:true" style="width: 120px;"/></td>
				<td><a href="#" class="easyui-linkbutton c8" onclick="delGoodAmountDetai(this)">删除</a></td>
				</tr> -->
			</table>
			</form>
		</div>
	</div>
	<div id="quotationRemarkDlg" class="easyui-dialog" title="订货单备注" data-options="closed:true,resizable:true" style="width:600px;height:400px;">
		<div id="goodAmountDetail" style="text-align:center;">
			<table class="tableForm" >
				<td>
				<input id="quotationDlgRamark" data-options="multiline:true," prompt="请输入报价单的备注信息" style="height:310px;width:560px;"  value="" class="easyui-textbox" />
				</td>
			</table>
		</div>
	</div>
</body>
<input id="roleCode" value="${sessionInfo.role.roleCode}" />
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/owner/appointment.js?date=2017012002"></script>
</html>
<style>
#goodAmountTable th{
	text-align:center;
	padding:5px;
}
</style>
<script>
$(function(){
	//editGoodAmountDetail(1,2);
})

//添加配件金额明细
function addGoodAmountDetai(){
	var html = "";
	html += "<tr>";
	html += "	<td><input class='goodName'  style='width: 120px;' /></td>";
	html += "	<td><input class='goodAmount'  style='width: 120px;'/></td>";
	html += "	<td><a href='#' class='goodAmoutbutton' onclick='delGoodAmountDetai(this)'>删除</a></td>";
	html += "</tr>";
	$("#goodAmountTable_head").after(html);
	$(".goodAmoutbutton").linkbutton();
	$(".goodAmoutbutton").addClass("c8");
	$(".goodName").textbox({
		"required":true
	});
	$(".goodAmount").numberbox({
		"required":true,
		"precision":2,
		"min":0
	});
}

//删除配件金额明细
function delGoodAmountDetai(obj){
	$(obj).parent().parent().remove();
}
</script>