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
	<div region="north"  id="pioneerAgentArea" style="white-space: nowrap;padding: 5px; height: 75px;">
		<div style="margin-bottom: 5px">
			<form id="pioneerAgentSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >代理商名称：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="agentNameLike" prompt="模糊查询"/>
						</td>
						<td class="tar" >代理商电话：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="contactPhoneLike" prompt="模糊查询"/>
						</td>
					<c:if test="${flag eq 'joined' }">
						<td class="tar" >邀请人：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="submitterLike" prompt="模糊查询"/>
						</td>	
					</c:if>
					</tr>
					<tr>
						<td class="tar" >地址：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="addressLike" prompt="模糊查询"/>
						</td>
						<td class="tar" >商店名称：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="shopNameLike" prompt="模糊查询"/>
						</td>
						<c:if test="${flag eq 'joining' }">
						<td class="tar" >状态：</td>
						<td class="tal">
							<select name="status" class="easyui-combobox"  editable=false>
									<option value="">全部</option>
									<option value="1">待审核</option>
									<option value="2">已审核</option>
									<option value="3">已签约</option>
									<option value="4">已开业</option>
									<option value="21">审核不通过</option>
									<option value="31">签约失败</option>
									<option value="41">开业失败</option>
							</select>
						</td>
						</c:if>
						<c:if test="${flag eq 'joined' }">
						<td class="tar" >邀请人电话：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="submitterPhoneLike" prompt="模糊查询"/>
						</td>	
					</c:if>
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
    <input type="hidden" id="flag" value="${flag}"/>
    <div id="dlg" class="easyui-dialog"  data-options="closed:true,resizable:true" style="width:425px;height:210px;padding:10px 0;text-align:center">
		<input id="remark1" name="remark" value=""
		class="easyui-textbox" 	multiline="true" style="width: 400px;height:100px;" data-options="required:true"
		/>
	</div>
	<div id="dlgSignMode" class="easyui-dialog"  data-options="closed:true,resizable:true" style="width:300px;height:150px;padding:10px 0;text-align:center">
		<input type="radio" name="signMode" value="1" >独立介绍
		<input type="radio" name="signMode" value="2" >非独立介绍
	</div>
	 <div id="dlgSaleInfo" class="easyui-dialog" title="销售明细" data-options="closed:true,resizable:true" style="width:70%;height:700px;">
		<table id="datagridSaleInfo"></table>
	</div>
</body>

<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/pioneer/pioneerAgent.js?date=20160902330"></script>
</html>