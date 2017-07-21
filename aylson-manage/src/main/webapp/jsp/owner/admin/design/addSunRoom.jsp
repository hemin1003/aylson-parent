<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
/* .design_title{margin:10px;font-size:16px;font-weight:bolder;}
.tableForm_title{width:90%;border-style: none;}
.tableForm_title td{border-style: none;}
.tableForm{width:90%;text-align:center;}
.tableForm th{text-align:center;min-width:80px;}
.textboxCss{width:80px!important;} */
</style>
<div align="center">
	<form id="designForm" method="post" style="width:99%;">
		<table class="tableForm">
			<tr><td colspan="4" style="text-align:center;font-size:16px;font-weight:bolder;">艾臣安全门窗阳光房订单方案确认表</td></tr>
			<tr>
				<th>流水单号：</th>
				<td colspan="3"><input name="billCode" style="width:200px;" value="${designVo.billCode }" class="easyui-textbox" readonly="true" /></td>
			</tr>
			<tr>
				<th>下单时间：</th>
				<td><input name="orderTimeStr"  value="${designVo.orderTimeStr }" class="easyui-datebox" /></td>
				<th>订单编号：</th>
				<td><input name="orderNo"  value="${designVo.orderNo}" style="width:200px;" class="easyui-textbox" /></td>
			</tr>
			<tr>
				<th>一、类型：</th>
				<td >
					1、人字<input type="radio" value="1" name="designDetailSRVo.type"/>
					2、半坡<input type="radio" value="2"name="designDetailSRVo.type"/>
					3、L型<input type="radio"  value="3" name="designDetailSRVo.type"/>
					4、T型<input type="radio" value="4" name="designDetailSRVo.type"/>
					5、多边形<input type="radio" value="5" name="designDetailSRVo.type"/>
				</td>
				<td colspan="2">
					<a href='#' class='easyui-linkbutton c8' onclick='uploadDetailDraw(this)'>上传附件</a>
					<input type='hidden' name='designDetailSRVo.attachUrls' value=''/>
					<a href='#' class='easyui-linkbutton c8' onclick='viewDetailDraw(this)'>查看附件</a>
				</td>
			</tr> 
			<tr>
				<td colspan="4">
					<img width="70%" height="300px;"src="<%=request.getContextPath()%>/resources/images/design_sr.png"/>
				</td>
			</tr>
			<tr>
				<th>二、颜色：</th>
				<td colspan="3">
					室内：<input name="designDetailSRVo.colorIn"  value="" class="easyui-textbox" />&nbsp;&nbsp;
					室外：<input name="designDetailSRVo.colorOut"  value="" class="easyui-textbox" />
				</td>
			</tr>
			<tr>
				<th rowspan="3">三、尺寸：</th>
				<td colspan="3">
					长：
					L1=<input name="designDetailSRVo.length1"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
					L2=<input name="designDetailSRVo.length2"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
					L3=<input name="designDetailSRVo.length3"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
					L4=<input name="designDetailSRVo.length4"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
					L5=<input name="designDetailSRVo.length5"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
					L6=<input name="designDetailSRVo.length6"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="3">
				宽：
				W1=<input name="designDetailSRVo.width1"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
				W2=<input name="designDetailSRVo.width2"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
				角度：
				α=<input name="designDetailSRVo.partial"  data-options="precision:2" value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="3">
				高：
				H=<input name="designDetailSRVo.height"  value="" class="easyui-numberbox"  style="width:100px;"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th rowspan="2">四、玻璃规格：</th>
				<td>房顶：<input name="designDetailSRVo.roof"  value="" class="easyui-textbox"  style="width:100px;"/></td>
				<td>房身：<input name="designDetailSRVo.real"  value="" class="easyui-textbox"  style="width:100px;"/></td>
				<td>三角玻璃：<input name="designDetailSRVo.rearGlass"  value="" class="easyui-textbox"  style="width:100px;"/></td>
			</tr>
			<tr>
				<td>是否中空：
					<select  class="easyui-combobox" name="designDetailSRVo.rootIsHollow" style="width:100px;" data-options="" editable=false>
						<option></option>
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>
				<td>是否中空：
					<select  class="easyui-combobox" name="designDetailSRVo.realIsHollow" style="width:100px;" data-options="" editable=false>
						<option></option>
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>
				<td>是否中空：
					<select  class="easyui-combobox" name="designDetailSRVo.rearGlassIsHollow" style="width:100px;" data-options="" editable=false>
						<option></option>
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>五、玻璃颜色：</th>
				<td>房顶：<input name="designDetailSRVo.roofGlassColor"  value="" class="easyui-textbox"  style="width:100px;"/></td>
				<td>房身：<input name="designDetailSRVo.realGlassColor"  value="" class="easyui-textbox"  style="width:100px;"/></td>
				<td>三角玻璃：<input name="designDetailSRVo.rearGlassColor"  value="" class="easyui-textbox"  style="width:100px;"/></td>
			</tr>
			<tr>
				<th>六、中空百叶：</th>
				<td colspan="3">控制方式：
				<select  class="easyui-combobox" name="designDetailSRVo.hollowShutterConMode" style="width:100px;" data-options="" editable=false>
						<option></option>
						<option value="手动">手动</option>
						<option value="电动">电动</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>七、遮阳系统：</th>
				<td colspan="3">
					<select  class="easyui-combobox" name="designDetailSRVo.isHaveSunVisorSys" style="width:100px;" data-options="" editable=false>
						<option></option>
						<option value="0">无</option>
						<option value="1">有</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>八、安全系统：</th>
				<td colspan="3">
					<select  class="easyui-combobox" name="designDetailSRVo.isHaveSafeSys" style="width:100px;" data-options="" editable=false>
						<option></option>
						<option value="0">无</option>
						<option value="1">有</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>九、其他要求：</th>
				<td colspan="3">
					<input name="designDetailSRVo.otherRequire" data-options="multiline:true," style="height:80px;width:98%;"  value="" class="easyui-textbox" />
				</td>
			</tr>
			
		</table>
		<input type="hidden" name="id" value="${designVo.id}"/>
		<input type="hidden" name="appointId" value="${designVo.appointId}"/>
		<input type="hidden" name="clientAddress" value="${designVo.clientAddress}"/>
		<input type="hidden" name="clientName" value="${designVo.clientName}"/>
		<input type="hidden" name="designType" value="${designVo.designType}"/>
		<input type="hidden" name="clientPhone" value="${designVo.clientPhone}"/>
	</form>
</div>