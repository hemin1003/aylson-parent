<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.ul_1 li{
	list-style-type:none;
	float:left;
}
.ul_1 li a{
	text-decoration:none;
}
</style>
<div align="center">
	<form id="projectInfoForm" method="post">
		<table class="tableForm">
			<tr>
				<th>客户名称：</th>
				<td>
				<input  value="${projectInfoVo.clientName}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>工程名称：</th>
				<td>
				<input  value="${projectInfoVo.projectName}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>客户地址：</th>
				<td>
				<input  value="${projectInfoVo.clientAddress}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>客户电话：</th>
				<td>
				<input  value="${projectInfoVo.mobilePhone}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>工程预算：</th>
				<td>
				<input  value="${projectInfoVo.budgetRange}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>项目状态：</th>
				<td>
				<select  class="easyui-combobox" readonly="true" style="width: 200px;" editable=false>
					<c:if test="${!empty projectStatusMap }">
						<c:forEach items="${projectStatusMap}" var="item">
							<option value="${item.key}" <c:if test = "${item.key == projectInfoVo.status }">selected = "selected" </c:if>>${item.value }</option>
						</c:forEach>
					</c:if>
				</select>
			</tr>
			<tr>
				<th>设计师：</th>
				<td>
				<input   value="${projectInfoVo.accountName}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
				<th>设计师电话：</th>
				<td>
				<input  value="${projectInfoVo.memberPhone}"
					class="easyui-textbox" readonly="true"
					style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>所属代理商：</th>
				<td>
				<input  readonly="readonly" value="${projectInfoVo.byAgent}" 
				data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/agentUser/getList?status=1', valueField:'agentName', textField:'agentName', method:'GET',
				onSelect:function(record){
					$('#byAgentId').val(record.id);
				}" class="easyui-combobox" style="width: 200px;" prompt="---------请选择---------"/>
				</td>
			</tr>
			<tr>
				<th>产品设计内容：</th>
				<td colspan="3">
					<c:forEach items="${projectDesignVo.productTypesList}" var="productTypes">
						<input type="checkbox" disabled="disabled" <c:if test="${ productTypes.ck == true}">checked="checked" </c:if>/>${productTypes.dicName}
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>哈玛尼系统：</th>
				<td colspan="3">
					<c:forEach items="${projectDesignVo.autoSysList}" var="autoSys">
						<input type="checkbox" disabled="disabled" <c:if test="${ autoSys.ck == true}">checked="checked" </c:if>/>${autoSys.dicName}
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>方案需求说明：</th>
				<td colspan="3"><input name="description" value="${projectDesignVo.description}" readonly="true"
					class="easyui-textbox" 	multiline="true" style="width:80%;height:80px;" /></td>
			</tr>
			<c:forEach items="${projectInfoVo.flowNodeList}" var="flowNode" varStatus="varStatus">
				<c:if test="${ flowNode.status == 51}">
					<c:set var="returnReason" value="${flowNode.remark}"/>
				</c:if>
			</c:forEach>
			<c:if test="${not empty returnReason}">
			<tr>
				<th>设计不满意说明：</th>
				<td colspan="3"><input  value="${returnReason}" readonly="true"
				class="easyui-textbox" 	multiline="true" style="width:80%;height:80px;" />
				</td>
			</tr>
			</c:if>
			<tr>
				<th>方案需求：</th>
				<td>
					<ul class="ul_1">
					<c:forEach items="${projectDesignVo.requirementList}" var="requirementAttach"  varStatus="requirement">
						<c:choose> 
						<c:when test="${fn:contains(requirementAttach.attachAddress,'.jpg') 
									|| fn:contains(requirementAttach.attachAddress,'.png')
									|| fn:contains(requirementAttach.attachAddress,'.gif')
									|| fn:contains(requirementAttach.attachAddress,'.bmp')
						}">
							<li>
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>
							<img  class="naviga_big" alt="" src="${requirementAttach.attachAddress }" style="width:50px;height:50px;">
							</a><br />
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>附件${requirement.index+1}</a>
							</li>
						</c:when>
						<c:when test="${fn:contains(requirementAttach.attachAddress,'.pdf') }">
							<li>
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>
							<img alt="" src="<%=request.getContextPath()%>/resources/icon/pdf.png" style="width:50px;height:50px;">
							</a><br />
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>附件${requirement.index+1}</a>
							</li>
						</c:when>
						<c:when test="${fn:contains(requirementAttach.attachAddress,'.doc') 
									|| fn:contains(requirementAttach.attachAddress,'.docx')
						}">
							<li>
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>
							<img alt="" src="<%=request.getContextPath()%>/resources/icon/doc.png" style="width:50px;height:50px;">
							</a><br />
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>附件${requirement.index+1}</a>
							</li>
						</c:when>
						<c:when test="${fn:contains(requirementAttach.attachAddress,'.rar') }">
							<li>
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>
							<img alt="" src="<%=request.getContextPath()%>/resources/icon/rar.png" style="width:50px;height:50px;">
							</a><br />
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>附件${requirement.index+1}</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>
							<img alt="" src="<%=request.getContextPath()%>/resources/icon/document.png" style="width:50px;height:50px;">
							</a><br />
							<a target='_blank' href="${requirementAttach.attachAddress }" title='${fn:substringAfter( requirementAttach.attachAddress,"@|@")}'>附件${requirement.index+1}</a>
							</li>
						</c:otherwise>
						</c:choose>
					</c:forEach>
					</ul>
				</td>
				<th rowspan="4">退回原因：</th>
				<td rowspan="4"><input id="auditOpition" name="auditOpition" value="${projectInfoVo.auditOpition}"
					class="easyui-textbox" 	multiline="true" style="width:100%;height:100%;" prompt="退回必须填写退回原因，不需要上传设计附件"/></td>
			</tr>
			<tr>
				<th><a href="javascript:void(0)" class="easyui-linkbutton c8" onclick="uploadDesign('fileShow','attachments','dc-design')" id="uploadFile" style="padding:2px;margin:5px">上传文件设计方案</a></th>
				<td>
				<ol id="fileShow" class="ol_1">
					<c:forEach items="${projectDesignVo.designList}" var="designAttach" varStatus="design">
					<li><a name='fileItem' target='_blank' href='${ designAttach.attachAddress}' alt='${fn:substringAfter(designAttach.attachAddress,"@|@")}'>
					${fn:substringAfter( designAttach.attachAddress,"@|@")}</a>&nbsp;
					<a href='#' onclick='delElement(this,"attachments")'>删除</a>
					</li>
					</c:forEach>
				</ol>
				</td>
				
			</tr>
			<%-- <tr>
				<th>退回原因：</th>
				<td colspan="3"><input id="auditOpition" name="auditOpition" value="${projectInfoVo.auditOpition}"
					class="easyui-textbox" 	multiline="true" style="width: 400px;height:50px;" prompt="退回必须填写退回原因，不需要上传设计附件"/></td>
			</tr> --%>
			
		</table>
		<input name="id" type="hidden" value="${projectInfoVo.id}"/>
		<input id="byAgentId" name="byAgentId" type="hidden" value="${projectInfoVo.byAgentId}"/>
		<input name="status" type="hidden" value="${projectInfoVo.status}"/>
		<input name="memberId" type="hidden" value="${projectInfoVo.memberId}"/>
		<input name="projectName" type="hidden" value="${projectInfoVo.projectName}"/>
		<input name="projectDesignId" type="hidden" value="${projectDesignVo.id}"/>
		<input id="attachments" name="attachments" type="hidden" value="${projectInfoVo.attachments}"/>
		<input name="attachmentType" type="hidden" value="2"/>
		<input name="memberPhone" type="hidden" value="${projectInfoVo.memberPhone}"/>
		<input name="wxOpenId" type="hidden" value="${projectInfoVo.wxOpenId}"/>
		<input name="memberType" type="hidden" value="${projectInfoVo.memberType}"/>
	</form>
</div>
<div id="divImage" style="display:none; left: 220px;top:-200px; position: absolute">
        <img id="imgBig" src="" alt="预览" />
</div>
<script>
	$(function(){
		viewImg();
		var attachments = "";
		$.each($("[name ='fileItem']"),function(n,value) {   
	           attachments += value.href+",";
		}); 
//		alert(attachments);
		$("#attachments").val(attachments);
	})
</script>