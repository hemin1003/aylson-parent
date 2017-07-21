<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="projectInfoForm" method="post">
		<table class="tableForm" >
			<tr>
				<th>客户名称：</th>
				<td>
				<input  value="${projectInfoVo.clientName}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>工程名称：</th>
				<td>
				<input name="projectName" value="${projectInfoVo.projectName}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>客户地址：</th>
				<td>
				<input  value="${projectInfoVo.clientAddress}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>客户电话：</th>
				<td>
				<input  value="${projectInfoVo.mobilePhone}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>工程预算：</th>
				<td>
				<input value="${projectInfoVo.budgetRange}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>项目状态：</th>
				<td>
				<select  class="easyui-combobox" readonly="true" style="width: 200px;" editable=false>
					<c:if test="${!empty projectStatusMap }">
						<c:forEach items="${projectStatusMap}" var="item">
							<option value="${item.key}" <c:if test = "${item.key == projectInfoVo.status }">selected = "selected" </c:if>>${item.value }</option>
						</c:forEach>
					</c:if>
				</select>
				</td>
			</tr>
			<tr>
				<th>设计师：</th>
				<td>
				<input name="accountName"  value="${projectInfoVo.accountName}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>设计师电话：</th>
				<td>
				<input name="memberPhone"  value="${projectInfoVo.memberPhone}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>所属代理商：</th>
				<td>
				<input id="byAgent" name="byAgent" value="${projectInfoVo.byAgent}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/agentUser/getList?status=1', valueField:'agentName', textField:'agentName', method:'GET',
				onSelect:function(record){
					$('#byAgentId').val(record.id);
				}" class="easyui-combobox" style="width: 200px;" prompt="---------请选择---------"/>
				</td>
			</tr>
			<tr>
				<th>审核意见：</th>
				<td colspan="3"><input id="auditOpition" name="auditOpition" value="${projectInfoVo.auditOpition}"
					class="easyui-textbox" 	multiline="true" style="width: 400px;height:50px;" 
					prompt="审核不通过必须填写审核意见"/>
				</td>
			</tr>
			
		</table>
		<input name="id" type="hidden" value="${projectInfoVo.id}"/>
		<input name="isSent" type="hidden" value="${projectInfoVo.isSent}"/>
		<input name="integral" type="hidden" value="${projectInfoVo.integral}"/>
		<input name="memberId" type="hidden" value="${projectInfoVo.memberId}"/>
		<input name="referralId" type="hidden" value="${projectInfoVo.referralId}"/>
		<input name="referralName" type="hidden" value="${projectInfoVo.referralName}"/>
		<input name="memberType" type="hidden" value="${projectInfoVo.memberType}"/>
		<input name="wxOpenId" type="hidden" value="${projectInfoVo.wxOpenId}"/>
		<input id="byAgentId" name="byAgentId" type="hidden" value="${projectInfoVo.byAgentId}"/>
	</form>
</div>