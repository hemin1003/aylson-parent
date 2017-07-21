<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="roleForm" method="post">
		<table class="tableForm">
			<tr>
				<th>角色名称</th>
				<td><input name="roleName" value="${roleVo.roleName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>角色编号</th>
				<td><input name="roleCode" value="${roleVo.roleCode}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>角色说明</th>
				<td><input name="remark" value="${roleVo.remark}"
					class="easyui-textbox" style="width: 200px;" /></td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${roleVo.id}"/>
	</form>
</div>