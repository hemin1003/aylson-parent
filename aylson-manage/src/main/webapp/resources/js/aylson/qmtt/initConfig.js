	/**
	 * 初始化配置
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/qmtt/initConfig/admin/list?v_date=' + new Date(),
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
					handleHtml += '<a href="javascript:query(\'' + row.id + '\')">查看</a>&nbsp;';
					handleHtml += '<a href="javascript:edit(\'' + row.id + '\')">修改</a>&nbsp;';
					return handleHtml;
				}
			}, {
				title : '常见问题页面url',
				field : 'faqUrl',
				align : 'center',
				width : 100,
				sortable:true
			}, {
				title : '邀请规则说明图',
				field : 'inviteUrl',
				align : 'center',
				width : 120,
				sortable:true,
				formatter:function(value,row,index){
					if(value){
						var handleHtml = '';
						handleHtml += '<img src=\'' + value + '\' style="width:70px;height:70px"/>';
						return handleHtml;
					}
				}
			}, {
				title : '注册用户协议页面url',
				field : 'registerInfoUrl',
				align : 'center',
				width : 125,
				sortable:true
			}, {
				title : '阅读文章控制时长',
				field : 'duration',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '阅读文章拖动次数',
				field : 'frequency',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : 'APP分享邀请动态链接Url',
				field : 'appInviteUrl',
				align : 'left',
				width : 150,
				sortable:true
			}, {
				title : 'h5下载显示Url',
				field : 'pageUrl',
				align : 'left',
				width : 150,
				sortable:true
			}, {
				title : 'apk下载跳转接Url',
				field : 'downloadUrl',
				align : 'left',
				width : 150,
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
			height:'70%',
			modal:true,
			href:projectName+'/qmtt/initConfig/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
				    	$("#initConfigConfigForm").form('submit',{
				    		 type:'POST',
				    		 url : projectName+'/qmtt/initConfig/admin/add',
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
			height:'85%',
			maximizable:true,
			modal:true,
			href:projectName+'/qmtt/initConfig/admin/toEdit?id='+id,
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
			height:'85%',
			maximizable:true,
			modal:true,
			href:projectName+'/qmtt/initConfig/admin/toEdit?id='+id,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
					    	$("#initConfigConfigForm").form('submit',{
					    		 type:'POST',
					    		 url : projectName+'/qmtt/initConfig/admin/update',
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
					url:projectName+'/qmtt/initConfig/admin/deleteById?id=' + id,
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
					url:projectName+'/qmtt/initConfig/admin/changeStatus?id=' + id+'&status='+status,
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
		$("#datagrid").datagrid("load", serializeObject($("#initConfigForm")));
	}
	
	//重置
	function reset(){
		$("#initConfigForm").form("reset");
	}
	