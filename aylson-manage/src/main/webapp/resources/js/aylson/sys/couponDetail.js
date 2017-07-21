	/**
	 * 优惠券礼品配置
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/sys/couponDetail/admin/list?v_date=' + new Date(),
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
				text:"新增",
				iconCls : 'icon-add',
				handler : add
			},{
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
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
					if(row.isEnabled == 0){
						handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",1)'>启用</a>&nbsp;";
						
					}else if(row.isEnabled == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",0)'>禁用</a>&nbsp;";
					}
					return handleHtml;
				}
			}, {
				title : '是否启用',
				field : 'isEnabled',
				align : 'center',
				width : 150,
				formatter:function(value,row,index){
					if(row.isEnabled == 0){
						return "<font color=red>已禁用</font>";
					}else if(row.isEnabled == 1){
						return "<font color=green>已启用</font>";
					};
				}
			}, {
				title : '优惠券名称',
				field : 'couponName',
				align : 'center',
				width : 150
			}, {
				title : '优惠券类型',
				field : 'couponType',
				align : 'center',
				width : 150,
				formatter:function(value,row,index){
					if(row.couponType == 1){
						return "现金券";
					};
				}
			}, {
				title : '券面值',
				field : 'couponValue',
				align : 'center',
				width : 150
			}, {
				title : '有效开始时间',
				field : 'startTime',
				align : 'center',
				width : 150
			}, {
				title : '有效终止时间',
				field : 'endTime',
				align : 'center',
				width : 150
			}, {
				title : '客服电话',
				field : 'serviceTel',
				align : 'center',
				width : 150
			}
			] ]
		});
		
	});
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:900,
			height:'90%',
			modal:true,
			href:projectName+'/sys/couponDetail/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
	    	    	$("#couponConfigForm").form('submit',{
			    		 type:'POST',
			    		 url : projectName+'/sys/couponDetail/admin/add',
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
			width:900,
			height:'90%',
			maximizable:true,
			modal:true,
			href:projectName+'/sys/couponDetail/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	//处理富文本编辑的内容
				    	$("#couponConfigForm").form('submit',{
				    		 type:'POST',
				    		 url : projectName+'/sys/couponDetail/admin/update',
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
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load", serializeObject($("#couponSearchForm")));
	}
	
	//重置
	function reset(){
		$("#couponSearchForm").form("reset");
	}
	
	//发布
	function changeState(sid,isEnabled){
		var tip = "";
		if(isEnabled == 0){
			tip = "确定禁用吗？";
			
		}else if(isEnabled == 1){
			tip = "确定启用吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/sys/couponDetail/admin/changeState?id=' + sid+'&isEnabled='+isEnabled,
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
	