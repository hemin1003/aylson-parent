	/**
	 * 会员：我的反馈管理.js
	 */
	var datagrid;
	var type = $("#type").val();
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			queryParams:{
				"feedbackerType":type
			},
			url : projectName+'/mem/feedBack/admin/list?v_date=' + new Date(),
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
 			frozenColumns : [[{
				field : 'id',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			},{
				title : '状态',
				field : 'state',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.state == 1) return "<font color=blue>待回复</font>";
					if(row.state == 2) return "<font color=green>已回复</font>";
					if(row.state == 3) return "<font color=red>已查看</font>";
					if(row.state == 4) return "<font color=red>反馈人已查看</font>";
				},
				width : 150
			}, {
				title : '标题',
				field : 'title',
				align:'center',
				formatter:function(value,row,index){
					var title = row.title;
					if(title != null && title != ''){
						if(title.length > 20) title = title.substring(0,20)+"...";
						return "<span title='"+row.title+"'>" + title +"</span>";
					}
				},
				width : 300
			}, {
				title : '提交时间',
				field : 'feedbackTime',
				align:'center',
				width : 150
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 150,
				formatter:function(value,row,index){
					var handleHtml = "";
					//if(row.state == 1 || row.state == 2){
						handleHtml += "<a href='javascript:void(0);' onclick='reply("+row.id+")'>回复</a>&nbsp;";
					//}
					handleHtml += "<a href='javascript:void(0);' onclick='view("+row.id+")'>查看</a>&nbsp;";
					return handleHtml;
				}
			} ] ]
		});
		
	});			
	
	//修改
	function reply(sid){
		win = $("<div></div>").dialog({
			title:'回复',
			width:800,
			height:600,
			maximizable:false,
			modal:true,
			href:projectName+'/mem/feedBack/admin/toReply?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#feedBackForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/feedBack/admin/reply',
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
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:800,
			height:600,
			maximizable:false,
			modal:true,
			href:projectName+'/mem/feedBack/admin/toView?id='+sid,
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
	
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#feedBackSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#feedBackSearchForm").form("reset");
	}
	
	