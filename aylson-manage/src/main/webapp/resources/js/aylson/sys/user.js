/**
 * 用户账号管理
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
var treegrid;
var isLiveMode = $("#isLiveMode").val();
$(function() {
	treegrid = $("#treegrid").treegrid({
		method : 'GET',
		url : projectName + '/sys/user/gridTree',
		idField : 'id',
		treeField : 'userName',
		parentField : 'parentId',
		fit : true,
		fitColumns : true,
		border : false,
		singleSelect : true,
		rownumbers: true,
		toolbar : [ {
			text : "新增",
			iconCls : 'icon-add',
			handler : add
		}, {
			text : "编辑",
			iconCls : 'icon-edit',
			handler : edit
		}, '-', {
			text : "展开",
			iconCls : 'icon-redo',
			handler : function() {
				expandAll();
			}
		}, {
			text : "收起",
			iconCls : 'icon-undo',
			handler : function() {
				collapseAll();
			}
		} ],
		frozenColumns : [ [ {
			title : 'id',
			field : 'id',
			width : 50,
			hidden : true
		}, {
			field : 'userName',
			title : '用户名',
			width : 200
		}, {
			field : 'realName',
			title : '姓名',
			align:'center',
			width : 150
		} ] ],
		onLoadSuccess : function(row, data) {
			var t = $(this);
			if (data) {
				$(data).each(function(index, d) {
					if (this.state == 'closed' && this.levelNum == 0) {
						t.treegrid('expandAll');
					}
				});
			}
		}
	});
});

function collapseAll() {
	var node = treegrid.treegrid('getSelected');
	if (node) {
		$('#treegrid').treegrid('collapseAll', node.id);
	} else {
		$('#treegrid').treegrid('collapseAll');
	}
}

function expandAll() {
	var node = treegrid.treegrid('getSelected');
	if (node) {
		$('#treegrid').treegrid('expandAll', node.id);
	} else {
		$('#treegrid').treegrid('expandAll');
	}
}
/**
 * 后台-添加用户
 * @param obj
 */
function add(obj) {
	var node = treegrid.treegrid('getSelected');
	if (node == null) {
		$.messager.show({
			"title" : "系统提示",
			"msg" : "请选择一条上级记录",
			"timeout" : timeout
		});
		return;
	}
	var levelNum = node.levelNum;
	if (levelNum >= 2) {
		$.messager.show({
			"title" : "系统提示",
			"msg" : "不能在当前节点添加子节点",
			"timeout" : timeout
		});
		return;
	}
	var win;
	win = $("<div></div>").dialog({
		title : '新增',
		width : 600,
		height : 400,
		modal : true,
		href : projectName + '/sys/user/admin/toAdd?parentId=' + node.id + "&levelNum=" + (levelNum + 1),
		onClose : function() {
			$(this).dialog("destroy");
		},
		buttons : [{
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$("#userForm").form('submit',{
					type : 'POST',
					url : projectName + '/sys/user/admin/add',
					success : function(	responseData){
						if(responseData){
							var data = $.parseJSON(responseData);
							$.messager.show({
								"title" : "系统提示",
								"msg" : data.message,
								"timeout" : timeout
							});
							if(data.success){
								$.messager.confirm("提示","继续新增吗？",function(r){
									if(r){

									}else{
										$("#treegrid").treegrid("reload");
										win	.dialog('destroy');
									}
								})
							}
						}
					}
				});
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(){
				win.dialog('destroy');
			}
		} ]
	});
}

function edit() {
	var obj = treegrid.treegrid('getSelected');
	var win;
	var userId = obj.id;
	win = $("<div></div>").dialog({
		title : '编辑',
		width:'80%',
		height:'80%',
		modal : true,
		href : projectName + '/sys/user/toEdit?id=' + userId,
		onClose : function() {
			$(this).dialog("destroy");
		},
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$("#userForm").form('submit', {
					type : 'POST',
					url : projectName + '/sys/user/update',
					success : function(responseData) {
						win.dialog('destroy');
						if (responseData) {
							var data = $.parseJSON(responseData);
							$.messager.show({
								"title" : "系统提示",
								"msg" : data.message,
								"timeout" : 1000
							});
							if (data.success) {
								$("#treegrid").treegrid("reload");
							}
						}
					}
				});
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				win.dialog('destroy');
			}
		} ]
	});
}

