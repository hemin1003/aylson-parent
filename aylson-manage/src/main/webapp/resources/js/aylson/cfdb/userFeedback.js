	/**
	 * 用户反馈数据记录查询
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/cfdb/userFeedback/admin/list?v_date=' + new Date(),
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
 			frozenColumns : [[
 				{
				title : '手机标识码(IMEI)',
				field : 'phoneId',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '反馈信息',
				field : 'info',
				align : 'center',
				width : 500,
				sortable:true
			}, {
				title : '反馈时间',
				field : 'createDate',
				align : 'center',
				width : 120,
				sortable:true,
				formatter:function(value,row,index){
					if(value){
						return value.substring(0,19);
					}
					return value;
				}
			}
			] ]
		});
	});
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load", serializeObject($("#userFeedbackForm")));
	}
	
	//重置
	function reset(){
		$("#userFeedbackForm").form("reset");
	}
	