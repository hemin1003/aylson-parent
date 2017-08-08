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
		 <div title="APK下载地址操作" style="padding:10px;text-align:center">
		 	<form id="apkUrlConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>应用名称：</th>
						<td colspan="3" style="text-align:left">
							<input name="appName" value="${apkUrlVo.appName}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>下载链接：</th>
						<td colspan="3" style="text-align:left">
							<input name="apkUrl" value="${apkUrlVo.apkUrl}"
								class="easyui-textbox" data-options="required:true"
								style="width:95%; text-align:left; "/>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${apkUrlVo.id}"/>
			</form>
		</div>
	</div> 
</div>