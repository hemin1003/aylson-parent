<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<div align="center">
	<form id="menuForm" method="post">
		<table class="tableForm">
			<tr>
				<th>排序：</th>
				<td  style="display:none;">
					<input name="id" value="${menuVo.id}"/>
				</td>
				<td>
					<input name="seq" value="${menuVo.seq}" class="easyui-numberbox" style="width: 300px;"/>
				</td>
			</tr>
			<tr>
				<th>类型：</th>
				<td>
					<select name="type" class="easyui-combobox"   style="width: 300px;" editable=false>
						<option value="1" <c:if test="${menuVo.type == 1}">selected</c:if>>菜单</option>
						<option value="2" <c:if test="${menuVo.type == 2}">selected</c:if>>操作</option>
					</select>				
				</td>
			</tr>
			<tr>
				<th>菜单名称：</th>
				<td>
					<input name="menuName" value="${menuVo.menuName}" class="easyui-textbox" data-options="required:true" style="width: 300px;"/>
				</td>
			</tr>
			<tr>
				<th>图标：</th>
				<td>
					<input name="iconUrl" value="${menuVo.iconUrl}" class="easyui-textbox" style="width: 300px;"/>
				</td>
			</tr>
			<tr>
				<th>菜单地址：</th>
				<td >
					<input name="src" value="${menuVo.src}" class="easyui-textbox" style="width: 300px;"/>
				</td>
			</tr>
			<tr>
				<th>备注说明：</th>
				<td >
					<input name="remark" value="${menuVo.remark}" class="easyui-textbox" style="width: 300px;"/>
				</td>
			</tr>
		</table>
		<input name="parentId" type="hidden" value="${menuVo.parentId}"/>
		<input id="levelNum" name="levelNum" type="hidden" value="${menuVo.levelNum}"/>
	</form>
</div>