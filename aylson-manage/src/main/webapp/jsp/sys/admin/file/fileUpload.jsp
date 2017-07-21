<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<div align="center">
	<form id="fileUploadForm" method="post" enctype="multipart/form-data">
		<table class="">
			<tr style="height:50px;">
				<td >
					请选择文件：<input id="fileName" name="fileName"  class="easyui-filebox" data-options="buttonText:'选择文件'" style="width:300px;"/>
				</td>
			</tr>
		</table>
		<input type="hidden" id="bucket" name="bucket" value=""/>
	</form>
</div>
