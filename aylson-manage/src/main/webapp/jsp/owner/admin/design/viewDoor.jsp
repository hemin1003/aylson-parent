<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="designForm" method="post" style="padding:0 20px;">
		<div class="design_title">门设计信息表</div>
		<div>
		<table class="tableForm_title">
			<tr>
				<td>客户地址：${designVo.clientAddress }</td><td></td>
				<td>客户姓名：${designVo.clientName }</td><td></td>
				<td>下单日期：<input name="orderTimeStr" value="${designVo.orderTimeStr}" data-options="required:true,readonly:true" class="easyui-datebox" /></td><td></td>
				<td>流水单号：${designVo.billCode }</td>
			</tr>
		</table>
		</div>
		<table class="tableForm tableForm_design">
			<tr>
				<th rowspan="2" style="width:80px;">产品编号</th>
				<th rowspan="2" style="width:80px;">产品名称</th>
				<th rowspan="2" style="width:80px;">安装位置</th>
				<th rowspan="2" style="width:30px;">樘数</th>
				<th rowspan="2" style="width:80px;">五金配件</th>
				<th rowspan="2" style="width:50px;">下轨类型</th>
				<th rowspan="2" style="width:50px;">墙厚（mm）</th>
				<th colspan="2" >产品尺寸（mm）</th>
				<th colspan="2" >颜色</th>
				<th colspan="2">玻璃/百叶</th>
				<th rowspan="2" style="width:50px;">包套</th>
				<th rowspan="2" style="width:50px;">安全系统</th>
				<th rowspan="2" ></th>
			</tr>
			<tr>
				<th style="width:50px;">宽</th>
				<th style="width:50px;">高</th>
				<th style="width:50px;">内</th>
				<th style="width:50px;">外</th>
				<th style="width:50px;">玻璃</th>
				<th style="width:50px;">百叶</th>
			</tr>
			<c:if test="${!empty designVo.designDetailDWList }">
				<c:forEach items="${designVo.designDetailDWList}" var="designDetailDWVo">
					<tr class="detail_tr">
						<td><input value='${designDetailDWVo.productNo }' name="productNo" data-options="readonly:true" class="easyui-textbox" /></td>
						<td><input value='${designDetailDWVo.productName }' name="productName" data-options="readonly:true"	class="easyui-textbox" /></td>
						<td><input value='${designDetailDWVo.installLocation }' name="installLocation" data-options="readonly:true" class="easyui-textbox"/></td>
						<td><input value='${designDetailDWVo.frameNum }' name="frameNum"  data-options="readonly:true" class="easyui-numberbox"/></td>
						<td><input value='${designDetailDWVo.metalParts }' name="metalParts"  data-options="readonly:true" class="easyui-textbox"/></td>
						<td>
							<select  class="easyui-combobox" name="railsTypeD"  style="width:55px;" data-options='readonly:true,value:${designDetailDWVo.railsTypeD }' editable=false>
								<option value="1">平轨</option>
								<option value="2">低轨</option>
								<option value="3">铜轨</option>
							</select>
						</td>
						<td><input value='${designDetailDWVo.wallThick }' name="wallThick"  data-options="readonly:true" class="easyui-numberbox"/></td>
						<td><input value='${designDetailDWVo.productSizeW }' name="productSizeW"  data-options="readonly:true" class="easyui-numberbox"/></td>
						<td><input value='${designDetailDWVo.productSizeH }' name="productSizeH"  data-options="readonly:true" class="easyui-numberbox"/></td>
						<td><input value='${designDetailDWVo.colorIn }' name="colorIn"  data-options="readonly:true" class="easyui-textbox"/></td>
						<td><input value='${designDetailDWVo.colorOut }' name="colorOut"  data-options="readonly:true" class="easyui-textbox"/></td>
						<td><input value='${designDetailDWVo.glass }' name="glass"  data-options="readonly:true" class="easyui-textbox"/></td>
						<td>
							<select  class="easyui-combobox" name="shutter" style="width:55px" data-options='readonly:true,value:${designDetailDWVo.shutter }' editable=false>
								<option value="0">无</option>
								<option value="1">电动</option>
								<option value="2">手动</option>
							</select>
						</td>
						<td>
							<select  class="easyui-combobox" name="pack" style="width:55px"  data-options='readonly:true,value:${designDetailDWVo.pack }' editable=false>
								<option value="0">无</option>
								<option value="1">单包</option>
								<option value="2">双包</option>
							</select>
						</td>
						<td>
							<select  class="easyui-combobox" name="isHaveSafeSys" style="width:55px"  data-options='readonly:true,value:${designDetailDWVo.isHaveSafeSys}'  editable=false>
								<option value="false">无</option>
								<option value="true">有</option>
							</select>
						</td>
						<td>
							<input type='hidden' name='attachUrls' value='${designDetailDWVo.attachUrls }'/>
							<a href="#" class="easyui-linkbutton c8" onclick="viewDetailDraw(this)">查看附件</a>
						</td>
					</tr>	
				</c:forEach>
			</c:if>
			<tr id="remark_tr">
				<th>备注</td>
				<td colspan="15" >
					<input name="remark" data-options="readonly:true,multiline:true," style="height:80px;width:98%;"  value="${designVo.remark }" class="easyui-textbox" />
				</td>
			</tr>
		</table>
		<div class="design_desc">
			填表说明：
			<font color="red" >1、</font>断桥需说明内外颜色，非断桥内外颜色一致；
			<font color="red" >2、</font>洞口尺寸与产品二选一填写； 
			<font color="red" >3、</font>产品序号窗的话按C1，C2 如此类推命名。 
			<font color="red" >4、</font>轨道无特殊要求一般标配平轨 
		</div>
	</form>
</div>
