<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="rechargeConfigForm" method="post">
		<table class="tableForm">
			<tr>
				<th>主题：</th>
				<td>
					<input name="title" value="${rechargeConfigVo.title}" style="width:300px"
					class="easyui-textbox" data-options="required:true" prompt="描述该次充值主题，如：冲100送10金币"/>
				</td>
			</tr>
			<tr>
				<th>支付金额：</th>
				<td>
					<input name="amount" value="${rechargeConfigVo.amount}" style="width:300px"
					class="easyui-numberbox" data-options="required:true" prompt="需要支付的金额"/>
				</td>
			</tr>
			<tr>
				<th>兑换金币：</th>
				<td>
					<input name="buyGold" value="${rechargeConfigVo.buyGold}" style="width:300px"
					class="easyui-numberbox" data-options="required:true" prompt="支付后获取的金币值"/>
				</td>
			</tr>
			<tr>
				<th>赠送金币：</th>
				<td>
					<input name="sendGold" value="${rechargeConfigVo.sendGold}" style="width:300px"
					class="easyui-numberbox" data-options="required:true" prompt="本次支付赠送的金币值"/>
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${rechargeConfigVo.id}"/>
		<input name="status" type="hidden" value="${rechargeConfigVo.status}"/>
	</form>
</div>