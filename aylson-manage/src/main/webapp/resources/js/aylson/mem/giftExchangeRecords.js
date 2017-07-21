	/**
	 * 礼品积分兑换配置管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/mem/giftExchangeRecords/admin/list?v_date=' + new Date(),
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
			}, {
				title : '兑换时间',
				field : 'exchangeTime',
				align:'center',
				width : 150
			}, {
				title : '有效领取时间',
				field : 'validTime',
				align:'center',
				width : 150
			}, {
				title : '礼品名称',
				field : 'giftName',
				align:'center',
				formatter:function(value,row,index){
					var giftName = row.giftName;
					if(giftName != null && giftName != ''){
						if(giftName.length > 20) giftName = giftName.substring(0,20)+"...";
						return "<span title='"+row.giftName+"'>" + giftName +"</span>";
					}
				},
				width : 250
			}, {
				title : '兑换码',
				field : 'exchangeCode',
				align:'center',
				width : 150
			}, {
				title : '所费积分',
				field : 'integral',
				align:'center',
				width : 100
			}, {
				title : '兑换人手机号',
				field : 'exchangePhone',
				align:'center',
				width : 100
			}, {
				title : '兑换人',
				field : 'exchanger',
				align:'center',
				width : 100
			},{
				title : '适用兑换对象',
				field : 'matchObj',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.matchObj == 0) return "全部";
					if(row.matchObj == 1) return "设计师";
					if(row.matchObj == 2) return "产业工人";
					if(row.matchObj == 3) return "开拓者";
					return "全部";
				},
				width : 80
			} ] ]
		});
		
	});			
	
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#giftExchangeRecordsSearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#giftExchangeRecordsSearchForm").form("reset");
	}
	
	