<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="appointmentForm" method="post">
		<table class="tableForm">
			<tr>
				<th>预约人姓名：</th>
				<td ><input name="actTheme" value="${appointmentVo.name}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
				<th>预约人电话：</th>
				<td ><input name="actTheme" value="${appointmentVo.mobilePhone}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>所在城市：</th>
				<td colspan="3"><input  value="${appointmentVo.province}${appointmentVo.city}${appointmentVo.area}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>说明：</th>
				<td colspan="3"><input  name="remark" value="${appointmentVo.remark}" prompt="请说明作废原因" data-options="required:true"
					class="easyui-textbox" multiline="true"	style="width: 400px;height:100px"/></td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${appointmentVo.id}"/>
		<input name="state" type="hidden" value="3"/>
	</form>
</div>