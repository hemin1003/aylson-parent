<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="couponConfigForm" method="post">
		<table class="tableForm">
			<tr>
				<th>券名：</th>
				<td ><input  name="couponName" value="${couponConfigVo.couponName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>券面值：</th>
				<td ><input  name="couponValue" value="${couponConfigVo.couponValue}"
					class="easyui-numberbox" data-options="required:true,precision:2,min:0"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>条件：</th>
				<td >订单满足 <input  name="achieveMoney" value="${couponConfigVo.achieveMoney}"
					class="easyui-numberbox" data-options="required:true,min:0"
					style="width: 80px;" /> 元可以使用</td>
			</tr>
			<tr>
				<th>有效天数：</th>
				<td >申请通过后 <input  name="effectDays" value="${couponConfigVo.effectDays}"
					class="easyui-numberbox" data-options="required:true,min:1"
					style="width: 80px;" /> 天可以使用</td>
			</tr>
			<%-- <tr>
				<th>状态：</th>
				<td>
				<select  class="easyui-combobox" name="state" value="${couponConfigVo.state}" style="width:200px;" data-options="" editable=false>
						<option value="0">新建</option>
						<option value="1">上架</option>
						<option value="2">下架</option>
				</select>
				</td>
			</tr> --%>
			<tr>
				<th>备注：</th>
				<td ><input  name="remark" value="${couponConfigVo.remark}"
					class="easyui-textbox" data-options="multiline:true"
					style="width: 200px;height:50px;" /></td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${couponConfigVo.id}"/>
	</form>
</div>