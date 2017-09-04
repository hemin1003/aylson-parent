	/**
	 * 统计报表数据查询
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/qmtt/qmttSysReportInfo/admin/list?num=7',
			pagination : true,
			pageSize : 30,
			pageList : [ 30, 60, 90 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'dateStr',
			singleSelect:true,
			rownumbers: true,
			toolbar:[{
				text:"详细列表"
			},{
				text:"刷新",
				iconCls : 'icon-reload',
				handler : reload
			}],
 			frozenColumns : [[ {
				title : '日期',
				field : 'dateStr',
				align : 'center',
				width : 200
			}, {
				title : '新增用户数',
				field : 'newUserOfDay',
				align : 'center',
				width : 120
			}, {
				title : '用户金币余额',
				field : 'userGoldOfDay',
				align : 'center',
				width : 120
			}, {
				title : '用户零钱金额',
				field : 'userBalanceOfDay',
				align : 'center',
				width : 120
			}, {
				title : '用户提现金额',
				field : 'userIncomeOfDay',
				align : 'center',
				width : 120
			}, {
				title : '用户浏览新闻数',
				field : 'userReadOfDay',
				align : 'center',
				width : 120
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
			href:projectName+'/qmtt/qmttSysReportInfo/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
				    	$("#qmttSysReportInfoConfigForm").form('submit',{
				    		 type:'POST',
				    		 url : projectName+'/qmtt/qmttSysReportInfo/admin/add',
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
			href:projectName+'/qmtt/qmttSysReportInfo/admin/toEdit?id='+id,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
					    	$("#qmttSysReportInfoConfigForm").form('submit',{
					    		 type:'POST',
					    		 url : projectName+'/qmtt/qmttSysReportInfo/admin/update',
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
					url:projectName+'/qmtt/qmttSysReportInfo/admin/deleteById?id=' + id,
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
	function doSearch(num){
		getMapData(num);
		//查询参数直接添加在queryParams中     
        $('#datagrid').datagrid({  
        		url : projectName+'/qmtt/qmttSysReportInfo/admin/list?num=' + num
        })  
        var queryParams =$('#datagrid').datagrid('options').queryParams;  
        getQueryParams(queryParams);  
        $('#datagrid').datagrid('options').queryParams = queryParams;  
       	$("#datagrid").datagrid('reload'); 
	}
	
	//重置
	function reset(){
		$("#qmttSysReportInfoForm").form("reset");
	}
	