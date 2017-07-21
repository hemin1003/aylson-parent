/**
 * 开拓者代理商资料管理-js
 * @author wwx
 * @since  2016-09
 * @version v1.0
 *
 */

var datagrid;
var flag = $("#flag").val();
$(function() { 
	datagrid = $('#datagrid').datagrid({
		method:'get',
		url : projectName+'/pioneer/pioneerAgent/admin/list?v_date=' + new Date(),
		pagination : true,
		queryParams: {
			flag: flag
		},
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
		}, {field : 'opt',
			title:'操作选项',
			align:'center',
			width : 150,
			formatter:function(value,row,index){
				var handleHtml = "";
				if(row.status == 1){
					handleHtml += "<a href='javascript:void(0);' onclick='verify("+row.id+")'>审核</a>&nbsp;";
				}
				if(row.status == 2){
					handleHtml += "<a href='javascript:void(0);' onclick='beforeSign("+row.id+",3)'>签约成功</a>&nbsp;";
					handleHtml += "<a href='javascript:void(0);' onclick='beforeSign("+row.id+",31)'>签约失败</a>&nbsp;";
				}
				if(row.status == 3){
					handleHtml += "<a href='javascript:void(0);' onclick='openShop("+row.id+")'>开业成功</a>&nbsp;";
					handleHtml += "<a href='javascript:void(0);' onclick='openShopFail("+row.id+")'>开业失败</a>&nbsp;";
				}
				if("joining" == flag && row.status == 4){
					handleHtml += "<a href='javascript:void(0);' onclick='view("+row.id+")'>查看</a>&nbsp;";
				}
				if("joined" == flag && row.status == 4){
					handleHtml += "<a href='javascript:void(0);' onclick='saleInfo("+row.id+")' >销售明细</a>&nbsp;";
				}
				return handleHtml;
			}
		} , {
			title : '代理商名称 ',
			field : 'agentName',
			align:'center',
			sortable:true,
			width : 150
		}, {
			title : '代理商电话 ',
			field : 'contactPhone',
			align:'center',
			sortable:true,
			width : 100
		}]],
		columns : [[{
			title : '地址',
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
			title : '商店名称',
			field : 'shopName',
			align:'center',
			sortable:true,
			width : 100
		},{
			title : '商店地址',
			field : 'shopAddress',
			align:'center',
			formatter:function(value,row,index){
				var address = row.shopProvince+row.shopCity+row.shopArea+row.shopAddress;
				var addresssub = row.shopProvince+row.shopCity+row.shopArea+row.shopAddress;
				if(addresssub != null && addresssub != ''){
					if(addresssub.length > 20) addresssub = addresssub.substring(0,20)+"...";
					return "<span title='"+address+"'>" + addresssub +"</span>";
				}
			},
			sortable:true,
			width : 300
		}, {
			title : '提交人 ',
			field : 'submitter',
			align:'center',
			sortable:true,
			width : 100
		},{
			title : '提交人电话',
			field : 'submitterPhone',
			align:'center',
			sortable:true,
			width : 80
		},{
			title : '状态',
			field : 'status',
			align:'center',
			formatter:function(value,row,index){
				if(row.status == 1)return "<font color=green>待审核</font>";
				if(row.status == 2)return "<font color=green>已审核</font>";
				if(row.status == 3)return "<font color=green>已签约</font>";
				if(row.status == 4)return "<font color=green>已开业</font>";
				if(row.status == 21)return "<font color=red>审核不通过</font>";
				if(row.status == 31)return "<font color=red>签约失败</font>";
				if(row.status == 41)return "<font color=red>开业失败</font>";
			},
			sortable:true,
			width : 80
		},{
			title : '之前代理品牌',
			field : 'agency',
			align:'center',
			sortable:true,
			formatter:function(value,row,index){
				var agency = row.agency;
				if(agency != null && agency != ''){
					if(agency.length > 20) agency = agency.substring(0,20)+"...";
					return "<span title='"+row.agency+"'>" + agency +"</span>";
				}
			},
			width : 300
		},{
			title : '概况',
			field : 'descs',
			align:'center',
			sortable:true,
			formatter:function(value,row,index){
				var descs = row.descs;
				if(descs != null && descs != ''){
					if(descs.length > 25) descs = descs.substring(0,25)+"...";
					return "<span title='"+row.descs+"'>" + descs +"</span>";
				}
			},
			width : 350
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
		},{
			title : '历史备注',
			field : 'historyRemark',
			align:'center',
			sortable:true,
			formatter:function(value,row,index){
				var remark = row.historyRemark;
				if(remark != null && remark != ''){
					if(remark.length > 25) remark = remark.substring(0,25)+"...";
					return "<span title='"+row.historyRemark+"'>" + remark +"</span>";
				}
			},
			width : 350
		}] ]
	});
});	

