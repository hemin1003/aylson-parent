<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="designForm" method="post" style="padding:0 20px;">
		<div class="design_title">窗设计信息表</div>
		<div>
		<table class="tableForm_title">
			<tr>
				<td>客户地址：${designVo.clientAddress }</td><td></td>
				<td>客户姓名：${designVo.clientName }</td><td></td>
				<td>下单日期：<input name="orderTimeStr" value="${designVo.orderTimeStr }" data-options="required:true" class="easyui-datebox" /></td><td></td>
				<td>流水单号：${designVo.billCode }</td>
			</tr>
		</table>
		</div>
		<table class="tableForm tableForm_design">
			<!-- <tr>
				<td colspan="18">
					<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加产品</a>
				</td>
			</tr> -->
			<tr>
				<th rowspan="2" style="width:80px;">产品编号</th>
				<th rowspan="2" style="width:80px;">产品名称</th>
				<th rowspan="2" style="width:80px;">安装位置</th>
				<th rowspan="2" style="width:30px;">樘数</th>
				<th rowspan="2" style="width:80px;">开启扇数量</th>
				<th rowspan="2" style="width:80px;">五金配件</th>
				<th rowspan="2" style="width:50px;">墙厚（mm）</th>
				<th rowspan="2" style="width:80px;">窗台高度（mm）</th>
				<th colspan="2" style="width:80px;">产品尺寸（mm）</th>
				<th colspan="2" style="width:80px;">颜色</th>
				<th colspan="2" style="width:80px;">玻璃/百叶</th>
				<th rowspan="2" style="width:50px;">包套</th>
				<th rowspan="2" style="width:50px;">安全系统</th>
				<th rowspan="2" ><a href="#" class="easyui-linkbutton c8" onclick="addDesignDetail(2)">添加产品设计明细</a></th>
			</tr>
			<tr>
				<th style="width:50px;">宽</th>
				<th style="width:50px;">高</th>
				<th style="width:50px;">内</th>
				<th style="width:50px;">外</th>
				<th style="width:50px;">玻璃</th>
				<th style="width:50px;">百叶</th>
			</tr>
			<tr id="remark_tr">
				<th>备注</td>
				<td colspan="16" >
					<input name="remark" data-options="multiline:true," style="height:80px;width:98%;"  value="${designVo.remark }" class="easyui-textbox" />
				</td>
			</tr>
		</table>
		<div class="design_desc">
			填表说明：
			<font color="red" >1、</font>断桥需说明内外颜色，非断桥内外颜色一致；	
			<font color="red" >2、</font>洞口尺寸与产品二选一填写； 
			<font color="red" >3、</font>产品序号窗的话按C1，C2 如此类推命名。 
		</div>
		<input type="hidden" name="id" value="${designVo.id}"/>
		<input type="hidden" name="appointId" value="${designVo.appointId}"/>
		<input type="hidden" name="clientAddress" value="${designVo.clientAddress}"/>
		<input type="hidden" name="clientName" value="${designVo.clientName}"/>
		<input type="hidden" name="designType" value="${designVo.designType}"/>
		<input type="hidden" name="clientPhone" value="${designVo.clientPhone}"/>
		<input type="hidden" name="billCode" value="${designVo.billCode}"/>
	</form>
</div>
<script>
	$(function(){
		addDesignDetail(2);
	})
</script>