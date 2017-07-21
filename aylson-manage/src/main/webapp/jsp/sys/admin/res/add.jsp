<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="addResForm" method="post">
		<table class="tableForm">
			<tr>
				<th>排序</th>
				<td style="display: none;"><input name="id"
					value="${resourceVo.id}" /></td>
				<td><input name="seq" value="${resourceVo.seq}"
					class="easyui-numberbox" style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>资源名称</th>
				<td><input name="resName" value="${resourceVo.resName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>资源地址</th>
				<td><input name="src" value="${resourceVo.src}"
					class="easyui-textbox" style="width: 200px;" /></td>
			</tr>
		</table>
		<input name="parentId" type="hidden" value="${resourceVo.parentId}" />
		<input id="levelNum" name="levelNum" type="hidden"	value="${resourceVo.levelNum}" />
	</form>
</div>