//审核代理商资料
function verify(sid){
	win = $("<div></div>").dialog({
		title:'审核',
		width:700,
		height:400,
		modal:true,
		href:projectName+'/pioneer/pioneerAgent/admin/toVerify?id='+sid,
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
			    	$("#pioneerAgentForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/pioneer/pioneerAgent/admin/verify?status=2',
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
				    	var remark = $("#remark").textbox("getValue");
				    	if(remark == ""){
				    		$.messager.show({"title":"系统提示","msg":"审核不通过必须填写审核意见","timeout":1000});
				    		return;
				    	}
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	$("#pioneerAgentForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/pioneer/pioneerAgent/admin/verify?status=21',
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


//签约
function beforeSign(sid,status){
	if(status == 3){//签约成功
		$('#dlgSignMode').dialog({
			title:"请选择签约方式",
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	var signMode = $("input[name='signMode']:checked").val();
			    	if(signMode == null || signMode == 'null' || signMode == ''){
			    		$.messager.show({"title":"系统提示","msg":"请选择签约方式","timeout":1000});
			    		return;
			    	}
			    	var tip = "确认签约成功吗？";
					$.messager.confirm("提示",tip,function(r){
						if(r){
							var winTip = $.messager.progress({
								title:'请稍候...',
								msg:'正在处理...'
							});
							sign(sid,status,'',signMode);
							$('#dlgSignMode').dialog('close');
						}
					})
			    	
			     }   
			   },{
				 text:'取消',
			     iconCls:'icon-cancel',  
			 	 handler:function(){
			 		$('#dlgSignMode').dialog('close');
			 	 }   
			  }]
		})
		$('#dlgSignMode').dialog('open');
	}else if(status == 31){//签约失败
		$('#dlg').dialog({
			title:"请输入签约失败的原因",
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	var remark = $("#remark1").textbox("getValue");
			    	if(remark == ""){
			    		$.messager.show({"title":"系统提示","msg":"审核不通过必须填写审核意见","timeout":1000});
			    		return;
			    	}
			    	var winTip = $.messager.progress({
						title:'请稍候...',
						msg:'正在处理...'
					});
			    	sign(sid,status,remark);
			    	$('#dlg').dialog('close');
			     }   
			   },{
				 text:'取消',
			     iconCls:'icon-cancel',  
			 	 handler:function(){
			 		$('#dlg').dialog('close');
			 	 }   
			  }]
		})
		$('#dlg').dialog('open');
	}
}

function sign(sid,status,remark,signMode){
	$.ajax({
		type:"POST",
		data:{
			'id':sid,
			'status':status,
			'signMode':signMode,
			'remark':remark
		},
		url:projectName+'/pioneer/pioneerAgent/admin/sign',
		dataType:"json",
		success:function(data){
			if(data){
				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				if(data.success){
					$.messager.progress('close');
					$('#dlg').dialog('close');
					$("#datagrid").datagrid("reload");
				}
			 }
		}
	});
}

