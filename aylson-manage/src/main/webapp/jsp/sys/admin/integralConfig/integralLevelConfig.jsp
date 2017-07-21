<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<style>
	td{font-size:12px;padding:3px;height:25px;}
	.td_1{background-color:#eeeeff;}
	.td_2{padding:0px;}
</style>
<body >
	<div align="left" >
		<form id="integralConfigForm" method="post">
		<table class="tableForm" cellpadding="1" cellspacing="0" border="1" style="width:100%;border-collapse: collapse;border-style:solid;border-color:#95B8E7">
			<tr>
				<td class="td_1" colspan="4" style="font-weight:bolder;font-size:16px;">设计师积分等级以及业主资料分配配置：</td>
			</tr>
			<tr>
				<td colspan="4">
				说明：<br/>
				<p>&nbsp;&nbsp;1、业主资料最多派送给设计师的次数：配置后一个业主资料可以同时给几个设计师查看到</p>
				<p>&nbsp;&nbsp;2、会员积分等级：用于更新会员的积分等级，请从1开始递增</p>
				<p>&nbsp;&nbsp;3、会员等级积分范围：配置后，当会员获取的总积分在该范围时，自动更新会员的积分等级</p>
				<p>&nbsp;&nbsp;4、分配业主资料数量：配置系统发送给设计师的业主资料的最大次数</p>
				
				</td>
			</tr>
			<tr>
				<td class="td_1" style="width:300px;">业主资料最多派送给设计师的次数</td>
				<td colspan="3">
					<input name="integral"	value="${clientLimit.integral }" class="easyui-numberbox" data-options="required:true"
					style="width: 200px;" />
					<input name="type" value="12" type="hidden"/>
					<input name="rangeBegin" value="0" type="hidden"/>
					<input name="rangeEnd" value="0" type="hidden"/>
					<input name="rate" value="0" type="hidden"/>
				</td>
			</tr>
			<tr>
				<td class="td_1">积分等级配置：</td>
				<td colspan="3" class="td_2">
					<table id="table_1" cellpadding="1" cellspacing="0" border="1" style="width:100%;border-collapse: collapse;border-style:solid;border-color:#95B8E7">
						<tr id="tr_1">
						<td width="150px" class="td_1">会员积分等级</td>
						<td width="250px" class="td_1">会员等级积分范围</td>
						<td width="150px" class="td_1">分配业主资料数量</td>
						<td ><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width:80px" onclick="addConfig('table_1',11)">添加</a>
						</td>
						</tr>
						<c:forEach items="${configList}" var="configList">
							<tr>
								<td><input class="easyui-numberbox" name="integral" style="width: 100px;" value="${configList.integral}"/></td>
								<td >
									<input type="hidden" name="type" value="11"/>
									<input class="easyui-numberbox" name="rangeBegin" value="${configList.rangeBegin }" style="width: 100px;" />—
									<input class="easyui-numberbox" name="rangeEnd"  value="${configList.rangeEnd }"style="width: 100px;" />
								</td>
								<td >
									<input class="easyui-numberbox" value="${configList.rate }" name="rate"  style="width: 100px;" />&nbsp;
								</td>
								<td ><a href="#" onclick="delConfig(this)">删除</a></td>
							</tr>
						</c:forEach>
						<!-- <tr id="tr_2">
						<td colspan="4" ></td>
						</tr> -->
					</table>
				</td>
			</tr>
			<tr>
				<!-- <td class="td_1"></td> -->
				<td colspan="4" align="center">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="width:80px" onclick="save()">保存</a>
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px" onclick="reset()">重置</a>
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
<script>
$(function(){
	$(".easyui-numberbox").numberbox({required:true})
})
function delConfig(obj){
	$(obj).parent().parent().remove();
}
function addConfig(id,type){
	var config = "<tr><td ><input class=\"numberboxCss\" style=\"width: 100px;\" name=\"integral\" value=\"\"/></td>"+
	"<td><input type=\"hidden\" name=\"type\" value=\""+type+"\"/>"+
	"<input class=\"numberboxCss\" name=\"rangeBegin\" style=\"width: 100px;\" />—"+
	"<input class=\"numberboxCss\" name=\"rangeEnd\" style=\"width: 100px;\" /></td>"+
	"<td ><input name=\"rate\"  class=\"numberboxCss\" style=\"width: 100px;\" /></td>"+
	"<td ><a href=\"#\" onclick=\"delConfig(this)\">删除</a></td></tr>";
	$("#"+id).append(config);
	$(".numberboxCss").numberbox({required:true})
}

function save(){
	$("#integralConfigForm").form('submit',{
		 type:'POST',
		 url:projectName+'/sys/integralConfig/admin/integralLevelConfig',
		 success:function(responseData){
			 if(responseData){
				var data = $.parseJSON(responseData);
			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			 	if(data.success){
			 		//location.reload();
				}
			 } 
		 }
	 });
}

function reset(){
	$('#integralConfigForm').form('clear');
}
</script>
</html>