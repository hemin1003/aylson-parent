<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="easyui-layout" fit="true">
	<!-- 设计师管理 查询 --> 
	<div region="north"  id="projectInfoArea" style="white-space: nowrap;padding: 5px; height: 75px;">
		<div style="margin-bottom: 5px">
			<form id="projectInfoSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >工程名称：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="projectNameLike"  prompt="模糊查询"/>
						</td>
						<td class="tar" >地址：</td>
						<td class="tal" >
							<input  name="addressLike"  class="easyui-textbox"   prompt="模糊查询"/>
						</td>
					</tr>
					<tr>
						<input name ="memberType" value="${memberType}" type="hidden"/>
						<c:if test="${memberType == 1 }"> 
						<td class="tar" >状态：</td>
						<td class="tal" >
							<c:if test="${!empty projectStatusMergeMap }">
							<select  class="easyui-combobox"  editable=false name="statusMerge" style="width:173px;">
								<option value="" >全部</option>
								<c:forEach items="${projectStatusMergeMap}" var="item">
									<option value="${item.key}">${item.value }</option>
								</c:forEach>
							</select>
							</c:if>
						</td>
						</c:if>
						<c:if test="${memberType == 2 }"> 
						<td class="tar" >状态：</td>
						<td class="tal" >
							<select name="isVerify" class="easyui-combobox"  editable=false style="width:173px;">
									<option value="" >全部</option>
									<option value="0" >待审核</option>
									<option value="1" >已审核</option>
							</select>
						</td>
						</c:if>
						<td class="tar" >代理商：</td>
						<td class="tal" >
							<input  name="byAgentId"  
							data-options="url:'<%=request.getContextPath()%>/sys/agentUser/getList?status=1', valueField:'id', textField:'agentName', method:'GET'" class="easyui-combobox" prompt="输入模糊查询或下拉选择"/>
						</td>
						<td class="tar" >设计内容：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="productTypesLike" prompt="模糊查询"/>
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
    <div id="dlg" class="easyui-dialog" title="查看方案流程明细" data-options="closed:true,resizable:true" style="width:840px;height:400px;">
		<table id="datagridFlowNode"></table>
	</div>
	<input id="memberType" value="${memberType}" type="hidden"/>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/mem/projectInfo.js?date=2016090127"></script>
</html>