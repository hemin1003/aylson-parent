<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
</head>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.css" rel="stylesheet">

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
		 <div title="全局信息配置" style="padding:10px;text-align:center">
		 	<form id="appConfigConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>每日金币领取上限：</th>
						<td colspan="3" style="text-align:left">
							<input name="goldLimit" value="${appConfigVo.goldLimit}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>自动转换金币阈值：</th>
						<td colspan="3" style="text-align:left">
							<input name="goldAuto" value="${appConfigVo.goldAuto}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>阅读金币随机范围：</th>
						<td colspan="3" style="text-align:left">
							<input name="goldRange" value="${appConfigVo.goldRange}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>首次有效阅读奖励金币：</th>
						<td colspan="3" style="text-align:left">
							<input name="firstReadGold" value="${appConfigVo.firstReadGold}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>首次分享奖励金币：</th>
						<td colspan="3" style="text-align:left">
							<input name="firstShareGold" value="${appConfigVo.firstShareGold}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>首次邀请奖励金币：</th>
						<td colspan="3" style="text-align:left">
							<input name="firstInviteGold" value="${appConfigVo.firstInviteGold}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>安卓分享链接Url：</th>
						<td colspan="3" style="text-align:left">
							<input name="androidUrl" value="${appConfigVo.androidUrl}" 
								class="easyui-textbox" data-options="multiline:true"
								style="width:95%; height:80px; text-align:left"/>
						</td>
					</tr><tr>
						<th>苹果分享链接Url：</th>
						<td colspan="3" style="text-align:left">
							<input name="iphoneUrl" value="${appConfigVo.iphoneUrl}" 
								class="easyui-textbox" data-options="multiline:true"
								style="width:95%; height:80px; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>注册金币随机范围：</th>
						<td colspan="3" style="text-align:left">
							<input name="regGoldRange" value="${appConfigVo.regGoldRange}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>登录使用奖励间隔时长：</th>
						<td colspan="3" style="text-align:left">
							<input name="loginDuration" value="${appConfigVo.loginDuration}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/> (单位:小时)
						</td>
					</tr>
					<tr>
						<th>登录使用奖励金币固定值：</th>
						<td colspan="3" style="text-align:left">
							<input name="loginGold" value="${appConfigVo.loginGold}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>连续签到奖励金币随机范围：</th>
						<td colspan="3" style="text-align:left">
							<input name="checkInGold" value="${appConfigVo.checkInGold}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>连续签到金币和阀值配置：</th>
						<td colspan="3" style="text-align:left">
							<div id="editor" style="width:90%;height:300px;">
								${appConfigVo.checkInConfig}
							</div>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${appConfigVo.id}"/>
				<input name="checkInConfig" id="checkInConfig" type="hidden" value=""/>
			</form>
		</div>
	</div> 
</div>
<script>
var editor = ace.edit("editor");
editor.setTheme("ace/theme/monokai");
editor.getSession().setMode("ace/mode/javascript");
</script>