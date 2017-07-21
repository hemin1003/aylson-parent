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
				<th>宽</th>
				<th>高</th>
				<th>内</th>
				<th>外</th>
				<th>玻璃</th>
				<th>百叶</th>
			</tr>
			<c:if test="${!empty designVo.designDetailDWList }">
				<c:forEach items="${designVo.designDetailDWList}" var="designDetailDWVo">
					<tr class="detail_tr">
						<td><input value='${designDetailDWVo.productNo }' name="productNo" data-options="" class="easyui-textbox productNoCss" /></td>
						<td><input value='${designDetailDWVo.productName }' name="productName" data-options="required:true"	class="easyui-textbox productNameCss" /></td>
						<td><input value='${designDetailDWVo.installLocation }' name="installLocation" data-options="" class="easyui-textbox installLocationCss"/></td>
						<td><input value='${designDetailDWVo.frameNum }' name="frameNum"  data-options="" class="easyui-numberbox frameNumCss"/></td>
						<td><input value='${designDetailDWVo.openFanNumW }' name="openFanNumW"  data-options="" class="easyui-numberbox openFanNumWCss"/></td>
						<td><input value='${designDetailDWVo.metalParts }' name="metalParts"  data-options="" class="easyui-textbox metalPartsCss"/></td>
						<td><input value='${designDetailDWVo.wallThick }' name="wallThick"  data-options="required:true" class="easyui-numberbox wallThickCss"/></td>
						<td><input value='${designDetailDWVo.sillHeightW }' name="sillHeightW"  data-options="" class="easyui-numberbox sillHeightWCss"/></td>
					 	<td><input value='${designDetailDWVo.productSizeW }' name="productSizeW"  data-options="required:true" class="easyui-numberbox productSizeWCss"/></td>
						<td><input value='${designDetailDWVo.productSizeH }' name="productSizeH"  data-options="required:true" class="easyui-numberbox productSizeHCss"/></td>
						<td><input value='${designDetailDWVo.colorIn }' name="colorIn"  data-options="required:true" class="easyui-textbox colorInCss"/></td>
						<td><input value='${designDetailDWVo.colorOut }' name="colorOut"  data-options="required:true" class="easyui-textbox colorOutCss"/></td>
						<td><input value='${designDetailDWVo.glass }' name="glass"  data-options="" class="easyui-textbox glassCss"/></td>
						<td>
							<select  class="easyui-combobox shutterCss" name="shutter" style="width:55px;" data-options='value:${designDetailDWVo.shutter }' editable=false>
								<option value="0">无</option>
								<option value="1">电动</option>
								<option value="2">手动</option>
							</select>
						</td>
						<td>
							<select  class="easyui-combobox packCss" name="pack" style="width:55px;" data-options='value:${designDetailDWVo.pack }' editable=false>
								<option value="0">无</option>
								<option value="1">单包</option>
								<option value="2">双包</option>
							</select>
						</td>
						<td>
							<select  class="easyui-combobox isHaveSafeSysCss" name="isHaveSafeSys" style="width:55px;" data-options='value:${designDetailDWVo.isHaveSafeSys}'  editable=false>
								<option value="false">无</option>
								<option value="true">有</option>
							</select>
						</td>
						<td>
							<a href="#" class="easyui-linkbutton c8" style="width:40px;" onclick="delDesignDetail(this)">删除</a>
							<a href="#" class="easyui-linkbutton c8"  style="width:60px;" onclick="uploadDetailDraw(this)">上传附件</a>
							<input type='hidden' name='attachUrls' value='${designDetailDWVo.attachUrls }'/>
							<a href="#" class="easyui-linkbutton c8" style="width:60px;" onclick="viewDetailDraw(this)">查看附件</a>
						</td>
					</tr>	
				</c:forEach>
			</c:if>
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
	</form>
</div>