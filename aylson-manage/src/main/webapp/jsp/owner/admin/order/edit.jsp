<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="orderForm" method="post">
		<table class="tableForm">
			<tr>
				<th>流水单号：</th>
				<td><input   value="${orderVo.orderNo}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" prompt="流水单保存会自动生成"/></td>
			</tr>
			<tr>
				<th>下单手机：</th>
				<td ><input  value="${orderVo.mobilePhone}"
					class="easyui-textbox" data-options="required:true,readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<%-- <tr>
				<th>订单说明：</th>
				<td ><input value="${orderVo.remark}" 
					class="easyui-textbox" multiline="true"	readonly="true" style="width: 500px;height:50px"/>
				</td>
			</tr> --%>
		<c:if test="${ !empty orderVo.scheduleVoList }">
			<c:forEach items="${orderVo.scheduleVoList}" var="scheduleVo">
				<c:if test="${scheduleVo.state != null and scheduleVo.state != ''}">
				<tr>
					<c:if test="${scheduleVo.state == 1}">
						<th>订单确认说明：</th>
					</c:if>
					<c:if test="${scheduleVo.state == 2}">
						<th>完成生产说明：</th>
					</c:if>
					<c:if test="${scheduleVo.state == 3}">
						<th>入库说明：</th>
					</c:if>
					<c:if test="${scheduleVo.state == 4}">
						<th>产品出库说明：</th>
					</c:if>
					<c:if test="${scheduleVo.state == 5}">
						<th>发货说明：</th>
					</c:if>
					<td ><input name="scheduleRemark" value="${scheduleVo.remark}" 
					class="easyui-textbox" multiline="true"	readonly="true" style="width: 500px;height:50px"
					/></td>
				</tr>
				</c:if>
			</c:forEach>
		</c:if>
			<tr>
				<th>订单状态：</th>
				<td  colspan="1">
				 <select  class="easyui-combobox" name="state" required="true" style="width: 200px;" editable=false>
					<c:if test="${!empty orderScheduleStateMap }">
						<c:forEach items="${orderScheduleStateMap}" var="item">
							<c:if test = "${item.key > orderVo.state }">
								<option value="${item.key}">${item.value }</option>
							</c:if>
						</c:forEach>
					</c:if>
				 </select>
				</td>
			</tr>
			<tr>
					<%-- <c:if test="${orderVo.state == 1}">
						<th>完成生产说明：</th>
					</c:if>
					<c:if test="${orderVo.state == 2}">
						<th>入库说明：</th>
					</c:if>
					<c:if test="${orderVo.state == 3}">
						<th>产品出库说明：</th>
					</c:if>
					<c:if test="${orderVo.state == 4}">
						<th>发货说明：</th>
					</c:if> --%>
				<th>进度说明：</th>
				<td ><input name="scheduleRemark" value="${orderVo.scheduleRemark}" 
					class="easyui-textbox" multiline="true"	 style="width: 500px;height:100px"
					prompt="当前订单进度备注说明"
				/></td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${orderVo.id}"/>
	</form>
</div>