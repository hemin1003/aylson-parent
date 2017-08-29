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
		 <div title="用户详情" style="padding:10px;text-align:center">
		 	<form id="appUserConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>手机号码：</th>
						<td colspan="3" style="text-align:left">
							<input value="${appUserVo.phoneNum}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>住址：</th>
						<td colspan="3" style="text-align:left">
							<input name="address" value="${appUserVo.address}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>微信号：</th>
						<td colspan="3" style="text-align:left">
							<input name="wechat" value="${appUserVo.wechat}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>QQ号：</th>
						<td colspan="3" style="text-align:left">
							<input name="qq" value="${appUserVo.qq}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>邮箱地址：</th>
						<td colspan="3" style="text-align:left">
							<input name="email" value="${appUserVo.email}"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
				</table>
				<input name="phoneNum" type="hidden" value="${appUserVo.phoneNum}"/>
			</form>
		</div>
	</div> 
</div>