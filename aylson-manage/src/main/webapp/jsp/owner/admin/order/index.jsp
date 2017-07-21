<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div region="north"  id="orderArea" style="white-space: nowrap;padding: 5px; height: 80px;">
		<div style="margin-bottom: 5px">
			<form id="orderSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >客户姓名：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="nameLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td class="tar" >客户电话：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="mobilePhoneLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td class="tar" >下单时间：</td>
						<td class="tal" >
							<input class="easyui-datebox" name="createTimeStr"  style="width: 120px;"/>
						</td> 
					</tr>
					<tr>
						<td class="tar" >订单号：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="orderNoLike" style="width: 120px;" prompt="模糊查询"/>
						</td> 
						<td class="tar" >预约单号：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="appointNoLike" style="width: 120px;" prompt="模糊查询"/>
						</td> 
						<td class="tar" >设计单号：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="designNoLike" style="width: 120px;" prompt="模糊查询"/>
						</td> 
						<td class="tar" >更新时间：</td>
						<td class="tal" >
							<input class="easyui-datebox" name="updateTimeStr"  style="width: 120px;"/>
						</td>
					    <td style="padding-left:20px">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch()">搜索</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px" onclick="reset()">重置</a>
						</td> 
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'center'"  border="false" style="overflow: hidden;width:80%">
    	<table id="datagrid"></table>
    </div>
    <div id="viewDetailDrawDlg" class="easyui-dialog" title="查看附件" data-options="closed:true,resizable:true" style="width:60%;height:60%;">
		<div id="viewDetailDraw" style="text-align:center;">
		</div>
	</div>
  	<div id="viewDrawDlg" class="easyui-dialog" title="查看大样图" data-options="closed:true,resizable:true" style="width:80%;height:80%;">
		<div id="viewDraw" style="text-align:center;">
		</div>
	</div>
	<div id="chooseCouponDlg" class="easyui-dialog" title="选择优惠券" data-options="closed:true,resizable:true" style="width:700px;height:400px;">
		<table id="datagridCoupon"></table>
	</div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/owner/order.js?date=2017020826"></script>
</html>