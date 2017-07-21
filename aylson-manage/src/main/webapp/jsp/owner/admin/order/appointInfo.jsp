<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="orderForm2" method="post">
		<table class="tableForm">
			<tr>
				<th colspan="4" style="text-align:center;font-size:16px;font-weight:bolder;height:25px">[<font color=red>${appointmentVo.billCode}</font>]预约单信息：</th>
			</tr>
			<tr>
				<th colspan="4" style="text-align:rignt;font-size:16px;font-weight:bolder;height:25px;padding:5px 10px 5px 0px;">
					<%-- <c:if test="${orderVo.state < 2 }">
						<a href="#" class="easyui-linkbutton c8" onclick="edit(${orderVo.id},2)">完成生产</a>
					</c:if>
					<c:if test="${orderVo.state < 3 }">
						<a href="#" class="easyui-linkbutton c8" onclick="edit(${orderVo.id},3)">入库中</a>
					</c:if>
					<c:if test="${orderVo.state < 4 }">
						<a href="#" class="easyui-linkbutton c8" onclick="edit(${orderVo.id},4)">产品出库</a>
					</c:if> --%>
					<c:if test="${orderVo.state < 5 }">
						<a href="#" class="easyui-linkbutton c8" onclick="edit(${orderVo.id})">修改</a>
					</c:if>
				</th>
			</tr>
			<tr>
				<th>预约人姓名：</th>
				<td ><input  name="name" value="${appointmentVo.name}"
					class="easyui-textbox" data-options="required:true,readonly:true"
					style="width: 200px;" /></td>
				<th>预约人手机：</th>
				<td ><input  name="mobilePhone" value="${appointmentVo.mobilePhone}"
					class="easyui-textbox" data-options="required:true,readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>所在城市：</th>
				<td colspan="1">
					<input  id="provinceId" name="provinceId" value="${appointmentVo.provinceId}" 
				data-options="readonly:true,required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=1', valueField:'regionId', textField:'regionName', method:'GET'
				" class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				<input  id="cityId" name="cityId" value="${appointmentVo.cityId}" 
				data-options="readonly:true,required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=2', valueField:'regionId', textField:'regionName', method:'GET'
				" class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				<input  id="areaId" name="areaId" value="${appointmentVo.areaId}" 
				data-options="readonly:true,required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=3', valueField:'regionId', textField:'regionName', method:'GET'
				" 
				class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/></td>
				<th>装修地址：</th>
				<td colspan="1"><input name="address" value="${appointmentVo.address}"
					class="easyui-textbox" data-options="readonly:true,required:true"
					style="width: 400px;" /></td>
			</tr>
			<tr>
				<th>上门量尺时间：</th>
				<td><input name="homeTimeStr" value="${appointmentVo.homeTimeStr}"
					class="easyui-datetimebox" data-options="readonly:true,required:false"
					style="width: 200px;" /></td>
				<th>预计装修时间：</th>
				<td><input name="decoratingTimeStr" value="${appointmentVo.decoratingTimeStr}"
					class="easyui-datebox" data-options="readonly:true,required:false"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>上门设计师：</th>
				<td ><input name="designer" value="${appointmentVo.designer}"
					class="easyui-textbox" data-options="readonly:true,required:false"
					style="width: 200px;" /></td>
				<th>上门设计师手机：</th>
				<td ><input name="designerPhone" value="${appointmentVo.designerPhone}"
					class="easyui-textbox" data-options="readonly:true,required:false"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>装修项目：</th>
				<td >
				<select  class="easyui-combobox" name="decorateProject"  data-options="readonly:true,multiple:true,value:'${appointmentVo.decorateProject}'" style="width: 200px;" editable=false>
					<option value="1">门</option>
					<option value="2">窗</option>
					<option value="3">阳光房</option>
				</select>
				</td>
				<th>工程范围预算：</th>
				<td>
				<input  name="budgetRange" value="${appointmentVo.budgetRange}" 
				data-options="readonly:true,editable:false, url:'<%=request.getContextPath()%>/sys/dictionary/getList?dicType=budgetRange_item', valueField:'dicName', textField:'dicName', method:'GET'," class="easyui-combobox" style="width: 200px;" prompt="---------请选择---------"/>
				</td>
			</tr>
			<tr>
				<th>所属代理商：</th>
				<td colspan="1">
				<input  name="byAgentUserId" value="${appointmentVo.byAgentUserId}" 
				data-options="readonly:true,editable:false, url:'<%=request.getContextPath()%>/sys/agentUser/getList?status=1', valueField:'userId', textField:'agentName', method:'GET',
				onSelect:function(record){
					$('#byAgent').val(record.agentName);
				}" class="easyui-combobox" style="width: 200px;" prompt="---------请选择---------"/>
				</td>
				<input id="byAgent" name = "byAgent" type="hidden" value="${appointmentVo.byAgent}"/>
				<th>预约创建时间：</th>
				<td><input name="appointDateStr" value="${appointmentVo.appointDateStr}"
					class="easyui-datebox" data-options="readonly:true,required:false"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>说明：</th>
				<td colspan="3"><input name="remark" value="${appointmentVo.remark}" 
					class="easyui-textbox" readonly="true" multiline="true"	style="width: 80%;height:100px"/></td>
			</tr>
			<tr>
				<th colspan="4" style="text-align:center;font-size:16px;font-weight:bolder;height:25px">对应的设计信息：</th>
			</tr>
		<c:if test="${orderVo.designId == 0 or orderVo.designId == null or orderVo.designId == ''}">
			<tr>
				<th colspan="4" style="text-align:center;font-size:16px;color:#ff0000;font-weight:bolder;height:25px">---&nbsp;无&nbsp;----</th>
			</tr>
		</c:if>
		<c:if test="${orderVo.designId != 0 and orderVo.designId != null and orderVo.designId != ''}">
			<tr >
				<td colspan="4" style="height:150px;">
				 <table id="datagridDesign"></table>
				</td>
			</tr>
		</c:if>
		</table>
	</form>
