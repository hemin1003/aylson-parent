	/**
	 * 机构用户管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/sys/orgUser/admin/list?v_date=' + new Date(),
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
			toolbar:[ {
				text:"新增",
				iconCls : 'icon-add',
				handler : add
			},{
				text:"刷新",
				iconCls : 'icon-reload',
				handler : reload
			}],
 			frozenColumns : [[{
				field : 'id',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 150,
				formatter:function(value,row,index){
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
					//handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
					if(row.status == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.userId+",0)'>禁用</a>&nbsp;";
					}else if(row.status == 0){
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.userId+",1)'>启用</a>&nbsp;";
					}
					return handleHtml;
				}
			},{
				title : '禁用',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 0)return '<font color=red>是</font>';
					if(row.status == 1) return '<font color=green>否</font>';
				},
				width : 50
			}, {
				title : '帐号',
				field : 'userName',
				align:'center',
				width : 100
			}, {
				title : '姓名',
				field : 'realName',
				align:'center',
				width : 100
			},{
				title : '所属部门',
				field : 'orgName',
				align:'center',
				sortable:true,
				width : 100
			},{
				title : '移动电话',
				field : 'mobilePhone',
				align:'center',
				sortable:true,
				width : 80
			}]],
			columns : [[{
				title : '电子邮箱',
				field : 'email',
				align:'center',
				sortable:true,
				width : 120
			},{
				title : 'qq号码',
				field : 'qq',
				align:'center',
				sortable:true,
				width : 80
			},{
				title : '创建日期',
				field : 'createTime',
				align:'center',
				sortable:true,
				width : 150
			} ] ]
		});
		
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:800,
			height:300,
			modal:true,
			href:projectName+'/sys/orgUser/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
		    		$("#orgUserForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/orgUser/admin/add',
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
	function edit(sid){
		win = $("<div></div>").dialog({
			title:'编辑',
			width:800,
			height:300,
			modal:true,
			href:projectName+'/sys/orgUser/admin/toEdit?id=' + sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#orgUserForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/sys/orgUser/admin/update',
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
	function del(sid){
		$.messager.confirm("提示","确定删除此记录吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/sys/orgUser/admin/deleteById?id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#orgUserSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#orgUserSearchForm").form("reset");
	}
	
	//修改用户状态
	function changeStatus(sid,status){
		var tip = "确定禁用该用户吗？";
		if(status == 1){
			tip = "确定启用该用户吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/sys/user/admin/changeStatus?id=' + sid+'&status='+status,
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
	
	