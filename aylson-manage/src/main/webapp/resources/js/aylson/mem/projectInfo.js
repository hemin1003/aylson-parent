	/**
	 * 方案管理.js
	 */
	var datagrid;
	var memberType = $("#memberType").val();
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/mem/projectInfo/admin/list?v_date=' + new Date(),
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
				'memberType':memberType
			},
			toolbar:[ /*{
				text:"新增",
				iconCls : 'icon-add',
				handler : add
			},*/{
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
				width : 200,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(memberType == 1){//设计师
						if(row.status == 2){
							handleHtml += "<a href='javascript:void(0);' onclick='verify("+row.id+")'>审核</a>&nbsp;";
						}else if(row.status == 4 || row.status == 51){
							handleHtml += "<a href='javascript:void(0);' onclick='design("+row.id+")'>设计</a>&nbsp;";
						}else if(row.status == 6 || row.status == 7){
							handleHtml += "<a href='javascript:void(0);' onclick='settle("+row.id+")'>结算</a>&nbsp;";
						}
						if(row.status == 8){
							handleHtml += "<a href='javascript:void(0);' onclick='addSuccessCase("+row.id+")'>成功案例</a>&nbsp;";
						}
						handleHtml += "<a href='javascript:void(0);' onclick='view("+row.id+")'>查看</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='viewFlowNode("+row.id+")'>查看流程</a>&nbsp;";
					}else if(memberType == 2){//产业工人
						if(row.status == 2){
							handleHtml += "<a href='javascript:void(0);' onclick='verifyWork("+row.id+")'>审核</a>&nbsp;";
						}
					}else if(memberType == 3){//开拓者
						if(row.status == 2){
							handleHtml += "<a href='javascript:void(0);' onclick='verifyPioneer("+row.id+")'>审核</a>&nbsp;";
						}
					}
					return handleHtml;
				}
			}, {
				title : '工程名称',
				field : 'projectName',
				align:'center',
				formatter:function(value,row,index){
					var projectName = row.projectName;
					if(projectName != null && projectName != ''){
						if(projectName.length > 15) projectName = projectName.substring(0,15)+"...";
						return "<span title='"+row.projectName+"'>" + projectName +"</span>";
					}
				},
				width : 200
			}, {
				title : '设计内容',
				field : 'productTypes',
				align:'center',
				width : 150
			}, {
				title : '地址',
				field : 'clientAddress',
				align:'center',
				formatter:function(value,row,index){
					var address = row.province + row.city + row.area +row.clientAddress;
					var clientAddress = row.province + row.city + row.area +row.clientAddress;
					if(clientAddress != null && clientAddress != ''){
						if(clientAddress.length > 15) clientAddress = clientAddress.substring(0,15)+"...";
						return "<span title='"+ address +"'>" + clientAddress +"</span>";
					}
				},
				width : 250
			}, {
				title : '电话号码',
				field : 'mobilePhone',
				align:'center',
				width : 100
			}]],
			columns:[[{
				title : '设计师',
				field : 'accountName',
				align:'center',
				width : 100
			}, {
				title : '设计师电话',
				field : 'memberPhone',
				align:'center',
				width : 120
			}, {
				title : '所属代理商',
				field : 'byAgent',
				align:'center',
				width : 150
			}, {
				title : '状态',
				field : 'status',
				align:'center',
				formatter:function(value,row,index){
					if(row.memberType == 1){
						if(row.status == 1)return "待提交资料";
						if(row.status == 2)return "待审核 ";
						if(row.status == 3)return "已审核，待提交方案 ";
						if(row.status == 4)return "已提交方案 ，待设计方案";
						if(row.status == 5)return "已设计 ，待确认";
						if(row.status == 6)return "已确认 ，待结算";
						if(row.status == 7)return "已评价";
						if(row.status == 8)return "已结算";
						if(row.status == 11)return "审核不通过，待重新提交资料";
						if(row.status == 31)return "客户未确认，待重新提交方案";
						if(row.status == 32)return "方案需求审核不通过，待重新提交方案";
						if(row.status == 51)return "设计不满意，待重新设计";
					}else if(row.memberType == 2){
						if(row.status == 2)return "<font color=red>待审核</font> ";
						if(row.status == 3)return "<font color=green>已审核</font> ";
						if(row.status == 11)return "<font color=grey>审核不通过</font>";
						else return "<font color=red>未知</font> ";
					}
					
				},
				width : 200
			}, {
				title : '备注',
				field : 'remark',
				align:'center',
				formatter:function(value,row,index){
					var remark = row.remark;
					if(remark != null && remark != ''){
						if(remark.length > 12) remark = remark.substring(0,12)+"...";
						return "<span title='"+row.remark+"'>" + remark +"</span>";
					}
				},
				width : 200
			}, {
				title : '是否成功案例',
				field : 'isSuccessfulCase',
				align:'center',
				formatter:function(value,row,index){
					if(row.isSuccessfulCase == true) return '<font color=green>是</font>';
					if(row.isSuccessfulCase == false) return "<font color=grey>否</font>";
				},
				width : 100
			}] ],
			onBeforeLoad:function(){
				if(memberType == 2){
					$("#datagrid").datagrid('hideColumn','productTypes');
					$("#datagrid").datagrid('hideColumn','isSuccessfulCase');
					$("#datagrid").datagrid('hideColumn','accountName');
					$("#datagrid").datagrid('hideColumn','memberPhone');
					$("#datagrid").datagrid('autoSizeColumn','opt');
				}
			}, 
		});
	});			
	
	//审核设计师客户资料
	function verify(sid){
		win = $("<div></div>").dialog({
			title:'审核',
			width:700,
			height:400,
			modal:true,
			href:projectName+'/mem/projectInfo/admin/toVerify?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'审核通过',
				    iconCls:'icon-ok',
				    handler:function(){
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	var byAgentId = $("#byAgentId").val();
				    	if(byAgentId == null || byAgentId == ''){
				    		$("#byAgent").combobox({required:true});
				    	}
				    	$("#projectInfoForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/projectInfo/admin/verify?status=3',
				    		 success:function(responseData){
				    			 $.messager.progress('close');
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
						text:'审核不通过',
					    iconCls:'icon-no',
					    handler:function(){
					    	$("#byAgent").combobox("clear");
					    	$("#byAgent").combobox({required:false});
					    	var auditOpition = $("#auditOpition").textbox("getValue");
					    	if(auditOpition == ""){
					    		$.messager.show({"title":"系统提示","msg":"审核不通过必须填写审核意见","timeout":1000});
					    		return;
					    	}
					    	var winTip = $.messager.progress({
								title:'请稍候...',
								msg:'正在处理...'
							});
					    	$("#projectInfoForm").form('submit',{
					    		 type:'POST',
					    		 url:projectName+'/mem/projectInfo/admin/verify?status=11',
					    		 success:function(responseData){
					    			 $.messager.progress('close');
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
	
	//审核产业人员提交的客户资料
	function verifyWork(sid){
		win = $("<div></div>").dialog({
			title:'审核',
			width:700,
			height:400,
			modal:true,
			href:projectName+'/mem/projectInfo/admin/toVerifyWork?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'审核通过',
				    iconCls:'icon-ok',
				    handler:function(){
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	$("#projectInfoForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/projectInfo/admin/verifyWork?status=3',
				    		 success:function(responseData){
				    			 $.messager.progress('close');
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
						text:'审核不通过',
					    iconCls:'icon-no',
					    handler:function(){
					    	var auditOpition = $("#auditOpition").textbox("getValue");
					    	if(auditOpition == ""){
					    		$.messager.show({"title":"系统提示","msg":"审核不通过必须填写审核意见","timeout":1000});
					    		return;
					    	}
					    	var winTip = $.messager.progress({
								title:'请稍候...',
								msg:'正在处理...'
							});
					    	$("#projectInfoForm").form('submit',{
					    		 type:'POST',
					    		 url:projectName+'/mem/projectInfo/admin/verifyWork?status=11',
					    		 success:function(responseData){
					    			 $.messager.progress('close');
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
	
	//审核开拓者提交的客户资料
	function verifyPioneer(sid){
		win = $("<div></div>").dialog({
			title:'审核',
			width:700,
			height:400,
			modal:true,
			href:projectName+'/mem/projectInfo/admin/toVerifyPioneer?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'审核通过',
				    iconCls:'icon-ok',
				    handler:function(){
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	$("#projectInfoForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/projectInfo/admin/verifyPioneer?status=3',
				    		 success:function(responseData){
				    			 $.messager.progress('close');
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
						text:'审核不通过',
					    iconCls:'icon-no',
					    handler:function(){
					    	var auditOpition = $("#auditOpition").textbox("getValue");
					    	if(auditOpition == ""){
					    		$.messager.show({"title":"系统提示","msg":"审核不通过必须填写审核意见","timeout":1000});
					    		return;
					    	}
					    	var winTip = $.messager.progress({
								title:'请稍候...',
								msg:'正在处理...'
							});
					    	$("#projectInfoForm").form('submit',{
					    		 type:'POST',
					    		 url:projectName+'/mem/projectInfo/admin/verifyPioneer?status=11',
					    		 success:function(responseData){
					    			 $.messager.progress('close');
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
	
	//设计
	function design(sid){
		win = $("<div></div>").dialog({
			title:'设计',
			width:'80%',
			height:'95%',
			modal:true,
			href:projectName+'/mem/projectInfo/admin/toDesign?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'退回',
				    iconCls:'icon-back',
				    handler:function(){
				    	var auditOpition = $("#auditOpition").textbox("getValue");
				    	if(auditOpition == ""){
				    		$.messager.show({"title":"系统提示","msg":"退回必须填写退回原因","timeout":1000});
				    		return;
				    	}
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	$("#projectInfoForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/projectInfo/admin/returnRequirement',
				    		 success:function(responseData){
				    			 $.messager.progress('close');
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
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	$("#projectInfoForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/projectInfo/admin/design',
				    		 success:function(responseData){
				    			 $.messager.progress('close');
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
	
	//结算
	function settle(sid){
		win = $("<div></div>").dialog({
			title:'结算',
			width:'90%',
			height:'95%',
			modal:true,
			href:projectName+'/mem/projectInfo/admin/toSettle?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	$("#projectInfoForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/projectInfo/admin/settle',
				    		 success:function(responseData){
				    			 $.messager.progress('close');
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
	
	//查看
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:'100%',
			height:'100%',
			modal:true,
			href:projectName+'/mem/projectInfo/admin/toView?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					 text:'关闭',
				     iconCls:'icon-no',  
				 	 handler:function(){
				 		 win.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//查看流程节点
	function viewFlowNode(id){
		$('#dlg').dialog('open');
		var datagridGetDetail = $('#datagridFlowNode').datagrid({
			method:'get',
			url : projectName+'/mem/projectInfo/admin/flowNodeList?projectId=' + id +'&v_date=' + new Date(),	
			pagination : false,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			singleSelect:true,
			rownumbers: true,
 			frozenColumns : [[{
				field : 'id',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			}, {
				title : '流程环节',
				field : 'status',
				align:'center',
				formatter:function(value,row,index){
					if(row.status == 1)return "待提交资料";
					if(row.status == 2)return "待审核";
					if(row.status == 3)return "已审核，待提交方案";
					if(row.status == 4)return "已提交方案 ，待设计";
					if(row.status == 5)return "已设计 ，待确认";
					if(row.status == 6)return "已确认 ，待结算";
					if(row.status == 7)return "已评价";
					if(row.status == 8)return "已结算";
					if(row.status == 11)return "审核不通过，待重新提交资料";
					if(row.status == 31)return "客户未确认，待重新提交方案";
					if(row.status == 32)return "方案需求审核不通过，待重新提交方案";
					if(row.status == 51)return "设计不满意，待重新设计";
				},
				width : 250
			}, {
				title : '审核意见',
				field : 'remark',
				align:'center',
				width : 300
			}, {
				title : '操作人',
				field : 'oper',
				align:'center',
				width : 100
			}, {
				title : '操作时间',
				field : 'createTime',
				align:'center',
				width : 150
			}] ]
		});
	}
	
	//标记为成功案例
	/*function addSuccessCase(sid){
		var tip = "确认将该方案标记为【成功案例】吗？";
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/mem/projectInfo/admin/update?id=' + sid+'&isSuccessfulCase=true',
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
	}*/
	function addSuccessCase(sid){
		win = $("<div></div>").dialog({
			title:'成功案例',
			width:800,
			height:560,
			modal:true,
			href:projectName+'/mem/projectInfo/admin/toSuccessCase?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	$("#projectInfoForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/projectInfo/admin/successCase',
				    		 success:function(responseData){
				    			 $.messager.progress('close');
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
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#projectInfoSearchForm")));
	}
	
	//重置
	function reset(){
		$("#projectInfoSearchForm").form("reset");
	}
	
	
	function viewImg(){
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		var h = $(window).height();
		var hh = h/2;
		$(".naviga_big").each(function(){
			$(this).hover(function(e){
				$("#imgBig").attr({"src":$(this).attr("src")});
				$("#imgBig").css({"width":600,"height":600});
				//alert(e.pageY+";"+h/2)
			    $("#divImage").css("top",yOffset + "px")
				if(e.pageX < w/2){
					$("#divImage").css({
						left: e.pageX + xOffset + "px",
						right: "auto"
					}).fadeIn("fast");
				}else{
					$("#divImage").css("right",(w - e.pageX + yOffset) + "px").css("left", "auto").fadeIn("fast");	
				}
				$("#divImage").show();
			},function(){
				$("#divImage").hide();
			}).mousemove(function(e){
				$("#divImage").css("top",yOffset + "px")
				if(e.pageX < w/2){
					$("#divImage").css("left",(e.pageX + yOffset) + "px").css("right","auto");
				}else{
					$("#divImage").css("right",(w - e.pageX + yOffset) + "px").css("left","auto");
				}
			});	
		})
	}
	
	//上传设计附件
	function uploadDesign(id,target,bucket){//id:获取上传文件的元素,target:目标id
		if(bucket == null){
		 	$.messager.show({"title":"系统提示","msg":"找不到对应的资源空间，请联系管理员","timeout":1000});
		 	return;
		}
		var win;
		win = $("<div></div>").dialog({
			title:'上传文件',
			width:500,
			height:200,
			modal:true,
			href:projectName+'/sys/fileHandle/toFileUpload',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	var winTip = $.messager.progress({
						title:'请稍候...',
						msg:'正在上传...'
					});
			    	var filePath = $("#fileName").filebox('getValue');
			    	$("#bucket").val(bucket);
			    	$("#fileUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/fileUpload',
			    		 success:function(responseData){
			    			 $.messager.progress('close');
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		var fileName = data.data;
			    			 		var showFileName = fileName.split("@|@")[1];
			    			 		/*var appendHtml = "";
			    			 		appendHtml += "<li>"+
									"<a target='_blank' href=\"${designAttach.attachAddress }\" title='${fn:substringAfter( designAttach.attachAddress,\"@|@\")}'>"+
									"<img  class=\"naviga_big\" alt=\"\" src=\"${designAttach.attachAddress }\" style=\"width:50px;height:50px;\">"+
									"</a><br />"+
									"<a target='_blank' href=\"${requirementAttach.attachAddress }\" title='${fn:substringAfter( requirementAttach.attachAddress,\"@|@\")}'>"+
									"	附件${design.index+1}"+
									"</a>"+
									"</li>";*/
			    			    	$("#"+id).append("<li><a name='fileItem' target='_blank' href='"+ fileName +"' alt='"+showFileName+"'>"+showFileName+"</a>&nbsp;<a href='#' onclick='delElement(this,\""+target+"\")'>删除</a></li>");
			    			 		$("#"+target).val($("#"+target).val()+","+fileName);//
			    			 		win.dialog('destroy');
			    				}
			    			 } 
			    		 }
			    	});
			    	}
//			     }   
			   },{
				 text:'取消',
			     iconCls:'icon-cancel',  
			 	 handler:function(){
			 		 win.dialog('destroy');
			 	 }   
			  }]
		});
	}
	
	