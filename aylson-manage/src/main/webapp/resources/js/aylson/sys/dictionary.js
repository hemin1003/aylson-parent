var treegrid;
$(function() {
	treegrid = $("#treegrid").treegrid({
		method : 'GET',
		url : projectName + '/sys/dictionary/gridTree',
		idField : 'id',
		treeField : 'dicName',
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
		}/*
			 * ,{ text:"删除", iconCls : 'icon-remove', handler : del }
			 */, '-', {
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
			field : 'dicName',
			title : '字典名称',
			width : 250
		},{
			field : 'dicValue',
			title : '字典值',
			width : 150
		} ] ],
		columns : [ [  {
			field : 'dicType',
			title : '类型',
			width : 150
		}, {
			field : 'dicGroup',
			title : '所属组',
			width : 150
		}, {
			field : 'isVisible',
			title : '是否可见',
			formatter:function(value,row,index){
				if(row.levelNum > 1){
					if(row.isVisible == true)return '<a href="#" onclick=isVisibleChange('+ row.id +',false)>是</a>';
					if(row.isVisible == false) return '<a href="#" onclick=isVisibleChange('+ row.id +',true)>否</a>';
				}else{
					return '';
				}
			},
			width : 50
		}, {
			field : 'remark',
			title : '备注说明',
			formatter:function(value,row,index){
				var remark = row.remark;
				if(remark != null && remark != ''){
					if(remark.length > 40) remark = remark.substring(0,40)+"...";
					return "<span title='"+row.remark+"'>" + remark +"</span>";
				}
			},
			width : 300
		} ] ],
		onLoadSuccess:function(row,data){
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

function add(obj) {
	var node = treegrid.treegrid('getSelected');
	if (node == null){
		$.messager.show({"title":"系统提示","msg":"请选择一条上级记录","timeout":timeout});
		return;
	}
	var levelNum = node.levelNum;
	if(levelNum >= 4){
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
		height : 350,
		modal : true,
		href : projectName + '/sys/dictionary/toAdd?parentId='+node.id+"&levelNum="+(levelNum+1),
		onClose : function() {
			$(this).dialog("destroy");
		},
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$("#addDicForm").form('submit', {
					type : 'POST',
					url : projectName + '/sys/dictionary/add',
					success : function(responseData) {
						if (responseData) {
							var data = $.parseJSON(responseData);
							$.messager.show({
								"title" : "系统提示",
								"msg" : data.message,
								"timeout" : timeout
							});
							if (data.success) {
								$.messager.confirm("提示", "继续新增吗？", function(r) {
									if (r) {

									} else {
										win.dialog('destroy');
										$("#treegrid").treegrid("reload");
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
			}
		} ]
	});
}

function edit() {
	var obj = treegrid.treegrid('getSelected');
	var win;
	var dicId = obj.id;
	win = $("<div></div>").dialog({
		title : '编辑',
		width : 600,
		height : 400,
		modal : true,
		href : projectName + '/sys/dictionary/toEdit?id=' + dicId,
		onClose : function() {
			$(this).dialog("destroy");
		},
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$("#addDicForm").form('submit', {
					type : 'POST',
					url : projectName + '/sys/dictionary/update',
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
	var dicId = obj.id;
	$.messager.confirm("提示", "确定删除此字典？", function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : projectName + '/sys/dictionary/update?id=' + dicId,
				dataType : "json",
				success : function(data) {
					if (data) {
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
	});
}

//修改状态
function isVisibleChange(id,isVisible){
	var tip = "确定修改为不可见吗？";
	if(isVisible ==  true){
		tip = "确定修改为可见吗？";
	}
	$.messager.confirm("提示", tip, function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				url : projectName + '/sys/dictionary/updateAny',
				dataType : "json",
				data:{
					'id':id,
					'isVisible':isVisible
				},
				success : function(data) {
					if (data) {
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
	});
}