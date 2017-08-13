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
		 <div title="版本信息配置" style="padding:10px;text-align:center">
		 	<form id="wdkzAppConfigConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>版本号：</th>
						<td colspan="3" style="text-align:left">
							<input name="version" value="${wdkzAppConfigVo.version}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>版本说明：</th>
						<td colspan="3" style="text-align:left">
							<input name="versionDesc" value="${wdkzAppConfigVo.versionDesc}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>是否启用：</th>
						<td colspan="3" style="text-align:left">
							<select id="isEnabledFlag" name="isEnabledFlag" class="easyui-combobox" 
								data-options=" 
								onSelect:function(data){
									$('#isEnabled').val(data.value);
								}" 
								style="width:95%; text-align:left" editable=false>
								<option value="1">禁用</option>
								<option value="2">启用</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>开屏广告：</th>
						<td colspan="3" style="text-align:left">
							<select id="displayFlagSelect" name="displayFlagSelect" class="easyui-combobox" 
								data-options=" 
								onSelect:function(data){
									$('#displayFlag').val(data.value);
								}"
								style="width:95%; text-align:left" editable=false>
								<option value="0">自己广告</option>
								<option value="101">百度</option>
								<option value="102">广点通</option>
								<option value="103">有米</option>
								<option value="104">推啊</option>
								<option value="105">yeahmobi</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>横幅广告：</th>
						<td colspan="3" style="text-align:left">
							<select id="bannerFlagSelect" name="bannerFlagSelect" class="easyui-combobox" 
								data-options=" 
								onSelect:function(data){
									$('#bannerFlag').val(data.value);
								}"
								style="width:95%; text-align:left" editable=false>
								<option value="0">自己广告</option>
								<option value="101">百度</option>
								<option value="102">广点通</option>
								<option value="103">有米</option>
								<option value="104">推啊</option>
								<option value="105">yeahmobi</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>插频广告：</th>
						<td colspan="3" style="text-align:left">
							<select id="videoFlagSelect" name="videoFlagSelect" class="easyui-combobox" 
								data-options=" 
								onSelect:function(data){
									$('#videoFlag').val(data.value);
								}"
								style="width:95%; text-align:left" editable=false>
								<option value="0">自己广告</option>
								<option value="101">百度</option>
								<option value="102">广点通</option>
								<option value="103">有米</option>
								<option value="104">推啊</option>
								<option value="105">yeahmobi</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>抽奖广告：</th>
						<td colspan="3" style="text-align:left">
							<select id="awardFlagSelect" name="awardFlagSelect" class="easyui-combobox" 
								data-options=" 
								onSelect:function(data){
									$('#awardFlag').val(data.value);
								}"
								style="width:95%; text-align:left" editable=false>
								<option value="0">自己广告</option>
								<option value="101">百度</option>
								<option value="102">广点通</option>
								<option value="103">有米</option>
								<option value="104">推啊</option>
								<option value="105">yeahmobi</option>
								
							</select>
						</td>
					</tr>
					<tr>
						<th>无感广告：</th>
						<td colspan="3" style="text-align:left">
							<select id="nosenseFlagSelect" name="nosenseFlagSelect" class="easyui-combobox" 
								data-options=" 
								onSelect:function(data){
									$('#nosenseFlag').val(data.value);
								}" 
								style="width:95%; text-align:left" editable=false>
								<option value="0">自己广告</option>
								<option value="101">百度</option>
								<option value="102">广点通</option>
								<option value="103">有米</option>
								<option value="104">推啊</option>
								<option value="105">yeahmobi</option>
							</select>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${wdkzAppConfigVo.id}"/>
				<input name="isEnabled" id="isEnabled" type="hidden" value="${wdkzAppConfigVo.isEnabled}"/>
				<input name="displayFlag" id="displayFlag" type="hidden" value="${wdkzAppConfigVo.displayFlag}"/>
				<input name="bannerFlag" id="bannerFlag" type="hidden" value="${wdkzAppConfigVo.bannerFlag}"/>
				<input name="videoFlag" id="videoFlag" type="hidden" value="${wdkzAppConfigVo.videoFlag}"/>
				<input name="awardFlag" id="awardFlag" type="hidden" value="${wdkzAppConfigVo.awardFlag}"/>
				<input name="nosenseFlag" id="nosenseFlag" type="hidden" value="${wdkzAppConfigVo.nosenseFlag}"/>
			</form>
		</div>
	</div> 
</div>
<script type="text/javascript">
$(function(){
	var select = document.getElementById("isEnabledFlag");  
	var value = $('#isEnabled').val();  
	for(var i=0; i<select.options.length; i++){  
		//alert('value='+select.options[i].value+', value='+value);
	    if(select.options[i].value == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}
});

$(function(){
	var select = document.getElementById("displayFlagSelect");  
	var value = $('#displayFlag').val();  
	for(var i=0; i<select.options.length; i++){  
		//alert('value='+select.options[i].value+', value='+value);
	    if(select.options[i].value == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}
});

$(function(){
	var select = document.getElementById("bannerFlagSelect");  
	var value = $('#bannerFlag').val();  
	for(var i=0; i<select.options.length; i++){  
		//alert('value='+select.options[i].value+', value='+value);
	    if(select.options[i].value == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}
});

$(function(){
	var select = document.getElementById("videoFlagSelect");  
	var value = $('#videoFlag').val();  
	for(var i=0; i<select.options.length; i++){  
		//alert('value='+select.options[i].value+', value='+value);
	    if(select.options[i].value == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}
});

$(function(){
	var select = document.getElementById("awardFlagSelect");  
	var value = $('#awardFlag').val();  
	for(var i=0; i<select.options.length; i++){  
		//alert('value='+select.options[i].value+', value='+value);
	    if(select.options[i].value == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}
});

$(function(){
	var select = document.getElementById("nosenseFlagSelect");  
	var value = $('#nosenseFlag').val();  
	for(var i=0; i<select.options.length; i++){  
		//alert('value='+select.options[i].value+', value='+value);
	    if(select.options[i].value == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}
});
</script>