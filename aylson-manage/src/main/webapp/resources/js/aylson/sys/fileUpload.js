/**
 * fileUpload.js
 */
//文件上传
	function uploadFile(id,target,bucket){//id:获取上传文件的元素,target:目标id
		if(bucket == null){
		 	$.messager.show({"title":"系统提示","msg":"找不到对应的资源空间，请联系管理员","timeout":1000});
		 	return;
		}
		var win;
		win = $("<div></div>").dialog({
			title:'上传文件',
			width:500,
			height:200,
			modal:true,
			href:projectName+'/sys/fileHandle/toFileUpload',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	var winTip = $.messager.progress({
						title:'请稍候...',
						msg:'正在上传...'
					});
			    	var filePath = $("#fileName").filebox('getValue');
			    	$("#bucket").val(bucket);
			    	$("#fileUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/fileUpload',
			    		 success:function(responseData){
			    			 $.messager.progress('close');
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		var fileName = data.data;
			    			 		var showFileName = fileName.split("@|@")[1];
			    			    	$("#"+id).append("<li><a name='fileItem' target='_blank' href='"+ fileName +"' alt='"+showFileName+"'>"+showFileName+"</a>&nbsp;<a href='#' onclick='delElement(this,\""+target+"\")'>删除</a></li>");
			    			 		$("#"+target).val($("#"+target).val()+","+fileName);//
			    			 		win.dialog('destroy');
			    				}
			    			 } 
			    		 }
			    	});
			    	}
//			     }   
			   },{
				 text:'取消',
			     iconCls:'icon-cancel',  
			 	 handler:function(){
			 		 win.dialog('destroy');
			 	 }   
			  }]
		});
	}
	
	function uploadImg(id,target,bucket){//id:获取上传图片地址的元素,target:目标id
		var win;
		win = $("<div></div>").dialog({
			title:'上传图片',
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
			    	var winTip = $.messager.progress({
						title:'请稍候...',
						msg:'正在上传...'
					});
			    	$("#bucket").val(bucket);
			    	var filePath = $("#imgName").filebox('getValue');
			    	if(imgTypeCheck(filePath)){
				    	$("#imgUploadForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/sys/fileHandle/imgUpload',
				    		 success:function(responseData){
				    			 $.messager.progress('close');
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
				    			 		$("#"+id).attr({ src: data.data, title: data.data }).show();//系统使用缩略图
				    			 		$("#"+target).val(data.data);//目标字段保存原图
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
	
//图片类型上传校验   
	function imgTypeCheck(filePath){
		  if(filePath == ""){
			  	$.messager.show({"title":"系统提示","msg":"请选择图片上传！","timeout":1000});
	         	return false;
	      }else{
	         if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filePath))
	         {
	           $.messager.show({"title":"系统提示","msg":"图片类型必须是.gif,jpeg,jpg,png中的一种","timeout":1000});
	           return false;
	         }
	      }
		  return true;
	}
	function delElement(obj,target){
		$(obj).parent().remove();
		var attachments = "";
		$.each($("[name ='fileItem']"),function(n,value) {   
	           attachments += value.href+",";
		}); 
//		alert(attachments);
		$("#"+target).val(attachments);
	}