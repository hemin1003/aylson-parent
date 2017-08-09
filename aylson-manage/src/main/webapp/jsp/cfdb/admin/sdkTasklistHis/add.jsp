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
		 <div title="查看详情" style="padding:10px;text-align:center">
		 	<form id="sdkTasklistHisConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>唯一标识ID：</th>
						<td colspan="3" style="text-align:left">
							<input name="hashid" value="${sdkTasklistHisVo.hashid}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>开发者应用ID：</th>
						<td colspan="3" style="text-align:left">
							<input name="appid" value="${sdkTasklistHisVo.appid}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>广告ID：</th>
						<td colspan="3" style="text-align:left">
							<input name="adid" value="${sdkTasklistHisVo.adid}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>广告名称：</th>
						<td colspan="3" style="text-align:left">
							<input name="adname" value="${sdkTasklistHisVo.adname}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>开发者设置的用户ID：</th>
						<td colspan="3" style="text-align:left">
							<input name="userid" value="${sdkTasklistHisVo.userid}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>mac地址：</th>
						<td colspan="3" style="text-align:left">
							<input name="mac" value="${sdkTasklistHisVo.mac}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>手机设备标识码(IMEI)：</th>
						<td colspan="3" style="text-align:left">
							<input name="deviceid" value="${sdkTasklistHisVo.deviceid}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>渠道来源：</th>
						<td colspan="3" style="text-align:left">
							<input name="source" value="${sdkTasklistHisVo.source}"
								class="easyui-textbox"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>奖励积分：</th>
						<td colspan="3" style="text-align:left">
							<input name="point" value="${sdkTasklistHisVo.point}"
								class="easyui-textbox"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>奖励金额：</th>
						<td colspan="3" style="text-align:left">
							<input name="price" value="${sdkTasklistHisVo.price}"
								class="easyui-textbox"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>平台回调时间：</th>
						<td colspan="3" style="text-align:left">
							<input name="timeStr" value="${sdkTasklistHisVo.timeStr}"
								class="easyui-textbox"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>签到标识：</th>
						<td colspan="3" style="text-align:left">
							<input name="activeNum" value="${sdkTasklistHisVo.activeNum}"
								class="easyui-textbox"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>平台签名值：</th>
						<td colspan="3" style="text-align:left">
							<input name="checksum" value="${sdkTasklistHisVo.checksum}"
								class="easyui-textbox"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>后台签名值：</th>
						<td colspan="3" style="text-align:left">
							<input name="resultSum" value="${sdkTasklistHisVo.resultSum}"
								class="easyui-textbox"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					<tr>
						<th>后台验证结果：</th>
						<td colspan="3" style="text-align:left">
							<input name="result" value="${sdkTasklistHisVo.result}"
								class="easyui-textbox"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
					
				</table>
			</form>
		</div>
	</div> 
</div>