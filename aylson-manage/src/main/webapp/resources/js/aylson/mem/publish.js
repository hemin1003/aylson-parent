	/**
	 * 发布管理.js
	 */
	var datagrid;
	var type = $("#type").val();
	$(function() { 
		//$('#centerRegion').panel({'title':'通知公告列表'});
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/mem/publish/admin/list?v_date=' + new Date(),
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
			queryParams:{
				'type':type
			},
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
			}, {
				title : '标题',
				field : 'title',
				align:'center',
				formatter:function(value,row,index){
					var title = row.title;
					if(title != null && title != ''){
						if(title.length > 20) title = title.substring(0,20)+"...";
						return "<span title='"+row.title+"'>" + title +"</span>";
					}
				},
				width : 300
			}, {
				title : '发布时间',
				field : 'publishTime',
				align:'center',
				width : 150
			},{
				title : '状态',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 1) return "<font color=blue>草稿</font>";
					if(row.status == 2) return "<font color=green>发布</font>";
					if(row.status == 3) return "<font color=red>结束发布</font>";
				},
				width : 150
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 300,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.status == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
					}
					if(row.status != 2){
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.id+",2)'>发布</a>&nbsp;";
					}
					if(row.status == 2){
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.id+",3)'>结束发布</a>&nbsp;";
					}
					handleHtml += "<a href='javascript:void(0);' onclick='view("+row.id+")'>查看</a>&nbsp;";
					//handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
					return handleHtml;
				}
			} ] ]
		});
		
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:900,
			height:'100%',
			maximizable:true,
			modal:true,
			href:projectName+'/mem/publish/admin/toAdd?type='+type,
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	//处理富文本编辑的内容
			    	var html = editor.html();
			    	$("#content").val(html);
		    		$("#publishForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/mem/publish/admin/add',
			    		 success:function(responseData){
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    					$.messager.confirm("提示", "继续新增吗？", function(r) {
										if (r) {

										}else{
											$("#datagrid").datagrid("reload");
											win.dialog('destroy');
										}
									})
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
			width:900,
			height:'100%',
			maximizable:true,
			modal:true,
			href:projectName+'/mem/publish/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				     	//处理富文本编辑的内容
				    	var html = editor.html();
				    	$("#content").val(html);
				    	$("#publishForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/publish/admin/update',
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
	
	//发布
	function changeStatus(sid,status){
		var tip = "信息无误，确定发布吗？";
		if(status == 3){
			tip = "确定结束发布吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/mem/publish/admin/changeStatus?id=' + sid+'&status='+status,
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
	
	//查看
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:900,
			height:'100%',
			maximizable:true,
			modal:true,
			href:projectName+'/mem/publish/admin/toView?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
						 text:'关闭',
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
					url:projectName+'/mem/publish/admin/deleteById?id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#publishSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#publishSearchForm").form("reset");
	}
	
	