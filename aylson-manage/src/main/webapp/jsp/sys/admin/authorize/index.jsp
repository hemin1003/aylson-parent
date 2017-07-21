<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	var roleId=0;
	var roleTree;
	var gridId;
	var ckboxId;
	var formId=0;
	$(function(){
		roleTree = $('#roletree').tree({
			method:'GET',
			url : '<%=request.getContextPath()%>/sys/role/getRoleTreeGrid',
			animate : false,
			lines : !sy.isLessThanIe8(),
			onClick : function(node) {
				 roleId=node.id;
				 var selectedTab = $('#tt').tabs('getSelected');
				 var title = selectedTab.panel('options').title;
				 if(title=="角色用户" && usergrid!=null){
				 	$('#usergrid').datagrid('reload', {'roleId':node.id});
				 	return;
				 }
				 if(title=="菜单授权" && menugrid!=null){
					 $('#menugrid').treegrid('reload', {'roleId':node.id});
					 return;
				 }
				 if(title=="资源授权" && resgrid!=null){
					 $('#resgrid').treegrid('reload', {'roleId':node.id});
					 return;
				 }
				 if(title=="表单授权" && formgrid!=null){
					 $('#formgrid').datagrid('reload', {'roleId':node.id});
					 return;
				 }
				 if(title=="门户授权" && portalgrid!=null){
					 $('#portalgrid').datagrid('reload', {'roleId':node.id});
					 return;
				 }
			},
			onLoadSuccess : function(node, data) {
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.tree('expandAll');
						}
					});
				}
			}
		});
		
		$('#tt').tabs({
		    border:false,
		    onSelect:function(title,index){
		        if(title=="角色用户"){
		        	if(usergrid==null){
		        		showUserGrid();
		        	}else{
		        		$('#usergrid').datagrid('reload', {'roleId':roleId});
		        	}
		        }
		        if(title=="菜单授权"){
		        	gridId="menugrid";
		        	ckboxId="ck-men-";
		        	if(menugrid==null){
		        		showMenuGrid();
		        	}else{
		        		$('#menugrid').treegrid('reload', {'roleId':roleId});
		        	}
		        }
		        if(title=="资源授权"){
		        	gridId="resgrid";
		        	ckboxId="ck-res-";
		        	if(resgrid==null){
		        		showResGrid();
		        	}else{
		        		$('#resgrid').treegrid('reload', {'roleId':roleId});
		        	}
		        }
		        if(title=="表单授权"){
		        	gridId="formgrid";
		        	if(formgrid==null){
		        		showFormGrid();
		        	}else{
		        		$('#formgrid').datagrid('reload', {'roleId':roleId});
		        	}
		        }
		        if(title=="门户授权"){
		        	gridId="portalgrid";
		        	ckboxId="ck-ptl-";
		        	if(portalgrid==null){
		        		showPortalGrid();
		        	}else{
		        		$('#portalgrid').datagrid('reload', {'roleId':roleId});
		        	}
		        }
		    }
		});
		showUserGrid();
	})
	
	var usergrid;
	function showUserGrid(){
		usergrid = $('#usergrid').datagrid({
			method:'get',
			url : '<%=request.getContextPath()%>/sys/user/list',
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
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
				title : '用户名称',
				field : 'userName',
				align:'center',
				width : 200
			} ] ],
			columns : [ [ {
				title : '真实姓名',
				field : 'realName',
				align:'center',
				width : 50
			},{
				title : '所属组织',
				field : 'orgName',
				align:'center',
				width : 50
			},{
				title : '所属部门',
				field : 'deptName',
				align:'center',
				width : 50
			},{
				title : '所属角色id',
				field : 'roleIds',
				align:'center',
				hidden:true,
				width : 50
			},{
				title : '所属角色',
				field : 'roleNames',
				align:'center',
				width : 50
			},{
				title : '创建时间',
				field : 'createDate',
				align:'center',
				sortable:true,
				width : 50
			},{
				title : '最后修改时间',
				field : 'modifyDate',
				align:'center',
				sortable:true,
				width : 50
			},{
				title : '是否禁用',
				field : 'isDisable',
				align:'center',
				width : 50,
				formatter:function(value,row,index){if(row.isDisable==0) return '否';else return '是';}
			}]]
		});		
	}
	
	
	var menugrid;
	function showMenuGrid(){
		menugrid = $("#menugrid").treegrid({
			method:'GET',
			url : '<%=request.getContextPath()%>/sys/menu/getMenuTreeGrid',
			queryParams:{'roleId':roleId},
			idField : 'id',
			treeField : 'text',
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
						return '<input id=ck-men-'+row.id+' name="ckbox-men" type="checkbox" onclick="changeChildCheckBox('+row.id+')" checked />';
					}else{
						return '<input id=ck-men-'+row.id+' name="ckbox-men" type="checkbox" onclick="changeChildCheckBox('+row.id+')" />';
					}
				}
			},{
				field : 'text',
				title : '菜单名称',
				width : 200
			}] ],
			columns : [ [ {
				field : 'iconCls',
				title : '图标',
				width : 50
			}, {
				field : 'src',
				title : 'URI地址',
				width : 50
			}, {
				field : 'status',
				title : '状态',
				width : 50,
				formatter:function(value,row,index){if(row.status==0) return '禁用';else return '启用';}
			}] ],
			onLoadSuccess:function(row,data){
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.treegrid('expandAll');
						}
					});
				}
			}
		});
	};
	
	var resgrid;
	function showResGrid(){
		resgrid = $("#resgrid").treegrid({
			method:'GET',
			url : '<%=request.getContextPath()%>/sys/res/getResTreeGrid',
			queryParams:{'roleId':roleId},
			idField : 'id',
			treeField : 'text',
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
						return '<input id=ck-res-'+row.id+' name="ckbox-res" type="checkbox" onclick="changeChildCheckBox('+row.id+')" checked/>';
					}else{
						return '<input id=ck-res-'+row.id+' name="ckbox-res" type="checkbox" onclick="changeChildCheckBox('+row.id+')"/>';
					}
				}
			},{
				field : 'text',
				title : '菜单名称',
				width : 200
			}] ],
			columns : [ [ {
				field : 'src',
				title : 'URI地址',
				width : 50
			}, {
				field : 'status',
				title : '状态',
				width : 50,
				formatter:function(value,row,index){if(row.status==0) return '禁用';else return '启用';}
			}] ],
			
			onLoadSuccess:function(row,data){
				var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							//if(d.id==1){
								t.treegrid('expandAll');
							//}
						}
					});
				}
			}
		});
	};
	
	
	var formgrid;
	function showFormGrid(){
		formgrid = $("#formgrid").datagrid({
			method:'GET',
			url : '<%=request.getContextPath()%>/sys/form/getFormsByRoleId',
			queryParams:{'roleId':roleId},
			fit : true,
			fitColumns : true,
			border : false,
			singleSelect:true,
			frozenColumns : [ [{
				title : 'id',
				field : 'id',
				width : 50,
				hidden : true
			},{
				title : 'menuId',
				field : 'menuId',
				width : 50,
				hidden : true
			},{
				field : 'menuName',
				title : '菜单名称',
				width : 300
			}] ],
			columns : [ [ {
				field : 'formCode',
				title : '表单编号',
				width : 100
			}, {
				field : 'formName',
				title : '表单名称',
				width : 100
			},{
				field : 'isCustomForm',
				title : '表单类型',
				width : 100,
				formatter:function(value,row,index){
					if(row.isCustomForm==true){
						return '自定义表单';
					}else{
						return '默认表单';
					}
				}
			},{
				field : 'createDate',
				title : '创建时间',
				width : 100
			}] ],
			onSelect:function(index, data){
				formId=data.id;
				loadCurrectFormInfo();
			}
		});
	};
	
	
	function loadCurrectFormInfo(isReset){
		$.ajax({    
            url:'<%=request.getContextPath()%>/sys/form/getRoleCurrForm',
            data:{    
            	id : formId,
            	roleId:roleId
            },    
            type:'get',    
            cache:false,        
            success:function(responseData) {    
            	if(responseData){
    				var data = $.parseJSON(responseData);
    				
    				if(data.success&&data.data!=null){
    					if(data.data.isCustomForm==true){
    						$('#customJsonToolbar').textbox('setValue', data.data.customJsonToolbar);
	    					$('#customJsonFrozenColumns').textbox('setValue', data.data.customJsonFrozenColumns);
	    					$('#customJsonColumns').textbox('setValue', data.data.customJsonColumns);
    					}else{
	    					$('#customJsonToolbar').textbox('setValue', data.data.jsonToolbar);
	    					$('#customJsonFrozenColumns').textbox('setValue', data.data.jsonFrozenColumns);
	    					$('#customJsonColumns').textbox('setValue', data.data.jsonColumns);
    					}
    				}
    			 } 
             },    
             error : function() {    
                  alert("异常！");    
             }    
        });		
	}
	
	var portalgrid;
	function showPortalGrid(){
		portalgrid = $("#portalgrid").datagrid({
			method:'GET',
			url : '<%=request.getContextPath()%>/sys/portal/list',
			queryParams:{'roleId':roleId},
			fit : true,
			fitColumns : true,
			border : false,
			singleSelect:true,
			toolbar:[{
				text:"保存",
				iconCls : 'icon-save',
				handler : saveRolePortal
			},'-',{
				text:"重置",
				iconCls : 'icon-undo',
				handler : function(){
					$('#portalgrid').datagrid('reload', {'roleId':roleId});
				}
			}],
			columns : [ [ {
				title : 'id',
				field : 'id',
				width : 50,
				hidden : true
			},{
				field : 'ck',
				width : 15,
				formatter:function(value,row,index){
					if(row.ck){
						return '<input id=ck-ptl-'+row.id+' name="ckbox-ptl" type="checkbox" checked/>';
					}else{
						return '<input id=ck-ptl-'+row.id+' name="ckbox-ptl" type="checkbox" />';
					}
				}
			},{
				field : 'title',
				title : '门户名称',
				width : 150
			},{
				field : 'src',
				title : 'url地址',
				width : 150
			},{
				field : 'height',
				title : '高度',
				width : 100
			},{
				field : 'closable',
				title : '是否关闭',
				width : 100,
				formatter:function(value,row,index){if(row.closable==true) return '是';else return '否';}
			},{
				field : 'collapsible',
				title : '是否收缩',
				width : 100,
				formatter:function(value,row,index){if(row.collapsible==true) return '是';else return '否';}
			}] ]
		});
	};
	
	function changeChildCheckBox(rowId){
		var children = $('#'+gridId).treegrid("getChildren",rowId);
        for(var i=0;i<children.length;i++){
       		if ($('#'+ckboxId+rowId).is(':checked')){
       			$('#'+ckboxId+children[i].id).prop("checked",'checked');
       		}else{
       			$('#'+ckboxId+children[i].id).prop("checked",'');
       		}
        }
	};
	
	function saveRoleMenu(){
		if(roleId==0){
			$.messager.show({"title":"系统提示","msg":"请选择角色设置菜单，再保存","timeout":1000});
			return;
		}
		var result = new Array();
        $("[name = ckbox-men]:checkbox").each(function () {
            if ($(this).is(":checked")) {
                result.push($(this).attr("id").substring(7));
            }
        });

        var aj = $.ajax({    
            url:'<%=request.getContextPath()%>/sys/menu/saveRoleMenu',
            data:{    
                  roleId : roleId,    
                  menuIds : result.join(",")
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
	
	
	function saveRoleRes(){
		if(roleId==0){
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
            url:'<%=request.getContextPath()%>/sys/res/saveRoleRes',
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
	
	
	function saveRolePortal(){
		if(roleId==0){
			$.messager.show({"title":"系统提示","msg":"请选择角色设置门户，再保存","timeout":1000});
			return;
		}
		var result = new Array();
        $("[name = ckbox-ptl]:checkbox").each(function () {
            if ($(this).is(":checked")) {
                result.push($(this).attr("id").substring(7));
            }
        });

        var aj = $.ajax({    
            url:'<%=request.getContextPath()%>/sys/portal/saveRolePortal',
            data:{    
                  roleId : roleId,    
                  portalIds : result.join(",")
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
	
	function saveRoleForm(){
		if(roleId==0 || formId==0){
			$.messager.show({"title":"系统提示","msg":"请选择角色设置表单，再保存","timeout":1000});
			return;
		}
		
		$.messager.confirm('提示', '确认要提交吗？', function(r) {
			if (r) {
		        var aj = $.ajax({    
		            url:'<%=request.getContextPath()%>/sys/form/saveRoleForm',
		            data:{    
		            	  id : formId,
		                  roleId : roleId,    
		                  customJsonToolbar:$('#customJsonToolbar').val(),
		                  customJsonFrozenColumns:$('#customJsonFrozenColumns').val(),
		                  customJsonColumns:$('#customJsonColumns').val()
		            },    
		            type:'post',    
		            cache:false,        
		            success:function(responseData) {    
		            	if(responseData){
		    				var data = $.parseJSON(responseData);
		    				$('#formgrid').datagrid('reload', {'roleId':roleId});
		    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    			 } 
		             },    
		             error : function() {    
		                  alert("异常！");    
		             }    
		        });  
			}});
	};
</script>
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="overflow: hidden;">
			<div id="tt" class="easyui-tabs" fit="true" border="false" tabPosition="top">
		        <div title="角色用户">
		           <table id="usergrid"></table>
		        </div>
		        <div title="菜单授权">
		           <table id="menugrid" class="easyui-treegrid"></table>
		        </div>
		        <div title="资源授权">
		           <table id="resgrid" class="easyui-treegrid"></table>
		        </div>
		        <div title="表单授权">
		        	<div class="easyui-layout" fit="true">
		         	  <table region="center" id="formgrid"></table>
		         	  <div id="ft" region="south" class="easyui-tabs" border="false" split="true" height="300px" tabPosition="top" data-options="tools:'#tab-tools'">
		         	  	 <div title="工具栏">
				            <input class="easyui-textbox" type="text" id="customJsonToolbar" name="customJsonToolbar" data-options="multiline:true" style="height:100%;width:100%; padding:20px 20px 20px 20px;"></input>
		         	  	 </div>
		         	  	 <div title="固定列">
		         	  	 	<input class="easyui-textbox" type="text" id="customJsonFrozenColumns" name="customJsonFrozenColumns" data-options="multiline:true" style="height:100%;width:100%; padding:20px 20px 20px 20px;"></input>
		         	  	 </div>
		         	  	 <div title="数据列">
		         	  	 	<input class="easyui-textbox" type="text" id="customJsonColumns" name="customJsonColumns" data-options="multiline:true" style="height:100%;width:100%; padding:20px 20px 20px 20px;"></input>
		         	  	 </div>
		         	  </div>
		            </div>
		            <div id="tab-tools">
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-save'" onclick="saveRoleForm()">保存</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-reload'" onclick="loadCurrectFormInfo()">重置</a>
				    </div>
		        </div>
		        <div title="门户授权">
		           <table id="portalgrid"></table>
		        </div>
		    </div>
		</div>
		<div region="west" border="false" title="系统角色" split="true" iconCls="icon-large-chart" style="overflow: hidden; width:280px;">
			<ul id="roletree" style="margin-top: 0px;"></ul>
		</div>
	</div>
</body>
</html>