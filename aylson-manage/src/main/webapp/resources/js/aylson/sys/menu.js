/**
 * 菜单管理-js
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
var treegrid;
$(function() {
	treegrid = $("#treegrid").treegrid({
		method : 'GET',
		url : projectName + '/sys/menu/getSyncGridTree',
		idField : 'id',
		treeField : 'menuName',
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
		}, {
			text : "删除",
			iconCls : 'icon-remove',
			handler : del
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
			field : 'menuName',
			title : '菜单名称',
			width : 200
		} ] ],
		columns : [ [{
			field : 'type',
			title : '类型',
			align:'center',
			formatter:function(value,row,index){
				if(row.type == 1) return "菜单";
				if(row.type == 2) return "<font color=blue>操作</font>";
			},
			width :5
		}, {
			field : 'src',
			title : '菜单地址',
			align:'center',
			width : 50
		}, {
			field : 'remark',
			title : '备注说明',
			align:'center',
			width : 50
		} ] ]

		/*onLoadSuccess : function(row, data) {
			var t = $(this);
			if (data) {
				$(data).each(function(index, d) {
					console.info(this);
					if (this.state == 'open' && this.levelNum != 0) {
						t.treegrid('collapseAll');
					}
				});
			}
		}*/
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

function add(obj) {
	var node = treegrid.treegrid('getSelected');
	if (node == null){
		$.messager.show({"title":"系统提示","msg":"请选择一条上级记录","timeout":timeout});
		return;
	}
	var levelNum = node.levelNum;
	if(levelNum >= 3){
		$.messager.show({
			"title" : "系统提示",
			"msg" : "不能在当前节点添加子节点",
			"timeout" : timeout
		});
		return;
	}
	var win;
	win = $("<div></div>").dialog({
		title : '新增菜单',
		width : 500,
		height : 350,
		modal : true,
		href : projectName + '/sys/menu/admin/toAdd?parentId='+node.id+"&levelNum="+(levelNum+1),
		onClose : function() {
			$(this).dialog("destroy");
		},
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$("#menuForm").form('submit', {
					type : 'POST',
					url : projectName + '/sys/menu/admin/add',
					success : function(responseData) {
						//win.dialog('destroy');
						if (responseData) {
							var data = $.parseJSON(responseData);
							$.messager.show({
								"title" : "系统提示",
								"msg" : data.message,
								"timeout" : 1000
							});
							if (data.success) {
								$.messager.confirm("提示", "继续新增吗？", function(r) {
									if (r) {

									} else {
										$("#treegrid").treegrid("reload");
										win.dialog('destroy');
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
			handler : function() {
				win.dialog('destroy');
				$("#treegrid").treegrid("reload");
			}
		} ]
	});
}

function edit() {
	var obj = treegrid.treegrid('getSelected');
	var win;
	var menuId = obj.id;
	win = $("<div></div>").dialog({
		title : '编辑',
		width : 500,
		height : 350,
		modal : true,
		href : projectName + '/sys/menu/admin/toEdit?id=' + menuId,
		onClose : function() {
			$(this).dialog("destroy");
		},
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$("#menuForm").form('submit', {
					type : 'POST',
					url : projectName + '/sys/menu/admin/update',
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

function del() {
	var obj = treegrid.treegrid('getSelected');
	var menuId = obj.id;
	var children = treegrid.treegrid("getChildren",menuId);
	if(children.length > 0){
		$.messager.show({"title":"系统提示","msg":"请先删除下级节点","timeout":timeout});
		return;
	}
	$.messager.confirm("提示", "确定删除此菜单？", function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : projectName + '/sys/menu/deleteById?id=' + menuId,
				dataType : "json",
				success : function(data) {
					if (data) {
						$.messager.show({
							"title" : "系统提示",
							"msg" : data.message,
							"timeout" : timeout
						});
						if (data.success) {
							$("#treegrid").treegrid("reload");
						}
					}

				}
			});
		}
	});
}
