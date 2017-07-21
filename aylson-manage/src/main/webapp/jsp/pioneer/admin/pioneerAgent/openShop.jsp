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
				<th>商店名称：</th>
				<td>
				<input name="shopName"  value="${pioneerAgentVo.shopName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th rowspan="2">商店地址：</th>
				<td >
				<input  id="shopProvinceId" name="shopProvinceId" value="${pioneerAgentVo.shopProvinceId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=1', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#shopProvince').val(record.regionName);
					$('#shopCity').val('');
					$('#shopCityId').combobox('clear');
					$('#shopArea').val('');
					$('#shopAreaId').combobox('clear');
					$('#shopCityId').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
				}
				" class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				<input  id="shopCityId" name="shopCityId" value="${pioneerAgentVo.shopCityId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=2', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#shopCity').val(record.regionName);
					$('#shopArea').val('');
					$('#shopAreaId').combobox('clear');
					$('#shopAreaId').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
				}
				" class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				<input  id="shopAreaId" name="shopAreaId" value="${pioneerAgentVo.shopAreaId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=3', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#shopArea').val(record.regionName);
				}
				" 
				class="easyui-combobox" style="width: 130px;" prompt="-请选择-"/>
				</td>
			<tr>
				<td>
				<input name="shopAddress" value="${pioneerAgentVo.shopAddress}"
					class="easyui-textbox" data-options="required:true"
					style="width: 400px;" prompt="详细地址" />
				</td>
			</tr>
			<tr>
				<th>商店面积：</th>
				<td>
				<input name="shopAreas"  value="${pioneerAgentVo.shopAreas}"
					class="easyui-numberbox" data-options="required:true" precision="2"
					style="width: 200px;" />
				</td>
			</tr>
			
			<tr>
				<th>
				<a href="javascript:void(0)" class="easyui-linkbutton c8" onclick="uploadShopImg('shopImgItem','shopImgItem','dc-agentopen')" id="uploadFile" style="padding:2px;margin:5px">上传商店图片</a>
				</th>
				<td>
					<ul class="ul_1" id="shopImgItem" >
						<input id="shopImg" type="hidden" name="shopImg" value="${pioneerAgentVo.shopImg}"/> 
						
					</ul>
				</td>
			</tr> 
			<tr>
				<th>备注：</th>
				<td ><input id="remark" name="remark" value=""
					class="easyui-textbox" 	multiline="true" style="width: 400px;height:100px;" 
					/>
				</td>
			</tr>
			
		</table>
		<input name="id" type="hidden" value="${pioneerAgentVo.id}"/>
		<input name="submitterId" type="hidden" value="${pioneerAgentVo.submitterId}"/>
		<input id="shopProvince" name="shopProvince" type="hidden" value="${pioneerAgentVo.shopProvince}"/>
		<input id="shopCity" name="shopCity" type="hidden" value="${pioneerAgentVo.shopCity}"/>
		<input id="shopArea" name="shopArea" type="hidden" value="${pioneerAgentVo.shopArea}"/>
		<input name="signMode" type="hidden" value="${pioneerAgentVo.signMode}"/>
	</form>
</div>
<script>
	$(function(){
		resetShopImg();
	})
	
</script>