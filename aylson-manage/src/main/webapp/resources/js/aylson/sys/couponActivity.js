	/**
	 * 优惠券活动配置
	 */
	var datagrid;
	var editor;
	var curUserType = $("#curUserType").val();
	
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/sys/coupon/admin/list?v_date=' + new Date(),
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
				width : 150,
				formatter:function(value,row,index){
					var handleHtml = "";
					/*if(row.state == 0){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",1)'>上线</a>&nbsp;";
						
					}else if(row.state == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='query("+row.id+",0)'>查看</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",0)'>下线</a>&nbsp;";
					}*/
					//
					//alert("curUserType =="+curUserType+"|"+"userType =="+row.userType+"state =="+"|"+row.state)
					if(curUserType == 1){//总部操作
						if(row.userType == 1){
							if(row.state == 0){
								handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
								handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",1)'>上线</a>&nbsp;";
							}else if(row.state == 1){
								handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",0)'>下线</a>&nbsp;";
							}
						}else if(row.userType == 2 && row.auditState == 1){
							handleHtml += "<a href='javascript:void(0);' onclick='verify("+row.id+")'>审核</a>&nbsp;";
						}
					}else if(curUserType == 2){//门店操作
						if(row.state == 0 && (row.auditState == 0 || row.auditState == 3)){
							handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
							handleHtml += "<a href='javascript:void(0);' onclick='apply("+row.id+",1)'>申请</a>&nbsp;";
						}else if(row.state == 0 && row.auditState == 2){
							handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",1)'>上线</a>&nbsp;";
						}else if(row.state == 1){
							handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",0)'>下线</a>&nbsp;";
						}
					}
					handleHtml += "<a href='javascript:void(0);' onclick='query("+row.id+",0)'>查看</a>&nbsp;";
					return handleHtml;
				}
			}, {
				title : '发布状态',
				field : 'state',
				align : 'center',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					if(row.state == 0){
						return "<font color=red>未发布</font>";
					}else if(row.state == 1){
						return "<font color=green>已发布</font>";
					};
				}
			}, {
				title : '审核状态',
				field : 'auditState',
				align : 'center',
				sortable:true,
				width : 80,
				formatter:function(value,row,index){
					if(row.userType == 1) return "<font color=green>不用审核</font>";
					if(row.auditState == 0){
						return "<font color=red>未申请</font>";
					}else if(row.auditState == 1){
						return "<font color=grey>审核中...</font>";
					}else if(row.auditState == 2){
						return "<font color=green>已审核</font>";
					}else if(row.auditState == 3){
						return "<font color=red>审核不通过</font>";
					};
				}
			}, {
				title : '活动类型',
				field : 'activityType',
				align : 'center',
				width : 100,
				formatter:function(value,row,index){
					if(value == 1){
						return "线上活动";
					}else if(value == 2){
						return "线下活动";
					}else if(value == 3){
						return "电商活动";
					}else{
						return "未知";
					};
				}
			}, {
				title : '活动标题',
				field : 'title',
				align : 'center',
				formatter:function(value,row,index){
					var title = row.title;
					var tip = title;
					if(title != null && title != ''){
						if(title.length > 15) title = title.substring(0,15)+"...";
						return "<span title='"+tip+"'>" + title +"</span>";
					}
				},
				width : 150
			}, {
				title : '活动摘要',
				field : 'summary',
				align : 'center',
				formatter:function(value,row,index){
					var summary = row.summary;
					var tip = summary;
					if(summary != null && summary != ''){
						if(summary.length > 20) summary = summary.substring(0,20)+"...";
						return "<span title='"+tip+"'>" + summary +"</span>";
					}
				},
				width : 250
			}, {
				title : '活动开始时间',
				field : 'startTime',
				align : 'center',
				width : 150
			}, {
				title : '活动结束时间',
				field : 'endTime',
				align : 'center',
				width : 150
			}, {
				title : '活动地点',
				field : 'location',
				align : 'center',
				width : 150
			}, {
				title : '人数限制',
				field : 'limitNum',
				align : 'center',
				width : 80
			}, {
				title : '活动地区',
				field : 'area',
				align : 'center',
				formatter:function(value,row,index){
					if(row.area != '')return row.province+row.city+row.area;
				},
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
			height:'95%',
			modal:true,
			href:projectName+'/sys/coupon/admin/toAdd',
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
	    	    	$("#couponConfigForm").form('submit',{
			    		 type:'POST',
			    		 url : projectName+'/sys/coupon/admin/add',
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
			height:'95%',
			maximizable:true,
			modal:true,
			href:projectName+'/sys/coupon/admin/toEdit?id='+sid,
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
				    	$("#couponConfigForm").form('submit',{
				    		 type:'POST',
				    		 url : projectName+'/sys/coupon/admin/update',
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
	
	//查看
	function query(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:900,
			height:'95%',
			maximizable:true,
			modal:true,
			href:projectName+'/sys/coupon/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
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
	function changeState(sid,state){
		var tip = "";
		if(state == 0){
			tip = "确定下线吗？";
			
		}else if(state == 1){
			tip = "确定上线吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/sys/coupon/admin/changeState?id=' + sid+'&state='+state,
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
	
	//门店申请优惠活动
	function apply(sid,auditState){
		var tip = "确定申请该次活动吗？";
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/sys/coupon/admin/update?id=' + sid+'&auditState='+auditState,
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
	
	//审核
	function verify(sid){
		win = $("<div></div>").dialog({
			id:"verify",
			title:'审核',
			width:900,
			height:'95%',
			maximizable:true,
			modal:true,
			href:projectName+'/sys/coupon/admin/toVerify?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'通过',
				    iconCls:'icon-ok',
				    handler:function(){
				    	verifyPass(sid);
				    	$('#verify').dialog('destroy');
				    }   
				   },{
						text:'不通过',
					    iconCls:'icon-ok',
					    handler:function(){
					    	$('#auditOpitionDlg').dialog({
								buttons:[{
									text:'确定',
									iconCls:'icon-ok',
									handler:function(){
										//获取备注内容
										verifyNotPass(sid);
									}
								},{
									 text:'取消',
								     iconCls:'icon-cancel',  
								 	 handler:function(){
								 		$('#auditOpitionDlg').dialog('close');
								 	 }   
								  }]
							});
					    	$("#auditOpinion").textbox('setValue','');
					    	$('#auditOpitionDlg').dialog('open');
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
	
	//审核通过
	function verifyPass(sid){
		$.ajax({
			type:"POST",
			url:projectName+'/sys/coupon/admin/verify',
			data:{
				"id":sid,
				"auditState":2
			},
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
	
	//审核通过
	function verifyNotPass(sid){
		var auditOpinion = $("#auditOpinion").textbox('getValue');
		if(auditOpinion == ""){
			$.messager.show({"title":"系统提示","msg":"请输入审核不通过的意见","timeout":1000});
			return;
		}
		$.ajax({
			type:"POST",
			url:projectName+'/sys/coupon/admin/verify',
			data:{
				"id":sid,
				"auditState":3,
				"curAuditOpinion":auditOpinion
			},
			dataType:"json",
			success:function(data){
				if(data){
					$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
					if(data.success){
						$("#datagrid").datagrid("reload");
						$('#auditOpitionDlg').dialog('close');
						$('#verify').dialog('destroy');
					}
				 }
			}
		});
	}