<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="designForm" method="post">
		<img id="imgDrawUrl"  src="${designVo.drawUrl}" />
		<table class="tableForm" style="width:80%">
		<c:if test="${!empty designVo.drawOpition }">
			<tr>
				<th>历史不满意原因：</th>
				<td>
					${designVo.drawOpition}
				</td>
			</tr>
		</c:if>
			<tr>
				<th>不满意原因：</th>
				<td>
				<input id="drawOpition" name="drawOpition" value=""
				class="easyui-textbox" multiline="true" style="height:100px;width:100%" 
				prompt="不满意的话请务必填写原因，方便设计人员重新设计"/>
				<input type="hidden" value="${designVo.drawOpition}" name="drawOpitionHistory"/>
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${designVo.id}"/>
		<input name="appointId" type="hidden" value="${designVo.appointId}"/>
		<input name="appointState" type="hidden" value="${designVo.appointState}"/>
	</form>
</div>