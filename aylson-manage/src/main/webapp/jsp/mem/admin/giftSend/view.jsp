<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.table_share th{text-align:center;}
.table_share td{text-align:center;}
</style>
<div align="center">
	<form id="giftSendForm" method="post">
		<table class="tableForm">
			<tr>
				<th>收货人姓名：</th>
				<td ><input  name="consignee" value="${giftSendVo.consignee}"
					class="easyui-textbox" data-options="readonly:true"	style="width: 200px;" /></td>
				<th>收货人手机：</th>
				<td ><input  name="consigneePhone" value="${giftSendVo.consigneePhone}"
					class="easyui-textbox" data-options="readonly:true"	style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>参与活动时间：</th>
				<td ><input  value="${wxShareVo.createTimeStr}"
					class="easyui-datetimebox" data-options="readonly:true"	style="width: 200px;" /></td>
				<th>申请奖品时间：</th>
				<td ><input  value="${giftSendVo.createTimeStr}"
					class="easyui-datetimebox" data-options="readonly:true"	style="width: 200px;" /></td>
			</tr>
			<tr>
				<th>助力人数：</th>
				<td ><input  value="${wxShareVo.sharesHad}"
					class="easyui-numberbox" data-options="readonly:true"	style="width: 200px;" /></td>
				<th>助力完成率：</th>
				<td ><input  value="${(wxShareVo.assistValueHad+200)/10}%"
					class="easyui-textbox" data-options="readonly:true"	style="width: 200px;" /></td>
			</tr>
			<tr >
				<th colspan="4" style="text-align:center;height:25px;font-size:16px;color:#ff0000;width:100%">
					助力微信用户列表如下
				</th>
			<tr style="height:300px;">
				<td colspan="4" >
					<table class="tableForm table_share" >
					 	<tr>
					 	<th style="width:5px;">序号</th>
					 	<!-- <th>微信头像</th> -->
					 	<th>昵称</th>
					 	<th>助力时间</th>
					 	<th>助力值</th>
					 	</tr>
					 	<c:forEach items="${wxShareList}" var="wxShare" varStatus="status">
					 	<tr>
					 		<td>${status.count }</td>
					 		<!-- <td></td> -->
					 		<td>${wxShare.wxNickName}</td>
					 		<td><fmt:formatDate value="${wxShare.createTime}" type="both"/>  </td>
					 		<td>${wxShare.assistValue/10}%</td>
					 	</tr>
					 	</c:forEach>
					 </table>
				</td>
			</tr>
		</table>
	</form>
</div>
