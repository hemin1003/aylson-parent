<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="designForm" method="post" style="padding:0 20px;">
		<div class="design_title">
			<span>艾臣阳光房订货单</span>
			<span style="font-size:12px;margin-left:100px;">业务传真：0757-2936150</span>
			<span style="font-size:12px;">服务热线：400-816-2882</span>
		</div>
		<div>
		<table class="tableForm_title">
			<tr>
				<td>地址：广东省佛山市顺德区陈村镇白陈路石洲路段雄盈物流C3栋5楼 </td><td></td>
				<td>客户姓名：${quotationVo.clientName }</td><td></td>
				<td>工程地址：${quotationVo.clientAddress }</td><td></td>
				<td>下单日期：<input name="orderTimeStr"  value="${quotationVo.orderTimeStr }" class="easyui-datebox" /></td><td></td>
				<td>流水单号：${quotationVo.billCode }</td>
			</tr>
			<tr>
				<td>    销售部门： <input name="salesDep"  value="" class="easyui-textbox"  style="width:150px;"/></td><td></td>
				<td>   区域负责人：<input name="areaLeader"  value="" class="easyui-textbox"  style="width:150px;"/></td><td></td>
				<td>专卖店联系电话： <input name="shopPhone"  value="" class="easyui-textbox"  style="width:150px;"/></td><td></td>
				<td> 专卖店联系人：  <input name="shopContacter"  value="" class="easyui-textbox"  style="width:150px;"/></td><td></td>
				<td>    订单编号：  <input name="orderNo"  value="" class="easyui-textbox"  style="width:150px;"/></td><td></td>
			</tr>
		</table>
		</div>
		<table class="tableForm tableForm_quotation">
			<tr>
				<th rowspan="2" style="width:80px;">序号</th>
				<th rowspan="2" style="width:80px;">生产编号</th>
				<th rowspan="2" style="width:80px;">类别</th>
				<th rowspan="2" style="width:150px;">结构</th>
				<th colspan="2" >颜色</th>
				<th rowspan="2">玻璃/百叶</th>
				<th rowspan="2">长（MM）</th>
				<th rowspan="2">宽（MM）</th>
				<th rowspan="2">柱高（MM）</th>
				<th rowspan="2">执手/拉手颜色</th>
				<th rowspan="2">数量</th>
				<th rowspan="2">面积</th>
				<th rowspan="2">单位</th>
				<th rowspan="2">单价（元）</th>
				<th rowspan="2">金额（元）</th>
				<!-- <th rowspan="2">销售单价（元）</th>
				<th rowspan="2">销售金额（元）</th> -->
			</tr>
			<tr>
				<th style="width:50px;">内</th>
				<th style="width:50px;">外</th>
			</tr>
			<tr>
				<td>1<input name="detailSRVoList[0].seq" type="hidden" value="1" /></td>
				<td><input name="detailSRVoList[0].productNo"  value="" class="easyui-textbox"/></td>
				<td>钢结构<input name="detailSRVoList[0].category" type="hidden" value="1" /></td>
				<td>钢结构<input name="detailSRVoList[0].structure" type="hidden"  value="1"/></td>
				<td><input name="detailSRVoList[0].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[0].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[0].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[0].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[0].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[0].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[0].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[0].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[0].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>m²</td>
				<td><input name="detailSRVoList[0].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[0].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[0].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[0].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
 -->			</tr>
			<tr>
				<td rowspan="3">2<input name="detailSRVoList[1].seq" type="hidden" value="2" /></td>
				<td rowspan="3"><input name="detailSRVoList[1].productNo"  value="" class="easyui-textbox"
				data-options="onChange:function(newValue,oldValue){
					$('#productNo_2').val(newValue);
					$('#productNo_3').val(newValue);
				}"
				/></td>
				<td rowspan="3">立柱<input name="detailSRVoList[1].category" type="hidden" value="2" /></td>
				<td>主立柱<input name="detailSRVoList[1].structure" type="hidden"  value="2" /></td>
				<td><input name="detailSRVoList[1].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[1].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[1].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[1].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[1].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[1].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[1].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[1].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[1].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>根</td>
				<td><input name="detailSRVoList[1].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[1].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
			<!-- 	<td><input name="detailSRVoList[1].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[1].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
	 -->		</tr>
			<tr>
				<input name="detailSRVoList[2].seq" type="hidden" value="2" />
				<input id="productNo_2" name="detailSRVoList[2].productNo" value="" type="hidden" >
				<input name="detailSRVoList[2].category" type="hidden" value="2" />
				<td>靠墙立柱<input name="detailSRVoList[2].structure" type="hidden" value="3" /></td>
				<td><input name="detailSRVoList[2].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[2].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[2].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[2].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[2].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[2].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[2].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[2].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[2].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>根</td>
				<td><input name="detailSRVoList[2].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[2].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[2].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[2].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
	 -->		</tr>
			<tr>
				<input name="detailSRVoList[3].seq" type="hidden" value="2" />
				<input id="productNo_3" name="detailSRVoList[3].productNo" value="" type="hidden" >
				<input name="detailSRVoList[3].category" type="hidden" value="2" />
				<td>次立柱<input name="detailSRVoList[3].structure" type="hidden" value="4" /></td>
				<td><input name="detailSRVoList[3].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[3].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[3].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[3].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[3].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[3].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[3].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[3].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[3].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>根</td>
				<td><input name="detailSRVoList[3].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[3].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[3].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[3].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
 -->			</tr>
			<tr>
				<td rowspan="4">3<input name="detailSRVoList[4].seq" type="hidden" value="3" /></td>
				<td rowspan="4"><input name="detailSRVoList[4].productNo"  value="" class="easyui-textbox"
				data-options="onChange:function(newValue,oldValue){
					$('#productNo_5').val(newValue);
					$('#productNo_6').val(newValue);
					$('#productNo_7').val(newValue);
				}"
				/></td>
				<td rowspan="4">屋顶部分<input name="detailSRVoList[4].category" type="hidden" value="3" /></td>
				<td>三角面积<input name="detailSRVoList[4].structure" type="hidden" value="5" /></td>
				<td><input name="detailSRVoList[4].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[4].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[4].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[4].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[4].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[4].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[4].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[4].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[4].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>m²</td>
				<td><input name="detailSRVoList[4].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[4].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[4].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[4].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
		 -->	</tr>
			<tr>
				<input name="detailSRVoList[5].seq" type="hidden" value="3" />
				<input id="productNo_5" name="detailSRVoList[5].productNo" type="hidden" />
				<input name="detailSRVoList[5].category" type="hidden" value="3" />
				<td>屋顶<input name="detailSRVoList[5].structure" type="hidden" value="6" /></td>
				<td><input name="detailSRVoList[5].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[5].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[5].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[5].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[5].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[5].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[5].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[5].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[5].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>m²</td>
				<td><input name="detailSRVoList[5].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[5].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[5].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[5].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
		 -->	</tr>
			<tr>
				<input name="detailSRVoList[6].seq" type="hidden" value="3" />
				<input id="productNo_6" name="detailSRVoList[6].productNo" type="hidden" value="" />
				<input name="detailSRVoList[6].category" type="hidden" value="3" />
				<td>水槽<input name="detailSRVoList[6].structure" type="hidden" value="7" /></td>
				<td><input name="detailSRVoList[6].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[6].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[6].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[6].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[6].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[6].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[6].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[6].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[6].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>m</td>
				<td><input name="detailSRVoList[6].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[6].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[6].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[6].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
		 -->	</tr>
			<tr>
				<input name="detailSRVoList[7].seq" type="hidden" value="3" />
				<input id="productNo_7" name="detailSRVoList[7].productNo" type="hidden" value="" />
				<input name="detailSRVoList[7].category" type="hidden" value="3" />
				<td>水槽堵塞网<input name="detailSRVoList[7].structure" type="hidden" value="8" /></td>
				<td><input name="detailSRVoList[7].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[7].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[7].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[7].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[7].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[7].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[7].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[7].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[7].areas"  value="" class="easyui-numberbox areasCss" /></td>
				<td>m</td>
				<td><input name="detailSRVoList[7].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[7].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[7].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[7].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
		 -->	</tr>
			<tr>
				<td rowspan="3">4<input name="detailSRVoList[8].seq" type="hidden" value="4" /></td>
				<td rowspan="3"><input id="productNo_8" name="detailSRVoList[8].productNo" value=""  class="easyui-textbox"
				data-options="onChange:function(newValue,oldValue){
					$('#productNo_9').val(newValue);
					$('#productNo_10').val(newValue);
				}"
				/></td>
				<td rowspan="3">门窗<input name="detailSRVoList[8].category" type="hidden" value="4" /></td>
				<td>清风双悬推拉门<input name="detailSRVoList[8].structure" type="hidden" value="9" /></td>
				<td><input name="detailSRVoList[8].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[8].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[8].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[8].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[8].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[8].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[8].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[8].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[8].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>m²</td>
				<td><input name="detailSRVoList[8].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[8].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[8].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[8].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
	 -->		</tr>
			<tr>
				<input name="detailSRVoList[9].seq" type="hidden" value="4" />
				<input id="productNo_9" name="detailSRVoList[9].productNo" value="" type="hidden"/>
				<input name="detailSRVoList[9].category" type="hidden" value="4" />
				<td>95手摇平开窗-固定部分<input name="detailSRVoList[9].structure" type="hidden" value="10" /></td>
				<td><input name="detailSRVoList[9].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[9].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[9].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[9].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[9].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[9].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[9].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[9].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[9].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>m²</td>
				<td><input name="detailSRVoList[9].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[9].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<!-- <td><input name="detailSRVoList[9].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[9].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
	 -->		</tr>
			<tr>
				<input name="detailSRVoList[10].seq" type="hidden" value="4" />
				<input id="productNo_10" name="detailSRVoList[10].productNo" type="hidden"  value="" />
				<input name="detailSRVoList[10].category" type="hidden" value="4" />
				<td>95手摇平开窗-扇部分<input name="detailSRVoList[10].structure" type="hidden" value="11" /></td>
				<td><input name="detailSRVoList[10].colorIn"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[10].colorOut"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[10].glass"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[10].length"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[10].width"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[10].height"  value="" class="easyui-numberbox"/></td>
				<td><input name="detailSRVoList[10].handColor"  value="" class="easyui-textbox"/></td>
				<td><input name="detailSRVoList[10].pruductNum"  value="" class="easyui-numberbox productNumCss"/></td>
				<td><input name="detailSRVoList[10].areas"  value="" class="easyui-numberbox areasCss"/></td>
				<td>m²</td>
				<td><input name="detailSRVoList[10].unitPrice"  value="" class="easyui-numberbox unitPriceCss"/></td>
				<td><input name="detailSRVoList[10].amount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
			<!-- 	<td><input name="detailSRVoList[10].salesUnitPrice"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
				<td><input name="detailSRVoList[10].salesUnitAmount"  value="" class="easyui-numberbox amountCss" readonly="true"/></td>
	 -->		</tr>
			<tr>
				<th rowspan="3">备注：</td>
				<td rowspan="3" colspan="8" >
					<input name="remark" data-options="multiline:true," style="height:98%;width:98%;"  value="${quotationVo.remark }" class="easyui-textbox" />
				</td>
				<td colspan="4"></td>
				<td colspan="2" style="text-align:right;">合计金额：（元）</td>
				<td ><input id="orderAmount" name="orderAmount" readonly="true" data-options="min:0,precision:2" value="0" class="easyui-numberbox" /></td>
			</tr>
			<tr>
				<td colspan="2"></td>
				<td style="text-align:right;">优惠金额：（元）</td>
				<td ><input id="benefitAmount" name="benefitAmount" data-options="min:0,
				onChange:function(newValue,oldValue){
					realAmountCount(null,newValue);
				}
				" value="0" class="easyui-numberbox"/></td>
				<td colspan="2" style="text-align:right;">折后金额：（元）</td>
				<td ><input id="realAmount" name="realAmount" data-options="readonly:true,min:0,precision:2" value="0" class="easyui-numberbox" readonly="true"/></td>
			</tr>
			<tr>
				<td colspan="4"></td>
				<td colspan="2" style="text-align:right;">货物出厂前应付金额：（元）</td>
				<td >
				<input id="remainAmount" name="remainAmount" data-options="precision:2,min:0" value="0" class="easyui-numberbox remainAmountCss"/>
				</td>
			</tr>
			<%-- <tr>
				<td style="text-align:right;">付款方式：</td>
				<td ><input name=""  value="" class="easyui-textbox"/></td>
				<td style="text-align:right;">预收定金：（元）</td>
				<td ><input id="deposit" name="deposit"  value="0"  data-options="min:0,
				onChange:function(newValue,oldValue){
					remainAmountCount(null,newValue);
				}
				" class="easyui-numberbox"/></td>
				<td colspan="2"><font color=red>（代理商填写）</font>销售金额：（元）</td>
				<td ><input id="salesAmount" name="salesAmount" data-options="min:0,value:0,precision:2" value="" class="easyui-numberbox"
				<c:if test="${agentCanQuotation == false }">readonly= "true" </c:if>
				<c:if test="${agentCanQuotation == true }">required= "true" </c:if>
				/>
				</td>
			</tr> --%>
			<tr>
				<td colspan="16" style="text-align:left;">
					1 门框门洞测量方法：上下、左右量最窄处，墙厚量最厚处，门扇计算方法：上下比门洞小4.5CM，左右比门洞小7CM<br />
					2 铝框门门洞测量方法参照型材决定<br />
					3 付款方式：客户在下单确认后付50%以上，厂家发货前付清全部货款，未付清货款前，产品所有权归厂方所有<br />
					4 门页在弯曲2.5mm以内属正常范围，门套线与门页门框稍有色差属正常，内容填写清楚并核实，如因错误造成损失，公司不负责任 
				</td>
			</tr>
			<tr>
				<td colspan="17" style="text-align:left;">
				交货日期：<input name="deliveryTimeStr"  value="" class="easyui-datebox" style="width:150px;"/>&nbsp;&nbsp;
				制单：        <input name="originator"  value="" class="easyui-textbox" style="width:150px;"/>&nbsp;&nbsp;
				审核：        <input name="auditer"  value="" class="easyui-textbox" style="width:150px;"/>&nbsp;&nbsp;
				确认：        <input name="confirmer"  value="" class="easyui-textbox" style="width:150px;"/>&nbsp;&nbsp;
				财务审核：<input name="financer"  value="" class="easyui-textbox" style="width:150px;"/>&nbsp;&nbsp;
				<!-- 客户签名：<input name=""  value="" class="easyui-textbox" style="width:150px;"/>&nbsp;&nbsp; -->
				</td>
			</tr>
		</table>
		<input type="hidden" name="id" value="${quotationVo.id}"/>
		<input type="hidden" name="appointId" value="${quotationVo.appointId}"/>
		<input type="hidden" name="designId" value="${quotationVo.designId}"/>
		<input type="hidden" name="clientAddress" value="${quotationVo.clientAddress}"/>
		<input type="hidden" name="clientName" value="${quotationVo.clientName}"/>
		<input type="hidden" name="designType" value="${quotationVo.designType}"/>
		<input type="hidden" name="clientPhone" value="${quotationVo.clientPhone}"/>
		<input type="hidden" name="billCode" value="${quotationVo.billCode}"/>	
	</form>
