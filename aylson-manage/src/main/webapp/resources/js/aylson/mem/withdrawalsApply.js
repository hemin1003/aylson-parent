	/**
	 * 提现申请管理.js
	 */
	var datagrid;
	$(function() { 
		datagrid = $('#datagrid').datagrid({
			method:'get',
			url : projectName+'/mem/withdrawalsApply/admin/list?v_date=' + new Date(),
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'id',
			singleSelect:false,
			/*checkOnSelect:false,
			selectOnCheck:false,*/
			rownumbers: true,
			toolbar:[ {
				text:"导出提现申请",
				iconCls : 'icon-add',
				handler : exportApply
			}],
 			frozenColumns : [[{
				field : 'id',
				title:'序号',
				align:'center',
				hidden:true,
				width : 50
			},{
				field : 'ck',
				checkbox:true,
				align:'center',
				width : 50
			}, {field : 'opt',
				title:'操作选项',
				align:'center',
				width : 150,
				formatter:function(value,row,index){
					var handleHtml = "";
					if(row.status == 1){
						handleHtml += "<a href='javascript:void(0);' onclick='transfer ("+row.id+")'>转账</a>&nbsp;";
					}
					handleHtml += "<a href='javascript:void(0);' onclick='view("+row.id+")'>查看</a>&nbsp;";
					return handleHtml;
				}
			}, {
				title : '申请时间',
				field : 'applyTime',
				align:'center',
				width : 150
			}, {
				title : '申请人',
				field : 'applier',
				align:'center',
				width : 100
			}, {
				title : '申请人电话',
				field : 'applierPhone',
				align:'center',
				width : 100
			}, {
				title : '提现金额（￥）',
				field : 'amount',
				align:'center',
				width : 100
			}, {
				title : '持卡人姓名',
				field : 'bankholder',
				align:'center',
				width : 100
			}, {
				title : '银行卡号',
				field : 'bankNum',
				align:'center',
				width : 200
			}, {
				title : '所属银行',
				field : 'bankName',
				align:'center',
				width : 200
			}]],
			columns:[[{
				title : '状态',
				field : 'status',
				align:'center',
				sortable:true,
				formatter:function(value,row,index){
					if(row.status == 1) return "<font color=grey>处理中</font>";
					if(row.status == 2) return "<font color=green>已转账</font>";
					if(row.status == 3) return "<font color=red>转账失败</font>";
				},
				width : 150
			}, {
				title : '操作人',
				field : 'dealer',
				align:'center',
				width : 100
			}, {
				title : '操作时间',
				field : 'dealTime',
				align:'center',
				width : 150
			}, {
				title : '备注',
				field : 'remark',
				align:'center',
				formatter:function(value,row,index){
					var remark = row.remark;
					if(remark != null && remark != ''){
						if(remark.length > 12) remark = remark.substring(0,12)+"...";
						return "<span title='"+row.remark+"'>" + remark +"</span>";
					}
				},
				width : 250
			} ] ],
			onLoadSuccess:function(data){
				 if (data.rows.length > 0){
					 for (var i = 0; i < data.rows.length; i++){
						 if (data.rows[i].status != 1){
		                     $("input[type='checkbox']")[i + 1].disabled = true;
		                 }
					 }
				 }
			},
			onCheckAll:function(rows){
				 $("input[type='checkbox']").each(function(index, el){
		                //如果当前的复选框不可选，则不让其选中
		                if (el.disabled == true) {
		                	el.checked = false;
		                	//$('#datagrid').datagrid('unselectRow', index - 1);
		                }
		         })
			},
			onClickRow: function(rowIndex, rowData){
		        //加载完毕后获取所有的checkbox遍历
		        $("input[type='checkbox']").each(function(index, el){
		            //如果当前的复选框不可选，则不让其选中
		            if (el.disabled == true) {
		                $('#datagrid').datagrid('unselectRow', index - 1);
		            }
		        })
		    }
			
		});
		
	});			
	
	//转账
	function transfer(sid){
		win = $("<div></div>").dialog({
			title:'转账',
			width:800,
			height:600,
			maximizable:true,
			modal:true,
			href:projectName+'/mem/withdrawalsApply/admin/toTransfer?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
					text:'转账成功',
				    iconCls:'icon-ok',
				    handler:function(){
				    	var transferProof = $("#transferProof").val();
				    	if(transferProof == ""){
				    		$.messager.show({"title":"系统提示","msg":"转账成功必须上传转账凭证","timeout":1000});
				    		return;
				    	}
				    	var winTip = $.messager.progress({
							title:'请稍候...',
							msg:'正在处理...'
						});
				    	$("#withdrawalsApplyForm").form('submit',{
				    		 type:'POST',
				    		 url:projectName+'/mem/withdrawalsApply/admin/transfer?status=2',
				    		 success:function(responseData){
				    			  $.messager.progress('close');
				    			 if(responseData){
				    				var data = $.parseJSON(responseData);
				    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				    			 	if(data.success){
				    			 		$("#datagrid").datagrid("reload");
				    			 		win.dialog('destroy');
				    				}
				    			 } 
				    		 }
				    	 });
				     }   
				   },{
						text:'转账失败',
					    iconCls:'icon-ok',
					    handler:function(){
					    	var remark = $("#remark").textbox("getValue");
					    	if(remark == ""){
					    		$.messager.show({"title":"系统提示","msg":"转账失败必须备注原因","timeout":1000});
					    		return;
					    	}
					    	var winTip = $.messager.progress({
								title:'请稍候...',
								msg:'正在处理...'
							});
					    	$("#withdrawalsApplyForm").form('submit',{
					    		 type:'POST',
					    		 url:projectName+'/mem/withdrawalsApply/admin/transfer?status=3',
					    		 success:function(responseData){
					    			  $.messager.progress('close');
					    			 if(responseData){
					    				var data = $.parseJSON(responseData);
					    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
					    			 	if(data.success){
					    			 		$("#datagrid").datagrid("reload");
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
	
	//查看
	function view(sid){
		win = $("<div></div>").dialog({
			title:'查看',
			width:800,
			height:600,
			maximizable:true,
			modal:true,
			href:projectName+'/mem/withdrawalsApply/admin/toView?id='+sid,
			onClose:function(){
		    	$(this).dialog("destroy");
		    },
			buttons:[{
						 text:'关闭',
					     iconCls:'icon-cancel',  
					 	 handler:function(){
					 		 win.dialog('destroy');
					 	 }   
					}]
		});
	}
	
	//上传转账凭证
	function uploadTransferProof(id,target,bucket){
		if(bucket == null){
		 	$.messager.show({"title":"系统提示","msg":"找不到对应的资源空间，请联系管理员","timeout":1000});
		 	return;
		}
		var win;
		win = $("<div></div>").dialog({
			title:'上传转账凭证',
			width:500,
			height:200,
			modal:true,
			href:projectName+'/sys/fileHandle/toFileUpload',
			onClose:function(){
				$(this).dialog("destroy");
			},
			buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	var winTip = $.messager.progress({
						title:'请稍候...',
						msg:'正在上传...'
					});
			    	var filePath = $("#fileName").filebox('getValue');
			    	$("#bucket").val(bucket);
			    	$("#fileUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/fileUpload',
			    		 //url:projectName+'/sys/fileHandle/uploadTest',
			    		 success:function(responseData){
			    			 $.messager.progress('close');
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		var fileName = data.data;
			    			 		var html = "<li>";
			    			 		html += "<img class='naviga_big' alt='' src='"+fileName+"' style='width:50px;height:50px;'>";
			    			 		html += "<a href='#' onclick='delTransferProof(this)'>删除</a>";
			    			 		$("#"+id).append(html);
			    			 		resetTransferProof();
			    			 		viewImg();
			    			 		win.dialog('destroy');
			    				}
			    			 } 
			    		 }
			    	});
			    	}
//			     }   
			   },{
				 text:'取消',
			     iconCls:'icon-cancel',  
			 	 handler:function(){
			 		 win.dialog('destroy');
			 	 }   
			  }]
		});
	}
	
	//重置转账凭证
	function resetTransferProof(){
		var transferProof = "";
		$.each($(".naviga_big"),function(n,value) {   
	          transferProof += value.src+";";
		}); 
		$("#transferProof").val(transferProof);
	}
	
	//删除转账凭证
	function delTransferProof(obj){
		$(obj).parent().remove();
		resetTransferProof();
	}
	
	//查看转账凭证图
	function viewImg(){
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		var h = $(window).height();
		var hh = h/2;
		$(".naviga_big").each(function(){
			$(this).hover(function(e){
				$("#imgBig").attr({"src":$(this).attr("src")});
				$("#imgBig").css({"width":600,"height":600});
				//alert(e.pageY+";"+h/2)
			    $("#divImage").css("top",yOffset + "px")
				if(e.pageX < w/2){
					$("#divImage").css({
						left: e.pageX + xOffset + "px",
						right: "auto"
					}).fadeIn("fast");
				}else{
					$("#divImage").css("right",(w - e.pageX + yOffset) + "px").css("left", "auto").fadeIn("fast");	
				}
				$("#divImage").show();
			},function(){
				$("#divImage").hide();
			}).mousemove(function(e){
				$("#divImage").css("top",yOffset + "px")
				if(e.pageX < w/2){
					$("#divImage").css("left",(e.pageX + yOffset) + "px").css("right","auto");
				}else{
					$("#divImage").css("right",(w - e.pageX + yOffset) + "px").css("left","auto");
				}
			});	
		})
	}
	
	//刷新
	function reload(){
		$("#datagrid").datagrid("reload");
	}
	
	
	//搜索
	function doSearch(){
		$("#datagrid").datagrid("load",serializeObject($("#withdrawalsApplySearchForm")));
	}
	
	
	//重置
	function reset(){
		$("#withdrawalsApplySearchForm").form("reset");
	}
	
	function exportApply(){
		var rows =  $('#datagrid').datagrid('getChecked');
		if (rows.length == 0){
			$.messager.show({"title":"系统提示","msg":"至少选择一条数据","timeout":1000});
			return;
		}
		var applyIds = [];
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if(row.status == 1){
				applyIds.push(row.id);
			}
		}
		window.location = projectName + '/mem/withdrawalsApply/admin/exportApply?applyIds='+applyIds.join(",");
	
	}
	