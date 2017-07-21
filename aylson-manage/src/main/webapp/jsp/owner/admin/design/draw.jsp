<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="designForm" method="post">
		<c:if test="${ !empty designVo.drawOpition}">
			<table class="tableForm" style="width:75%;font-size:16px;">
			<tr>
				<th width="100px">不满意原因：</th>
				<td style="color:#ff0000;font-size:14px;">${designVo.drawOpition}</td>
			</tr>
			</table>
		</c:if>
		<input type="hidden" id="drawUrl"  name="drawUrl" value="${designVo.drawUrl}" />
		<!-- <input type="hidden" id="drawUrl"  name="drawUrl" value="1" /> -->
		<img id="imgDrawUrl"  src="${designVo.drawUrl}" />
		<input name="id" type="hidden" value="${designVo.id}"/>
		<input name="state" type="hidden" value="${designVo.state}"/>
	</form>
</div>
<script>
   function uploadDrawUrl(){
	   var win;
		win = $("<div></div>").dialog({
			title:'上传大样图',
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
			    	if(filePath == ''){
			    		$.messager.show({"title":"系统提示","msg":"请选择图片上传","timeout":1000});
			    		return;
			    	}
			    	var winTip = $.messager.progress({
						title:'请稍候...',
						msg:'正在上传...'
					});
			    	$("#bucket").val("dc-test");
			    	$("#imgUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/imgUpload',
			    		 //url:projectName+'/sys/fileHandle/uploadTest',
			    		 success:function(responseData){
			    			 $.messager.progress('close');
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		var fileName = data.data;
			    			 		$("#drawUrl").val(fileName);
			    			 		$("#imgDrawUrl").attr("src",fileName);
			    			 		win.dialog('destroy');
			    				}
			    			 } 
			    		 }
			    	});
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