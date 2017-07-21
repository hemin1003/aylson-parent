var roleTree;    //角色树列表
var menuTree;    //菜单树列表
var userTree;    //用户树列表
var resourceTree;//资源树列表
var roleId= 0;   //所选角色id

/*
 * 说明：角色-菜单处理
 * 
 */
//显示角色-菜单
function showMenuGrid(){
	menuTree = $("#menugrid").treegrid({
			method : 'GET',
			url : projectName + '/sys/menu/getSyncGridTree',
			queryParams:{'roleId':roleId},
			idField : 'id',
			treeField : 'menuName',
			parentField : 'parentId',
			fit : true,
			fitColumns : true,
			border : false,
			singleSelect : true,
			toolbar:[{
				text:"保存",
				iconCls : 'icon-save',
				handler :saveRoleMenu
			},'-',{
				text:"重置",
				iconCls : 'icon-undo',
				handler:function(){
					$('#menugrid').treegrid('reload', {'roleId':roleId});
				}
			}],
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				hidden : true
			},{
				field : 'ck',
				width : 30,
				formatter:function(value,row,index){
					if(row.ck){
						return '<input id=ck-men-'+row.id+' name="ckbox-men" type="checkbox" onclick="changeChildCheckBox(\'menugrid\',\'ck-men-\','+ row.id +')" checked />';
					}else{
						return '<input id=ck-men-'+row.id+' name="ckbox-men" type="checkbox" onclick="changeChildCheckBox(\'menugrid\',\'ck-men-\','+ row.id +')" />';
					}
				}
			}, {
				field : 'menuName',
				title : '菜单名称',
				width : 200
			} ] ],
			columns : [ [ /*{
				field : 'iconCls',
				title : '图标',
				width : 50
			},*/{
				field : 'type',
				title : '类型',
				align:'center',
				formatter:function(value,row,index){
					if(row.type == 1) return "菜单";
					if(row.type == 2) return "<font color=blue>操作</font>";
				},
				width :5
			}, {
				field : 'remark',
				title : '备注说明',
				align:'center',
				width : 50
			},{
				field : 'src',
				title : '菜单地址',
				width : 50
			}] ]
	
		});
	};

//保存所选角色菜单
function saveRoleMenu(){
	if(roleId == 0){
		$.messager.show({"title":"系统提示","msg":"请选择角色设置菜单，再保存","timeout":timeout});
		return;
	}
	var result = new Array();//获取勾选的菜单id数组
    $("[name = ckbox-men]:checkbox").each(function () {
        if ($(this).is(":checked")) {
            result.push($(this).attr("id").substring(7));
        }
    });
    var aj = $.ajax({    
        url:projectName + '/sys/menu/saveRoleMenu',
        data:{    
              roleId : roleId,    
              menuIds : result.join(",")
        },    
        type:'post',    
        cache:false,        
        success:function(responseData) {    
        	if(responseData){
				var data = $.parseJSON(responseData);
			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":timeout});
			 } 
         },    
         error : function() {    
              alert("异常！");    
         }    
    });  
};
	
/*
 * 说明：角色-资源处理
 * 
 */
//显示角色-资源	
function showResGrid(){
	resgrid = $("#resgrid").treegrid({
		method:'GET',
		url : projectName + '/sys/res/getSyncGridTree',
		queryParams:{'roleId':roleId},
		idField : 'id',
		treeField : 'resName',
		parentField : 'parentId',
		lines: true,
        rownumbers: true,
		fit : true,
		fitColumns : true,
		border : false,
		singleSelect:true,
		selectOnCheck:false,
		checkOnSelect:false,
		toolbar:[{
			text:"保存",
			iconCls : 'icon-save',
			handler :saveRoleRes
		},'-',{
			text:"重置",
			iconCls : 'icon-undo',
			handler:function(){
				$('#resgrid').treegrid('reload', {'roleId':roleId});
			}
		}],
		frozenColumns : [ [ {
			title : 'id',
			field : 'id',
			width : 50,
			hidden : true
		},{
			field : 'ck',
			width : 30,
			formatter:function(value,row,index){
				if(row.ck){
					return '<input id=ck-res-'+row.id+' name="ckbox-res" type="checkbox" onclick="changeChildCheckBox(\'resgrid\',\'ck-res-\','+ row.id +')" checked/>';
				}else{
					return '<input id=ck-res-'+row.id+' name="ckbox-res" type="checkbox" onclick="changeChildCheckBox(\'resgrid\',\'ck-res-\','+ row.id +')"/>';
				}
			}
		},{
			field : 'resName',
			title : '资源名称',
			width : 300
		},{
			field : 'src',
			title : '资源地址',
			width : 200
		},{
			field : 'remark',
			title : '备注说明',
			width : 200
		}] ]
	});
};	

