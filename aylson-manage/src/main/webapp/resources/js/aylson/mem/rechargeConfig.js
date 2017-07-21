	/**
	 * 充值配置管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/mem/rechargeConfig/admin/list?v_date=' + new Date(),
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
					if(row.status == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.id+",2)'>上架</a>&nbsp;";
					}
					if(row.status == 2){
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.id+",3)'>下架</a>&nbsp;";
					}
					//handleHtml += "<a href='javascript:void(0);' onclick='view("+row.id+")'>查看</a>&nbsp;";
					return handleHtml;
				}
			}, {
				title : '标题',
				field : 'title',
				align:'center',
				width : 200
			}, {
				title : '支付金额',
				field : 'amount',
				align:'center',
				width : 100
			}, {
				title : '兑换金币',
				field : 'buyGold',
				align:'center',
				width : 100
			}, {
				title : '赠送金币',
				field : 'sendGold',
				align:'center',
				width : 100
			}, {
				title : '上架时间',
				field : 'publishTime',
				align:'center',
				width : 150
			},{
				title : '状态',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 1) return "<font color=blue>新增</font>";
					if(row.status == 2) return "<font color=green>上架</font>";
					if(row.status == 3) return "<font color=red>下架</font>";
				},
				width : 150
			} ] ]
		});
		
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:600,
			height:400,
			maximizable:true,
			modal:true,
			href:projectName+'/mem/rechargeConfig/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
		    		$("#rechargeConfigForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/mem/rechargeConfig/admin/add',
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
			width:600,
			height:400,
			maximizable:true,
			modal:true,
			href:projectName+'/mem/rechargeConfig/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#rechargeConfigForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/rechargeConfig/admin/update',
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
					url:projectName+'/mem/rechargeConfig/admin/changeStatus?id=' + sid+'&status='+status,
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
			href:projectName+'/mem/rechargeConfig/admin/toView?id='+sid,
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
					url:projectName+'/mem/rechargeConfig/admin/deleteById?id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#rechargeConfigSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#rechargeConfigSearchForm").form("reset");
	}
	
	