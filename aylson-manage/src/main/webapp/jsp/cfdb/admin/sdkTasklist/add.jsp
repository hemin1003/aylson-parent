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
		 <div title="广告详情信息" style="padding:10px;text-align:center">
		 	<form id="sdkTasklistConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>广告渠道来源：</th>
						<td colspan="3" style="text-align:left">
							<input name="sdkType" value="${sdkTasklistVo.sdkType}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>广告ID：</th>
						<td colspan="3" style="text-align:left">
							<input name="adid" value="${sdkTasklistVo.adid}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>广告主ID：</th>
						<td colspan="3" style="text-align:left">
							<input name="cid" value="${sdkTasklistVo.cid}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>广告名称：</th>
						<td colspan="3" style="text-align:left">
							<input name="title" value="${sdkTasklistVo.title}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>应用简介：</th>
						<td colspan="3" style="text-align:left">
							<input name="intro" value="${sdkTasklistVo.intro}" 
								class="easyui-textbox" data-options="multiline:true"
								style="width:95%; height:80px; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>广告APK下载地址：</th>
						<td colspan="3" style="text-align:left">
							<input name="url" value="${sdkTasklistVo.url}" 
								class="easyui-textbox" data-options="multiline:true"
								style="width:95%; height:60px; text-align:left"/>
						</td>
					</tr>
					<!-- 
					<tr>
						<th>任务步骤描述：</th>
						<td colspan="3" style="text-align:left">
							<input name="android_url" value="${sdkTasklistVo.android_url}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					 -->
					<tr>
						<th>广告激活时间：</th>
						<td colspan="3" style="text-align:left">
							<input name="active_time" value="${sdkTasklistVo.active_time}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>当前步骤激活条件：</th>
						<td colspan="3" style="text-align:left">
							<input name="curr_note" value="${sdkTasklistVo.curr_note}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>简介下载量：</th>
						<td colspan="3" style="text-align:left">
							<input name="text1" value="${sdkTasklistVo.text1}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>激活次数：</th>
						<td colspan="3" style="text-align:left">
							<input name="active_num" value="${sdkTasklistVo.active_num}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>任务总积分：</th>
						<td colspan="3" style="text-align:left">
							<input name="score" value="${sdkTasklistVo.score}" 
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div> 
</div>