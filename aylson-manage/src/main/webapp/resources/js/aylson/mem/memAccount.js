	/**
	 * 会员信息管理.js
	 */
	var datagrid;
	var isOnlyMy = $("#isOnlyMy").val();    //是否我的设计师
	var memberType = $("#memberType").val();//用户类型
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/mem/memAccount/admin/list?v_date=' + new Date(),
			queryParams:{
				'memberType':memberType,
				'isOnlyMy':isOnlyMy
			},
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
				width : 100,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.status == 0){
						handleHtml += "<a href='javascript:void(0);' onclick='updateStatus("+row.id+",1)'>审核通过</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
					}
					if(row.status == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='updateStatus("+row.id+",2)'>禁用</a>&nbsp;";
					}
					if(row.status == 2){
						handleHtml += "<a href='javascript:void(0);' onclick='updateStatus("+row.id+",1)'>启用</a>&nbsp;";
					}
					return handleHtml;
				}
			}, {
				title : '是否禁用',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 2)return "<font color=red>是</font>";
					else return "<font color=green>否</font>"
				},
				width : 80
			}, {
				title : '账号',
				field : 'accountName',
				align:'center',
				width : 100
			}, {
				title : '姓名',
				field : 'realName',
				align:'center',
				width : 100
			}, {
				title : '单位名称',
				field : 'companyName',
				align:'center',
				formatter:function(value,row,index){
					var companyName = row.companyName;
					if(companyName != null && companyName != ''){
						if(companyName.length > 15) companyName = companyName.substring(0,15)+"...";
						return "<span title='"+row.companyName+"'>" + companyName +"</span>";
					}
				},
				width : 200
			}]],
			columns:[[ {
				title : '移动电话',
				field : 'mobilePhone',
				align:'center',
				width : 100
			}, {
				title : '推荐人',
				field : 'referralName',
				align:'center',
				width : 100
			}, {
				title : '电子邮箱',
				field : 'email',
				align:'center',
				width : 100
			}, {
				title : 'qq号码',
				field : 'qq',
				align:'center',
				width : 80
			}, {
				title : '注册时间',
				field : 'registerTime',
				align:'center',
				width : 150
			}, {
				title : '地址',
				field : 'address',
				align:'center',
				formatter:function(value,row,index){
					var address = row.province+row.city+row.area+row.address;
					if(address != null && address != ''){
						if(address.length > 25) address = address.substring(0,25)+"...";
						return "<span title='"+row.address+"'>" + address +"</span>";
					}
				},
				width : 300
			}] ],
			onBeforeLoad:function(){
				if(memberType == 2){
					$("#datagrid").datagrid('hideColumn','companyName');
					$("#datagrid").datagrid('hideColumn','companyName');
					$("#datagrid").datagrid('hideColumn','email');
					$("#datagrid").datagrid('hideColumn','qq');
					$("#datagrid").datagrid('hideColumn','address');
				}
				if(isOnlyMy == 'true'){
					$("#datagrid").datagrid('hideColumn','opt');
				}
			}, 
		});
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:'90%',
			height:'90%',
			modal:true,
			href:projectName+'/mem/memAccount/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
		    		$("#memAccountForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/mem/memAccount/admin/add',
			    		 success:function(responseData){
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    					$.messager.confirm("提示", "继续新增吗？", function(r) {
										if (r) {

										}else{
											$("#datagrid").datagrid("reload");
											win.dialog('destroy');
										}
									})
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
			width:'90%',
			height:'90%',
			modal:true,
			href:projectName+'/mem/memAccount/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#memAccountForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/memAccount/admin/update',
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
	function updateStatus(sid,status){
		var tip = "确定审核通过吗？";
		if(status == 2){
			tip = "确定禁用该会员账号吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/mem/memAccount/admin/update?id=' + sid+"&status="+status,
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
	
	//删除
	function del(sid){
		$.messager.confirm("提示","确定删除此记录吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/mem/memAccount/admin/deleteById?id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#memAccountSearchForm")));
	}
	
	//重置
	function reset(){
		$("#memAccountSearchForm").form("reset");
	}
	
	