<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/plugins/code/prettify.css" />
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/lang/zh_CN.js"></script>
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
	width:150px;
	height:100px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:150px;
}
.basic_td{
text-align:left!important;
}
</style>
<script type="text/javascript">
function createEditor(){
	 window.editor = KindEditor.create('#editor_id',{
        resizeType:1,      
        urlType:projectName, // 带有域名的绝对路径
        allowFileManager : false,
        allowImageUpload : true,
        uploadJson : projectName+'/sys/fileHandle/kindeditorUpload?bucket=dc-gift',
		 items : [
				'source', '|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link','fullscreen'], 
				
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
		<div title="优惠券配置" style="padding:10px">
			<form id="couponConfigForm" method="post">
				<table class="tableForm" style="width:75%;">
					<tr>
						<th>优惠券名称：</th>
						<td>
							<input name="couponName" class="easyui-textbox" value="${couponDetailVo.couponName}"  
								data-options="required:true,prompt:'例如：加油现金券...'" style="width:200px;">
						</td>
					</tr>
					<tr>
						<th>优惠券类型：</th>
						<td style="text-align:left">
							<select name="couponType" class="easyui-combobox" value="${couponDetailVo.couponType}" 
								data-options="required:true" style="width: 200px;" editable=false>
									<option value="1">现金券</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>券面值(元)：</th>
						<td>
							<input name="couponValue" class="easyui-numberbox" value="${couponDetailVo.couponValue}" 
								data-options="required:true" style="width:200px;">
						</td>
					</tr>
					<tr>
						<th>兑换规则说明：</th>
						<td>
							<input name="ruleDesc" class="easyui-textbox" value="${couponDetailVo.ruleDesc}" 
								data-options="prompt:'例如：2000以下减50，满2000减100，满4000减200...'"
								style="width: 650px;" />
						</td>
					</tr>
					<tr>
						<th>兑换规则设定：</th>
						<td>
							<c:forEach items="${list}" var="rule" varStatus="vs">
								<input name="ruleId" type="hidden" value="${rule.id}"/>
							 	规则${vs.index+1}.&nbsp;<input name="startPrice" value="${rule.startPrice}" class="easyui-textbox"  data-options="prompt:'开始金额...'" style="width:100px;">~<input name="endPrice" value="${rule.endPrice}" class="easyui-textbox"  data-options="prompt:'结束金额...'" style="width:100px;">
									  &nbsp;减免&nbsp;<input name="deratePrice" value="${rule.deratePrice}" class="easyui-textbox"  data-options="prompt:'减免金额...'" style="width:100px;">元
									  <br/>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th>有效开始时间：</th>
						<td>
							<input name="startTimeStr" value="${couponDetailVo.startTimeStr}" class="easyui-datetimebox" data-options="required:true"
								style="width: 200px;" />
						</td>
					</tr>
					<tr>
						<th>有效终止时间：</th>
						<td>
							<input name="endTimeStr" value="${couponDetailVo.endTimeStr}" class="easyui-datetimebox" data-options="required:true"
								style="width: 200px;" />
						</td>
					</tr>
					<tr>
						<th>兑奖须知：</th>
						<td>
							<input name="notice" value="${couponDetailVo.notice}" class="easyui-textbox" 
								data-options="multiline:true" style="width:650px;height:100px" />
						</td>
					</tr>
					<tr>
						<th>客服电话：</th>
						<td>
							<input name="serviceTel" value="${couponDetailVo.serviceTel}"
								class="easyui-textbox" style="width: 200px;" />
						</td>
					</tr>
					<tr>
						<th>备注说明：</th>
						<td>
							<input name="comments" value="${couponDetailVo.comments}" 
								class="easyui-textbox" style="width: 650px;" />
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${couponDetailVo.id}"/>
			</form>
		</div>
	</div> 
</div>