	/**
	 * 业主订单管理.js
	 */
	var datagrid;
	//var datagridCoupon
	var win_appoint;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/owner/order/admin/list?v_date=' + new Date(),
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
				field : 'seq',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 120,
				formatter:function(value,row,index){
					var handleHtml = "";
					var updateState = 1;
					/*if(row.state < 2){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+",2)'>完成生产</a>&nbsp";
					}
					if(row.state < 3){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+",3)'>入库中</a>&nbsp";
					}
					if(row.state < 4){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+",4)'>产品出库</a>&nbsp";
					}
					if(row.state < 5){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+",5)'>发货</a>&nbsp";
					}*/
					if(row.state < 5){
						handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp";
					}
					if(!row.isSettle){
						handleHtml += "<a href='javascript:void(0);' onclick='settle("+row.id+","+row.sourceType+")'>结算</a>&nbsp";
					}
					//handleHtml += "<a href='javascript:void(0);' onclick='view(" +row.id + ")'>查看</a>&nbsp";
					return handleHtml;
				}
			}, {
				title : '是否结算',
				field : 'isSettle',
				align:'center',
				formatter:function(value,row,index){
					if(row.isSettle){
						return "<font color=green>是</font>";
					}else{
						return "<font color=red>否</font>";
					}
				},
				width : 80
			}, {
				title : '状态',
				field : 'state',
				align:'center',
				formatter:function(value,row,index){
					if(row.state == 1) return "<font color=red>确认订单</font>";
					if(row.state == 2) return "<font color=red>生产中</font>";
					if(row.state == 3) return "<font color=red>产品入库</font>";
					if(row.state == 4) return "<font color=red>已发货</font>";
					if(row.state == 5) return "<font color=green>客户已收货</font>";
				},
				width : 100
			},{
				title : '订单来源',
				field : 'sourceType',
				align:'center',
				formatter:function(value,row,index){
					if(row.sourceType == 1){
						return "<font color=Purple>订货单下单</font>";
					}else if(row.sourceType == 2){
						return "<font color=GoldenRod>后台下单</font>";
					}
				},
				sortable:true,
				width : 80
			},{
				title : '订单类型',
				field : 'designType',
				align:'center',
				formatter:function(value,row,index){
					if(row.designType == 1){return "门"};
					if(row.designType == 2){return "窗"};
					if(row.designType == 3){return "阳光房"};
				},
				sortable:true,
				width : 80
			}, {
				title : '客户姓名',
				field : 'name',
				align:'center',
				width : 100
			}, {
				title : '客户手机',
				field : 'mobilePhone',
				align:'center',
				width : 100
			}, {
				title : '订单号',
				field : 'orderNo',
				align:'center',
				width : 150
			}, {
				title : '销售金额',
				field : 'salesAmount',
				formatter:function(value,row,index){
					if(row.sourceType == 1){
						return row.quoSalesAmount;
					}else if(row.sourceType == 2){
						return row.salesAmount;
					}
				},
				align:'center',
				width : 80
			}]],
			columns : [[{
				title : '下单时间',
				field : 'createTime',
				align:'center',
				width : 150
			}, {
				title : '更新时间',
				field : 'updateTime',
				align:'center',
				width : 150
			}, {
				title : '结算时间',
				field : 'settleTime',
				align:'center',
				width : 150
			}, {
				title : '预约单号',
				field : 'appointNo',
				formatter:function(value,row,index){
					if(row.appointId != 0 && row.appointId != null && row.appointId != ''){
						return "<a href='javascript:void(0);' title='查看对应的预约信息' onclick='appointInfo("+row.id+")'>"+row.appointNo+"</a>&nbsp"
					}else{
						return "--";
					}
				},
				align:'center',
				width : 150
			}, {
				title : '设计单号',
				field : 'designNo',
				formatter:function(value,row,index){
					if(row.designId != 0 && row.designId != null && row.designId != ''){
						return "<a href='javascript:void(0);' title='查看对应的预约信息' onclick='appointInfo("+row.id+")'>"+row.designNo+"</a>&nbsp"
					}else{
						return "--";
					}
				},
				align:'center',
				width : 150
			}/*, {
				title : '备注',
				field : 'question',
				align:'center',
				formatter:function(value,row,index){
					var remark = row.remark;
					if(remark != null && remark != ''){
						if(remark.length > 25) remark = remark.substring(0,25)+"...";
						return "<span title='"+row.remark+"'>" + remark +"</span>";
					}
				},
				width : 300
			}*/] ]
		});
		
	});			
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:750,
			height:450,
			modal:true,
			href:projectName+'/owner/order/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
	    	    	$("#orderForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/owner/order/admin/add',
			    		 success:function(responseData){
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
									$("#datagrid").datagrid("reload");
									win.dialog('destroy');
									//是否立即结算
									$.messager.confirm("提示","是否立即结算？",function(r){
										if(r){
											settle(data.data.id,data.data.sourceType);
										}
									});
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
			height:600,
			maximizable:true,
			modal:true,
			href:projectName+'/owner/order/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#orderForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/owner/order/admin/update',
				    		 success:function(responseData){
				    			 win.dialog('destroy');
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
				    			 		$("#datagrid").datagrid("reload");
				    			 		if(win_appoint != undefined){
				    			 			win_appoint.dialog('destroy');
				    			 		}
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
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:800,
			height:600,
			maximizable:true,
			modal:true,
			href:projectName+'/owner/order/admin/toView?id='+sid,
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
	//删除
	function del(sid){
		$.messager.confirm("提示","确定删除此记录吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/owner/order/admin/deleteById?id=' + sid,
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
	//查看预约信息
	function appointInfo(orderId){
		win_appoint = $("<div id='appointInfo'></div>").dialog({
			title:'查看预约信息',
			width:'80%',
			height:'80%',
			maximizable:true,
			modal:true,
			href:projectName+'/owner/order/admin/appointInfo?orderId='+orderId,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[
			{
				 text:'关闭',
			    iconCls:'icon-cancel',  
				 handler:function(){
					 win_appoint.dialog('destroy');
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
		$("#datagrid").datagrid("load",serializeObject($("#orderSearchForm")));
	}
	//重置
	function reset(){
		$("#orderSearchForm").form("reset");
	}
	//结算
	function settle(sid,sourceType){
		win = $("<div></div>").dialog({
			title:'编辑',
			width:800,
			height:600,
			maximizable:true,
			modal:true,
			href:projectName+'/owner/order/admin/toSettle?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定结算',
				    iconCls:'icon-ok',
				    handler:function(){
				    	/*$("#orderForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/owner/order/admin/settle',
				    		 success:function(responseData){
				    			 win.dialog('destroy');
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
				    			 		$("#datagrid").datagrid("reload");
				    			 		if(win_appoint != undefined){
				    			 			win_appoint.dialog('destroy');
				    			 		}
				    				}
				    			 } 
				    		 }
				    	 });*/
				    	var couponIds = $("#couponIds").val();
				    	//alert(sid)
				    	//alert(couponIds)
				    	$.messager.confirm("提示","信息无误，确认结算吗？",function(r){
							if(r){
								$.ajax({
									type:"POST",
									data:{
										"id":sid,
										"sourceType":sourceType,
										"couponIds":couponIds
									},
									url:projectName+'/owner/order/admin/settle',
									dataType:"json",
									success:function(data){
										if(data){
						    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						    				if(data.success){
						    					$("#datagrid").datagrid("reload");
						    					win.dialog('destroy');
						    				}else{
						    					 $("#coupons").html("");
						    					 $("#showTip").html("");
						    					 countCouponValueSum(0);
						    					 countSettleAmount(0);
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
	//优惠券列表
	function datagridCoupon(ownerPhone){
		var datagridCoupon = $('#datagridCoupon').datagrid({
			method:'get',
			url : projectName +  '/partner/coupon/getList?v_date=' + new Date(),
			pagination : false,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			queryParams: {
				ownerPhone: ownerPhone,
				"sort":"partner_coupon.state"
			},
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
				field : 'ck',
				formatter:function(value,row,index){
					if(row.state == 0){
						return '<input id='+row.id+' title='+row.couponCode+' value='+row.couponValue+' name="ckbox-coupon" type="checkbox" />';
					}
				},
				align:'center',
				width : 50
			}, {
				title : '券号',
				field : 'couponCode',
				align:'center',
				width : 120
			}, {
				title : '券值',
				field : 'couponValue',
				align:'center',
				width : 80
			},{
				title : '申请类型',
				field : 'applyCouponName',
				align:'center',
				sortable:false,
				width : 150
			}]],
			columns:[[  {
				title : '状态',
				field : 'state',
				align:'center',
				formatter:function(value,row,index){
					if(row.state == 0) return "<font color=green>未使用</font>";
					if(row.state == 1) return "<font color=red>已使用</font>";
					if(row.state == 2) return "<font color=grey>已失效</font>";
				},
				width : 80
			},{
				title : '使用/失效时间',
				field : 'updateTime',
				align:'center',
				width : 150
			}] ]
			

		});
	}
	//选择优惠券
	function chooseCoupon(ownerPhone){
		$('#chooseCouponDlg').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var result = new Array();//获取勾选的优惠券
					//var salesAmount = $("#salesAmount").val();//销售金额
					var couponCodeHtml = "";
					var partnerCouponValue = 0;
				    $("[name = ckbox-coupon]:checkbox").each(function () {
				        if ($(this).is(":checked")) {
				            result.push($(this).attr("id"));
				            couponCodeHtml += $(this).attr("title")+";<br />";
				            partnerCouponValue += parseInt($(this).attr("value"));
				        }
				    });
				    //校验选择的优惠券
				    var salesAmount = $("#salesAmount").val();     //销售金额
				    var tip = "";
				    if(salesAmount < 5000){
				    	tip = "订单金额至少5000元以上才能使用优惠券";
	    				$.messager.show({"title":"系统提示","msg":tip,"timeout":1000});
	    				return;
				    }
				    var maxCouponValue = salesAmount*0.1;
				    if(partnerCouponValue > maxCouponValue){
				    	tip = "可以使用的优惠券总金额不能超过"+maxCouponValue+"元";
				    	$.messager.show({"title":"系统提示","msg":tip,"timeout":1000});
				    	return;
				    }	
				    console.info(countCouponValueSum(partnerCouponValue))
				    console.info(countSettleAmount(partnerCouponValue))
				    countCouponValueSum(partnerCouponValue);
				    countSettleAmount(partnerCouponValue);
				    $("#couponIds").val(result.join(","));
				    $("#coupons").html(couponCodeHtml);
				    $("#showTip").html("<font color=color>预计优惠了"+partnerCouponValue+"元</font>");
					$('#chooseCouponDlg').dialog('close');
				}
			}]
		});
		if(ownerPhone != '' && ownerPhone != null){
			datagridCoupon(ownerPhone);
		}
		$('#chooseCouponDlg').dialog('open');
	}
	
	
	
	