/**
 * 注销登录
 */
function logout() {
	$.messager.confirm("提示", "确认要注销系统吗？", function (r){
		if (r){
			$.ajax({
				type:'POST',
				url : projectName + '/sys/user/admin/loginOut',
				cache : false,
				success:function(responseData){
	    			 if(responseData){
	    				var data = $.parseJSON(responseData);
	    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":timeout});
	    			 	if(data.success==true){ 
	    			 		window.location.href = projectName+"/login.jsp";
	    			 		
	    			 	}
	    			 }
	    		 }
			});
		}
    }); 
}

//个人资料
function editUserInfo(){
	var win;
	win = $("<div></div>").dialog({
		title : '编辑个人资料',
		width:800,
		height:350,
		modal : true,
		href : projectName + '/sys/user/toEdit',
		onClose : function() {
			$(this).dialog("destroy");
		},
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$("#userForm").form('submit', {
					type : 'POST',
					url : projectName + '/sys/user/admin/update',
					success : function(responseData) {
						if (responseData) {
							var data = $.parseJSON(responseData);
							$.messager.show({
								"title" : "系统提示",
								"msg" : data.message,
								"timeout" : 1000
							});
							if (data.success) {
								win.dialog('destroy');
							}
						}
					}
				});
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				win.dialog('destroy');
			}
		} ]
	});
}

//修改个人密码
function modifyPwd(){
	var win;
	win = $("<div></div>").dialog({
		title:'修改密码',
		width:400,
		height:250,
		modal:true,
		href:projectName+'/sys/user/toEditPwd',
		onClose:function(){
			$(this).dialog("destroy");
		},
		buttons:[{
			text:'确定',
		    iconCls:'icon-ok',
		    handler:function(){
		    	 $("#editPassWordForm").form('submit',{
		    		 type:'POST',
		    		 url:projectName+'/sys/user/editPassWord',
		    		 success:function(responseData){
		    			 if(responseData){
		    				var data = $.parseJSON(responseData);
		    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    			 	if(data.success){
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

//获取我的设计师推广二维码
function getMyQrCodeUrl(id){
	var url = 'http://test.aylsonclub.com/dc-web/wx/frame/getMySjsQrCodeUrl?channel=agent&memberId='+ id;
	if(isLiveMode == 'true'){
		url = 'http://sjs.aylsonclub.com/service/wx/frame/getMyQrCodeUrl?channel=agent&memberId='+ id
	}
	$('#qrCodeDlg').dialog({
		buttons:[{
			 text:'取消',
		     iconCls:'icon-cancel',  
		 	 handler:function(){
		 		$('#qrCodeDlg').dialog('close');
		 	 }   
		}]
	});
	$('#qrCodeDlg').dialog('open');
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
	
};

//获取我的安居艾臣推广二维码
function getMyOwnQrCodeUrl(id){
	var url = 'http://test.aylsonclub.com/dc-web/wx/frame/getMyQrCodeUrl?channel=agentOwner&memberId='+ id;
	if(isLiveMode == 'true'){
		url = 'http://ktz.aylsonclub.com/service/wx/frame/getMyQrCodeUrl?channel=agentOwner&memberId='+ id
	}
	$('#qrCodeDlg').dialog({
		buttons:[{
			 text:'取消',
		     iconCls:'icon-cancel',  
		 	 handler:function(){
		 		$('#qrCodeDlg').dialog('close');
		 	 }   
		}]
	});
	$('#qrCodeDlg').dialog('open');
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
	
};