</div>
<script>
$(function(){
	var designId = "${orderVo.designId}";
	if(designId != 0 && designId != null && designId != '' ){
		//console.info(designId);
		datagridDesign(designId);
	}
})
function datagridDesign(designId){
	datagridDesign = $('#datagridDesign').datagrid({
		method:'get',
		url : projectName+'/owner/design/admin/list?v_date=' + new Date(),
		pagination : false,
		queryParams:{
			'id':designId
		},
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
			title:'操作选项',
			align:'center',
			width : 200,
			formatter:function(value,row,index){
				var handleHtml = "";
				handleHtml += "<a href='javascript:void(0);' title='查看设计信息表内容' onclick='viewDesign("+row.id+","+row.designType+")'>查看</a>&nbsp;";
				handleHtml += "<a href='javascript:void(0);' title='查看对应设计信息表的大样图' onclick='viewDraw(\""+row.drawUrl+"\",\""+row.drawOpition+"\")'>查看大样图</a>&nbsp;";
				handleHtml += "<a href='javascript:void(0);' title='查看订货单' onclick='viewQuotation("+row.id+","+row.designType+","+row.appointId+")'>查看订货单</a>&nbsp;";
				return handleHtml;
			}
		}, {
			title : '流水单号',
			field : 'billCode',
			align:'center',
			width : 150
		},{
			title : '设计表类型',
			field : 'designType',
			align:'center',
			formatter:function(value,row,index){
				if(row.designType == 1){return "门"};
				if(row.designType == 2){return "窗"};
				if(row.designType == 3){return "阳光房"};
			},
			sortable:true,
			width : 100
		},{
			title : '下单时间',
			field : 'orderTime',
			align:'center',
			sortable:true,
			width : 150
		}]],
		columns:[[
			{
				title : '订货单下单时间',
				field : 'quoOrderTime',
				align:'center',
				width : 120
			},{
				title : '订单原价',
				field : 'orderAmount',
				align:'center',
				width : 80
			},{
				title : '实付金额',
				field : 'realAmount',
				align:'center',
				width : 80
			},{
				title : '优惠金额',
				field : 'benefitAmount',
				align:'center',
				width : 80
			},{
				title : '优惠券减免',
				field : 'couponValue',
				align:'center',
				width : 80
			},{
				title : '现金券减免',
				field : 'partnerCouponValue',
				align:'center',
				width : 80
			},{
				title : '定金',
				field : 'deposit',
				align:'center',
				width : 80
			},{
				title : '销售金额',
				field : 'salesAmount',
				align:'center',
				width : 80
			},{
				title : '结算金额',
				field : 'settleAmount',
				align:'center',
				formatter:function(value,row,index){
					var settleAmount = 0.0;
					var salesAmount = row.salesAmount;
					var deposit = row.deposit;
					var couponValue = row.couponValue;
					var partnerCouponValue = row.partnerCouponValue;
					if(salesAmount == null)salesAmount=0.0;
					if(deposit == null)deposit=0.0;
					if(couponValue == null)couponValue=0.0;
					if(partnerCouponValue == null)partnerCouponValue=0.0;
					return parseFloat(salesAmount)-parseFloat(row.deposit)-parseFloat(row.couponValue)-parseFloat(row.partnerCouponValue);
				},
				width : 80
			},{
				title : '支付方式',
				field : 'payMode',
				align:'center',
				width : 100
			},{
				title : '交货时间',
				field : 'deliveryTime',
				align:'center',
				width : 100
			}
		]]
	});
}

