<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="UTF-8">
	var tree;
	$(function() {
		  $.ajax({
	          type: 'GET',
	           dataType: "json",
	           url : '<%=request.getContextPath()%>/sys/menu/getUserMenu?parentId=1&isUserMenuTree=false',
	           success: function (data) {
	               $.each(data, function (i, n) {
	                   $('#menu').accordion('add', {
	                	   id:n.id,
	                       title: n.menuName,
	                       iconCls: '',
	                       selected: false,
	                       onBeforeExpand:function(){
	                    	  CreateMenuTree(n.menuName,n.id);
	                       }
	                  });
	                  $('#'+n.id).html('<ul class="easyui-tree" id="MenuTree-'+ n.id +'" style="margin-top: 5px;"></ul>'); 
	              }); 
	          }
	      });    
	      $('#menu').accordion({
	          autoHeight:false,
	          navigator:true
	      });    
	});
	
	
	function CreateMenuTree(mname,mid){
		$('#MenuTree-'+mid).tree({
			url : '<%=request.getContextPath()%>/sys/menu/getUserMenu?parentId=' + mid+'&isUserMenuTree=true',
			method:'GET',
		    dataType: "json",
			animate : false,
			lines : !sy.isLessThanIe8(),
			formatter:function(node){
				return node.menuName;
			},
			onClick : function(node) {
				if ( node.src && node.src != '') {
					var href;
					if (/^\//.test(node.src)) {/*以"/"符号开头的,说明是本项目地址*/
						href = node.src.substr(1);
						/*$.messager.progress({
							text : '请求数据中....',
							interval : 100
						});*/
					} else {
						href = node.src;
					}
					addTabFun({
						src : href,
						title : node.menuName
					});
				} else {
					addTabFun({
						src : 'err.jsp',
						title : '工程建设中...'
					});
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
	};
	
	function collapseAll() {
		var node = tree.tree('getSelected');
		if (node) {
			tree.tree('collapseAll', node.target);
		} else {
			tree.tree('collapseAll');
		}
	};
	
	function expandAll() {
		var node = tree.tree('getSelected');
		if (node) {
			tree.tree('expandAll', node.target);
		} else {
			tree.tree('expandAll');
		}
	};
</script>
<div class="easyui-panel" fit="true" border="false">
	<div  id="menu" class="easyui-accordion" fit="true" border="false">	</div>
</div>