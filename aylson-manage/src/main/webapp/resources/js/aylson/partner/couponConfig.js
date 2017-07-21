	/**
	 * 优惠券配置管理.js
	 */
	var datagrid;                           //优惠券配置管理列表
	var curUrl = "/partner/couponConfig";   //当前接口链接
	
	$(function(){
		datagrid();
	})
	
	//列表
	function datagrid(){
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
			toolbar:[ {
				text:"添加",
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
				title:'操作',
				align:'center',
				width : 120,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.state == 0){
						handleHtml += "<a href='javascript:void(0);' title='上架' onclick='upDown("+row.id+",1)'>上架</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' title='修改' onclick='edit("+row.id+")'>修改</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' title='删除' onclick='del("+row.id+")'>删除</a>&nbsp;";
					}
					if(row.state == 1){
						handleHtml += "<a href='javascript:void(0);' title='上架' onclick='upDown("+row.id+",2)'>下架</a>&nbsp;";
					}
					return handleHtml;
				}
			}, {
				title : '状态',
				field : 'state',
				align:'center',
				width : 80,
				formatter:function(value,row,index){
					if(row.state == 0) return "<font color=red>新建</font>";
					if(row.state == 1) return "<font color=green>已上架</font>";
					if(row.state == 2) return "<font color=red>已下架</font>";
				}
			}, {
				title : '券名',
				field : 'couponName',
				align:'center',
				width : 150
			},{
				title : '券面值',
				field : 'couponValue',
				align:'center',
				sortable:false,
				width : 80
			},{
				title : '使用条件',
				field : 'achieveMoney',
				formatter:function(value,row,index){
					return "订单满足"+row.achieveMoney+"元可以使用";
				},
				align:'center',
				width : 250
			},{
				title : '有效天数',
				field : 'effectDays',
				formatter:function(value,row,index){
					if(row.effectDays == null || row.effectDays=='null' || row.effectDays=='')return "<font color=red>未定义</font>";
					return "申请通过后，"+row.effectDays+"天内可以使用";
				},
				align:'center',
				width : 180
			},{
				title : '备注',
				field : 'industryType',
				formatter:function(value,row,index){
					var remark = row.remark;
					if(remark != null && remark != ''){
						if(remark.length > 25) remark = remark.substring(0,25)+"...";
						return "<span title='"+row.remark+"'>" + remark +"</span>";
					}
				},
				align:'center',
				width : 300
			}]]
		});
	}

	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:500,
			height:350,
			maximizable:true,
			modal:true,
			href:projectName + curUrl+'/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
		    		$("#couponConfigForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName + curUrl + '/admin/add',
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
			width:500,
			height:350,
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
				    	$("#couponConfigForm").form('submit',{
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
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#couponConfigSearchForm")));
	}
	
	//重置搜索条件
	function reset(){
		$("#couponConfigSearchForm").form("reset");
	}
	
	//上架/下架
	function upDown(sid,state){
		var tip = "";
		if(state == 1){
			tip = "确定上架吗？";
		}else if(state == 2){
			tip = "确定下架吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					data:{
						'id':sid,
						'state':state
					},
					url:projectName + curUrl + '/admin/update',
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

	