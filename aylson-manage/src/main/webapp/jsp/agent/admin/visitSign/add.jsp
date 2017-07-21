<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="visitSignForm" method="post">
		<table class="tableForm">
			<tr>
				<th>工程名称：</th>
				<td><input name="projectName" value="${visitSignVo.projectName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 400px;" prompt="工程名称"/></td>
			</tr>
			<tr>
				<th>工程预算：</th>
				<td>
					<input   name="budgetRangeId" value="${visitSignVo.budgetRangeId}" 
					data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/dictionary/getList?dicType=budgetRange_item', valueField:'id', textField:'dicName', method:'GET',
					onSelect:function(record){
					$('#budgetRange').val(record.dicName);
					$('#budgetRangeValue').val(record.dicValue);
				}
				" class="easyui-combobox" style="width: 400px;" prompt="-请选择-"/>
				</td>
			</tr>
			<tr>
				<th>客户姓名：</th>
				<td><input name="clientName" value="${visitSignVo.clientName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 400px;" prompt="客户姓名（必填）"/></td>
			</tr>
			<tr>
				<th>客户电话：</th>
				<td><input name="mobilePhone" value="${visitSignVo.mobilePhone}"
					class="easyui-textbox" data-options="required:true"
					style="width: 400px;" prompt="客户电话（必填）"/></td>
			</tr>
			<tr>
				<th rowspan="2">客户地址：</th>
				<td>
					<input  id="provinceId" name="provinceId" value="${visitSignVo.provinceId}" 
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
				<input  id="cityId" name="cityId" value="${visitSignVo.cityId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=2', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#city').val(record.regionName);
					$('#area').val('');
					$('#areaId').combobox('clear');
					$('#areaId').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
				}
				" class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				<input  id="areaId" name="areaId" value="${visitSignVo.areaId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=3', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#area').val(record.regionName);
				}
				" 
				class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/></td>
			</tr>
			<tr>
				<td>
				<input name="address" value="${visitSignVo.address}"
					class="easyui-textbox" data-options="required:true"
					style="width: 400px;" prompt="街道以及门牌号（必填）" />
				</td>
			</tr>
			<tr>
				<th>来访人数：</th>
				<td><input name="visitNum" value="${visitSignVo.visitNum}"
					class="easyui-numberbox" data-options="required:true"
					style="width: 400px;" prompt="来访人数（必填）"/></td>
			</tr>
			<tr>
				<th>备注：</th>
				<td><input name="remark" value="${visitSignVo.remark}" prompt="备注（非必填）"
					class="easyui-textbox" multiline="true"	style="width: 400px;height:100px" /></td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${visitSignVo.id}"/>
		<input name="agentId" type="hidden" value="${visitSignVo.agentId}"/>
		<input name="agentName" type="hidden" value="${visitSignVo.agentName}"/>
		<input id="province" name="province" type="hidden" value="${visitSignVo.province}"/>
		<input id="city" name="city" type="hidden" value="${visitSignVo.city}"/>
		<input id="area" name="area" type="hidden" value="${visitSignVo.area}"/>
		<input id="budgetRange" name="budgetRange" type="hidden" value="${visitSignVo.budgetRange}"/>
		<input id="budgetRangeValue" name="budgetRangeValue" type="hidden" value="${visitSignVo.budgetRangeValue}"/>
	</form>
</div>