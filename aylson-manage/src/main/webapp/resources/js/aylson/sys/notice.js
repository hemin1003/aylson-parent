	/**
	 * 优惠券管理.js
	 */
	var datagrid;                           //优惠券配置管理列表
	var curUrl = "/sys/notice";         //当前接口链接
	
	$(function(){
		datagrid();
	})
	
	//列表
	function datagrid(){
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName + curUrl + '/admin/list?v_date=' + new Date(),
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
				text:"添加",
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
				title:'操作',
				align:'center',
				width : 120,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.state == 0){
						handleHtml += "<a href='javascript:void(0);' title='发布' onclick='publish("+row.id+",1)'>发布</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' title='修改' onclick='edit("+row.id+")'>修改</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' title='删除' onclick='del("+row.id+")'>删除</a>&nbsp;";
					}
					if(row.state == 1){
						handleHtml += "<a href='javascript:void(0);' title='取消发布' onclick='publish("+row.id+",2)'>取消发布</a>&nbsp;";
					}
					return handleHtml;
				}
			}, {
				title : '状态',
				field : 'state',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.state == 0) return "<font color=red>新建</font>";
					if(row.state == 1) return "<font color=green>已发布</font>";
					if(row.state == 2) return "<font color=grey>取消发布</font>";
				},
				width : 80
			}, {
				title : '标题',
				field : 'title',
				align:'center',
				width : 250
			}, {
				title : '创建时间',
				field : 'createTime',
				align:'center',
				width : 150
			},{
				title : '发布时间',
				field : 'publishTime',
				align:'center',
				sortable:false,
				width : 150
			}]]
		});
	}

	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#noticeSearchForm")));
	}
	
	//重置搜索条件
	function reset(){
		$("#noticeSearchForm").form("reset");
	}
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:600,
			height:450,
			maximizable:true,
			modal:true,
			href:projectName + curUrl+'/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
		    		$("#noticeForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName + curUrl + '/admin/add',
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
			height:450,
			maximizable:true,
			modal:true,
			href:projectName + curUrl + '/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#noticeForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName + curUrl + '/admin/update',
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
					url:projectName + curUrl + '/admin/deleteById?id=' + sid,
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
	
	/**
	 * 公告发布操作
	 * @returns
	 */
	function publish(sid,state){
		var tip = "";
		if(state == 1){
			tip = "确定发布吗？";
		}else if(state == 2){
			tip = "确定取消发布吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					data:{
						'id':sid,
						'state':state
					},
					url:projectName + curUrl + '/admin/publish',
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
	
