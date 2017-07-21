<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<div align="center">
	<form id="editPassWordForm" method="post">
		<table class="tableForm">
				<tr>
					<th>旧密码：</th>
					<td>
						<input type="password" id="oldPassWord" name="oldPassWord" value="" class="easyui-textbox" data-options="required:true" style="width: 200px;"/>
					</td>
				</tr>
				<tr>
					<th>新的密码：</th>
					<td>
						<input type="password" id="newPassWord" name="newPassWord" value="" class="easyui-textbox" data-options="required:true"  validType="minLength[4]" invalidMessage="输入长度必须大于4位" style="width: 200px;"/>
					</td>
				</tr>
				<tr>
					<th>确认密码：</th>
					<td>
						<input type="password" name="secondPassWord" value="" class="easyui-textbox" data-options="required:true" validType="equalTo['#newPassWord']" invalidMessage="两次输入密码不匹配" style="width: 200px;"/>
					</td>
				</tr>
		</table>
	</form>
</div>