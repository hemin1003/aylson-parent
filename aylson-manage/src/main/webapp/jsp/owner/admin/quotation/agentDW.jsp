<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="designForm" method="post" style="padding:0 20px;">
		<div class="design_title">
			<span>艾臣安全门窗订货单</span>
			<span style="font-size:12px;margin-left:100px;">业务传真：0757-2936150</span>
			<span style="font-size:12px;">服务热线：400-816-2882</span>
		</div>
		<div>
		<table class="tableForm_title">
			<tr>
				<td>地址：广东省佛山市顺德区陈村镇白陈路石洲路段雄盈物流C3栋5楼 </td><td></td>
				<td>客户姓名：${quotationVo.clientName }</td><td></td>
				<td>工程地址：${quotationVo.clientAddress }</td><td></td>
				<td>下单日期：<input name="orderTimeStr"  value="${quotationVo.orderTimeStr }" class="easyui-datebox"  readonly="true"/></td><td></td>
				<td>流水单号：${quotationVo.billCode }</td>
			</tr>
			<tr>
				<td>    销售部门： <input name="salesDep"  value="${quotationVo.salesDep }" class="easyui-textbox"  style="width:150px;"  readonly="true"/></td><td></td>
				<td>   区域负责人：<input name="areaLeader"  value="${quotationVo.areaLeader }" class="easyui-textbox"  style="width:150px;" readonly="true"/></td><td></td>
				<td>专卖店联系电话： <input name="shopPhone"  value="${quotationVo.shopPhone }" class="easyui-textbox"  style="width:150px;" readonly="true"/></td><td></td>
				<td> 专卖店联系人：  <input name="shopContacter"  value="${quotationVo.shopContacter }" class="easyui-textbox"  style="width:150px;" readonly="true"/></td><td></td>
				<td>    订单编号：  <input name="orderNo"  value="${quotationVo.orderNo }" class="easyui-textbox"  style="width:150px;" readonly="true"/></td><td></td>
			</tr>
		</table>
		</div>
		<table class="tableForm tableForm_quotation">
			<tr>
				<th rowspan="2" style="width:30px;">序号</th>
				<th rowspan="2" style="width:80px;">生产编号</th>
				<th rowspan="2" style="width:80px;">产品名称</th>
				<th colspan="2" >产品尺寸（mm）</th>
				<th rowspan="2" style="width:50px;">墙厚（mm）</th>
				<th colspan="2" >颜色</th>
				<th rowspan="2" style="width:80px;">玻璃/百叶</th>
				<th rowspan="2" style="width:80px;">下轨道</th>
				<th rowspan="2" style="width:80px;">执手/拉手颜色</th>
				<th rowspan="2" style="width:50px;">数量（套）</th>
				<th rowspan="2" style="width:50px;">面积（m2）</th>
				<th rowspan="2" style="width:50px;">单价（元）</th>
				<th rowspan="2" style="width:80px;">配件金额（元）</th>
				<th rowspan="2" style="width:60px;">金额（元）</th>
				<th rowspan="2" >备注</th>
				<th rowspan="2" style="width:50px;">销售单价（元）</th>
				<th rowspan="2" style="width:80px;">销售金额（元）</th>
			</tr>
			<tr>
				<th style="width:50px;">宽</th>
				<th style="width:50px;">高</th>
				<th style="width:50px;">内</th>
				<th style="width:50px;">外</th>
			</tr>
		<c:if test="${!empty  quotationVo.detailDWVoList}">
			<c:forEach items="${quotationVo.detailDWVoList}" var="detailDWVo">
				<tr class='detail_tr'>
					<td><input value="${detailDWVo.seq }" id='seq' class='easyui-numberbox seqCss'  readonly="true"/></td>
					<td><input value="${detailDWVo.productNo }" id='productNo' required='true'	class='easyui-textbox productNoCss'  readonly="true"/></td>
					<td><input value="${detailDWVo.productName }" id='productName' class='easyui-textbox productNameCss' readonly="true"/></td>
					<td><input value="${detailDWVo.productSizeW }" id='productSizeW' data-options='required:true,min:1' class='easyui-numberbox productSizeWCss' readonly="true"/></td>
					<td><input value="${detailDWVo.productSizeH }" id='productSizeH' data-options='required:true,min:1' class='easyui-numberbox productSizeHCss' readonly="true"/></td>
					<td><input value="${detailDWVo.wallThick}" id='wallThick' data-options='required:true,min:1' class='easyui-numberbox wallThickCss' readonly="true"/></td>
					<td><input value="${detailDWVo.colorIn }" id='colorIn' class='easyui-textbox colorInCss' readonly="true"/></td>
					<td><input value="${detailDWVo.colorOut}" id='colorOut' class='easyui-textbox colorOutCss' readonly="true"/></td>
					<td><input value="${detailDWVo.glass }" id='glass' class='easyui-textbox glassCss' readonly="true"/></td>
					<td><input value="${detailDWVo.rails }" id='rails' class='easyui-textbox railsCss' readonly="true"/></td>
					<td><input value="${detailDWVo.handColor }" id='handColor' class='easyui-textbox handColorCss' readonly="true"/></td>
					<td><input value="${detailDWVo.pruductNum}" id='pruductNum' data-options='required:true,min:1' class='easyui-numberbox productNumCss' readonly="true"/></td>
					<td><input value="${detailDWVo.areas }" id='areas' data-options='required:true,min:1' class='easyui-numberbox areasCss' readonly="true"/></td>
					<td><input value="${detailDWVo.unitPrice }" id='unitPrice' data-options='required:true,min:1' class='easyui-numberbox unitPriceCss' readonly="true"/></td>
					<td>
					<a href="#" id="goodsAmount_a" onclick="viewGoodAmountDetail(this)" >${detailDWVo.goodsAmount}</a>
 					<input value="${detailDWVo.goodsAmount }" id='goodsAmount'  type="hidden" />
					<input value="${detailDWVo.goodNames }" id='goodNames'  type="hidden" />
					<input value="${detailDWVo.goodAmounts}" id='goodAmounts'  type="hidden" />
					</td>
					<td><input value="${detailDWVo.amount }" id='amount' data-options='required:true,min:1' class='easyui-numberbox amountCss' readonly="true"/></td>
					<td><input value="${detailDWVo.remark }" id='remark' data-options='' class='easyui-textbox remarkCss' readonly="true"/></td>
					<td><input value="${detailDWVo.salesUnitPrice }" id='salesUnitPrice' data-options='required:true,min:1' class='easyui-numberbox salesUnitPriceCss' /></td>
					<td><input value="${detailDWVo.salesUnitAmount }" id='salesUnitAmount' data-options='readonly:true,required:true,min:1,precision:2' class='easyui-numberbox salesUnitAmountCss' /></td>
				</tr>
			</c:forEach>
		</c:if>
			<tr id="remark_tr">
				<td colspan="12"></td>
				<td colspan="3" style="text-align:right;">合计金额：（元）</td>
				<td ><input id="orderAmount" name="orderAmount" data-options="readonly:true,min:0" value="${quotationVo.orderAmount }" class="easyui-numberbox orderAmountCss"/></td>
				<td >---</td>
				<td >---</td>
				<td >---</td>
			</tr>
			<tr>
				<td colspan="6"></td>
				<td colspan="2" style="text-align:right;">优惠金额：（元）</td>
				<td ><input name="benefitAmount"  value="${quotationVo.benefitAmount }" class="easyui-numberbox benefitAmountCss" readonly="true"/></td>
				<td colspan="2" style="text-align:right;">折后金额：（元）</td>
				<td ><input id="realAmount" name="realAmount" data-options="min:0" value="${quotationVo.realAmount }" class="easyui-numberbox realAmountCss" readonly="true"/></td>
				<td colspan="3" style="text-align:right;">货物出厂前应付金额：（元）</td>
				<td ><input id="remainAmount" name="remainAmount" data-options="precision:2,min:0" value="${quotationVo.remainAmount }" class="easyui-numberbox remainAmountCss" readonly="true"/>
				</td>
				<td >---</td>
				<td >---</td>
				<td >---</td>
			</tr>
			<tr>
				<td colspan="10"></td>
				<td style="text-align:right;">付款方式：</td>
				<td ><input name="payMode"  value="${quotationVo.payMode }" class="easyui-textbox payModeCss"/></td>
				<td >---</td>
				<td colspan="2" style="text-align:right;">预收定金：（元）</td>
				<td ><input id="deposit" name="deposit"  value="${quotationVo.deposit }"  data-options="required:true,min:0,
				
				" class="easyui-numberbox depositCss"/></td>
				<td colspan="2" style="text-align:right;">销售金额：（元）</td>
				<td ><input id="salesAmount" name="salesAmount"  value="${quotationVo.salesAmount }" class="easyui-numberbox salesAmountCss" data-options="required:true,readonly:true"/></td>
				<!-- <td >---</td> -->
			</tr>
			<tr>
				<td colspan="19" style="text-align:left;">
					1 门框门洞测量方法：上下、左右量最窄处，墙厚量最厚处，门扇计算方法：上下比门洞小4.5CM，左右比门洞小7CM<br />
					2 铝框门门洞测量方法参照型材决定<br />
					3 付款方式：客户在下单确认后付50%以上，厂家发货前付清全部货款，未付清货款前，产品所有权归厂方所有<br />
					4 门页在弯曲2.5mm以内属正常范围，门套线与门页门框稍有色差属正常，内容填写清楚并核实，如因错误造成损失，公司不负责任 
				</td>
			</tr>
			<tr>
				<td colspan="19" style="text-align:left;">
				交货日期：<input name="deliveryTimeStr"  value="${quotationVo.deliveryTimeStr }" class="easyui-datebox" style="width:150px;" readonly="true"/>&nbsp;&nbsp;
				制单：        <input name="originator"  value="${quotationVo.originator }" class="easyui-textbox" style="width:150px;" readonly="true"/>&nbsp;&nbsp;
				审核：        <input name="auditer"  value="${quotationVo.auditer }" class="easyui-textbox" style="width:150px;" readonly="true"/>&nbsp;&nbsp;
				确认：        <input name="confirmer"  value="${quotationVo.confirmer }" class="easyui-textbox" style="width:150px;" readonly="true"/>&nbsp;&nbsp;
				财务审核：<input name="financer"  value="${quotationVo.financer }" class="easyui-textbox" style="width:150px;"  readonly="true"/>&nbsp;&nbsp;
				<%-- 客户签名：<input name=""  value="${quotationVo. }" class="easyui-textbox" style="width:150px;"/>&nbsp;&nbsp;
				 --%></td>
			</tr>
		</table>
		<input type="hidden" name="id" value="${quotationVo.id}"/>
		<input type="hidden" name="appointId" value="${quotationVo.appointId}"/>
		<input type="hidden" name="designId" value="${quotationVo.designId}"/>
		<input type="hidden" name="clientAddress" value="${quotationVo.clientAddress}"/>
		<input type="hidden" name="clientName" value="${quotationVo.clientName}"/>
		<input type="hidden" name="clientPhone" value="${quotationVo.clientPhone}"/>
		<input type="hidden" name="designState" value="${quotationVo.designState}"/>
		<input type="hidden" name="designType" value="12"/>
	</form>
</div>
<script>
$(function(){
	   $('.salesUnitPriceCss').numberbox({
		   "precision":2, 
		   onChange:function(newValue,oldValue){
			  var productNumValue = $(this).parent().parent().find(".productNumCss").numberbox('getValue');
			  var areasValue = $(this).parent().parent().find(".areasCss").numberbox('getValue');
			  var salesUnitAmount = 0.00;
			  if(productNumValue != null && productNumValue != ''
				  && areasValue != null && areasValue != ''){
				 salesUnitAmount = newValue*productNumValue*areasValue;
			  	$(this).parent().parent().find(".salesUnitAmountCss").numberbox('setValue',salesUnitAmount.toFixed(2));
			  	salesAmountCount();
			  }
		   }
	   })
	   setTimeout(salesAmountCount,50);
})
</script>