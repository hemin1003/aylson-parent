	/**
	 * SDK平台广告回调查询
	 */
	var datagrid;
	var editor;
	
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/cfdb/sdkTasklistHis/admin/list?v_date=' + new Date(),
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'hashid',
			singleSelect:true,
			rownumbers: true,
			toolbar:[{
				text:"刷新",
				iconCls : 'icon-reload',
				handler : reload
			}],
 			frozenColumns : [[{ 
				field : 'opt',
				title : '操作选项',
				align : 'center',
				width : 100,
				formatter:function(value,row,index){
					var handleHtml = '';
					handleHtml += '<a href="javascript:edit(\'' + row.hashid + '\')">查看详情</a>&nbsp;';
					return handleHtml;
				}
			}, 
// 				{
//				title : '唯一标识ID',
//				field : 'hashid',
//				align : 'center',
//				width : 80,
//				sortable:true
//			}, 
			{
				title : '开发者应用ID',
				field : 'appid',
				align : 'center',
				width : 80,
				sortable:true
			}, 
//			{
//				title : '广告ID',
//				field : 'adid',
//				align : 'center',
//				width : 80,
//				sortable:true
//			}, 
			{
				title : '广告名称',
				field : 'adname',
				align : 'center',
				width : 150,
				sortable:true
			}, 
//			{
//				title : '开发者设置的用户ID',
//				field : 'userid',
//				align : 'center',
//				width : 120,
//				sortable:true
//			}, {
//				title : 'mac地址',
//				field : 'mac',
//				align : 'center',
//				width : 120,
//				sortable:true
//			}, 
			{
				title : '手机标识码(IMEI)',
				field : 'deviceid',
				align : 'center',
				width : 120,
				sortable:true
			}, {
				title : '渠道来源',
				field : 'source',
				align : 'center',
				width : 80,
				sortable:true,
				formatter:function(value,row,index){
					if(value == 'dianru'){
						return '点入';
					}else if(value == 'youmi'){
						return '有米';
					}
					return value;
				}
			}, {
				title : '奖励积分',
				field : 'point',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '奖励金额',
				field : 'price',
				align : 'center',
				width : 80,
				sortable:true
			}, {
				title : '平台回调时间',
				field : 'timeStr',
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
				title : '系统接收时间',
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
			}, {
				title : '校验结果',
				field : 'result',
				align : 'center',
				width : 80,
				formatter:function(value,row,index){
					if(value == '校验正确'){
						return "<font color=green>" + value + "</font>";
					}else if(value == '校验失败'){
						return "<font color=red>" + value + "</font>";
					}
					return value;
				}
			}
			] ]
		});
	});
	
	//查看详情
	function edit(hashid){
		win = $("<div></div>").dialog({
			title:'明细数据',
			width:450,
			height:'75%',
			maximizable:true,
			modal:true,
			href:projectName+'/cfdb/sdkTasklistHis/admin/toEdit?hashid='+hashid,
			onClose:function(){
		    		$(this).dialog("destroy");
		    },
			buttons:[{
					 text:'取消',
				     iconCls:'icon-cancel',  
				 	 handler:function(){
				 		 win.dialog('destroy');
				 	 }   
				  }]
		});
	}
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load", serializeObject($("#sdkTasklistHisForm")));
	}
	
	//重置
	function reset(){
		$("#sdkTasklistHisForm").form("reset");
	}
	