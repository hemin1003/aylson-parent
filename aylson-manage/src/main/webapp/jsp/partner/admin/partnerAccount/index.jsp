<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="easyui-layout" fit="true">
	<div region="north"  id="partnerAccountArea" style="white-space: nowrap;padding: 5px; height: 80px;">
		<div style="margin-bottom: 5px">
			<form id="partnerAccountSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td>注册人：</td>
						<td>							
							<input class="easyui-textbox" name="partnerNameLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>注册人电话：</td>
						<td>							
							<input class="easyui-textbox" name="mobilePhoneLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>注册时间：</td>
						<td>
							<input class="easyui-datebox" name="registerTime"  style="width: 120px;"/>
						</td> 
						<td>行业：</td>
						<td>
							<select  class="easyui-combobox"  editable=false  name="industryType" style="width:120px;">
								<option value="" >全部</option>
								<c:forEach items="${industryTypeMap}" var="item">
									<option value="${item.key}">${item.value }</option>
								</c:forEach>
							</select>
						</td> 
					</tr>
					<tr>
						<td>推荐人：</td>
						<td>							
							<input class="easyui-textbox" name="referralNameLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>推荐人电话：</td>
						<td>							
							<input class="easyui-textbox" name="referralPhoneLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>代理商：</td>
						<td>							
							<input class="easyui-textbox" name="agentNameLike" style="width: 120px;" prompt="模糊查询"/>
						</td>
						<td>代理商电话：</td>
						<td>							
							<input class="easyui-textbox" name="agentPhoneLike" style="width: 120px;" prompt="模糊查询"/>
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
    <div id="walletDatailDlg" class="easyui-dialog" title="查看附件" data-options="closed:true,resizable:true" style="width:600px;height:400px;">
		<div id="walletDatailUrl" style="text-align:center;">
		
		</div>
	</div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/partner/partnerAccount.js?date=2017022801"></script>
</html>
