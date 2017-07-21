<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="partnerAccountForm" method="post">
		<table class="tableForm">
			<tr>
				<th>合伙人姓名：</th>
				<td ><input   value="${partnerAccountVo.partnerName}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
				<th>手机号：</th>
				<td ><input   value="${partnerAccountVo.mobilePhone}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>未转分红：</th>
				<td ><input id="wallet"   value="${partnerAccountVo.wallet}"
					class="easyui-numberbox" data-options="readonly:true,min:0,precision:2"
					style="width: 200px;" /></td>
				<th>已转分红：</th>
				<td ><input   value="${partnerAccountVo.walletHad}"
					class="easyui-numberbox" data-options="readonly:true,min:0,precision:2"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>身份证号码：</th>
				<td ><input   value="${partnerAccountVo.cardID}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
				<th>开户银行：</th>
				<td ><input   value="${partnerAccountVo.bankName}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>银行卡号：</th>
				<td ><input   value="${partnerAccountVo.bankNo}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
				<th>开户名：</th>
				<td ><input   value="${partnerAccountVo.bankAccountName}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>转账金额：</th>
				<td colspan="1"><input id="transferValue" name="wallet" value=""
					class="easyui-numberbox" data-options="required:true,min:0,precision:2"
					style="width: 200px;" /></td>
				<td colspan="2">
					<a href="#" onClick="uploadTransfer(this)" class="easyui-linkbutton c8" style="width:100px">上传附件</a>
					<a href="#" onClick="viewAttachUrl()" class="easyui-linkbutton c8" style="width:100px">查看附件</a>
				</td>
			</tr>
		</table>
			<div id="attachUrlView">
			
			</div>
		<input name="accountId" type="hidden" value="${partnerAccountVo.id}"/>
		<input name="attachUrl" id="attachUrl" type="hidden" value=""/>
	</form>
	</div>
<script>
function uploadTransfer(obj){
	var win;
	win = $("<div></div>").dialog({
		title:'上传附件',
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
		    			 		//上传成功
		    			 		var fileName = data.data;//返回的图片地址
		    			 		$("#attachUrl").val(fileName);
		    			 		var html = "<img src='"+fileName+"' width='600px' height='400px'/>";
		    			 		$("#attachUrlView").html(html)
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

//查看转账附件
function viewAttachUrl(attachUrl){
	$('#walletDatailDlg').dialog({
		buttons:[{
			text:'确定并保存',
			iconCls:'icon-ok',
			handler:function(){
				var wallet = $("#wallet").val();//未转分红
		    	var transferValue = $("#transferValue").val(); //转账金额
		    	var attachUrls= $("#attachUrl").val();
		    	if(parseFloat(transferValue) <= 0){
		    		$.messager.show({"title":"系统提示","msg":"转账金额不能小于0","timeout":1000});
		    		return;
		    	}
		    	if(parseFloat(wallet) < parseFloat(transferValue)){
		    		$.messager.show({"title":"系统提示","msg":"转账金额超过未转分红的金额","timeout":1000});
		    		return;
		    	}
		    	if(attachUrls == ""){
		    		$.messager.show({"title":"系统提示","msg":"转账凭证附件不能为空","timeout":1000});
		    		return;
		    	}
		    	$.messager.confirm("提示", "确认该转账记录吗？", function(r) {
					if (r) {
						$("#partnerAccountForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName + curUrl + '/admin/addBonus',
				    		 success:function(responseData){
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
				    			 		$("#bonusDatagrid").datagrid("reload");
										//win.dialog('destroy');
										location.href = projectName + curUrl + '/admin/toBonusIndex';
				    				}
				    			 } 
				    		 }
				    	 });
					}else{
						$("#bonusDatagrid").datagrid("reload");
						//win.dialog('destroy');
						location.href = projectName + curUrl + '/admin/toBonusIndex';
						return;
					}
				})
				$('#walletDatailDlg').dialog('close');
			}
		},{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				$('#walletDatailDlg').dialog('close');
			}
		}]
	});
	$('#walletDatailDlg').dialog('open');
	if(attachUrl == undefined){ 
		attachUrl = $("#attachUrl").val();
	}
	var html = "";
	if(attachUrl != ''){
		html += "<img src='"+attachUrl+"' width='100%' height='100%'/>";
	}
	$('#walletDatailUrl').html(html);
}
	
</script>