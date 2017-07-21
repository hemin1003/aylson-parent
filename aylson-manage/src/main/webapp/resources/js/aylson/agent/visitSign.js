	/**
	 * 来访人数管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/agent/visitSign/admin/list?v_date=' + new Date(),
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
			}/*, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 150,
				formatter:function(value,row,index){
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
					handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
					return handleHtml;
				}
			}*/ , {
				title : '工程名称 ',
				field : 'projectName',
				align:'center',
				sortable:true,
				width : 100
			}, {
				title : '客户姓名 ',
				field : 'clientName',
				align:'center',
				sortable:true,
				width : 100
			}, {
				title : '客户电话',
				field : 'mobilePhone',
				align:'center',
				sortable:true,
				width : 100
			}]],
			columns : [[{
				title : '客户地址',
				field : 'address',
				align:'center',
				formatter:function(value,row,index){
					var address = row.province+row.city+row.area+row.address;
					var addresssub = row.province+row.city+row.area+row.address;
					if(addresssub != null && addresssub != ''){
						if(addresssub.length > 20) addresssub = addresssub.substring(0,20)+"...";
						return "<span title='"+address+"'>" + addresssub +"</span>";
					}
				},
				sortable:true,
				width : 300
			}, {
				title : '工程预算',
				field : 'budgetRange',
				align:'center',
				sortable:true,
				width : 100
			}, {
				title : '添加时间 ',
				field : 'createTime',
				align:'center',
				sortable:true,
				width : 150
			},{
				title : '来访人数',
				field : 'visitNum',
				align:'center',
				sortable:true,
				width : 80
			},{
				title : '备注',
				field : 'remark',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					var remark = row.remark;
					if(remark != null && remark != ''){
						if(remark.length > 25) remark = remark.substring(0,25)+"...";
						return "<span title='"+row.remark+"'>" + remark +"</span>";
					}
				},
				width : 350
			}] ]
		});
		
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:650,
			height:450,
			modal:true,
			href:projectName+'/agent/visitSign/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
				      $.messager.confirm("提示", "确定保存吗？保存后将不能再修改", function(r) {
				    	    if(r){
				    	    	$("#visitSignForm").form('submit',{
						    		 type:'POST',
						    		 url:projectName+'/agent/visitSign/admin/add',
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
				      })
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
			width:650,
			height:450,
			modal:true,
			href:projectName+'/agent/visitSign/admin/toEdit?id=' + sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#visitSignForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/agent/visitSign/admin/update',
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
					url:projectName+'/agent/visitSign/admin/deleteById?id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#visitSignSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#visitSignSearchForm").form("reset");
	}
	
	