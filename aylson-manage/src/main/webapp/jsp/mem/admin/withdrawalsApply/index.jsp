<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="easyui-layout" fit="true">
	<!-- 提现管理 查询 --> 
	<div region="north"  id="withdrawalsApplyArea" style="white-space: nowrap;padding: 5px; height: 50px;">
		<div style="margin-bottom: 5px">
			<form id="withdrawalsApplySearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >申请人：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="applierLike" prompt="模糊查询"/>
						</td>
						<td class="tar" >申请人电话：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="applierPhoneLike" prompt="模糊查询"/>
						</td>
						<td class="tar" >状态：</td>
						<td class="tal">
							<select name="status" class="easyui-combobox"  editable=false>
									<option value="">全部</option>
									<option value="1">处理中...</option>
									<option value="2">已转账</option>
									<option value="3">转账失败</option>
							</select>
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
	<div data-options="region:'center'"  border="false" style="overflow: hidden;width:85%">
    	<table id="datagrid"></table>
    </div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/mem/withdrawalsApply.js?date=2016090930"></script>
</html>
