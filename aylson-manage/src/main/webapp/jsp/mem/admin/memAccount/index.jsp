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
	<c:choose>
		<c:when test="${memberType == 1 }">
			<div region="north"  id="memAccountArea" style="white-space: nowrap;padding: 5px; height: 75px;">
		</c:when>
		<c:otherwise>
			<div region="north"  id="memAccountArea" style="white-space: nowrap;padding: 5px; height: 50px;">
		</c:otherwise>
	</c:choose>
		<div style="margin-bottom: 5px">
			<form id="memAccountSearchForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >账号：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="accountNameLike" prompt="模糊查询"/>
						</td>
						<td class="tar" >禁用：</td>
						<td class="tal">
							<select name="status" class="easyui-combobox" style="width:173px;" editable=false>
									<option value="" >全部</option>
									<!-- <option value="0" >待审核</option> -->
									<option value="1" >否</option>
									<option value="2" >是</option>
							</select>
						</td>
						<c:if test="${isOnlyMy != true }">
						<td class="tar" >推荐人：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="referralNameLike" prompt="模糊查询"/>
						</td>
						</c:if>
						<c:if test="${memberType != 1 }">
						<td style="padding-left:20px" colspan="3">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch()">搜索</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px" onclick="reset()">重置</a>
						</td> 
						</c:if>
					</tr>
					<tr>
						<input value="${memberType}" name="memberType" type="hidden"/>
						<input value="${isOnlyMy}" name="isOnlyMy" type="hidden"/>
						<c:if test="${memberType == 1 }">
							<td class="tar" >姓名：</td>
							<td class="tal" >
								<input class="easyui-textbox" name="realNameLike" prompt="模糊查询"/>
							</td>
							<td class="tar" >移动电话：</td>
							<td class="tal" >
								<input class="easyui-textbox" name="mobilePhoneLike" prompt="模糊查询"/>
							</td>
							<td colspan="2"></td>
						    <td style="padding-left:20px" colspan="3">
								<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch()">搜索</a>
								<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px" onclick="reset()">重置</a>
							</td> 
						</c:if>
						
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'center'"  border="false" style="overflow: hidden;width:85%">
    	<table id="datagrid"></table>
    </div>
    <input id="isOnlyMy" value="${isOnlyMy }" type="hidden"/>
    <input id="memberType" value="${memberType }" type="hidden"/>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/mem/memAccount.js?date=2016080929"></script>
</html>