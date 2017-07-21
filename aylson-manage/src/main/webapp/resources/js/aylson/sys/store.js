	/**
	 * 门店管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/sys/store/admin/list?v_date=' + new Date(),
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
				width : 100,
				formatter:function(value,row,index){
					var handleHtml = "";
					handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
					handleHtml += "<a href='javascript:void(0);' onclick='del("+row.id+")'>删除</a>&nbsp;";
					return handleHtml;
				}
			}, {
				title : '门店名称',
				field : 'storeName',
				align:'center',
				width : 150
			}, {
				title : '营业时间',
				field : 'openTime',
				align:'center',
				width : 150
			}, {
				title : '工作电话',
				field : 'workPhone',
				align:'center',
				width : 100
			}] ],
			columns : [ [ {
				title : '地址',
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
			}, {
				title : '门店介绍',
				field : 'introduce',
				align:'center',
				formatter:function(value,row,index){
					var introduce = row.introduce;
					if(introduce != null && introduce != ''){
						if(introduce.length > 25) introduce = introduce.substring(0,25)+"...";
						return "<span title='"+row.introduce+"'>" + introduce +"</span>";
					}
				},
				width : 300
			}, {
				title : '经度',
				field : 'longitude',
				align:'center',
				width : 100
			}, {
				title : '纬度',
				field : 'latitude',
				align:'center',
				width : 100
			} ] ]
		});
		
	});			
	
	//新增
	function add(obj){
		var win;
		win = $("<div></div>").dialog({
			title:'新增',
			width:800,
			height:550,
			modal:true,
			href:projectName+'/sys/store/admin/toAdd',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
	    	    	$("#storeForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/store/admin/add',
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
			width:800,
			height:550,
			maximizable:true,
			modal:true,
			href:projectName+'/sys/store/admin/toEdit?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'确定',
				    iconCls:'icon-ok',
				    handler:function(){
				    	$("#storeForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/sys/store/admin/update',
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
					url:projectName+'/sys/store/admin/deleteById?id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#storeSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#storeSearchForm").form("reset");
	}
	//获取经纬度
	function getGps(){
		var address = $("#address").textbox('getValue');
		if(address == null || address == ''){
			$.messager.show({"title":"系统提示","msg":"地址不能为空","timeout":1000});
			return;
		}
		$.ajax({
			type:"POST",
			data:{"address":address},
			url:projectName+'/sys/store/admin/getGpsByAddress',
			dataType:"json",
			success:function(data){
				console.info(data);
				if(data){
    				if(data.success){
    					$("#longitude").numberbox('setValue',data.data[0]);//经度
    					$("#latitude").numberbox('setValue',data.data[1]); //维度
    				}else{
    					$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
    				}
    			 }
			}
		});
	}
	
	