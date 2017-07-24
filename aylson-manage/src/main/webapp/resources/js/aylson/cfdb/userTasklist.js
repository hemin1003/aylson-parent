	/**
	 * 用户任务审核管理
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/cfdb/userTasklist/admin/list?v_date=' + new Date(),
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
					//审核中的状态才需要审核
					if(row.statusFlag == 2 || row.statusFlag == 3 || row.statusFlag == 4){
						handleHtml += '<a href="javascript:edit(\'' + row.id + '\')">审批</a>&nbsp;';
					}
					return handleHtml;
				}
			}, {
				title : '手机标识码',
				field : 'phoneId',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '任务名称',
				field : 'taskName',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '任务标签',
				field : 'taskTag',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '任务收益金额',
				field : 'income',
				align : 'center',
				width : 80,
				sortable:true
			}, 
//			{
//				title : '跳转url地址',
//				field : 'goUrl',
//				align : 'center',
//				width : 120,
//				sortable:true
//			}, 
			{
				title : '用户抢购任务时间',
				field : 'createDate',
				align : 'center',
				width : 150,
				sortable:true,
				formatter:function(value,row,index){
					if(value){
						return value.substring(0,19);
					}
					return value;
				}
			}, {
				title : '任务状态',
				field : 'status',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '审批时间',
				field : 'updateDate',
				align : 'center',
				width : 150,
				sortable:true,
				formatter:function(value,row,index){
					if(value){
						return value.substring(0,19);
					}
					return value;
				}
			}, {
				title : '是否查看过',
				field : 'isChecked',
				align : 'center',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					if(value == 1){
						return "否";
					}else if(value == 2){
						return "是";
					}
				}
			}
			] ]
		});
		
		$(".datagrid-body").css("overflow-x","scroll");
	});
	
	//修改
	function edit(id){
		win = $("<div></div>").dialog({
			title:'操作',
			width:450,
			height:'65%',
			maximizable:true,
			modal:true,
			href:projectName+'/cfdb/userTasklist/admin/toEdit?id='+id,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'提交',
				    iconCls:'icon-ok',
				    handler:function(){
					    	$.messager.confirm("提示","确定更新该记录吗？",function(r){
							if(r){
								$("#userTasklistConfigForm").form('submit',{
							    		 type:'POST',
							    		 url : projectName+'/cfdb/userTasklist/admin/update',
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
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load", serializeObject($("#userTasklistForm")));
	}
	
	//重置
	function reset(){
		$("#userTasklistForm").form("reset");
	}
	