//开业成功
function openShop(sid){
	win = $("<div></div>").dialog({
		title:'开业',
		width:'80%',
		height:'95%',
		modal:true,
		href:projectName+'/pioneer/pioneerAgent/admin/toOpenShop?id='+sid,
		onClose:function(){
	    	$(this).dialog("destroy");
	    },
		buttons:[{
				text:'开业成功',
			    iconCls:'icon-ok',
			    handler:function(){
			    	var winTip = $.messager.progress({
						title:'请稍候...',
						msg:'正在处理...'
					});
			    	$("#pioneerAgentForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/pioneer/pioneerAgent/admin/openShop?status=4',
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

//开业失败
function openShopFail(sid){
	$('#dlg').dialog({
		title:"请输入开业失败的原因",
		buttons:[{
			text:'确定',
		    iconCls:'icon-ok',
		    handler:function(){
		    	var remark = $("#remark1").textbox("getValue");
		    	if(remark == ""){
		    		$.messager.show({"title":"系统提示","msg":"开业失败必须填写失败原因","timeout":1000});
		    		return;
		    	}
		    	var winTip = $.messager.progress({
					title:'请稍候...',
					msg:'正在处理...'
				});
		    	$.ajax({
		    		type:"POST",
		    		data:{
		    			'id':sid,
		    			'status':41,
		    			'remark':remark
		    		},
		    		url:projectName+'/pioneer/pioneerAgent/admin/openShop',
		    		dataType:"json",
		    		success:function(data){
		    			if(data){
		    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    				if(data.success){
		    					$('#dlg').dialog('close');
		    					$.messager.progress('close');
		    					$("#datagrid").datagrid("reload");
		    				}
		    			 }
		    		}
		    	});
		    	//$('#dlg').dialog('destroy');
		     }   
		   },{
			 text:'取消',
		     iconCls:'icon-cancel',  
		 	 handler:function(){
		 		$('#dlg').dialog('close');
		 	 }   
		  }]
	})
	$('#dlg').dialog('open');
}

//上传转账凭证
function uploadShopImg(id,target,bucket){
	if(bucket == null){
	 	$.messager.show({"title":"系统提示","msg":"找不到对应的资源空间，请联系管理员","timeout":1000});
	 	return;
	}
	var win;
	win = $("<div></div>").dialog({
		title:'上传商店图片',
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
		    		 //url:projectName+'/sys/fileHandle/uploadTest',
		    		 success:function(responseData){
		    			 $.messager.progress('close');
		    			 if(responseData){
		    				var data = $.parseJSON(responseData);
		    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    			 	if(data.success){
		    			 		var fileName = data.data;
		    			 		var html = "<li>";
		    			 		html += "<img class='naviga_big' alt='' src='"+fileName+"' style='width:150px;height:150px;'>";
		    			 		html += "<a href='#' onclick='delShopImg(this)'>删除</a>";
		    			 		$("#"+id).append(html);
		    			 		resetShopImg();
		    			 		win.dialog('destroy');
		    				}
		    			 } 
		    		 }
		    	});
		    	}
//		     }   
		   },{
			 text:'取消',
		     iconCls:'icon-cancel',  
		 	 handler:function(){
		 		 win.dialog('destroy');
		 	 }   
		  }]
	});
}

//重置商店图片
function resetShopImg(){
	var shopImg = "";
	$.each($(".naviga_big"),function(n,value) {   
		shopImg += value.src+";";
	}); 
	$("#shopImg").val(shopImg);
}

//删除商店图片
function delShopImg(obj){
	$(obj).parent().remove();
	resetShopImg();
}

function saleInfo(sid){
	$('#dlgSaleInfo').dialog({
		buttons:[{
			 text:'关闭',
		     iconCls:'icon-cancel',  
		 	 handler:function(){
		 		$('#dlgSaleInfo').dialog('close');
		 	 }   
		  }]
	});
	$('#dlgSaleInfo').dialog('open');
	var datagridGetDetail = $('#datagridSaleInfo').datagrid({
		method:'get',
		url : projectName+'/pioneer/pioneerAgentSaleInfo/admin/list?agentId=' + sid +'&v_date=' + new Date(),	
		pagination : true,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : true,
		nowrap : false,
		border : false,
		idField : 'id',
		singleSelect:true,
		toolbar:[ {
			text:"新增",
			iconCls : 'icon-add',
			handler : function(){addSaleInfo(sid)}
/*			handler : addSaleInfo
*/		},{
			text:"刷新",
			iconCls : 'icon-reload',
			handler : reloadSaleInfo
		}],
		rownumbers: true,
			frozenColumns : [[{
			field : 'id',
			title:'序号',
			align:'center',
			hidden:true,
			width : 50
		}, {
			title : '时间（年份）',
			field : 'year',
			align:'center',
			width : 100
		}, {
			title : '销售额',
			field : 'sales',
			align:'center',
			width : 100
		}, {
			title : '邀请人回扣',
			field : 'rebate',
			align:'center',
			width : 100
		}, {
			title : '备注',
			field : 'remark',
			align:'center',
			width : 300
		}] ]
	});
}

//新增销售明细
function addSaleInfo(sid){
	var win;
	win = $("<div></div>").dialog({
		title:'新增销售明细',
		width:600,
		height:400,
		maximizable:true,
		modal:true,
		href:projectName+'/pioneer/pioneerAgentSaleInfo/admin/toAdd',
		onClose:function(){
			$(this).dialog("destroy");
		},
		buttons:[{
			text:'确定',
		    iconCls:'icon-ok',
		    handler:function(){
		    	var tip = "是否确认无误？保存成功后会直接将回扣的金额添加到邀请人的钱包中";
		    	$.messager.confirm("提示",tip,function(r){
					if(r){
						$("#pioneerAgentSaleInfoForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/pioneer/pioneerAgentSaleInfo/admin/add?agentId='+sid,
				    		 success:function(responseData){
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
										$("#datagridSaleInfo").datagrid("reload");
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

//查看
function view(sid){
	win = $("<div></div>").dialog({
		title:'查看',
		width:'60%',
		height:'60%',
		modal:true,
		href:projectName+'/pioneer/pioneerAgent/admin/toView?id='+sid,
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

//刷新
function reload(){
	$("#datagrid").datagrid("reload");
}

//刷新
function reloadSaleInfo(){
	$("#datagridSaleInfo").datagrid("reload");
}


//搜索
function doSearch(){
	$("#datagrid").datagrid("load",serializeObject($("#pioneerAgentSearchForm")));
}


//重置
function reset(){
	$("#pioneerAgentSearchForm").form("reset");
}