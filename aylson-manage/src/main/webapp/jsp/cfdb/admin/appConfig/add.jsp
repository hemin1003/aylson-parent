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
    var simg = $('#appLearnUrl').val();;  
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
		 <div title="初始化信息配置" style="padding:10px;text-align:center">
		 	<form id="appConfigConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>常见问题页面url：</th>
						<td colspan="3" style="text-align:left">
							<input name="appFaqUrl" value="${appConfigVo.appFaqUrl}" data-options="required:true"
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'" 
								onclick="uploadImg('img','appLearnUrl','yfax-test')" id="uploadImg" style="margin-bottom:10px;">上传红包攻略图</a>
						</th>
						<td colspan="3" style="text-align:left">
							<div style="width:240px;height:120px">
								<c:if test="${not empty appConfigVo.appLearnUrl}"><img id="img" src="${appConfigVo.appLearnUrl}" style="width:120px;height:120px"/></c:if>
								<c:if test="${empty appConfigVo.appLearnUrl }"><img id="img" src="" style="width:120px;height:120px"/></c:if>
								<input id="appLearnUrl" name="appLearnUrl" value="${appConfigVo.appLearnUrl}" type="hidden"/>
							</div>
							<c:if test="${not empty appConfigVo.appLearnUrl}"><a href="javascript:preview()">查看大图</a></c:if>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${appConfigVo.id}"/>
			</form>
		</div>
	</div> 
</div>