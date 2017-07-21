	/**
	 * 反馈管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/sys/activity/admin/list?v_date=' + new Date(),
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
				field : 'id',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 300,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.state == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",2)'>发布</a>&nbsp;";
					}
					if(row.state == 2){
						handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",3)'>结束发布</a>&nbsp;";
					}
					if(row.state != 1){
						handleHtml += "<a href='javascript:void(0);' onclick='viewRegister("+row.id+",\""+ row.actTheme +"\")'>查看报名情况</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='exportRegister("+row.id+",\""+ row.actTheme +"\")'>导出报名列表</a>&nbsp;";
					}
					return handleHtml;
				}
			}, {
				title : '活动主题',
				field : 'actTheme',
				align:'center',
				formatter:function(value,row,index){
					var actTheme = row.actTheme;
					if(actTheme != null && actTheme != ''){
						if(actTheme.length > 20) actTheme = actTheme.substring(0,20)+"...";
						return "<span title='"+row.actTheme+"'>" + actTheme +"</span>";
					}
				},
				width : 250
			}, {
				title : '活动人数',
				field : 'actNums',
				align:'center',
				width : 100
			}, {
				title : '活动开始时间',
				field : 'actBeginTime',
				align:'center',
				width : 150
			}, {
				title : '活动结束时间',
				field : 'actEndTime',
				align:'center',
				width : 150
			}, {
				title : '活动地点',
				field : 'actPlace',
				align:'center',
				formatter:function(value,row,index){
					var actPlace = row.actPlace;
					if(actPlace != null && actPlace != ''){
						if(actPlace.length > 20) actPlace = actPlace.substring(0,20)+"...";
						return "<span title='"+row.actPlace+"'>" + actPlace +"</span>";
					}
				},
				width : 300
			},{
				title : '状态',
				field : 'state',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.state == 1){return "<font color=red>未发布</font>"};
					if(row.state == 2){return "<font color=green>已发布</font>"};
					if(row.state == 3){return "<font color=grey>已结束</font>"};
				},
				width : 100
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
			href:projectName+'/sys/activity/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	//处理富文本编辑的内容
			    	var html = editor.html();
			    	$("#actIntroduce").val(html);
		    		$("#activityForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/activity/admin/add',
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
			href:projectName+'/sys/activity/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				     	//处理富文本编辑的内容
				    	var html = editor.html();
				    	$("#actIntroduce").val(html);
				    	$("#activityForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/sys/activity/admin/update',
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
	function changeState(sid,state){
		var tip = "信息无误，确定发布吗？";
		if(state == 3){
			tip = "确定结束发布吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/sys/activity/admin/changeState?id=' + sid+'&state='+state,
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
		$("#datagrid").datagrid("load",serializeObject($("#activitySearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#activitySearchForm").form("reset");
	}
	
	//查看活动报名情况
	function viewRegister(id,actTheme){
		$('#dlg').dialog('open');
		var datagridGetDetail = $('#datagridRegister').datagrid({
			method:'get',
			url : projectName+'/sys/activity/admin/registerList?actId=' + id +'&v_date=' + new Date(),	
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			singleSelect:true,
			rownumbers: true,
			toolbar:[{
				text:"导出",
				iconCls : 'icon-add',
				handler : function exp(){
					exportRegister(id,actTheme);
				}
			}],
 			frozenColumns : [[{
				field : 'id',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			}, {
				title : '报名人姓名',
				field : 'registerName',
				align:'center',
				width : 100
			}, {
				title : '报名人电话',
				field : 'registerPhone',
				align:'center',
				width : 100
			}, {
				title : '工作单位',
				field : 'workUnit',
				align:'center',
				width : 250
			}, {
				title : '报名时间',
				field : 'registerTime',
				align:'center',
				width : 150
			}] ]
		});
		
	}
	
	function exportRegister(sid,actTheme){
		window.location = projectName + '/sys/activity/admin/exportRegister?actId='+sid+'&actTheme='+actTheme;
	}
	
	