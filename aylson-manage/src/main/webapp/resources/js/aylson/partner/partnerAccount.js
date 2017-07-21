	/**
	 * 合伙人账号信息管理.js
	 */
	var datagrid;           //合伙人账号信息列表
	var bonusDatagrid;      //分红管理列表
	var curUrl = "/partner/partnerAccount";   //当前接口链接
	
	$(function(){
		datagrid();
		bonusDatagrid();
	})
	
	//列表
	function datagrid(){
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/partner/partnerAccount/admin/list?v_date=' + new Date(),
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
			}, {field : 'opt',
				title:'操作',
				align:'center',
				width : 100,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.state == 0){
						handleHtml += "<a href='javascript:void(0);' title='通过' onclick='verify("+row.id+",true)'>通过</a>&nbsp;";
						handleHtml += "<a href='javascript:void(0);' title='不通过' onclick='verify("+row.id+",false)'>不通过</a>&nbsp;";
					}
					return handleHtml;
				}
			}, {
				title : '状态',
				field : 'state',
				align:'center',
				width : 80,
				formatter:function(value,row,index){
					if(row.state == 0) return "<font color=red>申请中...</font>";
					if(row.state == 1) return "<font color=green>已通过</font>";
					if(row.state == 2) return "<font color=red>未通过</font>";
				}
			}, {
				title : '姓名',
				field : 'partnerName',
				align:'center',
				width : 80
			},{
				title : '手机号',
				field : 'mobilePhone',
				align:'center',
				sortable:true,
				width : 80
			}, {
				title : '所在城市',
				field : 'province',
				align:'center',
				formatter:function(value,row,index){
					return row.province+row.city;
				},
				width : 200
			}, {
				title : '预约人电话',
				field : 'mobilePhone',
				align:'center',
				width : 100
			},{
				title : '行业',
				field : 'industryType',
				formatter:function(value,row,index){
					if(row.industryType == 0) return "其他";
					if(row.industryType == 1) return "设计师";
					if(row.industryType == 2) return "工长";
					if(row.industryType == 3) return "建筑";
					if(row.industryType == 4) return "房产中介";
					if(row.industryType == 5) return "售楼员";
				},
				align:'center',
				width : 100
			}, {
				title : '卖场/品牌',
				field : 'storeBrand',
				align:'center',
				width : 150
			}]],
			columns:[[ {
				title : '注册时间',
				field : 'registerTime',
				align:'center',
				width : 150
			},{
				title : '推荐人',
				field : 'referralName',
				align:'center',
				width : 100
			}, {
				title : '推荐人电话',
				field : 'referralPhone',
				align:'center',
				width : 100
			},{
				title : '代理商',
				field : 'agentName',
				align:'center',
				width : 100
			}, {
				title : '代理商电话',
				field : 'agentPhone',
				align:'center',
				width : 100
			} ] ]
		});
	}

	//查看
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:750,
			height:450,
			maximizable:true,
			modal:true,
			href:projectName+'/partner/partnerAccount/admin/toView?id='+sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#partnerAccountSearchForm")));
		$("#bonusDatagrid").datagrid("load",serializeObject($("#partnerAccountSearchForm")));
	}
	
	//重置搜索条件
	function reset(){
		$("#partnerAccountSearchForm").form("reset");
	}
	
	//审核合伙人注册申请
	function verify(sid,isPass){
		$.messager.confirm("提示","信息无误，确认执行此操作吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					data:{
						'id':sid,
						'isPass':isPass
					},
					url:projectName+'/partner/partnerAccount/admin/verify',
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

	//分红管理
	function bonusDatagrid(){
		bonusDatagrid = $('#bonusDatagrid').datagrid({
			method:'get',
			url : projectName+'/partner/partnerAccount/admin/list?v_date=' + new Date(),
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
			queryParams: {
				state: '1'
			},
 			frozenColumns : [[{
				field : 'id',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			}, {field : 'opt',
				title:'操作',
				align:'center',
				width : 150,
				formatter:function(value,row,index){
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' title='添加转账记录' onclick='addBonus("+row.id+")'>添加转账</a>&nbsp;";
					handleHtml += "<a href='javascript:void(0);' title='查看转账记录' onclick='viewBonusRecords("+row.id+")'>转账记录</a>&nbsp;";
					return handleHtml;
				}
			}, {
				title : '合伙人姓名',
				field : 'partnerName',
				align:'center',
				width : 80
			},{
				title : '手机号',
				field : 'mobilePhone',
				align:'center',
				sortable:true,
				width : 80
			},{
				title : '总分红',
				field : 'walletTotal',
				align:'center',
				formatter:function(value,row,index){
					var total = row.wallet-row.walletHad
					return "<font color=purple>"+total+"</font>";
				},
				sortable:true,
				width : 80
			},{
				title : '未转分红',
				field : 'wallet',
				align:'center',
				formatter:function(value,row,index){
					return "<font color=red>"+row.wallet+"</font>";
				},
				sortable:true,
				width : 80
			},{
				title : '已转分红',
				field : 'walletHad',
				align:'center',
				formatter:function(value,row,index){
					return "<font color=green>"+row.walletHad+"</font>";
				},
				sortable:true,
				width : 80
			}]],
			columns:[[ 
			{
				title : '身份证号码',
				field : 'cardID',
				align:'center',
				sortable:true,
				width : 150
			},{
				title : '开户银行',
				field : 'bankName',
				align:'center',
				sortable:true,
				width : 150
			},{
				title : '银行卡号',
				field : 'bankNo',
				align:'center',
				sortable:true,
				width : 200
			},{
				title : '开户名',
				field : 'bankAccountName',
				align:'center',
				sortable:true,
				width : 150
			}
			] ]
		});
	}
	
	//添加转账
	function addBonus(sid){
		var win;
		win = $("<div></div>").dialog({
			title:'添加转账记录',
			width:800,
			height:650,
			maximizable:true,
			modal:true,
			href:projectName + curUrl+'/admin/toAddBonus?id='+sid,
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	var wallet = $("#wallet").val();//未转分红
			    	var transferValue = $("#transferValue").val(); //转账金额
			    	var attachUrls= $("#attachUrl").val();
			    	if(parseFloat(transferValue) <= 0){
			    		$.messager.show({"title":"系统提示","msg":"转账金额不能小于0","timeout":1000});
			    		return;
			    	}
			    	if(parseFloat(wallet) < parseFloat(transferValue)){
			    		$.messager.show({"title":"系统提示","msg":"转账金额超过未转分红的金额","timeout":1000});
			    		return;
			    	}
			    	if(attachUrls == ""){
			    		$.messager.show({"title":"系统提示","msg":"转账凭证附近不能为空","timeout":1000});
			    		return;
			    	}
			    	$.messager.confirm("提示", "确认该转账记录吗？", function(r) {
						if (r) {
							$("#partnerAccountForm").form('submit',{
					    		 type:'POST',
					    		 url:projectName + curUrl + '/admin/addBonus',
					    		 success:function(responseData){
					    			 if(responseData){
					    				var data = $.parseJSON(responseData);
					    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
					    			 	if(data.success){
					    			 		$("#bonusDatagrid").datagrid("reload");
											win.dialog('destroy');
					    				}
					    			 } 
					    		 }
					    	 });
						}else{
							$("#bonusDatagrid").datagrid("reload");
							win.dialog('destroy');
							return;
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
	
	//查看转账记录
	function viewBonusRecords(sid){
		$('#bonusRecordsDlg').dialog({
			buttons:[{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					$('#bonusRecordsDlg').dialog('close');
				}
			}]
		});
		bonusRecords(sid);//获取列表
		$('#bonusRecordsDlg').dialog('open');
	}
	
	//转账记录
	function bonusRecords(sid){
		datagrid = $('#bonusRecords').datagrid({
			method:'get',
			url : projectName+'/partner/partnerAccount/admin/billList?v_date=' + new Date(),
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
			queryParams: {
				accountId: sid,
				type:-1
			},
 			frozenColumns : [[{
				field : 'id',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			}, {
				title : '转账金额',
				field : 'wallet',
				align:'center',
				width : 80
			},{
				title : '转账人',
				field : 'description',
				align:'center',
				sortable:true,
				width : 80
			}, {
				title : '转账时间',
				field : 'createTime',
				align:'center',
				width : 150
			}, {
				title : '附件',
				field : 'attachUrl',
				formatter:function(value,row,index){
					if(row.attachUrl != null && row.attachUrl != ''){
						return "<a href='javascript:void(0);' onclick='viewAttachUrl(\""+row.attachUrl+"\")' title='查看转账凭证附件' >查看</a>";
					}else{
						return "无"
					}
				},
				align:'center',
				width : 150
			}]]
			
		});
	}
	
	//查看转账附件
	function viewAttachUrl(attachUrl){
		$('#walletDatailDlg').dialog({
			buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					$('#walletDatailDlg').dialog('close');
				}
			}]
		});
		$('#walletDatailDlg').dialog('open');
		var html = "<img src='"+attachUrl+"' width='600px' height='400px'/>";
		$('#walletDatailUrl').html(html);
	}
	
	