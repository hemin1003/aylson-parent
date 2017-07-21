	/**
	 * 优惠券配置管理.js
	 */
	var datagrid;                           //优惠券配置管理列表
	var curUrl = "/partner/couponApply";   //当前接口链接
	
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
			}, {field : 'opt',
				title:'操作',
				align:'center',
				width : 120,
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
				title : '申请人',
				field : 'applier',
				align:'center',
				width : 80
			},{
				title : '申请类型',
				field : 'applyCouponName',
				align:'center',
				sortable:false,
				width : 150
			}, {
				title : '申请数量',
				field : 'applyNum',
				align:'center',
				width : 100
			},{
				title : '申请时间',
				field : 'applyTime',
				align:'center',
				width : 150
			}]],
			columns:[[ {
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
						if(address.length > 15) address = address.substring(0,15)+"...";
						return "<span title='"+tip+"'>" + address +"</span>";
					}
				},
				align:'center',
				width : 250
			}, {
				title : '客户电话',
				field : 'ownerPhone',
				align:'center',
				width : 100
			},{
				title : '风格',
				field : 'houseStyle',
				align:'center',
				width : 100
			}, {
				title : '户型',
				field : 'houseType',
				align:'center',
				width : 100
			}, {
				title : '面积',
				field : 'houseArea',
				align:'center',
				width : 100
			}, {
				title : '进度',
				field : 'progress',
				align:'center',
				formatter:function(value,row,index){
					if(row.progress == 0) return '<font color=red>未进场</font>'
					if(row.progress == 1) return '<font color=green>已进场</font>'
				},
				width : 100
			}, {
				title : '申请人电话',
				field : 'applierPhone',
				align:'center',
				width : 100
			}   ] ]
		});
	}

	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#couponApplySearchForm")));
	}
	
	//重置搜索条件
	function reset(){
		$("#couponApplySearchForm").form("reset");
	}
	
	//审核优惠券申请
	function verify(sid,isPass){
		$.messager.confirm("提示","信息无误，确认执行此操作吗？",function(r){
			if(r){
				$.ajax({
					type:"POST",
					data:{
						'id':sid,
						'isPass':isPass
					},
					url:projectName+ curUrl + '/admin/verify',
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
	