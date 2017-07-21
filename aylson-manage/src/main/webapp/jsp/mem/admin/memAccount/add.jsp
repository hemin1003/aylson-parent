<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="memAccountForm" method="post">
		<table class="tableForm">
			<tr>
				<th>标题：</th>
				<td><input name="title" value="${memAccountVo.title}"
					class="easyui-textbox" data-options="required:true"
					style="width: 400px;" /></td>
			</tr>
			<tr>
				<th>内容：</th>
				<td>
					<textarea id="editor_id"  style="width:400px;height:300px;">
							${memAccountVo.content}
					</textarea>
				</td>
			</tr>
			<%-- <tr>
				<th>内容：</th>
				<td><input name="content" value="${memAccountVo.content}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
			</tr> --%>
		</table>
		<input name="id" type="hidden" value="${memAccountVo.id}"/>
		<input name="type" type="hidden" value="${memAccountVo.type}"/>
		<input name="content" id="content" type="hidden" value="${memAccountVo.content}"/>
	</form>
</div>