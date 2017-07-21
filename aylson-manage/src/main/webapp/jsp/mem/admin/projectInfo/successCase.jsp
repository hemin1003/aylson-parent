<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.td_sc_1{
/* width:180px; */
width:150px;
height:100px;
padding:2px;
margin:2px;
}
.td_sc_1 img{
width:150px;
}
</style>
<div align="center">
	<form id="projectInfoForm" method="post">
		<table class="tableForm">
			<tr>
				<th style="width:200px;">是否成功案例：</th>
				<td style="width:200px;" colspan="9">
					<select  class="easyui-combobox" id="isSuccessfulCase" name="isSuccessfulCase" style="width: 200px;"  data-options="editable:false,
					onSelect:function(record){
						if(record.value == 'true'){
							$('.isShow').show();
						}else if(record.value == 'false'){
						   $('.isShow').hide();
						}
					}"
					>
						<option value="true" <c:if test = "${projectInfoVo.isSuccessfulCase == true}">selected = "selected" </c:if>>是</option>
						<option value="false" <c:if test = "${projectInfoVo.isSuccessfulCase == false }">selected = "selected" </c:if>>否</option>
					</select>
					<font color=red>如果选【是】：需要上传【主题】【明细】图片</font>
				</td>
			</tr>
			<tr class="isShow">
				<th style="text-align:left;padding:5px;" colspan="10">
					<a href="javascript:void(0)" class="easyui-linkbutton c8" onclick="addImg(1)" >上传案例主题图片</a>
					<a href="javascript:void(0)" class="easyui-linkbutton c8" onclick="addImg(2)" >上传案例明细图片</a>
				</th>
			</tr>
			<tr class="isShow"><th style="text-align:left;padding:5px;font-size:14px;" colspan="10">案例【<font color=red>主题</font>】图片显示↓</th></tr>
			<tr class="isShow">
				<td colspan="10" >
					<c:if test="${not empty projectInfoVo.successCaseImg}" >
						<img  id="main_img" src="${projectInfoVo.successCaseImg}" style="width:300px;height:200px;" />
					</c:if>
					<input id="successCaseImg" name="successCaseImg" value="${projectInfoVo.successCaseImg}" type="hidden"/>
				</td>
			</tr>
			<tr class="isShow"><th style="text-align:left;padding:5px;font-size:14px;" colspan="10">案例【<font color=red>明细</font>】图片显示↓</th></tr>
			<tr class="isShow">
				<c:forEach items="${projectInfoVo.successCaseList}" var="successCase">
					<td class="td_sc_1">
					<img src="${successCase.attachAddress}"/><br />
					<input name="imgUrl" value="${successCase.attachAddress}" type="hidden"/>
					<a href="#" onclick="delImg(this)" class="easyui-linkbutton c8" style="width:120px;">删除</a>
					</td>
				</c:forEach>
				<td class="td_sc_2" colspan="2"></td>
			</tr>
			
		</table>
		<input name="id" type="hidden" value="${projectInfoVo.id}"/>
	</form>
</div>
<script>
//显示
$(function(){
	var isSuccessfulCase = '${projectInfoVo.isSuccessfulCase}';
	if(isSuccessfulCase == 'true'){
		$('.isShow').show();
	}else if(isSuccessfulCase == 'false'){
		$('.isShow').hide();
	}
})
//删除图片
function delImg(obj){
	$(obj).parent().remove();
}
//上传案例图片
function addImg(type){//type:1案例主题图片 2案例明细图片
	var win;
	win = $("<div></div>").dialog({
		title:'上传案例图片',
		width:500,
		height:200,
		modal:true,
		href:projectName+'/sys/fileHandle/toImgUpload',
		onClose:function(){
			$(this).dialog("destroy");
		},
		buttons:[{
			text:'确定',
		    iconCls:'icon-ok',
		    handler:function(){
 		    	var filePath = $("#imgName").filebox('getValue');
		    	$("#bucket").val("dc-publish");
		    	if(imgTypeCheck(filePath)){
			    	$("#imgUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/imgUpload',
			    		 success:function(responseData){
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		if(type == 1){
			    			 			$("#successCaseImg").val(data.data);
			    			 			$("#main_img").css({'width':'300px','height':'200px'}).attr({'src':data.data}).show();
			    			 		}
			    			 		if(type == 2){
			    			 			var param = "";
				    			 		param += "	<td class='td_sc_1'>";
				    			 		param += "		<img  src='"+data.data+"' />";
				    			 		param += "		<input name='imgUrl' value='"+data.data+"' type='hidden'/>";
				    			 		param += "		<a href='#'class='buttonCss' onclick='delImg(this)' class='easyui-linkbutton c8' style='width:120px'>删除</a>";
				    			 		param += "	</td>";
				    			 		$(".td_sc_2").before(param);
				    			 		//$(".textboxCss").textbox({required:true})
				    			 		$(".buttonCss").linkbutton();
				    			 		$(".buttonCss").addClass("c8");
			    			 		}
			    			 		win.dialog('destroy');
			    				}
			    			 } 
			    		 }
			    	});
		    	} 
		     }   
		   },{
			 text:'取消',
		     iconCls:'icon-cancel',  
		 	 handler:function(){
		 		 win.dialog('destroy');
		 	 }   
		  }]
	});
}
</script>