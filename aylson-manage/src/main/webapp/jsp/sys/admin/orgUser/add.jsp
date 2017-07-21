<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="orgUserForm" method="post">
		<table class="tableForm">
			<tr>
				<th>用户名：</th>
				<td><input name="userName" value="${orgUserVo.userName}"
					class="easyui-textbox" data-options="required:true"	style="width: 200px;" 
					<c:if test="${flag eq 'edit'}"> readonly="ture"</c:if>/>
				</td>
				<th>姓名：</th>
				<td><input name="realName" value="${orgUserVo.realName}"
					class="easyui-textbox" data-options="required:true"	style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>性别：</th>
				<td>
					<select name="sex" class="easyui-combobox" style="width: 200px;" editable=false>
							<option value="true" <c:if test="${orgUserVo.sex==true}" >selected ="selected"</c:if>>男</option>
							<option value="false" <c:if test="${orgUserVo.sex==false}" >selected ="selected"</c:if>>女</option>
					</select>
				</td>
				<th>出生日期：</th>
				<td>
					<input name="birthdayStr" value="${orgUserVo.birthdayStr}"
					class="easyui-datebox" 	style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>组织部门：</th>
				<td>
				<input  name="orgId" value="${orgUserVo.orgId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/org/getList?parentId=1', valueField:'id', textField:'orgName', method:'GET',
				onSelect:function(record){
					$('#orgName').val(record.orgName);
				}
				" class="easyui-combobox" style="width: 200px;" prompt="---------请选择---------"/>
				</td>
				<th>用户角色：</th>
				<td>
				<input  name="roleId" value="${orgUserVo.roleId}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/role/getList', valueField:'id', textField:'roleName', method:'GET',
				onSelect:function(record){
					$('#roleName').val(record.roleName);
				}
				" class="easyui-combobox" style="width: 200px;" prompt="-请选择-"/>
			</tr>
			<tr>
				<th>固定电话：</th>
				<td><input name="fixedPhone" value="${orgUserVo.fixedPhone}"
					class="easyui-textbox" data-options="required:true"	style="width: 200px;" />
				</td>
				<th>移动电话：</th>
				<td><input name="mobilePhone" value="${orgUserVo.mobilePhone}"
					class="easyui-textbox" data-options="required:true"	style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>QQ号码：</th>
				<td><input name="qq" value="${orgUserVo.qq}"
					class="easyui-textbox" style="width: 200px;" />
				</td>
				<th>电子邮箱：</th>
				<td><input name="email" value="${orgUserVo.email}"
					class="easyui-textbox" 	style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>密码：</th>
				<td><input name="pwd" value=""
				<c:if test="${flag eq 'add' }">required="true"</c:if>
				<c:if test="${flag eq 'edit' }">prompt="如果不修改，请留空"</c:if>
					class="easyui-textbox" data-options=""	style="width: 200px;" />
				</td>
				<th>确认密码：</th>
				<td><input name="confirmPwd" value=""
				<c:if test="${flag eq 'add' }">required="true"</c:if>
				<c:if test="${flag eq 'edit' }">prompt="如果不修改，请留空"</c:if>
					class="easyui-textbox" data-options=""	style="width: 200px;" />
				</td>
			</tr> 
		</table>
		<input name="id" type="hidden" value="${orgUserVo.id}"/>
		<input name="userId" type="hidden" value="${orgUserVo.userId}"/>
		<input id="orgName" name="orgName" type="hidden" value="${orgUserVo.orgName}"/>
		<input id="roleName" name="roleName" type="hidden" value="${orgUserVo.roleName}"/>
	</form>
</div>