//查看设计信息表
function viewDesign(id,designType){
	var width = '100%';
	var height = '100%';
	if(designType == 3){
		width = '80%';
	}
	win_design = $("<div></div>").dialog({
		title:'设计信息表',
		width:width,
		height:height,
		maximizable:true,
		modal:true,
		href:projectName+'/owner/design/admin/toViewDesign?id='+id+'&designType='+designType,
		onClose:function(){
	    	$(this).dialog("destroy");
	    },
		buttons:[{
					text:'关闭',
				    iconCls:'icon-cancel',
				    handler:function(){ 
				    	win_design.dialog('destroy');
				    }   
			    }]
	});
}

//查看大样图
function viewDraw(drawUrl,drawOpition){
	$('#viewDrawDlg').dialog({
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				$('#viewDrawDlg').dialog('close');
			}
		}]
	});
	$('#viewDrawDlg').dialog('open');
	var html = "";
	if(drawOpition != 'null' && drawOpition != ''){
		html += "<div style='margin:auto;width:75%;font-size:16px;text-align:left;'><table class='tableForm'><tr>";
		html += "<th width='100px'>不满意原因：</th>";
		html += "<td style='color:#ff0000;font-size:14px;'>";
		html += drawOpition;
		html += "</td></tr></table></div>";
	}
	html += "<img src='"+drawUrl+"' />";
	$('#viewDraw').html(html);
}
//查看订货单
function viewQuotation(designId,designType){
	var width = '100%';
	var height = '100%';
	win_design = $("<div></div>").dialog({
		title:'查看订货单',
		width:width,
		height:height,
		maximizable:true,
		modal:true,
		href:projectName+'/owner/quotation/admin/toViewQuotation?designId='+designId+'&designType='+designType,
		onClose:function(){
	    	$(this).dialog("destroy");
	    },
		buttons:[{
					text:'关闭',
				    iconCls:'icon-cancel',
				    handler:function(){ 
				    	win_design.dialog('destroy');
				    }   
			    }]
	});
}
//查看设计明细的设计图附件
function viewDetailDraw(obj){
	$('#viewDetailDrawDlg').dialog({
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				$('#viewDetailDrawDlg').dialog('close');
			}
		}]
	});
	$('#viewDetailDrawDlg').dialog('open');
	var attachUrls = $(obj).prev().val();
	var html = "";
	if(attachUrls != null && attachUrls != ''){
		attachUrlArray = attachUrls.split("<;>");
		for(var i=0; i<attachUrlArray.length; i++){
			var imgUrl = attachUrlArray[i];
			if( imgUrl != null && imgUrl != ''){
				html += "<img src='"+imgUrl+"'  height='400px'/>";
			}
		}
	}
	$('#viewDetailDraw').html(html);
}
</script>