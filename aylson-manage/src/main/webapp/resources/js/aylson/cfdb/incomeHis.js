	/**
	 * 用户收益记录查询
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/cfdb/incomeHis/admin/list?v_date=' + new Date(),
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
				title : '收益时间',
				field : 'incomeTime',
				align : 'center',
				width : 120,
				sortable:true,
				formatter:function(value,row,index){
					if(value){
						return value.substring(0,19);
					}
					return value;
				}
			}, {
				title : '记录标识',
				field : 'flag',
				align : 'center',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					if(value == 1){
						return "<font color=green>加钱</font>";
					}else if(value == 2){
						return "<font color=red>扣钱</font>";
					}else if(value == 3){
						return "直接失败";
					}
				}
			}, {
				title : '收益渠道来源',
				field : 'channel',
				align : 'center',
				width : 120,
				sortable:true,
				formatter:function(value,row,index){
					if(value == 1){
						return "后台系统任务";
					}else if(value == 2){
						return "抢红包";
					}else if(value == 3){
						return "邀请";
					}else if(value == 4){
						return "点入平台";
					}else if(value == 5){
						return "有米平台";
					}
					return "未知渠道";
				}
			}, {
				title : '用户收益金额',
				field : 'income',
				align : 'center',
				width : 80
			}, {
				title : '平台收益金额',
				field : 'sysIncome',
				align : 'center',
				width : 80
			}, {
				title : '审核完成时间',
				field : 'createDate',
				align : 'center',
				width : 120,
				sortable:true,
				formatter:function(value,row,index){
					//仅显示后台任务的时间
					if(row.channel == 1){
						return value.substring(0,19);
					}
					return '';
				}
			}
			, {
				title : '审核人',
				field : 'createdBy',
				align : 'center',
				width : 120
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
		$("#datagrid").datagrid("load", serializeObject($("#incomeHisForm")));
	}
	
	//重置
	function reset(){
		$("#incomeHisForm").form("reset");
	}
	