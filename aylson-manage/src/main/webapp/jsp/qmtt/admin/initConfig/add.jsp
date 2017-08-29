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
		 <div title="初始化配置" style="padding:10px;text-align:center">
		 	<form id="initConfigConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>常见问题页面url：</th>
						<td colspan="3" style="text-align:left">
							<input name="faqUrl" value="${initConfigVo.faqUrl}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>邀请规则说明页面url：</th>
						<td colspan="3" style="text-align:left">
							<input name="inviteUrl" value="${initConfigVo.inviteUrl}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>注册用户协议页面url：</th>
						<td colspan="3" style="text-align:left">
							<input name="registerInfoUrl" value="${initConfigVo.registerInfoUrl}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>阅读文章控制时长：</th>
						<td colspan="3" style="text-align:left">
							<input name="duration" value="${initConfigVo.duration}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>阅读文章拖动次数：</th>
						<td colspan="3" style="text-align:left">
							<input name="frequency" value="${initConfigVo.frequency}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${initConfigVo.id}"/>
			</form>
		</div>
	</div> 
</div>