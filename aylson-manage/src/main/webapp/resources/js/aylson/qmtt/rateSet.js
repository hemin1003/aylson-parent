	/**
	 * 金币汇率配置
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/qmtt/rateSet/admin/list?v_date=' + new Date(),
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'id',
			singleSelect:true,
			rownumbers: true,
			toolbar:[{
				text:"刷新",
				iconCls : 'icon-reload',
				handler : reload
			}],
 			frozenColumns : [[{ 
				field : 'opt',
				title : '操作选项',
				align : 'center',
				width : 100,
	 			formatter:function(value,row,index){
					var handleHtml = '';
					if(row.status == 2){
						handleHtml += '<a href="javascript:changeStatus(\'' + row.id + '\',1)">下线</a>&nbsp;';
						handleHtml += '<a href="javascript:query(\'' + row.id + '\')">查看</a>&nbsp;';
					}else{
						handleHtml += '<a href="javascript:changeStatus(\'' + row.id + '\',2)">上线</a>&nbsp;';
						handleHtml += '<a href="javascript:edit(\'' + row.id + '\')">修改</a>&nbsp;';
					}
					
					return handleHtml;
				}
			}, {
				title : '记录状态',
				field : 'status',
				align : 'center',
				width : 60,
				sortable:true,
				formatter:function(value,row,index){
					if(value == 2){
						return "<font color=green>上线</font>";
					}else if(value == 1){
						return "<font color=red>下线</font>";
					}
					return '';
				}
			}, {
				title : '金币汇率值',
				field : 'rate',
				align : 'center',
				width : 100,
				sortable:true
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
			width:450,
			height:'50%',
			modal:true,
			href:projectName+'/qmtt/rateSet/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
				    	$("#rateSetConfigForm").form('submit',{
				    		 type:'POST',
				    		 url : projectName+'/qmtt/rateSet/admin/add',
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
	
	//查看
	function query(id){
		win = $("<div></div>").dialog({
			title:'查看',
			width:450,
			height:'50%',
			maximizable:true,
			modal:true,
			href:projectName+'/qmtt/rateSet/admin/toEdit?id='+id,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		 win.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//修改
	function edit(id){
		win = $("<div></div>").dialog({
			title:'修改',
			width:450,
			height:'50%',
			maximizable:true,
			modal:true,
			href:projectName+'/qmtt/rateSet/admin/toEdit?id='+id,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
					    	$("#rateSetConfigForm").form('submit',{
					    		 type:'POST',
					    		 url : projectName+'/qmtt/rateSet/admin/update',
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
	function del(id){
		$.messager.confirm("提示","确定删除此记录吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/qmtt/rateSet/admin/deleteById?id=' + id,
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
	
	//发布
	function changeStatus(id, status){
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
					url:projectName+'/qmtt/rateSet/admin/changeStatus?id=' + id+'&status='+status,
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
		$("#datagrid").datagrid("load", serializeObject($("#rateSetForm")));
	}
	
	//重置
	function reset(){
		$("#rateSetForm").form("reset");
	}
	