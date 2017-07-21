<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="feedBackForm" method="post">
		<div style="padding:8px 5px;text-align:left;">
			<span style="font-weight:bolder;">&nbsp;&nbsp;标题：</span>${feedBackVo.title}
		</div>
		<div style="padding:8px 5px;text-align:left;">
			<span style="font-weight:bolder;">问题描述：</span>${feedBackVo.content}
		</div>
		
		<c:if test="${!empty feedBackVo.feedBackReplyList }">
		<div id="p" class="easyui-panel" title="历史回复：" style="height:350px;padding:10px;">
			<c:forEach items="${feedBackVo.feedBackReplyList}" var="feedBackReply">
				<c:if test="${feedBackReply.replyType == 1 }"> 
					<div style="text-align:left;padding:5px;">
					<span style="font-weight:bolder;">提问人:</span>
					<span style="background-color:#E4EFFF;padding:5px;border-radius:4px;">${feedBackReply.replyCont}</span>
					</div>
				</c:if>
				<c:if test="${feedBackReply.replyType == 2 }"> 
					<div style="text-align:right;padding:5px;">
					<span style="background-color:#B2E281;padding:5px;border-radius:4px;">${feedBackReply.replyCont}</span>
					<span style="font-weight:bolder;">：客服人员</span>
					</div>
				</c:if>
			</c:forEach>
		</div>
		</c:if>
		<div style="padding-top:5px;">
		<input name="replyCont" value="" prompt="添加回复内容..."
		class="easyui-textbox " data-options="required:true" style="width:98%;height:70px;" multiline="true" />
		</div>
		<input name="feedBackId" type="hidden" value="${feedBackVo.id}"/>
	</form>
</div>