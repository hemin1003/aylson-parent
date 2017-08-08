<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		 <div title="广告配置" style="padding:10px;text-align:center">
		 	<form id="tasklistConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>广告名称：</th>
						<td colspan="3" style="text-align:left">
							<input name="taskName" value="${tasklistVo.taskName}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'" 
								onclick="uploadImg('img','logoUrl','yfax-test')" id="uploadImg" style="margin-bottom:10px;">上传广告Logo图</a>
						</th>
						<td colspan="3" style="text-align:left">
							<div style="width:240px;height:120px">
								<c:if test="${not empty tasklistVo.logoUrl}"><img id="img" src="${tasklistVo.logoUrl}" style="width:120px;height:120px"/></c:if>
								<c:if test="${empty tasklistVo.logoUrl }"><img id="img" src="" style="width:120px;height:120px"/></c:if>
								<input id="logoUrl" name="logoUrl" value="${tasklistVo.logoUrl}" type="hidden"/>
							</div>
						</td>
					</tr>
					<tr>
						<th>广告标签：</th>
						<td colspan="3" style="text-align:left">
							<input name="taskTag" value="${tasklistVo.taskTag}"
								class="easyui-textbox" data-options="required:true"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>剩余数量：</th>
						<td colspan="3" style="text-align:left">
							<input name="amount" value="${tasklistVo.amount}"
								class="easyui-numberbox" data-options="required:true"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>平台金额：</th>
						<td colspan="3" style="text-align:left">
							<input name="taskValue" value="${tasklistVo.taskValue}"
								class="easyui-numberbox" data-options="required:true"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>任务收益金额：</th>
						<td colspan="3" style="text-align:left">
							<input name="income" value="${tasklistVo.income}"
								class="easyui-numberbox" data-options="required:true"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>排序编号：</th>
						<td colspan="3" style="text-align:left">
							<input name="orderNo" value="${tasklistVo.orderNo}"
								class="easyui-numberbox" data-options="required:true"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>跳转url地址：</th>
						<td colspan="3" style="text-align:left">
							<input name="goUrl" value="${tasklistVo.goUrl}"
								class="easyui-textbox" data-options="required:true"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
				</table>
				<input name="taskId" type="hidden" value="${tasklistVo.taskId}"/>
			</form>
		</div>
	</div> 
</div>