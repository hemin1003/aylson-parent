	/**
	 * 优惠券管理.js
	 */
	var datagrid;                           //优惠券配置管理列表
	var curUrl = "/partner/coupon";         //当前接口链接
	
	$(function(){
		datagrid();
	})
	
	//列表
	function datagrid(){
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName + curUrl + '/admin/list?v_date=' + new Date(),
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
			}, {
				title : '券号',
				field : 'couponCode',
				align:'center',
				width : 120
			}, {
				title : '券值',
				field : 'couponValue',
				align:'center',
				width : 120
			}, {
				title : '客户姓名',
				field : 'ownerName',
				align:'center',
				width : 80
			},{
				title : '客户地址',
				field : 'province',
				formatter:function(value,row,index){
					var address = row.province+row.city+row.area+row.street+row.unit;
					var tip = address;
					if(address != null && address != ''){
						if(address.length > 20) address = address.substring(0,20)+"...";
						return "<span title='"+tip+"'>" + address +"</span>";
					}
				},
				align:'center',
				width : 300
			}, {
				title : '客户电话',
				field : 'ownerPhone',
				align:'center',
				width : 100
			},{
				title : '申请类型',
				field : 'applyCouponName',
				align:'center',
				sortable:false,
				width : 150
			}]],
			columns:[[  {
				title : '状态',
				field : 'state',
				align:'center',
				formatter:function(value,row,index){
					if(row.state == 0) return "<font color=green>未使用</font>";
					if(row.state == 1) return "<font color=red>已使用</font>";
					if(row.state == 2) return "<font color=grey>已失效</font>";
				},
				width : 80
			},{
				title : '使用/失效时间',
				field : 'updateTime',
				align:'center',
				width : 140
			}, {
				title : '申请人',
				field : 'applier',
				align:'center',
				width : 80
			}, {
				title : '申请人电话',
				field : 'applierPhone',
				align:'center',
				width : 100
			} ,{
				title : '发放时间',
				field : 'createTime',
				align:'center',
				width : 150
			},{
				title : '有效时间',
				field : 'effectTime',
				align:'center',
				width : 150
			}  ] ]
		});
	}

	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#couponSearchForm")));
	}
	
	//重置搜索条件
	function reset(){
		$("#couponSearchForm").form("reset");
	}
	
