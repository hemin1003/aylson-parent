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
<script type="text/javascript">
function createEditor(){
	$('#summernote').summernote({
		height: 160,
		focus: true,
		lang: 'zh-CN'   	    		  
	});
}
$("#tabActivity").tabs({
	onSelect:function(title,index){
		if(index == 0){
			createEditor();
		}
	}
})
</script>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="版本升级配置" style="padding:10px;text-align:center">
		 	<form id="appUpgradeConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>版本号：</th>
						<td colspan="3" style="text-align:left">
							<input name="version" value="${appUpgradeVo.version}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>版本描述：</th>
						<td colspan="3" style="text-align:left">
							<div id="summernote" style="width:85%">
								${appUpgradeVo.vdesc}
							</div>
						</td>
					</tr>
					<tr>
						<th>安装包下载地址：</th>
						<td colspan="3" style="text-align:left">
							<input name="apkUrl" value="${appUpgradeVo.apkUrl}" 
								class="easyui-textbox" data-options="multiline:true, required:true"
								style="width:95%; height:80px; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>广告渠道组合：</th>
						<td colspan="3" style="text-align:left">
							<div id="editor" style="width:90%;height:220px;">
								${appUpgradeVo.fields}
							</div>
						</td>
					</tr>
					<tr>
						<th>广告平台标识：</th>
						<td colspan="3" style="text-align:left">
							<input name="platform" value="${appUpgradeVo.platform}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
				</table>
				<input name="vdesc" id="vdesc" type="hidden" value=""/>
				<input name="id" type="hidden" value="${appUpgradeVo.id}"/>
				<input name="fields" id="fields" type="hidden" value=""/>
			</form>
		</div>
	</div> 
</div>
<script>
var editor = ace.edit("editor");
editor.setTheme("ace/theme/monokai");
editor.getSession().setMode("ace/mode/javascript");
</script>