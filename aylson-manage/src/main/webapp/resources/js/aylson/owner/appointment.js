	/**
	 * 在线预约.js
	 */
	var datagrid;           //预约信息列表
	var datagridDesign;     //预约对应的设计信息列表
	var roleCode = $("#roleCode").val();
	
	$(function(){
		datagrid();
		datagridDesign(-1);
	})
	
	//预约单列表
	function datagrid(){
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/owner/appointment/admin/list?v_date=' + new Date(),
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
				text:"添加预约单",
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
				width : 250,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.state == 1){
						handleHtml += "<a href='javascript:void(0);' title='完善预约信息，并指派设计师' onclick='edit("+row.id+",2)'>预约处理</a>&nbsp;";
					}
					if(row.state == 2 ){
						if(roleCode == 'admin' || roleCode == 'homeDesigner'){
							handleHtml += "<a href='javascript:void(0);' title='提交至总部设计大样图，申请成功后将不能再对相关的设计信息表进行操作' onclick='applyDraw("+row.id+")'>提交去设计大样图</a>&nbsp;";
						}
					}
					if(row.state == 4 ){
						if(roleCode == 'admin' || roleCode == 'drawDesigner'){
							handleHtml += "<a href='javascript:void(0);' title='所有设计信息的大样图已经上传，提交到下一步进行确认' onclick='sumConfrimDraw("+row.id+")'>提交大样图确认</a>&nbsp;";
						}
					}
					if(row.state == 41){
						if(roleCode == 'admin' || roleCode == 'drawDesigner'){
							handleHtml += "<a href='javascript:void(0);' title='不满意的大样图已经重新上传，重新提交到下一步进行确认' onclick='sumConfrimDraw("+row.id+")'>重新提交大样图确认</a>&nbsp;";
						}
					}
					//if(row.state == 6 && row.isAgent){
					if(row.state == 6 ){
						if(roleCode == 'admin' || roleCode == 'quotationer' ){
							handleHtml += "<a href='javascript:void(0);' title='通知门店对订货报价单进行二次报价，以供客户确认' onclick='noticeAgentQuote("+row.id+")'>通知门店报价</a>&nbsp;";
						}
					}
					/*if(row.state == 6 && !row.isAgent){//直营
						if(roleCode == 'admin' || roleCode == 'quotationer'){
							handleHtml += "<a href='javascript:void(0);' title='通知客户对最后的报价进行确认' onclick='noticeConfirmQuote("+row.id+")'>通知客户确认</a>&nbsp;";
						}
					}*/
					//if(row.state == 7 && row.isAgent){//门店
					if(row.state == 7){//门店
						if(roleCode == 'admin' || roleCode == 'agent'){
							handleHtml += "<a href='javascript:void(0);' title='通知客户对最后的报价进行确认' onclick='noticeConfirmQuote("+row.id+")'>通知客户确认</a>&nbsp;";
						}
					}
					//if(row.state == 91 && !row.isAgent){//直营
					/*if(row.state == 91 && !row.isAgent){
						if(roleCode == 'admin' || roleCode == 'quotationer' ){
							handleHtml += "<a href='javascript:void(0);' title='重新通知客户对最后的报价进行确认' onclick='noticeConfirmQuote("+row.id+")'>重新通知客户确认</a>&nbsp;";
						}
					}*/
					if(row.state == 91){//门店
						if(roleCode == 'admin' || roleCode == 'agent' ){
							handleHtml += "<a href='javascript:void(0);' title='重新通知客户对最后的报价进行确认' onclick='noticeConfirmQuote("+row.id+")'>重新通知客户确认</a>&nbsp;";
						}
					}
					handleHtml += "<a href='javascript:void(0);' title='查看预约信息' onclick='view("+row.id+")'>查看</a>&nbsp;";
					return handleHtml;
				}
			}, {
				title : '流水单号',
				field : 'billCode',
				align:'center',
				width : 150
			}, {
				title : '更新时间',
				field : 'updateTime',
				align:'center',
				width : 150
			},{
				title : '状态',
				field : 'state',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.state == 1){return "<font color=red>新建</font>"};
					if(row.state == 2){return "<font color=red>已指派</font>"};
					if(row.state == 3){return "<font color=red>待添加设计信息表</font>"};
					if(row.state == 4){return "<font color=red>待设计大样图</font>"};
					if(row.state == 41){return "<font color=red>待重新设计大样图</font>"};
					if(row.state == 5){return "<font color=red>待确认大样图</font>"};
					if(row.state == 51){return "<font color=red>待重新确认大样图</font>"};
					if(row.state == 6){return "<font color=red>待报价</font>"};
					if(row.state == 61){return "<font color=red>aylson重新报价中</font>"};
					if(row.state == 7){return "<font color=red>门店报价中</font>"};
					if(row.state == 71){return "<font color=red>门店重新报价中</font>"};
					if(row.state == 8){return "<font color=red>客户确认中</font>"};
					if(row.state == 9){return "<font color=green>客户满意报价</font>"};
					if(row.state == 91){return "<font color=red>客户不满意报价</font>"};
					if(row.state == 10){return "<font color=green>客户确认订单</font>"};
				},
				width : 120
			}, {
				title : '预约人姓名',
				field : 'name',
				align:'center',
				width : 100
			}, {
				title : '预约人电话',
				field : 'mobilePhone',
				align:'center',
				width : 100
			},{
				title : '装修项目',
				field : 'decorateProject',
				align:'center',
				formatter:function(value,row,index){
					var decorateProject = row.decorateProject;
					if(decorateProject != null && decorateProject != ''){
						decorateProject = decorateProject.replace('1','门');
						decorateProject = decorateProject.replace('2','窗');
						decorateProject = decorateProject.replace('3','阳关房');
					}
					return decorateProject;
				},
				width : 100
			}, {
				title : '所在城市',
				field : 'province',
				align:'center',
				formatter:function(value,row,index){
					var address = row.province+row.city+row.area;
					if(address != null && address != ''){
						if(address.length > 25) address = address.substring(0,25)+"...";
						return "<span title='"+row.address+"'>" + address +"</span>";
					}
				},
				width : 150
			}]],
			columns:[[ {
				title : '预约地址',
				field : 'address',
				align:'center',
				formatter:function(value,row,index){
					var address = row.address;
					if(address != null && address != ''){
						if(address.length > 25) address = address.substring(0,25)+"...";
						return "<span title='"+row.address+"'>" + address +"</span>";
					}
				},
				width : 300
			},{
				title : '预约时间',
				field : 'appointDate',
				align:'center',
				width : 150
			}, {
				title : '上门设计师',
				field : 'designer',
				align:'center',
				width : 100
			},{
				title : '上门设计师电话',
				field : 'designerPhone',
				align:'center',
				width : 100
			}, {
				title : '说明',
				field : 'remark',
				align:'center',
				formatter:function(value,row,index){
					var remark = row.remark;
					if(remark != null && remark != ''){
						if(remark.length > 25) remark = remark.substring(0,25)+"...";
						return "<span title='"+row.remark+"'>" + remark +"</span>";
					}
				},
				width : 300
			} ] ],
			onSelect:function(index, data){
				var appointId = data.id;
				datagridDesign.datagrid('reload',{'appointId':appointId});
			}
		});
	}
	
	//添加预约单
	function add(){
		win = $("<div></div>").dialog({
			title:'添加预约单',
			width:750,
			height:450,
			maximizable:true,
			modal:true,
			href:projectName+'/owner/appointment/admin/toAdd',
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$.messager.confirm("提示","信息无误，确定保存吗？保存后将不能再修改",function(r){
				    		if(r){
				    			var winProgress = $.messager.progress({
				    				title:'请稍候',
				    				msg:'保存中...'
				    			});
						    	$("#appointmentForm").form('submit',{
						    		 type:'POST',
						    		 url:projectName+'/owner/appointment/admin/add',
						    		 success:function(responseData){
						    			 if(responseData){
						    				var data = $.parseJSON(responseData);
						    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						    			 	if(data.success){
						    			 		$.messager.progress('close');
						    			 		win.dialog('destroy');
						    			 		$("#datagrid").datagrid("reload");
						    				}
						    			 } 
						    		 }
						    	 });
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
	
	//修改预约单
	function edit(sid,state){
		win = $("<div></div>").dialog({
			title:'编辑',
			width:750,
			height:450,
			maximizable:true,
			modal:true,
			href:projectName+'/owner/appointment/admin/toEdit?id='+sid+'&state='+state,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$.messager.confirm("提示","信息无误，确定保存吗？",function(r){
				    		if(r){
						    	$("#appointmentForm").form('submit',{
						    		 type:'POST',
						    		 url:projectName+'/owner/appointment/admin/update',
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
	
	//查看预约信息
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:750,
			height:450,
			maximizable:true,
			modal:true,
			href:projectName+'/owner/appointment/admin/toView?id='+sid,
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
	
	//刷新预约单列表
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索预约单列表
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#appointmentSearchForm")));
	}
	
	//重置预约单搜索条件
	function reset(){
		$("#appointmentSearchForm").form("reset");
	}
	
	//设计信息列表
	function datagridDesign(appointId){
		datagridDesign = $('#datagridDesign').datagrid({
			method:'get',
			url : projectName+'/owner/design/admin/list?v_date=' + new Date(),
			pagination : false,
			queryParams:{'appointId':appointId},
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
				text:"<span title='添加门的设计信息表'>添加门<span>",
				iconCls : 'icon-add',
				handler : function(){
					addDesign(appointId,1);
				}
			},{
				text:"<span title='添加窗的设计信息表'>添加窗<span>",
				iconCls : 'icon-add',
				handler : function(){
					addDesign(appointId,2);
				}
			},{
				text:"<span title='添加阳光房的设计信息表'>添加阳光房<span>",
				iconCls : 'icon-add',
				handler : function(){
					addDesign(appointId,3);
				}
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
				width : 450,
				formatter:function(value,row,index){
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' title='查看设计信息表内容' onclick='viewDesign("+row.id+","+row.designType+")'>查看</a>&nbsp;";
					if(row.state <= 2){
						if(roleCode == 'admin' || roleCode == 'homeDesigner'){
							handleHtml += "<a href='javascript:void(0);' title='修改设计信息表内容' onclick='editDesign("+row.id+","+row.designType+")'>修改</a>&nbsp;";
							handleHtml += "<a href='javascript:void(0);' title='删除设计信息表内容' onclick='delDesign("+row.id+","+row.designType+")'>删除</a>&nbsp;";
						}
					}
					if(row.state == 3 || row.state == 4){
						if(roleCode == 'admin' || roleCode == 'drawDesigner'){
							handleHtml += "<a href='javascript:void(0);' title='上传对应设计信息表的大样图' onclick='uploadDraw("+row.id+")'>上传大样图</a>&nbsp;";
						}
					}
					if(row.state >= 4){
						handleHtml += "<a href='javascript:void(0);' title='查看对应设计信息表的大样图' onclick='viewDraw(\""+row.drawUrl+"\",\""+row.drawOpition+"\")'>查看大样图</a>&nbsp;";
					}
					if( row.state == 5 && (row.appointState == 5 || row.appointState == 41)){
						if(roleCode == 'admin' || roleCode == 'homeDesigner'){
							handleHtml += "<a href='javascript:void(0);' title='确认对应设计信息表的大样图' onclick='confirmDraw("+row.id+")'>确认大样图</a>&nbsp;";
						}
					}
					if( row.state == 51 && row.appointState == 51){
						if(roleCode == 'admin' || roleCode == 'homeDesigner'){
							handleHtml += "<a href='javascript:void(0);' title='重新确认对应设计信息表的大样图' onclick='confirmDraw("+row.id+")'>重新确认大样图</a>&nbsp;";
						}
					}
					if(row.state == 61 ){
						if(roleCode == 'admin' || roleCode == 'drawDesigner'){
							handleHtml += "<a href='javascript:void(0);' title='重新上传对应设计信息表的大样图' onclick='uploadDraw("+row.id+")'>重新上传大样图</a>&nbsp;";
						}
					}
					if(row.appointState == 6 && row.state == 6){
						if(roleCode == 'admin' || roleCode == 'quotationer'){
							handleHtml += "<a href='javascript:void(0);' title='添加订货单' onclick='addQuotation("+row.id+","+row.designType+","+row.appointId+")'>添加订货单</a>&nbsp;";
						}
					}
					if(row.appointState == 6 && row.state == 7){
						if(roleCode == 'admin' || roleCode == 'quotationer'){
							handleHtml += "<a href='javascript:void(0);' title='修改订货单' onclick='editQuotation("+row.id+","+row.designType+","+row.appointId+")'>修改订货单</a>&nbsp;";
						}
					}
					if(row.state == 7 || row.state == 71 || row.state == 8 || row.state == 81 ||
							row.state == 9 || row.state == 91 || row.state == 10 ){
						handleHtml += "<a href='javascript:void(0);' title='查看订货单' onclick='viewQuotation("+row.id+","+row.designType+","+row.appointId+")'>查看订货单</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' title='导出订货单' onclick='exportQuotation("+row.id+","+row.designType+","+row.appointId+")'>导出订货单</a>&nbsp;";
					}
					if(row.state == 91){
						if(roleCode == 'admin' || roleCode == 'quotationer'){
							handleHtml += "<a href='javascript:void(0);' title='修改订货单' onclick='editQuotation("+row.id+","+row.designType+","+row.appointId+")'>修改订货单</a>&nbsp;";
						}
					}
					if(row.appointState == 7 && row.state == 7 ){
						if(roleCode == 'admin' || roleCode == 'agent'){
							handleHtml += "<a href='javascript:void(0);' title='门店报价，客户会根据这个报价进行确认' onclick='agentQuote("+row.id+","+row.designType+","+row.appointId+")'>门店报价</a>&nbsp;";
						}
					}
					if(row.appointState == 7 && row.state == 8 ){
						if(roleCode == 'admin' || roleCode == 'agent'){
							handleHtml += "<a href='javascript:void(0);' title='门店报价，客户会根据这个报价进行确认' onclick='agentQuote("+row.id+","+row.designType+","+row.appointId+")'>门店报价</a>&nbsp;";
						}
					}
					if(row.appointState == 91 && row.state == 91){
						if(roleCode == 'admin' || roleCode == 'agent'){
							handleHtml += "<a href='javascript:void(0);' title='门店重新报价，客户会根据这个报价进行确认' onclick='agentQuote("+row.id+","+row.designType+","+row.appointId+")'>门店重新报价</a>&nbsp;";
						}
					}
				/*	if(row.appointState == 81 && row.state == 81 && row.byAgentUserId != null){
						if(roleCode == 'admin' || roleCode == 'agent'){
							handleHtml += "<a href='javascript:void(0);' title='门店重新报价，客户会根据这个报价进行确认' onclick='agentQuote("+row.id+","+row.designType+","+row.appointId+")'>门店重新报价</a>&nbsp;";
						}
					}*/
					return handleHtml;
				}
			}, {
				title : '流水单号',
				field : 'billCode',
				align:'center',
				width : 150
			},{
				title : '状态',
				field : 'state',
				align:'center',
				formatter:function(value,row,index){
					if(row.state == 1){return "<font color=red>新建</font>"};
					if(row.state == 2){return "<font color=green>已确定设计信息</font>"};
					if(row.state == 3){return "<font color=red>待上传大样图</font>"};
					if(row.state == 31){return "<font color=red>待重新上传大样图</font>"};
					if(row.state == 4){return "<font color=green>已上传大样图</font>"};
					if(row.state == 41){return "<font color=green>已重新上传大样图</font>"};
					if(row.state == 5){return "<font color=red>待确认大样图</font>"};
					if(row.state == 51){return "<font color=red>待重新确认大样图</font>"};
					if(row.state == 6){return "<font color=green>已确认大样图</font>"};
					if(row.state == 61){return "<font color=red>不满意大样图</font>"};
					if(row.state == 7){return "<font color=green>aylson已报价</font>"};
					if(row.state == 71){return "<font color=green>aylson已重新报价</font>"};
					if(row.state == 8){return "<font color=green>门店已报价</font>"};
					if(row.state == 81){return "<font color=green>门店已重新报价</font>"};
					if(row.state == 9){return "<font color=green>客户满意报价</font>"};
					if(row.state == 91){return "<font color=red>客户不满意报价</font>"};
					if(row.state == 10){return "<font color=green>客户确认下单</font>"};
				},
				sortable:true,
				width : 120
			},{
				title : '设计表类型',
				field : 'designType',
				align:'center',
				formatter:function(value,row,index){
					if(row.designType == 1){return "门"};
					if(row.designType == 2){return "窗"};
					if(row.designType == 3){return "阳光房"};
				},
				sortable:true,
				width : 100
			},{
				title : '下单时间',
				field : 'orderTime',
				align:'center',
				sortable:true,
				width : 150
			}]],
			columns:[[
				{
					title : '订货单下单时间',
					field : 'quoOrderTime',
					align:'center',
					width : 120
				},{
					title : '订单原价',
					field : 'orderAmount',
					align:'center',
					width : 100
				},{
					title : '实付金额',
					field : 'realAmount',
					align:'center',
					width : 100
				},{
					title : '优惠金额',
					field : 'benefitAmount',
					align:'center',
					width : 100
				},{
					title : '定金',
					field : 'deposit',
					align:'center',
					width : 100
				},{
					title : '销售金额',
					field : 'salesAmount',
					align:'center',
					width : 100
				},{
					title : '支付方式',
					field : 'payMode',
					align:'center',
					width : 100
				},{
					title : '交货时间',
					field : 'deliveryTime',
					align:'center',
					width : 100
				}
			]]
		});
	}
	
	//添加设计信息单
	function addDesign(id,designType){
		var row = $('#datagrid').datagrid('getSelected');
		if (row == null){
			$.messager.show({"title":"系统提示","msg":"请先选择一条预约信息","timeout":2000});
			return;
		}
		if(row.state != 2){
			$.messager.show({"title":"系统提示","msg":"不可再添加","timeout":2000});
			return;
		}
		if(roleCode != 'admin' && roleCode != 'homeDesigner'){
			$.messager.show({"title":"系统提示","msg":"您没有添加的权限，请联系管理员","timeout":2000});
			return;
		}
		var width = '100%';
		var height = '100%';
		if(designType == 3){
			width = '80%';
		}
		win_design = $("<div></div>").dialog({
			title:'设计信息表',
			width:width,
			height:height,
			maximizable:false,
			modal:true,
			href:projectName+'/owner/design/admin/toAddDesign?appointId='+ row.id +'&designType='+designType,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){ 
				    	//处理详情
				    	var queryParams = "";
				    	if(designType != 3){
				    		queryParams = JSON.stringify(getDesignDetialList());
				    		if(queryParams == "[]"){
				    			$.messager.show({"title":"系统提示","msg":"至少添加一条设计明细","timeout":1000});
				    			return;
				    		}
				    	}
//				    	$.messager.confirm("提示","请确认信息是否无误，保存后将不能再修改！！",function(r){
//				    		if(r){
				    			$("#designForm").form('submit',{
						    		 type:'POST',
						    		 queryParams:{"designDetailDWVoListJson":queryParams},
						    		 url:projectName+'/owner/design/admin/addDesign',
						    		 success:function(responseData){
						    			 win_design.dialog('destroy');
						    			 if(responseData){
						    				var data = $.parseJSON(responseData);
						    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						    			 	if(data.success){
						    			 		$('#datagridDesign').datagrid('reload',{'appointId':row.id});
						    				}
						    			 }
						    			 //是否立即提交下一步设计大样图
						    			 var nextNode = "是否立即提交下一步:<font color=red>设计大样图</font>";
						    			 $.messager.confirm("提示",nextNode,function(r){
						    				if(r){
						    					applyDraw(row.id);
						    				}else{
						    					$('#datagridDesign').datagrid('reload',{'appointId':row.id});
						    				} 
						    			 });
						    		 }
						    	 });
//				    		}
//				    	 })
				    	
				    }   
				   },{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		win_design.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//修改设计信息
	function editDesign(id,designType){
		var width = '100%';
		var height = '100%';
		if(designType == 3){
			width = '80%';
		}
		win_design = $("<div></div>").dialog({
			title:'设计信息表',
			width:width,
			height:height,
			//maximizable:false,
			modal:true,
			href:projectName+'/owner/design/admin/toEditDesign?designId='+id+'&designType='+designType,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){ 
				    	//处理详情
				    	var queryParams = "";
				    	if(designType != 3){
				    		queryParams = JSON.stringify(getDesignDetialList());
				    		if(queryParams == "[]"){
				    			$.messager.show({"title":"系统提示","msg":"至少添加一条设计明细","timeout":1000});
				    			return;
				    		}
				    	}
//				    	$.messager.confirm("提示","请确认信息是否无误，保存后将不能再修改！！",function(r){
//				    		if(r){
				    			$("#designForm").form('submit',{
						    		 type:'POST',
						    		 queryParams:{"designDetailDWVoListJson":queryParams},
						    		 url:projectName+'/owner/design/admin/editDesign',
						    		 success:function(responseData){
						    			 win_design.dialog('destroy');
						    			 if(responseData){
						    				var data = $.parseJSON(responseData);
						    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						    			 	if(data.success){
						    			 		$('#datagridDesign').datagrid('reload',{'appointId':id});
						    				}
						    			 }
						    			 //是否立即提交下一步设计大样图
						    			 var nextNode = "是否立即提交下一步:<font color=red>设计大样图</font>";
						    			 $.messager.confirm("提示",nextNode,function(r){
						    				if(r){
						    					applyDraw(id);
						    				}else{
						    					$('#datagridDesign').datagrid('reload',{'appointId':id});
						    				}  
						    			 });
						    		 }
						    	 });
//				    		}
//				    	 })
				    	
				    }   
				   },{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		win_design.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//获得门窗设计明细列表参数
	function getDesignDetialList(){
		var designDetailList = new Array();
		$(".detail_tr").each(function(index,obj){
    		var productNo = $(obj).find("input[name='productNo']").val();
    		var productName = $(obj).find("input[name='productName']").val();
    		var installLocation = $(obj).find("input[name='installLocation']").val();
    		var frameNum = $(obj).find("input[name='frameNum']").val();
    		var metalParts = $(obj).find("input[name='metalParts']").val();
    		var railsTypeD = $(obj).find("input[name='railsTypeD']").val();
    		var wallThick = $(obj).find("input[name='wallThick']").val();
    		//var holeSizeW = $(obj).find("input[name='holeSizeW']").val();
    		//var holeSizeH = $(obj).find("input[name='holeSizeH']").val();
    		var productSizeW = $(obj).find("input[name='productSizeW']").val();
    		var productSizeH = $(obj).find("input[name='productSizeH']").val();
    		var colorIn = $(obj).find("input[name='colorIn']").val();
    		var colorOut = $(obj).find("input[name='colorOut']").val();
    		var glass = $(obj).find("input[name='glass']").val();
    		var shutter = $(obj).find("input[name='shutter']").val();
    		var pack = $(obj).find("input[name='pack']").val();
    		var isHaveSafeSys = $(obj).find("input[name='isHaveSafeSys']").val();
    		var attachUrls = $(obj).find("input[name='attachUrls']").val();
    		var openFanNumW = $(obj).find("input[name='openFanNumW']").val();
    		var sillHeightW = $(obj).find("input[name='sillHeightW']").val();
    		designDetailList.push({ 
    			'productNo':productNo,
    			'productName':productName,
    			'installLocation':installLocation,
    			'frameNum':frameNum,
    			'metalParts':metalParts,
    			'railsTypeD':railsTypeD,
    			'wallThick':wallThick,
    			//'holeSizeW':holeSizeW,
    			//'holeSizeH':holeSizeH,
    			'productSizeW':productSizeW,
    			'productSizeH':productSizeH,
    			'colorIn':colorIn,
    			'colorOut':colorOut,
    			'glass':glass,
    			'shutter':shutter,
    			'pack':pack,
    			'isHaveSafeSys':isHaveSafeSys,
    			'attachUrls':attachUrls,
    			'openFanNumW':openFanNumW,
    			'sillHeightW':sillHeightW
    		})
    	})
    	return designDetailList;
	}
	
	//删除设计信息表
	function delDesign(sid,designType){
		$.messager.confirm("提示","确定删除此记录吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/owner/design/admin/delDesign?designId=' + sid+'&designType='+designType,
					dataType:"json",
					success:function(data){
						if(data){
		    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    				if(data.success){
		    					$("#datagridDesign").datagrid("reload");
		    				}
		    			 }
					}
				});
			}
		});
	}
	
	//查看设计信息表
	function viewDesign(id,designType){
		var width = '100%';
		var height = '100%';
		if(designType == 3){
			width = '80%';
		}
		win_design = $("<div></div>").dialog({
			title:'设计信息表',
			width:width,
			height:height,
			//maximizable:true,
			modal:true,
			href:projectName+'/owner/design/admin/toViewDesign?id='+id+'&designType='+designType,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
						text:'关闭',
					    iconCls:'icon-cancel',
					    handler:function(){ 
					    	win_design.dialog('destroy');
					    }   
				    }]
		});
	}
	
	//提交申请设计大样图
	function applyDraw(appointId){
		$.messager.confirm("提示","是否提交所有设计信息表到总部设计大样图，提交成功后，将不能再对相关的设计信息表进行操作！！",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/owner/design/admin/applyDraw?appointId=' + appointId,
					dataType:"json",
					success:function(data){
						if(data){
		    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    				if(data.success){
		    					$("#datagridDesign").datagrid("reload");
		    					$("#datagrid").datagrid("reload");
		    				}
		    			 }
					}
				});
			}
		});
	}
	
	//上传大样图
	function uploadDraw(designId){
		winDraw = $("<div></div>").dialog({
			title:'上传大样图',
			width:'80%',
			height:'80%',
			maximizable:true,
			modal:true,
			queryParams:{'designId':designId},
			href:projectName+'/owner/design/admin/toUploadDraw',
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[
					{
						 text:'上传大样图',
					    iconCls:'icon-add',  
						 handler:function(){
							 uploadDrawUrl();
						 }
					},
			        {
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){ 
				    	var drawUrl = $("#drawUrl").val();
				    	if(drawUrl == ''){
		    			 	$.messager.show({"title":"系统提示","msg":"请先上传大样图","timeout":1000});
		    			 	return;
				    	}
//						$.messager.confirm("提示","是否确认保存，保存成功后将不能再进行修改！！！",function(r){
//							if(r){
								$("#designForm").form('submit',{
						    		 type:'POST',
						    		 url:projectName+'/owner/design/admin/uploadDraw',
						    		 success:function(responseData){
						    			 if(responseData){
						    				var data = $.parseJSON(responseData);
						    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						    			 	if(data.success){
												$("#datagridDesign").datagrid("reload");
												winDraw.dialog('destroy');
						    				}
						    			 } 
						    		 }
						    	 });
//							}
//						})
				    }   
				   },{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		winDraw.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//查看大样图
	function viewDraw(drawUrl,drawOpition){
		$('#viewDrawDlg').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					$('#viewDrawDlg').dialog('close');
				}
			}]
		});
		$('#viewDrawDlg').dialog('open');
		var html = "";
		if(drawOpition != 'null' && drawOpition != ''){
			html += "<div style='margin:auto;width:75%;font-size:16px;text-align:left;'><table class='tableForm'><tr>";
			html += "<th width='100px'>不满意原因：</th>";
			html += "<td style='color:#ff0000;font-size:14px;'>";
			html += drawOpition;
			html += "</td></tr></table></div>";
		}
		html += "<img src='"+drawUrl+"' />";
		$('#viewDraw').html(html);
	}
	
	//确认大样图
	function confirmDraw(designId){
		confirmDraw_win = $("<div></div>").dialog({
			title:'确认大样图：对设计的大样图进行审核确认；<font color=red>如果不满意，请务必在最下面的【不满意原因】输入框填写原因</font>；如果满意，可以不写',
			width:'80%',
			height:'80%',
			resizable:true,
			modal:true,
			queryParams:{'designId':designId},
			href:projectName+'/owner/design/admin/toConfirmDraw',
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'满意',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#designForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/owner/design/admin/confirmDraw?state=6',
				    		 success:function(responseData){
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
				    			 		$("#datagrid").datagrid("reload");
										$("#datagridDesign").datagrid("reload");
										confirmDraw_win.dialog("destroy");
				    				}
				    			 } 
				    		 }
				    	 });
				     }   
				   },{
						text:'不满意',
					    iconCls:'icon-no',
					    handler:function(){
					    	var drawOpition = $("#drawOpition").textbox('getValue');
					    	if(drawOpition == ''){
			    			 	$.messager.show({"title":"系统提示","msg":"请填写不满意的原因，方便设计人员重新设计","timeout":1000});
					    		return;
					    	}
					    	$("#designForm").form('submit',{
					    		 type:'POST',
					    		 url:projectName+'/owner/design/admin/confirmDraw?state=61',
					    		 success:function(responseData){
					    			 if(responseData){
					    				var data = $.parseJSON(responseData);
					    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
					    			 	if(data.success){
					    			 		$("#datagrid").datagrid("reload");
											$("#datagridDesign").datagrid("reload");
											confirmDraw_win.dialog("destroy");
					    				}
					    			 } 
					    		 }
					    	 });
					    	
					    }   
					 },{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		confirmDraw_win.dialog("destroy");
				 	 }   
				  }]
		});
	}
	
	//提交相关设计信息表的大样图，进入下一步确认
	function sumConfrimDraw(appointId){
		$.messager.confirm("提示","已经提交相关设计信息表的大样图，提交到下一步进行确认吗！！",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/owner/appointment/admin/sumConfrimDraw?appointId=' + appointId,
					dataType:"json",
					success:function(data){
						if(data){
		    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    				if(data.success){
		    					$("#datagridDesign").datagrid("reload",{'appointId':appointId});
		    					$("#datagrid").datagrid("reload");
		    				}
		    			 }
					}
				});
			}
		});
	}
	
	//添加设计信息表明细
	function addDesignDetail(designType){
		var detailHtml = "";
		detailHtml += "<tr class='detail_tr'>";
		/*产品编号*/
		detailHtml += "<td><input  name='productNo' class='easyui-textbox productNoCss' /></td>";
		/*产品名称*/
		detailHtml += "<td><input  name='productName' required='true'	class='easyui-textbox productNameCss' /></td>";
		/*安装位置*/
		detailHtml += "<td><input  name='installLocation' class='easyui-textbox installLocationCss'/></td>";
		/*樘数*/
		detailHtml += "<td><input  name='frameNum' data-options='value:1,min:0' class='easyui-numberbox frameNumCss'/></td>";
	if(designType == 2){	
		/*（窗）开启扇数量*/
		detailHtml += "<td><input  name='openFanNumW' data-options='value:1,min:1' required='true'	class='easyui-numberbox openFanNumWCss' /></td>";
	}
		/*五金配件*/
		detailHtml += "<td><input  name='metalParts' class='easyui-textbox metalPartsCss'/></td>";
	if(designType == 1){
		/*（门）下轨类型*/
		//detailHtml += "<td><input  name='railsTypeD' class='easyui-textbox'/></td>";
		detailHtml += "<td>"
		detailHtml +=	"<select  class='comboboxCss railsTypeDCss' name='railsTypeD' style='width:55px' editable=false>"
		detailHtml +=		"	<option value='1'>平轨</option>"
		detailHtml +=		"	<option value='2'>低轨</option>"
		detailHtml +=		"	<option value='3'>铜轨</option>"
		detailHtml +=	"</select>"
		detailHtml += "</td>";
		
	}
		/*墙厚*/
		detailHtml += "<td><input  name='wallThick' data-options='required:true,min:1' class='easyui-numberbox wallThickCss'/></td>";
	if(designType == 2){	
		/*（窗）窗台高度*/
		detailHtml += "<td><input  name='sillHeightW' required='true'	class='easyui-numberbox sillHeightWCss' /></td>";
	}
		/*洞口尺寸-宽*/
		//detailHtml += "<td><input  name='holeSizeW' data-options='required:true,min:1' class='easyui-numberbox'/></td>";
		/*洞口尺寸-高*/
		//detailHtml += "<td><input  name='holeSizeH' data-options='required:true,min:1' class='easyui-numberbox'/></td>";
		/*产品尺寸-宽*/
		detailHtml += "<td><input  name='productSizeW' data-options='required:true,min:1' class='easyui-numberbox productSizeWCss'/></td>";
		/*产品尺寸-高*/
		detailHtml += "<td><input  name='productSizeH' data-options='required:true,min:1' class='easyui-numberbox productSizeHCss'/></td>";
		/*颜色-内*/
		detailHtml += "<td><input  name='colorIn' data-options='required:true' class='easyui-textbox colorInCss'/></td>";
		/*颜色-外*/
		detailHtml += "<td><input  name='colorOut' data-options='required:true' class='easyui-textbox colorOutCss'/></td>";
		/*玻璃*/
		detailHtml += "<td><input  name='glass' class='easyui-textbox glassCss'/></td>";
		/*百叶*/
		detailHtml += "<td>"
		detailHtml +=	"<select  class='comboboxCss shutterCss' name='shutter' style='width:50px' editable=false>"
		detailHtml +=		"	<option value='0'>无</option>"
		detailHtml +=		"	<option value='1'>电动</option>"
		detailHtml +=		"	<option value='2'>手动</option>"
		detailHtml +=	"</select>"
		detailHtml += "</td>";
		/*包套*/
		detailHtml += "<td>";
		detailHtml +=	"<select  class='comboboxCss packCss' name='pack' style='width:50px' editable=false>";
		detailHtml +=		"	<option value='0'>无</option>";
		detailHtml +=		"	<option value='1'>单包</option>";
		detailHtml +=		"	<option value='2'>双包</option>";
		detailHtml +=	"</select>";
		detailHtml += "</td>";
		/*安全系统*/
		detailHtml += "<td>";
		detailHtml +=	"<select  class='comboboxCss isHaveSafeSysCss' name='isHaveSafeSys' style='width:50px' editable=false>";
		detailHtml +=		"	<option value='false'>无</option>";
		detailHtml +=		"	<option value='true'>有</option>";
		detailHtml +=	"</select>";
		detailHtml += "</td>";
		detailHtml += "<td>";
		detailHtml += "<a href='#' class='buttonCss' style='width:40px;' onclick='delDesignDetail(this)'>删除</a> ";
		detailHtml += "<a href='#' class='buttonCss' style='width:60px;' onclick='uploadDetailDraw(this)'>上传附件</a> ";
		detailHtml += "<input type='hidden' name='attachUrls' value=''/> ";
		detailHtml += "<a href='#' class='buttonCss' style='width:60px;' onclick='viewDetailDraw(this)'>查看附件</a> ";
		detailHtml += "</td>";
		detailHtml += "</tr>";
		var curRowObj  = $(detailHtml);
		$("#remark_tr").before(curRowObj);
		$(".buttonCss").linkbutton();
		$(".buttonCss").addClass("c8");
		$(".easyui-textbox").textbox();
		$(".easyui-numberbox").numberbox();
		curRowObj.find(".comboboxCss").combobox({"width":55});
		//$(".buttonCss").addClass("c8");
	}
	
	//删除设计信息表明细
	function delDesignDetail(obj){
		$(obj).parent().parent().remove();
		orderAmountCount();
	}
	
	//查看设计明细的设计图附件
	function viewDetailDraw(obj){
		$('#viewDetailDrawDlg').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					$('#viewDetailDrawDlg').dialog('close');
				}
			}]
		});
		$('#viewDetailDrawDlg').dialog('open');
		var attachUrls = $(obj).prev().val();
		var html = "";
		if(attachUrls != null && attachUrls != ''){
			attachUrlArray = attachUrls.split("<;>");
			for(var i=0; i<attachUrlArray.length; i++){
				var imgUrl = attachUrlArray[i];
				if( imgUrl != null && imgUrl != ''){
					html += "<img src='"+imgUrl+"'  height='400px'/>";
				}
			}
		}
		$('#viewDetailDraw').html(html);
	}
	
	//上传设计明细的设计图附件
	function uploadDetailDraw(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'上传附件',
			width:500,
			height:200,
			modal:true,
			href:projectName+'/sys/fileHandle/toImgUpload',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	var filePath = $("#imgName").filebox('getValue');
			    	if(filePath == ''){
			    		$.messager.show({"title":"系统提示","msg":"请选择图片上传","timeout":1000});
			    		return;
			    	}
			    	var winTip = $.messager.progress({
						title:'请稍候...',
						msg:'正在上传...'
					});
			    	$("#bucket").val("dc-test");
			    	$("#imgUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/imgUpload',
			    		 //url:projectName+'/sys/fileHandle/uploadTest',
			    		 success:function(responseData){
			    			 $.messager.progress('close');
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		var fileName = data.data;
			    			 		var attachUrls = $(obj).next().val() + "<;>"+ fileName;
			    			 		$(obj).next().val(attachUrls)
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
	
	//添加订货报价单
	function addQuotation(designId,designType,appointId){
		winDesign = $("<div></div>").dialog({
			title:'订货报价单',
			width:'100%',
			height:'100%',
			//maximizable:true,
			modal:true,
			href:projectName+'/owner/quotation/admin/toAddQuotation?designId='+designId+'&designType='+designType,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){ 
				    	//处理详情
				    	var queryParams = "";
				    	if(designType != 3){
				    		queryParams = JSON.stringify(getQuotationDetialList());
				    		console.info(queryParams);
				    		if(queryParams == "[]"){
				    			$.messager.show({"title":"系统提示","msg":"至少添加一条订货单明细","timeout":1000});
				    			return;
				    		}
				    	}
//				    	console.info(getQuotationDetialList());return;
				    	
//				    	$.messager.confirm("提示","请确认信息是否无误，保存后将不能再修改！！",function(r){
//				    		if(r){
				    			$("#designForm").form('submit',{
						    		 type:'POST',
						    		 queryParams:{"quotationDetailDWVoListJson":queryParams},
						    		 url:projectName+'/owner/quotation/admin/addQuotation',
						    		 success:function(responseData){
				    					 winDesign.dialog('destroy');
						    			 if(responseData){
						    				var data = $.parseJSON(responseData);
						    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						    			 	if(data.success){
						    			 		$('#datagridDesign').datagrid('reload',{'appointId':appointId});
						    				}
						    			 } 
						    		 }
						    	 });
//				    		}
//				    	 })
				    	
				    }   
				   },{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		winDesign.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//修改订货报价单
	function editQuotation(designId,designType,appointId){
		winDesign = $("<div></div>").dialog({
			title:'修改订货报价单',
			width:'100%',
			height:'100%',
			//maximizable:true,
			modal:true,
			href:projectName+'/owner/quotation/admin/toEditQuotation?designId='+designId+'&designType='+designType,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){ 
				    	//处理详情
				    	var queryParams = "";
				    	if(designType != 3){
				    		queryParams = JSON.stringify(getQuotationDetialList());
				    		if(queryParams == "[]"){
				    			$.messager.show({"title":"系统提示","msg":"至少添加一条订货单明细","timeout":1000});
				    			return;
				    		}
				    	}
//				    	$.messager.confirm("提示","请确认信息是否无误，保存后将不能再修改！！",function(r){
//				    		if(r){
				    			$("#designForm").form('submit',{
						    		 type:'POST',
						    		 queryParams:{"quotationDetailDWVoListJson":queryParams},
						    		 url:projectName+'/owner/quotation/admin/editQuotation',
						    		 success:function(responseData){
				    					 winDesign.dialog('destroy');
						    			 if(responseData){
						    				var data = $.parseJSON(responseData);
						    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						    			 	if(data.success){
						    			 		$('#datagridDesign').datagrid('reload',{'appointId':appointId});
						    				}
						    			 } 
						    		 }
						    	 });
//				    		}
//				    	 })
				    	
				    }   
				   },{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		winDesign.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//门店报价
	function agentQuote(designId,designType,appointId){
		winDesign = $("<div></div>").dialog({
			title:'门店进行报价',
			width:'100%',
			height:'100%',
			//maximizable:true,
			modal:true,
			href:projectName+'/owner/quotation/admin/toAgentQuote?designId='+designId+'&designType='+designType,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){ 
				    	//处理详情
				    	var queryParams = "";
				    	if(designType != 3){
				    		queryParams = JSON.stringify(getQuotationDetialList());
				    		if(queryParams == "[]"){
				    			$.messager.show({"title":"系统提示","msg":"至少添加一条订货单明细","timeout":1000});
				    			return;
				    		}
				    	}
				    	//console.info(queryParams);return;
//				    	$.messager.confirm("提示","请确认信息是否无误，保存后将不能再修改！！",function(r){
//				    		if(r){
				    			$("#designForm").form('submit',{
						    		 type:'POST',
						    		 queryParams:{"quotationDetailDWVoListJson":queryParams},
						    		 url:projectName+'/owner/quotation/admin/agentQuote',
						    		 success:function(responseData){
				    					 winDesign.dialog('destroy');
						    			 if(responseData){
						    				var data = $.parseJSON(responseData);
						    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						    			 	if(data.success){
						    			 		$('#datagridDesign').datagrid('reload',{'appointId':appointId});
						    				}
						    			 } 
						    		 }
						    	 });
//				    		}
//				    	 })
				    	
				    }   
				   },{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		winDesign.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//添加门窗报价明细
	function addQuotationDetail(){
		var detailHtml = "";
		detailHtml += "<tr class='detail_tr'>";
		/*序号*/
		detailHtml += "<td><input  id='seq' class='easyui-numberbox seqCss' /></td>";
		/*生产编号*/
		detailHtml += "<td><input  id='productNo' required='true'	class='easyui-textbox productNoCss' /></td>";
		/*产品名称*/
		detailHtml += "<td><input  id='productName' class='easyui-textbox productNameCss'/></td>";
		/*洞口尺寸-宽*/
		//detailHtml += "<td><input  id='holeSizeW' data-options='required:true,min:1' class='easyui-numberbox'/></td>";
		/*洞口尺寸-高*/
		//detailHtml += "<td><input  id='holeSizeH' data-options='required:true,min:1' class='easyui-numberbox'/></td>";
		/*产品尺寸-宽*/
		detailHtml += "<td><input  id='productSizeW' data-options='required:true,min:1' class='easyui-numberbox productSizeWCss'/></td>";
		/*产品尺寸-高*/
		detailHtml += "<td><input  id='productSizeH' data-options='required:true,min:1' class='easyui-numberbox productSizeHCss'/></td>";
		/*墙厚*/
		detailHtml += "<td><input  id='wallThick' data-options='required:true,min:1' class='easyui-numberbox wallThickCss'/></td>";
		/*颜色-内*/
		detailHtml += "<td><input  id='colorIn' class='easyui-textbox colorInCss'/></td>";
		/*颜色-外*/
		detailHtml += "<td><input  id='colorOut' class='easyui-textbox colorOutCss'/></td>";
		/*玻璃/百叶*/
		detailHtml += "<td><input  id='glass' class='easyui-textbox glassCss'/></td>";
		/*下轨道*/
		detailHtml += "<td><input  id='rails' class='easyui-textbox railsCss'/></td>";
		/*执手/拉手颜色*/
		detailHtml += "<td><input  id='handColor' class='easyui-textbox handColorCss'/></td>";
		/*数量*/
		detailHtml += "<td><input  id='pruductNum' data-options='required:true,min:1' class='easyui-numberbox productNumCss'/></td>";
		/*面积*/
		detailHtml += "<td><input  id='areas' data-options='required:true,min:1' class='easyui-numberbox areasCss'/></td>";
		/*单价*/
		detailHtml += "<td><input  id='unitPrice' data-options='required:true,min:1' class='easyui-numberbox unitPriceCss'/></td>";
		/*配件金额*/
		//detailHtml += "<td><input  id='goodsAmount' data-options='required:true,min:1' class='easyui-numberbox'/></td>";
		detailHtml += "<td>";
		detailHtml += "		<a href='#' id='goodsAmount_a' onclick='editGoodAmountDetail(this)' >0.00</a>";
		detailHtml += "		<input value='' id='goodsAmount'  type='hidden' />";
		detailHtml += "		<input value='' id='goodNames'  type='hidden' />";
		detailHtml += "		<input value='' id='goodAmounts'  type='hidden' />";
		detailHtml += "</td>";
		/*金额*/
		detailHtml += "<td><input  id='amount' data-options='readonly:true,precision:2,required:true,min:1' class='easyui-numberbox amountCss'/></td>";
		/*备注*/
		detailHtml += "<td><input  id='remark' data-options='' class='easyui-textbox remarkCss'/></td>";
		/*销售单价*/
		//detailHtml += "<td><input  name='salesUnitPrice' data-options='readonly:true,required:false,min:1' class='easyui-numberbox salesUnitPriceCss'/></td>";
		/*销售金额*/
		//detailHtml += "<td><input  name='salesUnitAmount' data-options='readonly:true,precision:2,required:false,min:1' class='easyui-numberbox salesUnitAmountCss'/></td>";
		
		detailHtml += "<td>";
		detailHtml += "<a href='#' class='buttonCss' onclick='delDesignDetail(this)'>删除</a>&nbsp;";
		detailHtml += "</td>";
		detailHtml += "</tr>";
		var curRowObj  = $(detailHtml);
		$("#remark_tr").before(curRowObj);
		$(".buttonCss").linkbutton();
		$(".buttonCss").addClass("c8");
		$(".easyui-textbox").textbox();
		$(".easyui-numberbox").numberbox();
		 $('.productNumCss').numberbox({
			 //"precision":2, 
			 onChange:function(newValue,oldValue){
				  var unitPriceValue = $(this).parent().parent().find(".unitPriceCss").numberbox('getValue');
				  var areasValue = $(this).parent().parent().find(".areasCss").numberbox('getValue');
				  var unitAmount = 0.00;
				  if(unitPriceValue != null && unitPriceValue != '' 
					  && areasValue != null && areasValue != ''){
				  	unitAmount = newValue*unitPriceValue*areasValue;
				  	$(this).parent().parent().find(".amountCss").numberbox('setValue',unitAmount.toFixed(2));
				  	orderAmountCount();
				  }
			   }
		   })
		   $('.unitPriceCss').numberbox({
			   "precision":2, 
			   onChange:function(newValue,oldValue){
				  var productNumValue = $(this).parent().parent().find(".productNumCss").numberbox('getValue');
				  var areasValue = $(this).parent().parent().find(".areasCss").numberbox('getValue');
				  var unitAmount = 0.00;
				  if(productNumValue != null && productNumValue != ''
					  && areasValue != null && areasValue != ''){
				  	unitAmount = newValue*productNumValue*areasValue;
				  	$(this).parent().parent().find(".amountCss").numberbox('setValue',unitAmount.toFixed(2));
				  	orderAmountCount();
				  }
			   }
		   })
		    $('.areasCss').numberbox({
		    	"precision":2, 
		    	onChange:function(newValue,oldValue){
				  var unitPriceValue = $(this).parent().parent().find(".unitPriceCss").numberbox('getValue');
				  var productNumValue = $(this).parent().parent().find(".productNumCss").numberbox('getValue');
				  var unitAmount = 0.00;
				  if(unitPriceValue != null && unitPriceValue != ''
					  && productNumValue != null && productNumValue != ''){
				  	unitAmount = newValue*unitPriceValue*productNumValue;
				  	$(this).parent().parent().find(".amountCss").numberbox('setValue',unitAmount.toFixed(2));
				  	orderAmountCount();
				  }
			   }
		   })
		//curRowObj.find(".comboboxCss").combobox();
	}
	
	//获得订货单明细列表
	function getQuotationDetialList(){
		var quotationDetialList = new Array();
		$(".detail_tr").each(function(index,obj){
    		var seq = $(obj).find("input[id='seq']").val();
    		var productNo = $(obj).find("input[id='productNo']").val();
    		var productName = $(obj).find("input[id='productName']").val();
    		//var holeSizeW = $(obj).find("input[id='holeSizeW']").val();
    		//var holeSizeH = $(obj).find("input[id='holeSizeH']").val();
    		var productSizeW = $(obj).find("input[id='productSizeW']").val();
    		var productSizeH = $(obj).find("input[id='productSizeH']").val();
    		var wallThick = $(obj).find("input[id='wallThick']").val();
    		var colorIn = $(obj).find("input[id='colorIn']").val();
    		var colorOut = $(obj).find("input[id='colorOut']").val();
    		var glass = $(obj).find("input[id='glass']").val();
    		//var shutter = $(obj).find("input[id='shutter']").val();
    		var pruductNum = $(obj).find("input[id='pruductNum']").val();
    		var areas = $(obj).find("input[id='areas']").val();
    		var unitPrice = $(obj).find("input[id='unitPrice']").val();
    		var amount = $(obj).find("input[id='amount']").val();
    		var rails = $(obj).find("input[id='rails']").val();
    		var handColor = $(obj).find("input[id='handColor']").val();
    		var salesUnitPrice = $(obj).find("input[id='salesUnitPrice']").val();
    		var salesUnitAmount = $(obj).find("input[id='salesUnitAmount']").val();
    		var goodsAmount = $(obj).find("input[id='goodsAmount']").val();
    		var remark = $(obj).find("input[id='remark']").val();
    		var goodNames = $(obj).find("input[id='goodNames']").val();
    		var goodAmounts = $(obj).find("input[id='goodAmounts']").val();
    		quotationDetialList.push({ 
    			'seq':seq,
    			'productNo':productNo,
    			'productName':productName,
    			//'holeSizeW':'',
    			//'holeSizeH':'',
    			'productSizeW':productSizeW,
    			'productSizeH':productSizeH,
    			'wallThick':wallThick,
    			'colorIn':colorIn,
    			'colorOut':colorOut,
    			'glass':glass,
    			//'shutter':'',
    			'pruductNum':pruductNum,
    			'areas':areas,
    			'unitPrice':unitPrice,
    			'amount':amount,
    			'rails':rails,
    			'handColor':handColor,
    			'salesUnitPrice':salesUnitPrice,
    			'salesUnitAmount':salesUnitAmount,
    			'goodsAmount':goodsAmount,
    			'goodNames':goodNames,
    			'goodAmounts':goodAmounts,
    			'remark':remark
    		})
    	})
    	return quotationDetialList;
	}
	
	//查看订货单
	function viewQuotation(designId,designType,appointId){
		var width = '100%';
		var height = '100%';
		win_design = $("<div></div>").dialog({
			title:'查看订货单',
			width:width,
			height:height,
			maximizable:true,
			modal:true,
			href:projectName+'/owner/quotation/admin/toViewQuotation?designId='+designId+'&designType='+designType+'&appointId='+appointId,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
						text:'关闭',
					    iconCls:'icon-cancel',
					    handler:function(){ 
					    	win_design.dialog('destroy');
					    }   
				    }]
		});
	}
	
	//通知门店进行报价
	function noticeAgentQuote(appointId){
		$.messager.confirm("提示","已经完成相关的报价，确定提交到客户确认报价环节吗！！",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/owner/appointment/admin/noticeAgentQuote?appointId=' + appointId,
					dataType:"json",
					success:function(data){
						if(data){
		    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    				if(data.success){
		    					$("#datagridDesign").datagrid("reload");
		    					$("#datagrid").datagrid("reload");
		    				}
		    			 }
					}
				});
			}
		});
	}
	
	//通知客户确认报价
	function noticeConfirmQuote(appointId){
		$.messager.confirm("提示","已经完成相关的报价，确定提交到客户确认报价环节吗！！",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/owner/appointment/admin/noticeConfirmQuote?appointId=' + appointId,
					dataType:"json",
					success:function(data){
						if(data){
		    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    				if(data.success){
		    					$("#datagridDesign").datagrid("reload");
		    					$("#datagrid").datagrid("reload");
		    				}
		    			 }
					}
				});
			}
		});
	}
	
	//计算合计金额
    function orderAmountCount(){
	   var obj = $(".amountCss");
	   var total = 0.00;
	   $.each(obj, function(i,e){ 
			var curValue = $(e).numberbox('getValue');
			if(curValue != ''){
				total += parseFloat(curValue);
			}
		}); 
		$("#orderAmount").numberbox('setValue',total.toFixed(2)); 
		realAmountCount(total,null);
    }
    
    //计算折后金额
    function realAmountCount(orderAmount,benefitAmount){
    	//console.info(orderAmount);
    	//console.info(benefitAmount);
    	var realAmount = 0;
    	if(orderAmount == null || orderAmount == ''){
    		orderAmount = $("#orderAmount").numberbox('getValue');
    	}
    	if(benefitAmount == null || benefitAmount == ''){
    		benefitAmount = $("#benefitAmount").numberbox('getValue');
    	}
    	//console.info(orderAmount);
    	//console.info(benefitAmount);
    	if(parseFloat(orderAmount) < parseFloat(benefitAmount)){
			$.messager.show({"title":"系统提示","msg":"优惠金额不能超过订单金额","timeout":1000});
			benefitAmount = 0;
			$("#benefitAmount").numberbox('setValue',0);
    	}
    	realAmount = (orderAmount-benefitAmount).toFixed(2);
    	$("#realAmount").numberbox('setValue',realAmount); 
//    	remainAmountCount(realAmount,null);
    	remainAmountCount(realAmount);
    	return realAmount;
    }
    
    //计算:出厂前应付金额
    function remainAmountCount(realAmount){
    	var remainAmount = 0;
    	if(realAmount == null || realAmount == ''){
    		realAmount = $("#realAmount").numberbox('getValue');
    	}
    	/*if(deposit == null || deposit == ''){
    		deposit = $("#deposit").numberbox('getValue');
    	}*/
    	remainAmount = (realAmount/2).toFixed(2);
    	//$("#remainAmount").html(remainAmount);
    	$("#remainAmount").numberbox('setValue',remainAmount);
    	return remainAmount;
    }
    
    //计算销售金额
    function salesAmountCount(){
		 var obj = $(".salesUnitAmountCss");
		 var total = 0.00;
		 var deposit = 0.00;
		 $.each(obj, function(i,e){ 
				var curValue = $(e).numberbox('getValue');
				if(curValue != ''){
					total += parseFloat(curValue);
				}
		 }); 
		 deposit = parseFloat(total/2);
		 $("#salesAmount").numberbox('setValue',total.toFixed(2)); 
		 $("#deposit").numberbox('setValue',deposit.toFixed(2)); 
	}
  
    //编辑配件金额明细
	function editGoodAmountDetail(curObj){
		$("#goodAmountTable_head").siblings("tr").remove(); 
		var goodNames = $(curObj).siblings("[id = 'goodNames']").val();
		var goodAmounts = $(curObj).siblings("[id = 'goodAmounts']").val();
		if(goodNames != ''){
			var goodNamesArray = goodNames.split("|");
			var goodAmountsArray = goodAmounts.split("|");
			//console.info(goodNamesArray);
			var html = "";
			for(var i=0; i<goodNamesArray.length; i++){
				//console.info(goodNamesArray[i]);
				//console.info(goodAmountsArray[i]);
				html += "<tr>";
				html += "	<td><input class='goodName' value='"+ goodNamesArray[i] + "' style='width: 120px;' /></td>";
				html += "	<td><input class='goodAmount' value='"+ goodAmountsArray[i] + "'  style='width: 120px;'/></td>";
				html += "	<td><a href='#' class='goodAmoutbutton' onclick='delGoodAmountDetai(this)'>删除</a></td>";
				html += "</tr>";
			}
			if(html != ""){
				$("#goodAmountTable_head").after(html);
				$(".goodAmoutbutton").linkbutton();
				$(".goodAmoutbutton").addClass("c8");
				$(".goodName").textbox({
					"required":true
				});
				$(".goodAmount").numberbox({
					"required":true,
					"precision":2,
					"min":0
				});
			}
		}else{
			addGoodAmountDetai()
		}
		
		$('#goodAmountDlg').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					//goodAmountForm
					//$('#goodAmountDlg').dialog('close');
					//获取配件明细
					var result = $("#goodAmountForm").form("validate");
					if(!result)return;
					var goodsAmount = 0.0;
					var goodNames = "";
					var goodAmounts = "";
					var obj = $(".goodName");
				    $.each(obj, function(i,e){ 
				    	var goodAmount = $(e).parent().next().find(".goodAmount").numberbox('getValue');
				    	//console.info(goodAmount);
						var goodName = $(e).textbox('getValue');
						//console.info(goodName);
						goodNames +=  goodName+"|";
						goodAmounts +=  goodAmount+"|";
						if(goodAmount != ''){
							goodsAmount += parseFloat(goodAmount);
						}
				    }); 
				    if(goodNames != ""){
				    	goodNames = goodNames.substring(0,goodNames.length-1);
				    }
				    if(goodAmounts != ""){
				    	goodAmounts = goodAmounts.substring(0,goodAmounts.length-1);
				    }
					$(curObj).siblings("[id = 'goodsAmount']").val(goodsAmount.toFixed(2));
					$(curObj).siblings("[id = 'goodNames']").val(goodNames);
					$(curObj).siblings("[id = 'goodAmounts']").val(goodAmounts);
					$(curObj).html(goodsAmount.toFixed(2));
					var unitPriceValue = $(curObj).parent().parent().find(".unitPriceCss").numberbox('getValue');
					var productNumValue = $(curObj).parent().parent().find(".productNumCss").numberbox('getValue');
					var areasValue = $(curObj).parent().parent().find(".areasCss").numberbox('getValue');
					var unitAmount = 0.00;
					var otherValue = 0.00;
					if(productNumValue != null && productNumValue != ''
						  && areasValue != null && areasValue != ''
							  && unitPriceValue != null && unitPriceValue != ''){
						otherValue = parseFloat(unitPriceValue*productNumValue*areasValue);
					}
					unitAmount = parseFloat(goodsAmount+otherValue);
					$(curObj).parent().parent().find(".amountCss").numberbox('setValue',unitAmount.toFixed(2));
					orderAmountCount();
					$('#goodAmountDlg').dialog('close');
				}
			}]
		});
		$('#goodAmountDlg').dialog('open');
	}
	
	//查看配件金额明细
	function viewGoodAmountDetail(curObj){
		$("#goodAmountTable_head").siblings("tr").remove(); 
		$("#goodAmountTable_head_a").parent().remove(); 
		var goodNames = $(curObj).siblings("[id = 'goodNames']").val();
		var goodAmounts = $(curObj).siblings("[id = 'goodAmounts']").val();
		if(goodNames != ''){
			var goodNamesArray = goodNames.split("|");
			var goodAmountsArray = goodAmounts.split("|");
			var html = "";
			for(var i=0; i<goodNamesArray.length; i++){
				console.info(goodNamesArray[i]);
				console.info(goodAmountsArray[i]);
				html += "<tr>";
				html += "	<td><input class='goodName' value='"+ goodNamesArray[i] + "' style='width: 120px;' /></td>";
				html += "	<td><input class='goodAmount' value='"+ goodAmountsArray[i] + "'  style='width: 120px;'/></td>";
				//html += "	<td>---</td>";
				html += "</tr>";
			}
			if(html != ""){
				$("#goodAmountTable_head").after(html);
				$(".goodAmoutbutton").linkbutton();
				$(".goodAmoutbutton").addClass("c8");
				$(".goodName").textbox({
					"required":true
				});
				$(".goodAmount").numberbox({
					"required":true,
					"precision":2,
					"min":0
				});
			}
		}
		$('#goodAmountDlg').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					$('#goodAmountDlg').dialog('close');
				}
			}]
		});
		$('#goodAmountDlg').dialog('open');
	}
	
	//添加订货单备注
	function addQuotationRemark(){
		$('#quotationRemarkDlg').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					//获取备注内容
					var quotationRemark = $("#quotationDlgRamark").textbox('getValue');
					$("#quotationRemark").val(quotationRemark);
					$('#quotationRemarkDlg').dialog('close');
				}
			}]
		});
		$('#quotationRemarkDlg').dialog('open');
	}
	
	//查看订货单备注
	function viewQuotationRemark(){
		$('#quotationRemarkDlg').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					//获取备注内容
					$('#quotationRemarkDlg').dialog('close');
				}
			}]
		});
		var quotationRemark = $("#quotationRemark").val();
		$("#quotationDlgRamark").textbox('setValue',quotationRemark);
		$('#quotationRemarkDlg').dialog('open');
	}
	
	//导出订货单
	function exportQuotation(designId,designType,appointId){
		window.location = projectName+'/owner/quotation/admin/exportQuotation?designId='+designId+'&designType='+designType+'&appointId='+appointId;
	}