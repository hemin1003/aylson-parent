<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/plugins/code/prettify.css" />
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/lang/zh_CN.js"></script>
<style>
.param_th{
text-align:center!important;
font-size:14px;
padding:5px;
}
.param_td_oper{
	text-align:center!important;
}
.imgItem_td_1{
	width:60px;
	height:100px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:60px;
}
.basic_td{
text-align:left!important;
}
</style>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="广告任务审批" style="padding:10px;text-align:center">
		 	<form id="userTasklistConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>手机标识码：</th>
						<td colspan="3" style="text-align:left">
							<input name="phoneId" value="${userTasklistVo.phoneId}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>广告名称：</th>
						<td colspan="3" style="text-align:left">
							<input name="taskName" value="${userTasklistVo.taskName}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>广告标签：</th>
						<td colspan="3" style="text-align:left">
							<input name="taskTag" value="${userTasklistVo.taskTag}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>用户收益金额：</th>
						<td colspan="3" style="text-align:left">
							<input name="income" value="${userTasklistVo.income}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>跳转url地址：</th>
						<td colspan="3" style="text-align:left">
							<input name="goUrl" value="${userTasklistVo.goUrl}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>用户抢购任务时间：</th>
						<td colspan="3" style="text-align:left">
							<input value="${userTasklistVo.createDate}"
								class="easyui-datetimebox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>用户提交审核时间：</th>
						<td colspan="3" style="text-align:left">
							<input value="${userTasklistVo.updateDate}"
								class="easyui-datetimebox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th><font color="red">*</font>广告任务状态：</th>
						<td colspan="3" style="text-align:left">
							<select id="statusFlag" name="statusFlag" class="easyui-combobox" value="${userTasklistVo.statusFlag}" 
								data-options=" 
								onSelect:function(data){
									$('#status').val(data.text);
								}" 
								style="width:50%; " editable=false>
								<option value="2">审核中</option>
								<option value="3">审核成功</option>
								<option value="4">审核失败</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>待审批姓名：</th>
						<td colspan="3" style="text-align:left">
							<input value="${userTasklistVo.proveName}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>待审批手机号码：</th>
						<td colspan="3" style="text-align:left">
							<input value="${userTasklistVo.proveMobile}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>待审批图片：</th>
						<td colspan="3" style="text-align:left">
							<input value="${userTasklistVo.proveImagesUrl}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>扩展信息1：</th>
						<td colspan="3" style="text-align:left">
							<input value="${userTasklistVo.proveColumn1}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>扩展信息2：</th>
						<td colspan="3" style="text-align:left">
							<input value="${userTasklistVo.proveColumn2}"
								class="easyui-textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${userTasklistVo.id}"/>
				<input name="taskId" type="hidden" value="${userTasklistVo.taskId}"/>
				<input name="logoUrl" type="hidden" value="${userTasklistVo.logoUrl}"/>
				<input name="status" id="status" value="${userTasklistVo.status}" type="hidden"/>
				<input name="statusFlagOld" id="statusFlagOld" type="hidden" value="${userTasklistVo.statusFlag}"/>
				<input name="proveDate" type="hidden" value="${userTasklistVo.proveDate}"/>
				<input name="isFirstSuc" type="hidden" value="${userTasklistVo.isFirstSuc}"/>
				
			</form>
		</div>
	</div> 
</div>
<script type="text/javascript">
$(function(){
	var select = document.getElementById("statusFlag");  
	var value = $('#status').val();  
	for(var i=0; i<select.options.length; i++){  
	    if(select.options[i].innerHTML == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}  
});
</script>