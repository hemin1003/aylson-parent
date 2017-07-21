<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<!--产品展示管理 查询 --> 
	<div region="north"  id="productArea" style="white-space: nowrap;padding: 5px; height: 50px;">
		<div style="margin-bottom: 5px">
			<form id="productSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >产品类别：</td>
						<td class="tal" >
							<input   name="category"  
							data-options="editable:false, url:'<%=request.getContextPath()%>/sys/dictionary/getList?dicType=ProductIntroduceCategory_Item', valueField:'id', textField:'dicName', method:'GET'
							" class="easyui-combobox"  prompt="-请选择-"/>
						</td>
						<td class="tar" >产品名称：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="productNameLike" prompt="模糊查询"/>
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
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/sys/product.js?date=2017030905"></script>
</html>