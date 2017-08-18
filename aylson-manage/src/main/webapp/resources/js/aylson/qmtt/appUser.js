	/**
	 * 用户信息管理
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/qmtt/appUser/admin/list?v_date=' + new Date(),
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'phoneNum',
			singleSelect:true,
			rownumbers: true,
			toolbar:[{
				text:"刷新",
				iconCls : 'icon-reload',
				handler : reload
			}],
 			frozenColumns : [[{
				title : '手机号码',
				field : 'phoneNum',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '金币余额',
				field : 'gold',
				align : 'center',
				width : 60,
				sortable:true
			}, {
				title : '零钱余额',
				field : 'balance',
				align : 'center',
				width : 60,
				sortable:true
			}, {
				title : '姓名',
				field : 'userName',
				align : 'center',
				width : 60,
				sortable:true
			}, {
				title : '住址',
				field : 'address',
				align : 'center',
				width : 150,
				sortable:true
			}, {
				title : '微信号',
				field : 'wechat',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : 'QQ号',
				field : 'qq',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '邮箱地址',
				field : 'email',
				align : 'center',
				width : 100,
				sortable:true
			}, {
				title : '注册时间',
				field : 'registerDate',
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
				title : '最后一次登录时间',
				field : 'lastLoginDate',
				align : 'center',
				width : 120,
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
			href:projectName+'/qmtt/appUser/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
				    	$("#appUserConfigForm").form('submit',{
				    		 type:'POST',
				    		 url : projectName+'/qmtt/appUser/admin/add',
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
			href:projectName+'/qmtt/appUser/admin/toEdit?id='+id,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
					    	$("#appUserConfigForm").form('submit',{
					    		 type:'POST',
					    		 url : projectName+'/qmtt/appUser/admin/update',
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
					url:projectName+'/qmtt/appUser/admin/deleteById?id=' + id,
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
		$("#datagrid").datagrid("load", serializeObject($("#appUserForm")));
	}
	
	//重置
	function reset(){
		$("#appUserForm").form("reset");
	}
	