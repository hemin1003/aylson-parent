<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.ul_1 li{
	list-style-type:none;
	float:left;
	width:150px;
	margin:0 5px;
}
.ul_1 li a{
	text-decoration:none;
}
</style>
<div align="center">
	<form id="pioneerAgentForm" method="post">
		<table class="tableForm" >
			<tr>
				<th>代理商名称：</th>
				<td>
					${pioneerAgentVo.agentName}
				</td>
				<th>代理商电话：</th>
				<td>
					${pioneerAgentVo.contactPhone}
				</td>
			</tr>
			<tr>
				<th>代理商地址：</th>
				<td colspan="3">
					${pioneerAgentVo.province}${pioneerAgentVo.city}${pioneerAgentVo.area}${pioneerAgentVo.address}
				</td>
			</tr>
			<tr>
				<th>商店名称：</th>
				<td>
					${pioneerAgentVo.shopName}
				</td>
				<th>商店面积(m2)：</th>
				<td>
					${pioneerAgentVo.shopAreas}
				</td>
			</tr>
			<tr>
				<th>商店地址：</th>
				<td colspan="3">
					${pioneerAgentVo.shopProvince}${pioneerAgentVo.shopCity}${pioneerAgentVo.shopArea}${pioneerAgentVo.shopAddress}
				</td>
			</tr>
			<tr>
				<th>提交人：</th>
				<td>
					${pioneerAgentVo.submitter}
				</td>
				<th>提交人电话：</th>
				<td>
					${pioneerAgentVo.submitterPhone}
				</td>
			</tr>
			<tr>
				<th>商品图片：</th>
				<td colspan="3">
					<ul class="ul_1" id="shopImgItem" >
						<c:forEach items="${pioneerAgentVo.shopImgList}" var="shopImg" >
						<li>
						<img class="naviga_big" alt="" src="${shopImg}" style="width:150px;height:150px;">
						</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
		</table>
	</form>
</div>
