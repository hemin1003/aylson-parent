	/**
	 * 机构用户管理.js
	 */
	var datagrid;
	var isLiveMode = $("#isLiveMode").val();
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/sys/agentUser/admin/list?v_date=' + new Date(),
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
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 240,
				formatter:function(value,row,index){
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
//					handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
					if(row.status == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.userId+",0)'>禁用</a>&nbsp;";
					}else if(row.status == 0){
						handleHtml += "<a href='javascript:void(0);' onclick='changeStatus("+row.userId+",1)'>启用</a>&nbsp;";
					}
					//handleHtml += "<a href='javascript:void(0);' onclick='getMyQrCodeUrl("+row.userId+",\""+row.agentName+"\",\""+row.wxQrcodeTicket+"\")'>设计师推广二维码</a>&nbsp;";
					handleHtml += "<a href='javascript:void(0);' onclick='getMyOwnQrCodeUrl("+row.userId+",\""+row.agentName+"\",\""+row.wxOwnQrcodeTicket+"\")'>安居艾臣推广二维码</a>&nbsp;";
					return handleHtml;
				}
			},{
				title : '是否禁用',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 0)return '<font color=red>是</font>';
					if(row.status == 1) return '<font color=green>否</font>';
				},
				width : 80
			},{
				title : '门店类型',
				field : 'isAgent',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.isAgent == false)return '<font color=green>直营</font>';
					if(row.isAgent == true) return '<font color=blue>代理</font>';
				},
				width : 80
			}, {
				title : '账号',
				field : 'userName',
				align:'center',
				width : 100
			}, {
				title : '门店名称',
				field : 'agentName',
				align:'center',
				width : 150
			},{
				title : '门店编号',
				field : 'agentCode',
				align:'center',
				sortable:true,
				width : 100
			},{
				title : '所属地区',
				field : 'province',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					return row.province+row.city+row.area;
				},
				width : 200
			}]],
			columns : [[{
				title : '联系人',
				field : 'contacter',
				align:'center',
				sortable:true,
				width : 80
			},{
				title : '联系人电话',
				field : 'contactPhone',
				align:'center',
				sortable:true,
				width : 80
			},{
				title : '证件号',
				field : 'certificateNo',
				align:'center',
				sortable:true,
				width : 150
			},{
				title : '地址',
				field : 'address',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					var address = row.address;
					if(address != null && address != ''){
						if(address.length > 25) address = address.substring(0,25)+"...";
						return "<span title='"+row.address+"'>" + address +"</span>";
					}
				},
				width : 300
			},{
				title : '代理产品',
				field : 'products',
				align:'center',
				sortable:true,
				width : 150
			},{
				title : '创建日期',
				field : 'createTime',
				align:'center',
				sortable:true,
				width : 150
			}] ]
		});
		
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:'80%',
			height:'80%',
			modal:true,
			href:projectName+'/sys/agentUser/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
		    		$("#agentUserForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/agentUser/admin/add',
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
			width:'80%',
			height:'80%',
			modal:true,
			href:projectName+'/sys/agentUser/admin/toEdit?id=' + sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#agentUserForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/sys/agentUser/admin/update',
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
					url:projectName+'/sys/agentUser/admin/deleteById?id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#agentUserSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#agentUserSearchForm").form("reset");
	}
	
	//修改用户状态
	function changeStatus(sid,status){
		var tip = "确定禁用该用户吗？";
		if(status == 1){
			tip = "确定启用该用户吗？";
		}
		$.messager.confirm("提示",tip,function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/sys/user/admin/changeStatus?id=' + sid+'&status='+status,
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
	
	//获取设计师推广二维码
	function getMyQrCodeUrl(id,title,qrCode){
		var url = 'http://test.aylsonclub.com/dc-web/wx/frame/getMySjsQrCodeUrl?channel=agent&memberId='+ id;
		if(isLiveMode == 'true'){
			url = 'http://sjs.aylsonclub.com/service/wx/frame/getMyQrCodeUrl?channel=agent&memberId='+ id
		}
		$('#qrCodeDlg').dialog({
			title:'【'+title+'】设计师推广二维码',
			buttons:[{
				 text:'取消',
			     iconCls:'icon-cancel',  
			 	 handler:function(){
			 		$('#qrCodeDlg').dialog('close');
			 	 }   
			}]
		});
		$('#qrCodeDlg').dialog('open');
		if(qrCode != null && qrCode != '' && qrCode != 'null'){
			$("#qrCode").attr('src','https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket='+qrCode);
		}else{
			$.ajax({
				type:'GET',
				url : url,
				cache : false,
				success:function(responseData){
					if(responseData){
						var data = $.parseJSON(responseData);
						$("#qrCode").attr('src',data.data);
					}
				}
			});
		}
	};
	
	//获取安居艾臣推广二维码
	function getMyOwnQrCodeUrl(id,title,qrCode){
		var url = 'http://test.aylsonclub.com/dc-web/wx/frame/getMyQrCodeUrl?channel=agentOwner&memberId='+ id;
		if(isLiveMode == 'true'){
			url = 'http://ktz.aylsonclub.com/service/wx/frame/getMyQrCodeUrl?channel=agentOwner&memberId='+ id
		}
		$('#qrCodeDlg').dialog({
			title:'【'+title+'】安居艾臣推广二维码',
			buttons:[{
				 text:'取消',
			     iconCls:'icon-cancel',  
			 	 handler:function(){
			 		$('#qrCodeDlg').dialog('close');
			 	 }   
			}]
		});
		$('#qrCodeDlg').dialog('open');
		if(qrCode != null && qrCode != '' && qrCode != 'null'){
			$("#qrCode").attr('src','https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket='+qrCode);
		}else{
			$.ajax({
				type:'GET',
				url : url,
				cache : false,
				success:function(responseData){
					if(responseData){
						var data = $.parseJSON(responseData);
						$("#qrCode").attr('src',data.data);
					}
				}
			});
		}
	};
	
	