	/**
	 * 反馈管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/sys/feedback/admin/list?v_date=' + new Date(),
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
						handleHtml += "<a href='javascript:void(0);' onclick='finish("+row.id+")'>确认处理</a>&nbsp;";
					}
					return handleHtml;
				}
			}, {
				title : '反馈时间',
				field : 'feedbackTime',
				align:'center',
				width : 150
			}, {
				title : '反馈人',
				field : 'feedbacker',
				align:'center',
				width : 100
			}, {
				title : '反馈人电话',
				field : 'feedbackPhone',
				align:'center',
				width : 100
			}, {
				title : '反馈内容',
				field : 'describe',
				align:'center',
				formatter:function(value,row,index){
					var describe = row.describe;
					if(describe != null && describe != ''){
						if(describe.length > 30) describe = describe.substring(0,30)+"...";
						return "<span title='"+row.describe+"'>" + describe +"</span>";
					}
				},
				width : 400
			},{
				title : '状态',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 0){return "<font color=red>未处理</font>"};
					if(row.status == 1){return "<font color=green>已处理</font>"};
				},
				width : 100
			} ] ]
		});
		
	});			
	
	//删除
	function finish(sid){
		$.messager.confirm("提示","确定已处理完成该反馈吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:projectName+'/sys/feedback/admin/update?status=1&id=' + sid,
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
		$("#datagrid").datagrid("load",serializeObject($("#feedbackSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#feedbackSearchForm").form("reset");
	}
	
	