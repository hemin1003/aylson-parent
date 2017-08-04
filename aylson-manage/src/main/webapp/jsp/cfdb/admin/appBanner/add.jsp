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
<script type="text/javascript">
function preview(){  
    var simg = $('#url').val();;  
    wins = $("<div align='center' style='text-align:center; background:#90A4AE'><img id='simg'/></div>").dialog({
		title:'大图预览',
		width:'95%',
		height:'95%',
		maximizable:true,
		modal:true,
		onClose:function(){
	    		$(this).dialog("destroy");
	    },
	});
    $("#simg").attr("src",simg);  
}
</script>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="广告Banner配置" style="padding:10px;text-align:center">
		 	<form id="appBannerConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>广告说明：</th>
						<td colspan="3" style="text-align:left">
							<input name="vdesc" value="${appBannerVo.vdesc}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'" 
								onclick="uploadImg('img','url','yfax-test')" id="uploadImg" style="margin-bottom:10px;">上传图片</a>
						</th>
						<td colspan="3" style="text-align:left">
							<div style="width:240px;height:120px">
								<c:if test="${not empty appBannerVo.url}"><img id="img" src="${appBannerVo.url}" style="width:120px;height:120px"/></c:if>
								<c:if test="${empty appBannerVo.url }"><img id="img" src="" style="width:120px;height:120px"/></c:if>
								<input id="url" name="url" value="${appBannerVo.url}" type="hidden"/>
							</div>
							<c:if test="${not empty appBannerVo.url}"><a href="javascript:preview()">查看大图</a></c:if>
						</td>
					</tr>
					<tr>
						<th>排序编号：</th>
						<td colspan="3" style="text-align:left">
							<input name="orderNo" value="${appBannerVo.orderNo}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${appBannerVo.id}"/>
			</form>
		</div>
	</div> 
</div>