<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="appointmentForm" method="post">
		<table class="tableForm">
			<tr>
				<th>流水单号：</th>
				<td  colspan="3"><input  name="billCode" value="${appointmentVo.billCode}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>预约人姓名：</th>
				<td ><input  name="name" value="${appointmentVo.name}"
					class="easyui-textbox" data-options="required:true,readonly:false"
					style="width: 200px;" /></td>
				<th>预约人手机：</th>
				<td ><input  name="mobilePhone" value="${appointmentVo.mobilePhone}"
					class="easyui-textbox" data-options="required:true,readonly:false"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>所在城市：</th>
				<td colspan="3">
					<input  id="provinceId" name="provinceId" value="${appointmentVo.provinceId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=1', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#province').val(record.regionName);
					$('#city').val('');
					$('#cityId').combobox('clear');
					$('#area').val('');
					$('#areaId').combobox('clear');
					$('#cityId').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
				}
				" class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				<input  id="cityId" name="cityId" value="${appointmentVo.cityId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=2', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#city').val(record.regionName);
					$('#area').val('');
					$('#areaId').combobox('clear');
					$('#areaId').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
				}
				" class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				<input  id="areaId" name="areaId" value="${appointmentVo.areaId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=3', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#area').val(record.regionName);
				}
				" 
				class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/></td>
			</tr>
			<tr>
				<th>装修地址：</th>
				<td colspan="3"><input name="address" value="${appointmentVo.address}"
					class="easyui-textbox" data-options="required:true"
					style="width: 400px;" /></td>
			</tr>
			<tr>
				<th>上门量尺时间：</th>
				<td><input name="homeTimeStr" value="${appointmentVo.homeTimeStr}"
					class="easyui-datetimebox" data-options="required:false"
					style="width: 200px;" /></td>
				<th>预计装修时间：</th>
				<td><input name="decoratingTimeStr" value="${appointmentVo.decoratingTimeStr}"
					class="easyui-datebox" data-options="required:false"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>上门设计师：</th>
				<td ><input name="designer" value="${appointmentVo.designer}"
					class="easyui-textbox" data-options="required:false"
					style="width: 200px;" /></td>
				<th>上门设计师手机：</th>
				<td ><input name="designerPhone" value="${appointmentVo.designerPhone}"
					class="easyui-textbox" data-options="required:false"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>装修项目：</th>
				<td >
				<select  class="easyui-combobox" name="decorateProject"  data-options="multiple:true,value:'${appointmentVo.decorateProject}'" style="width: 200px;" editable=false>
					<option value="1">门</option>
					<option value="2">窗</option>
					<option value="3">阳光房</option>
				</select>
				（下拉可多选）
				</td>
				<th>工程预算：</th>
				<td>
				<input  name="budgetRange" value="${appointmentVo.budgetRange}" 
				data-options="editable:false, url:'<%=request.getContextPath()%>/sys/dictionary/getList?dicType=budgetRange_item', valueField:'dicName', textField:'dicName', method:'GET'," class="easyui-combobox" style="width: 200px;" prompt="---------请选择---------"/>
				</td>
			</tr>
			<tr>
				<th>所属代理商：</th>
				<td colspan="3">
				<input  name="byAgentUserId" value="${appointmentVo.byAgentUserId}" 
				data-options="required:true,editable:true, url:'<%=request.getContextPath()%>/sys/agentUser/getList?status=1', valueField:'userId', textField:'agentName', method:'GET',
				onSelect:function(record){
					$('#byAgent').val(record.agentName);
					var isAgent = record.isAgent;
					$('#isAgent').val(isAgent);
					if(isAgent == true){$('#agent_desc').html('代理店')}
					if(isAgent == false){$('#agent_desc').html('直营店')}
					
				}
				" class="easyui-combobox" style="width: 300px;" prompt="---------请选择---------"/>
				<font color=red id="agent_desc">
					<c:if test="${appointmentVo.isAgent == true }">代理店</c:if>
					<c:if test="${appointmentVo.isAgent == false }">直营店</c:if>
				</font>
				</td>
				<input id="byAgent" name = "byAgent" type="hidden" value="${appointmentVo.byAgent}"/>
			</tr>
			<tr>
				<th>说明：</th>
				<td colspan="3"><input name="remark" value="${appointmentVo.remark}" 
					class="easyui-textbox" multiline="true"	style="width: 400px;height:100px"/></td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${appointmentVo.id}"/>
		<input name="state" type="hidden" value="2"/>
		<input id="province" name="province" type="hidden" value="${appointmentVo.province}"/>
		<input id="city" name="city" type="hidden" value="${appointmentVo.city}"/>
		<input id="area" name="area" type="hidden" value="${appointmentVo.area}"/>
		<input id="isAgent" name="isAgent" type="hidden" value="${appointmentVo.isAgent}"/>
	</form>
</div>