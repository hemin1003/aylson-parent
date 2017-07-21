<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="agentUserForm" method="post">
		<table class="tableForm">
			<tr>
				<th>用户名：</th>
				<td><input name="userName" value="${agentUserVo.userName}"
				<c:if test="${flag eq 'edit' }">readonly="true"</c:if>
					class="easyui-textbox" data-options="required:true"	style="width: 240px;" />
				</td>
				<th>门店名称：</th>
				<td><input name="agentName" value="${agentUserVo.agentName}"
					class="easyui-textbox" data-options="required:true"	style="width: 240px;" />
				</td>
			</tr>
			<tr>
				<th>门店编号：</th>
				<td><input name="agentCode" value="${agentUserVo.agentCode}"
					class="easyui-textbox" data-options="required:true"	style="width: 240px;" />
				</td>
				<th>门店类型：</th>
				<td>
					<select name="isAgent" class="easyui-combobox" style="width: 240px;" editable=false required ="true">
							<option value=""></option>
							<option value="true" <c:if test="${agentUserVo.isAgent==true}" >selected ="selected"</c:if>>代理</option>
							<option value="false" <c:if test="${agentUserVo.isAgent==false}" >selected ="selected"</c:if>>直营</option>
					</select>
				</td>
				<%-- <th>用户角色：</th>
				<td>
				<input  name="roleId" value="${agentUserVo.roleId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/role/getList', valueField:'id', textField:'roleName', method:'GET',
				onSelect:function(record){
					$('#roleName').val(record.roleName);
				}
				" class="easyui-combobox" style="width: 200px;" prompt="-请选择-"/> --%>
			</tr>
			<tr>
				<th>联系人：</th>
				<td><input name="contacter" value="${agentUserVo.contacter}"
					class="easyui-textbox" data-options="required:true"	style="width: 240px;" />
				</td>
				<th>联系电话：</th>
				<td><input name="contactPhone" value="${agentUserVo.contactPhone}"
					class="easyui-textbox" data-options="required:true"	style="width: 240px;" />
				</td>
			</tr>
			<tr>
				<th>性别：</th>
				<td>
					<select name="sex" class="easyui-combobox" style="width: 240px;" editable=false>
							<option value="true" <c:if test="${agentUserVo.sex==true}" >selected ="selected"</c:if>>男</option>
							<option value="false" <c:if test="${agentUserVo.sex==false}" >selected ="selected"</c:if>>女</option>
					</select>
				</td>
				<th>出生日期：</th>
				<td>
					<input name="birthdayStr" value="${agentUserVo.birthdayStr}"
					class="easyui-datebox" 	style="width: 240px;" />
				</td>
			</tr>
			<tr>
				<th>证件号：</th>
				<td><input name="certificateNo" value="${agentUserVo.certificateNo}"
					class="easyui-textbox" data-options="required:true"	style="width: 240px;" />
				</td>
				<th>商店地址：</th>
				<td><input name="address" value="${agentUserVo.address}"
					class="easyui-textbox" data-options="required:true"	style="width: 240px;" />
				</td>
			</tr>
			<tr>
			<th>所属地区：</th>
				<td>
				<input  id="provinceId" name="provinceId" value="${agentUserVo.provinceId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=1', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#province').val(record.regionName);
					$('#city').val('');
					$('#cityId').combobox('clear');
					$('#area').val('');
					$('#areaId').combobox('clear');
					$('#cityId').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
				}
				" class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
				<input  id="cityId" name="cityId" value="${agentUserVo.cityId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=2', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#city').val(record.regionName);
					$('#area').val('');
					$('#areaId').combobox('clear');
					$('#areaId').combobox('reload','<%=request.getContextPath()%>/sys/region/getList?parentId='+record.regionId);
				}
				" class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
				<input  id="areaId" name="areaId" value="${agentUserVo.areaId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/region/getList?regionLevel=3', valueField:'regionId', textField:'regionName', method:'GET',
				onSelect:function(record){
					$('#area').val(record.regionName);
				}
				" 
				class="easyui-combobox" style="width: 80px;" prompt="-请选择-"/>
				</td>
			</tr>
			<tr>
				<th>代理产品：</th>
				<td>
					<%-- <input name="products" value="${agentUserVo.products}"
					class="easyui-textbox" data-options="required:true"	style="width: 240px;" /> --%>
					<c:forEach items="${agentUserVo.productTypesList}" var="productTypes">
						<input type="checkbox" name="product" onchange="change(this)" <c:if test="${ productTypes.ck == true}">checked="checked" </c:if> value="${productTypes.dicName}"/>${productTypes.dicName}
						<span style="display:none">
						<input type="checkbox" name="productId" <c:if test="${ productTypes.ck == true}">checked="checked" </c:if> value="${productTypes.id}"/>
						</span>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>密码：</th>
				<td><input name="pwd" id="pwd" value="" 
				<c:if test="${flag eq 'add' }">required="true"</c:if>
				<c:if test="${flag eq 'edit' }">prompt="如果不修改，请留空"</c:if>
					class="easyui-textbox" data-options=""	style="width: 240px;" />
				</td>
				<th>确认密码：</th>
				<td><input name="confirmPwd" value="" 
				<c:if test="${flag eq 'add' }">required="true" validType="equalTo['#pwd']" invalidMessage="两次输入密码不匹配" </c:if>
				<c:if test="${flag eq 'edit' }">prompt="如果不修改，请留空"</c:if>
					class="easyui-textbox" data-options=""	style="width: 240px;" />
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${agentUserVo.id}"/>
		<input name="userId" type="hidden" value="${agentUserVo.userId}"/>
		<input id="roleId" name="roleId" type="hidden" value="4"/>
		<input id="roleName" name="roleName" type="hidden" value="代理商"/>
		<input id="province" name="province" type="hidden" value="${agentUserVo.province}"/>
		<input id="city" name="city" type="hidden" value="${agentUserVo.city}"/>
		<input id="area" name="area" type="hidden" value="${agentUserVo.area}"/>
	</form>
	<script type="text/javascript">
	function change(obj){
		if($(obj).prop("checked")==true){
			$(obj).next("span").children(":checkbox").prop("checked",true);
		}else{
			$(obj).next("span").children(":checkbox").prop("checked",false);
		}
	}
	</script>
</div>