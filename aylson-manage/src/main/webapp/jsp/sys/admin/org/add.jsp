<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<div align="center">
	<form id="orgForm" method="post">
		<table class="tableForm">
			<%-- <tr>
				<th>排序</th>
				<td>
					<input name="seq" value="${orgVo.seq}" class="easyui-numberbox" style="width: 200px;"/>
				</td>
			</tr> --%>
			<tr>
				<th>组织编号：</th>
				<td>
					<input name="orgCode" value="${orgVo.orgCode}" class="easyui-textbox" data-options="required:true" style="width: 200px;"/>
				</td>
			</tr>
			<tr>
				<th>组织名称：</th>
				<td>
					<input name="orgName" value="${orgVo.orgName}" class="easyui-textbox" data-options="required:true" style="width: 200px;"/>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td >
					<input name="remark" value="${orgVo.remark}" class="easyui-textbox" style="width: 200px;"/>
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${orgVo.id}"/>
		<input name="parentId" type="hidden" value="${orgVo.parentId}"/>
		<input id="levelNum" name="levelNum" type="hidden" value="${orgVo.levelNum}"/>
	</form>
</div>