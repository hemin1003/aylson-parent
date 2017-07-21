	/**
	 * 帮助管理.js
	 */
	var datagrid;                          //员工管理
	var curUrl = "/sys/staff";             //当前接口链接
	var userType = $("#userType").val();   //当前用户类型
	$(function() { 
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
 				title : 'id',
 				field : 'id',
 				width : 50,
 				hidden : true
 			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 100,
				formatter:function(value,row,index){
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
					if(row.isDisable){
						handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",false)'>启用</a>&nbsp;";
					}else{
						handleHtml += "<a href='javascript:void(0);' onclick='changeState("+row.id+",true)'>禁用</a>&nbsp;";
					}
					return handleHtml;
				}
			}, {
				title : '姓名',
				field : 'staffName',
				align:'center',
				width : 150
			}, {
				title : '电话',
				field : 'staffPhone',
				align:'center',
				width : 150
			}, {
				title : '所属组织',
				field : 'source',
				align:'center',
				width : 150
			}, {
				title : '创建时间',
				field : 'createTime',
				align:'center',
				width : 150
			}, {
				title : '备注',
				field : 'remark',
				align:'center',
				formatter:function(value,row,index){
					var remark = row.remark;
					if(remark != null && remark != ''){
						if(remark.length > 25) remark = remark.substring(0,25)+"...";
						return "<span title='"+row.remark+"'>" + remark +"</span>";
					}
				},
				width : 350
			}] ],
			onBeforeLoad:function(){
				if(userType == 2){
					$("#datagrid").datagrid('hideColumn','source');
				}
			},
		});
		
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:800,
			height:550,
			modal:true,
			href:projectName + curUrl + '/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
	    	    	$("#staffForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName + curUrl + '/admin/add',
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
			width:800,
			height:550,
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
				    	$("#staffForm").form('submit',{
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
	
	function changeState(){
		
	}
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#staffSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#staffSearchForm").form("reset");
	}
	
	