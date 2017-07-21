<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="pioneerAgentForm" method="post">
		<table class="tableForm" >
			<tr>
				<th>代理商名称：</th>
				<td>
				<input  name="agentName" value="${pioneerAgentVo.agentName}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>代理商电话：</th>
				<td>
				<input  name="contactPhone" value="${pioneerAgentVo.contactPhone}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>代理商地址：</th>
				<td colspan="3">
				<input  value="${pioneerAgentVo.province}${pioneerAgentVo.city}${pioneerAgentVo.area}${pioneerAgentVo.address}"
					class="easyui-textbox" readonly="true"
					style="width: 400px;" />
				</td>
			</tr>
			<tr>
				<th>提交人：</th>
				<td>
				<input name="submitter" value="${pioneerAgentVo.submitter}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>提交人电话：</th>
				<td>
				<input name="submitterPhone" value="${pioneerAgentVo.submitterPhone}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>之前代理品牌：</th>
				<td colspan="3"><input  value="${pioneerAgentVo.agency}" readonly="true"
					class="easyui-textbox" 	multiline="true" style="width: 400px;height:30px;" 
					prompt="之前代理品牌"/>
				</td>
			</tr>
			<tr>
				<th>代理商概况：</th>
				<td colspan="3"><input  value="${pioneerAgentVo.descs}" readonly="true"
					class="easyui-textbox" 	multiline="true" style="width: 400px;height:50px;" 
					prompt="代理商概况"/>
				</td>
			</tr>
			<tr>
				<th>审核意见：</th>
				<td colspan="3"><input id="remark" name="remark" value="${pioneerAgentVo.remark}"
					class="easyui-textbox" 	multiline="true" style="width: 400px;height:50px;" 
					prompt="审核不通过必须填写审核意见"/>
				</td>
			</tr>
			
		</table>
		<input name="id" type="hidden" value="${pioneerAgentVo.id}"/>
		<input name="submitterId" type="hidden" value="${pioneerAgentVo.submitterId}"/>
		<input name="submitterReferral" type="hidden" value="${pioneerAgentVo.submitterReferral}"/>
		<input name="wxOpenId" type="hidden" value="${pioneerAgentVo.wxOpenId}"/>
	</form>
</div>