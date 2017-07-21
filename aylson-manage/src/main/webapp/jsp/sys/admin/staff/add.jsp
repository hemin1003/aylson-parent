<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="staffForm" method="post">
		<table class="tableForm">
			<tr>
				<th >姓名：</th>
				<td ><input name="staffName" value="${staffVo.staffName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 300px;" /></td>
			</tr>
			<tr>
				<th >电话：</th>
				<td ><input name="staffPhone" value="${staffVo.staffPhone}"
					class="easyui-textbox" data-options="required:true"
					style="width: 300px;" /></td>
			</tr>
			<c:if test="${2 eq sessionInfo.user.type}">
			<input name="sourceType" type="hidden" value="2"/>
			<input name="sourceId" type="hidden" value="${sessionInfo.user.id}"/>
			<input name="source" type="hidden" value="${sessionInfo.user.userName}"/>
		</c:if>
		<c:if test="${1 == sessionInfo.user.type}">
			<tr>
				<th >所属组织结构：</th>
				<td>
					<input id="sourceId" name="sourceId" value="${staffVo.sourceId}" class="easyui-combotree"
						  data-options="url:'<%=request.getContextPath()%>/sys/org/getSyncGridTree',parentField : 'parentId', lines : true, method:'GET',required:true,
						  onLoadSuccess : function(node, data) {
							var t = $(this);
							if (data) {
								$(data).each(function(index, d) {
									if (this.state == 'closed') {
										t.tree('expandAll');
									}
								});
							}
						},
						onSelect:function(node) {
							$('#source').val(node.orgName);
                        }
						" 
						style="width: 300px;" />
				</td>
			</tr>
			<input name="sourceType" type="hidden" value="1"/>
			<input name="source" id="source" type="hidden" value="${staffVo.source}"/>
		</c:if>
			<tr>
				<th >备注：</th>
				<td ><input name="remark" value="${staffVo.remark}"
					class="easyui-textbox" data-options="multiline:true"
					style="width: 300px;height:60px;" /></td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${staffVo.id}"/>
	</form>
</div>