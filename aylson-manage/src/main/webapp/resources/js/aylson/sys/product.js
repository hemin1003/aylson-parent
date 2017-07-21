	/**
	 * 产品展示管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/sys/product/admin/list?v_date=' + new Date(),
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'id',
			singleSelect:true,
			rownumbers: false,
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
				field : 'seq',
				title:'序号',
				align:'center',
				width : 50
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 120,
				formatter:function(value,row,index){
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
					handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
					handleHtml += "<a href='javascript:void(0);' onclick='view("+row.id+")'>查看</a>&nbsp;";
					return handleHtml;
				}
			}, {
				title : '产品类别',
				field : 'categoryName',
				align:'center',
				width : 150
			}, {
				title : '产品名称',
				field : 'productName',
				align:'center',
				width : 150
			}, {
				title : '适用范围',
				field : 'application',
				align:'center',
				formatter:function(value,row,index){
					var application = row.application;
					if(application != null && application != ''){
						if(application.length > 25) application = application.substring(0,25)+"...";
						return "<span title='"+row.application+"'>" + application +"</span>";
					}
				},
				width : 250
			}] ]
		});
		
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:900,
			height:790,
			modal:true,
			href:projectName+'/sys/product/admin/toAdd',
			onClose:function(){
				editor_productProp=null;
		    	editor_params=null;
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	if(isEditor){
			    		var html_productProp = editor_productProp.html();
			    		var html_params = editor_params.html();
			    		$("#productProp").val(html_productProp);
			    		$("#params").val(html_params);
			    	}
	    	    	$("#productForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/product/admin/add',
			    		 success:function(responseData){
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		editor_productProp=null;
			    			    	editor_params=null;
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
			 		editor_productProp=null;
			    	editor_params=null;
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
			height:790,
			maximizable:true,
			modal:true,
			href:projectName+'/sys/product/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    	editor_productProp=null;
		    	editor_params=null;
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	if(isEditor){
				    		var html_productProp = editor_productProp.html();
				    		var html_params = editor_params.html();
				    		$("#productProp").val(html_productProp);
				    		$("#params").val(html_params);
				    	}
				    	console.info($("#productForm"));
				    	console.info($("#productForm"));
				    	$("#productForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/sys/product/admin/update',
				    		 onSubmit: function(){
				    			 if(!isEditor){
				    				 $("#productProp").remove();
				    				 $("#params").remove();
				    			 }
				    		 },
				    		 success:function(responseData){
				    			 win.dialog('destroy');
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
				    			 		$("#datagrid").datagrid("reload");
				    			 		editor_productProp=null;
				    			    	editor_params=null;
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
				 		editor_productProp=null;
				    	editor_params=null;
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
					url:projectName+'/sys/product/admin/deleteById?id=' + sid,
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
	
	//查看
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:900,
			height:790,
			maximizable:true,
			modal:true,
			href:projectName+'/sys/product/admin/toView?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
						 text:'关闭',
					     iconCls:'icon-cancel',  
					 	 handler:function(){
					 		 win.dialog('destroy');
					 		editor_productProp=null;
	    			    	editor_params=null;
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
		$("#datagrid").datagrid("load",serializeObject($("#productSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#productSearchForm").form("reset");
	}
	
	