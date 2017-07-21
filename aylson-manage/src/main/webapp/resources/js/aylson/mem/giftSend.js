	/**
	 * 活动礼品发放管理.js
	 */
	var datagrid;                             //活动礼品发放列表
	var curUrl = projectName+"/mem/giftSend"; //当前接口链接
	
	$(function(){
		datagrid();//页面加载
	})
	
	//列表
	function datagrid(){
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : curUrl + '/admin/list?v_date=' + new Date(),
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
				width : 200,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.state == 1){
						handleHtml += "<a href='javascript:void(0);' title='已回访' onclick='deal("+row.id+",2)'>已回访</a>&nbsp;";
					}
					if(row.state == 1 || row.state == 2){
						handleHtml += "<a href='javascript:void(0);' title='已发货' onclick='deal("+row.id+",3)'>已发货</a>&nbsp;";
					}
					handleHtml += "<a href='javascript:void(0);' title='查看活动参与情况' onclick='view("+row.id+")'>活动参与情况</a>&nbsp;";
					return handleHtml;
				}
			}, {
				title : '状态',
				field : 'state',
				align:'center',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.state == 1)return "<font color=red>已下单</font>";
					if(row.state == 2)return "<font color=red>已回访</font>";
					if(row.state == 3)return "<font color=green>已发货</font>";
					return handleHtml;
				}
				
			}, {
				title : '单号',
				field : 'billCode',
				align:'center',
				width : 150
				
			}, {
				title : '礼品名称',
				field : 'giftName',
				align:'center',
				width : 150
				
			}, {
				title : '收货人',
				field : 'consignee',
				align:'center',
				width : 100
				
			}, {
				title : '收货人手机',
				field : 'consigneePhone',
				align:'center',
				width : 100
				
			}]],
			columns:[[ 
				{
					title : '省会',
					field : 'province',
					align:'center',
					width : 80
				},
				{
					title : '城市',
					field : 'city',
					align:'center',
					width : 150
				},{
					title : '区域',
					field : 'area',
					align:'center',
					width : 100
				},{
					title : '详细地址',
					field : 'address',
					align:'center',
					formatter:function(value,row,index){
						var address = row.address;
						var tip = address;
						if(address != null && address != ''){
							if(address.length > 20) address = address.substring(0,20)+"...";
							return "<span title='"+tip+"'>" + address +"</span>";
						}
					},
					width : 250
				},{
					title : '创建时间',
					field : 'createTime',
					align:'center',
					width : 140
				},{
					title : '更新时间',
					field : 'updateTime',
					align:'center',
					width : 140
				}
			] ]
		});
	}
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#giftSendSearchForm")));
	}
	
	//重置
	function reset(){
		$("#giftSendSearchForm").form("reset");
	}
	
	//处理发货请求
	function deal(id,state){
		$.messager.confirm("提示","确定处理吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/mem/giftSend/admin/deal',
					data:{
						"id":id,
						"state":state
					},
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
	
	//查看活动情况
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看活动参与情况',
			width:800,
			height:600,
			maximizable:true,
			modal:true,
			href:projectName+'/mem/giftSend/admin/toView?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[
		        {
				 text:'关闭',
			     iconCls:'icon-cancel',  
			 	 handler:function(){
			 		 win.dialog('destroy');
			 	 }   
				}
			]
		});
	}
	
