<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="addDicForm" method="post">
		<table class="tableForm">
			<tr>
				<th>排序：</th>
				<td style="display: none;"><input  name="id"
					value="${dictionaryVo.id}" /></td>
				<td><input name="seq" value="${dictionaryVo.seq}"
					class="easyui-numberbox" style="width: 300px;" /></td>
			</tr>
			<tr>
				<th>字典名称：</th>
				<td><input  name="dicName" value="${dictionaryVo.dicName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 300px;" /></td>
			</tr>
			<tr>
				<th>字典值：</th>
				<td><input  name="dicValue" value="${dictionaryVo.dicValue}"
					class="easyui-textbox" data-options="required:true"
					style="width: 300px;" /></td>
			</tr>
			<tr>
				<th>类型：</th>
				<td><input name="dicType" value="${dictionaryVo.dicType}"
					class="easyui-textbox" style="width: 300px;" /></td>
			</tr>
			<tr>
				<th>分组：</th>
				<td><input name="dicGroup" value="${dictionaryVo.dicGroup}"
					class="easyui-textbox" style="width: 300px;" /></td>
			</tr>
			<tr>
				<th>备注说明：</th>
				<td><input name="remark" value="${dictionaryVo.remark}"
					class="easyui-textbox" multiline="true" style="width: 300px;height:80px;" /></td>
			</tr>
		</table>
		<input name="parentId" type="hidden" value="${dictionaryVo.parentId}"/>
		<input id="levelNum" name="levelNum" type="hidden" value="${dictionaryVo.levelNum}"/>
	</form>
</div>