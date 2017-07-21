<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="orderForm" method="post">
		<table class="tableForm">
			<tr>
				<th>订单号：</th>
				<td><input   value="${orderVo.orderNo}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
				<th>下单时间：</th>
				<td><input   value="${orderVo.createTimeStr}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>客户姓名：</th>
				<td><input   value="${orderVo.name}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
				<th>客户手机：</th>
				<td><input   value="${orderVo.mobilePhone}"
					class="easyui-textbox" data-options="readonly:true"
					style="width: 200px;" /></td>
		    </tr>
			<tr>
				<th>销售金额：</th>
				<td><input id="salesAmount"  value="${quotationVo.salesAmount}"
					class="easyui-numberbox" data-options="readonly:true,precision:2"
					style="width: 200px;" /></td>
				<th>定金：</th>
				<td><input  id="deposit" value="${quotationVo.deposit}"
					class="easyui-numberbox" data-options="readonly:true,precision:2"
					style="width: 200px;" /></td>
		    </tr>
			<tr>
				<th>优惠券金额：</th>
				<td><input id="couponValue"  value="${quotationVo.couponValue}"
					class="easyui-numberbox" data-options="readonly:true,precision:2"
					style="width: 200px;" /></td>
				<th>现金券减免金额：</th>
				<td><input  id="couponValueSum" value="${quotationVo.partnerCouponValue}"
					class="easyui-numberbox" data-options="readonly:true"
					style="width: 200px;" /></td>
		    </tr>
		    <c:if test="${ !empty couponVoList}">
		    	<tr>
		    		<th rowspan="2">是否用券：</th>
		    		<th colspan="3" style="text-align:left;">					
		    			<a href="#" onClick="chooseCoupon(${orderVo.mobilePhone})" class="easyui-linkbutton c8" style="width:100px">选择优惠券</a>
		    			<span id="showTip"></span>
		    		</th>
		    	</tr>
		    	<tr>
		    		<td colspan="3" id="coupons">
		    			
		    		</td>
		    	</tr> 
		    </c:if>
		    <tr>
				<th>结算金额：</th>
				<td><input  id="settleAmount" value="${quotationVo.salesAmount-quotationVo.deposit-quotationVo.couponValue}"
					class="easyui-numberbox" data-options="readonly:true,precision:2"
					style="width: 200px;" /></td>
				<td colspan="2">
					结算金额 = 销售金额 - 定金 - 优惠券金额 - 现金券减免金额
				</td>
		    </tr>
		</table>
		<input name="id" type="hidden" value="${orderVo.id}"/>
		<input id="couponIds" name="couponIds" type="hidden" value=""/>
		<input id="couponValue"  type="hidden" value="${quotationVo.couponValue}"/>
	</form>
</div>
<script>
$(function(){
	//setTimeout(countSettleAmount(0),1000);
	//setTimeout(countCouponValueSum(0),1000);
	//countSettleAmount(0);
	//countCouponValueSum(0);
})

//计算计算金额
	function countSettleAmount(partnerCouponValue){
		var salesAmount = $("#salesAmount").val();     //销售金额
	    var deposit = $("#deposit").val();             //定金
		//var benefitAmount = $("#benefitAmount").val(); //优惠金额
		var couponValue = $("#couponValue").val();     //客户优惠券减免金额
		//var settleAmount = salesAmount-deposit-benefitAmount-couponValue-partnerCouponValue;
		var settleAmount = salesAmount-deposit-couponValue-partnerCouponValue;
		console.info("settleAmount:"+settleAmount)
		//$('#settleAmount').val(settleAmount)
		$("#settleAmount").numberbox('setValue',settleAmount);
		return settleAmount;
		
	}
	
	//计算优惠券优惠总金额
	function countCouponValueSum(partnerCouponValue){
		//var couponValue = $("#couponValue").val();
		//var couponValueSum = parseInt(couponValue)+parseInt(partnerCouponValue);
		//$("#couponValueSum").val(couponValueSum);
		$("#couponValueSum").numberbox('setValue',partnerCouponValue);
		return couponValueSum;
	}
</script>
