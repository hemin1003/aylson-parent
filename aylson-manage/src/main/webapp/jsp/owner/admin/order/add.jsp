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
				<td  colspan="1"><input  name="billCode" value="${orderVo.orderNo}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" prompt="保存成功自动生成"/></td>
			</tr>
			<tr>
				<th>客户姓名：</th>
				<td ><input  name="name" value="${orderVo.name}"
					class="easyui-textbox" data-options="required:true,readonly:false"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>客户手机：</th>
				<td ><input  name="mobilePhone" value="${orderVo.mobilePhone}"
					class="easyui-textbox" data-options="required:true,readonly:false"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>所在城市：</th>
				<td colspan="1">
					<input  id="provinceId" name="provinceId" value="${orderVo.provinceId}" 
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
				<input  id="cityId" name="cityId" value="${orderVo.cityId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=2', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#city').val(record.regionName);
					$('#area').val('');
					$('#areaId').combobox('clear');
					$('#areaId').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
				}
				" class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				<input  id="areaId" name="areaId" value="${orderVo.areaId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=3', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#area').val(record.regionName);
				}
				" 
				class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/></td>
			</tr>
			<tr>
				<th>装修地址：</th>
				<td colspan="1"><input name="address" value="${orderVo.address}"
					class="easyui-textbox" data-options="required:true"
					style="width: 400px;" /></td>
			</tr>
			<tr>
				<th>装修项目：</th>
				<td >
				<select  class="easyui-combobox" name="decorateProject"  data-options="value:'${orderVo.decorateProject}',
				onSelect:function(record){
					$('#designType').val(record.value)
				}
				" style="width: 200px;" editable=false>
					<option value="1">门</option>
					<option value="2">窗</option>
					<option value="3">阳光房</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>销售金额（元）：</th>
				<td>
					<input name="salesAmount" value="${orderVo.salesAmount}"
					class="easyui-numberbox" data-options="required:true,min:0,max:90000000,precision:2"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>状态：</th>
				<td >
				 <select  class="easyui-combobox" name="state" required="true" style="width: 200px;" editable=false>
					<c:if test="${!empty orderScheduleStateMap }">
						<c:forEach items="${orderScheduleStateMap}" var="item">
								<option value="${item.key}">${item.value }</option>
						</c:forEach>
					</c:if>
				 </select>
				</td>
			</tr>
			<tr>
				<th>订单说明：</th>
				<td colspan="1"><input name="remark" value="${orderVo.remark}" 
					class="easyui-textbox" multiline="true"	style="width: 400px;height:100px"/></td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${orderVo.id}"/>
		<input id="province" name="province" type="hidden" value="${orderVo.province}"/>
		<input id="city" name="city" type="hidden" value="${orderVo.city}"/>
		<input id="area" name="area" type="hidden" value="${orderVo.area}"/>
		<input id="designType" name="designType" type="hidden" value="${orderVo.designType}"/>
		
	</form>
</div>