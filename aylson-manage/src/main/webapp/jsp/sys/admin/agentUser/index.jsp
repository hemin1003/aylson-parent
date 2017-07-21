<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<!-- 代理商用户管理 查询 --> 
	<div region="north"  id="roleArea" style="white-space: nowrap;padding: 2px; height: 70px;">
		<div style="margin-bottom: 3px">
			<form id="agentUserSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >账号名：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="userNameLike" prompt="模糊查询"/>
						</td>
						<td class="tar" >门店名称：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="agentNameLike" prompt="模糊查询"/>
						</td>
					</tr>
					<tr>
						<td class="tar" >联系人电话：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="contactPhoneLike" prompt="模糊查询"/>
						</td>
						<td class="tar" >所属地区：</td>
						<td>
							<input  id="provinceId_s" name="provinceId" 
							data-options="editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=1', valueField:'regionId', textField:'regionName', method:'GET',
							onSelect:function(record){
								$('#cityId_s').combobox('clear');
								$('#areaId_s').combobox('clear');
								$('#cityId_s').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
							}
							" class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
							<input  id="cityId_s" name="cityId"  
							data-options="editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=2', valueField:'regionId', textField:'regionName', method:'GET',
							onSelect:function(record){
								$('#areaId_s').combobox('clear');
								$('#areaId_s').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
							}
							" class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
							<input  id="areaId_s" name="areaId" 
							data-options="editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=3', valueField:'regionId', textField:'regionName', method:'GET'" 
							class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
						</td>
						<td class="tar" >门店类型：</td>
						<td>
							<select name="isAgent" class="easyui-combobox" style="width: 80px;" editable=false required ="true">
									<option value="">全部</option>
									<option value="true" >代理</option>
									<option value="false">直营</option>
							</select>
						</td>
					    <td colspan="2" style="padding-left:20px">
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
    <div id="qrCodeDlg" class="easyui-dialog" title="推广二维码" data-options="closed:true,resizable:true" style="width:450px;height:510px;">
		<img id="qrCode" src=''/>
	</div>
	<input type="hidden" id="isLiveMode" value="${isLiveMode }"/>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/sys/agentUser.js?date=2016122711"></script>
</html>