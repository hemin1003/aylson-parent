<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="easyui-layout" fit="true">
	<div region="north"  id="couponApplyArea" style="white-space: nowrap;padding: 5px; height: 80px;">
		<div style="margin-bottom: 5px">
			<form id="couponApplySearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td>申请人：</td>
						<td>							
							<input class="easyui-textbox" name="applierLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>申请人电话：</td>
						<td>							
							<input class="easyui-textbox" name="applierPhoneLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>申请时间：</td>
						<td>							
							<input class="easyui-datebox" name="applyTime" style="width: 120px;" />
						</td>
					</tr>
					<tr>
						<td>客户姓名：</td>
						<td>							
							<input class="easyui-textbox" name="ownerNameLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>客户电话：</td>
						<td>							
							<input class="easyui-textbox" name="ownerPhoneLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>现金券名称：</td>
						<td>							
							<input class="easyui-textbox" name="applyCouponNameLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td colspan="2"></td>
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
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/partner/couponApply.js?date=2017011705"></script>
</html>
