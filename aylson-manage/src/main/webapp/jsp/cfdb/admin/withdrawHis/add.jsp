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
		 <div title="提现操作" style="padding:10px;text-align:center">
		 	<form id="withdrawHisConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>手机标识码：</th>
						<td colspan="3" style="text-align:left">
							<input value="${withdrawHisVo.phoneId}"
								class="easyui-validatebox textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>提现类型：</th>
						<td colspan="3" style="text-align:left">
							<input value="${withdrawHisVo.withdrawName}"
								class="easyui-validatebox textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>姓名：</th>
						<td colspan="3" style="text-align:left">
							<input value="${withdrawHisVo.name}"
								class="easyui-validatebox textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>账户名：</th>
						<td colspan="3" style="text-align:left">
							<input value="${withdrawHisVo.account}"
								class="easyui-validatebox textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>提现金额：</th>
						<td colspan="3" style="text-align:left">
							<input value="${withdrawHisVo.income}"
								class="easyui-validatebox textbox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th>提现时间：</th>
						<td colspan="3" style="text-align:left">
							<input value="${withdrawHisVo.withdrawTime}"
								class="easyui-datetimebox"
								style="width:95%; text-align:left" readOnly=true/>
						</td>
					</tr>
					<tr>
						<th><font color="red">*</font>提现状态：</th>
						<td colspan="3" style="text-align:left">
							<select id="statusType" name="statusType" class="easyui-combobox" value="${withdrawHisVo.statusType}" 
								data-options=" 
								onSelect:function(data){
									$('#status').val(data.text);
								}" 
								style="width:50%; " editable=false>
								<option value="1">处理中</option>
								<option value="2">支付成功</option>
								<option value="3">充值成功</option>
								<option value="4">失败</option>
							</select>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${withdrawHisVo.id}"/>
				<input name="status" id="status" value="${withdrawHisVo.status}" type="hidden"/>
			</form>
		</div>
	</div> 
</div>
<script type="text/javascript">
$(function(){
	var select = document.getElementById("statusType");  
	var value = $('#status').val();  
	for(var i=0; i<select.options.length; i++){  
	    if(select.options[i].innerHTML == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}  
});
</script>