//保存所选角色资源
function saveRoleRes(){
	if(roleId == 0){
		$.messager.show({"title":"系统提示","msg":"请选择角色设置资源，再保存","timeout":1000});
		return;
	}
	var result = new Array();
    $("[name = ckbox-res]:checkbox").each(function () {
        if ($(this).is(":checked")) {
            result.push($(this).attr("id").substring(7));
        }
    });

    var aj = $.ajax({    
        url:projectName + '/sys/res/saveRoleRes',
        data:{    
              roleId : roleId,    
              resIds : result.join(",")
        },    
        type:'post',    
        cache:false,        
        success:function(responseData) {    
        	if(responseData){
				var data = $.parseJSON(responseData);
			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			 } 
         },    
         error : function() {    
              alert("异常！");    
         }    
    });  
};
	

$(function() {
	//加载角色树
	roleTree = $("#roletree").tree({
		method:'GET',
		url : projectName + '/sys/role/getList',
		fit : true,
		fitColumns : true,
		border : false,
		singleSelect:true,
		formatter:function(node){
			return node.roleName;
		},
		onClick : function(node) {
			 console.info(node);
			 if(node.levelNum == 0){
				 roleId = 0;
				 return;
			 }
			 roleId = node.id;  //获取角色id
			 var selectedTab = $('#tt').tabs('getSelected');
			 var title = selectedTab.panel('options').title;
			 if(title == "菜单授权"){
				// $('#menugrid').treegrid('reload', {'roleId':node.id});
				 showMenuGrid();
				 return;
			 }
			 if(title=="资源授权"){
				 //$('#resgrid').treegrid('reload', {'roleId':node.id});
				 showResGrid();
				 return;
			 }
		},
		onLoadSuccess:function(row,data){
			/*var t = $(this);
			if (data) {
				$(data).each(function(index, d) {
					if (this.state == 'closed') {
						t.tree('expandAll');
					}
				});
			}*/
			showMenuGrid();//显示第一个tab的内容
		}
	});
	
	//添加选择卡：【菜单授权】
	$('#tt').tabs({
	    border:false,
	    onSelect:function(title,index){
	        if(title=="菜单授权"){
//	        	if(roleId == 0){
//	        		showMenuGrid();
//	        	}else{
//	        		$('#menugrid').treegrid('reload', {'roleId':roleId});
//	        	}
	        	showMenuGrid();
	        }
	        if(title == "资源授权"){
//	        	if(roleId == 0){
//	        		showResGrid();
//	        	}else{
//	        		$('#resgrid').treegrid('reload', {'roleId':roleId});
//	        	}
	        	showResGrid();
	        }
	    }
	});
})
	
	
	//修改复选框状态
	/*function changeChildCheckBox(rowId){
		var children = $('#menugrid').treegrid("getChildren",rowId);
	    for(var i=0;i<children.length;i++){
	   		if ($('#ck-men-'+rowId).is(':checked')){
	   			$('#ck-men-'+children[i].id).prop("checked",'checked');
	   		}else{
	   			$('#ck-men-'+children[i].id).prop("checked",'');
	   		}
	    }
	};*/
	
	//修改复选框状态
	function changeChildCheckBox(gridId,ckboxId,rowId){
		var children = $('#'+gridId).treegrid("getChildren",rowId);
		var parent = $('#'+gridId).treegrid("getParent",rowId);
		if(parent != null){
			if ($('#'+ckboxId+rowId).is(':checked')){
       			$('#'+ckboxId+parent.id).prop("checked",'checked');
       		}
		}
        for(var i=0;i<children.length;i++){
       		if ($('#'+ckboxId+rowId).is(':checked')){
       			$('#'+ckboxId+children[i].id).prop("checked",'checked');
       		}else{
       			$('#'+ckboxId+children[i].id).prop("checked",'');
       		}
        }
	};
	
	