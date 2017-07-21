<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="pioneerAgentSaleInfoForm" method="post">
		<table class="tableForm" >
			<tr>
				<th>时间：</th>
				<td>
				<select  class="easyui-combobox"  editable=false name="year" data-options="required:true" style="width:200px;">
					<option value="2016" >2016</option>
					<option value="2017" >2017</option>
					<option value="2018" >2018</option>
					<option value="2019" >2019</option>
					<option value="2020" >2020</option>
					<option value="2021" >2021</option>
					<option value="2022" >2022</option>
					<option value="2023" >2023</option>
					<option value="2024" >2024</option>
					<option value="2025" >2025</option>
					<option value="2026" >2026</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>销售额：</th>
				<td >
				<input  name = "sales" value="${pioneerAgentSaleInfoVo.sales}"
					class="easyui-numberbox" precision="2" data-options="required:true,onChange:function(newValue,oldValue){
						var rebateRate = $('#rebateRate').numberbox('getValue');
						if(rebateRate == null || rebateRate == ''){
							$('#tip').html('请联系管理员配置邀请人回扣率');
						}
						if(rebateRate != null && rebateRate != null){
							$('#rebate').numberbox('setValue',newValue*rebateRate/100);
						}
					}"
					style="width: 200px;" />&nbsp;元
				</td>
			</tr>
			<tr>
				<th>当前配置回扣率：</th>
				<td >
				<input  id="rebateRate" name = "rebateRate" value="${pioneerAgentSaleInfoVo.rebateRate}"
					class="easyui-numberbox" precision="2" data-options="readonly:true,required:true"
					style="width: 200px;" />&nbsp;%
				</td>
			</tr>
				<tr>
				<th>预计回扣金额：</th>
				<td >
				<input  id="rebate" name = "rebate" value=""
					class="easyui-numberbox" precision="2" data-options="readonly:true,required:true"
					style="width: 200px;" />&nbsp;元
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td ><input id="remark" name="remark" value="${pioneerAgentSaleInfoVo.remark}"
					class="easyui-textbox" 	multiline="true" style="width: 400px;height:50px;" 
					/>
				</td>
			</tr>
		</table>
		<div id="tip" style="text-align:center;margin-top:14px;color:#ff0000;"></div>
		<input name="id" type="hidden" value="${pioneerAgentSaleInfoVo.id}"/>
	</form>
</div>