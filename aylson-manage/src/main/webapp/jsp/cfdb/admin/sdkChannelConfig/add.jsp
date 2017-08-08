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
		 <div title="平台渠道分成配置" style="padding:10px;text-align:center">
		 	<form id="sdkChannelConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>SDK广告渠道标识：</th>
						<td colspan="3" style="text-align:left">
							<input name="channelFlag" value="${sdkChannelConfigVo.channelFlag}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>SDK广告渠道名称：</th>
						<td colspan="3" style="text-align:left">
							<input name="channelName" value="${sdkChannelConfigVo.channelName}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>换算比例：</th>
						<td colspan="3" style="text-align:left">
							<input name="cRate" value="${sdkChannelConfigVo.cRate}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>分成比例：</th>
						<td colspan="3" style="text-align:left">
							<input name="sRate" value="${sdkChannelConfigVo.sRate}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>优先级：</th>
						<td colspan="3" style="text-align:left">
							<input name="orderNo" value="${sdkChannelConfigVo.orderNo}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${sdkChannelConfigVo.id}"/>
			</form>
		</div>
	</div> 
</div>