</div>
<script>

   $(function(){
	   $('.productNumCss').numberbox({
		   onChange:function(newValue,oldValue){
				  var unitPriceValue = $(this).parent().parent().find(".unitPriceCss").numberbox('getValue');
				  var areasValue = $(this).parent().parent().find(".areasCss").numberbox('getValue');
				  var unitAmount = 0.00;
				  if(unitPriceValue != null && unitPriceValue != '' 
					  && areasValue != null && areasValue != ''){
				  	unitAmount = newValue*unitPriceValue*areasValue;
				  	$(this).parent().parent().find(".amountCss").numberbox('setValue',unitAmount.toFixed(2));
				  	orderAmountCount();
				  }
			}
	   })
	   $('.unitPriceCss').numberbox({
		   "precision":2, 
		   onChange:function(newValue,oldValue){
			  var productNumValue = $(this).parent().parent().find(".productNumCss").numberbox('getValue');
			  var areasValue = $(this).parent().parent().find(".areasCss").numberbox('getValue');
			  var unitAmount = 0.00;
			  if(productNumValue != null && productNumValue != ''
				  && areasValue != null && areasValue != ''){
			  	unitAmount = newValue*productNumValue*areasValue;
			  	$(this).parent().parent().find(".amountCss").numberbox('setValue',unitAmount.toFixed(2));
			  	orderAmountCount();
			  }
		   }
	   })
	   $('.amountCss').numberbox({
		   "precision":2
	   })
	    $('.areasCss').numberbox({
		    	"precision":2, 
		    	onChange:function(newValue,oldValue){
				  var unitPriceValue = $(this).parent().parent().find(".unitPriceCss").numberbox('getValue');
				  var productNumValue = $(this).parent().parent().find(".productNumCss").numberbox('getValue');
				  var unitAmount = 0.00;
				  if(unitPriceValue != null && unitPriceValue != ''
					  && productNumValue != null && productNumValue != ''){
				  	unitAmount = newValue*unitPriceValue*productNumValue;
				  	$(this).parent().parent().find(".amountCss").numberbox('setValue',unitAmount.toFixed(2));
				  	orderAmountCount();
				  }
			   }
		  })
	  /*  $('#benefitAmount').numberbox({
		   onChange:function(newValue,oldValue){
			  var orderAmount = $("#orderAmount").numberbox('getValue');
			  var deposit = $("#deposit").numberbox('getValue');
			  if(orderAmount == null || orderAmount == '')orderAmount = 0;
			  if(deposit == null || deposit == '')deposit = 0;
			  if(newValue >= orderAmount){
    			 	$.messager.show({"title":"系统提示","msg":"优惠金额不能大于订单金额","timeout":1000});
    			 	return;
			  }
			  $("#realAmount").numberbox('setValue',(orderAmount-newValue).toFixed(2));
			  $("#remainAmount").html((orderAmount-newValue-deposit).toFixed(2));
		   }
	   }) */
	   setTimeout(orderAmountCount,100);
   })
   
  
</script>