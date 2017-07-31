	/**
	 * 任务操作历史查询
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/cfdb/userTasklistHis/admin/list?v_date=' + new Date(),
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
				title : '手机标识码',
				field : 'phoneId',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '广告名称',
				field : 'taskName',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '广告Logo',
				field : 'logoUrl',
				align : 'center',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					if(value){
						var handleHtml = '';
						handleHtml += '<img src=\'' + value + '\' style="width:50px;height:50px"/>';
						return handleHtml;
					}
				}
			}, {
				title : '用户收益金额',
				field : 'income',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '任务总积分',
				field : 'point',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '广告任务状态',
				field : 'status',
				align : 'center',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					if(row.statusFlag == 3){
						return "<font color=green>" + value + "</font>";
					}else if(row.statusFlag == 4){
						return "<font color=red>" + value + "</font>";
					}
					return value;
				}
			}, {
				title : '操作时间',
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
		
		$(".datagrid-body").css("overflow-x","scroll");
	});
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load", serializeObject($("#userTasklistHisForm")));
	}
	
	//重置
	function reset(){
		$("#userTasklistHisForm").form("reset");
	}
	