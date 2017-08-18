	/**
	 * APP初始化信息配置
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/cfdb/sdkChannelConfig/admin/list?v_date=' + new Date(),
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
				width : 100,
				formatter:function(value,row,index){
					var handleHtml = '';
					handleHtml += '<a href="javascript:edit(\'' + row.id + '\')">修改</a>&nbsp;';
					handleHtml += '<a href="javascript:del(\'' + row.id + '\')">删除</a>&nbsp;';
					return handleHtml;
				}
			}, {
				title : 'SDK广告渠道标识',
				field : 'channelFlag',
				align : 'center',
				width : 130
			}, {
				title : 'SDK广告渠道名称',
				field : 'channelName',
				align : 'center',
				width : 130
			}, {
				title : '换算比例',
				field : 'cRate',
				align : 'center',
				width : 60
			}, {
				title : '分成比例',
				field : 'sRate',
				align : 'center',
				width : 60
			}, {
				title : '优先级',
				field : 'orderNo',
				align : 'center',
				width : 50
			}, {
				title : '平台回调秘钥',
				field : 'sdkPwd',
				align : 'center',
				width : 150
			}, {
				title : '是否启用',
				field : 'isEnabled',
				align : 'center',
				width : 60,
				sortable:true,
				formatter:function(value,row,index){
					if(value == 2){
						return "<font color=green>启用</font>";
					}else if(value == 1){
						return "<font color=red>禁用</font>";
					}
					return '';
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
			}, {
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
			height:'60%',
			modal:true,
			href:projectName+'/cfdb/sdkChannelConfig/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
				    	$("#sdkChannelConfigForm").form('submit',{
				    		 type:'POST',
				    		 url : projectName+'/cfdb/sdkChannelConfig/admin/add',
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
	function edit(id){
		win = $("<div></div>").dialog({
			title:'修改',
			width:450,
			height:'60%',
			maximizable:true,
			modal:true,
			href:projectName+'/cfdb/sdkChannelConfig/admin/toEdit?id='+id,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
					    	$("#sdkChannelConfigForm").form('submit',{
					    		 type:'POST',
					    		 url : projectName+'/cfdb/sdkChannelConfig/admin/update',
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
					url:projectName+'/cfdb/sdkChannelConfig/admin/deleteById?id=' + id,
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
		$("#datagrid").datagrid("load", serializeObject($("#sdkChannelConfigForm")));
	}
	
	//重置
	function reset(){
		$("#sdkChannelConfigForm").form("reset");
	}
	