<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="noticeForm" method="post">
		<table class="tableForm">
			<tr>
				<th >标题：</th>
				<td >
					<input name="title" value="${noticeVo.title}" class="easyui-textbox" data-options="required:true" style="width: 400px;" />
				</td>
			</tr>
			<tr>
				<th >内容：</th>
				<td >
					<input name="content" value="${noticeVo.content}" class="easyui-textbox" data-options="required:true,multiline:true" style="width: 400px;height:200px;" />
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${noticeVo.id}"/>
		<input name="noticeObj" type="hidden" value="${noticeVo.noticeObj}"/>
	</form>
</div>