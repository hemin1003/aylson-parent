	/**
	 * 任务配置
	 */
	var datagrid;
	var editor;
	var finalStepUrl='';
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/cfdb/tasklist/admin/list?v_date=' + new Date(),
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'taskId',
			singleSelect:true,
			rownumbers: true,
			toolbar:[{
				text:"新增",
				iconCls : 'icon-add',
				handler : add
			},{
				text:"刷新",
				iconCls : 'icon-reload',
				handler : reload
			}],
 			frozenColumns : [[{ 
				field : 'opt',
				title : '操作选项',
				align : 'center',
				width : 120,
				formatter:function(value,row,index){
					var handleHtml = '';
					if(row.status == 2){
						handleHtml += '<a href="javascript:changeStatus(\'' + row.taskId + '\',1)">下线</a>&nbsp;';
						handleHtml += '<a href="javascript:detailQuery(\'' + row.taskId + '\')">查看详情</a>&nbsp;';
					}else{
						handleHtml += '<a href="javascript:changeStatus(\'' + row.taskId + '\',2)">上线</a>&nbsp;';
						handleHtml += '<a href="javascript:edit(\'' + row.taskId + '\')">修改</a>&nbsp;';
						handleHtml += '<a href="javascript:del(\'' + row.taskId + '\')">删除</a>&nbsp;';
						handleHtml += '<a href="javascript:detail(\'' + row.taskId + '\')">详情</a>&nbsp;';
					}
					
					return handleHtml;
				}
			}, {
				title : '广告状态',
				field : 'status',
				align : 'center',
				width : 60,
				sortable:true,
				formatter:function(value,row,index){
					if(value == 2){
						return "<font color=green>上线</font>";
					}else{
						return "<font color=red>下线</font>";
					}
				}
			}, {
				title : '广告任务名称',
				field : 'taskName',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '广告Logo',
				field : 'logoUrl',
				align : 'center',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					if(value){
						var handleHtml = '';
						handleHtml += '<img src=\'' + value + '\' style="width:50px;height:50px"/>';
						return handleHtml;
					}
				}
			}, 
			{
				title : '广告标签',
				field : 'taskTag',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '剩余数量',
				field : 'amount',
				align : 'center',
				width : 60,
				sortable:true
			}, {
				title : '平台金额',
				field : 'taskValue',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '用户收益金额',
				field : 'income',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '排序编号',
				field : 'orderNo',
				align : 'center',
				width : 60,
				sortable:true
			}, {
				title : '跳转url地址',
				field : 'goUrl',
				align : 'center',
				width : 70,
				sortable:true,
				formatter:function(value,row,index){
					return '<a href="javascript:window.open(\'' + value + '\',\'_blank\')">查看</a>&nbsp;';
				}
			}, {
				title : '创建时间',
				field : 'createDate',
				align : 'center',
				width : 120,
				sortable:true,
				formatter:function(value,row,index){
					if(value){
						return value.substring(0,19);
					}
					return value;
				}
			}
			, {
				title : '更新时间',
				field : 'updateDate',
				align : 'center',
				width : 120,
				formatter:function(value,row,index){
					if(value){
						return value.substring(0,19);
					}
					return value;
				}
			}
			] ]
		});
		
	});
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:480,
			height:'80%',
			modal:true,
			href:projectName+'/cfdb/tasklist/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
				    	$("#tasklistConfigForm").form('submit',{
				    		 type:'POST',
				    		 url : projectName+'/cfdb/tasklist/admin/add',
				    		 success:function(responseData){
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
										$("#datagrid").datagrid("reload");
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
	
	//修改
	function edit(taskId){
		win = $("<div></div>").dialog({
			title:'修改',
			width:480,
			height:'80%',
			maximizable:true,
			modal:true,
			href:projectName+'/cfdb/tasklist/admin/toEdit?taskId='+taskId,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
					    	$("#tasklistConfigForm").form('submit',{
					    		 type:'POST',
					    		 url : projectName+'/cfdb/tasklist/admin/update',
					    		 success:function(responseData){
					    			 win.dialog('destroy');
					    			 if(responseData){
					    				var data = $.parseJSON(responseData);
					    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
					    			 	if(data.success){
					    			 		$("#datagrid").datagrid("reload");
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

	//删除
	function del(taskId){
		$.messager.confirm("提示","确定删除此记录吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/cfdb/tasklist/admin/deleteById?taskId=' + taskId,
					dataType:"json",
					success:function(data){
						if(data){
		    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    				if(data.success){
		    					$("#datagrid").datagrid("reload");
		    				}
		    			 }
					}
				});
			}
		});
	}
	
	//详情配置
	function detail(taskId){
		win = $("<div></div>").dialog({
			title:'详情',
			width:830,
			height:'95%',
			maximizable:true,
			modal:true,
			href:projectName+'/cfdb/tasklist/admin/toDetail?taskId='+taskId,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    		$('#stepUrl').val(finalStepUrl);
				     	//处理富文本编辑的内容
					    	$("#taskDesc").val($('#summernote').summernote('code'));
					    	$("#fields").val($.trim(editor.getValue()));
					    	$("#isUpload").val($("input[name='isUploadRd']:checked").val());
					    	
					    	$("#taskDetailConfigForm").form('submit',{
					    		 type:'POST',
					    		 url : projectName+'/cfdb/tasklist/admin/updateDetail',
					    		 success:function(responseData){
					    			 win.dialog('destroy');
					    			 if(responseData){
					    				var data = $.parseJSON(responseData);
					    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
					    			 	if(data.success){
					    			 		$("#datagrid").datagrid("reload");
					    				}
					    			 } 
					    		 }
					    	 });
					    	 $('#summernote').summernote('destroy');
				     }   
				   },{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		 $('#summernote').summernote('destroy');
				 		 $('#summernote2').summernote('destroy');
				 		 win.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//详情查看
	function detailQuery(taskId){
		win = $("<div></div>").dialog({
			title:'仅查看（<font color=blue>如需修改，请先进行任务下线操作</font>）',
			width:800,
			height:'95%',
			maximizable:true,
			modal:true,
			href:projectName+'/cfdb/tasklist/admin/toDetail?taskId='+taskId,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		 $('#summernote').summernote('destroy');
				 		 $('#summernote2').summernote('destroy');
				 		 win.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//发布
	function changeStatus(taskId, status){
		var tip = "";
		if(status == 1){
			tip = "确定下线吗？";
			
		}else if(status == 2){
			tip = "确定上线吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/cfdb/tasklist/admin/changeStatus?taskId=' + taskId+'&status='+status,
					dataType:"json",
					success:function(data){
						if(data){
		    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    				if(data.success){
		    					$("#datagrid").datagrid("reload");
		    				}
		    			 }
					}
				});
			}
		});
	}
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load", serializeObject($("#tasklistForm")));
	}
	
	//重置
	function reset(){
		$("#tasklistForm").form("reset");
	}
	