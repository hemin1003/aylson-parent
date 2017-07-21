	/**
	 * 礼品积分兑换配置管理.js
	 */
	var datagrid;
	var editor;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/mem/giftConfig/admin/list?v_date=' + new Date(),
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
			}, {
				title : '礼品名称',
				field : 'giftName',
				align:'center',
				width : 300
			}, {
				title : '礼品编码',
				field : 'giftCode',
				align:'center',
				width : 150
			}, {
				title : '所需积分',
				field : 'integral',
				align:'center',
				width : 100
			},{
				title : '适用兑换对象',
				field : 'matchObj',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.matchObj == 0) return "全部";
					if(row.matchObj == 1) return "设计师";
					if(row.matchObj == 2) return "产业工人";
					if(row.matchObj == 3) return "开拓者";
					return "全部";
				},
				width : 80
			},{
				title : '状态',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 1) return "<font color=blue>新建</font>";
					if(row.status == 2) return "<font color=green>上架</font>";
					if(row.status == 3) return "<font color=red>下架</font>";
				},
				width : 80
			},{
				title : '状态',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 1) return "<font color=blue>新建</font>";
					if(row.status == 2) return "<font color=green>上架</font>";
					if(row.status == 3) return "<font color=red>下架</font>";
				},
				width : 80
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 300,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.status != 2){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.id+",2)'>上架</a>&nbsp;";
					}
					if(row.status == 2){
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.id+",3)'>下架</a>&nbsp;";
					}
					if(row.status == 1 ){
						handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
					}
					handleHtml += "<a href='javascript:void(0);' onclick='view("+row.id+")'>查看</a>&nbsp;";
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
			height:'90%',
			maximizable:true,
			modal:true,
			href:projectName+'/mem/giftConfig/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	//处理富文本编辑的内容
			    	var html = "";
			    	if(editor != null && editor != "undefined"){
			    		var html = editor.html();
			    	}
			    	$("#description").val(html);
		    		$("#giftConfigForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/mem/giftConfig/admin/add',
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
			height:'90%',
			maximizable:true,
			modal:true,
			href:projectName+'/mem/giftConfig/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	//处理富文本编辑的内容
				    	var html = editor.html();
				    	$("#description").val(html);
				    	$("#giftConfigForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/giftConfig/admin/update',
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
		var tip = "信息无误，确定上架吗？";
		if(status == 3){
			tip = "确定结束下架吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/mem/giftConfig/admin/changeStatus?id=' + sid+'&status='+status,
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
			width:700,
			height:400,
			maximizable:true,
			modal:true,
			href:projectName+'/mem/giftConfig/admin/toView?id='+sid,
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
					url:projectName+'/mem/giftConfig/admin/deleteById?id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#giftConfigSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#giftConfigSearchForm").form("reset");
	}
	
	