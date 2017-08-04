	/**
	 * 提现审核管理
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/cfdb/withdrawHis/admin/list?v_date=' + new Date(),
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
				width : 80,
				formatter:function(value,row,index){
					var handleHtml = '';
					handleHtml += '<a href="javascript:edit(\'' + row.id + '\')">处理</a>&nbsp;';
					return handleHtml;
				}
			}, {
				title : '手机标识码',
				field : 'phoneId',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '提现类型',
				field : 'withdrawName',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '姓名',
				field : 'name',
				align : 'center',
				width : 90,
				sortable:true
			}, {
				title : '账户名',
				field : 'account',
				align : 'center',
				width : 150,
				sortable:true
			}, {
				title : '提现金额',
				field : 'income',
				align : 'center',
				width : 70,
				sortable:true
			}, {
				title : '提现发起时间',
				field : 'withdrawTime',
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
				title : '提现状态',
				field : 'status',
				align : 'center',
				sortable:true,
				width : 80,
				formatter:function(value,row,index){
					if(row.statusType == 2 || row.statusType == 3){
						return "<font color=green>成功</font>";
					}else if(row.statusType == 4){
						return "<font color=red>失败</font>";
					}
					return value;
				}
			}
			, {
				title : '审批完成时间',
				field : 'updateDate',
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
				title : '审核人',
				field : 'updatedBy',
				align : 'center',
				width : 120
			}
			] ]
		});
		
	});
	
	//修改
	function edit(id){
		win = $("<div></div>").dialog({
			title:'提现处理',
			width:450,
			height:'52%',
			maximizable:true,
			modal:true,
			href:projectName+'/cfdb/withdrawHis/admin/toEdit?id='+id,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'提交',
				    iconCls:'icon-ok',
				    handler:function(){
				    		var statusTypeOld = $('#statusTypeOld').val();
				    		var statusType = $('#statusType').combobox('getValue');
				    		if(statusTypeOld == statusType){
				    			$.messager.alert('提示','数据未变化','info');
				    			return;
				    		}
				    		if((statusTypeOld == 2 && statusType ==3) || (statusTypeOld == 3 && statusType ==2)){
				    			$.messager.alert('提示','不能重复审核成功','info');
				    			return;
				    		}
				    		if(statusTypeOld == 4){
				    			$.messager.alert('提示','已审核失败，系统自动回款了，不能再审核！<font color=blue>APP用户重新发起提现操作即可</font>','info');
				    			return;
				    		}
					    
				    		if(statusType == 1){
				    			$.messager.alert('提示','提交\'提现状态\'不能为\'处理中\'，请审核或取消','error');
				    		}else{
				    			$.messager.confirm("提示","确定更新该记录吗?",function(r){
									if(r){
										$("#withdrawHisConfigForm").form('submit',{
									    		 type:'POST',
									    		 url : projectName+'/cfdb/withdrawHis/admin/update',
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
		$("#datagrid").datagrid("load", serializeObject($("#withdrawHisForm")));
	}
	
	//重置
	function reset(){
		$("#withdrawHisForm").form("reset");
	}
	